<%@ page import="org.hibernate.criterion.Restrictions" %>
<%@ page import="com.celebtwit.dao.Twit" %>
<%@ page import="com.celebtwit.dao.hibernate.HibernateUtil" %>
<%@ page import="org.hibernate.criterion.Order" %>
<%@ page import="com.celebtwit.htmlui.Pagez" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="com.celebtwit.dao.Pl" %>
<%@ page import="com.celebtwit.htmluibeans.PublicRightcolListCelebs" %>
<div class="roundedBox" style="width:220px;">
    <!--<center>-->
    <font class="mediumfont">have <b>they</b> tweeted <b>you</b>?</font>
    <br/><br/>

    <form action="/twitterusernameredirect.jsp" method="get">
        <font class="normalfont" style="font-weight:bold;">enter your twitter username</font>
        <br/><input type="text" name="twitterusername" value="">
        <input type="submit" value="go">
        <br/><font class="tinyfont" style="font-weight:bold;">example "joereger34"</font>
    </form>
    <!--</center>-->
</div>

<div class="roundedBoxXXX" style="width:220px; padding: 28px;">
    <a href="/suggest.jsp"><font class="normalfont" style="font-weight:bold;">who're we missing?<br/>suggest
        somebody!</font></a><br/>
    <br/>
    <font class="mediumfont">the <%=Pagez.getUserSession().getPl().getCelebiscalled()%>s</font><br/><br/>

                <%=PublicRightcolListCelebs.getHtml(Pagez.getUserSession().getPl())%>
                <br/><br/>
                <font class="mediumfont">check these out too</font><br/><br/>
                <%
                if (true){
                    List<Pl> plsFooter = HibernateUtil.getSession().createCriteria(Pl.class)
                                                   .addOrder(Order.asc("name"))
                                                   .setMaxResults(1000)
                                                   .setCacheable(true)
                                                   .list();
                    //Iterate first time for sister domains
                    for (Iterator<Pl> iterator=plsFooter.iterator(); iterator.hasNext();) {
                        Pl plFt=iterator.next();
                        if (plFt.getSisterdomain1().length()>0 && plFt.getSistername().length()>0){
                            %><a href="http://<%=plFt.getSisterdomain1()%>/"><font class="normalfont" style="font-weight:bold; color:#000000;"><%=plFt.getSistername()%></font></a><br/><%
                        }
                    }
                    %><br/><%
                    //Iterate second time for who domains
                    for (Iterator<Pl> iterator=plsFooter.iterator(); iterator.hasNext();) {
                        Pl plFt=iterator.next();
                        %><a href="http://<%=plFt.getCustomdomain1()%>/"><font class="normalfont" style="font-weight:bold; color:#000000;"><%=plFt.getName()%></font></a><br/><%
                    }
                }
                %>
                <br/><br/>
                <%if (Pagez.getUserSession().getPl().getTwitterusername().length()>0){%>
                    <a href="http://www.twitter.com/<%=Pagez.getUserSession().getPl().getTwitterusername()%>/" target="_blank"><font class="normalfont" style="font-weight:bold;">us on twitter!</font></a><br/>
                <%}%>
                <br/><br/>
                <a href="http://www.twitter.com/" target="_blank"><img src="/images/powered-by-twitter-badge.gif" alt="Powered by Twitter" width="145" height="15" border="0"></a>
            </div>