package com.celebtwit.htmluibeans;

import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twitpost;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.cache.html.DbcacheexpirableCache;
import com.celebtwit.helpers.*;
import com.celebtwit.htmlui.Pagez;
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
public class PublicIndexTweetlist {


    public static String getHtml(Pl pl, int page){
        Logger logger = Logger.getLogger(PublicTwitterWhoPanel.class);
        String out = "";
        String key = "plid-"+pl.getPlid()+"-page-"+page;
        String group = "PublicIndexTweetlist.java";
        Object fromCache = DbcacheexpirableCache.get(key, group);
        if (fromCache!=null){
            try{out = (String)fromCache;}catch(Exception ex){logger.error("", ex);}
        } else {
            out = generateHtml(pl, page);
            DbcacheexpirableCache.put(key, group, out, DbcacheexpirableCache.expireIn5Min());
        }
        return out;
    }

    private static String generateHtml(Pl pl, int page){
        StringBuffer out = new StringBuffer();
        int perPage = 25;
        int firstResult = page * perPage;
        if (page<=1){ firstResult = 0; }
        List<Twitpost> twitposts = HibernateUtil.getSession().createCriteria(Twitpost.class)
            .addOrder(Order.desc("created_at"))
            .createCriteria("twit")
            .createCriteria("twitpls")
            .add(Restrictions.eq("plid", pl.getPlid()))
            .setMaxResults(perPage)
            .setFirstResult(firstResult)
            .setCacheable(true)
            .list();
        for (Iterator<Twitpost> tpIt=twitposts.iterator(); tpIt.hasNext();) {
            Twitpost twitpost=tpIt.next();
            out.append(TwitpostAsHtml.get(twitpost, 400));
        }


        return out.toString();
    }


}
