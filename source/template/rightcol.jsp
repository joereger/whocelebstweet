<%@ page import="org.hibernate.criterion.Restrictions" %>
<%@ page import="com.celebtwit.dao.Twit" %>
<%@ page import="com.celebtwit.dao.hibernate.HibernateUtil" %>
<%@ page import="org.hibernate.criterion.Order" %>
<%@ page import="com.celebtwit.htmlui.Pagez" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="com.celebtwit.dao.Pl" %>
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
                <font class="mediumfont">the <%=Pagez.getUserSession().getPl().getCelebiscalled()%>s</font><br/>
                <a href="/suggest.jsp"><font class="tinyfont">who're we missing? suggest somebody!</font></a><br/><br/>
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
                <a href="http://www.twitter.com/"><img src="/images/powered-by-twitter-badge.gif" alt="Powered by Twitter" width="145" height="15" border="0"></a>
            </div>