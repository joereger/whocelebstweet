package com.celebtwit.htmluibeans;

import org.apache.log4j.Logger;

import com.celebtwit.systemprops.SystemProperty;
import com.celebtwit.systemprops.BaseUrl;
import com.celebtwit.htmlui.Pagez;
import com.celebtwit.htmlui.ValidationException;

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

    public SysadminSystemProps(){

    }



    public void initBean(){
        baseurl = SystemProperty.getProp(SystemProperty.PROP_BASEURL);
        sendxmpp = SystemProperty.getProp(SystemProperty.PROP_SENDXMPP);
        smtpoutboundserver = SystemProperty.getProp(SystemProperty.PROP_SMTPOUTBOUNDSERVER);
        issslon = SystemProperty.getProp(SystemProperty.PROP_ISSSLON);

    }

    public void saveProps() throws ValidationException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            SystemProperty.setProp(SystemProperty.PROP_BASEURL, baseurl);
            SystemProperty.setProp(SystemProperty.PROP_SENDXMPP, sendxmpp);
            SystemProperty.setProp(SystemProperty.PROP_SMTPOUTBOUNDSERVER, smtpoutboundserver);
            SystemProperty.setProp(SystemProperty.PROP_ISSSLON, issslon);
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
}
