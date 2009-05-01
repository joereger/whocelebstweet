package com.celebtwit.embed;

import com.celebtwit.dao.Twit;
import com.celebtwit.dao.Pl;
import com.celebtwit.systemprops.SystemProperty;
import com.celebtwit.helpers.CountUniqueCelebsWhoMentioned;
import com.celebtwit.helpers.CountMentionsByCelebs;
import com.celebtwit.htmluibeans.PublicTwitterWhoPanel;
import com.celebtwit.cache.html.DbcacheexpirableCache;
import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Apr 22, 2009
 * Time: 8:55:25 AM
 */
public class JsCelebMentions {

    public static String get(Twit twit, String twitterusername, Pl pl){
        Logger logger = Logger.getLogger(PublicTwitterWhoPanel.class);
        String out = "";
        int twitid = 0;
        if (twit!=null){twitid=twit.getTwitid();}
        String key = "JsCelebMentions.java-twitid-"+twitid+"-twitterusername-"+twitterusername;
        String group = "JsCelebMentions.java-plid-"+pl.getPlid();
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
        int mentionsByCelebs = 0;
        if (twit!=null){
            twitterusername = twit.getTwitterusername();
            mentionsByCelebs = CountMentionsByCelebs.getAllTime(twit, pl.getPlid());
        }

        StringBuffer o = new StringBuffer();
        o.append("<div style=\"width:150px; background-color:#cccccc; overflow:hidden; text-align:center;  padding: 5px;  border: 2px #999999 solid;\">");
        o.append("<center>");
        o.append("<a href=\"http://"+pl.getCustomdomain1()+"/twitter/"+twitterusername+"/\">");
        if (twit!=null && twit.getIsceleb()){
            o.append("<font style=\"font-size:13px; font-weight:bold;\">@"+twit.getRealname()+"</font>");
        } else {
            o.append("<font style=\"font-size:13px; font-weight:bold;\">@"+twitterusername+"</font>");
        }
        o.append("</a>");
        o.append("<br>");
        o.append("<font style=\"font-size:13px;\">has been tweeted</font>");
        o.append("<br>");
        o.append("<font style=\"font-size:35px;\">"+mentionsByCelebs+"</font>");
        o.append("<br>");
        o.append("<a href=\"http://"+pl.getCustomdomain1()+"/twitter/"+twitterusername+"/\"><font style=\"font-size:13px;\">times by "+pl.getCelebiscalled()+"s!</font></a>");
        o.append("</center>");
        o.append("</div>");

        return o.toString();
    }
}