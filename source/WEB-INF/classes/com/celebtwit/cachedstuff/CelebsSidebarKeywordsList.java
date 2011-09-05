package com.celebtwit.cachedstuff;


import com.celebtwit.dao.Keyword;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.keywords.KeywordHelpers;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * User: Joe Reger Jr
 * Date: Jan 29, 2008
 * Time: 2:03:07 PM
 */
public class CelebsSidebarKeywordsList implements CachedStuff, Serializable {

    private Calendar refreshedTimestamp;
    private String html;

    private Twit twit;

    public CelebsSidebarKeywordsList(Twit twit) {
        this.twit = twit;
    }

    public String getKey() {
        return "CelebsSidebarKeywordsList-twit-"+twit.getTwitid();
    }

    public void refresh(Pl pl) {
        Logger logger = Logger.getLogger(this.getClass().getName());
        StringBuffer out = new StringBuffer();
        //Start Refresh
        ArrayList<Keyword> keywords = KeywordHelpers.getKeywordsACelebHasMentioned(twit);
        if (keywords!=null && keywords.size()>0){
            out.append("<font class=\"normalfont\" style=\"font-weight: bold; background: #e6e6e6; padding-left: 2px;\">@"+twit.getTwitterusername()+" Talks About</font><br/>");
            for (Iterator<Keyword> kwIt = keywords.iterator(); kwIt.hasNext();) {
                Keyword keyword = kwIt.next();
                try{
                    out.append("<a href=\"/twitter/"+twit.getTwitterusername()+"/talksabout/"+ URLEncoder.encode(keyword.getKeyword(), "UTF-8")+"/\"><div style=\"padding-left: 11px; font-family:arial,sans-serif; font-size:11px; line-height:13px; color:#0000FF; font-weight:normal; text-decoration:underline;\">"+keyword.getKeyword()+"</div></a>");
                } catch (Exception ex){
                    logger.debug("", ex);
                }


            }
        }
        //End Refresh
        html = out.toString();
        refreshedTimestamp = Calendar.getInstance();
    }

    public Calendar refreshedTimestamp() {
        return refreshedTimestamp;
    }

    public int maxAgeInMinutes() {
        return 60*24*31;
    }

    public String getHtml() {
        return html;
    }
}