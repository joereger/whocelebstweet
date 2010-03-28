package com.celebtwit.twitteroauth;


import com.celebtwit.dao.Pl;
import com.celebtwit.htmlui.Pagez;
import com.celebtwit.util.Num;
import org.apache.log4j.Logger;
import twitter4j.Twitter;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: Mar 25, 2010
 * Time: 6:32:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallbackServlet  extends HttpServlet {

    //consumer key and consumer secret are in /WEB-INF/classes/twitter4j.properties
    //configure servlet url in web.xml

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        //Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
        //RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        Twitter twitter = Pagez.getUserSession().getTwitter();
        RequestToken requestToken = Pagez.getUserSession().getTwitterRequestToken();
        String verifier = request.getParameter("oauth_verifier");
        try {
            AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
            Pagez.getUserSession().setTwitterRequestToken(null);
            //request.getSession().removeAttribute("requestToken");
            logger.debug("accessToken.getToken()="+accessToken.getToken());
            logger.debug("accessToken.getTokenSecret()="+accessToken.getTokenSecret());
            logger.debug("request.getParameter(\"plid\")="+request.getParameter("plid"));
            if (Num.isinteger(request.getParameter("plid"))){
                Pl pl = Pl.get(Integer.parseInt(request.getParameter("plid")));
                if (pl!=null && pl.getPlid()>0 && Pagez.getUserSession().getUser()!=null && pl.canEdit(Pagez.getUserSession().getUser())){
                    pl.setTwitteraccesstoken(accessToken.getToken());
                    pl.setTwitteraccesstokensecret(accessToken.getTokenSecret());
                    twitter4j.User twitterUser = twitter.verifyCredentials();
                    pl.setTwitterusername(twitterUser.getScreenName());
                    pl.save();
                }
            }
        } catch (Exception ex) {
            logger.error("", ex);
        }
        Pagez.getUserSession().setMessage("You've successfully authorized Twitter!");
        response.sendRedirect("/sysadmin/privatelabeledit.jsp?plid="+request.getParameter("plid"));
    }


}
