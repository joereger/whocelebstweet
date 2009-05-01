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
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.dao.hibernate.NumFromUniqueResult;


import java.util.*;

import twitter4j.Twitter;
import twitter4j.User;

/**
 * User: Joe Reger Jr
 * Date: Jul 19, 2006
 * Time: 2:22:28 PM
 */
public class MakeFriends implements StatefulJob {

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (InstanceProperties.getRunScheduledTasksOnThisInstance()){
            logger.debug("execute() MakeFriends called");

            List<Pl> pls = HibernateUtil.getSession().createCriteria(Pl.class)
                                           .addOrder(Order.asc("name"))
                                           .setCacheable(true)
                                           .list();
            //Iterate first time for sister domains
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
        if (pl.getTwitterusername().length()==0 || pl.getTwitterpassword().length()==0){
            return;
        }
        List<User> twitterUsersFollowing = new ArrayList<User>();
        try{
            Twitter twitter = new Twitter(pl.getTwitterusername(), pl.getTwitterpassword());
            twitterUsersFollowing = twitter.getFriends();
            logger.error("processPl("+pl.getName()+") twitterUsersFollowing.size()="+twitterUsersFollowing.size());
        } catch (Exception ex){
            logger.error("", ex);
        }
        //Go get celebs for this pl
        List<Twit> celebs = HibernateUtil.getSession().createCriteria(Twit.class)
                                       .add(Restrictions.eq("isceleb", true))
                                       .addOrder(Order.desc("lastprocessed"))
                                       .createCriteria("twitpls")
                                       .add(Restrictions.eq("plid", pl.getPlid()))
                                       .setMaxResults(5000)
                                       .setCacheable(true)
                                       .list();
        for (Iterator<Twit> iterator=celebs.iterator(); iterator.hasNext();) {
            Twit twit=iterator.next();
            //Use the list of friends for this pl to see if this user is a friend
            boolean followingThisUser = false;
            for (Iterator<User> userIterator=twitterUsersFollowing.iterator(); userIterator.hasNext();) {
                User user=userIterator.next();
                if (user.getScreenName().equals(twit.getTwitterusername())){
                    followingThisUser = true;
                }
            }
            //Only process this user if we're not following them
            if (!followingThisUser){
                processTwitForPl(twit, pl);
            }
        }
    }

    private void processTwitForPl(Twit twit, Pl pl){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            if (SystemProperty.getProp(SystemProperty.PROP_DOSTATTWEETS).equals("1")){
                Twitter twitter = new Twitter(pl.getTwitterusername(), pl.getTwitterpassword());
                twitter.create(twit.getTwitterusername());
            } else {
                logger.debug("Woulda created a friendship with "+twit.getTwitterusername()+" but SystemProperty.PROP_DOSTATTWEETS != 1.");
            }
        } catch (Exception ex){
            logger.error("", ex);
        }
    }

}