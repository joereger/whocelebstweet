package com.celebtwit.htmluibeans;

import com.celebtwit.helpers.*;
import com.celebtwit.htmlui.Pagez;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.cache.html.DbcacheexpirableCache;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Apr 29, 2009
 * Time: 3:19:12 PM
 */
public class PublicIndexWhoPanel {

    public static String getHtml(Pl pl, String requestParamTime){
        return getHtml(pl, requestParamTime, false);
    }

    public static String getHtml(Pl pl, String requestParamTime, String refreshRequestParam){
        boolean forceRefresh = false;
        if (refreshRequestParam!=null && (refreshRequestParam.equals("true") || refreshRequestParam.equals("1"))){
            forceRefresh = true;
        }
        return getHtml(pl, requestParamTime, forceRefresh);
    }

    public static String getHtml(Pl pl, String requestParamTime, boolean forceRefresh){
        Logger logger = Logger.getLogger(PublicTwitterWhoPanel.class);
        String out = "";
        String key = "index.jsp-whopanel-time-"+requestParamTime;
        String group = "PublicIndexWhoPanel.java-plid-"+pl.getPlid();
        Object fromCache = DbcacheexpirableCache.get(key, group);
        if (fromCache!=null && !forceRefresh){
            try{out = (String)fromCache;}catch(Exception ex){logger.error("", ex);}
        } else {
            out = generateHtml(pl, requestParamTime);
            DbcacheexpirableCache.put(key, group, out, DbcacheexpirableCache.expireIn3Hrs());
        }
        return out;
    }

