package com.celebtwit.scheduledjobs;

import com.celebtwit.dao.Keyword;
import com.celebtwit.dao.Keywordtwit;
import com.celebtwit.dao.Twitpost;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.dao.hibernate.NumFromUniqueResult;
import com.celebtwit.systemprops.InstanceProperties;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
            HashMap<Integer, Integer> twitidNumberoftwitposts = new HashMap<Integer, Integer>();
            //Note that this is a native sql query
            //This query requires a FULLTEXT index in MySQL on the post column of twitpost
            List twitposts = HibernateUtil.getSession().createSQLQuery("SELECT * FROM twitpost WHERE MATCH(post) AGAINST('"+keyword.getKeyword()+"') AND twitpostid>"+keyword.getSincetwitpostid()+" AND twitpostid<"+maxTwitpostid+""+emptyStr).addEntity(Twitpost.class).list();
            for (Iterator iterator=twitposts.iterator(); iterator.hasNext();) {
                Twitpost twitpost = (Twitpost)iterator.next();
                logger.debug("found twitpostid="+twitpost.getTwitpostid()+" which uses '"+keyword.getKeyword()+"' post="+twitpost.getPost());
                twitidNumberoftwitposts = addAMention(twitidNumberoftwitposts, twitpost.getTwitid());
            }
            //Save Keywordtwit records
            saveAllKeywordTwits(twitidNumberoftwitposts, keyword);
            //Save highest twitpostid processed
            keyword.setSincetwitpostid(maxTwitpostid);
            keyword.save();
        } catch (Exception ex){
            logger.error("", ex);
        }
    }


    private HashMap<Integer, Integer> addAMention(HashMap<Integer, Integer> in, int twitid){
        int numberoftwitposts = 0;
        if (in.containsKey(twitid)){
            numberoftwitposts = (Integer)in.get(twitid);
        }
        in.put(twitid, numberoftwitposts+1);
        return in;
    }

    private void saveAllKeywordTwits(HashMap<Integer, Integer> in, Keyword keyword){
        Iterator keyValuePairs = in.entrySet().iterator();
        for (int i = 0; i < in.size(); i++){
            Map.Entry mapentry = (Map.Entry) keyValuePairs.next();
            int key = (Integer)mapentry.getKey();
            int value = (Integer)mapentry.getValue();
            saveSingleKeywordTwit(key, value, keyword);
        }
    }

    private void saveSingleKeywordTwit(int twitid, int numberofNewTwitposts, Keyword keyword){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            //Set up an empty Keywordtwit object
            Keywordtwit keywordtwit = new Keywordtwit();
            keywordtwit.setKeywordid(keyword.getKeywordid());
            keywordtwit.setTwitid(twitid);
            keywordtwit.setNumberoftwitposts(0);
            //See if another one already exists and if so use it
            List keywordtwits = HibernateUtil.getSession().createQuery("FROM Keywordtwit WHERE keywordid='"+keyword.getKeywordid()+"' AND twitid='"+twitid+"'").list();
            for (Iterator iterator=keywordtwits.iterator(); iterator.hasNext();) {
                Keywordtwit kwt = (Keywordtwit)iterator.next();
                keywordtwit = kwt;
            }
            //Add numberofNewTwitposts
            keywordtwit.setNumberoftwitposts(keywordtwit.getNumberoftwitposts() + numberofNewTwitposts);
            //Save it
            keywordtwit.save();
        } catch (Exception ex){
            logger.error("", ex);
        }
    }





}