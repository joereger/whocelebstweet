package com.celebtwit.scheduledjobs;

import com.celebtwit.dao.Mention;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.helpers.IsTwitACelebInThisPl;
import com.celebtwit.systemprops.InstanceProperties;
import com.celebtwit.util.Time;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * User: Joe Reger Jr
 * Date: Jul 19, 2006
 * Time: 2:22:28 PM
 */
public class FixIsmentionedaceleb implements Job {


    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (InstanceProperties.getRunScheduledTasksOnThisInstance()){
            logger.debug("execute() FixIsmentionedaceleb called");
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
        Logger logger = Logger.getLogger(FixIsmentionedaceleb.class);
        //ismentionedacelebs that haven't been verified in 6 mos are pulled 1000 at a time
        List<Mention> mentions = HibernateUtil.getSession().createCriteria(Mention.class)
                                               .add(Restrictions.eq("ismentionedaceleb", true))
                                               .add(Restrictions.lt("ismentionedacelebverifiedon", Time.xMonthsAgoStart(Calendar.getInstance(), 6).getTime()))
                                               .addOrder(Order.asc("mentionid"))
                                               .setMaxResults(1000)
                                               .setCacheable(true)
                                               .list();
        for (Iterator<Mention> it=mentions.iterator(); it.hasNext();) {
            Mention mention = it.next();
            try{
                Twit twit = Twit.get(mention.getTwitidmentioned());
                Pl pl = Pl.get(mention.getPlid());
                if (!IsTwitACelebInThisPl.isTwitACelebInThisPl(twit, pl)){
                    mention.setIsmentionedaceleb(false);
                }
                mention.setIsmentionedacelebverifiedon(new Date());
                mention.save();
            } catch (Exception ex) {
                logger.error("", ex);
            }
        }

    }

  

}