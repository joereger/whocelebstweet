package com.celebtwit.api.server;

import java.util.ArrayList;

/**
 * User: Joe Reger Jr
 * Date: Feb 10, 2009
 * Time: 2:45:12 PM
 */
public class ServerMethods {

    public static byte[] get(String username, String password, String bucketname, String key){
        return null;
    }

    public static String put(String username, String password, String bucketname, String key, byte[] bytes){
        return "";
    }

    public static String delete(String username, String password, String bucketname, String key){
        return "";
    }

    public static String keyExists(String username, String password, String bucketname, String key){
        return "";
    }

    public static ArrayList<String> getKeys(String username, String password, String bucketname){
        return new ArrayList<String>();
    }

}
