package com.celebtwit.dao;

import com.celebtwit.dao.hibernate.BasePersistentClass;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.session.AuthControlled;
import org.apache.log4j.Logger;


public class Keyword extends BasePersistentClass implements java.io.Serializable, AuthControlled {

    // Fields
     private int keywordid;
     private String keyword;
     private boolean islocation;
     private int sincetwitpostid;



    public static Keyword get(int id) {
        Logger logger = Logger.getLogger("com.celebtwit.dao.Keyword");
        try {
            logger.debug("Keyword.get(" + id + ") called.");
            Keyword obj = (Keyword) HibernateUtil.getSession().get(Keyword.class, id);
            if (obj == null) {
                logger.debug("Keyword.get(" + id + ") returning new instance because hibernate returned null.");
                return new Keyword();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.celebtwit.dao.Keyword", ex);
            return new Keyword();
        }
    }

    // Constructors

    /** default constructor */
    public Keyword() {
    }

    public boolean canRead(User user){
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors


    public int getKeywordid() {
        return keywordid;
    }

    public void setKeywordid(int keywordid) {
        this.keywordid = keywordid;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public boolean getIslocation() {
        return islocation;
    }

    public void setIslocation(boolean islocation) {
        this.islocation = islocation;
    }

    public int getSincetwitpostid() {
        return sincetwitpostid;
    }

    public void setSincetwitpostid(int sincetwitpostid) {
        this.sincetwitpostid = sincetwitpostid;
    }
}