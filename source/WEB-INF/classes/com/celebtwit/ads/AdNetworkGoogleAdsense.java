package com.celebtwit.ads;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: Feb 2, 2010
 * Time: 7:31:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdNetworkGoogleAdsense implements AdNetwork {

    public static String ADNETWORKNAME = "GoogleAdsense";

    public String getAdNetworkName() {
        return ADNETWORKNAME;
    }

    


    //New Ones


    public String get120x240SIDEBAR() {
        StringBuffer out = new StringBuffer();
        out.append("<script type=\"text/javascript\"><!--\n" +
                "google_ad_client = \"pub-9883617370563969\";\n" +
                "/* WCT_120x240_SIDEBAR */\n" +
                "google_ad_slot = \"3926353123\";\n" +
                "google_ad_width = 120;\n" +
                "google_ad_height = 240;\n" +
                "//-->\n" +
                "</script>\n" +
                "<script type=\"text/javascript\"\n" +
                "src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n" +
                "</script>");
        return out.toString();
    }

    public String get160x600INDEX() {
        StringBuffer out = new StringBuffer();
        out.append("<script type=\"text/javascript\"><!--\n" +
                "google_ad_client = \"pub-9883617370563969\";\n" +
                "/* WCT_160x600_INDEX */\n" +
                "google_ad_slot = \"1582608432\";\n" +
                "google_ad_width = 160;\n" +
                "google_ad_height = 600;\n" +
                "//-->\n" +
                "</script>\n" +
                "<script type=\"text/javascript\"\n" +
                "src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n" +
                "</script>");
        return out.toString();
    }

    public String get336x280PROFILEPIC() {
        StringBuffer out = new StringBuffer();
        out.append("<script type=\"text/javascript\"><!--\n" +
                "google_ad_client = \"pub-9883617370563969\";\n" +
                "/* WCT_336x280_PROFILEPIC */\n" +
                "google_ad_slot = \"4955551788\";\n" +
                "google_ad_width = 336;\n" +
                "google_ad_height = 280;\n" +
                "//-->\n" +
                "</script>\n" +
                "<script type=\"text/javascript\"\n" +
                "src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n" +
                "</script>");
        return out.toString();
    }

    public String get160x600STATS() {
        StringBuffer out = new StringBuffer();
        out.append("<script type=\"text/javascript\"><!--\n" +
                "google_ad_client = \"pub-9883617370563969\";\n" +
                "/* WCT_160x600_STATS */\n" +
                "google_ad_slot = \"3403015691\";\n" +
                "google_ad_width = 160;\n" +
                "google_ad_height = 600;\n" +
                "//-->\n" +
                "</script>\n" +
                "<script type=\"text/javascript\"\n" +
                "src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n" +
                "</script>");
        return out.toString();
    }

    public String get336x280TWEET() {
        StringBuffer out = new StringBuffer();
        out.append("<script type=\"text/javascript\"><!--\n" +
                "google_ad_client = \"pub-9883617370563969\";\n" +
                "/* WCT_336x280_TWEET */\n" +
                "google_ad_slot = \"0333573216\";\n" +
                "google_ad_width = 336;\n" +
                "google_ad_height = 280;\n" +
                "//-->\n" +
                "</script>\n" +
                "<script type=\"text/javascript\"\n" +
                "src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n" +
                "</script>");
        return out.toString();
    }

    public String get160x600TWITTERSTATS() {
        StringBuffer out = new StringBuffer();
        out.append("<script type=\"text/javascript\"><!--\n" +
                "google_ad_client = \"pub-9883617370563969\";\n" +
                "/* WCT_160x600_TWITTERSTATS */\n" +
                "google_ad_slot = \"8254790059\";\n" +
                "google_ad_width = 160;\n" +
                "google_ad_height = 600;\n" +
                "//-->\n" +
                "</script>\n" +
                "<script type=\"text/javascript\"\n" +
                "src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n" +
                "</script>");
        return out.toString();
    }

    public String get234x60TWITPOSTASHTML() {
        StringBuffer out = new StringBuffer();
        out.append("<script type=\"text/javascript\"><!--\n" +
                "google_ad_client = \"pub-9883617370563969\";\n" +
                "/* WCT_234x60_TWITPOSTASHTML */\n" +
                "google_ad_slot = \"8170913052\";\n" +
                "google_ad_width = 234;\n" +
                "google_ad_height = 60;\n" +
                "//-->\n" +
                "</script>\n" +
                "<script type=\"text/javascript\"\n" +
                "src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n" +
                "</script>");
        return out.toString();
    }

    public String get336x280PUBTWITWHOPANEL() {
        StringBuffer out = new StringBuffer();
        out.append("<script type=\"text/javascript\"><!--\n" +
                "google_ad_client = \"pub-9883617370563969\";\n" +
                "/* WCT_336x280_PUBTWITWHOPANEL */\n" +
                "google_ad_slot = \"5875830632\";\n" +
                "google_ad_width = 336;\n" +
                "google_ad_height = 280;\n" +
                "//-->\n" +
                "</script>\n" +
                "<script type=\"text/javascript\"\n" +
                "src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n" +
                "</script>");
        return out.toString();
    }

    public String get336x280KEYWORD() {
        StringBuffer out = new StringBuffer();
        out.append("<script type=\"text/javascript\"><!--\n" +
                "google_ad_client = \"pub-9883617370563969\";\n" +
                "/* WCT_336x280_KEYWORD */\n" +
                "google_ad_slot = \"0515480042\";\n" +
                "google_ad_width = 336;\n" +
                "google_ad_height = 280;\n" +
                "//-->\n" +
                "</script>\n" +
                "<script type=\"text/javascript\"\n" +
                "src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n" +
                "</script>");
        return out.toString();
    }

    public String get160x600TALKSABOUT() {
        StringBuffer out = new StringBuffer();
        out.append("<script type=\"text/javascript\"><!--\n" +
                "google_ad_client = \"pub-9883617370563969\";\n" +
                "/* WCT_160x600_TALKSABOUT */\n" +
                "google_ad_slot = \"5468974226\";\n" +
                "google_ad_width = 160;\n" +
                "google_ad_height = 600;\n" +
                "//-->\n" +
                "</script>\n" +
                "<script type=\"text/javascript\"\n" +
                "src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n" +
                "</script>");
        return out.toString();
    }

    public String getSEARCH() {
        StringBuffer out = new StringBuffer();
        out.append("\n" +
                "<form action=\"http://www.google.com/cse\" id=\"cse-search-box\" target=\"_blank\">\n" +
                "  <div>\n" +
                "    <input type=\"hidden\" name=\"cx\" value=\"partner-pub-9883617370563969:14w8ba-7cin\" />\n" +
                "    <input type=\"hidden\" name=\"ie\" value=\"ISO-8859-1\" />\n" +
                "    <input type=\"text\" name=\"q\" size=\"40\" />\n" +
                "    <input type=\"submit\" name=\"sa\" value=\"Search\" />\n" +
                "  </div>\n" +
                "</form>\n" +
                "<script type=\"text/javascript\" src=\"http://www.google.com/cse/brand?form=cse-search-box&amp;lang=en\"></script>");
        return out.toString();
    }


}
