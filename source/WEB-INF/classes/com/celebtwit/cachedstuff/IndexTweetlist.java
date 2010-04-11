package com.celebtwit.cachedstuff;


import com.celebtwit.ads.AdNetworkNone;
import com.celebtwit.dao.Pl;
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
public class IndexTweetlist implements CachedStuff, Serializable {

    private Calendar refreshedTimestamp;
    private String html;

    private int page;

    public IndexTweetlist(int page) {
        this.page = page;

    }

    public String getKey() {
        return "IndexTweetlist-page"+page;
    }

    public void refresh(Pl pl) {
        StringBuffer out = new StringBuffer();
        int perPage = 25;
        int firstResult = page * perPage;
        if (page<=1){ firstResult = 0; }
        int insertAdCount = 0;
        int randomAdInsertionPoint = 2 + Num.randomInt(4);
        List<Twitpost> twitposts = HibernateUtil.getSession().createCriteria(Twitpost.class)
            .addOrder(Order.desc("created_at"))
            .createCriteria("twit")
            .createCriteria("twitpls")
            .add(Restrictions.eq("plid", pl.getPlid()))
            .setMaxResults(perPage)
            .setFirstResult(firstResult)
            .setCacheable(true)
            .list();
        for (Iterator<Twitpost> tpIt=twitposts.iterator(); tpIt.hasNext();) {
            Twitpost twitpost=tpIt.next();
            //Only insert ad if it's not the none adnetwork
            if(!Pagez.getUserSession().getAdNetworkName().equals(AdNetworkNone.ADNETWORKNAME)){
                insertAdCount++;
                if (insertAdCount>=randomAdInsertionPoint){
                    //insertAdCount = 0;
                    //randomAdInsertionPoint = Num.randomInt(10);
                    randomAdInsertionPoint = 10000;
                    out.append(TwitpostAsHtml.getAdsenseAsTwitpost(410));
                }
            }
            out.append(TwitpostAsHtml.get(twitpost, 410));
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