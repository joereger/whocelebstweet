package com.celebtwit.ads;

import com.celebtwit.dao.Pl;
import com.celebtwit.htmlui.Pagez;
import com.celebtwit.htmlui.UserSession;
import com.celebtwit.privatelabel.PlFinder;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: Feb 2, 2010
 * Time: 7:27:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdUtil {

    private static String getAdNetworkNameFromSession(){
        Logger logger = Logger.getLogger(AdUtil.class);
        //This is only in case there's no UserSession... normally there is
        if (Pagez.getUserSession()==null){
            logger.debug("Pagez.getUserSession()==null so will look to create one");
            //UserSession is null so need to do some of the work normally done in FilterMail
            //Sometimes, somehow, on live site (probably spiders) get no ad network set, this is saved to cache and real users don't see ads
            if (Pagez.getRequest()!=null){
                logger.debug("Pagez.getRequest()!=null so will create new UserSession");
                Pl pl = PlFinder.find(Pagez.getRequest());
                UserSession userSession = new UserSession();
                userSession.setPl(pl);
                Pagez.setUserSession(userSession);
                AssignAdNetwork.assign(Pagez.getRequest());
            } else {
                logger.debug("Pagez.getRequest()==null");
            }
        }
        //By now we should have a userSession
        if (Pagez.getUserSession()!=null){
            if (Pagez.getUserSession().getAdNetworkName()!=null && Pagez.getUserSession().getAdNetworkName().length()>0){
                logger.debug("returning adnetworkname="+Pagez.getUserSession().getAdNetworkName()+" from Pagez.getUserSession().getAdNetworkName()");
                return Pagez.getUserSession().getAdNetworkName();
            }
        } else {
            //WTF?
            logger.error("No UserSession found so defaulting to AdNetworkNone.ADNETWORKNAME");
        }
        return AdNetworkNone.ADNETWORKNAME;
    }


    //New Ones
    public static String get120x240SIDEBAR() {
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get120x240SIDEBAR();
    }

    public static String get160x600INDEX() {
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get160x600INDEX();
    }

    public static String get336x280PROFILEPIC() {
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get336x280PROFILEPIC();
    }

    public static String get160x600STATS() {
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get160x600STATS();
    }

    public static String get336x280TWEET() {
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get336x280TWEET();
    }

    public static String get160x600TWITTERSTATS() {
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get160x600TWITTERSTATS();
    }

    public static String get234x60TWITPOSTASHTML() {
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get234x60TWITPOSTASHTML();
    }

    public static String get336x280TWITPOSTASHTML() {
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get336x280TWITPOSTASHTML();
    }

    public static String get336x280PUBTWITWHOPANEL() {
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get336x280PUBTWITWHOPANEL();
    }

    public static String get336x280KEYWORD() {
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get336x280KEYWORD();
    }

    public static String get336x280MENTIONSKEYWORD() {
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get336x280MENTIONSKEYWORD();
    }
    
    public static String get336x280TWITNOTCELEB() {
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get336x280TWITNOTCELEB();
    }
    
    public static String get160x600TALKSABOUT() {
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get160x600TALKSABOUT();
    }

    public static String getSEARCH() {
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).getSEARCH();
    }



}
