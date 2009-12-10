package com.celebtwit.helpers;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.celebtwit.dao.Twitpost;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.htmlui.Pagez;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.util.List;
import java.util.Iterator;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: Joe Reger Jr
 * Date: Jul 14, 2009
 * Time: 8:39:06 PM
 */
public class Sitemap extends HttpServlet {

    Logger logger = Logger.getLogger(this.getClass().getName());

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = Logger.getLogger(Sitemap.class);
        PrintWriter out = response.getWriter();
        Pl pl = Pagez.getUserSession().getPl();

        out.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.print("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");

        out.print("<url>");
        out.print("<loc>"+"http://"+pl.getCustomdomain1()+"/"+"</loc>");
        out.print("<changefreq>hourly</changefreq>");
        out.print("<priority>1.0</priority>");
        out.print("</url>");

        out.print("<url>");
        out.print("<loc>"+"http://"+pl.getCustomdomain1()+"/suggest.jsp"+"</loc>");
        out.print("<changefreq>monthly</changefreq>");
        out.print("<priority>.5</priority>");
        out.print("</url>");


        List<Twit> twits = HibernateUtil.getSession().createCriteria(Twit.class)
            .addOrder(Order.desc("twitid"))
            .add(Restrictions.eq("isceleb", true))
            .createCriteria("twitpls")
            .add(Restrictions.eq("plid", pl.getPlid()))
            .setCacheable(true)
            .list();
        for (Iterator<Twit> tpIt=twits.iterator(); tpIt.hasNext();) {
            Twit twit=tpIt.next();

            out.print("<url>");
            out.print("<loc>"+"http://"+pl.getCustomdomain1()+"/twitter/"+twit.getTwitterusername()+"/"+"</loc>");
            out.print("<changefreq>hourly</changefreq>");
            out.print("<priority>.9</priority>");
            out.print("</url>");

            out.print("<url>");
            out.print("<loc>"+"http://"+pl.getCustomdomain1()+"/twitter/"+twit.getTwitterusername()+"/who/"+"</loc>");
            out.print("<changefreq>daily</changefreq>");
            out.print("<priority>.7</priority>");
            out.print("</url>");

            out.print("<url>");
            out.print("<loc>"+"http://"+pl.getCustomdomain1()+"/twitter/"+twit.getTwitterusername()+"/when/thismonth/"+"</loc>");
            out.print("<changefreq>daily</changefreq>");
            out.print("<priority>.6</priority>");
            out.print("</url>");

            out.print("<url>");
            out.print("<loc>"+"http://"+pl.getCustomdomain1()+"/twitter/"+twit.getTwitterusername()+"/when/thisweek/"+"</loc>");
            out.print("<changefreq>daily</changefreq>");
            out.print("<priority>.6</priority>");
            out.print("</url>");

            out.print("<url>");
            out.print("<loc>"+"http://"+pl.getCustomdomain1()+"/twitter/"+twit.getTwitterusername()+"/when/yesterday/"+"</loc>");
            out.print("<changefreq>daily</changefreq>");
            out.print("<priority>.5</priority>");
            out.print("</url>");

            out.print("<url>");
            out.print("<loc>"+"http://"+pl.getCustomdomain1()+"/twitter/"+twit.getTwitterusername()+"/when/today/"+"</loc>");
            out.print("<changefreq>daily</changefreq>");
            out.print("<priority>.5</priority>");
            out.print("</url>");

            
        }


        out.print("</urlset>");
    }






}