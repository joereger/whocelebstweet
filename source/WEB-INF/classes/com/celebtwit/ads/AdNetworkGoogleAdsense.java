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

    public String get160x600() {
        StringBuffer out = new StringBuffer();
        out.append("<script type=\"text/javascript\"><!--\n" +
                "            google_ad_client = \"pub-9883617370563969\";\n" +
                "            /* 160x600, Skyscraper */\n" +
                "            google_ad_slot = \"2576530148\";\n" +
                "            google_ad_width = 160;\n" +
                "            google_ad_height = 600;\n" +
                "            //-->\n" +
                "            </script>\n" +
                "            <script type=\"text/javascript\"\n" +
                "            src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n" +
                "            </script>");
        return out.toString();
    }

    public String get336x280() {
        StringBuffer out = new StringBuffer();
        out.append("<script type=\"text/javascript\"><!--\n" +
                "            google_ad_client = \"pub-9883617370563969\";\n" +
                "            /* 336x280, created 8/3/09 */\n" +
                "            google_ad_slot = \"6301770143\";\n" +
                "            google_ad_width = 336;\n" +
                "            google_ad_height = 280;\n" +
                "            //-->\n" +
                "            </script>\n" +
                "            <script type=\"text/javascript\"\n" +
                "            src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n" +
                "            </script>");
        return out.toString();
    }

    public String get160x90() {
        StringBuffer out = new StringBuffer();
        out.append("<script type=\"text/javascript\"><!--\n" +
                "                                google_ad_client = \"pub-9883617370563969\";\n" +
                "                                /* 160x90, link unit whocelebstweet smallfont */\n" +
                "                                google_ad_slot = \"5409295921\";\n" +
                "                                google_ad_width = 160;\n" +
                "                                google_ad_height = 90;\n" +
                "                                //-->\n" +
                "                                </script>\n" +
                "                                <script type=\"text/javascript\"\n" +
                "                                src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n" +
                "                                </script>");
        return out.toString();
    }

    public String get234x60() {
        StringBuffer out = new StringBuffer();
        out.append("<script type=\"text/javascript\"><!--\n" +
                "google_ad_client = \"pub-9883617370563969\";\n" +
                "/* 234x60, WhoCelebs Half Banner */\n" +
                "google_ad_slot = \"5458098190\";\n" +
                "google_ad_width = 234;\n" +
                "google_ad_height = 60;\n" +
                "//-->\n" +
                "</script>\n" +
                "<script type=\"text/javascript\"\n" +
                "src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n" +
                "</script>");
        return out.toString();
    }




    public String get300x250() {
        StringBuffer out = new StringBuffer();
        out.append("<script type=\"text/javascript\"><!--\n" +
                "google_ad_client = \"pub-9883617370563969\";\n" +
                "/* 300x250, trendz medium rectangle */\n" +
                "google_ad_slot = \"2481339787\";\n" +
                "google_ad_width = 300;\n" +
                "google_ad_height = 250;\n" +
                "//-->\n" +
                "</script>\n" +
                "<script type=\"text/javascript\"\n" +
                "src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n" +
                "</script>");
        return out.toString();
    }


    public String getCornerPeel() {
        StringBuffer out = new StringBuffer();
        out.append("");
        return out.toString();
    }

    public String getFloaterBanner() {
        StringBuffer out = new StringBuffer();
        out.append("");
        return out.toString();
    }

    public String get100percentX200() {
        StringBuffer out = new StringBuffer();
        out.append("");
        return out.toString();
    }

    public String get400x600() {
        StringBuffer out = new StringBuffer();
        out.append("");
        return out.toString();
    }

}
