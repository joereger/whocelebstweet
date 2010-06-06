package com.celebtwit.scheduledjobs;

import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.helpers.TwitMention;
import com.celebtwit.pingfm.PingfmUpdate;
import com.celebtwit.systemprops.InstanceProperties;
import com.celebtwit.systemprops.SystemProperty;
import com.celebtwit.util.DateDiff;
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
public class StatsTweetNonceleb implements StatefulJob {

    private static int MAXTWEETSPERPLPERRUN = 10;
    private HashMap<Integer, Integer> plTweetCounts = new HashMap<Integer, Integer>();

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (InstanceProperties.getRunScheduledTasksOnThisInstance()){
            logger.debug("execute() StatsTweet called");

                //List pls
                List<Pl> pls = HibernateUtil.getSession().createCriteria(Pl.class)
                                       .add(Restrictions.ne("twitteraccesstoken", ""))
                                       .addOrder(Order.asc("plid"))
                                       .setCacheable(true)
                                       .list();
                for (Iterator<Pl> iterator=pls.iterator(); iterator.hasNext();) {
                    Pl pl=iterator.next();
                    processPl(pl);
                }
        } else {
            logger.debug("InstanceProperties.getRunScheduledTasksOnThisInstance() is FALSE for this instance so this task is not being executed.");
        }
    }


    private void processPl(Pl pl){
        Logger logger = Logger.getLogger(this.getClass().getName());
        String emptyStr = "";
        String startDateStr = Time.dateformatfordb(Time.getCalFromDate(Time.xDaysAgoStart(Calendar.getInstance(), 7).getTime()));
        String endDateStr = Time.dateformatfordb(Time.getCalFromDate(new Date()));
        int numbertoget = 20;

        List results = HibernateUtil.getSession().createQuery("SELECT sum(1) as numOfMentions, mention.twitidmentioned FROM Mention mention, Twit twit WHERE twit.twitid=mention.twitidmentioned AND twit.laststatstweet<'"+startDateStr+"' AND mention.plid='"+pl.getPlid()+"' AND mention.created_at>'"+startDateStr+"' and mention.created_at<'"+endDateStr+"' and mention.ismentionedaceleb=false group by mention.twitidmentioned order by sum(1) desc"+emptyStr).setMaxResults(numbertoget).list();
        for (Iterator iterator=results.iterator(); iterator.hasNext();) {
            Object[] row = (Object[])iterator.next();
            Long numOfMentions = (Long)row[0];
            int twitidmentioned = (Integer)row[1];
            Twit twit = Twit.get(twitidmentioned);
            TwitMention twitMention = new TwitMention();
            twitMention.setTwit(twit);
            twitMention.setMentions(numOfMentions.intValue());
            processTwitMention(twitMention, pl);
        }


    }


    private void processTwitMention(TwitMention twitMention, Pl pl){
        Logger logger = Logger.getLogger(this.getClass().getName());
        //If we haven't hit the limit of tweets per run per pl
        if (!hasReachedTweetLimitPerPl(pl)){
            boolean tweetMade = processTwitMentionForPl(twitMention, pl);
            //If a tweet was made, record it
            if (tweetMade){ recordTweetCount(pl); }
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

    private boolean processTwitMentionForPl(TwitMention twitMention, Pl pl){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("processTwitMentionForPl("+twitMention.getTwit().getRealname()+", "+pl.getName()+")");

        try{
            boolean shouldTweet = true;
            //If not enough tweets
            if (twitMention.getMentions()<=0){
                shouldTweet = false;
            }
            //Figure out whether this twit has been tweeted too recently
            int daysAgo = DateDiff.dateDiff("day", Calendar.getInstance(), Time.getCalFromDate(twitMention.getTwit().getLaststatstweet()));
            if (daysAgo<7){
                shouldTweet = false;
            }
            //Build status
            if (shouldTweet){
                StringBuffer status = new StringBuffer();
                status.append("@"+twitMention.getTwit().getTwitterusername());
                status.append(" tweeted ");
                status.append("by "+pl.getCelebiscalled()+"s ");
                if (twitMention.getMentions()>1){
                    status.append(twitMention.getMentions()+ " times ");
                } else {
                    status.append(twitMention.getMentions()+ " time ");   
                }
                status.append("in the last week! ");
                status.append("http://"+pl.getName()+"/twitter/"+twitMention.getTwit().getTwitterusername()+"/");
                logger.debug("status="+status.toString());
                logger.debug("status.length()="+status.length());
                //Update status
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
                        twitMention.getTwit().setLaststatstweet(new Date());
                        twitMention.getTwit().save();
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