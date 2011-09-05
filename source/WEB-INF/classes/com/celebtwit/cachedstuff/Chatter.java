package com.celebtwit.cachedstuff;


import com.celebtwit.dao.Mention;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twitpost;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.helpers.TwitpostAsHtml;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * User: Joe Reger Jr
 * Date: Jan 29, 2008
 * Time: 2:03:07 PM
 */
public class Chatter implements CachedStuff, Serializable {

    private Calendar refreshedTimestamp;
    private String html;

    private int twitid1;
    private int twitid2;
    private String adnetworkname;

    public Chatter(int twitid1, int twitid2, String adnetworkname) {
        this.twitid1 = twitid1;
        this.twitid2 = twitid2;
        this.adnetworkname = adnetworkname;
    }

    public String getKey() {
        return "Chatter-twitid1"+twitid1+"-twitid2"+twitid2+"-adnetworkname"+adnetworkname;
    }

    public void refresh(Pl pl) {
        Logger logger = Logger.getLogger(this.getClass().getName());
        StringBuffer out = new StringBuffer();
//        Twit twit1 = Twit.get(twitid1);
//        Twit twit2 = Twit.get(twitid2);
        String empty = "";
        List<Mention> mentions = HibernateUtil.getSession().createQuery(empty + "from Mention where (twitidceleb='"+twitid1+"' and twitidmentioned='"+twitid2+"') or (twitidceleb='"+twitid2+"' and twitidmentioned='"+twitid1+"')").setMaxResults(3000).list();
        ArrayList<Integer> twitpostids = new ArrayList<Integer>();
        for (Iterator<Mention> menIt=mentions.iterator(); menIt.hasNext();) {
            Mention mention=menIt.next();
            twitpostids.add(mention.getTwitpostid());
        }
        if (twitpostids.size()>0){
            List<Twitpost> twitposts = HibernateUtil.getSession().createCriteria(Twitpost.class)
                                               .add(Restrictions.in("twitpostid", twitpostids))
                                               .addOrder(Order.desc("twitterguid"))
                                               .setCacheable(true)
                                               .list();
            for (Iterator<Twitpost> tpIt=twitposts.iterator(); tpIt.hasNext();) {
                Twitpost twitpost=tpIt.next();
                String paddingLeft = "-250";
                if (twitpost.getTwit().getTwitid()==twitid2){
                    paddingLeft = "250";
                }
                out.append("<div style=\"margin-left: "+paddingLeft+"px;\">"+TwitpostAsHtml.get(twitpost, 300)+"</div>");
            }
        } else {
            out.append("<br/><font class=\"normalfont\">No chatter between them!</font>");
        }
        //End Refresh
        html = out.toString();
        refreshedTimestamp = Calendar.getInstance();
    }

    public Calendar refreshedTimestamp() {
        return refreshedTimestamp;
    }

    public int maxAgeInMinutes() {
        return 60*24*14;
    }

    public String getHtml() {
        return html;
    }
}