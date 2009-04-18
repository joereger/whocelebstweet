package com.celebtwit.helpers;

import com.celebtwit.dao.*;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.htmlui.Pagez;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Iterator;

/**
 * User: Joe Reger Jr
 * Date: Dec 19, 2007
 * Time: 10:28:40 AM
 */
public class DeleteUser {

    public static void delete(User user){
        Logger logger = Logger.getLogger(DeleteUser.class);
        if (user!=null && user.getUserid()>0){
            deleteObj(Userpersistentlogin.class, "userid", user.getUserid());

        }
    }

    private static void deleteObj(Class clazz, String fieldname, int id){
        Logger logger = Logger.getLogger(DeleteUser.class);
        List objects = HibernateUtil.getSession().createCriteria(clazz)
                                           .add(Restrictions.eq(fieldname, id))
                                           .setCacheable(false)
                                           .list();
        for (Iterator iterator=objects.iterator(); iterator.hasNext();) {
            Object o= iterator.next();
            try{
                HibernateUtil.getSession().delete(o);
            } catch (Exception ex){
                logger.error("", ex);
            }
        }

    }





}
