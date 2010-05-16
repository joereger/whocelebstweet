package com.celebtwit.keywords;

import com.celebtwit.dao.*;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.helpers.TwitPlHelper;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: May 15, 2010
 * Time: 12:34:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class KeywordHelpers {


    public static Keyword getKeywordByString(String keyword){
        if (keyword!=null){
            List<Keyword> keywords = HibernateUtil.getSession().createCriteria(Keyword.class)
                                           .add(Restrictions.eq("keyword", keyword.toLowerCase()))
                                           .setCacheable(true)
                                           .list();
            for (Iterator<Keyword> tpIt=keywords.iterator(); tpIt.hasNext();) {
                Keyword kw=tpIt.next();
                return kw;
            }
        }
        return null;
    }

    private static ArrayList<Integer> getKeywordidsACelebHasMentioned(Twit twit){
        ArrayList<Integer> keywordids = new java.util.ArrayList<Integer>();
        List<Keywordtwit> keywordtwits = HibernateUtil.getSession().createCriteria(Keywordtwit.class)
                                       .add(Restrictions.eq("twitid", twit.getTwitid()))
                                       .addOrder(Order.desc("numberoftwitposts"))
                                       .setCacheable(true)
                                       .list();
        for (Iterator<Keywordtwit> tpIt=keywordtwits.iterator(); tpIt.hasNext();) {
            Keywordtwit keywordtwit = tpIt.next();
            if (!keywordids.contains(keywordtwit.getKeywordid())){
                keywordids.add(keywordtwit.getKeywordid());
            }
        }
        return keywordids;
    }

    public static ArrayList<Keyword> getKeywordsACelebHasMentioned(Twit twit){
        ArrayList<Keyword> out = new ArrayList<Keyword>();
        ArrayList<Integer> keywordids = getKeywordidsACelebHasMentioned(twit);
        for (Iterator<Integer> integerIterator = keywordids.iterator(); integerIterator.hasNext();) {
            Integer keywordid = integerIterator.next();
            Keyword keyword = Keyword.get(keywordid);
            out.add(keyword);
        }
        return out;
    }

    public static int getNumberOfKeywordsACelebHasMentioned(Twit twit){
        return getKeywordidsACelebHasMentioned(twit).size();
    }

    public static ArrayList<Twitpost> getCelebMentionsOfKeyword(Twit twit, Keyword keyword){
        Logger logger = Logger.getLogger(KeywordHelpers.class);
        ArrayList<Twitpost> out = new ArrayList<Twitpost>();
        List twitposts = HibernateUtil.getSession().createSQLQuery("SELECT * FROM twitpost WHERE MATCH(post) AGAINST('"+keyword.getKeyword()+"') AND twitid='"+twit.getTwitid()+"'").addEntity(Twitpost.class).list();
        for (Iterator iterator=twitposts.iterator(); iterator.hasNext();) {
            Twitpost twitpost = (Twitpost)iterator.next();
            out.add(twitpost);
        }
        return out;
    }

    public static ArrayList<Twit> getCelebsWhoMentionKeyword(Keyword keyword, Pl pl){
        ArrayList<Twit> out = new java.util.ArrayList<Twit>();
        List<Keywordtwit> kwms = HibernateUtil.getSession().createCriteria(Keywordtwit.class)
                                       .add(Restrictions.eq("keywordid", keyword.getKeywordid()))
                                       .addOrder(Order.desc("numberoftwitposts"))
                                       .setCacheable(true)
                                       .list();
        for (Iterator<Keywordtwit> tpIt=kwms.iterator(); tpIt.hasNext();) {
            Keywordtwit keywordtwit = tpIt.next();
            Twit twit = Twit.get(keywordtwit.getTwitid());
            if (TwitPlHelper.isTwitACelebInThisPl(twit, pl)){
                if (!out.contains(twit)){
                    out.add(twit);
                }
            }
        }
        return out;
    }


}
