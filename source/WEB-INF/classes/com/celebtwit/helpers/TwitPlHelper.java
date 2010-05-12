package com.celebtwit.helpers;

import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.Twitpost;
import com.celebtwit.dao.hibernate.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: Apr 11, 2010
 * Time: 1:36:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class TwitPlHelper {

    public static void putIntoThesePlsAndRemoveFromOthers(Twit twit, ArrayList<Integer> plids){
        Logger logger = Logger.getLogger(TwitPlHelper.class);
        twit.setPl01(0);
        twit.setPl02(0);
        twit.setPl03(0);
        twit.setPl04(0);
        twit.setPl05(0);
        for (Iterator<Integer> integerIterator = plids.iterator(); integerIterator.hasNext();) {
            Integer plid = integerIterator.next();
            if (twit.getPl01()==0){
                twit.setPl01(plid);
            } else if (twit.getPl02()==0){
                twit.setPl02(plid);
            } else if (twit.getPl03()==0){
                twit.setPl03(plid);
            } else if (twit.getPl04()==0){
                twit.setPl04(plid);
            } else if (twit.getPl05()==0){
                twit.setPl05(plid);
            } else {
                logger.error("Twit in too many pls");
            }
        }
        try{twit.save();}catch(Exception ex){logger.error("", ex);}
        wipeOutMentionsAndTwitpostsForTwit(twit); 
    }

    public static void addToPl(Twit twit, Pl pl){
        Logger logger = Logger.getLogger(TwitPlHelper.class);
        if (!isTwitACelebInThisPl(twit, pl)){
            if (twit.getPl01()==0){
                twit.setPl01(pl.getPlid());
            } else if (twit.getPl02()==0){
                twit.setPl02(pl.getPlid());
            } else if (twit.getPl03()==0){
                twit.setPl03(pl.getPlid());
            } else if (twit.getPl04()==0){
                twit.setPl04(pl.getPlid());
            } else if (twit.getPl05()==0){
                twit.setPl05(pl.getPlid());
            } else {
                logger.error("Twit in too many pls");
            }
            try{twit.save();}catch(Exception ex){logger.error("", ex);}
            wipeOutMentionsAndTwitpostsForTwit(twit);
        }
    }

    public static Twitpost addTwitpostToPlDontSave(Twitpost twitpost, Pl pl){
        Logger logger = Logger.getLogger(TwitPlHelper.class);
        if (!isTwitpostInThisPl(twitpost, pl)){
            if (twitpost.getPl01()==0){
                twitpost.setPl01(pl.getPlid());
            } else if (twitpost.getPl02()==0){
                twitpost.setPl02(pl.getPlid());
            } else if (twitpost.getPl03()==0){
                twitpost.setPl03(pl.getPlid());
            } else if (twitpost.getPl04()==0){
                twitpost.setPl04(pl.getPlid());
            } else if (twitpost.getPl05()==0){
                twitpost.setPl05(pl.getPlid());
            } else {
                logger.error("Twitpost("+twitpost.getTwitpostid()+") in too many pls");
            }
        }
        return twitpost;
    }

    public static void removeFromPl(Twit twit, Pl pl){
        Logger logger = Logger.getLogger(TwitPlHelper.class);
        if (isTwitACelebInThisPl(twit, pl)){
            if (twit.getPl01()==pl.getPlid()){
                twit.setPl01(0);
            } else if (twit.getPl02()==pl.getPlid()){
                twit.setPl02(0);
            } else if (twit.getPl03()==pl.getPlid()){
                twit.setPl03(0);
            } else if (twit.getPl04()==pl.getPlid()){
                twit.setPl04(0);
            } else if (twit.getPl05()==pl.getPlid()){
                twit.setPl05(0);
            }
            try{twit.save();}catch(Exception ex){logger.error("", ex);}
            wipeOutMentionsAndTwitpostsForTwit(twit);
        }
    }

    public static void wipeOutMentionsAndTwitpostsForTwit(Twit twit){
        Logger logger = Logger.getLogger(TwitPlHelper.class);
        HibernateUtil.getSession().createQuery("delete Mention m where m.twitidceleb='"+twit.getTwitid()+"'").executeUpdate();
        HibernateUtil.getSession().createQuery("delete Twitpost t where t.twitid='"+twit.getTwitid()+"'").executeUpdate();
        twit.setSince_id("1");
        try{twit.save();}catch(Exception ex){logger.error("", ex);}
    }

    public static void syncTwitWithTwitpost(Twit twit){
        Logger logger = Logger.getLogger(TwitPlHelper.class);
        HibernateUtil.getSession().createQuery("UPDATE twitpost SET pl01='"+twit.getPl01()+"', pl02='"+twit.getPl02()+"', pl03='"+twit.getPl03()+"', pl04='"+twit.getPl04()+"', pl05='"+twit.getPl05()+"' WHERE twitid='"+twit.getTwitid()+"'").executeUpdate();
    }

    public static ArrayList<Integer> getPlidsTwitIsIn(Twit twit){
        Logger logger = Logger.getLogger(TwitPlHelper.class);
        ArrayList<Integer> out = new ArrayList<Integer>();
        if (twit.getPl01()>0){ out.add(twit.getPl01()); }
        if (twit.getPl02()>0){ out.add(twit.getPl02()); }
        if (twit.getPl03()>0){ out.add(twit.getPl03()); }
        if (twit.getPl04()>0){ out.add(twit.getPl04()); }
        if (twit.getPl05()>0){ out.add(twit.getPl05()); }
        if (out.size()==0){
            out.add(-1); //So hibernate queries don't crash with empty set
        }
        return out;
    }


    public static boolean isTwitACelebInThisPl(Twit twit, Pl pl){
        if (twit.getPl01()==pl.getPlid()){
            return true;
        } else if (twit.getPl02()==pl.getPlid()){
            return true;
        } else if (twit.getPl03()==pl.getPlid()){
            return true;
        } else if (twit.getPl04()==pl.getPlid()){
            return true;
        } else if (twit.getPl05()==pl.getPlid()){
            return true;
        }
        return false;
    }

    public static boolean isTwitpostInThisPl(Twitpost twitpost, Pl pl){
        if (twitpost.getPl01()==pl.getPlid()){
            return true;
        } else if (twitpost.getPl02()==pl.getPlid()){
            return true;
        } else if (twitpost.getPl03()==pl.getPlid()){
            return true;
        } else if (twitpost.getPl04()==pl.getPlid()){
            return true;
        } else if (twitpost.getPl05()==pl.getPlid()){
            return true;
        }
        return false;
    }


    public static Criterion getCrit(ArrayList<Integer> plidList){
        Criterion pl01Criterion = Restrictions.in("pl01", plidList);
        Criterion pl02Criterion = Restrictions.in("pl02", plidList);
        Criterion pl03Criterion = Restrictions.in("pl03", plidList);
        Criterion pl04Criterion = Restrictions.in("pl04", plidList);
        Criterion pl05Criterion = Restrictions.in("pl05", plidList);
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(pl01Criterion);
        disjunction.add(pl02Criterion);
        disjunction.add(pl03Criterion);
        disjunction.add(pl04Criterion);
        disjunction.add(pl05Criterion);
        return disjunction;
    }


}
