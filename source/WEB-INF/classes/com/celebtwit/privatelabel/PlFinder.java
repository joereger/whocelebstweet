package com.celebtwit.privatelabel;

import com.celebtwit.dao.Pl;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.cache.providers.CacheFactory;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Iterator;

/**
 * User: Joe Reger Jr
 * Date: Jun 17, 2008
 * Time: 8:56:35 AM
 */
public class PlFinder {

    public static String CACHEGROUP = "PlFinder";

    public static Pl find(HttpServletRequest request){
        Logger logger = Logger.getLogger(PlFinder.class);
        logger.debug("request.getServerName()="+request.getServerName());
        String key = request.getServerName().toLowerCase();
        try{
            Object obj = CacheFactory.getCacheProvider().get(key, CACHEGROUP);
            if (obj!=null && (obj instanceof Pl)){
                Pl pl = (Pl)obj;
                logger.debug("Pl found in cache plid="+pl.getPlid()+" name="+pl.getName());
                return pl;
            } else {
                Pl pl = doDatabaseSearching(request);
                CacheFactory.getCacheProvider().put(key, CACHEGROUP, pl);
                return pl;
            }
        } catch (Exception ex){
            logger.error("", ex);
        }
        return Pl.get(1);
    }

    private static Pl doDatabaseSearching(HttpServletRequest request){
        Logger logger = Logger.getLogger(PlFinder.class);
        logger.debug("doDatabaseSearching() called");
        //Search via customdomain1
        List<Pl> plsCustomdomain1 = HibernateUtil.getSession().createCriteria(Pl.class)
                       .add(Restrictions.eq("customdomain1", request.getServerName().toLowerCase()))
                       .setCacheable(true)
                       .list();
        for (Iterator<Pl> plIterator=plsCustomdomain1.iterator(); plIterator.hasNext();) {
            Pl pl=plIterator.next();
            logger.debug("Found pl via customdomain1="+pl.getCustomdomain1()+": plid="+pl.getPlid()+" name="+pl.getName());
            return pl;
        }
        //Search via customdomain2
        List<Pl> plsCustomdomain2 = HibernateUtil.getSession().createCriteria(Pl.class)
                       .add(Restrictions.eq("customdomain2", request.getServerName().toLowerCase()))
                       .setCacheable(true)
                       .list();
        for (Iterator<Pl> plIterator=plsCustomdomain2.iterator(); plIterator.hasNext();) {
            Pl pl=plIterator.next();
            logger.debug("Found pl via customdomain2="+pl.getCustomdomain2()+": plid="+pl.getPlid()+" name="+pl.getName());
            return pl;
        }
        //Search via customdomain3
        List<Pl> plsCustomdomain3 = HibernateUtil.getSession().createCriteria(Pl.class)
                       .add(Restrictions.eq("customdomain3", request.getServerName().toLowerCase()))
                       .setCacheable(true)
                       .list();
        for (Iterator<Pl> plIterator=plsCustomdomain3.iterator(); plIterator.hasNext();) {
            Pl pl=plIterator.next();
            logger.debug("Found pl via customdomain3="+pl.getCustomdomain3()+": plid="+pl.getPlid()+" name="+pl.getName());
            return pl;
        }
        //None found, return the basic
        return Pl.get(1);
    }

    public static boolean isSisterPl(HttpServletRequest request, Pl pl){
        Logger logger = Logger.getLogger(PlFinder.class);
        String domainName = request.getServerName().toLowerCase();
        if (pl.getCustomdomain1().equalsIgnoreCase(domainName)){ return false; }
        if (pl.getCustomdomain2().equalsIgnoreCase(domainName)){ return false; }
        if (pl.getCustomdomain3().equalsIgnoreCase(domainName)){ return false; }
        if (pl.getSisterdomain1().equalsIgnoreCase(domainName)){ return true; }
        if (pl.getSisterdomain2().equalsIgnoreCase(domainName)){ return true; }
        if (pl.getSisterdomain3().equalsIgnoreCase(domainName)){ return true; }
        return false;
    }

}
