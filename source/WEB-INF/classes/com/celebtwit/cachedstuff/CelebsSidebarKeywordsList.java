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
            for (Iterator<Keyword> kwIt = keywords.iterator(); kwIt.hasNext();) {
                Keyword keyword = kwIt.next();
                try{
                    out.append("<font class=\"nornamfont\" style=\"font-weight: bold; background: #e6e6e6; padding-left: 2px;\">@"+twit.getTwitterusername()+" Talks About</font><br/>");
                    out.append("<font class=\"normalfont\" style=\"padding-left: 10px;\"><a href=\"/twitter/"+twit.getTwitterusername()+"/talksabout/"+ URLEncoder.encode(keyword.getKeyword(), "UTF-8")+"/\">"+keyword.getKeyword()+"</a></font><br/>");
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
        return 60*24;
    }

    public String getHtml() {
        return html;
    }
}