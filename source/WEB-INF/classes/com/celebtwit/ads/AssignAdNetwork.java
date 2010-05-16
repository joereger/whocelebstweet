package com.celebtwit.ads;

import com.celebtwit.dao.Pl;
import com.celebtwit.htmlui.Pagez;
import com.celebtwit.htmlui.UserSession;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: Feb 2, 2010
 * Time: 9:57:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class AssignAdNetwork {

    public static void assign(HttpServletRequest request){
        Logger logger = Logger.getLogger(AssignAdNetwork.class);
        if (Pagez.getUserSession()==null){
            logger.debug("Pagez.getUserSession()==null so creating one");
            UserSession userSession = new UserSession();
            Pagez.setUserSession(userSession);
        }
        //Set to default
        logger.debug("assign() setting to Default at start of process--");
        Pagez.getUserSession().setAdNetworkName(AdNetworkFactory.getDefaultAdNetwork().getAdNetworkName());
        //Only allow anything outside of the default if this is coming from google referer
        //if (request.getHeader("referer")!=null && request.getHeader("referer").indexOf("google")>-1){
            //Set by pl
            setByPl(request);
            //If referrer should be safe
            safeReferers(request);
        //}
        //If GoogleBot
        //if (request.getHeader("user-agent")!=null){
        //    if (request.getHeader("user-agent").indexOf("googlebot")>-1){
        //        logger.debug("assign() setting to AdSense because Googlebot");
        //        Pagez.getUserSession().setAdNetworkName(AdNetworkNone.ADNETWORKNAME);
        //    }
        //}
        //Override with url param = safe=1
        if (request!=null && request.getParameter("safe")!=null && request.getParameter("safe").equals("1")){
            logger.debug("assign() setting to None because request.getParameter('safe')");
            Pagez.getUserSession().setAdNetworkName(AdNetworkNone.ADNETWORKNAME);
        }
        //If manual set in url
        if (request!=null && request.getParameter("ads")!=null){
            AdNetwork adNetwork = AdNetworkFactory.getByName(request.getParameter("ads"));
            if (adNetwork!=null){
                logger.debug("assign() setting to "+adNetwork.getAdNetworkName()+" in URL because request.getParameter('ads')");
                Pagez.getUserSession().setAdNetworkName(adNetwork.getAdNetworkName());
            }
        }
        //Set None as master override
        if (Pagez.getUserSession().getPl()!=null && Pagez.getUserSession().getPl().getCommasepadnetworks().toLowerCase().trim().indexOf(AdNetworkNone.ADNETWORKNAME.toLowerCase().trim())>-1){
            logger.debug("assign() setting to None because Pagez.getUserSession().getPl().getCommasepadnetworks().toLowerCase().trim().indexOf(AdNetworkNone.ADNETWORKNAME.toLowerCase().trim())>-1");
            Pagez.getUserSession().setAdNetworkName(AdNetworkNone.ADNETWORKNAME);
        }
    }

    private static void setByPl(HttpServletRequest request){
        Logger logger = Logger.getLogger(AssignAdNetwork.class);
        Pl pl = Pagez.getUserSession().getPl();
        logger.debug("setByPl() pl.getCommasepadnetworks()="+pl.getCommasepadnetworks());
        if (pl.getCommasepadnetworks()!=null && pl.getCommasepadnetworks().trim().length()>0){
            //If NONE is anywhere in there then use the blank ad network
            if (pl.getCommasepadnetworks().toLowerCase().trim().indexOf(AdNetworkNone.ADNETWORKNAME.toLowerCase())>-1){
                logger.debug("setByPl() setting to None because pl.getCommasepadnetworks().toLowerCase().trim().indexOf(AdNetworkNone.ADNETWORKNAME.toLowerCase())>-1");
                Pagez.getUserSession().setAdNetworkName(AdNetworkNone.ADNETWORKNAME);
                return;
            }
            //If there's no comma just assign by the name
            if (pl.getCommasepadnetworks().indexOf(",")<=-1){
                logger.debug("setByPl() setting to "+pl.getCommasepadnetworks()+" because no commas in DB pl.getCommasepadnetworks().indexOf(\",\")<=-1");
                Pagez.getUserSession().setAdNetworkName(pl.getCommasepadnetworks().trim());
                return;
            }
            //Choose a random ad network from those in the list of acceptable pl ads
            int attempts = 0;
            int maxattempts = 100;
            boolean assignmentmade = false;
            while (attempts<maxattempts && !assignmentmade){
                attempts = attempts + 1;
                AdNetwork randomAdNetwork = AdNetworkFactory.getRandom();
                if (pl.getCommasepadnetworks().toLowerCase().indexOf(randomAdNetwork.getAdNetworkName().toLowerCase())>-1){
                    assignmentmade = true;
                    logger.debug("setByPl() setting to Random(but in list) because pl.getCommasepadnetworks().toLowerCase().indexOf(randomAdNetwork.getAdNetworkName().toLowerCase())>-1");
                    Pagez.getUserSession().setAdNetworkName(randomAdNetwork.getAdNetworkName());
                    return;
                }
            }
            //If we go through all this and none is assigned, use default
            if (!assignmentmade){
                logger.debug("setByPl() setting to Default because !assignmentmade");
                Pagez.getUserSession().setAdNetworkName(AdNetworkFactory.getDefaultAdNetwork().getAdNetworkName());
            }
        } else {
            //Blank commasepadnetworks in pl so... go with random
            logger.debug("setByPl() setting to None because pl.getCommasepadnetworks()==null || pl.getCommasepadnetworks().trim().length()>0");
            Pagez.getUserSession().setAdNetworkName(AdNetworkNone.ADNETWORKNAME);
        }
    }

    private static void safeReferers(HttpServletRequest request){
        Logger logger = Logger.getLogger(AssignAdNetwork.class);
        if (request!=null && request.getHeader("referer")!=null){
            boolean isFromRefererWhoShouldBeSafe = false;
            ArrayList<String> urlsThatShouldBeSafe = new ArrayList<String>();
            urlsThatShouldBeSafe.add("joereger.com");
            urlsThatShouldBeSafe.add("ideasdefine.com");
            //urlsThatShouldBeSafe.add("facebook.com");
            for (int i = 0; i < urlsThatShouldBeSafe.size(); i++) {
                String url = urlsThatShouldBeSafe.get(i);
                //If the url is anywhere in the referer show the safe ad network
                if (url.toLowerCase().indexOf(request.getHeader("referer").toLowerCase())>-1){
                    isFromRefererWhoShouldBeSafe = true;
                }
            }
            if (isFromRefererWhoShouldBeSafe){
                logger.debug("safeReferers() setting to AdSense because isFromRefererWhoShouldBeSafe=true");
                Pagez.getUserSession().setAdNetworkName(AdNetworkGoogleAdsense.ADNETWORKNAME);
            }
        }
    }











}
