package com.celebtwit.scheduledjobs;

import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.helpers.CountMentionsByCelebs;
import com.celebtwit.helpers.CountUniqueCelebsWhoMentioned;
import com.celebtwit.helpers.StartDateEndDate;
import com.celebtwit.helpers.TwitPlHelper;
import com.celebtwit.pingfm.PingfmUpdate;
import com.celebtwit.systemprops.InstanceProperties;
import com.celebtwit.systemprops.SystemProperty;
import com.celebtwit.util.Time;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;

import java.util.*;


/**
 * User: Joe Reger Jr
 * Date: Jul 19, 2006
 * Time: 2:22:28 PM
 */
public class StatsTweet implements StatefulJob {

    private static int MAXTWEETSPERPLPERRUN = 10;
    private HashMap<Integer, Integer> plTweetCounts = new HashMap<Integer, Integer>();

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (InstanceProperties.getRunScheduledTasksOnThisInstance()){
            logger.debug("execute() StatsTweet called");
                //Set week ago
                Date weekAgo = Time.xDaysAgoEnd(Calendar.getInstance(), 7).getTime();
                //List celebs who haven't been updated in the last week
                List<Twit> celebs = HibernateUtil.getSession().createCriteria(Twit.class)
                                       .add(Restrictions.eq("isceleb", true))
                                       .add(Restrictions.le("laststatstweet", weekAgo))
                                       .addOrder(Order.asc("laststatstweet"))
                                       .setCacheable(true)
                                       .list();
                for (Iterator<Twit> iterator=celebs.iterator(); iterator.hasNext();) {
                    Twit twit=iterator.next();
                    processTwit(twit);
                }
        } else {
            logger.debug("InstanceProperties.getRunScheduledTasksOnThisInstance() is FALSE for this instance so this task is not being executed.");
        }
    }

    private void processTwit(Twit twit){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("processTwit("+twit.getRealname()+")------------------------");
        ArrayList<Integer> plsTwitIsIn = TwitPlHelper.getPlidsTwitIsIn(twit);
        for (Iterator iterator = plsTwitIsIn.iterator(); iterator.hasNext();) {
            Integer plid = (Integer) iterator.next();
            Pl pl = Pl.get(plid);
            //If we haven't hit the limit of tweets per run per pl
            if (!hasReachedTweetLimitPerPl(pl)){
                boolean tweetMade = processTwitForPl(twit, pl);
                //If a tweet was made, record it
                if (tweetMade){ recordTweetCount(pl); }
            }
        }
    }

    private void recordTweetCount(Pl pl){
        if (plTweetCounts==null){plTweetCounts=new HashMap<Integer, Integer>();}
        if (plTweetCounts.containsKey(pl.getPlid())){
            int currentCount = plTweetCounts.get(pl.getPlid());
            plTweetCounts.put(pl.getPlid(), currentCount + 1);
        } else {
            plTweetCounts.put(pl.getPlid(), 1);
        }
    }

    private boolean hasReachedTweetLimitPerPl(Pl pl){
        if (plTweetCounts==null){plTweetCounts=new HashMap<Integer, Integer>();}
        if (plTweetCounts.containsKey(pl.getPlid())){
            int currentCount = plTweetCounts.get(pl.getPlid());
            if (currentCount>=MAXTWEETSPERPLPERRUN){
                return true;
            }
        }
        return false;
    }

    private boolean processTwitForPl(Twit twit, Pl pl){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("processTwitForPl("+twit.getRealname()+", "+pl.getName()+")");

        try{
            //Calculate start/end date
            StartDateEndDate sted = new StartDateEndDate(StartDateEndDate.TYPE_LAST7DAYS);
            //Count mentions/stats
            int mentions = CountMentionsByCelebs.get(twit, sted.getStartDate(), sted.getEndDate(), pl.getPlid());
            logger.debug("mentions="+mentions);
            int uniqueCelebs = CountUniqueCelebsWhoMentioned.get(twit, sted.getStartDate(), sted.getEndDate(), pl.getPlid());
            logger.debug("uniqueCelebs="+uniqueCelebs);
            //Build status
            StringBuffer status = new StringBuffer();
            status.append("@"+twit.getTwitterusername());
            status.append(" was tweeted ");
            status.append(mentions+ " times ");
            if (uniqueCelebs==1){
                status.append("by "+uniqueCelebs+" "+pl.getCelebiscalled()+" ");
            } else {
                status.append("by "+uniqueCelebs+" different "+pl.getCelebiscalled()+"s ");
            }
            status.append("in last 7 days ");
            status.append("http://"+pl.getName()+"/twitter/"+twit.getTwitterusername()+"/who/");
            logger.debug("status="+status.toString());
            logger.debug("status.length()="+status.length());
            //Update status
            if (mentions>0){
                if (status.length()>0){
                    if (SystemProperty.getProp(SystemProperty.PROP_DOSTATTWEETS).equals("1")){
                        TwitterFactory twitterFactory = new TwitterFactory();
                        Twitter twitter = twitterFactory.getInstance();
                        AccessToken accessToken = new AccessToken(pl.getTwitteraccesstoken(), pl.getTwitteraccesstokensecret());
                        twitter.setOAuthAccessToken(accessToken);
                        
                        Status twitterStatus = twitter.updateStatus(status.toString());
                        Long statusId = twitterStatus.getId();
                        logger.debug("twutterStatus.getId()="+statusId);
                        //Update the laststatstweet date
                        twit.setLaststatstweet(new Date());
                        twit.save();
                        //Do ping.fm
                        try{
                            if (pl.getPingfmapikey()!=null && !pl.getPingfmapikey().equals("")){
                                logger.debug("sending to ping.fm");
                                PingfmUpdate pfm = new PingfmUpdate(pl.getPingfmapikey(), status.toString(), pl.getPlid());
                                pfm.update();
                            }
                        } catch (Exception ex){
                            logger.error("", ex);
                        }
                        //Return true, telling the main method(s) that a tweet was made
                        return true;
                    } else {
                        logger.debug("not running because SystemProperty.PROP_DOSTATTWEETS != 1.");
                    }
                }
            }
        } catch (Exception ex){
            logger.error("", ex);
        }
        //Return false, telling the main method(s) that a tweet was not made
        return false;
    }


}