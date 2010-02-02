package com.celebtwit.htmluibeans;

import com.celebtwit.ads.AdUtil;
import com.celebtwit.cache.html.DbcacheexpirableCache;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.helpers.*;
import com.celebtwit.htmlui.Pagez;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * User: Joe Reger Jr
 * Date: Apr 29, 2009
 * Time: 3:56:21 PM
 */
public class PublicTwitterWhoPanel {

    public static String getHtml(Twit twit, String twitterusername, Pl pl, String requestParamTime){
        return getHtml(twit, twitterusername, pl, requestParamTime, false);
    }

    public static String getHtml(Twit twit, String twitterusername, Pl pl, String requestParamTime, String refreshRequestParam){
        boolean forceRefresh = false;
        if (refreshRequestParam!=null && (refreshRequestParam.equals("true") || refreshRequestParam.equals("1"))){
            forceRefresh = true;
        }
        return getHtml(twit, twitterusername, pl, requestParamTime, forceRefresh);
    }

    public static String getHtml(Twit twit, String twitterusername, Pl pl, String requestParamTime, boolean forceRefresh){
        Logger logger = Logger.getLogger(PublicTwitterWhoPanel.class);
        String out = "";
        String key = "twitter.jsp-whopanel-twitid="+twit.getTwitid()+"-twitterusername-"+twitterusername+"-time-"+requestParamTime+"-adnetworkname-"+Pagez.getUserSession().getAdNetworkName();
        String group = "PublicTwitterWhoPanel.java-plid-"+pl.getPlid();
        Object fromCache = DbcacheexpirableCache.get(key, group);
        if (fromCache!=null && !forceRefresh){
            try{out = (String)fromCache;}catch(Exception ex){logger.error("", ex);}
        } else {
            out = generateHtml(twit, twitterusername, pl, requestParamTime);
            DbcacheexpirableCache.put(key, group, out, DbcacheexpirableCache.expireIn6Hrs());
        }
        return out;
    }


    private static String generateHtml(Twit twit, String twitterusername, Pl pl, String requestParamTime){
        StringBuffer out = new StringBuffer();

        StartDateEndDate sted = new StartDateEndDate(requestParamTime);

        out.append("<table cellpadding=\"0\" cellspacing=\"10\" border=\"0\">\n");

        if (twit.getIsceleb()){

            out.append("            <tr>\n");
            out.append("                <td valign=\"top\" width=\"50%\">\n");

            if (twit!=null && twit.getIsceleb()){
                out.append("<font class=\"mediumfont\">non-"+pl.getCelebiscalled()+"s tweeted by "+twit.getRealname()+"</font>");
            } else {
                out.append("<font class=\"mediumfont\">non-"+pl.getCelebiscalled()+"s tweeted by @"+twitterusername+"</font>");
            }

            out.append("                </td>\n");
            out.append("                <td valign=\"top\">\n");
            
            if (twit!=null && twit.getIsceleb()){
                out.append("<font class=\"mediumfont\">"+pl.getCelebiscalled()+"s tweeted by "+twit.getRealname()+"</font>");
            } else {
                out.append("<font class=\"mediumfont\">"+pl.getCelebiscalled()+"s tweeted by @"+twitterusername+"</font>");
            }
            out.append("<br/><font class=\"tinyfont\">click to see "+pl.getCelebiscalled()+"-to-"+pl.getCelebiscalled()+" chatter</font>\n");
            out.append("                </td>\n");
            out.append("            </tr>\n");
            out.append("            <tr>\n");
            out.append("                <td valign=\"top\" width=\"50%\">\n");

            if (true){
                ArrayList<TwitMention> twitMentions = GetTwitsByMentioned.get(sted.getStartDate(), sted.getEndDate(), twit.getTwitid(), false, 15, Pagez.getUserSession().getPl().getPlid());
                for (Iterator<TwitMention> iterator=twitMentions.iterator(); iterator.hasNext();) {
                    TwitMention twitMention = iterator.next();
                    out.append("<font class=\"normalfont\" style=\"font-weight:bold;\"><a href=\"/twitter/"+twitMention.getTwit().getTwitterusername()+"/\">@"+twitMention.getTwit().getTwitterusername()+"</a></font><font class=\"tinyfont\"> "+twitMention.getMentions()+" tweets</font><br/>");
                }
            }

            out.append("                </td>\n");
            out.append("                <td valign=\"top\">\n");

            if (true){
                ArrayList<TwitMention> twitMentions = GetTwitsByMentioned.get(sted.getStartDate(), sted.getEndDate(), twit.getTwitid(), true, 15, Pagez.getUserSession().getPl().getPlid());
                for (Iterator<TwitMention> iterator=twitMentions.iterator(); iterator.hasNext();) {
                    TwitMention twitMention = iterator.next();
                    out.append("<font class=\"normalfont\" style=\"font-weight:bold;\"><a href=\"/chatter/"+twit.getTwitterusername()+"/"+twitMention.getTwit().getTwitterusername()+"/\">@"+twitMention.getTwit().getRealname()+"</a></font><font class=\"tinyfont\"> "+twitMention.getMentions()+" tweets</font><br/>");
                }
            }

            out.append("                </td>\n");
            out.append("            </tr>\n");

        }


        out.append("            <tr>\n");
        out.append("                <td colspan=\"2\">\n");
        out.append("                    <img src=\"/images/clear.gif\" alt=\"\" width=\"1\" height=\"10\" align=\"left\">\n");
        out.append("                </td>\n");
        out.append("            </tr>\n");
        out.append("            <tr>\n");
        out.append("                <td valign=\"top\" width=\"50%\" rowspan=\"2\">\n");

        out.append(AdUtil.get300x250());

        out.append("                </td>\n");
        out.append("                <td valign=\"top\" height=\"40\">\n");
        //out.append("                    <img src=\"/images/infoicon-manycelebstoceleb.png\" alt=\"\" width=\"110\" height=\"64\" align=\"left\">\n");
        if (twit!=null && twit.getIsceleb()){
            out.append("<font class=\"mediumfont\">"+pl.getCelebiscalled()+"s who've tweeted "+twit.getRealname()+"</font>");
        } else {
            out.append("<font class=\"mediumfont\">"+pl.getCelebiscalled()+"s who've tweeted @"+twitterusername+"</font>");
        }
        out.append("<br/><font class=\"tinyfont\">click to see "+pl.getCelebiscalled()+"-to-"+pl.getCelebiscalled()+" chatter</font>\n");
        out.append("                </td>\n");
        out.append("            </tr>\n");
        out.append("            <tr>\n");

        out.append("                <td valign=\"top\">\n");

        if (true){
            ArrayList<TwitCelebWhoMentioned> twitUniques = GetCelebsWhoMentioned.get(sted.getStartDate(), sted.getEndDate(), twit.getTwitid(), 15, Pagez.getUserSession().getPl().getPlid());
            for (Iterator<TwitCelebWhoMentioned> iterator=twitUniques.iterator(); iterator.hasNext();) {
                TwitCelebWhoMentioned twitCelebWhoMentioned = iterator.next();
                out.append("<font class=\"normalfont\" style=\"font-weight:bold;\"><a href=\"/chatter/"+twit.getTwitterusername()+"/"+twitCelebWhoMentioned.getTwit().getTwitterusername()+"/\">@"+twitCelebWhoMentioned.getTwit().getRealname()+"</a></font><font class=\"tinyfont\"> "+twitCelebWhoMentioned.getMentions()+" tweets</font><br/>");
            }
        }


        out.append("                </td>\n");
        out.append("            </tr>\n");
        out.append("        </table>");


        return out.toString();
    }


}
