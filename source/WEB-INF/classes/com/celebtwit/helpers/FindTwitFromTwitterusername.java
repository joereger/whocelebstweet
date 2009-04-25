package com.celebtwit.helpers;

import com.celebtwit.dao.Twit;
import com.celebtwit.dao.hibernate.HibernateUtil;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Iterator;

/**
 * User: Joe Reger Jr
 * Date: Apr 21, 2009
 * Time: 5:57:33 PM
 */
public class FindTwitFromTwitterusername {

    public static Twit find(String twitterusername){
        twitterusername = cleanTwitterusername(twitterusername);
        List<Twit> twits = HibernateUtil.getSession().createCriteria(Twit.class)
                                           .add(Restrictions.eq("twitterusername", twitterusername))
                                           .setCacheable(true)
                                           .list();
        for (Iterator<Twit> twitIterator=twits.iterator(); twitIterator.hasNext();) {
            Twit twit=twitIterator.next();
            return twit;
        }
        return null;
    }

    public static String cleanTwitterusername(String twitterusername){
        if (twitterusername==null){
            return "";
        }
        if (twitterusername.equals("")){
            return "";    
        }
        twitterusername = twitterusername.trim();
        if (twitterusername.substring(twitterusername.length()-1, twitterusername.length()).equals("/")){
            twitterusername = twitterusername.substring(0, twitterusername.length()-1);
        }
        if (twitterusername.substring(twitterusername.length()-1, twitterusername.length()).equals("\\")){
            twitterusername = twitterusername.substring(0, twitterusername.length()-1);
        }
        return twitterusername;
    }


}
