package com.celebtwit.embed;

import com.celebtwit.cache.html.DbcacheexpirableCache;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.helpers.CountUniqueCelebsWhoMentioned;
import com.celebtwit.htmluibeans.PublicTwitterWhoPanel;
import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Apr 22, 2009
 * Time: 8:55:25 AM
 */
public class JsDifferentCelebs {


    public static String get(Twit twit, String twitterusername, Pl pl){
        Logger logger = Logger.getLogger(PublicTwitterWhoPanel.class);
        String out = "";
        int twitid = 0;
        if (twit!=null){twitid=twit.getTwitid();}
        String key = "JsDifferentCelebs.java-twitid-"+twitid+"-twitterusername-"+twitterusername;
        String group = "JsDifferentCelebs.java-plid-"+pl.getPlid();
        Object fromCache = DbcacheexpirableCache.get(key, group);
        if (fromCache!=null){
            try{out = (String)fromCache;}catch(Exception ex){logger.error("", ex);}
        } else {
            out = generate(twit, twitterusername, pl);
            DbcacheexpirableCache.put(key, group, out, DbcacheexpirableCache.expireIn6Hrs());
        }
        return out;
    }

    private static String generate(Twit twit, String twitterusername, Pl pl){
        int uniqueCelebsWhoMentioned = 0;
        if (twit!=null){
            twitterusername = twit.getTwitterusername();
            uniqueCelebsWhoMentioned = CountUniqueCelebsWhoMentioned.getAllTime(twit, pl.getPlid());
        }

        StringBuffer o = new StringBuffer();
        o.append("<div style=\"width:150px; background-color:#ffffff; overflow:hidden; text-align:center;  padding: 5px; border: 2px #cccccc solid;\">");
        o.append("<center>");
        o.append("<a href=\"http://"+pl.getCustomdomain1()+"/twitter/"+twitterusername+"/\">");
        if (twit!=null && twit.getIsceleb()){
            o.append("<font style=\"font-size:11px; font-weight:bold;\">@"+twit.getRealname()+"</font>");
        } else {
            o.append("<font style=\"font-size:11px; font-weight:bold;\">@"+twitterusername+"</font>");
        }
        o.append("</a>");
        o.append("<br>");
        o.append("<font style=\"font-size:10px; color:#aaaaaa;\">has been tweeted by</font>");
        o.append("<br>");
        o.append("<font style=\"font-size:30px; color:#aaaaaa;\">"+uniqueCelebsWhoMentioned+"</font>");
        o.append("<br>");
        o.append("<a href=\"http://"+pl.getCustomdomain1()+"/twitter/"+twitterusername+"/\"><font style=\"font-size:10px;\">different "+pl.getCelebiscalled()+"s!</font></a>");
        o.append("</center>");
        o.append("</div>");

        return o.toString();
    }
}
