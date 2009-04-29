package com.celebtwit.util;

import org.apache.log4j.Logger;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.Cookie;
import java.util.*;

import sun.reflect.Reflection;

/**
 * User: Joe Reger Jr
 * Date: Jul 27, 2006
 * Time: 7:50:21 AM
 */
public class Util {

    public static List setToArrayList(Set set){
        List out = new ArrayList();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            Object o = iterator.next();
            out.add(o);
        }
        return out;
    }

    /**
     * Clean for appendreplacement.  Java is a real pain in the arse when it comes to dollar signs and dashes.
     * This method must be called on any appendreplacement string.
     */
     public static String cleanForAppendreplacement(String in){
        String out = "";
        if(in!=null){
            out=in.replaceAll("\\\\","\\\\\\\\").replaceAll("\\$", "\\\\\\$");
        }

        return out;
    }



    public static void logStackTrace(int levelsdeep){
        Logger logger = Logger.getLogger(Util.class.getName());
        logger.debug("logStackTrace("+levelsdeep+") begin");
        StackTraceElement[] elements = new Exception().getStackTrace();
        for (int i=0; i<elements.length; i++) {
            StackTraceElement element=elements[i];
            if (i<levelsdeep){
                logger.debug("class="+element.getClassName()+"."+element.getMethodName()+":"+element.getLineNumber());
            }
        }
        logger.debug("logStackTrace("+levelsdeep+") end");
    }

    public static String[] addToStringArray(String[] src, String str){
        if (src==null){
            src=new String[0];
        }

        String[] outArr = new String[src.length+1];
        for(int i=0; i < src.length; i++) {
            outArr[i]=src[i];
        }
        outArr[src.length]=str;
        return outArr;
    }

    public static ArrayList stringArrayToArrayList(String[] src){
        ArrayList<String> out = new ArrayList<String>();
        if (src!=null){
            for(int i=0; i < src.length; i++) {
                out.add(src[i]);
            }
        }
        return out;
    }



    public static boolean arrayContains(String[] array, String value){
        if (array!=null){
            for (int i = 0; i < array.length; i++) {
                String s = array[i];
                if (s.equals(value)){
                    return true;
                }
            }
        }
        return false;
    }

    public static String[] appendToEndOfStringArray(String[] in, String[] append){
        int outlength = 0;
        if (in!=null){
            outlength = outlength + in.length;
        }
        if (append!=null){
            outlength = outlength + append.length;
        }
        String[] out = new String[outlength];
        int outindex = 0;
        if (in!=null){
            for (int i = 0; i < in.length; i++) {
                out[outindex]=in[i];
                outindex = outindex + 1;
            }
        }
        if (append!=null){
            for (int i = 0; i < append.length; i++) {
                out[outindex]=append[i];
                outindex = outindex + 1;
            }
        }
        return out;
    }


    public static String truncateString(String instring, int maxlength){
        String outstring="";
        try{
            outstring = instring.substring(0,maxlength-1);
        } catch (Exception e) {
            outstring = instring;
        }
        return outstring;
    }

    public static Cookie[] addToCookieArray(Cookie[] src, Cookie str){
        if (src==null){
            src=new Cookie[0];
        }

        Cookie[] outArr = new Cookie[src.length+1];
        for(int i=0; i < src.length; i++) {
            outArr[i]=src[i];
        }
        outArr[src.length]=str;
        return outArr;
    }

    public static TreeMap treeSetToTreeMap(TreeSet ts){
        TreeMap tm = new TreeMap();
        for (Iterator iterator=ts.iterator(); iterator.hasNext();) {
            Object o=iterator.next();
            tm.put(o,o);
        }
        return tm;   
    }

    public static ArrayList treeSetToArrayList(TreeSet ts){
        ArrayList al = new ArrayList();
        for (Iterator iterator=ts.iterator(); iterator.hasNext();) {
            Object o=iterator.next();
            al.add(o);
        }
        return al;   
    }

    public static String[] arrayListToStringArray(ArrayList<String> in){
        String[] out = new String[0];
        if (in!=null){
            out = new String[in.size()];
            int i = 0;
            for (Iterator it = in.iterator(); it.hasNext(); ) {
                String var = (String)it.next();
                out[i] = var;
                i = i + 1;
            }
        }
        return out;
    }

    public static ArrayList<Integer> commaSeparatedStringListToIntegerArrayList(String list){
        ArrayList<Integer> al = new ArrayList<Integer>();
        if (list!=null){
            String[] split = list.split(",");
            if(split!=null){
                for (int i = 0; i < split.length; i++) {
                    String s = split[i];
                    if (Num.isinteger(s)){
                        al.add(Integer.parseInt(s));
                    }
                }
            }
        }
        return al;
    }

    public static String integerArrayListToString(ArrayList<Integer> in){
        StringBuffer out = new StringBuffer();
        if (in!=null){
            for (Iterator it = in.iterator(); it.hasNext(); ) {
                Integer var = (Integer)it.next();
                out.append(String.valueOf(var));
                if (it.hasNext()){
                    out.append(",");
                }
            }
        }
        return out.toString();
    }

    public static String getFilenameExtension(String infilename){
        return FilenameUtils.getExtension(infilename);
    }

    /**
     * Returns the base filename of a file.  This is essentially the filename minus the extension.
     */
    public static String getFilenameBase(String infilename){
        String filename = FilenameUtils.getName(infilename);
        String filenamebase = FilenameUtils.removeExtension(filename);
        return filenamebase;
    }


}
