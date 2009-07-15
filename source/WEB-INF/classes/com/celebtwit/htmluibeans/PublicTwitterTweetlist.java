package com.celebtwit.htmluibeans;

import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twitpost;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.cache.html.DbcacheexpirableCache;
import com.celebtwit.helpers.*;
import com.celebtwit.htmlui.Pagez;
import com.celebtwit.util.Num;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: Joe Reger Jr
 * Date: Apr 29, 2009
 * Time: 4:44:52 PM
 */
public class PublicTwitterTweetlist {

    public static String getHtml(Twit twit, int page){
        return getHtml(twit, page, false);
    }

    public static String getHtml(Twit twit, int page, String refreshRequestParam){
        boolean forceRefresh = false;
        if (refreshRequestParam!=null && (refreshRequestParam.equals("true") || refreshRequestParam.equals("1"))){
            forceRefresh = true;
        }
        return getHtml(twit, page, forceRefresh);
    }

    public static String getHtml(Twit twit, int page, boolean forceRefresh){
        Logger logger = Logger.getLogger(PublicTwitterWhoPanel.class);
        String out = "";
        String key = "page-"+page;
        String group = "PublicTwitterTweetlist.java-twitid-"+twit.getTwitid();
        Object fromCache = DbcacheexpirableCache.get(key, group);
        if (fromCache!=null && !forceRefresh){
            try{out = (String)fromCache;}catch(Exception ex){logger.error("", ex);}
        } else {
            out = generateHtml(twit, page);
            DbcacheexpirableCache.put(key, group, out, DbcacheexpirableCache.expireIn24Hrs());
        }
        return out;
    }

    private static String generateHtml(Twit twit, int page){
        StringBuffer out = new StringBuffer();
        int perPage = 10;
        int firstResult = page * perPage;
        if (page<=1){ firstResult = 0; }
        int insertAdCount = 0;
        int randomAdInsertionPoint = 2 + Num.randomInt(4);
        List<Twitpost> twitposts = HibernateUtil.getSession().createCriteria(Twitpost.class)
                                       .add(Restrictions.eq("twitid", twit.getTwitid()))
                                       .addOrder(Order.desc("created_at"))
                                       .setFirstResult(firstResult)
                                       .setMaxResults(perPage)
                                       .setCacheable(true)
                                       .list();
        for (Iterator<Twitpost> tpIt=twitposts.iterator(); tpIt.hasNext();) {
            Twitpost twitpost=tpIt.next();
            insertAdCount++;
            if (insertAdCount>=randomAdInsertionPoint){
                //insertAdCount = 0;
                //randomAdInsertionPoint = Num.randomInt(10);
                randomAdInsertionPoint = 10000;
                out.append(TwitpostAsHtml.getAdsenseAsTwitpost(380));
            }
            out.append(TwitpostAsHtml.get(twitpost, 380));
        }
        return out.toString();
    }


}