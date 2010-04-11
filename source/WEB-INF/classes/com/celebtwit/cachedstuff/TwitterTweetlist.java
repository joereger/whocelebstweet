package com.celebtwit.cachedstuff;


import com.celebtwit.ads.AdNetworkNone;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.Twitpost;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.helpers.TwitpostAsHtml;
import com.celebtwit.htmlui.Pagez;
import com.celebtwit.util.Num;
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

    public TwitterTweetlist(Twit twit, int page) {
        this.twit = twit;
        this.page = page;
    }

    public String getKey() {
        return "TwitterTweetlist-twitid"+twit.getTwitid()+"-page"+page;
    }

    public void refresh(Pl pl) {
        StringBuffer out = new StringBuffer();
        int perPage = 20;
        int maxAdsPerPage = 3;
        int firstResult = page * perPage;
        if (page<=1){ firstResult = 0; }
        int insertAdCount = 0;
        int adsInserted = 0;
        int randomAdInsertionPoint = 2 + Num.randomInt(4);
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
            if(!Pagez.getUserSession().getAdNetworkName().equals(AdNetworkNone.ADNETWORKNAME)){
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
        //End Refresh
        html = out.toString();
        refreshedTimestamp = Calendar.getInstance();
    }

    public Calendar refreshedTimestamp() {
        return refreshedTimestamp;
    }

    public int maxAgeInMinutes() {
        return 60;
    }

    public String getHtml() {
        return html;
    }
}