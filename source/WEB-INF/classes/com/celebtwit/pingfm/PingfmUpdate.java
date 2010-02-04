package com.celebtwit.pingfm;

import com.celebtwit.threadpool.ThreadPool;
import org.apache.log4j.Logger;
import org.indrio.pingfm.PingFmService;
import org.indrio.pingfm.beans.Message;
import org.indrio.pingfm.impl.PingFmServiceImpl;

/**
 * User: Joe Reger Jr
 * Date: May 24, 2007
 * Time: 12:25:50 PM
 */
public class PingfmUpdate implements Runnable {


    private static ThreadPool tp;
    private String pingfmapikey = "";
    private String updatetext = "";
    private int id=0;

    private static String DEVELOPERKEY = "ff44031b33c2fbf70315e2c82e71edef";



    public PingfmUpdate(String pingfmapikey, String updatetext, int id){
        this.pingfmapikey = pingfmapikey;
        this.updatetext = updatetext;
        this.id = id;
    }


    public void run(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("run() called.");

        try{
        
            PingFmService pingFmService = new PingFmServiceImpl(DEVELOPERKEY, pingfmapikey);
            Message message = new Message(String.valueOf(id), "default", updatetext, updatetext);
            pingFmService.postMessage(message, "default", false);


        } catch (Exception ex){
            logger.error("", ex);
        }

        logger.debug("done processing.");
    }


    public void update(){
        if (tp ==null){
            tp= new ThreadPool(15);
        }
        tp.assign(this);
    }



}
