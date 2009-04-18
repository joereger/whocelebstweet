package com.celebtwit.dao;

import com.celebtwit.dao.hibernate.BasePersistentClass;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.session.AuthControlled;

import java.util.Date;

import org.apache.log4j.Logger;


public class Twitpost extends BasePersistentClass implements java.io.Serializable, AuthControlled {

    // Fields
     private int twitpostid;
     private int twitid;
     private String twitterguid;
     private String post;
     private Date created_at;
     private String created_at_string;




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

    public String getCreated_at_string() {
        return created_at_string;
    }

    public void setCreated_at_string(String created_at_string) {
        this.created_at_string=created_at_string;
    }
}