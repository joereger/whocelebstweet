<%@ page import="org.hibernate.criterion.Restrictions" %>
<%@ page import="java.util.List" %>
<%@ page import="com.celebtwit.dao.hibernate.HibernateUtil" %>
<%@ page import="com.celebtwit.dao.Twit" %>
<%@ page import="org.hibernate.criterion.Order" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.celebtwit.dao.Pl" %>
</td>
        <td valign="top" width="220">
            

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
                <font class="mediumfont">the <%=Pagez.getUserSession().getPl().getCelebiscalled()%>s</font><br/><br/>
                <%
                if (true){
                    List<Twit> celebs = HibernateUtil.getSession().createCriteria(Twit.class)
                                                   .add(Restrictions.eq("isceleb", true))
                                                   .addOrder(Order.asc("realname"))
                                                   .createCriteria("twitpls")
                                                   .add(Restrictions.eq("plid", Pagez.getUserSession().getPl().getPlid()))
                                                   .setMaxResults(1000)
                                                   .setCacheable(true)
                                                   .list();
                    for (Iterator<Twit> iterator=celebs.iterator(); iterator.hasNext();) {
                        Twit twitFooter=iterator.next();
                        %><a href="/twitter/<%=twitFooter.getTwitterusername()%>/"><font class="normalfont" style="font-weight:bold; color:#000000;">@<%=twitFooter.getRealname()%></font></a><br/><%
                    }
                }
                %>
                <br/><br/>
                <font class="mediumfont">have you checked out</font><br/><br/>
                <%
                if (true){
                    List<Pl> plsFooter = HibernateUtil.getSession().createCriteria(Pl.class)
                                                   .add(Restrictions.ne("plid", Pagez.getUserSession().getPl().getPlid()))
                                                   .addOrder(Order.asc("name"))
                                                   .setMaxResults(1000)
                                                   .setCacheable(true)
                                                   .list();
                    for (Iterator<Pl> iterator=plsFooter.iterator(); iterator.hasNext();) {
                        Pl plFt=iterator.next();
                        %><a href="http://<%=plFt.getCustomdomain1()%>/"><font class="normalfont" style="font-weight:bold; color:#000000;"><%=plFt.getName()%></font></a><br/><%
                    }
                }
                %>
                <br/><br/>
                <a href="http://www.twitter.com/"><img src="/images/powered-by-twitter-badge.gif" alt="Powered by Twitter" width="145" height="15" border="0"></a>
            </div>
            
        </td>
    </tr>
</table>


<!-- End Body -->
</div>
</td>
</tr>
</table>

<br/><br/>
<table width="100%" cellspacing="0" border="0" cellpadding="0">
<tr>
    <tr>
        <td valign="top" align="right">
            <center><font class="tinyfont">Copyright 2009. All rights reserved.</font> <font class="tinyfont" style="color: #cccccc; padding-right: 10px;">At Your Service is a Server Called: <%=InstanceProperties.getInstancename()%> which built this page in: <a href="/pageperformance.jsp" style="color: #999999;"><%=Pagez.getElapsedTime()%> milliseconds</a></font></center>
        </td>
    </tr>
</table>
<br/>

    <%--</center>--%>

    <script type="text/javascript">
    var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
    document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
    </script>
    <script type="text/javascript">
    try {
    var pageTracker = _gat._getTracker("UA-208946-9");
    pageTracker._trackPageview();
    } catch(err) {}</script>


</body>
</html>
