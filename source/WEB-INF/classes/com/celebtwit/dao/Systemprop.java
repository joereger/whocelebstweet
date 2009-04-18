package com.celebtwit.dao;

import com.celebtwit.dao.hibernate.BasePersistentClass;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.session.AuthControlled;
import org.apache.log4j.Logger;

import java.util.Iterator;

/**
 * Userrole generated by hbm2java
 */

public class Systemprop extends BasePersistentClass implements java.io.Serializable, AuthControlled {


    // Fields

     private int systempropid;
     private String name;
     private String value;


    public static Systemprop get(int id) {
        Logger logger = Logger.getLogger("com.celebtwit.dao.Systemprop");
        try {
            logger.debug("Systemprop.get(" + id + ") called.");
            Systemprop obj = (Systemprop) HibernateUtil.getSession().get(Systemprop.class, id);
            if (obj == null) {
                logger.debug("Systemprop.get(" + id + ") returning new instance because hibernate returned null.");
                return new Systemprop();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.celebtwit.dao.Systemprop", ex);
            return new Systemprop();
        }
    }

    // Constructors

    /** default constructor */
    public Systemprop() {
    }

    public boolean canRead(User user){
        for (Iterator<Userrole> iterator = user.getUserroles().iterator(); iterator.hasNext();) {
            Userrole userrole = iterator.next();
            if (userrole.getRoleid()== Userrole.SYSADMIN){
                return true;
            }
        }
        return false;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }


    public int getSystempropid() {
        return systempropid;
    }

    public void setSystempropid(int systempropid) {
        this.systempropid = systempropid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
