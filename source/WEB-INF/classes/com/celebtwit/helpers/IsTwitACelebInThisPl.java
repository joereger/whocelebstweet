package com.celebtwit.helpers;

import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.Twitpl;
import com.celebtwit.dao.hibernate.HibernateUtil;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: Feb 2, 2010
 * Time: 7:17:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class IsTwitACelebInThisPl {

    public static boolean isTwitACelebInThisPl(Twit twit, Pl pl){
        List<Twitpl> twitpls = HibernateUtil.getSession().createCriteria(Twitpl.class)
                                                   .add(Restrictions.eq("twitid", twit.getTwitid()))
                                                   .add(Restrictions.eq("plid", pl.getPlid()))
                                                   .setCacheable(true)
                                                   .list();
        if (twitpls!=null && twitpls.size()>0){
            return true;
        }
//        for (Iterator<Twitpl> it=twitpls.iterator(); it.hasNext();) {
//            Twitpl twitpl=it.next();
//
//        }
        return false;
    }

}
