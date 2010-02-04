package com.celebtwit.helpers;

import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.htmlui.Pagez;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: Feb 4, 2010
 * Time: 1:28:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatsOutput {


    private static String countStr(int count, boolean includerankingnumbers){
        //If no nums return blank
        if (!includerankingnumbers){return "";}
        //Otherwise generate the nums
        StringBuffer out = new StringBuffer();
        out.append(count);
        out.append(".&nbsp;");
        if (count<=9){
            out.append("&nbsp;&nbsp;&nbsp;&nbsp;");
        } else if (count<=99){
            out.append("&nbsp;&nbsp;");
        }
        return out.toString();
    }

    //These are methods for overall stats for the whole private label

    public static String nonCelebsTweetedMostByCelebs(Pl pl, String requestParamTime, int numbertoget, boolean includerankingnumbers){
        StringBuffer out = new StringBuffer();
        StartDateEndDate sted = new StartDateEndDate(requestParamTime);

        int count = 0;
        ArrayList<TwitMention> twitMentions = GetTwitsByMentioned.get(sted.getStartDate(), sted.getEndDate(), 0, false, numbertoget, pl.getPlid());
        for (Iterator<TwitMention> iterator=twitMentions.iterator(); iterator.hasNext();) {
             TwitMention twitMention = iterator.next();
             count++;
             out.append("<font class=\"normalfont\" style=\"font-weight:bold;\">"+countStr(count, includerankingnumbers)+"<a href=\"/twitter/"+twitMention.getTwit().getTwitterusername()+"/\">@"+twitMention.getTwit().getTwitterusername()+"</a></font><font class=\"tinyfont\"> in "+twitMention.getMentions()+" "+pl.getCelebiscalled()+" tweets<br/></font>");
        }

        return out.toString();
    }

    public static String celebsTweetedMostByCelebs(Pl pl, String requestParamTime, int numbertoget, boolean includerankingnumbers){
        StringBuffer out = new StringBuffer();
        StartDateEndDate sted = new StartDateEndDate(requestParamTime);

        int count = 0;
        ArrayList<TwitMention> twitMentions = GetTwitsByMentioned.get(sted.getStartDate(), sted.getEndDate(), 0, true, numbertoget, pl.getPlid());
        for (Iterator<TwitMention> iterator=twitMentions.iterator(); iterator.hasNext();) {
            TwitMention twitMention = iterator.next();
            count++;
            String countStr = "";
            if (includerankingnumbers){ countStr = count+". "; }
            out.append("<font class=\"normalfont\" style=\"font-weight:bold;\">"+countStr(count, includerankingnumbers)+"<a href=\"/twitter/"+twitMention.getTwit().getTwitterusername()+"/\">@"+twitMention.getTwit().getRealname()+"</a></font><font class=\"tinyfont\"> in "+twitMention.getMentions()+" "+pl.getCelebiscalled()+" tweets</font><br/>\n");
        }

        return out.toString();
    }

    public static String nonCelebsTweetedByMostDifferentCelebs(Pl pl, String requestParamTime, int numbertoget, boolean includerankingnumbers){
        StringBuffer out = new StringBuffer();
        StartDateEndDate sted = new StartDateEndDate(requestParamTime);

        int count = 0;
        ArrayList<TwitUniqueCelebsMentioning> twitUniques = GetTwitsByUniqueCelebsMentioning.get(sted.getStartDate(), sted.getEndDate(), false, numbertoget, pl.getPlid());
        for (Iterator<TwitUniqueCelebsMentioning> iterator=twitUniques.iterator(); iterator.hasNext();) {
            TwitUniqueCelebsMentioning twitUnique = iterator.next();
            count++;
            String countStr = "";
            if (includerankingnumbers){ countStr = count+". "; }
            out.append("<font class=\"normalfont\" style=\"font-weight:bold;\">"+countStr(count, includerankingnumbers)+"<a href=\"/twitter/"+twitUnique.getTwit().getTwitterusername()+"/\">@"+twitUnique.getTwit().getTwitterusername()+"</a></font><font class=\"tinyfont\"> tweeted by "+twitUnique.getUniquecelebsmentioning()+" "+pl.getCelebiscalled()+"s</font><br/>");
        }

        return out.toString();
    }


    public static String celebsTweetedByMostDifferentCelebs(Pl pl, String requestParamTime, int numbertoget, boolean includerankingnumbers){
        StringBuffer out = new StringBuffer();
        StartDateEndDate sted = new StartDateEndDate(requestParamTime);

        int count = 0;
        ArrayList<TwitUniqueCelebsMentioning> twitUniques = GetTwitsByUniqueCelebsMentioning.get(sted.getStartDate(), sted.getEndDate(), true, numbertoget, pl.getPlid());
        for (Iterator<TwitUniqueCelebsMentioning> iterator=twitUniques.iterator(); iterator.hasNext();) {
            TwitUniqueCelebsMentioning twitUnique = iterator.next();
            count++;
            String countStr = "";
            if (includerankingnumbers){ countStr = count+". "; }
            out.append("<font class=\"normalfont\" style=\"font-weight:bold;\">"+countStr(count, includerankingnumbers)+"<a href=\"/twitter/"+twitUnique.getTwit().getTwitterusername()+"/\">@"+twitUnique.getTwit().getRealname()+"</a></font><font class=\"tinyfont\"> tweeted by "+twitUnique.getUniquecelebsmentioning()+" "+pl.getCelebiscalled()+"s</font><br/>\n");
        }

        return out.toString();
    }



    //Below this line are methods for individual tweeps



    public static String nonCelebsTweetedMostByTwit(Twit twit, String twitterusername, Pl pl, String requestParamTime, int numbertoget, boolean includerankingnumbers){
        StringBuffer out = new StringBuffer();
        StartDateEndDate sted = new StartDateEndDate(requestParamTime);

        int count = 0;
        ArrayList<TwitMention> twitMentions = GetTwitsByMentioned.get(sted.getStartDate(), sted.getEndDate(), twit.getTwitid(), false, numbertoget, Pagez.getUserSession().getPl().getPlid());
        for (Iterator<TwitMention> iterator=twitMentions.iterator(); iterator.hasNext();) {
            TwitMention twitMention = iterator.next();
            count++;
            String countStr = "";
            if (includerankingnumbers){ countStr = count+". "; }
            out.append("<font class=\"normalfont\" style=\"font-weight:bold;\">"+countStr(count, includerankingnumbers)+"<a href=\"/twitter/"+twitMention.getTwit().getTwitterusername()+"/\">@"+twitMention.getTwit().getTwitterusername()+"</a></font><font class=\"tinyfont\"> "+twitMention.getMentions()+" tweets</font><br/>");
        }

        return out.toString();
    }

    public static String celebsTweetedMostByTwit(Twit twit, String twitterusername, Pl pl, String requestParamTime, int numbertoget, boolean includerankingnumbers){
        StringBuffer out = new StringBuffer();
        StartDateEndDate sted = new StartDateEndDate(requestParamTime);

        int count = 0;
        ArrayList<TwitMention> twitMentions = GetTwitsByMentioned.get(sted.getStartDate(), sted.getEndDate(), twit.getTwitid(), true, numbertoget, Pagez.getUserSession().getPl().getPlid());
        for (Iterator<TwitMention> iterator=twitMentions.iterator(); iterator.hasNext();) {
            TwitMention twitMention = iterator.next();
            count++;
            String countStr = "";
            if (includerankingnumbers){ countStr = count+". "; }
            out.append("<font class=\"normalfont\" style=\"font-weight:bold;\">"+countStr(count, includerankingnumbers)+"<a href=\"/chatter/"+twit.getTwitterusername()+"/"+twitMention.getTwit().getTwitterusername()+"/\">@"+twitMention.getTwit().getRealname()+"</a></font><font class=\"tinyfont\"> "+twitMention.getMentions()+" tweets</font><br/>");
        }

        return out.toString();
    }


    public static String celebsWhoTweetedTwit(Twit twit, String twitterusername, Pl pl, String requestParamTime, int numbertoget, boolean includerankingnumbers){
        StringBuffer out = new StringBuffer();
        StartDateEndDate sted = new StartDateEndDate(requestParamTime);

        int count = 0;
        ArrayList<TwitCelebWhoMentioned> twitUniques = GetCelebsWhoMentioned.get(sted.getStartDate(), sted.getEndDate(), twit.getTwitid(), numbertoget, Pagez.getUserSession().getPl().getPlid());
        for (Iterator<TwitCelebWhoMentioned> iterator=twitUniques.iterator(); iterator.hasNext();) {
            TwitCelebWhoMentioned twitCelebWhoMentioned = iterator.next();
            count++;
            String countStr = "";
            if (includerankingnumbers){ countStr = count+". "; }
            out.append("<font class=\"normalfont\" style=\"font-weight:bold;\">"+countStr(count, includerankingnumbers)+"<a href=\"/chatter/"+twit.getTwitterusername()+"/"+twitCelebWhoMentioned.getTwit().getTwitterusername()+"/\">@"+twitCelebWhoMentioned.getTwit().getRealname()+"</a></font><font class=\"tinyfont\"> "+twitCelebWhoMentioned.getMentions()+" tweets</font><br/>");
        }

        return out.toString();
    }


}
