package com.celebtwit.htmluibeans;

import org.apache.log4j.Logger;
import org.apache.commons.validator.EmailValidator;

import com.celebtwit.util.RandomString;
import com.celebtwit.util.GeneralException;
import com.celebtwit.util.Str;
import com.celebtwit.util.jcaptcha.CaptchaServiceSingleton;
import com.celebtwit.dao.User;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.htmlui.UserSession;
import com.celebtwit.htmlui.Pagez;
import com.celebtwit.htmlui.ValidationException;
import com.celebtwit.email.EmailSend;
import com.celebtwit.email.EmailActivationSend;
import com.octo.captcha.service.CaptchaServiceException;

import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.io.Serializable;

/**
 * User: Joe Reger Jr
 * Date: Apr 21, 2006
 * Time: 10:38:03 AM
 */
public class EmailActivationResend implements Serializable {

    //Form props
    private String email;
    private String j_captcha_response;
    private String captchaId;

    public EmailActivationResend(){

    }


    public void initBean(){
        if (Pagez.getUserSession().getUser()!=null){
            email = Pagez.getUserSession().getUser().getEmail();
        }
    }

    public void reSendEmail() throws ValidationException {
        ValidationException vex = new ValidationException();
//        boolean isCaptchaCorrect = false;
//        try {
//            isCaptchaCorrect = CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId, j_captcha_response);
//        } catch (CaptchaServiceException e) {
//             //should not happen, may be thrown if the id is not valid
//        }
//        if (!isCaptchaCorrect){
//            vex.addValidationError("You failed to correctly type the letters into the box.");
//            throw vex;
//        }

        List<User> users = HibernateUtil.getSession().createQuery("from User where email='"+ Str.cleanForSQL(email)+"'").list();
        if (email==null || email.equals("") || users.size()<=0){
            vex.addValidationError("That email address was not found.");
            throw vex;
        }
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
            User user = iterator.next();
            EmailActivationSend.sendActivationEmail(user);
        }
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getJ_captcha_response() {
        return j_captcha_response;
    }

    public void setJ_captcha_response(String j_captcha_response) {
        this.j_captcha_response = j_captcha_response;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId=captchaId;
    }
}