    private static String generateHtml(Pl pl, String requestParamTime){
        StringBuffer out = new StringBuffer();

        StartDateEndDate sted = new StartDateEndDate(requestParamTime);

        out.append("<table cellpadding=\"0\" cellspacing=\"10\" border=\"0\">\n");
        out.append("            <tr>\n");
        out.append("                <td valign=\"top\" width=\"50%\">\n");
        out.append("                    <img src=\"/images/infoicon-celebtonon.png\" alt=\"\" width=\"110\" height=\"64\" align=\"left\">\n");
        out.append("                    <font class=\"mediumfont\" style=\"color:#666666;\">non-"+pl.getCelebiscalled()+"s tweeted the most by "+pl.getCelebiscalled()+"s</font>\n");
        out.append("                </td>\n");
        out.append("                <td valign=\"top\">\n");
        out.append("                    <img src=\"/images/infoicon-celebtoceleb.png\" alt=\"\" width=\"110\" height=\"64\" align=\"left\">\n");
        out.append("                    <font class=\"mediumfont\" style=\"color:#666666;\">"+pl.getCelebiscalled()+"s tweeted the most by other "+pl.getCelebiscalled()+"s</font>\n");
        out.append("                </td>\n");
        out.append("            </tr>\n");
        out.append("            <tr>\n");
        out.append("                <td valign=\"top\" width=\"50%\">\n");

        if (true){
             ArrayList<TwitMention> twitMentions = GetTwitsByMentioned.get(sted.getStartDate(), sted.getEndDate(), 0, false, 15, pl.getPlid());
             for (Iterator<TwitMention> iterator=twitMentions.iterator(); iterator.hasNext();) {
                 TwitMention twitMention = iterator.next();
                 out.append("<font class=\"normalfont\" style=\"font-weight:bold;\"><a href=\"/twitter/"+twitMention.getTwit().getTwitterusername()+"/\">@"+twitMention.getTwit().getTwitterusername()+"</a></font><font class=\"tinyfont\"> in "+twitMention.getMentions()+" "+pl.getCelebiscalled()+" tweets<br/></font>");
             }
         }

        out.append("                </td>\n");
        out.append("                <td valign=\"top\">\n");

        if (true){
            ArrayList<TwitMention> twitMentions = GetTwitsByMentioned.get(sted.getStartDate(), sted.getEndDate(), 0, true, 15, pl.getPlid());
            for (Iterator<TwitMention> iterator=twitMentions.iterator(); iterator.hasNext();) {
                TwitMention twitMention = iterator.next();
                out.append("<font class=\"normalfont\" style=\"font-weight:bold;\"><a href=\"/twitter/"+twitMention.getTwit().getTwitterusername()+"/\">@"+twitMention.getTwit().getRealname()+"</a></font><font class=\"tinyfont\"> in "+twitMention.getMentions()+" "+pl.getCelebiscalled()+" tweets</font><br/>\n");
            }
        }

        out.append("                </td>\n");
        out.append("            </tr>\n");
        out.append("            <tr>\n");
        out.append("                <td colspan=\"2\">\n");
        out.append("                    <img src=\"/images/clear.gif\" alt=\"\" width=\"1\" height=\"10\" align=\"left\">\n");
        out.append("                </td>\n");
        out.append("            </tr>\n");
        out.append("            <tr>\n");
        out.append("                <td valign=\"top\" width=\"50%\">\n");
        out.append("                    <img src=\"/images/infoicon-manycelebstonon.png\" alt=\"\" width=\"110\" height=\"64\" align=\"left\">\n");
        out.append("                    <font class=\"mediumfont\" style=\"color:#666666;\">non-"+pl.getCelebiscalled()+"s tweeted by the most different "+pl.getCelebiscalled()+"s</font>\n");
        out.append("                </td>\n");
        out.append("                <td valign=\"top\">\n");
        out.append("                    <img src=\"/images/infoicon-manycelebstoceleb.png\" alt=\"\" width=\"110\" height=\"64\" align=\"left\">\n");
        out.append("                    <font class=\"mediumfont\" style=\"color:#666666;\">"+pl.getCelebiscalled()+"s tweeted by the most different "+pl.getCelebiscalled()+"s</font>\n");
        out.append("                </td>\n");
        out.append("            </tr>\n");
        out.append("            <tr>\n");
        out.append("                <td valign=\"top\" width=\"50%\">\n");

        if (true){
            ArrayList<TwitUniqueCelebsMentioning> twitUniques = GetTwitsByUniqueCelebsMentioning.get(sted.getStartDate(), sted.getEndDate(), false, 15, pl.getPlid());
            for (Iterator<TwitUniqueCelebsMentioning> iterator=twitUniques.iterator(); iterator.hasNext();) {
                TwitUniqueCelebsMentioning twitUnique = iterator.next();
                out.append("<font class=\"normalfont\" style=\"font-weight:bold;\"><a href=\"/twitter/"+twitUnique.getTwit().getTwitterusername()+"/\">@"+twitUnique.getTwit().getTwitterusername()+"</a></font><font class=\"tinyfont\"> tweeted by "+twitUnique.getUniquecelebsmentioning()+" "+pl.getCelebiscalled()+"s</font><br/>");
            }
        }


        out.append("                </td>\n");
        out.append("                <td valign=\"top\">\n");

        if (true){
            ArrayList<TwitUniqueCelebsMentioning> twitUniques = GetTwitsByUniqueCelebsMentioning.get(sted.getStartDate(), sted.getEndDate(), true, 15, pl.getPlid());
            for (Iterator<TwitUniqueCelebsMentioning> iterator=twitUniques.iterator(); iterator.hasNext();) {
                TwitUniqueCelebsMentioning twitUnique = iterator.next();
                out.append("<font class=\"normalfont\" style=\"font-weight:bold;\"><a href=\"/twitter/"+twitUnique.getTwit().getTwitterusername()+"/\">@"+twitUnique.getTwit().getRealname()+"</a></font><font class=\"tinyfont\"> tweeted by "+twitUnique.getUniquecelebsmentioning()+" "+pl.getCelebiscalled()+"s</font><br/>\n");
            }
        }


        out.append("                </td>\n");
        out.append("            </tr>\n");
        out.append("        </table>");


        return out.toString();
    }


}
