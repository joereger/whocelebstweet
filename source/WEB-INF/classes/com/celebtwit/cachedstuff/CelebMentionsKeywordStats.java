package com.celebtwit.cachedstuff;


import com.celebtwit.dao.Keyword;
import com.celebtwit.dao.Keywordtwit;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.dao.hibernate.NumFromUniqueResult;
import com.celebtwit.util.DateDiff;
import com.celebtwit.util.Time;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * User: Joe Reger Jr
 * Date: Jan 29, 2008
 * Time: 2:03:07 PM
 */
public class CelebMentionsKeywordStats implements CachedStuff, Serializable {

    private Calendar refreshedTimestamp;
    private String html;

    private Twit twit;
    private Keyword keyword;

    public CelebMentionsKeywordStats(Twit twit, Keyword keyword) {
        this.twit = twit;
        this.keyword = keyword;
    }

    public String getKey() {
        return "CelebMentionsKeywordStats-twitid-"+twit.getTwitid()+"-keywordid-"+keyword.getKeywordid();
    }

    public void refresh(Pl pl) {
        Logger logger = Logger.getLogger(this.getClass().getName());
        StringBuffer out = new StringBuffer();
        try{
            //Min/Max dates of twitposts from this user
            Date minDate = NumFromUniqueResult.getDate("SELECT min(created_at) FROM Twitpost WHERE twitid='"+twit.getTwitid()+"'");
            Date maxDate = NumFromUniqueResult.getDate("SELECT max(created_at) FROM Twitpost WHERE twitid='"+twit.getTwitid()+"'");
            logger.debug("minDate="+ Time.dateformatcompactwithtime(Time.getCalFromDate(minDate)));
            logger.debug("maxDate="+ Time.dateformatcompactwithtime(Time.getCalFromDate(maxDate)));
            //Number of weeks this min/max covers
            int numWeeks = DateDiff.dateDiff("week", Time.getCalFromDate(maxDate), Time.getCalFromDate(minDate));
            logger.debug("numWeeks="+numWeeks);
            if (numWeeks==0){
                numWeeks = 1;
                logger.debug("numWeeks massaged to 1");
            }
            //Number of times twit has mentioned keyword
            int numberoftwitposts = 0;
            List<Keywordtwit> kwms = HibernateUtil.getSession().createCriteria(Keywordtwit.class)
                                           .add(Restrictions.eq("keywordid", keyword.getKeywordid()))
                                           .add(Restrictions.eq("twitid", twit.getTwitid()))
                                           .addOrder(Order.asc("numberoftwitposts"))
                                           .setCacheable(true)
                                           .list();
            for (Iterator<Keywordtwit> tpIt=kwms.iterator(); tpIt.hasNext();) {
                Keywordtwit keywordtwit = tpIt.next();
                numberoftwitposts = keywordtwit.getNumberoftwitposts();
            }
            logger.debug("numberoftwitposts="+numberoftwitposts);
            //Maths in the house
            double mentionsperweek = new Double(numberoftwitposts)/new Double(numWeeks);
            logger.debug("mentionsperweek="+mentionsperweek);
            //Format maths
            NumberFormat formatter = new DecimalFormat("#######.##");
            String mentionsperweekStr = formatter.format(mentionsperweek);
            logger.debug("mentionsperweekStr="+mentionsperweekStr);
            //Get celeb name if possible
            String nameForScreen = "@"+twit.getTwitterusername();
            if (twit.getIsceleb()){
                nameForScreen = twit.getRealname();
            }
            //Output
            out.append(nameForScreen+" has said '"+keyword.getKeyword()+"' a total of "+numberoftwitposts+" times (~"+mentionsperweekStr+" times per week.) ");
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
        return 60*24*7;
    }

    public String getHtml() {
        return html;
    }
}