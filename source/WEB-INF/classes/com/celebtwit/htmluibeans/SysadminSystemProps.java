package com.celebtwit.htmluibeans;

import com.celebtwit.htmlui.ValidationException;
import com.celebtwit.systemprops.BaseUrl;
import com.celebtwit.systemprops.SystemProperty;
import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 * User: Joe Reger Jr
 * Date: Oct 6, 2006
 * Time: 3:35:02 AM
 */
public class SysadminSystemProps implements Serializable {


    public String baseurl;
    public String sendxmpp;
    public String smtpoutboundserver;
    public String issslon;
    public String dostattweets;
    public String twitteraccesstoken;
    public String twitteraccesstokensecret;

    public SysadminSystemProps(){

    }



    public void initBean(){
        baseurl = SystemProperty.getProp(SystemProperty.PROP_BASEURL);
        sendxmpp = SystemProperty.getProp(SystemProperty.PROP_SENDXMPP);
        smtpoutboundserver = SystemProperty.getProp(SystemProperty.PROP_SMTPOUTBOUNDSERVER);
        issslon = SystemProperty.getProp(SystemProperty.PROP_ISSSLON);
        dostattweets = SystemProperty.getProp(SystemProperty.PROP_DOSTATTWEETS);
        twitteraccesstoken = SystemProperty.getProp(SystemProperty.PROP_TWITTERACCESSTOKEN);
        twitteraccesstokensecret = SystemProperty.getProp(SystemProperty.PROP_TWITTERACCESSTOKENSECRET);
    }

    public void saveProps() throws ValidationException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            SystemProperty.setProp(SystemProperty.PROP_BASEURL, baseurl);
            SystemProperty.setProp(SystemProperty.PROP_SENDXMPP, sendxmpp);
            SystemProperty.setProp(SystemProperty.PROP_SMTPOUTBOUNDSERVER, smtpoutboundserver);
            SystemProperty.setProp(SystemProperty.PROP_ISSSLON, issslon);
            SystemProperty.setProp(SystemProperty.PROP_DOSTATTWEETS, dostattweets);
            SystemProperty.setProp(SystemProperty.PROP_TWITTERACCESSTOKEN, twitteraccesstoken);
            SystemProperty.setProp(SystemProperty.PROP_TWITTERACCESSTOKENSECRET, twitteraccesstokensecret);
            BaseUrl.refresh();
        } catch (Exception ex){
            logger.error("",ex);
        }
    }

    public String getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl=baseurl;
    }

    public String getSendxmpp() {
        return sendxmpp;
    }

    public void setSendxmpp(String sendxmpp) {
        this.sendxmpp=sendxmpp;
    }

    public String getSmtpoutboundserver() {
        return smtpoutboundserver;
    }

    public void setSmtpoutboundserver(String smtpoutboundserver) {
        this.smtpoutboundserver=smtpoutboundserver;
    }

    public String getIssslon() {
        return issslon;
    }

    public void setIssslon(String issslon) {
        this.issslon=issslon;
    }

    public String getDostattweets() {
        return dostattweets;
    }

    public void setDostattweets(String dostattweets) {
        this.dostattweets=dostattweets;
    }

    public String getTwitteraccesstoken() {
        return twitteraccesstoken;
    }

    public void setTwitteraccesstoken(String twitteraccesstoken) {
        this.twitteraccesstoken = twitteraccesstoken;
    }

    public String getTwitteraccesstokensecret() {
        return twitteraccesstokensecret;
    }

    public void setTwitteraccesstokensecret(String twitteraccesstokensecret) {
        this.twitteraccesstokensecret = twitteraccesstokensecret;
    }
}
