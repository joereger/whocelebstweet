package com.celebtwit.privatelabel;

import com.celebtwit.dao.Pl;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.htmlui.Pagez;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.hibernate.HibernateUtil;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * User: Joe Reger Jr
 * Date: Jun 21, 2008
 * Time: 8:56:12 AM
 */
public class PlVerification {

    public static boolean isValid(Pl pl){
        if (!isSubdomainUnique(pl)){
            return false;
        }
        if (!isCustomdomain1Unique(pl)){
            return false;
        }
        if (pl.getName()==null || pl.getName().equals("")){
            return false;   
        }
        return true;
    }

    private static boolean isSubdomainUnique(Pl pl){
        List<Pl> pls = HibernateUtil.getSession().createCriteria(Pl.class)
                                           .add(Restrictions.ne("plid", pl.getPlid()))
                                           .add(Restrictions.ne("customdomain1", ""))
                                           .setCacheable(true)
                                           .list();
        if (pls!=null && pls.size()>0){
            Pagez.getUserSession().setMessage("Pl not unique or customdomain1 is null.");
            return false;
        }
        return true;
    }

    private static boolean isCustomdomain1Unique(Pl pl){
        if (!isSpecificCustomdomainUnique(pl, pl.getCustomdomain1())){
            Pagez.getUserSession().setMessage("Customdomain1 not unique.");
            return false;
        }
        if (!isSpecificCustomdomainUnique(pl, pl.getCustomdomain2())){
            Pagez.getUserSession().setMessage("Customdomain2 not unique.");
            return false;
        }
        if (!isSpecificCustomdomainUnique(pl, pl.getCustomdomain3())){
            Pagez.getUserSession().setMessage("Customdomain3 not unique.");
            return false;
        }
        return true;
    }

    private static boolean isSpecificCustomdomainUnique(Pl pl, String customdomain){
        if (customdomain==null || customdomain.length()==0){
            return true;
        }
        if (1==1){
            List<Pl> pls = HibernateUtil.getSession().createCriteria(Pl.class)
                                               .add(Restrictions.ne("plid", pl.getPlid()))
                                               .add(Restrictions.eq("customdomain1", customdomain.toLowerCase()))
                                               .setCacheable(true)
                                               .list();
            if (pls!=null && pls.size()>0){
                return false;
            }
        }
        if (1==1){
            List<Pl> pls = HibernateUtil.getSession().createCriteria(Pl.class)
                                               .add(Restrictions.ne("plid", pl.getPlid()))
                                               .add(Restrictions.eq("customdomain2", customdomain.toLowerCase()))
                                               .setCacheable(true)
                                               .list();
            if (pls!=null && pls.size()>0){
                return false;
            }
        }
        if (1==1){
            List<Pl> pls = HibernateUtil.getSession().createCriteria(Pl.class)
                                               .add(Restrictions.ne("plid", pl.getPlid()))
                                               .add(Restrictions.eq("customdomain3", customdomain.toLowerCase()))
                                               .setCacheable(true)
                                               .list();
            if (pls!=null && pls.size()>0){
                return false;
            }
        }
        return true;
    }


}
