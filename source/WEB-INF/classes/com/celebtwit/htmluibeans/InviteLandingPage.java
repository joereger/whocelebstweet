package com.celebtwit.htmluibeans;


import com.celebtwit.util.Util;
import com.celebtwit.util.Num;
import com.celebtwit.htmlui.UserSession;
import com.celebtwit.htmlui.Pagez;
import com.celebtwit.dao.User;
import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 * User: Joe Reger Jr
 * Date: Sep 1, 2006
 * Time: 12:46:48 PM
 */
public class InviteLandingPage implements Serializable {

    private String referredby;

    public InviteLandingPage(){

    }
    


    public void initBean(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("instanciated");
        logger.debug("Pagez.getRequest().getParameter(\"referredby\")="+Pagez.getRequest().getParameter("referredby"));
        if (Num.isinteger(Pagez.getRequest().getParameter("referredby"))){
            User user = User.get(Integer.parseInt(Pagez.getRequest().getParameter("referredby")));
            if (user!=null && user.getUserid()>0){
                Pagez.getUserSession().setReferredbyOnlyUsedForSignup(Integer.parseInt(Pagez.getRequest().getParameter("referredby")));
                referredby = user.getFirstname() + " " + user.getLastname();
            } else {
                Pagez.getUserSession().setReferredbyOnlyUsedForSignup(0);
            }

        }
    }

    public String getReferredby() {
        return referredby;
    }

    public void setReferredby(String referredby) {
        this.referredby = referredby;
    }


}
