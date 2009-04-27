package com.celebtwit.dao;

import com.celebtwit.cache.providers.CacheFactory;
import com.celebtwit.dao.hibernate.BasePersistentClass;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.privatelabel.PlFinder;
import com.celebtwit.session.AuthControlled;
import com.celebtwit.util.GeneralException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;



public class Pl extends BasePersistentClass implements java.io.Serializable, AuthControlled {


    // Fields
    private int plid;
    private String name;
    private String celebiscalled;
    private String customdomain1;
    private String customdomain2;
    private String customdomain3;
    private String twitterusername;
    private String twitterpassword;



    public static Pl get(int id) {
        Logger logger = Logger.getLogger("com.dneero.dao.Pl");
        try {
            logger.debug("Pl.get(" + id + ") called.");
            Pl obj = (Pl) HibernateUtil.getSession().get(Pl.class, id);
            if (obj == null) {
                logger.debug("Pl.get(" + id + ") returning new instance because hibernate returned null.");
                return new Pl();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.dneero.dao.Pl", ex);
            return new Pl();
        }
    }

    public void save() throws GeneralException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        //Do the main save
        super.save();
        //Must clear cache
        try{
            CacheFactory.getCacheProvider().flush(PlFinder.CACHEGROUP);
        }catch(Exception ex){logger.error("",ex);}
    }

    public void delete() throws HibernateException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        //Do the main delete
        super.delete();
        //Must clear cache
        try{
            CacheFactory.getCacheProvider().flush(PlFinder.CACHEGROUP);
        }catch(Exception ex){logger.error("",ex);}
    }



    // Constructors

    /** default constructor */
    public Pl() {
    }

    public boolean canRead(User user){
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors

    public int getPlid() {
        return plid;
    }

    public void setPlid(int plid) {
        this.plid=plid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }


    public String getCelebiscalled() {
        return celebiscalled;
    }

    public void setCelebiscalled(String celebiscalled) {
        this.celebiscalled=celebiscalled;
    }



    public String getCustomdomain1() {
        return customdomain1;
    }

    public void setCustomdomain1(String customdomain1) {
        this.customdomain1=customdomain1;
    }

    public String getCustomdomain2() {
        return customdomain2;
    }

    public void setCustomdomain2(String customdomain2) {
        this.customdomain2=customdomain2;
    }

    public String getCustomdomain3() {
        return customdomain3;
    }

    public void setCustomdomain3(String customdomain3) {
        this.customdomain3=customdomain3;
    }

    public String getTwitterusername() {
        return twitterusername;
    }

    public void setTwitterusername(String twitterusername) {
        this.twitterusername=twitterusername;
    }

    public String getTwitterpassword() {
        return twitterpassword;
    }

    public void setTwitterpassword(String twitterpassword) {
        this.twitterpassword=twitterpassword;
    }
}