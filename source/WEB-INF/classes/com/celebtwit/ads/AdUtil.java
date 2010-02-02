package com.celebtwit.ads;

import com.celebtwit.htmlui.Pagez;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: Feb 2, 2010
 * Time: 7:27:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdUtil {

    private static String getAdNetworkNameFromSession(){
        if (Pagez.getUserSession()!=null){
            if (Pagez.getUserSession().getAdNetworkName()!=null && Pagez.getUserSession().getAdNetworkName().length()>0){
                return Pagez.getUserSession().getAdNetworkName();
            }
        }
        return AdNetworkFactory.getDefaultAdNetwork().getAdNetworkName();
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
