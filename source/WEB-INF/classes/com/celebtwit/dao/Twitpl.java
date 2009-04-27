package com.celebtwit.dao;

import com.celebtwit.dao.hibernate.BasePersistentClass;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.session.AuthControlled;

import java.util.Date;

import org.apache.log4j.Logger;


public class Twitpl extends BasePersistentClass implements java.io.Serializable, AuthControlled {

    // Fields
     private int twitplid;
     private int twitid;
     private int plid;




    public static Twitpl get(int id) {
        Logger logger = Logger.getLogger("com.celebtwit.dao.Twitpl");
        try {
            logger.debug("Twitpl.get(" + id + ") called.");
            Twitpl obj = (Twitpl) HibernateUtil.getSession().get(Twitpl.class, id);
            if (obj == null) {
                logger.debug("Twitpl.get(" + id + ") returning new instance because hibernate returned null.");
                return new Twitpl();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.celebtwit.dao.Twitpl", ex);
            return new Twitpl();
        }
    }

    // Constructors

    /** default constructor */
    public Twitpl() {
    }

    public boolean canRead(User user){
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors

    public int getTwitplid() {
        return twitplid;
    }

    public void setTwitplid(int twitplid) {
        this.twitplid=twitplid;
    }

    public int getTwitid() {
        return twitid;
    }

    public void setTwitid(int twitid) {
        this.twitid=twitid;
    }

    public int getPlid() {
        return plid;
    }

    public void setPlid(int plid) {
        this.plid=plid;
    }
}