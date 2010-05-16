package com.celebtwit.ads;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: Feb 2, 2010
 * Time: 7:31:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdNetworkAdultFriendFinder implements AdNetwork {

    public static String ADNETWORKNAME = "AdultFriendFinder";

    public String getAdNetworkName() {
        return ADNETWORKNAME;
    }

    public String get160x600() {
        StringBuffer out = new StringBuffer();
        //out.append("<iframe style=\"vertical-align: middle;\" height=\"870\" src=\"http://banners.passion.com/piclist?background_color=%23ffedfb&border_color=%23ffedfb&display=vertical&grid=1x4&js_select=1&link_color=%23626262&models=0&movie=1&p=piclist_links&page=registration&pic_border_color=%23000000&pic_border_width=0&pid=g1200428-pct&rate_img=red_kiss&rollover_header_color=%23ffe3f4&rows=4&show_join_link=0&show_title=0&size=1&spcpromo_creative=July-Spc&text_color=%23cc0066&this_page=banners_member_models_customize&thumb=landscape&title_color=%23000000&spcpromo_creative=July-spc&iframe=1\" scrolling=\"no\" width=\"180\" frameborder=\"0\"></iframe>");
        return out.toString();
    }

    public String get336x280() {
        StringBuffer out = new StringBuffer();
        //out.append("");
        return out.toString();
    }

    public String get160x90() {
        StringBuffer out = new StringBuffer();
        //out.append("<iframe width=\"100%\" scrolling=\"no\" frameborder=\"0\" src=\"http://banners.passion.com/piclist?background_color=transparent&border_color=transparent&display=vertical&grid=1x2&link_color=%230000FF&models=1&movie=0&pic_border_color=%23000000&pic_border_width=0&pid=g1200428-pct&race=0&rollover_header_color=%23FFF6CE&rows=2&show_join_link=0&show_title=0&site=ffadult&size=1&text_color=%23000000&title_color=%23000000&iframe=1\" style=\"vertical-align: middle;\" height=\"446\" allowtransparency=\"true\"></iframe>");
        return out.toString();
    }

    public String get234x60() {
        StringBuffer out = new StringBuffer();
        //out.append("<iframe src=\"http://adultfriendfinder.com/go/page/banners_index_pm_text_links_random?lang=english&pid=g1200428-pct\" scrolling=\"no\" align=\"middle\" width=\"300\" height=\"20\" frameborder=\"no\" marginwidth=\"0\" marginheight=\"0\"></iframe>");
        //out.append("<br/><iframe src=\"http://adultfriendfinder.com/go/page/banners_index_pm_text_links_random?lang=english&pid=g1200428-pct\" scrolling=\"no\" align=\"middle\" width=\"300\" height=\"20\" frameborder=\"no\" marginwidth=\"0\" marginheight=\"0\"></iframe>");
        //out.append("<iframe width=\"100%\" scrolling=\"no\" frameborder=\"0\" src=\"http://banners.passion.com/piclist?background_color=transparent&border_color=transparent&link_color=%230000FF&models=1&movie=0&pic_border_color=%23000000&pic_border_width=0&pid=g1200428-pct&race=0&rollover_header_color=%23FFF6CE&rows=1&show_join_link=0&show_title=0&site=ffadult&size=3&text_color=%23000000&thumb=thumb&title_color=%23000000&iframe=1\" style=\"vertical-align: middle;\" height=\"130\" allowtransparency=\"true\"></iframe>");
        return out.toString();
    }


    public String get300x250() {
        StringBuffer out = new StringBuffer();
        //out.append("<iframe src=\"http://banners.passion.com/go/page/115385_10?pid=g1200428-pct&no_click=1&popunder_off=1&models=0\" width=\"300\" height=\"250\" scrolling=\"no\" frameborder=\"no\" marginwidth=\"0\" marginheight=\"0\" allowtransparency=\"true\"></iframe>");
        return out.toString();
    }

    public String getCornerPeel() {
        StringBuffer out = new StringBuffer();
        //out.append("<script type=\"text/javascript\" src=\"http://banners.passion.com/go/page/js_peel_ads?lang=english&plain_text=1&pid=g1200428-pct&ad_id=CRN_0010170012207510432&ed=l&blayer=top\"></script>");
        return out.toString();
    }


    public String getFloaterBanner() {
        StringBuffer out = new StringBuffer();
        //out.append("<script src=\"http://banners.adultfriendfinder.com/go/page/js_shea_banner?plain_text=1&no_click=1&pid=g1200428-pct&delay=3\"></script>");
        return out.toString();
    }

    public String get100percentX200() {
        StringBuffer out = new StringBuffer();
        //out.append("<iframe width=\"100%\" scrolling=\"no\" frameborder=\"0\" src=\"http://banners.passion.com/piclist?background_color=transparent&border_color=transparent&link_color=%230000FF&models=0&movie=0&pic_border_color=%23000000&pic_border_width=0&pid=g1200428-pct&race=0&rollover_header_color=%23FFF6CE&rows=1&show_join_link=0&site=ffadult&size=3&text_color=%23000000&thumb=bigthumb&title_color=%23000000&spcpromo_creative=July-spc&iframe=1\" style=\"vertical-align: middle;\" height=\"218\" allowtransparency=\"true\"></iframe>");
        return out.toString();
    }

    public String get400x600() {
        StringBuffer out = new StringBuffer();
        //out.append("<iframe src=\"http://banners.passion.com/p/flash_banner.cgi?pid=g1200428-pct&no_click=1&sSiteName=passion&Banner_ID=11\" align=\"MIDDLE\" frameborder=\"No\" width=420 height=620></iframe>");
        return out.toString();
    }
}