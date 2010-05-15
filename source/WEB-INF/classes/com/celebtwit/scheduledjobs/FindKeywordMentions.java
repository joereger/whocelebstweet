package com.celebtwit.scheduledjobs;

import com.celebtwit.dao.Keyword;
import com.celebtwit.dao.Keywordmention;
import com.celebtwit.dao.Twitpost;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.dao.hibernate.NumFromUniqueResult;
import com.celebtwit.systemprops.InstanceProperties;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Iterator;
import java.util.List;

/**
 * User: Joe Reger Jr
 * Date: Jul 19, 2006
 * Time: 2:22:28 PM
 */
public class FindKeywordMentions implements Job {


    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (InstanceProperties.getRunScheduledTasksOnThisInstance()){
            logger.debug("execute() FindKeywordMentions called");
            try{

                int maxTwitpostid = NumFromUniqueResult.getInt("select max(twitpostid) from Twitpost");

                List<Keyword> keywords = HibernateUtil.getSession().createCriteria(Keyword.class)
                                       .addOrder(Order.asc("keywordid"))
                                       .setCacheable(true)
                                       .list();
                for (Iterator<Keyword> iterator=keywords.iterator(); iterator.hasNext();) {
                    Keyword keyword=iterator.next();
                    processKeyword(keyword, maxTwitpostid);
                }


            } catch (Exception ex){
                logger.error("", ex);
            }
        } else {
            logger.debug("InstanceProperties.getRunScheduledTasksOnThisInstance() is FALSE for this instance so this task is not being executed.");
        }
    }

    private void processKeyword(Keyword keyword, int maxTwitpostid){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            String emptyStr = "";
            //Note that this is a native sql query
            //This query requires a FULLTEXT index in MySQL on the post column of twitpost
            List twitposts = HibernateUtil.getSession().createSQLQuery("SELECT * FROM twitpost WHERE MATCH(post) AGAINST('"+keyword.getKeyword()+"') AND twitpostid>"+keyword.getSincetwitpostid()+" AND twitpostid<"+maxTwitpostid+""+emptyStr).addEntity(Twitpost.class)
.list();
            for (Iterator iterator=twitposts.iterator(); iterator.hasNext();) {
                Twitpost twitpost = (Twitpost)iterator.next();
                logger.debug("found twitpostid="+twitpost.getTwitpostid()+" which uses '"+keyword.getKeyword()+"' post="+twitpost.getPost());
                processTwitpost(keyword, twitpost);
            }
            //Save highest twitpostid processed
            keyword.setSincetwitpostid(maxTwitpostid);
            keyword.save();
        } catch (Exception ex){
            logger.error("", ex);
        }
    }

    private void processTwitpost(Keyword keyword, Twitpost twitpost){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            int numAlreadyExisting =NumFromUniqueResult.getInt("select count(*) from Keywordmention where keywordid='"+keyword.getKeywordid()+"' AND twitid='"+twitpost.getTwitid()+"' AND twitpostid='"+twitpost.getTwitpostid()+"'");
            if (numAlreadyExisting==0){
                Keywordmention keywordmention = new Keywordmention();
                keywordmention.setKeywordid(keyword.getKeywordid());
                keywordmention.setTwitid(twitpost.getTwitid());
                keywordmention.setTwitpostid(twitpost.getTwitpostid());
                keywordmention.save();
            }
        } catch (Exception ex){
            logger.error("", ex);
        }
    }


}