package com.celebtwit.cachedstuff;


import com.celebtwit.ads.AdNetworkNone;
import com.celebtwit.dao.*;
import com.celebtwit.helpers.TwitpostAsHtml;
import com.celebtwit.htmlui.Pagez;
import com.celebtwit.keywords.KeywordHelpers;
import com.celebtwit.util.Num;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * User: Joe Reger Jr
 * Date: Jan 29, 2008
 * Time: 2:03:07 PM
 */
public class CelebMentionsKeyword implements CachedStuff, Serializable {

    private Calendar refreshedTimestamp;
    private String html;

    private Twit twit;
    private Keyword keyword;

    public CelebMentionsKeyword(Twit twit, Keyword keyword) {
        this.twit = twit;
        this.keyword = keyword;
    }

    public String getKey() {
        return "CelebMentionsKeyword-twitid-"+twit.getTwitid()+"-keywordid-"+keyword.getKeywordid();
    }

    public void refresh(Pl pl) {
        StringBuffer out = new StringBuffer();
        //Start Refresh
        if (KeywordHelpers.getNumberOfKeywordsACelebHasMentioned(twit)>0){
            ArrayList<Twitpost> kwms = KeywordHelpers.getCelebMentionsOfKeyword(twit, keyword);
            int maxAdsPerPage = 3;
            int insertAdCount = 0;
            int adsInserted = 0;
            int randomAdInsertionPoint = 2 + Num.randomInt(4);
            for (Iterator<Twitpost> kwmIt = kwms.iterator(); kwmIt.hasNext();) {
                Twitpost twitpost = kwmIt.next();
                //Only insert ad if it's not the none adnetwork
                if(Pagez.getUserSession().getAdNetworkName().indexOf(AdNetworkNone.ADNETWORKNAME)<=-1){
                    insertAdCount++;
                    if (insertAdCount>=randomAdInsertionPoint && adsInserted<maxAdsPerPage){
                        adsInserted++;
                        insertAdCount = 0;
                        randomAdInsertionPoint = 2 + Num.randomInt(4);
                        //randomAdInsertionPoint = 10000;
                        out.append(TwitpostAsHtml.getAdsenseAsTwitpost(380));
                    }
                }
                out.append(TwitpostAsHtml.get(twitpost, 380));
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