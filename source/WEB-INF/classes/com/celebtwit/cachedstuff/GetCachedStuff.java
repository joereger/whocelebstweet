package com.celebtwit.cachedstuff;

import com.celebtwit.cache.html.DbcacheexpirableCache;
import com.celebtwit.dao.Pl;
import com.celebtwit.htmlui.Pagez;
import com.celebtwit.util.DateDiff;
import com.celebtwit.util.Time;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;

/**
 * User: Joe Reger Jr
 * Date: Jan 29, 2008
 * Time: 1:57:05 PM
 */
public class GetCachedStuff {
    

    public static CachedStuff get(CachedStuff cs, Pl pl){
        Logger logger = Logger.getLogger(CachedStuff.class);
        try{
            String key = cs.getKey();
            String group = "CachedStuff-plid="+pl.getPlid();
            logger.debug("get(key="+key+" group="+group+")");
            //Note third argument which tells cache to return object instead of null even if expired
            Object obj = DbcacheexpirableCache.get(key, group, false);
            if (obj!=null && (obj instanceof CachedStuff) && !requestSaysForceRefresh()){
                CachedStuff cachedCs = (CachedStuff)obj;
                int minago = DateDiff.dateDiff("minute", Calendar.getInstance(), cachedCs.refreshedTimestamp());
                logger.debug("get() not null minago="+minago+" cs.maxAgeInMinutes()="+cs.maxAgeInMinutes());
                if (minago>cs.maxAgeInMinutes()){
                    //Kick off the background thread to refresh
                    logger.debug("get() minago>cs.maxAgeInMinutes() so kicking off thread to refresh and returning immediately");
                    GetCachedStuffRefreshThread gcsrt = new GetCachedStuffRefreshThread(cs, pl, key, group);
                    gcsrt.startThread();
                }
                //Immediately return most recently refreshed
                logger.debug("get() returning cachedCs");
                //CacheLogger.log("CachedStuff", group, key, "immediately returning most recent", true);
                return cachedCs;
            } else {
                logger.debug("get() obj=null so refreshing before returning");
                cs.refresh(pl);
                logger.debug("get() done refreshing");
                Date expirationdate = Time.xMinutesAgoEnd(Calendar.getInstance(), (-1)*cs.maxAgeInMinutes()).getTime();
                DbcacheexpirableCache.put(key, group, cs, expirationdate);
                logger.debug("get() returning cs (new/refreshed)");
                //CacheLogger.log("CachedStuff", group, key, "returning refreshed", true);
                return cs;
            }
        } catch (Exception ex){
            logger.error("", ex);
        }
        //CacheLogger.log("CachedStuff", group, key, "returning from bottom", true);
        return cs;
    }

    private static boolean requestSaysForceRefresh(){
        Logger logger = Logger.getLogger(CachedStuff.class);
        try{
            if (Pagez.getRequest()!=null){
                if (Pagez.getRequest().getParameter("refresh")!=null){
                    if (Pagez.getRequest().getParameter("refresh").equals("1") || Pagez.getRequest().getParameter("refresh").equals("true")){
                        return true;
                    }
                }
            }
        } catch (Exception ex){
            logger.debug("", ex);
        }
        return false;
    }


    

}
