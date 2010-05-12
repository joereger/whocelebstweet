package com.celebtwit.scheduledjobs;

import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.Twitpost;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.helpers.TwitPlHelper;
import com.celebtwit.systemprops.InstanceProperties;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: Joe Reger Jr
 * Date: Jul 19, 2006
 * Time: 2:22:28 PM
 */
public class FixTwitpostsWithNoPl implements Job {


    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (InstanceProperties.getRunScheduledTasksOnThisInstance()){
            logger.debug("execute() FixTwitpostsWithNoPl called");
            try{
                fixIt();
            } catch (Exception ex){
                logger.error("", ex);
            }
        } else {
            logger.debug("InstanceProperties.getRunScheduledTasksOnThisInstance() is FALSE for this instance so this task is not being executed.");
        }
    }

    public static void fixIt(){
        Logger logger = Logger.getLogger(FixTwitpostsWithNoPl.class);
        int count = 0;
        List<Twitpost> twitposts = HibernateUtil.getSession().createCriteria(Twitpost.class)
                                               .add(Restrictions.eq("pl01", 0))
                                               .add(Restrictions.eq("pl02", 0))
                                               .add(Restrictions.eq("pl03", 0))
                                               .add(Restrictions.eq("pl04", 0))
                                               .add(Restrictions.eq("pl05", 0))
                                               .addOrder(Order.asc("twitpostid"))
                                               .setMaxResults(25000)
                                               .setCacheable(true)
                                               .list();
        for (Iterator<Twitpost> it=twitposts.iterator(); it.hasNext();) {
            Twitpost twitpost = it.next();
            count++;
            try{
                logger.debug("twitpostid="+twitpost.getTwitpostid()+" ("+count+"/"+twitposts.size()+" for this run)");
                Twit twit = Twit.get(twitpost.getTwitid());
                //Put this twitpost into proper plids
                ArrayList<Integer> plsTwitIsIn = TwitPlHelper.getPlidsTwitIsIn(twit);
                for (Iterator iterator = plsTwitIsIn.iterator(); iterator.hasNext();) {
                    Integer plid = (Integer) iterator.next();
                    Pl pl = Pl.get(plid);
                    twitpost = TwitPlHelper.addTwitpostToPlDontSave(twitpost, pl);
                }
                //Save it
                twitpost.save();
            } catch (Exception ex) {
                logger.error("", ex);
            }
        }

    }



}