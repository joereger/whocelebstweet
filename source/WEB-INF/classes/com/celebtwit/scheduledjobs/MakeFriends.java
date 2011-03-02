package com.celebtwit.scheduledjobs;

import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.helpers.TwitPlHelper;
import com.celebtwit.systemprops.InstanceProperties;
import com.celebtwit.systemprops.SystemProperty;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        if (pl.getTwitteraccesstoken().length()==0 || pl.getTwitteraccesstokensecret().length()==0){
            return;
        }
        List<Long> twitterUsersFollowing = new ArrayList<Long>();
        try{
            TwitterFactory twitterFactory = new TwitterFactory();
            Twitter twitter = twitterFactory.getInstance();
            AccessToken accessToken = new AccessToken(pl.getTwitteraccesstoken(), pl.getTwitteraccesstokensecret());
            twitter.setOAuthAccessToken(accessToken);

            IDs ids = twitter.getFriendsIDs(1);
            if (ids!=null && ids.getIDs()!=null){
                for (int i = 0; i < ids.getIDs().length; i++) {
                    long id = ids.getIDs()[i];
                    twitterUsersFollowing.add(id);
                }
            }
        } catch (Exception ex){
            logger.error("", ex);
        }
        //Go get celebs for this pl
        //TwitplQuery
        ArrayList<Integer> plidList = new ArrayList<Integer>();
        plidList.add(pl.getPlid());
        List<Twit> celebs = HibernateUtil.getSession().createCriteria(Twit.class)
                                       .add(Restrictions.eq("isceleb", true))
                                       .addOrder(Order.desc("lastprocessed"))
                                       .add(TwitPlHelper.getCrit(plidList))
                                       .setMaxResults(10)
                                       .setCacheable(true)
                                       .list();
        for (Iterator<Twit> iterator=celebs.iterator(); iterator.hasNext();) {
            Twit twit=iterator.next();
            //Use the list of friends for this pl to see if this user is a friend
            boolean followingThisUser = false;
            for (Iterator<Long> userIterator=twitterUsersFollowing.iterator(); userIterator.hasNext();) {
                Long userid = userIterator.next();
                String useridStr = String.valueOf(userid);
                if (useridStr.equals(twit.getTwitteruserid())){
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
                TwitterFactory twitterFactory = new TwitterFactory();
                Twitter twitter = twitterFactory.getInstance();
                AccessToken accessToken = new AccessToken(pl.getTwitteraccesstoken(), pl.getTwitteraccesstokensecret());
                twitter.setOAuthAccessToken(accessToken);

                twitter.createFriendship(twit.getTwitterusername());
            } else {
                logger.debug("Woulda created a friendship with "+twit.getTwitterusername()+" but SystemProperty.PROP_DOSTATTWEETS != 1.");
            }
        } catch (Exception ex){
            logger.error("", ex);
        }
    }

}