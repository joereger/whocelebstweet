package com.celebtwit.cachedstuff;

import com.celebtwit.ads.AssignAdNetwork;
import com.celebtwit.cache.html.DbcacheexpirableCache;
import com.celebtwit.dao.Pl;
import com.celebtwit.htmlui.Pagez;
import com.celebtwit.htmlui.UserSession;
import com.celebtwit.threadpool.ThreadPool;
import com.celebtwit.util.Time;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: Mar 20, 2010
 * Time: 10:20:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetCachedStuffRefreshThread  implements Runnable, Serializable  {

    private CachedStuff cs;
    private Pl pl;
    private String key;
    private String group;
    private static ThreadPool tp;

    public GetCachedStuffRefreshThread(CachedStuff cs, Pl pl, String key, String group){
        this.cs = cs;
        this.pl = pl;
        this.key = key;
        this.group = group;
    }

    public void run(){
        Logger logger = Logger.getLogger(CachedStuff.class);
        logger.debug("run() called for key="+key+" group="+group);
        try{
            //Important to make sure Pagez.userSession is set with the pl because running refresh this way means that there's no threadlocal with usersession already set up
            if (Pagez.getUserSession()==null){
                logger.debug("Pagez.getUserSession()==null so creating one");
                UserSession userSession = new UserSession();
                userSession.setPl(pl);
                Pagez.setUserSession(userSession);
                AssignAdNetwork.assign(Pagez.getRequest());
            }
            //Call the refresh
            cs.refresh(pl);
            Date expirationdate = Time.xMinutesAgoEnd(Calendar.getInstance(), (-1)*cs.maxAgeInMinutes()).getTime();
            DbcacheexpirableCache.put(key, group, cs, expirationdate);
        } catch (Exception ex){
            logger.error("", ex);
        }
    }

    public void startThread(){
        if (tp==null){tp = new ThreadPool(15);}
        tp.assign(this);
    }




}
