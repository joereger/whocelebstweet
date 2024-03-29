package com.celebtwit.htmlui;

import com.celebtwit.ads.AdNetworkFactory;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.User;
import com.celebtwit.dao.Userrole;
import org.apache.log4j.Logger;
import twitter4j.Twitter;
import twitter4j.auth.RequestToken;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;

/**
 * User: Joe Reger Jr
 * Date: May 31, 2006
 * Time: 12:51:06 PM
 */
public class UserSession implements Serializable {

    //Note: Only use primitives to simplify clustering.
    //Example: userid as int and then getUser() calls on the clustered/cached hibernate layer.

    private int userid;
    private boolean isloggedin = false;
    private boolean isAllowedToResetPasswordBecauseHasValidatedByEmail = false;
    private int referredbyOnlyUsedForSignup = 0;
    private boolean isSysadmin = false;
    private boolean isLoggedInToBeta = false;
    private String message = "";
    private Calendar createdate = Calendar.getInstance();
    private int plid = 1;
    private boolean isSisterPl = false;
    private String adNetworkName = AdNetworkFactory.getDefaultAdNetwork().getAdNetworkName();
    private Twitter twitter;
    private RequestToken twitterRequestToken;

    public UserSession(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("New UserSession created.");
        try{
            createdate = Calendar.getInstance();
        } catch (Exception ex){
            logger.error("", ex);
        }
    }

    public User getUser() {
        if (userid>0){
            return User.get(userid);
        } else {
            return null;
        }
    }




    public void setUser(User user) {
        if (user!=null){
            userid = user.getUserid();
            isSysadmin = isUserASysadmin(user);
        } else {
            userid = 0;
        }
    }

    public static boolean isUserASysadmin(User user){
        for (Iterator<Userrole> iterator = user.getUserroles().iterator(); iterator.hasNext();) {
            Userrole userrole = iterator.next();
            if (userrole.getRoleid()== Userrole.SYSADMIN){
                return true;
            }
        }
        return false;
    }

    public boolean getIsloggedin() {
        return isloggedin;
    }

    public void setIsloggedin(boolean isloggedin) {
        this.isloggedin = isloggedin;
    }

    public boolean getIsAllowedToResetPasswordBecauseHasValidatedByEmail() {
        return isAllowedToResetPasswordBecauseHasValidatedByEmail;
    }

    public void setAllowedToResetPasswordBecauseHasValidatedByEmail(boolean allowedToResetPasswordBecauseHasValidatedByEmail) {
        isAllowedToResetPasswordBecauseHasValidatedByEmail = allowedToResetPasswordBecauseHasValidatedByEmail;
    }

    public int getReferredbyOnlyUsedForSignup() {
        return referredbyOnlyUsedForSignup;
    }

    public void setReferredbyOnlyUsedForSignup(int referredbyOnlyUsedForSignup) {
        this.referredbyOnlyUsedForSignup = referredbyOnlyUsedForSignup;
    }

    public boolean getIsSysadmin() {
        return isSysadmin;
    }

    public void setSysadmin(boolean sysadmin) {
        isSysadmin = sysadmin;
    }

    public boolean getIsLoggedInToBeta() {
        return isLoggedInToBeta;
    }

    public void setIsLoggedInToBeta(boolean isLoggedInToBeta) {
        this.isLoggedInToBeta = isLoggedInToBeta;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if (this.message==null || this.message.equals("")){
            this.message = message;
        } else {
            this.message = this.message + "<br/>" + message;
        }
        if (message==null || message.equals("")){
            this.message = "";
        }
    }

    
    public Pl getPl() {
        return Pl.get(plid);
    }

    public void setPl(Pl pl) {
        this.plid=pl.getPlid();
    }



    public Calendar getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Calendar createdate) {
        this.createdate=createdate;
    }


    public void setLoggedInToBeta(boolean loggedInToBeta) {
        isLoggedInToBeta = loggedInToBeta;
    }

    public boolean isSisterPl() {
        return isSisterPl;
    }

    public void setIsSisterPl(boolean sisterPl) {
        isSisterPl=sisterPl;
    }


    public String getAdNetworkName() {
        return adNetworkName;
    }

    public void setAdNetworkName(String adNetworkName) {
        this.adNetworkName = adNetworkName;
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
    }

    public RequestToken getTwitterRequestToken() {
        return twitterRequestToken;
    }

    public void setTwitterRequestToken(RequestToken twitterRequestToken) {
        this.twitterRequestToken = twitterRequestToken;
    }
}
