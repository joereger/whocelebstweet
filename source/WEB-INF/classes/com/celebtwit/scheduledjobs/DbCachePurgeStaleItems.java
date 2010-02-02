package com.celebtwit.scheduledjobs;

import com.celebtwit.cache.html.DbcacheexpirableCache;
import com.celebtwit.systemprops.InstanceProperties;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * User: Joe Reger Jr
 * Date: Jul 19, 2006
 * Time: 2:22:28 PM
 */
public class DbCachePurgeStaleItems implements Job {


    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        if (InstanceProperties.getRunScheduledTasksOnThisInstance()){
            logger.debug("execute() DbCachePurgeStaleItems called");
            try{
                DbcacheexpirableCache.purgeStaleItems();
            } catch (Exception ex){
                logger.error("", ex);
            }
        } else {
            logger.debug("InstanceProperties.getRunScheduledTasksOnThisInstance() is FALSE for this instance so this task is not being executed.");
        }
    }

}