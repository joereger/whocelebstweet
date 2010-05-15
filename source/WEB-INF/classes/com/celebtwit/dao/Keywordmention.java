package com.celebtwit.dao;

import com.celebtwit.dao.hibernate.BasePersistentClass;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.session.AuthControlled;
import org.apache.log4j.Logger;


public class Keywordmention extends BasePersistentClass implements java.io.Serializable, AuthControlled {

    // Fields
    private int keywordmentionid;
    private int twitid;
    private int keywordid;
    private int twitpostid;



    public static Keywordmention get(int id) {
        Logger logger = Logger.getLogger("com.celebtwit.dao.Keywordmention");
        try {
            logger.debug("Keywordmention.get(" + id + ") called.");
            Keywordmention obj = (Keywordmention) HibernateUtil.getSession().get(Keywordmention.class, id);
            if (obj == null) {
                logger.debug("Keywordmention.get(" + id + ") returning new instance because hibernate returned null.");
                return new Keywordmention();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.celebtwit.dao.Keywordmention", ex);
            return new Keywordmention();
        }
    }

    // Constructors

    /** default constructor */
    public Keywordmention() {
    }

    public boolean canRead(User user){
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors


    public int getKeywordmentionid() {
        return keywordmentionid;
    }

    public void setKeywordmentionid(int keywordmentionid) {
        this.keywordmentionid = keywordmentionid;
    }

    public int getTwitid() {
        return twitid;
    }

    public void setTwitid(int twitid) {
        this.twitid = twitid;
    }

    public int getKeywordid() {
        return keywordid;
    }

    public void setKeywordid(int keywordid) {
        this.keywordid = keywordid;
    }

    public int getTwitpostid() {
        return twitpostid;
    }

    public void setTwitpostid(int twitpostid) {
        this.twitpostid = twitpostid;
    }
}