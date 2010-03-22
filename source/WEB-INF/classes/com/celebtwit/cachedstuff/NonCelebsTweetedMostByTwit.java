package com.celebtwit.cachedstuff;


import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.helpers.GetTwitsByMentioned;
import com.celebtwit.helpers.StartDateEndDate;
import com.celebtwit.helpers.TwitMention;
import com.celebtwit.htmlui.Pagez;
import com.celebtwit.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * User: Joe Reger Jr
 * Date: Jan 29, 2008
 * Time: 2:03:07 PM
 */
public class NonCelebsTweetedMostByTwit implements CachedStuff, Serializable {

    private Calendar refreshedTimestamp;
    private String html;

    private Twit twit;
    private String twitterusername;
    private String requestParamTime = "";
    private int numbertoget = 15;
    private boolean includerankingnumbers = false;


    public NonCelebsTweetedMostByTwit(Twit twit, String twitterusername, String requestParamTime, int numbertoget, boolean includerankingnumbers) {
        this.twit = twit;
        this.twitterusername = twitterusername;
        this.requestParamTime = requestParamTime;
        this.numbertoget = numbertoget;
        this.includerankingnumbers = includerankingnumbers;
    }

    public String getKey() {
        return "NonCelebsTweetedMostByTwit-"+requestParamTime+"-numbertoget-"+numbertoget+"-twit-"+twit.getTwitid()+"-twitterusername-"+twitterusername+"-includerankingnumbers-"+includerankingnumbers;
    }

    public void refresh(Pl pl) {
        StringBuffer out = new StringBuffer();
        //Start Refresh
        StartDateEndDate sted = new StartDateEndDate(requestParamTime);
        int count = 0;
        ArrayList<TwitMention> twitMentions = GetTwitsByMentioned.get(sted.getStartDate(), sted.getEndDate(), twit.getTwitid(), false, numbertoget, Pagez.getUserSession().getPl().getPlid());
        for (Iterator<TwitMention> iterator=twitMentions.iterator(); iterator.hasNext();) {
            TwitMention twitMention = iterator.next();
            count++;
            String countStr = "";
            if (includerankingnumbers){ countStr = count+". "; }
            out.append("<font class=\"normalfont\" style=\"font-weight:bold;\">"+Util.countStr(count, includerankingnumbers)+"<a href=\"/twitter/"+twitMention.getTwit().getTwitterusername()+"/\">@"+twitMention.getTwit().getTwitterusername()+"</a></font><font class=\"tinyfont\"> "+twitMention.getMentions()+" tweets</font><br/>");
        }
        //End Refresh
        html = out.toString();
        refreshedTimestamp = Calendar.getInstance();
    }

    public Calendar refreshedTimestamp() {
        return refreshedTimestamp;
    }

    public int maxAgeInMinutes() {
        return 180;
    }

    public String getHtml() {
        return html;
    }
}