package com.celebtwit.embed;

import com.celebtwit.dao.Twit;
import com.celebtwit.systemprops.SystemProperty;
import com.celebtwit.helpers.CountUniqueCelebsWhoMentioned;
import com.celebtwit.helpers.CountMentionsByCelebs;

/**
 * User: Joe Reger Jr
 * Date: Apr 22, 2009
 * Time: 8:55:25 AM
 */
public class JsCelebMentions {

    public static String get(Twit twit, String twitterusername){
        int mentionsByCelebs = 0;
        if (twit!=null){
            twitterusername = twit.getTwitterusername();
            mentionsByCelebs = CountMentionsByCelebs.getAllTime(twit);
        }

        StringBuffer o = new StringBuffer();
        o.append("<div style=\"width:175px; background-color:#cccccc; overflow:hidden; text-align:center;  padding: 5px;  border: 2px #999999 solid;\">");
        o.append("<center>");
        o.append("<a href=\"http://"+SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/twitter/"+twitterusername+"/\">");
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
        o.append("<a href=\"http://"+SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/twitter/"+twitterusername+"/\"><font style=\"font-size:13px;\">times by celebs!</font></a>");
        o.append("</center>");
        o.append("</div>");

        return o.toString();
    }
}