package com.celebtwit.cachedstuff;


import com.celebtwit.ads.AdNetworkNone;
import com.celebtwit.dao.Keyword;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.Twitpost;
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
        ArrayList<Twitpost> kwms = KeywordHelpers.getCelebMentionsOfKeyword(twit, keyword);
        if (kwms!=null && kwms.size()>0){
            //Vars for list of twitposts
            StringBuffer listOfTwitposts = new StringBuffer();
            //Small Ads
            int maxAdsPerPage = 1;
            int insertAdCount = 0;
            int adsInserted = 0;
            int randomAdInsertionPoint = 8 + Num.randomInt(4);
            //Big Ads
            int maxAdsPerPageBig = 1;
            int insertAdCountBig = 0;
            int adsInsertedBig = 0;
            int randomAdInsertionPointBig = 2 + Num.randomInt(2);
            //Loopamungous (not really)
            for (Iterator<Twitpost> kwmIt = kwms.iterator(); kwmIt.hasNext();) {
                Twitpost twitpost = kwmIt.next();
                //Only insert ad if it's not the none adnetwork
                if(Pagez.getUserSession().getAdNetworkName().indexOf(AdNetworkNone.ADNETWORKNAME)<=-1){
                    insertAdCount++;
                    insertAdCountBig++;
                    //Small Ads
                    if (insertAdCount>=randomAdInsertionPoint && adsInserted<maxAdsPerPage){
                        adsInserted++;
                        insertAdCount = 0;
                        randomAdInsertionPoint = 2 + Num.randomInt(4);
                        listOfTwitposts.append(TwitpostAsHtml.getAdsenseAsTwitpost(380));
                    }
                    //Big Ads
                    if (insertAdCountBig>=randomAdInsertionPointBig && adsInsertedBig<maxAdsPerPageBig){
                        adsInsertedBig++;
                        insertAdCountBig = 0;
                        randomAdInsertionPointBig = 2 + Num.randomInt(4);
                        listOfTwitposts.append(TwitpostAsHtml.getAdsenseAsTwitpostBigForMentions(380));
                    }
                }
                //Teh Tweet
                listOfTwitposts.append(TwitpostAsHtml.get(twitpost, 380));
            }
            //If there hasn't been a big ad yet
            if (adsInsertedBig==0){
                listOfTwitposts.append(TwitpostAsHtml.getAdsenseAsTwitpostBigForMentions(380));
            }
            //Append the list of twitposts
            out.append(listOfTwitposts);
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