package com.celebtwit.ads;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: Feb 2, 2010
 * Time: 7:31:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdNetworkNone implements AdNetwork {

    public static String ADNETWORKNAME = "None";

    public String getAdNetworkName() {
        return ADNETWORKNAME;
    }

    public String get160x600() {
        StringBuffer out = new StringBuffer();
        out.append("");
        return out.toString();
    }

    public String get336x280() {
        StringBuffer out = new StringBuffer();
        out.append("");
        return out.toString();
    }

    public String get160x90() {
        StringBuffer out = new StringBuffer();
        out.append("");
        return out.toString();
    }

    public String get234x60() {
        StringBuffer out = new StringBuffer();
        out.append("");
        return out.toString();
    }

    public String get300x250() {
        StringBuffer out = new StringBuffer();
        out.append("");
        return out.toString();
    }

    public String getCornerPeel() {
        StringBuffer out = new StringBuffer();
        out.append("");
        return out.toString();
    }

    public String getFloaterBanner() {
        StringBuffer out = new StringBuffer();
        out.append("");
        return out.toString();
    }

    public String get100percentX200() {
        StringBuffer out = new StringBuffer();
        out.append("");
        return out.toString();
    }

    public String get400x600() {
        StringBuffer out = new StringBuffer();
        out.append("");
        return out.toString();
    }
}