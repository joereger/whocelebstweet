package com.celebtwit.dao;

import com.celebtwit.dao.hibernate.BasePersistentClass;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.session.AuthControlled;
import org.apache.log4j.Logger;

import java.util.Date;


public class Twitpost extends BasePersistentClass implements java.io.Serializable, AuthControlled {

    // Fields
     private int twitpostid;
     private int twitid;
     private String twitterguid;
     private String post;
     private Date created_at;
     private int pl01;
     private int pl02;
     private int pl03;
     private int pl04;
     private int pl05;

     private Twit twit;



    public static Twitpost get(int id) {
        Logger logger = Logger.getLogger("com.celebtwit.dao.Twitpost");
        try {
            logger.debug("Twitpost.get(" + id + ") called.");
            Twitpost obj = (Twitpost) HibernateUtil.getSession().get(Twitpost.class, id);
            if (obj == null) {
                logger.debug("Twitpost.get(" + id + ") returning new instance because hibernate returned null.");
                return new Twitpost();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.celebtwit.dao.Twitpost", ex);
            return new Twitpost();
        }
    }

    // Constructors

    /** default constructor */
    public Twitpost() {
    }

    public boolean canRead(User user){
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors

    public int getTwitpostid() {
        return twitpostid;
    }

    public void setTwitpostid(int twitpostid) {
        this.twitpostid=twitpostid;
    }

    public int getTwitid() {
        return twitid;
    }

    public void setTwitid(int twitid) {
        this.twitid=twitid;
    }

    public String getTwitterguid() {
        return twitterguid;
    }

    public void setTwitterguid(String twitterguid) {
        this.twitterguid=twitterguid;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post=post;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at=created_at;
    }

    public Twit getTwit() {
        return twit;
    }

    public void setTwit(Twit twit) {
        this.twit=twit;
    }

    public int getPl01() {
        return pl01;
    }

    public void setPl01(int pl01) {
        this.pl01 = pl01;
    }

    public int getPl02() {
        return pl02;
    }

    public void setPl02(int pl02) {
        this.pl02 = pl02;
    }

    public int getPl03() {
        return pl03;
    }

    public void setPl03(int pl03) {
        this.pl03 = pl03;
    }

    public int getPl04() {
        return pl04;
    }

    public void setPl04(int pl04) {
        this.pl04 = pl04;
    }

    public int getPl05() {
        return pl05;
    }

    public void setPl05(int pl05) {
        this.pl05 = pl05;
    }
}