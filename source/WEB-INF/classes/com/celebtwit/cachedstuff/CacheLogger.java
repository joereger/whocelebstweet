package com.celebtwit.cachedstuff;

import com.celebtwit.systemprops.WebAppRootDir;
import com.celebtwit.util.Time;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: 3/24/11
 * Time: 8:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class CacheLogger {


    public static void log(String cacheProvider, String group, String key, String msg, boolean isSuccess){
        Logger logger = Logger.getLogger(CacheLogger.class);
        File fileAll = new File(WebAppRootDir.getWebAppRootPath()+"cache-all.log");
        File fileSuccess = new File(WebAppRootDir.getWebAppRootPath()+"cache-success.log");
        File fileFail = new File(WebAppRootDir.getWebAppRootPath()+"cache-fail.log");
        try{
            Calendar nowEST = Time.nowInUserTimezone("EST");
            String nowESTStr = Time.dateformatcompactwithtime(nowEST);
            String sucFaiFlag = "GOOD";
            if (!isSuccess){ sucFaiFlag = "FAIL";}

            //Build debug string
            String addToLog = nowESTStr+":"+sucFaiFlag+":"+cacheProvider+" (/"+group+"/"+key+") "+msg;

            //Do the logging
            appendLine(fileAll, addToLog);
            if (isSuccess){
                appendLine(fileSuccess, addToLog);
            } else {
                appendLine(fileFail, addToLog);
            }
        } catch (Exception ex){
            logger.error("", ex);
        }
    }

    private static void appendLine(File file, String line){
        Logger logger = Logger.getLogger(CacheLogger.class);

        BufferedWriter bw = null;
        try {
             bw = new BufferedWriter(new FileWriter(file, true));
             bw.write(line);
             bw.newLine();
             bw.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (bw != null){
                try {
                    bw.close();
                } catch (IOException ioe2) {

                }
            }
        }

    }


}
