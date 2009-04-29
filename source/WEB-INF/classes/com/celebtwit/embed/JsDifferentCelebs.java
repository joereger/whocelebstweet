package com.celebtwit.embed;

import com.celebtwit.dao.Twit;
import com.celebtwit.systemprops.SystemProperty;
import com.celebtwit.helpers.CountUniqueCelebsWhoMentioned;
import com.celebtwit.htmlui.Pagez;

/**
 * User: Joe Reger Jr
 * Date: Apr 22, 2009
 * Time: 8:55:25 AM
 */
public class JsDifferentCelebs {

    public static String get(Twit twit, String twitterusername){
        int uniqueCelebsWhoMentioned = 0;
        if (twit!=null){
            twitterusername = twit.getTwitterusername();
            uniqueCelebsWhoMentioned = CountUniqueCelebsWhoMentioned.getAllTime(twit, Pagez.getUserSession().getPl().getPlid());
        }

        StringBuffer o = new StringBuffer();
        o.append("<div style=\"width:150px; background-color:#cccccc; overflow:hidden; text-align:center;  padding: 5px; border: 2px #999999 solid;\">");
        o.append("<center>");
        o.append("<a href=\"http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/\">");
        if (twit!=null && twit.getIsceleb()){
            o.append("<font style=\"font-size:13px; font-weight:bold;\">@"+twit.getRealname()+"</font>");
        } else {
            o.append("<font style=\"font-size:13px; font-weight:bold;\">@"+twitterusername+"</font>");
        }
        o.append("</a>");
        o.append("<br>");
        o.append("<font style=\"font-size:13px;\">has been tweeted by</font>");
        o.append("<br>");
        o.append("<font style=\"font-size:35px;\">"+uniqueCelebsWhoMentioned+"</font>");
        o.append("<br>");
        o.append("<a href=\"http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/\"><font style=\"font-size:13px;\">different celebs!</font></a>");
        o.append("</center>");
        o.append("</div>");

        return o.toString();
    }
}
