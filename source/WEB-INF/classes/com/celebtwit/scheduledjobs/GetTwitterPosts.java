package com.celebtwit.scheduledjobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.apache.log4j.Logger;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.lang.time.DateFormatUtils;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Distinct;
import org.hibernate.criterion.Projections;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.Element;
import com.celebtwit.dao.*;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.dao.hibernate.NumFromUniqueResult;
import com.celebtwit.util.*;
import com.celebtwit.session.PersistentLogin;
import com.celebtwit.systemprops.InstanceProperties;
import com.celebtwit.cache.html.DbcacheexpirableCache;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

//import twitter4j.Status;
import twitter4j.RateLimitStatus;
import twitter4j.Twitter;



/**
 * User: Joe Reger Jr
 * Date: Jul 19, 2006
 * Time: 2:22:28 PM
 */
public class GetTwitterPosts implements StatefulJob {

    //regged w/joereger@charter.net
    private static String twitterusername = "whocelebstweet";
    private static String twitterpassword = "whocelebstweetrules";

    //Make sure multiple threads don't process the same twit
    private static HashMap<Integer, Boolean> processingStatus = new HashMap<Integer, Boolean>();
    private static HashMap<Integer, Boolean> editedDuringProcessing = new HashMap<Integer, Boolean>();
    //private static boolean processRunning = false;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (InstanceProperties.getRunScheduledTasksOnThisInstance()){
            logger.debug("execute() GetTwitterPosts called");
            //If another trigger is running, don't run
//            if (processRunning){
//                logger.error("Not running because processRunning=true "+Time.dateformatcompactwithtime(Time.nowInUserTimezone("EST")));
//                return;
//            } else {
//                processRunning = true;
//            }
            //Get on with it
            List<Twit> twits = HibernateUtil.getSession().createCriteria(Twit.class)
                                               .add(Restrictions.eq("isceleb", true))
                                               .addOrder(Order.asc("lastprocessed"))
                                               .setCacheable(true)
                                               .list();
            for (Iterator<Twit> twitIterator=twits.iterator(); twitIterator.hasNext();) {
                Twit twit=twitIterator.next();
                if (!isProcessing(twit.getTwitid())){
                    //Mark as processing, process and then unmark
                    startProcessing(twit.getTwitid());
                    collectPosts(twit);
                    endProcessing(twit.getTwitid());
                    //If was edited during processing, set since_id to 1 so it'll be refreshed
                    if (isInEditedDuringProcessing(twit.getTwitid())){
                        try{
                            twit.setSince_id("1");
                            twit.save();
                        } catch (Exception ex) { logger.error("", ex); }
                        removeFromEditedDuringProcessing(twit.getTwitid());
                    }
                }
            }
        } else {
            logger.debug("InstanceProperties.getRunScheduledTasksOnThisInstance() is FALSE for this instance so this task is not being executed.");
        }
        //Reset the processRunning thing so that others can run too
        //processRunning = false;
    }

    public static void collectPosts(Twit twit){
        Logger logger = Logger.getLogger(GetTwitterPosts.class);
        logger.debug("collectPosts("+twit.getTwitterusername()+")");
        try{
            long since_id_curr = 1;
            long since_id_orig = 1;
            if (Num.islong(twit.getSince_id())){ since_id_orig = Long.parseLong(twit.getSince_id());}
            String profile_image_url = twit.getProfile_image_url();
            String website_url = twit.getWebsite_url();
            String description = twit.getDescription();
            int statuses_count = twit.getStatuses_count();
            int followers_count = twit.getFollowers_count();
            boolean foundAPost = false;
            //Iterate pages
            for(int page=1; page<20; page++){
                logger.debug("page="+page);

                List<TwitterStatus> statuses = callApi(twit, since_id_orig, page);
                if (statuses==null || statuses.size()<=0){
                    logger.debug("statuses==null || statuses.size()<=0 so breaking out of page 1->100 loop page="+page);
                    break; //Save api calls
                } else {
                    foundAPost = true;
                }
                for (Iterator<TwitterStatus> statusIterator=statuses.iterator(); statusIterator.hasNext();) {
                    TwitterStatus status=statusIterator.next();
                    processStatus(twit, status);
                    profile_image_url = status.getProfile_image_url();
                    website_url = status.getWebsite_url();
                    description = status.getDescription();
                    if (Num.isinteger(status.getStatuses_count())){ statuses_count = Integer.parseInt(status.getStatuses_count()); }
                    if (Num.isinteger(status.getFollowers_count())){ followers_count = Integer.parseInt(status.getFollowers_count()); }
                    //Only keep the highest status seen so far
                    if (Num.islong(status.getId())){
                        if (Long.parseLong(status.getId())>since_id_curr){
                            since_id_curr = Long.parseLong(status.getId());
                        }
                    }
                }
            }
            //Only if a post was found
            if (foundAPost){
                //Save twit update
                if (Num.islong(twit.getSince_id())){
                    if (since_id_curr<Long.parseLong(twit.getSince_id())){
                        //Just make sure I don't set to an older since_id than what's stored currently
                        since_id_curr = Long.parseLong(twit.getSince_id());
                    }
                }
                //Update this twit
                twit.setProfile_image_url(profile_image_url);
                twit.setSince_id(String.valueOf(since_id_curr));
                twit.setLastprocessed(new Date());
                twit.setWebsite_url(website_url);
                twit.setDescription(description);
                twit.setFollowers_count(followers_count);
                twit.setStatuses_count(statuses_count);
                twit.save();
                //Flush the cache for this user
                DbcacheexpirableCache.flush("PublicTwitterTweetlist.java-twitid-"+twit.getTwitid());
            }
            //Report on RateLimitStatus
            //RateLimitStatus rls = twitter.rateLimitStatus();
            //logger.debug("Twitter RateLimitStatus: hourlylimit="+rls.getHourlyLimit()+" remaininghits="+rls.getRemainingHits()+" resettimeinseconds="+rls.getResetTimeInSeconds()+" datetime="+Time.dateformatcompactwithtime(Time.getCalFromDate(rls.getDateTime())));
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }


    //Processing Status
    private static void startProcessing(int twitid){
        processingStatus.put(twitid, true);
    }
    private static void endProcessing(int twitid){
        if (processingStatus.containsKey(twitid)){ processingStatus.remove(twitid); }
    }
    public static boolean isProcessing(int twitid){
        if (processingStatus.containsKey(twitid)){ return true; }
        return false;
    }

    //EditedDuringProcessing
    public static void addToEditedDuringProcessing(int twitid){
        editedDuringProcessing.put(twitid, true);
    }
    private static void removeFromEditedDuringProcessing(int twitid){
        if (editedDuringProcessing.containsKey(twitid)){ editedDuringProcessing.remove(twitid); }
    }
    public static boolean isInEditedDuringProcessing(int twitid){
        if (editedDuringProcessing.containsKey(twitid)){ return true; }
        return false;
    }
    private static void clearEditedDuringProcessing(){
        editedDuringProcessing = new HashMap<Integer, Boolean>();
    }





    public static ArrayList<TwitterStatus> callApi(Twit twit, long since_id, int page){
        Logger logger = Logger.getLogger(GetTwitterPosts.class);
        ArrayList<TwitterStatus> out = new ArrayList<TwitterStatus>();
        HttpClient client = new HttpClient();

        // pass our credentials to HttpClient, they will only be used for
        // authenticating to servers with realm "realm" on the host
        // "www.verisign.com", to authenticate against
        // an arbitrary realm or host change the appropriate argument to null.
        client.getState().setCredentials(
            new AuthScope(null, -1, null),
            new UsernamePasswordCredentials(twitterusername, twitterpassword)
        );
        client.getParams().setAuthenticationPreemptive(true);

        // create a GET method that reads a file over HTTPS, we're assuming
        // that this file requires basic authentication using the realm above.
        GetMethod post = new GetMethod("http://twitter.com/statuses/user_timeline.xml");

        // Tell the GET method to automatically handle authentication. The
        // method will use any appropriate credentials to handle basic
        // authentication requests.  Setting this value to false will cause
        // any request for authentication to return with a status of 401.
        // It will then be up to the client to handle the authentication.
        post.setDoAuthentication(true);

        //Needs to be less than 140 chars
        post.setQueryString("id="+twit.getTwitterusername()+"&since_id="+since_id+"&page="+page+"&count=200");
        

        try {
            int requestStatus = client.executeMethod( post );
            logger.debug("-----------page="+page);
            logger.debug(post.getResponseBodyAsString());
            logger.debug("-----------");
            //Parse that mofo
            SAXReader reader = new SAXReader();
            Document document = reader.read(post.getResponseBodyAsStream());
            Element root = document.getRootElement();
            //Check 4 errorz
            for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                Element el = (Element)i.next();
                if (el.getName().equals("error")){
                    logger.debug("API ERROR: "+el.getText());
                    return new ArrayList<TwitterStatus>();
                }
            }
            //Iterate again looking for
            for (Iterator i = root.elementIterator("status"); i.hasNext(); ) {
                Element el = (Element)i.next();
                //logger.debug("-----------page="+page);
                //logger.debug(el.asXML());
                //logger.debug("-----------");

                //Sat Apr 18 03:17:25 +0000 2009
                SimpleDateFormat df = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy");
                Date created_at = new Date();
                try{ created_at = df.parse(el.elementText("created_at")); } catch (Exception ex){ logger.error("", ex); }

                TwitterStatus ts = new TwitterStatus();
                ts.setId(el.elementText("id"));
                ts.setCreated_at(created_at);
                ts.setCreated_at_string(el.elementText("created_at"));
                ts.setText(el.elementText("text"));
                for (Iterator j = el.elementIterator("user"); j.hasNext(); ) {
                    Element elUser = (Element)j.next();
                    ts.setProfile_image_url(elUser.elementText("profile_image_url"));
                    ts.setWebsite_url(elUser.elementText("url"));
                    ts.setDescription(elUser.elementText("description"));
                    ts.setFollowers_count(elUser.elementText("followers_count"));
                    ts.setStatuses_count(elUser.elementText("statuses_count"));
                    ts.setFollowing(elUser.elementText("following"));
                }
                out.add(ts);
                logger.debug("parsing xml - id="+ts.getId()+" created_at="+ts.getCreated_at()+" text="+ts.getText());
                //logger.debug("-----------");
            }
        } catch (Exception ex){
            logger.error("",ex);
        } finally {
            post.releaseConnection();
        }
        logger.debug("done processing.");
        return out;
    }




    public static void processStatus(Twit twit, TwitterStatus status){
        Logger logger = Logger.getLogger(GetTwitterPosts.class);
        logger.debug("---");
        logger.debug("processStatus("+twit.getTwitterusername()+", "+status.getText()+")");
        try{
            int numberWithSameTwitterGuid =NumFromUniqueResult.getInt("select count(*) from Twitpost where twitterguid='"+status.getId()+"'");
            if (numberWithSameTwitterGuid==0){
                Twitpost twitpost = new Twitpost();
                twitpost.setTwitid(twit.getTwitid());
                twitpost.setCreated_at(status.getCreated_at());
                twitpost.setTwitterguid(String.valueOf(status.getId()));
                twitpost.setPost(status.getText());
                twitpost.save();
                //Process it
                processTwitpost(twitpost, twit);
            }
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }

    public static void processTwitpost(Twitpost twitpost, Twit twitCeleb){
        Logger logger = Logger.getLogger(GetTwitterPosts.class);
        logger.debug("processTwitpost(id="+twitpost.getTwitterguid()+")");
        try{
            ArrayList<String> mentions = parseOutTwitterUsernames(twitpost.getPost());
            for (Iterator<String> stringIterator=mentions.iterator(); stringIterator.hasNext();) {
                String mentionedUsername = stringIterator.next();
                //Update this twit
                Twit twitToUpdate = null;
                //See if there's a Twit with this username
                List<Twit> twits = HibernateUtil.getSession().createCriteria(Twit.class)
                                                   .add(Restrictions.eq("twitterusername", mentionedUsername))
                                                   .setCacheable(true)
                                                   .list();
                for (Iterator<Twit> twitIterator=twits.iterator(); twitIterator.hasNext();) {
                    Twit twit=twitIterator.next();
                    twitToUpdate = twit;
                }
                //If there wasn't one, create it
                if (twitToUpdate==null){
                    Twit newTwit = new Twit();
                    newTwit.setIsceleb(false);
                    newTwit.setLastprocessed(new Date());
                    newTwit.setLaststatstweet(new Date());
                    newTwit.setRealname("");
                    newTwit.setSince_id("1");
                    newTwit.setTwitterusername(mentionedUsername);
                    newTwit.setProfile_image_url("");
                    newTwit.setDescription("");
                    newTwit.setWebsite_url("");
                    newTwit.setStatuses_count(0);
                    newTwit.setFollowers_count(0);
                    newTwit.save();
                    twitToUpdate = newTwit;
                }
                //Create a record of the mention
                for (Iterator<Twitpl> twitplIt=twitCeleb.getTwitpls().iterator(); twitplIt.hasNext();) {
                    Twitpl twitpl=twitplIt.next();
                    //Save a mention record for each pl that this celeb is part of
                    saveMention(twitToUpdate, twitpost, twitpl);
                }
                //Increment mentions counters
                //twitToUpdate.setLastprocessed(new Date());
                //twitToUpdate.save();
            }
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }

    public static void saveMention(Twit twitToUpdate, Twitpost twitpost, Twitpl twitpl){
        Logger logger = Logger.getLogger(GetTwitterPosts.class);
        try{
            //Make sure it's not a self tweet
            if (twitpost.getTwitid()!=twitToUpdate.getTwitid()){
                //See if any exact mentions exist (other thread processing... something like that)
                int numberOfSameMentions =NumFromUniqueResult.getInt("select count(*) from Mention where twitpostid='"+twitpost.getTwitpostid()+"' and twitidceleb='"+twitpost.getTwitid()+"' and twitidmentioned='"+twitToUpdate.getTwitid()+"' and plid='"+twitpl.getPlid()+"'");
                if (numberOfSameMentions==0){
                    //Only insert if there are no exactly similar mentions
                    Mention mention = new Mention();
                    mention.setTwitpostid(twitpost.getTwitpostid());
                    mention.setTwitidceleb(twitpost.getTwitid());
                    mention.setTwitidmentioned(twitToUpdate.getTwitid());
                    mention.setIsmentionedaceleb(twitToUpdate.getIsceleb());
                    mention.setCreated_at(twitpost.getCreated_at());
                    mention.setPlid(twitpl.getPlid()); // <-- Setting plid of mention to the one of the plids of the celeb
                    mention.save();
                }
            }
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }

    public static ArrayList<String> parseOutTwitterUsernames(String post){
        Logger logger = Logger.getLogger(GetTwitterPosts.class);
        ArrayList<String> out = new ArrayList<String>();
        try{
            //Pattern p = Pattern.compile("(\\w)+");
            Pattern p = Pattern.compile("(^|\\W)@(\\w)+");
            Matcher matcher = p.matcher(post);
            while(matcher.find()){
                //logger.debug("parseOutTwitterUsernames() matcher.group()="+matcher.group());
                String s = matcher.group();
                logger.debug("parseOutTwitterUsernames() -- matcher.group()="+s);
                logger.debug("parseOutTwitterUsernames() -- s.indexOf(\"@\")="+s.indexOf("@"));
                if(s.indexOf("@")>-1){
                    if (s.length()>s.indexOf("@")+1){
                        s = s.substring(s.indexOf("@")+1, s.length());
                        logger.debug("parseOutTwitterUsernames() -- s="+s);
                        out.add(s);
                    }
                }

//                if (s.length()>2){
//                    s = s.substring(1, s.length());
//                    logger.debug("parseOutTwitterUsernames() -- @"+s);
//                    out.add(s);
//                }
            }
        } catch (Exception ex) {
            logger.error("", ex);
        }
        return out;
    }






}