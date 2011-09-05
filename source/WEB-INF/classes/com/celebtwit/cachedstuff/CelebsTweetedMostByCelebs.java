package com.celebtwit.cachedstuff;


import com.celebtwit.dao.Pl;
import com.celebtwit.helpers.GetTwitsByMentioned;
import com.celebtwit.helpers.StartDateEndDate;
import com.celebtwit.helpers.TwitMention;
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
public class CelebsTweetedMostByCelebs implements CachedStuff, Serializable {

    private Calendar refreshedTimestamp;
    private String html;

    private String requestParamTime = "";
    private int numbertoget = 15;
    private boolean forceRefresh = false;
    private boolean includerankingnumbers = false;


    public CelebsTweetedMostByCelebs(String requestParamTime, int numbertoget, boolean forceRefresh, boolean includerankingnumbers) {
        this.requestParamTime = requestParamTime;
        this.numbertoget = numbertoget;
        this.forceRefresh = forceRefresh;
        this.includerankingnumbers = includerankingnumbers;
    }

    public String getKey() {
        return "CelebsTweetedMostByCelebs-requestParamTime="+requestParamTime+"-numbertoget="+numbertoget+"-forceRefresh="+forceRefresh+"-includerankingnumbers="+includerankingnumbers;
    }

    public void refresh(Pl pl) {
        StringBuffer out = new StringBuffer();
        //Start Refresh
        StartDateEndDate sted = new StartDateEndDate(requestParamTime);
        int count = 0;
        ArrayList<TwitMention> twitMentions = GetTwitsByMentioned.get(sted.getStartDate(), sted.getEndDate(), 0, true, numbertoget, pl.getPlid());
        for (Iterator<TwitMention> iterator=twitMentions.iterator(); iterator.hasNext();) {
            TwitMention twitMention = iterator.next();
            count++;
            String countStr = "";
            if (includerankingnumbers){ countStr = count+". "; }
            out.append("<font class=\"normalfont\" style=\"font-weight:bold;\">"+Util.countStr(count, includerankingnumbers)+"<a href=\"/twitter/"+twitMention.getTwit().getTwitterusername()+"/\">@"+twitMention.getTwit().getRealname()+"</a></font><font class=\"tinyfont\"> in "+twitMention.getMentions()+" "+pl.getCelebiscalled()+" tweets</font><br/>\n");
        }
        //End Refresh
        html = out.toString();
        refreshedTimestamp = Calendar.getInstance();
    }

    public Calendar refreshedTimestamp() {
        return refreshedTimestamp;
    }

    public int maxAgeInMinutes() {
        return 60*24*3;
    }

    public String getHtml() {
        return html;
    }
}