package com.celebtwit.htmluibeans;


import com.celebtwit.util.Str;
import com.celebtwit.util.Num;
import com.celebtwit.dao.*;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.email.EmailActivationSend;
import com.celebtwit.email.LostPasswordSend;
import com.celebtwit.htmlui.Pagez;
import com.celebtwit.htmlui.ValidationException;
import com.celebtwit.helpers.DeleteUser;

import java.util.*;
import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Apr 21, 2006
 * Time: 10:38:03 AM
 */
public class SysadminUserDetail implements Serializable {

    private int userid;
    private String firstname;
    private String lastname;
    private String email;
    private String facebookuid="";
    private boolean issysadmin = false;
    private String activitypin;
    private boolean isenabled = true;
    private User user;


    public SysadminUserDetail(){

    }



    public void initBean(){
        User user = null;
        if (com.celebtwit.util.Num.isinteger(Pagez.getRequest().getParameter("userid"))){
            user = User.get(Integer.parseInt(Pagez.getRequest().getParameter("userid")));
        }
        if (user!=null && user.getUserid()>0){
            this.userid = user.getUserid();
            this.user = user;
            firstname = user.getFirstname();
            lastname = user.getLastname();
            email = user.getEmail();
            isenabled = user.getIsenabled();
            issysadmin = false;
            facebookuid = String.valueOf(user.getFacebookuserid());
            for (Iterator<Userrole> iterator = user.getUserroles().iterator(); iterator.hasNext();) {
                Userrole userrole = iterator.next();
                if (userrole.getRoleid()== Userrole.SYSADMIN){
                    issysadmin = true;
                }
            }






        }
    }

    public void save() throws ValidationException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("save() called");
        logger.debug("userid="+userid);
        logger.debug("firstname="+firstname);
        logger.debug("lastname="+lastname);
        logger.debug("email="+email);
        User user = User.get(userid);
        if (user!=null && user.getUserid()>0){
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmail(email);
            if (Num.isinteger(facebookuid)){
                user.setFacebookuserid(Integer.parseInt(facebookuid));
            }
            try{user.save();}catch (Exception ex){logger.error("",ex);}
        }

    }


    public String sendresetpasswordemail() throws ValidationException {
        User user = User.get(userid);
        if (user!=null && user.getUserid()>0){
            LostPasswordSend.sendLostPasswordEmail(user);
        }
        Pagez.getUserSession().setMessage("Password reset email sent");
        return "sysadminuserdetail";
    }

    public String reactivatebyemail() throws ValidationException {
        User user = User.get(userid);
        if (user!=null && user.getUserid()>0){
            EmailActivationSend.sendActivationEmail(user);
        }
        Pagez.getUserSession().setMessage("Reactivation email sent");
        return "sysadminuserdetail";
    }

    public String toggleisenabled() throws ValidationException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        User user = User.get(userid);
        if (user.getIsenabled()){
            //Disable
            user.setIsenabled(false);
            isenabled = false;
            Pagez.getUserSession().setMessage("Account disabled.");
        } else {
            // Enable
            user.setIsenabled(true);
            isenabled = true;
            Pagez.getUserSession().setMessage("Account enabled.");
        }
        try{user.save();}catch (Exception ex){logger.error("",ex);}
        return "sysadminuserdetail";
    }

    public String togglesysadminprivs() throws ValidationException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("togglesysadminprivs()");
        if (activitypin.equals("yes, i want to do this")){
            activitypin = "";
            User user = User.get(userid);
            if (user!=null && user.getUserid()>0){
                issysadmin = false;
                for (Iterator<Userrole> iterator = user.getUserroles().iterator(); iterator.hasNext();) {
                    Userrole userrole = iterator.next();
                    if (userrole.getRoleid()== Userrole.SYSADMIN){
                        issysadmin = true;
                    }
                }
                if (issysadmin){
                    logger.debug("is a sysadmin");
                    //@todo revoke sysadmin privs doesn't work
                    //int userroleidtodelete=0;
                    for (Iterator<Userrole> iterator = user.getUserroles().iterator(); iterator.hasNext();) {
                        Userrole userrole = iterator.next();
                        logger.debug("found roleid="+userrole.getRoleid());
                        if (userrole.getRoleid()==Userrole.SYSADMIN){
                            logger.debug("removing it from iterator");
                            iterator.remove();
                        }
                    }
                    try{user.save();} catch (Exception ex){logger.error("",ex);}
                    issysadmin = false;
                    Pagez.getUserSession().setMessage("User is no longer a sysadmin");
                } else {
                    Userrole role = new Userrole();
                    role.setUserid(user.getUserid());
                    role.setRoleid(Userrole.SYSADMIN);
                    user.getUserroles().add(role);
                    try{role.save();} catch (Exception ex){logger.error("",ex);}
                    issysadmin = true;
                    Pagez.getUserSession().setMessage("User is now a sysadmin");
                }
                initBean();
            }
        } else {
            Pagez.getUserSession().setMessage("Activity Pin Not Correct.");
        }
        return "sysadminuserdetail";
    }

    public void deleteuser() throws ValidationException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("deleteuser()");
        if (activitypin.equals("yes, i want to do this")){
            activitypin = "";
            User user = User.get(userid);
            DeleteUser.delete(user);
        } else {
            Pagez.getUserSession().setMessage("Activity Pin Not Correct.");
        }

    }








    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }


    public String getActivitypin() {
        return activitypin;
    }

    public void setActivitypin(String activitypin) {
        this.activitypin = activitypin;
    }

    public boolean getIssysadmin() {
        return issysadmin;
    }

    public void setIssysadmin(boolean issysadmin) {
        this.issysadmin = issysadmin;
    }






    public boolean getIsenabled() {
        return isenabled;
    }

    public void setIsenabled(boolean isenabled) {
        this.isenabled = isenabled;
    }





    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }












}
