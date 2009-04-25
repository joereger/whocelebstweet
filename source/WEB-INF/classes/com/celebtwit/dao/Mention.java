package com.celebtwit.dao;

import com.celebtwit.dao.hibernate.BasePersistentClass;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.session.AuthControlled;

import java.util.Date;

import org.apache.log4j.Logger;


public class Mention extends BasePersistentClass implements java.io.Serializable, AuthControlled {

    // Fields
     private int mentionid;
     private int twitidceleb;
     private int twitidmentioned;
     private boolean ismentionedaceleb;
     private int twitpostid;
     private Date created_at;




    public static Mention get(int id) {
        Logger logger = Logger.getLogger("com.celebtwit.dao.Mention");
        try {
            logger.debug("Mention.get(" + id + ") called.");
            Mention obj = (Mention) HibernateUtil.getSession().get(Mention.class, id);
            if (obj == null) {
                logger.debug("Mention.get(" + id + ") returning new instance because hibernate returned null.");
                return new Mention();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.celebtwit.dao.Mention", ex);
            return new Mention();
        }
    }

    // Constructors

    /** default constructor */
    public Mention() {
    }

    public boolean canRead(User user){
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors

    public int getMentionid() {
        return mentionid;
    }

    public void setMentionid(int mentionid) {
        this.mentionid=mentionid;
    }

    public int getTwitidceleb() {
        return twitidceleb;
    }

    public void setTwitidceleb(int twitidceleb) {
        this.twitidceleb=twitidceleb;
    }

    public int getTwitidmentioned() {
        return twitidmentioned;
    }

    public void setTwitidmentioned(int twitidmentioned) {
        this.twitidmentioned=twitidmentioned;
    }

    public int getTwitpostid() {
        return twitpostid;
    }

    public void setTwitpostid(int twitpostid) {
        this.twitpostid=twitpostid;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at=created_at;
    }

    public boolean getIsmentionedaceleb() {
        return ismentionedaceleb;
    }

    public void setIsmentionedaceleb(boolean ismentionedaceleb) {
        this.ismentionedaceleb=ismentionedaceleb;
    }
}