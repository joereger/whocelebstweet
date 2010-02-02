package com.celebtwit.ads;

import com.celebtwit.dao.Pl;
import com.celebtwit.htmlui.Pagez;

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
        //Set to default
        Pagez.getUserSession().setAdNetworkName(AdNetworkFactory.getDefaultAdNetwork().getAdNetworkName());
        //Only allow anything outside of the default if this is coming from google referer
        if (request.getHeader("referer")!=null && request.getHeader("referer").indexOf("google")>-1){
            //Set by pl
            setByPl(request);
            //If referrer should be safe
            safeReferers(request);
        }
        //If GoogleBot
        if (request.getHeader("user-agent")!=null){
            if (request.getHeader("user-agent").indexOf("googlebot")>-1){
                Pagez.getUserSession().setAdNetworkName(AdNetworkGoogleAdsense.ADNETWORKNAME);
            }
        }
        //Override with url param = safe=1
        if (request.getParameter("safe")!=null && request.getParameter("safe").equals("1")){
            Pagez.getUserSession().setAdNetworkName(AdNetworkGoogleAdsense.ADNETWORKNAME);
        }
        //If manual set in url
        if (request.getParameter("ads")!=null){
            AdNetwork adNetwork = AdNetworkFactory.getByName(request.getParameter("ads"));
            if (adNetwork!=null){
                Pagez.getUserSession().setAdNetworkName(adNetwork.getAdNetworkName());
            }
        }
    }

    private static void setByPl(HttpServletRequest request){
        Pl pl = Pagez.getUserSession().getPl();
        if (pl.getCommasepadnetworks()!=null && pl.getCommasepadnetworks().trim().length()>0){
            //If NONE is anywhere in there then use the blank ad network
            if (pl.getCommasepadnetworks().toLowerCase().indexOf(AdNetworkNone.ADNETWORKNAME.toLowerCase())>-1){
                Pagez.getUserSession().setAdNetworkName(AdNetworkNone.ADNETWORKNAME);
                return;
            }
            //Choose a random ad network
            int attempts = 0;
            int maxattempts = 100;
            boolean assignmentmade = false;
            while (attempts<maxattempts && !assignmentmade){
                attempts = attempts + 1;
                AdNetwork randomAdNetwork = AdNetworkFactory.getRandom();
                if (pl.getCommasepadnetworks().toLowerCase().indexOf(randomAdNetwork.getAdNetworkName().toLowerCase())>-1){
                    assignmentmade = true;
                    Pagez.getUserSession().setAdNetworkName(randomAdNetwork.getAdNetworkName());
                    return;
                }
            }
            //If we go through all this and none is assigned, use default
            if (!assignmentmade){
                Pagez.getUserSession().setAdNetworkName(AdNetworkFactory.getDefaultAdNetwork().getAdNetworkName());
            }
        } else {
            //Blank commasepadnetworks in pl so... go with random
            Pagez.getUserSession().setAdNetworkName(AdNetworkFactory.getRandom().getAdNetworkName());
        }

    }

    private static void safeReferers(HttpServletRequest request){
        if (request.getHeader("referer")!=null){
            boolean isFromRefererWhoShouldBeSafe = false;
            ArrayList<String> urlsThatShouldBeSafe = new ArrayList<String>();
            urlsThatShouldBeSafe.add("joereger.com");
            urlsThatShouldBeSafe.add("ideasdefine.com");
            urlsThatShouldBeSafe.add("facebook.com");
            for (int i = 0; i < urlsThatShouldBeSafe.size(); i++) {
                String url = urlsThatShouldBeSafe.get(i);
                //If the url is anywhere in the referer show the safe ad network
                if (url.toLowerCase().indexOf(request.getHeader("referer").toLowerCase())>-1){
                    isFromRefererWhoShouldBeSafe = true;
                }
            }
            if (isFromRefererWhoShouldBeSafe){
                Pagez.getUserSession().setAdNetworkName(AdNetworkGoogleAdsense.ADNETWORKNAME);
            }
        }
    }











}
