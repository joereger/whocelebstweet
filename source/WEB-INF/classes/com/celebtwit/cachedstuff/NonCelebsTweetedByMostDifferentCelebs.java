package com.celebtwit.cachedstuff;


import com.celebtwit.dao.Pl;
import com.celebtwit.helpers.*;
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
public class NonCelebsTweetedByMostDifferentCelebs implements CachedStuff, Serializable {

    private Calendar refreshedTimestamp;
    private String html;

    private String requestParamTime = "";
    private int numbertoget = 15;
    private boolean forceRefresh = false;
    private boolean includerankingnumbers = false;


    public NonCelebsTweetedByMostDifferentCelebs(String requestParamTime, int numbertoget, boolean forceRefresh, boolean includerankingnumbers) {
        this.requestParamTime = requestParamTime;
        this.numbertoget = numbertoget;
        this.forceRefresh = forceRefresh;
        this.includerankingnumbers = includerankingnumbers;
    }

    public String getKey() {
        return "NonCelebsTweetedByMostDifferentCelebs-requestParamTime="+requestParamTime+"-numbertoget="+numbertoget+"-forceRefresh="+forceRefresh+"-includerankingnumbers="+includerankingnumbers;
    }

    public void refresh(Pl pl) {
        StringBuffer out = new StringBuffer();
        //Start Refresh
        StartDateEndDate sted = new StartDateEndDate(requestParamTime);
        int count = 0;
        ArrayList<TwitUniqueCelebsMentioning> twitUniques = GetTwitsByUniqueCelebsMentioning.get(sted.getStartDate(), sted.getEndDate(), false, numbertoget, pl.getPlid());
        for (Iterator<TwitUniqueCelebsMentioning> iterator=twitUniques.iterator(); iterator.hasNext();) {
            TwitUniqueCelebsMentioning twitUnique = iterator.next();
            count++;
            String countStr = "";
            if (includerankingnumbers){ countStr = count+". "; }
            out.append("<font class=\"normalfont\" style=\"font-weight:bold;\">"+Util.countStr(count, includerankingnumbers)+"<a href=\"/twitter/"+twitUnique.getTwit().getTwitterusername()+"/\">@"+twitUnique.getTwit().getTwitterusername()+"</a></font><font class=\"tinyfont\"> tweeted by "+twitUnique.getUniquecelebsmentioning()+" "+pl.getCelebiscalled()+"s</font><br/>");
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