package com.celebtwit.htmluibeans;



import java.io.Serializable;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Feb 21, 2007
 * Time: 11:26:03 AM
 */
public class SystemStats implements Serializable {

    //BE SURE TO SYNC CODE HERE WITH MAIN SystemStats in scheduledjobs
    //These need to be here because of direct POJO injection by the framework
    private int totalusers=com.celebtwit.scheduledjobs.SystemStats.getTotalusers();

    public SystemStats(){}

    public void initBean(){
        
    }

    public int getTotalusers() {
        return com.celebtwit.scheduledjobs.SystemStats.getTotalusers();
    }

    public void setTotalusers(int totalusers) {

    }



    




}
