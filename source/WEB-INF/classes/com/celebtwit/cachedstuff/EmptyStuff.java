package com.celebtwit.cachedstuff;


import com.celebtwit.dao.Pl;

import java.io.Serializable;
import java.util.Calendar;

/**
 * User: Joe Reger Jr
 * Date: Jan 29, 2008
 * Time: 2:03:07 PM
 */
public class EmptyStuff implements CachedStuff, Serializable {

    private Calendar refreshedTimestamp;
    private String html;

    private String var1 = "";
    private String var2 = "";

    public EmptyStuff(String var1, String var2){
        this.var1 = var1;
        this.var2 = var2;
    }

    public String getKey() {
        return "EmptyStuff-var1="+var1+"-var2="+var2;
    }

    public void refresh(Pl pl) {
        StringBuffer out = new StringBuffer();
        //Start Refresh

        //End Refresh
        html = out.toString();
        refreshedTimestamp = Calendar.getInstance();
    }

    public Calendar refreshedTimestamp() {
        return refreshedTimestamp;
    }

    public int maxAgeInMinutes() {
        return 5;
    }

    public String getHtml() {
        return html;
    }
}
