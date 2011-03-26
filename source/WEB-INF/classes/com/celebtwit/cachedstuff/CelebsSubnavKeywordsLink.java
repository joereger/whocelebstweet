package com.celebtwit.cachedstuff;


import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.keywords.KeywordHelpers;

import java.io.Serializable;
import java.util.Calendar;

/**
 * User: Joe Reger Jr
 * Date: Jan 29, 2008
 * Time: 2:03:07 PM
 */
public class CelebsSubnavKeywordsLink implements CachedStuff, Serializable {

    private Calendar refreshedTimestamp;
    private String html;

    private Twit twit;

    public CelebsSubnavKeywordsLink(Twit twit) {
        this.twit = twit;
    }

    public String getKey() {
        return "CelebsSubnavKeywordsLink-twit-"+twit.getTwitid();
    }

    public void refresh(Pl pl) {
        StringBuffer out = new StringBuffer();
        //Start Refresh
        if (KeywordHelpers.getNumberOfKeywordsACelebHasMentioned(twit)>0){
            out.append("&nbsp;&nbsp;&nbsp;&nbsp;");
            out.append("<a href=\"/twitter/"+twit.getTwitterusername()+"/talksabout/\">What @"+twit.getTwitterusername()+" Talks About</a>");
        }
        //End Refresh
        html = out.toString();
        refreshedTimestamp = Calendar.getInstance();
    }

    public Calendar refreshedTimestamp() {
        return refreshedTimestamp;
    }

    public int maxAgeInMinutes() {
        return 60*24*7;
    }

    public String getHtml() {
        return html;
    }
}