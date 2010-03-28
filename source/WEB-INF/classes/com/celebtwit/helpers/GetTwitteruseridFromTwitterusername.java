package com.celebtwit.helpers;

import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import org.apache.log4j.Logger;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.http.AccessToken;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: Jan 16, 2010
 * Time: 10:44:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetTwitteruseridFromTwitterusername {


    public static String get(String twitterusername, Pl pl){
        return get(FindTwitFromTwitterusername.find(twitterusername), pl);
    }

    public static String get(Twit twit, Pl pl){
        Logger logger = Logger.getLogger(GetTwitteruseridFromTwitterusername.class);
        if (twit==null || twit.getTwitterusername().equals("")){
            return "";
        }
        //If we have a valid twitteruserid stored in the twit already
        if (twit.getTwitteruserid()!=null && twit.getTwitteruserid().length()>0){
            return twit.getTwitteruserid();
        } else {
            //Need to go to the api
            try{
                TwitterFactory twitterFactory = new TwitterFactory();
                Twitter twitter = twitterFactory.getInstance();
                AccessToken accessToken = new AccessToken(pl.getTwitteraccesstoken(), pl.getTwitteraccesstokensecret());
                twitter.setOAuthAccessToken(accessToken);
                User user = twitter.showUser(twit.getTwitterusername());
                if (user!=null && user.getId()>0){
                    twit.setTwitteruserid(String.valueOf(user.getId()));
                    twit.save();
                }
                return String.valueOf(user.getId());
            } catch (Exception ex){
                logger.debug("", ex);
            }
        }
        return "";
    }


}
