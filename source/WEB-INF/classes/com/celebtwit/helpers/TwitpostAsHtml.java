package com.celebtwit.helpers;

import com.celebtwit.dao.Twitpost;
import com.celebtwit.dao.Twit;
import com.celebtwit.util.Time;
import com.celebtwit.util.Util;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
        String post = twitpost.getPost();
        post = activateLinks(post);
        post = linkTwitternames(post, twit);

        out.append("<div class=\"notRoundedBox\" style=\"width:"+widthinpixels+"px; padding:3px;\">");
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
            out.append("            <font class=\"mediumfont\" style=\"font-weight:bold; font-color:#333333;\"><a href=\"/twitter/"+twit.getTwitterusername()+"/\">"+twit.getRealname()+"</a></font>");
        } else {
            out.append("            <font class=\"mediumfont\" style=\"font-weight:bold; font-color:#333333;\"><a href=\"/twitter/"+twit.getTwitterusername()+"/\">@"+twit.getTwitterusername()+"</a></font>");
        }
        out.append("<a href=\"http://www.twitter.com/"+twit.getTwitterusername()+"/\"><img src=\"/images/twitter-16x16.png\" width=16 height=16 border=0></a>");
        out.append("        </td>");
        out.append("    </tr>");
        out.append("    <tr>");
        out.append("        <td valign=\"top\">");
        out.append("            <font class=\"normalfont\" style=\"font-weight:bold;\">"+post+"</font><br/>");
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


    private static String activateLinks(String post){
        Logger logger = Logger.getLogger(TwitpostAsHtml.class);
        boolean haveLink = false;
        //if (post!=null && post.indexOf("http")>-1){ haveLink = true; }
        //if (haveLink){logger.debug("activateLinks("+post+")");}
        StringBuffer out = new StringBuffer();
        try{
            //http://regexlib.com/REDetails.aspx?regexp_id=96
            Pattern p = Pattern.compile("(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?");
            Matcher m = p.matcher(post);
            // Loop through
            while(m.find()) {
                String match = m.group();
                //if (haveLink){logger.debug("match="+match);}
                m.appendReplacement(out, Util.cleanForAppendreplacement("<a href=\""+match+"\" target=\"_blank\">"+match+"</a>"));
            }
            // Add the last segment
            try{
                m.appendTail(out);
            } catch (Exception e){
                //Do nothing... just null pointer
            }

        } catch (Exception ex) {
            logger.error("", ex);
        }
        //if (haveLink){logger.debug("out="+out.toString());}
        return out.toString();
    }

    private static String linkTwitternames(String post, Twit twit){
        Logger logger = Logger.getLogger(TwitpostAsHtml.class);
        boolean haveLink = false;
        //if (post!=null && post.indexOf("http")>-1){ haveLink = true; }
        //if (haveLink){logger.debug("activateLinks("+post+")");}
        StringBuffer out = new StringBuffer();
        try{
            //http://regexlib.com/REDetails.aspx?regexp_id=96
            Pattern p = Pattern.compile("(^|\\W)@(\\w)+");
            Matcher m = p.matcher(post);
            // Loop through
            while(m.find()) {
                String match = m.group();
                //if (haveLink){logger.debug("match="+match);}
                m.appendReplacement(out, Util.cleanForAppendreplacement("<a href=\"/chatter/"+twit.getTwitterusername()+"/"+match.substring(1,match.length())+"/\">"+match+"</a>"));
            }
            // Add the last segment
            try{
                m.appendTail(out);
            } catch (Exception e){
                //Do nothing... just null pointer
            }

        } catch (Exception ex) {
            logger.error("", ex);
        }
        //if (haveLink){logger.debug("out="+out.toString());}
        return out.toString();
    }


}
