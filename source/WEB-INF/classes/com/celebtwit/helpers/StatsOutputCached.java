package com.celebtwit.helpers;

import com.celebtwit.cachedstuff.*;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: Feb 4, 2010
 * Time: 1:28:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatsOutputCached {

    //These are methods for overall stats for the whole private label

    public static String nonCelebsTweetedMostByCelebs(Pl pl, String requestParamTime, int numbertoget, boolean forceRefresh, boolean includerankingnumbers){
        Logger logger = Logger.getLogger(StatsOutputCached.class);
        CachedStuff cs = new NonCelebsTweetedMostByCelebs(requestParamTime, numbertoget, forceRefresh, includerankingnumbers);
        NonCelebsTweetedMostByCelebs obj = (NonCelebsTweetedMostByCelebs) GetCachedStuff.get(cs, pl);
        return obj.getHtml();
//        String out = "";
//        String key = "nonCelebsTweetedMostByCelebs()-"+requestParamTime+"-numbertoget-"+numbertoget+"-includerankingnumbers-"+includerankingnumbers;
//        String group = "StatsOutputCached-plid-"+pl.getPlid();
//        Object fromCache = DbcacheexpirableCache.get(key, group);
//        if (fromCache!=null && !forceRefresh){
//            try{out = (String)fromCache;}catch(Exception ex){logger.error("", ex);}
//        } else {
//            out = StatsOutput.nonCelebsTweetedMostByCelebs(pl, requestParamTime, numbertoget, includerankingnumbers);
//            DbcacheexpirableCache.put(key, group, out, DbcacheexpirableCache.expireIn3Hrs());
//        }
//        return out;
    }

    public static String celebsTweetedMostByCelebs(Pl pl, String requestParamTime, int numbertoget, boolean forceRefresh, boolean includerankingnumbers){
        Logger logger = Logger.getLogger(StatsOutputCached.class);
        CachedStuff cs = new CelebsTweetedMostByCelebs(requestParamTime, numbertoget, forceRefresh, includerankingnumbers);
        CelebsTweetedMostByCelebs obj = (CelebsTweetedMostByCelebs) GetCachedStuff.get(cs, pl);
        return obj.getHtml();
//        String out = "";
//        String key = "celebsTweetedMostByCelebs()-"+requestParamTime+"-numbertoget-"+numbertoget+"-includerankingnumbers-"+includerankingnumbers;
//        String group = "StatsOutputCached-plid-"+pl.getPlid();
//        Object fromCache = DbcacheexpirableCache.get(key, group);
//        if (fromCache!=null && !forceRefresh){
//            try{out = (String)fromCache;}catch(Exception ex){logger.error("", ex);}
//        } else {
//            out = StatsOutput.celebsTweetedMostByCelebs(pl, requestParamTime, numbertoget, includerankingnumbers);
//            DbcacheexpirableCache.put(key, group, out, DbcacheexpirableCache.expireIn3Hrs());
//        }
//        return out;
    }

    public static String nonCelebsTweetedByMostDifferentCelebs(Pl pl, String requestParamTime, int numbertoget, boolean forceRefresh, boolean includerankingnumbers){
        Logger logger = Logger.getLogger(StatsOutputCached.class);
        CachedStuff cs = new NonCelebsTweetedByMostDifferentCelebs(requestParamTime, numbertoget, forceRefresh, includerankingnumbers);
        NonCelebsTweetedByMostDifferentCelebs obj = (NonCelebsTweetedByMostDifferentCelebs) GetCachedStuff.get(cs, pl);
        return obj.getHtml();
//        String out = "";
//        String key = "nonCelebsTweetedByMostDifferentCelebs()-"+requestParamTime+"-numbertoget-"+numbertoget+"-includerankingnumbers-"+includerankingnumbers;
//        String group = "StatsOutputCached-plid-"+pl.getPlid();
//        Object fromCache = DbcacheexpirableCache.get(key, group);
//        if (fromCache!=null && !forceRefresh){
//            try{out = (String)fromCache;}catch(Exception ex){logger.error("", ex);}
//        } else {
//            out = StatsOutput.nonCelebsTweetedByMostDifferentCelebs(pl, requestParamTime, numbertoget, includerankingnumbers);
//            DbcacheexpirableCache.put(key, group, out, DbcacheexpirableCache.expireIn3Hrs());
//        }
//        return out;
    }


    public static String celebsTweetedByMostDifferentCelebs(Pl pl, String requestParamTime, int numbertoget, boolean forceRefresh, boolean includerankingnumbers){
        Logger logger = Logger.getLogger(StatsOutputCached.class);
        CachedStuff cs = new CelebsTweetedByMostDifferentCelebs(requestParamTime, numbertoget, forceRefresh, includerankingnumbers);
        CelebsTweetedByMostDifferentCelebs obj = (CelebsTweetedByMostDifferentCelebs) GetCachedStuff.get(cs, pl);
        return obj.getHtml();
//        String out = "";
//        String key = "celebsTweetedByMostDifferentCelebs()-"+requestParamTime+"-numbertoget-"+numbertoget+"-includerankingnumbers-"+includerankingnumbers;
//        String group = "StatsOutputCached-plid-"+pl.getPlid();
//        Object fromCache = DbcacheexpirableCache.get(key, group);
//        if (fromCache!=null && !forceRefresh){
//            try{out = (String)fromCache;}catch(Exception ex){logger.error("", ex);}
//        } else {
//            out = StatsOutput.celebsTweetedByMostDifferentCelebs(pl, requestParamTime, numbertoget, includerankingnumbers);
//            DbcacheexpirableCache.put(key, group, out, DbcacheexpirableCache.expireIn3Hrs());
//        }
//        return out;
    }



    //Below this line are methods for individual tweeps



    public static String nonCelebsTweetedMostByTwit(Twit twit, String twitterusername, Pl pl, String requestParamTime, int numbertoget, boolean forceRefresh, boolean includerankingnumbers){
        Logger logger = Logger.getLogger(StatsOutputCached.class);
        CachedStuff cs = new NonCelebsTweetedMostByTwit(twit, twitterusername, requestParamTime, numbertoget, includerankingnumbers);
        NonCelebsTweetedMostByTwit obj = (NonCelebsTweetedMostByTwit) GetCachedStuff.get(cs, pl);
        return obj.getHtml();
//        String out = "";
//        String key = "nonCelebsTweetedMostByTwit()-"+requestParamTime+"-numbertoget-"+numbertoget+"-twit-"+twit.getTwitid()+"-twitterusername-"+twitterusername+"-includerankingnumbers-"+includerankingnumbers;
//        String group = "StatsOutputCached-plid-"+pl.getPlid();
//        Object fromCache = DbcacheexpirableCache.get(key, group);
//        if (fromCache!=null && !forceRefresh){
//            try{out = (String)fromCache;}catch(Exception ex){logger.error("", ex);}
//        } else {
//            out = StatsOutput.nonCelebsTweetedMostByTwit(twit, twitterusername, pl, requestParamTime, numbertoget, includerankingnumbers);
//            DbcacheexpirableCache.put(key, group, out, DbcacheexpirableCache.expireIn3Hrs());
//        }
//        return out;
    }

    public static String celebsTweetedMostByTwit(Twit twit, String twitterusername, Pl pl, String requestParamTime, int numbertoget, boolean forceRefresh, boolean includerankingnumbers){
        Logger logger = Logger.getLogger(StatsOutputCached.class);
        CachedStuff cs = new CelebsTweetedMostByTwit(twit, twitterusername, requestParamTime, numbertoget, includerankingnumbers);
        CelebsTweetedMostByTwit obj = (CelebsTweetedMostByTwit) GetCachedStuff.get(cs, pl);
        return obj.getHtml();
//        String out = "";
//        String key = "celebsTweetedMostByTwit()-"+requestParamTime+"-numbertoget-"+numbertoget+"-twit-"+twit.getTwitid()+"-twitterusername-"+twitterusername+"-includerankingnumbers-"+includerankingnumbers;
//        String group = "StatsOutputCached-plid-"+pl.getPlid();
//        Object fromCache = DbcacheexpirableCache.get(key, group);
//        if (fromCache!=null && !forceRefresh){
//            try{out = (String)fromCache;}catch(Exception ex){logger.error("", ex);}
//        } else {
//            out = StatsOutput.celebsTweetedMostByTwit(twit, twitterusername, pl, requestParamTime, numbertoget, includerankingnumbers);
//            DbcacheexpirableCache.put(key, group, out, DbcacheexpirableCache.expireIn3Hrs());
//        }
//        return out;
    }


    public static String celebsWhoTweetedTwit(Twit twit, String twitterusername, Pl pl, String requestParamTime, int numbertoget, boolean forceRefresh, boolean includerankingnumbers){
        Logger logger = Logger.getLogger(StatsOutputCached.class);
        CachedStuff cs = new CelebsWhoTweetedTwit(twit, twitterusername, requestParamTime, numbertoget, includerankingnumbers);
        CelebsWhoTweetedTwit obj = (CelebsWhoTweetedTwit) GetCachedStuff.get(cs, pl);
        return obj.getHtml();
//        String out = "";
//        String key = "celebsWhoTweetedTwit()-"+requestParamTime+"-numbertoget-"+numbertoget+"-twit-"+twit.getTwitid()+"-twitterusername-"+twitterusername+"-includerankingnumbers-"+includerankingnumbers;
//        String group = "StatsOutputCached-plid-"+pl.getPlid();
//        Object fromCache = DbcacheexpirableCache.get(key, group);
//        if (fromCache!=null && !forceRefresh){
//            try{out = (String)fromCache;}catch(Exception ex){logger.error("", ex);}
//        } else {
//            out = StatsOutput.celebsWhoTweetedTwit(twit, twitterusername, pl, requestParamTime, numbertoget, includerankingnumbers);
//            DbcacheexpirableCache.put(key, group, out, DbcacheexpirableCache.expireIn3Hrs());
//        }
//        return out;
    }


}