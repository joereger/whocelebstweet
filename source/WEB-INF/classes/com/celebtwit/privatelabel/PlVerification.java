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
        if (!isCustomdomainUnique(pl)){
            return false;
        }
        if (pl.getName()==null || pl.getName().equals("")){
            return false;   
        }
        return true;
    }

    private static boolean isCustomdomainUnique(Pl pl){
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
        if (!isSpecificCustomdomainUnique(pl, pl.getSisterdomain1())){
            Pagez.getUserSession().setMessage("Sisterdomain1 not unique.");
            return false;
        }
        if (!isSpecificCustomdomainUnique(pl, pl.getSisterdomain2())){
            Pagez.getUserSession().setMessage("Sisterdomain2 not unique.");
            return false;
        }
        if (!isSpecificCustomdomainUnique(pl, pl.getSisterdomain3())){
            Pagez.getUserSession().setMessage("Sisterdomain3 not unique.");
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
        if (1==1){
            List<Pl> pls = HibernateUtil.getSession().createCriteria(Pl.class)
                                               .add(Restrictions.ne("plid", pl.getPlid()))
                                               .add(Restrictions.eq("sisterdomain1", customdomain.toLowerCase()))
                                               .setCacheable(true)
                                               .list();
            if (pls!=null && pls.size()>0){
                return false;
            }
        }
        if (1==1){
            List<Pl> pls = HibernateUtil.getSession().createCriteria(Pl.class)
                                               .add(Restrictions.ne("plid", pl.getPlid()))
                                               .add(Restrictions.eq("sisterdomain2", customdomain.toLowerCase()))
                                               .setCacheable(true)
                                               .list();
            if (pls!=null && pls.size()>0){
                return false;
            }
        }
        if (1==1){
            List<Pl> pls = HibernateUtil.getSession().createCriteria(Pl.class)
                                               .add(Restrictions.ne("plid", pl.getPlid()))
                                               .add(Restrictions.eq("sisterdomain3", customdomain.toLowerCase()))
                                               .setCacheable(true)
                                               .list();
            if (pls!=null && pls.size()>0){
                return false;
            }
        }
        return true;
    }


}
