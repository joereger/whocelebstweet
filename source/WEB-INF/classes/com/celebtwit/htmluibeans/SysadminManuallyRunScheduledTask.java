package com.celebtwit.htmluibeans;

import com.celebtwit.scheduledjobs.*;
import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 * User: Joe Reger Jr
 * Date: Sep 20, 2006
 * Time: 8:42:14 AM
 */
public class SysadminManuallyRunScheduledTask implements Serializable {

    public SysadminManuallyRunScheduledTask(){}

    public void initBean(){
        
    }





   








    public String runDeleteOldPersistentlogins(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{DeleteOldPersistentlogins task = new DeleteOldPersistentlogins();
            task.execute(null);} catch (Exception ex){logger.error("",ex);}
        return "sysadminmanuallyrunscheduledtask";
    }




    
    public String runSystemStats(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{com.celebtwit.scheduledjobs.SystemStats task = new com.celebtwit.scheduledjobs.SystemStats();
            task.execute(null);} catch (Exception ex){logger.error("",ex);}
        return "sysadminmanuallyrunscheduledtask";
    }










    public String runPagePerformanceRecordAndFlush(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{PagePerformanceRecordAndFlush task = new com.celebtwit.scheduledjobs.PagePerformanceRecordAndFlush();
            task.execute(null);} catch (Exception ex){logger.error("",ex);}
        return "sysadminmanuallyrunscheduledtask";
    }

}
