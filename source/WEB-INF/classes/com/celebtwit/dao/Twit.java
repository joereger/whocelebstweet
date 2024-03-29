package com.celebtwit.dao;

import com.celebtwit.dao.hibernate.BasePersistentClass;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.session.AuthControlled;
import org.apache.log4j.Logger;

import java.util.Date;


public class Twit extends BasePersistentClass implements java.io.Serializable, AuthControlled {

    // Fields
     private int twitid;
     private String twitterusername;
     private String twitteruserid;
     private String realname;
     private boolean isceleb;
     private String since_id;
     private Date lastprocessed;
     private String profile_image_url;
     private String website_url;
     private String description;
     private int followers_count;
     private int statuses_count;
     private Date laststatstweet;
     private int pl01;
     private int pl02;
     private int pl03;
     private int pl04;
     private int pl05;

     



    public static Twit get(int id) {
        Logger logger = Logger.getLogger("com.celebtwit.dao.Twit");
        try {
            logger.debug("Twit.get(" + id + ") called.");
            Twit obj = (Twit) HibernateUtil.getSession().get(Twit.class, id);
            if (obj == null) {
                logger.debug("Twit.get(" + id + ") returning new instance because hibernate returned null.");
                return new Twit();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.celebtwit.dao.Twit", ex);
            return new Twit();
        }
    }

    // Constructors

    /** default constructor */
    public Twit() {
    }

    public boolean canRead(User user){
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors

    public int getTwitid() {
        return twitid;
    }

    public void setTwitid(int twitid) {
        this.twitid=twitid;
    }

    public String getTwitterusername() {
        return twitterusername;
    }

    public void setTwitterusername(String twitterusername) {
        this.twitterusername=twitterusername;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname=realname;
    }

    public boolean getIsceleb() {
        return isceleb;
    }

    public void setIsceleb(boolean isceleb) {
        this.isceleb=isceleb;
    }

    public String getSince_id() {
        return since_id;
    }

    public void setSince_id(String since_id) {
        this.since_id=since_id;
    }

    public Date getLastprocessed() {
        return lastprocessed;
    }

    public void setLastprocessed(Date lastprocessed) {
        this.lastprocessed=lastprocessed;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url=profile_image_url;
    }

    
    public String getWebsite_url() {
        return website_url;
    }

    public void setWebsite_url(String website_url) {
        this.website_url=website_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count=followers_count;
    }

    public int getStatuses_count() {
        return statuses_count;
    }

    public void setStatuses_count(int statuses_count) {
        this.statuses_count=statuses_count;
    }

    public Date getLaststatstweet() {
        return laststatstweet;
    }

    public void setLaststatstweet(Date laststatstweet) {
        this.laststatstweet=laststatstweet;
    }

    public String getTwitteruserid() {
        return twitteruserid;
    }

    public void setTwitteruserid(String twitteruserid) {
        this.twitteruserid = twitteruserid;
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