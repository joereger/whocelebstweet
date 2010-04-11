package com.celebtwit.htmluibeans;

import com.celebtwit.cache.html.DbcacheexpirableCache;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.helpers.TwitPlHelper;
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
 * Time: 4:53:15 PM
 */
public class PublicRightcolListCelebs {


    public static String getHtml(Pl pl){
        Logger logger = Logger.getLogger(PublicTwitterWhoPanel.class);
        String out = "";
        String key = "plid-"+pl.getPlid()+"-adnetworkname-"+Pagez.getUserSession().getAdNetworkName();
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

    public static String generateHtml(Pl pl){
        StringBuffer out = new StringBuffer();
        //TwitplQuery
        ArrayList<Integer> plidList = new ArrayList<Integer>();
        plidList.add(pl.getPlid());
        List<Twit> celebs = HibernateUtil.getSession().createCriteria(Twit.class)
                                       .add(Restrictions.eq("isceleb", true))
                                       .addOrder(Order.asc("realname"))
                                       .add(TwitPlHelper.getCrit(plidList))
                                       .setMaxResults(5000)
                                       .setCacheable(true)
                                       .list();
        for (Iterator<Twit> iterator=celebs.iterator(); iterator.hasNext();) {
            Twit twitFooter=iterator.next();
            out.append("<a href=\"/twitter/"+twitFooter.getTwitterusername()+"/\"><font class=\"normalfont\" style=\"font-weight:bold; color:#000000;\">@"+twitFooter.getRealname()+"</font></a><br/>");
        }
        return out.toString();
    }




}
