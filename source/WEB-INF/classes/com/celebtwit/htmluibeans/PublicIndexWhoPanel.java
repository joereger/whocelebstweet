package com.celebtwit.htmluibeans;

import com.celebtwit.dao.Pl;
import com.celebtwit.helpers.StartDateEndDate;
import com.celebtwit.helpers.StatsOutputCached;

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

//    public static String getHtml(Pl pl, String requestParamTime, boolean forceRefresh){
//        Logger logger = Logger.getLogger(PublicTwitterWhoPanel.class);
//        String out = "";
//        String key = "index.jsp-whopanel-time-"+requestParamTime+"-adnetworkname-"+Pagez.getUserSession().getAdNetworkName();
//        String group = "PublicIndexWhoPanel.java-plid-"+pl.getPlid();
//        Object fromCache = DbcacheexpirableCache.get(key, group);
//        if (fromCache!=null && !forceRefresh){
//            try{out = (String)fromCache;}catch(Exception ex){logger.error("", ex);}
//        } else {
//            out = generateHtml(pl, requestParamTime);
//            DbcacheexpirableCache.put(key, group, out, DbcacheexpirableCache.expireIn3Hrs());
//        }
//        return out;
//    }

    public static String getHtml(Pl pl, String requestParamTime, boolean forceRefresh){
        StringBuffer out = new StringBuffer();

        StartDateEndDate sted = new StartDateEndDate(requestParamTime);
        if (requestParamTime==null){ requestParamTime="alltime"; }

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
        out.append(StatsOutputCached.nonCelebsTweetedMostByCelebs(pl, requestParamTime, 15, forceRefresh, false));
        out.append("<br/><font class=\"normalfont\" style=\"font-weight:bold; margin-left:0px;\"><a href=\"/stats/nonCelebsTweetedMostByCelebs/when/"+requestParamTime+"/\">see more >></a></font>");
        out.append("                </td>\n");
        out.append("                <td valign=\"top\">\n");
        out.append(StatsOutputCached.celebsTweetedMostByCelebs(pl, requestParamTime, 15, forceRefresh, false));
        out.append("<br/><font class=\"normalfont\" style=\"font-weight:bold; margin-left:0px;\"><a href=\"/stats/celebsTweetedMostByCelebs/when/"+requestParamTime+"/\">see more >></a></font>");
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
        out.append(StatsOutputCached.nonCelebsTweetedByMostDifferentCelebs(pl, requestParamTime, 15, forceRefresh, false));
        out.append("<br/><font class=\"normalfont\" style=\"font-weight:bold; margin-left:0px;\"><a href=\"/stats/nonCelebsTweetedByMostDifferentCelebs/when/"+requestParamTime+"/\">see more >></a></font>");
        out.append("                </td>\n");
        out.append("                <td valign=\"top\">\n");
        out.append(StatsOutputCached.celebsTweetedByMostDifferentCelebs(pl, requestParamTime, 15, forceRefresh, false));
        out.append("<br/><font class=\"normalfont\" style=\"font-weight:bold; margin-left:0px;\"><a href=\"/stats/celebsTweetedByMostDifferentCelebs/when/"+requestParamTime+"/\">see more >></a></font>");
        out.append("                </td>\n");
        out.append("            </tr>\n");
        out.append("        </table>");


        return out.toString();
    }


}
