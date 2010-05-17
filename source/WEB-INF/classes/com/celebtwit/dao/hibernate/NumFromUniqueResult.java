package com.celebtwit.dao.hibernate;

import com.celebtwit.util.Num;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * User: Joe Reger Jr
 * Date: Oct 3, 2007
 * Time: 7:35:25 AM
 */
public class NumFromUniqueResult {

    public static int getInt(String query){
        Logger logger = Logger.getLogger(NumFromUniqueResult.class.getName());
        Object obj = HibernateUtil.getSession().createQuery(query).uniqueResult();
        if (obj!=null && obj instanceof Long){
            try{
                Long lng =  (Long)obj;
                if (Num.isinteger(String.valueOf(lng))){
                    int i = lng.intValue();
                    return i;
                }
            }catch(Exception ex){logger.error("",ex); return 0;}
        }
        if (obj!=null && obj instanceof Integer){
            try{
                Integer integer =  (Integer)obj;
                return integer;
            }catch(Exception ex){logger.error("",ex); return 0;}
        }
        return 0;
    }

    public static int getIntFromNativeSQLQuery(String query){
        Logger logger = Logger.getLogger(NumFromUniqueResult.class.getName());
        Object obj = HibernateUtil.getSession().createSQLQuery(query).uniqueResult();
        if (obj!=null && obj instanceof Long){
            try{
                Long lng =  (Long)obj;
                if (Num.isinteger(String.valueOf(lng))){
                    int i = lng.intValue();
                    return i;
                }
            }catch(Exception ex){logger.error("",ex); return 0;}
        }
        if (obj!=null && obj instanceof Integer){
            try{
                Integer integer =  (Integer)obj;
                return integer;
            }catch(Exception ex){logger.error("",ex); return 0;}
        }
        return 0;
    }

    public static double getDouble(String query){
        Logger logger = Logger.getLogger(NumFromUniqueResult.class.getName());
        Object obj = HibernateUtil.getSession().createQuery(query).uniqueResult();
        if (obj!=null && obj instanceof Double){
            try{
                Double dbl =  (Double)obj;
                if (Num.isdouble(String.valueOf(dbl))){
                    return dbl;
                }
            }catch(Exception ex){logger.error("",ex); return 0;}
        }
        return new Double(0);
    }

    public static Date getDate(String query){
        Logger logger = Logger.getLogger(NumFromUniqueResult.class.getName());
        Object obj = HibernateUtil.getSession().createQuery(query).uniqueResult();
        if (obj!=null && obj instanceof Date){
            try{
                Date dbl =  (Date)obj;
                return dbl;
            }catch(Exception ex){logger.error("",ex); return null;}
        }
        return null;
    }


}
