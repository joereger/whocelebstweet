package com.celebtwit.ads;


import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: Feb 2, 2010
 * Time: 7:34:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdNetworkFactory {

    private static final Random RANDOM = new Random();

    public static ArrayList<AdNetwork> getAllNetworks(){
        ArrayList<AdNetwork> out = new ArrayList<AdNetwork>();
        out.add(new AdNetworkGoogleAdsense());
        //out.add(new AdNetworkPassionDotCom());
        //Adnetworknone must always be last
        out.add(new AdNetworkNone());
        return out;
    }

    public static AdNetwork getDefaultAdNetwork(){
        return new AdNetworkGoogleAdsense();
    }

    public static AdNetwork getByName(String name){
        for (Iterator<AdNetwork> iterator = AdNetworkFactory.getAllNetworks().iterator(); iterator.hasNext();) {
            AdNetwork adNetwork = iterator.next();
            if (adNetwork.getAdNetworkName().equals(name)){
                return adNetwork;
            }
        }
        return getDefaultAdNetwork();
    }

    public static String getAllAsString(){
        StringBuffer out = new StringBuffer();
        for (Iterator<AdNetwork> iterator = AdNetworkFactory.getAllNetworks().iterator(); iterator.hasNext();) {
            AdNetwork adNetwork = iterator.next();
            out.append(adNetwork.getAdNetworkName()+",");
        }
        return out.toString();
    }

    public static AdNetwork getRandom(){
        Logger logger = Logger.getLogger(AdNetworkFactory.class);
        int numOfNetworks = getAllNetworks().size() - 1; //Minus 1 because we don't want to choose AdNetworkNone
        //logger.debug("numOfNetworks = "+numOfNetworks);
        int networkToGet = RANDOM.nextInt(numOfNetworks);
        //logger.debug("networkToGet = "+networkToGet);
        return getAllNetworks().get(networkToGet); 
    }
    

    public static void testRandom(){
        Logger logger = Logger.getLogger(AdNetworkFactory.class);
        int timesToRun = 100000;
        int countTotal = 0;
        int countGoogle = 0;
        int countAff = 0;
        int countPassion = 0;
        int countUnknown = 0;
        for (int i=0; i<timesToRun; i++){
            countTotal = countTotal + 1;
            AdNetwork adNetwork = AdNetworkFactory.getRandom();
            if (adNetwork.getAdNetworkName().equals(AdNetworkGoogleAdsense.ADNETWORKNAME)){
                countGoogle = countGoogle + 1;
            } else if (adNetwork.getAdNetworkName().equals(AdNetworkPassionDotCom.ADNETWORKNAME)){
                countAff = countAff + 1;
            } else if (adNetwork.getAdNetworkName().equals(AdNetworkPassionDotCom.ADNETWORKNAME)){
                countPassion = countPassion + 1;
            } else {
                countUnknown = countUnknown + 1;
            }
        }
        logger.debug("countTotal="+countTotal);
        logger.debug("countGoogle="+countGoogle);
        logger.debug("countAff="+countAff);
        logger.debug("countPassion="+countPassion);
        logger.debug("countUnknown="+countUnknown);
    }



}
