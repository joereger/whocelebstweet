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
            logger.error("Pagez.getUserSession()==null so will look to create one");
            //UserSession is null so need to do some of the work normally done in FilterMail
            //Sometimes, somehow, on live site (probably spiders) get no ad network set, this is saved to cache and real users don't see ads
            if (Pagez.getRequest()!=null){
                logger.error("Pagez.getRequest()!=null so will create new UserSession");
                Pl pl = PlFinder.find(Pagez.getRequest());
                UserSession userSession = new UserSession();
                userSession.setPl(pl);
                Pagez.setUserSession(userSession);
                AssignAdNetwork.assign(Pagez.getRequest());
            } else {
                logger.error("Pagez.getRequest()==null");
            }
        }
        //By now we should have a userSession
        if (Pagez.getUserSession()!=null){
            if (Pagez.getUserSession().getAdNetworkName()!=null && Pagez.getUserSession().getAdNetworkName().length()>0){
                return Pagez.getUserSession().getAdNetworkName();
            }
        } else {
            //WTF?
            logger.error("No UserSession found so defaulting to AdNetworkNone.ADNETWORKNAME");
        }
        return AdNetworkNone.ADNETWORKNAME;
    }

    public static String get160x600(){
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get160x600();
    }

    public static String get336x280(){
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get336x280();
    }

    public static String get160x90(){
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get160x90();
    }

    public static String get234x60(){
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get234x60();
    }


    public static String get300x250(){
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get300x250();
    }


    
    public static String getCornerPeel(){
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).getCornerPeel();
    }

    public static String getFloaterBanner() {
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).getFloaterBanner();
    }

    public static String get100percentX200() {
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get100percentX200();
    }
    
    public static String get400x600(){
        return AdNetworkFactory.getByName(AdUtil.getAdNetworkNameFromSession()).get400x600();
    }




}
