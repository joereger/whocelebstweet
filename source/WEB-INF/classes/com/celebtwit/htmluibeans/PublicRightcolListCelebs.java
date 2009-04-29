package com.celebtwit.htmluibeans;

import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twitpost;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.cache.html.DbcacheexpirableCache;
import com.celebtwit.helpers.TwitpostAsHtml;
import com.celebtwit.htmlui.Pagez;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Iterator;

/**
 * User: Joe Reger Jr
 * Date: Apr 29, 2009
 * Time: 4:53:15 PM
 */
public class PublicRightcolListCelebs {


    public static String getHtml(Pl pl){
        Logger logger = Logger.getLogger(PublicTwitterWhoPanel.class);
        String out = "";
        String key = "plid-"+pl.getPlid();
        String group = "PublicRightcolListCelebs.java";
        Object fromCache = DbcacheexpirableCache.get(key, group);
        if (fromCache!=null){
            try{out = (String)fromCache;}catch(Exception ex){logger.error("", ex);}
        } else {
            out = generateHtml(pl);
            DbcacheexpirableCache.put(key, group, out, DbcacheexpirableCache.expireIn24Hrs());
        }
        return out;
    }

    private static String generateHtml(Pl pl){
        StringBuffer out = new StringBuffer();
        List<Twit> celebs = HibernateUtil.getSession().createCriteria(Twit.class)
                                       .add(Restrictions.eq("isceleb", true))
                                       .addOrder(Order.asc("realname"))
                                       .createCriteria("twitpls")
                                       .add(Restrictions.eq("plid", pl.getPlid()))
                                       .setMaxResults(1000)
                                       .setCacheable(true)
                                       .list();
        for (Iterator<Twit> iterator=celebs.iterator(); iterator.hasNext();) {
            Twit twitFooter=iterator.next();
            out.append("<a href=\"/twitter/"+twitFooter.getTwitterusername()+"/\"><font class=\"normalfont\" style=\"font-weight:bold; color:#000000;\">@"+twitFooter.getRealname()+"</font></a><br/>");
        }
        return out.toString();
    }




}
