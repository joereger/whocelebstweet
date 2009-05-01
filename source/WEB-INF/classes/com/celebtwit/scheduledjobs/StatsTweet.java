package com.celebtwit.scheduledjobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.celebtwit.systemprops.InstanceProperties;
import com.celebtwit.systemprops.SystemProperty;
import com.celebtwit.dao.Userpersistentlogin;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.Twitpl;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.dao.hibernate.NumFromUniqueResult;
import com.celebtwit.util.Time;
import com.celebtwit.helpers.CountMentionsByCelebs;
import com.celebtwit.helpers.StartDateEndDate;
import com.celebtwit.helpers.CountUniqueCelebsWhoMentioned;


import java.util.*;

import twitter4j.Twitter;
import twitter4j.User;

/**
 * User: Joe Reger Jr
 * Date: Jul 19, 2006
 * Time: 2:22:28 PM
 */
public class StatsTweet implements StatefulJob {

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
                                       .setMaxResults(10)
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
        for (Iterator<Twitpl> plIt=twit.getTwitpls().iterator(); plIt.hasNext();) {
            Twitpl twitpl=plIt.next();
            Pl pl = Pl.get(twitpl.getPlid());
            processTwitForPl(twit, pl);
        }
    }

    private void processTwitForPl(Twit twit, Pl pl){
        Logger logger = Logger.getLogger(this.getClass().getName());
        //Calculate start/end date
        StartDateEndDate sted = new StartDateEndDate(StartDateEndDate.TYPE_LAST7DAYS);
        //Count mentions/stats
        int mentions = CountMentionsByCelebs.get(twit, sted.getStartDate(), sted.getEndDate(), pl.getPlid());
        int uniqueCelebs = CountUniqueCelebsWhoMentioned.get(twit, sted.getStartDate(), sted.getEndDate(), pl.getPlid());
        //Build status
        StringBuffer status = new StringBuffer();
        status.append("@"+twit.getTwitterusername());
        status.append(" was tweeted ");
        status.append(mentions+ " times ");
        status.append("by "+uniqueCelebs+" different "+pl.getCelebiscalled()+"s ");
        status.append("(last 7 days) ");
        status.append("http://"+pl.getName()+"/twitter/"+twit.getTwitterusername());
        logger.debug("status="+status.toString());
        logger.debug("status.length()="+status.length());
        //Update status
        try{
            if (status.length()>0){
                if (SystemProperty.getProp(SystemProperty.PROP_DOSTATTWEETS).equals("1")){
                    Twitter twitter = new Twitter(pl.getTwitterusername(), pl.getTwitterpassword());
                    twitter.update(status.toString());
                    //Update the laststatstweet date
                    twit.setLaststatstweet(new Date());
                    twit.save();
                } else {
                    logger.debug("Not running because SystemProperty.PROP_DOSTATTWEETS != 1.");
                }
            }
        } catch (Exception ex){
            logger.error("", ex);
        }
    }


}