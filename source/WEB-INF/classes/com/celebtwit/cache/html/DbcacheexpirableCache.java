package com.celebtwit.cache.html;

import com.celebtwit.cachedstuff.CacheLogger;
import com.celebtwit.dao.Dbcacheexpirable;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.util.Time;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Dbcache cache implementation
 * This stores cached items in a separate database as defined in HibernateUtilDbcache.java
 */
public class DbcacheexpirableCache {


    public DbcacheexpirableCache(){

    }

    public static Object get(String key, String group) {
        return get(key, group, true);
    }


    public static Object get(String key, String group, boolean deleteIfExpired) {
        Logger logger = Logger.getLogger(Dbcacheexpirable.class);
        try{
            List<Dbcacheexpirable> dbcaches = HibernateUtil.getSession().createCriteria(Dbcacheexpirable.class)
                                               .add(Restrictions.eq("grp", group))
                                               .add(Restrictions.eq("keyname", key))
                                               .setCacheable(true)
                                               .list();
            if (dbcaches!=null && dbcaches.size()>0){
                Dbcacheexpirable dbcache = dbcaches.get(0);
                Object obj = dbcache.getVal();
                //If it's expired delete and deleteIfExpired=true then return null
                if (deleteIfExpired && dbcache.getExpirationdate().before(Calendar.getInstance().getTime())){
                    //logger.debug("object expired and deleteIfExpired==true");
                    //Return null because it's expired
                    //Note that I'm no longer deleting... I'm letting the cleanup scheduled process do that
                    //dbcache.delete();
                    CacheLogger.log("Dbcacheexpirable", group, key, "expired", false);
                    return null;
                }
                CacheLogger.log("Dbcacheexpirable", group, key, "", true);
                return obj;
            }
        } catch (Exception ex){
            logger.error("", ex);
        }
        CacheLogger.log("Dbcacheexpirable", group, key, "returning null by default", false);
        return null;
    }

    public static void put(String key, String group, Object obj, Date expirationdate) {
        Logger logger = Logger.getLogger(Dbcacheexpirable.class);
        try{
            //Delete any existing entries from the same group/key
            try{
                List<Dbcacheexpirable> dbcaches = HibernateUtil.getSession().createCriteria(Dbcacheexpirable.class)
                                                   .add(Restrictions.eq("grp", group))
                                                   .add(Restrictions.eq("keyname", key))
                                                   .setCacheable(true)
                                                   .list();
                if (dbcaches!=null && dbcaches.size()>0){
                    for (Iterator<Dbcacheexpirable> dbcacheIterator=dbcaches.iterator(); dbcacheIterator.hasNext();) {
                        Dbcacheexpirable dbcache=dbcacheIterator.next();
                        dbcache.delete();
                    }
                }
            } catch (Exception ex){
                logger.error("Problem deleting dbcache.delete", ex);
            }
            //Save the value
            Dbcacheexpirable dbcacheNew = new Dbcacheexpirable();
            dbcacheNew.setDate(new Date());
            dbcacheNew.setExpirationdate(expirationdate);
            dbcacheNew.setGrp(group);
            dbcacheNew.setKeyname(key);
            dbcacheNew.setVal(obj);
            dbcacheNew.save();
        } catch (Exception ex){
            logger.error("bottom of put()", ex);
        }
    }

    public static void purgeStaleItems(){
        Logger logger = Logger.getLogger(Dbcacheexpirable.class);
        try{
            //X days grace period on cleanup
            int gracePeriod = 10;
            Calendar endOfGracePeriod = Time.xDaysAgoEnd(Calendar.getInstance(), (-1)*gracePeriod);
            String now = Time.dateformatfordb(endOfGracePeriod);
            HibernateUtil.getSession().createQuery("delete Dbcacheexpirable d where d.dbcacheexpirableid>0 and d.expirationdate<'"+now+"'").executeUpdate();
        } catch (Exception ex){logger.error("", ex);}

        
    }

    public static void flush() {
        Logger logger = Logger.getLogger(Dbcacheexpirable.class);
        try{
            String emptyString = "";
            HibernateUtil.getSession().createQuery("delete Dbcacheexpirable d where d.dbcacheexpirableid>0"+emptyString).executeUpdate();
        } catch (Exception ex){
            logger.error("", ex);
        }
    }

    public static void flush(String group) {
        Logger logger = Logger.getLogger(Dbcacheexpirable.class);
        try{
            HibernateUtil.getSession().createQuery("delete Dbcacheexpirable d where d.dbcacheexpirableid>0 and d.grp='"+group+"'").executeUpdate();
        } catch (Exception ex){
            logger.error("", ex);
        }
    }

    public static void flush(String key, String group) {
        Logger logger = Logger.getLogger(Dbcacheexpirable.class);
        try{
            HibernateUtil.getSession().createQuery("delete Dbcacheexpirable d where d.dbcacheexpirableid>0 and d.grp='"+group+"' and d.keyname='"+key+"'").executeUpdate();
        } catch (Exception ex){
            logger.error("", ex);
        }
    }

    public static Date expireIn5Min(){
        return Time.xMinutesAgoEnd(Calendar.getInstance(), -5).getTime();
    }

    public static Date expireIn10Min(){
        return Time.xMinutesAgoEnd(Calendar.getInstance(), -10).getTime();
    }

    public static Date expireIn15Min(){
        return Time.xMinutesAgoEnd(Calendar.getInstance(), -15).getTime();
    }

    public static Date expireIn30Min(){
        return Time.xMinutesAgoEnd(Calendar.getInstance(), -30).getTime();
    }

    public static Date expireIn1Hr(){
        return Time.xMinutesAgoEnd(Calendar.getInstance(), -60).getTime();
    }

    public static Date expireIn2Hrs(){
        return Time.xMinutesAgoEnd(Calendar.getInstance(), -120).getTime();
    }

    public static Date expireIn3Hrs(){
        return Time.xMinutesAgoEnd(Calendar.getInstance(), -180).getTime();
    }

    public static Date expireIn6Hrs(){
        return Time.xMinutesAgoEnd(Calendar.getInstance(), -360).getTime();
    }

    public static Date expireIn12Hrs(){
        return Time.xMinutesAgoEnd(Calendar.getInstance(), -720).getTime();
    }

    public static Date expireIn24Hrs(){
        return Time.xMinutesAgoEnd(Calendar.getInstance(), -1440).getTime();
    }


    public static Date expireIn48Hrs(){
        return Time.xMinutesAgoEnd(Calendar.getInstance(), -1440*2).getTime();
    }




}