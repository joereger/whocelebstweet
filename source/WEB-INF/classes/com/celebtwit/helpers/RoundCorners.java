package com.celebtwit.helpers;

/**
 * User: Joe Reger Jr
 * Date: Apr 19, 2009
 * Time: 11:23:58 AM
 */
public class RoundCorners {
    
    public static String start(int widthInPixels){
        StringBuffer out = new StringBuffer();
        out.append("<div style=\"width:"+widthInPixels+";");
        out.append("<div class=\"roundedcornr_box_507644\">\n" +
                "   <div class=\"roundedcornr_top_507644\"><div></div></div>\n" +
                "      <div class=\"roundedcornr_content_507644\">\n");
        return out.toString();
    }

    public static String end(){
        StringBuffer out = new StringBuffer();
        out.append("   </div>\n" +
                "   <div class=\"roundedcornr_bottom_507644\"><div></div></div>\n" +
                "</div>");
        out.append("</div>");
        return out.toString();
    }

}
