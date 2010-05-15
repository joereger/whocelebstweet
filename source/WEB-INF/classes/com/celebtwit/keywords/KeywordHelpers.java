package com.celebtwit.keywords;

import com.celebtwit.dao.Keyword;
import com.celebtwit.dao.Keywordmention;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.helpers.TwitPlHelper;
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
        List<Keywordmention> keywordmentions = HibernateUtil.getSession().createCriteria(Keywordmention.class)
                                       .add(Restrictions.eq("twitid", twit.getTwitid()))
                                       .addOrder(Order.asc("keywordid"))
                                       .setCacheable(true)
                                       .list();
        for (Iterator<Keywordmention> tpIt=keywordmentions.iterator(); tpIt.hasNext();) {
            Keywordmention keywordmention = tpIt.next();
            if (!keywordids.contains(keywordmention.getKeywordid())){
                keywordids.add(keywordmention.getKeywordid());
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

    public static ArrayList<Keywordmention> getCelebMentionsOfKeyword(Twit twit, Keyword keyword){
        ArrayList<Keywordmention> out = new java.util.ArrayList<Keywordmention>();
        List<Keywordmention> kwms = HibernateUtil.getSession().createCriteria(Keywordmention.class)
                                       .add(Restrictions.eq("twitid", twit.getTwitid()))
                                       .add(Restrictions.eq("keywordid", keyword.getKeywordid()))
                                       .addOrder(Order.desc("twitpostid"))
                                       .setCacheable(true)
                                       .list();
        for (Iterator<Keywordmention> tpIt=kwms.iterator(); tpIt.hasNext();) {
            Keywordmention keywordmention = tpIt.next();
            out.add(keywordmention);
        }
        return out;
    }

    public static ArrayList<Twit> getCelebsWhoMentionKeyword(Keyword keyword, Pl pl){
        ArrayList<Twit> out = new java.util.ArrayList<Twit>();
        List<Keywordmention> kwms = HibernateUtil.getSession().createCriteria(Keywordmention.class)
                                       .add(Restrictions.eq("keywordid", keyword.getKeywordid()))
                                       .addOrder(Order.desc("twitpostid"))
                                       .setCacheable(true)
                                       .list();
        for (Iterator<Keywordmention> tpIt=kwms.iterator(); tpIt.hasNext();) {
            Keywordmention keywordmention = tpIt.next();
            Twit twit = Twit.get(keywordmention.getTwitid());
            if (TwitPlHelper.isTwitACelebInThisPl(twit, pl)){
                if (!out.contains(twit)){
                    out.add(twit);
                }
            }
        }
        return out;
    }


}
