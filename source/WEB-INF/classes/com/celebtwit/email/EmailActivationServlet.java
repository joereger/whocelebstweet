package com.celebtwit.email;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.awt.image.BufferedImage;

import com.celebtwit.util.jcaptcha.CaptchaServiceSingleton;
import com.celebtwit.util.GeneralException;
import com.celebtwit.dao.User;
import com.celebtwit.systemprops.SystemProperty;
import com.celebtwit.htmlui.Pagez;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.octo.captcha.service.CaptchaServiceException;

/**
 * Serves a captcha image
 */
public class EmailActivationServlet extends HttpServlet {

    Logger logger = Logger.getLogger(this.getClass().getName());

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        User user=null;
        if (request.getParameter("u")!=null && com.celebtwit.util.Num.isinteger(request.getParameter("u"))){
            user = User.get(Integer.parseInt(request.getParameter("u")));
        }

        String emailactivationkey="";
        if (request.getParameter("k")!=null){
            emailactivationkey = request.getParameter("k");
        }

        if (user!=null && user.getEmailactivationkey().trim().equals(emailactivationkey.trim())){
            user.setIsactivatedbyemail(true);
            try{user.save();} catch (Exception ex){logger.error("",ex);}

            //@todo send a welcome email message after successful email activation
            if(user.getFacebookuserid()<=0){
                Pagez.getUserSession().setMessage("Email activation was successful!  Your account is ready to roll!");
                response.sendRedirect("/account/index.jsp");
                return;
            } else {
                //response.sendRedirect("http://apps.facebook.com/"+ SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_APP_NAME)+"/");
                return;
            }
        } else {
            response.sendRedirect("/emailactivationfail.jsp");
            return;
        }


    }
}
