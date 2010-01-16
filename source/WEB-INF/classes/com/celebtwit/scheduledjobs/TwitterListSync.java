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
import com.celebtwit.util.Num;
import com.celebtwit.helpers.CountMentionsByCelebs;
import com.celebtwit.helpers.StartDateEndDate;
import com.celebtwit.helpers.CountUniqueCelebsWhoMentioned;
import com.celebtwit.cache.html.DbcacheexpirableCache;


import java.util.*;

import twitter4j.Twitter;
import twitter4j.User;
import twitter4j.PagableResponseList;


/**
 * User: Joe Reger Jr
 * Date: Jul 19, 2006
 * Time: 2:22:28 PM
 */
public class TwitterListSync implements StatefulJob {

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (InstanceProperties.getRunScheduledTasksOnThisInstance()){
            logger.debug("execute() TwitterListSync called");
                //List 1
                List<Pl> pls = HibernateUtil.getSession().createCriteria(Pl.class)
                                       .add(Restrictions.ne("twitterusername", ""))
                                       .add(Restrictions.ne("twitterpassword", ""))
                                       .addOrder(Order.asc("plid"))
                                       .setCacheable(true)
                                       .list();
                for (Iterator<Pl> iterator=pls.iterator(); iterator.hasNext();) {
                    Pl pl=iterator.next();
                    //If there's a list 1
                    if (pl.getListownerscreenname1()!=null && pl.getListownerscreenname1().length()>0){
                        if (pl.getListid1()!=null && pl.getListid1().length()>0){
                            processPl(pl, pl.getListownerscreenname1(), pl.getListid1());
                        }
                    }
                    //If there's a list 2
                    if (pl.getListownerscreenname2()!=null && pl.getListownerscreenname2().length()>0){
                        if (pl.getListid2()!=null && pl.getListid2().length()>0){
                            processPl(pl, pl.getListownerscreenname2(), pl.getListid2());
                        }
                    }
                    //If there's a list 3
                    if (pl.getListownerscreenname3()!=null && pl.getListownerscreenname3().length()>0){
                        if (pl.getListid3()!=null && pl.getListid3().length()>0){
                            processPl(pl, pl.getListownerscreenname3(), pl.getListid3());
                        }
                    }
                }
        } else {
            logger.debug("InstanceProperties.getRunScheduledTasksOnThisInstance() is FALSE for this instance so this task is not being executed.");
        }
    }

    private void processPl(Pl pl, String listownerscreenname, String listid){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            Twitter twitter = new Twitter(pl.getTwitterusername(), pl.getTwitterpassword());
            PagableResponseList<User> listMembers = twitter.getUserListMembers(listownerscreenname, Integer.parseInt(listid), -1);
            for(User user :listMembers){
                logger.debug("user.getScreenName()="+user.getScreenName()+" user.getName()="+user.getName());
                //Start a blank Twit
                Twit twit = new Twit();
                twit.setIsceleb(true);
                twit.setLastprocessed(Time.xYearsAgoStart(Calendar.getInstance(), 25).getTime());
                twit.setLaststatstweet(Time.xYearsAgoStart(Calendar.getInstance(), 25).getTime());
                twit.setProfile_image_url("");
                twit.setSince_id("1");
                twit.setTwitterusername("");
                twit.setTwitteruserid("");
                twit.setDescription("");
                twit.setWebsite_url("");
                twit.setStatuses_count(0);
                twit.setFollowers_count(0);
                //Search for another one named twitterusername
                List<Twit> twits = HibernateUtil.getSession().createCriteria(Twit.class)
                                                   .add(Restrictions.eq("twitterusername", user.getScreenName()))
                                                   .setCacheable(true)
                                                   .list();
                for (Iterator<Twit> twitIterator=twits.iterator(); twitIterator.hasNext();) {
                    Twit twit1=twitIterator.next();
                    //If one already exists with this twitterusername, use it.
                    twit = twit1;
                    logger.debug("user.getScreenName()="+user.getScreenName()+" twit already existed twitid="+twit.getTwitid());
                }
                //Set stuff in twit
                twit.setTwitterusername(user.getScreenName());
                twit.setTwitteruserid(String.valueOf(user.getId()));
                twit.setRealname(user.getName());
                twit.setIsceleb(true);
                twit.save();
                //Make sure any already-mentions are marked as being about a celeb, now that this twit is one
                HibernateUtil.getSession().createQuery("update Mention m set ismentionedaceleb=true where m.twitidmentioned='"+twit.getTwitid()+"'").executeUpdate();
                //Does a pl relationship exist yet?
                boolean isTwitInPl = false;
                for (Iterator<Twitpl> tplIt=twit.getTwitpls().iterator(); tplIt.hasNext();) {
                    Twitpl twitpl=tplIt.next();
                    if (twitpl.getPlid()==pl.getPlid()){isTwitInPl=true;}
                }
                logger.debug("user.getScreenName()="+user.getScreenName()+" isTwitInPl"+isTwitInPl);
                //If a pl relationship doesn't yet exist
                if (!isTwitInPl){
                    //Create pl relationship
                    Twitpl twitpl = new Twitpl();
                    twitpl.setTwitid(twit.getTwitid());
                    twitpl.setPlid(pl.getPlid());
                    twit.getTwitpls().add(twitpl);
                    //Any edit to twit will force twitter api refresh
                    if (twit.getTwitid()>0){
                        HibernateUtil.getSession().createQuery("delete Mention m where m.twitidceleb='"+twit.getTwitid()+"'").executeUpdate();
                        HibernateUtil.getSession().createQuery("delete Twitpost t where t.twitid='"+twit.getTwitid()+"'").executeUpdate();
                    }
                    //If it's being processed by GetTwitterPosts we need to note that it's been edited
                    if (GetTwitterPosts.isProcessing(twit.getTwitid())){
                        GetTwitterPosts.addToEditedDuringProcessing(twit.getTwitid());
                    }
                    //Set since_id to 1 so twitter api refreshes
                    twit.setSince_id("1");
                    //Refresh the twit to pick up the Twitpl changes
                    twit.save();
                    //Flush the right col list cache
                    DbcacheexpirableCache.flush("PublicRightcolListCelebs.java");
                }
            }
        }  catch (Exception ex){
            logger.error("", ex);
        }
    }




}