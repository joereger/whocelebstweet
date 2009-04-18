package com.celebtwit.scheduledjobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.log4j.Logger;
import com.celebtwit.systemprops.InstanceProperties;
import com.celebtwit.dao.Userpersistentlogin;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.dao.hibernate.NumFromUniqueResult;


import java.util.*;

/**
 * User: Joe Reger Jr
 * Date: Jul 19, 2006
 * Time: 2:22:28 PM
 */
public class SystemStats implements Job {



    //BE SURE TO SYNC CODE HERE WITH MAIN SystemStats in beans 
    private static int totalusers=0;






    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        //if (InstanceProperties.getRunScheduledTasksOnThisInstance()){
            logger.debug("execute() SystemStats called");

            totalusers = NumFromUniqueResult.getInt("select count(*) from User");






     

        //} else {
            //logger.debug("InstanceProperties.getRunScheduledTasksOnThisInstance() is FALSE for this instance so this task is not being executed.");
        //}
    }




   



    
    


    public static int getTotalusers() {
        return totalusers;
    }

    public static void setTotalusers(int totalusers) {
        //SystemStats.totalusers=totalusers;
    }
}
