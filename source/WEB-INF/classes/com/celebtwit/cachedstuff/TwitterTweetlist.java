package com.celebtwit.cachedstuff;


import com.celebtwit.ads.AdNetworkNone;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.Twitpost;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.helpers.TwitpostAsHtml;
import com.celebtwit.util.Num;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * User: Joe Reger Jr
 * Date: Jan 29, 2008
 * Time: 2:03:07 PM
 */
public class TwitterTweetlist implements CachedStuff, Serializable {

    private Calendar refreshedTimestamp;
    private String html;

    private Twit twit;
    private int page;
    private String adnetworkname;

    public TwitterTweetlist(Twit twit, int page, String adnetworkname) {
        this.twit = twit;
        this.page = page;
        this.adnetworkname = adnetworkname;
    }

    public String getKey() {
        return "TwitterTweetlist-twitid"+twit.getTwitid()+"-page"+page+"-adnetworkname"+adnetworkname;
    }

    public void refresh(Pl pl) {
        Logger logger = Logger.getLogger(this.getClass().getName());
        StringBuffer out = new StringBuffer();
        int perPage = 20;
        int firstResult = page * perPage;
        if (page<=1){ firstResult = 0; }
        //Small Ads
        int maxAdsPerPage = 2;
        int insertAdCount = 0;
        int adsInserted = 0;
        int randomAdInsertionPoint = 8 + Num.randomInt(4);
        //Big Ads
        int maxAdsPerPageBig = 1;
        int insertAdCountBig = 0;
        int adsInsertedBig = 0;
        int randomAdInsertionPointBig = 3 + Num.randomInt(2);
        List<Twitpost> twitposts = HibernateUtil.getSession().createCriteria(Twitpost.class)
                                       .add(Restrictions.eq("twitid", twit.getTwitid()))
                                       .addOrder(Order.desc("created_at"))
                                       .setFirstResult(firstResult)
                                       .setMaxResults(perPage)
                                       .setCacheable(true)
                                       .list();
        for (Iterator<Twitpost> tpIt=twitposts.iterator(); tpIt.hasNext();) {
            Twitpost twitpost=tpIt.next();
            //Only insert ad if it's not the none adnetwork
            if(!adnetworkname.equalsIgnoreCase(AdNetworkNone.ADNETWORKNAME)){
                insertAdCount++;
                insertAdCountBig++;
                //Small Ads
                if (1==2 && insertAdCount>=randomAdInsertionPoint && adsInserted<maxAdsPerPage){
                    adsInserted++;
                    insertAdCount = 0;
                    randomAdInsertionPoint = 2 + Num.randomInt(4);
                    out.append(TwitpostAsHtml.getAdsenseAsTwitpost(380));
                    //logger.debug("just added small Ad to TweetList");
                }
                //Big Ads
                if (1==2 && insertAdCountBig>=randomAdInsertionPointBig && adsInsertedBig<maxAdsPerPageBig){
                    adsInsertedBig++;
                    insertAdCountBig = 0;
                    randomAdInsertionPointBig = 2 + Num.randomInt(4);
                    out.append(TwitpostAsHtml.getAdsenseAsTwitpostBig(380));
                    //logger.debug("just added big Ad to TweetList");
                }
            }
            //The tweet!
            out.append(TwitpostAsHtml.get(twitpost, 380));
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