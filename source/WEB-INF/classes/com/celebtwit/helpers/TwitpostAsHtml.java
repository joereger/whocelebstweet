package com.celebtwit.helpers;

import com.celebtwit.dao.Twitpost;
import com.celebtwit.dao.Twit;
import com.celebtwit.util.Time;
import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Apr 22, 2009
 * Time: 5:31:26 PM
 */
public class TwitpostAsHtml {

    public static String get(Twitpost twitpost, int widthinpixels){
        Logger logger = Logger.getLogger(TwitpostAsHtml.class);
        StringBuffer out = new StringBuffer();
        Twit twit = Twit.get(twitpost.getTwitid());
        out.append("<div class=\"notRoundedBoxYellow\" style=\"width:"+widthinpixels+"px; padding:3px;\">");
        out.append("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");
        out.append("    <tr>");
        out.append("        <td rowspan=\"3\" width=\"55\">");
        if (!twit.getProfile_image_url().equals("")){
            out.append("        <img src=\""+twit.getProfile_image_url()+"\" width=\"48\" height=\"48\" border=\"0\">");
        } else {
            out.append("        <img src=\"/images/clear.gif\" width=\"48\" height=\"48\" border=\"0\">");
        }
        out.append("        </td>");
        out.append("        <td>");
        if (twit.getIsceleb() && !twit.getRealname().equals("")){
            out.append("            <font class=\"mediumfont\" style=\"font-weight:bold; font-color:#333333;\"><a href=\"/twitter/"+twit.getTwitterusername()+"/\">@"+twit.getRealname()+"</a></font>");
        } else {
            out.append("            <font class=\"mediumfont\" style=\"font-weight:bold; font-color:#333333;\"><a href=\"/twitter/"+twit.getTwitterusername()+"/\">@"+twit.getTwitterusername()+"</a></font>");
        }
        out.append("<a href=\"http://www.twitter.com/"+twit.getTwitterusername()+"/\"><img src=\"/images/twitter-16x16.png\" width=16 height=16 border=0></a>");
        out.append("        </td>");
        out.append("    </tr>");
        out.append("    <tr>");
        out.append("        <td valign=\"top\">");
        out.append("            <font class=\"normalfont\" style=\"font-weight:bold;\">"+twitpost.getPost()+"</font><br/>");
        out.append("        </td>");
        out.append("    </tr>");
        out.append("    <tr>");
        out.append("        <td valign=\"top\">");
        out.append("            <font class=\"tinyfont\">"+Time.agoText(Time.getCalFromDate(twitpost.getCreated_at()))+"</font>");
        out.append("        </td>");
        out.append("    </tr>");
        out.append("</table>");
        out.append("</div>");
        out.append("<img src=\"/images/clear.gif\" width=\"1\" height=\"3\"><br/>");
        return out.toString();
    }

}
