package com.celebtwit.dao;

import com.celebtwit.dao.hibernate.BasePersistentClass;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.session.AuthControlled;

import java.util.Date;

import org.apache.log4j.Logger;


public class Emailme extends BasePersistentClass implements java.io.Serializable, AuthControlled {


    // Fields
     private int emailmeid;
     private String email;
     private String twitterusername;
     private String secretkey;
     private Date createdate;
     private Date lastsentdate;
     private int sentcount;
     private int plid;
     private boolean isverifiedbyemail;



    public static Emailme get(int id) {
        Logger logger = Logger.getLogger("com.celebtwit.dao.Emailme");
        try {
            logger.debug("Emailme.get(" + id + ") called.");
            Emailme obj = (Emailme) HibernateUtil.getSession().get(Emailme.class, id);
            if (obj == null) {
                logger.debug("Emailme.get(" + id + ") returning new instance because hibernate returned null.");
                return new Emailme();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.celebtwit.dao.Emailme", ex);
            return new Emailme();
        }
    }

    // Constructors

    /** default constructor */
    public Emailme() {
    }

    public boolean canRead(User user){
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors

    public int getEmailmeid() {
        return emailmeid;
    }

    public void setEmailmeid(int emailmeid) {
        this.emailmeid=emailmeid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getTwitterusername() {
        return twitterusername;
    }

    public void setTwitterusername(String twitterusername) {
        this.twitterusername=twitterusername;
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey=secretkey;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate=createdate;
    }

    public Date getLastsentdate() {
        return lastsentdate;
    }

    public void setLastsentdate(Date lastsentdate) {
        this.lastsentdate=lastsentdate;
    }

    public int getSentcount() {
        return sentcount;
    }

    public void setSentcount(int sentcount) {
        this.sentcount=sentcount;
    }

    public int getPlid() {
        return plid;
    }

    public void setPlid(int plid) {
        this.plid=plid;
    }

    public boolean getIsverifiedbyemail() {
        return isverifiedbyemail;
    }

    public void setIsverifiedbyemail(boolean isverifiedbyemail) {
        this.isverifiedbyemail=isverifiedbyemail;
    }
}