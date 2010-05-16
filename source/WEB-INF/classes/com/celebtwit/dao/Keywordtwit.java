package com.celebtwit.dao;

import com.celebtwit.dao.hibernate.BasePersistentClass;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.session.AuthControlled;
import org.apache.log4j.Logger;


public class Keywordtwit extends BasePersistentClass implements java.io.Serializable, AuthControlled {

    // Fields
    private int keywordtwitid;
    private int keywordid;
    private int twitid;
    private int numberoftwitposts;



    public static Keywordtwit get(int id) {
        Logger logger = Logger.getLogger("com.celebtwit.dao.Keywordtwit");
        try {
            logger.debug("Keywordtwit.get(" + id + ") called.");
            Keywordtwit obj = (Keywordtwit) HibernateUtil.getSession().get(Keywordtwit.class, id);
            if (obj == null) {
                logger.debug("Keywordtwit.get(" + id + ") returning new instance because hibernate returned null.");
                return new Keywordtwit();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.celebtwit.dao.Keywordtwit", ex);
            return new Keywordtwit();
        }
    }

    // Constructors

    /** default constructor */
    public Keywordtwit() {
    }

    public boolean canRead(User user){
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors


    public int getKeywordtwitid() {
        return keywordtwitid;
    }

    public void setKeywordtwitid(int keywordtwitid) {
        this.keywordtwitid = keywordtwitid;
    }

    public int getKeywordid() {
        return keywordid;
    }

    public void setKeywordid(int keywordid) {
        this.keywordid = keywordid;
    }

    public int getTwitid() {
        return twitid;
    }

    public void setTwitid(int twitid) {
        this.twitid = twitid;
    }

    public int getNumberoftwitposts() {
        return numberoftwitposts;
    }

    public void setNumberoftwitposts(int numberoftwitposts) {
        this.numberoftwitposts = numberoftwitposts;
    }
}