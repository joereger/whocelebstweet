package com.celebtwit.cachedstuff;


import com.celebtwit.dao.Keyword;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.htmlui.Pagez;
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
public class KeywordPage implements CachedStuff, Serializable {

    private Calendar refreshedTimestamp;
    private String html;

    private Keyword keyword;

    public KeywordPage(Keyword keyword) {
        this.keyword = keyword;
    }

    public String getKey() {
        return "KeywordPage-keywordid-"+keyword.getKeywordid();
    }

    public void refresh(Pl pl) {
        Logger logger = Logger.getLogger(this.getClass().getName());
        StringBuffer out = new StringBuffer();
        //Start Refresh
        try{
            out.append("<ul>");
            ArrayList<Twit> twits = KeywordHelpers.getCelebsWhoMentionKeyword(keyword, Pagez.getUserSession().getPl());
            for (Iterator<Twit> tIt = twits.iterator(); tIt.hasNext();) {
                Twit twit = tIt.next();
                out.append("<li><a href=\"/twitter/"+twit.getTwitterusername()+"/talksabout/"+ URLEncoder.encode(keyword.getKeyword(), "UTF -8")+"/\">"+twit.getRealname()+"</a></li>");
            }
            out.append("</ul>");
        } catch (Exception ex){
            logger.error("", ex);
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