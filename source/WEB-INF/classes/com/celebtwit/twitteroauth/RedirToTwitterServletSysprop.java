package com.celebtwit.twitteroauth;


import com.celebtwit.htmlui.Pagez;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: Mar 25, 2010
 * Time: 6:23:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class RedirToTwitterServletSysprop extends HttpServlet {

    //consumer key and consumer secret are in /WEB-INF/classes/twitter4j.properties
    //configure servlet url in web.xml

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TwitterFactory twitterFactory = new TwitterFactory();
        Twitter twitter = twitterFactory.getInstance();
        Pagez.getUserSession().setTwitter(twitter);
        //request.getSession().setAttribute("twitter", twitter);
        try {
            StringBuffer callbackURL = request.getRequestURL();
            int index = callbackURL.lastIndexOf("/");
            callbackURL.replace(index, callbackURL.length(), "").append("/twittercallbacksysprop");
            RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL.toString());
            Pagez.getUserSession().setTwitterRequestToken(requestToken);
            //request.getSession().setAttribute("requestToken", requestToken);
            response.sendRedirect(requestToken.getAuthenticationURL());
        } catch (TwitterException e) {
            throw new ServletException(e);
        }
    }


}