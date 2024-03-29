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

    public String runMakeFriends(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            MakeFriends task = new MakeFriends();
            task.execute(null);} catch (Exception ex){logger.error("",ex);}
        return "sysadminmanuallyrunscheduledtask";
    }

    public String runStatsTweet(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            StatsTweet task = new StatsTweet();
            task.execute(null);} catch (Exception ex){logger.error("",ex);}
        return "sysadminmanuallyrunscheduledtask";
    }

    public String runStatsTweetNonceleb(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            StatsTweetNonceleb task = new StatsTweetNonceleb();
            task.execute(null);} catch (Exception ex){logger.error("",ex);}
        return "sysadminmanuallyrunscheduledtask";
    }

    public String runGetTwitterPosts(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            GetTwitterPosts task = new GetTwitterPosts();
            task.execute(null);} catch (Exception ex){logger.error("",ex);}
        return "sysadminmanuallyrunscheduledtask";
    }

    public String runTwitterListSync(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            TwitterListSync task = new TwitterListSync();
            task.execute(null);} catch (Exception ex){logger.error("",ex);}
        return "sysadminmanuallyrunscheduledtask";
    }

    public String runFixIsmentionedaceleb(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            FixIsmentionedaceleb task = new FixIsmentionedaceleb();
            task.execute(null);} catch (Exception ex){logger.error("",ex);}
        return "sysadminmanuallyrunscheduledtask";
    }

    public String runDbCachePurgeStaleItems(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            DbCachePurgeStaleItems task = new DbCachePurgeStaleItems();
            task.execute(null);} catch (Exception ex){logger.error("",ex);}
        return "sysadminmanuallyrunscheduledtask";
    }
    
    public String runFixTwitpostsWithNoPl(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            FixTwitpostsWithNoPl task = new FixTwitpostsWithNoPl();
            task.execute(null);} catch (Exception ex){logger.error("",ex);}
        return "sysadminmanuallyrunscheduledtask";
    }

    public String runFindKeywordMentions(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            FindKeywordMentions task = new FindKeywordMentions();
            task.execute(null);} catch (Exception ex){logger.error("",ex);}
        return "sysadminmanuallyrunscheduledtask";
    }

}
