package com.celebtwit.dao;

import com.celebtwit.dao.hibernate.BasePersistentClass;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.session.AuthControlled;

import java.util.Date;

import org.apache.log4j.Logger;


public class Suggest extends BasePersistentClass implements java.io.Serializable, AuthControlled {

    // Fields
     private int suggestid;
     private int plid;
     private String realname;
     private String twitterusername;
     private String submitteremail;
     private String reason;



    public static Suggest get(int id) {
        Logger logger = Logger.getLogger("com.celebtwit.dao.Suggest");
        try {
            logger.debug("Suggest.get(" + id + ") called.");
            Suggest obj = (Suggest) HibernateUtil.getSession().get(Suggest.class, id);
            if (obj == null) {
                logger.debug("Suggest.get(" + id + ") returning new instance because hibernate returned null.");
                return new Suggest();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.celebtwit.dao.Suggest", ex);
            return new Suggest();
        }
    }

    // Constructors

    /** default constructor */
    public Suggest() {
    }

    public boolean canRead(User user){
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors

    public int getSuggestid() {
        return suggestid;
    }

    public void setSuggestid(int suggestid) {
        this.suggestid=suggestid;
    }

    public int getPlid() {
        return plid;
    }

    public void setPlid(int plid) {
        this.plid=plid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname=realname;
    }

    public String getTwitterusername() {
        return twitterusername;
    }

    public void setTwitterusername(String twitterusername) {
        this.twitterusername=twitterusername;
    }

    public String getSubmitteremail() {
        return submitteremail;
    }

    public void setSubmitteremail(String submitteremail) {
        this.submitteremail=submitteremail;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason=reason;
    }
}