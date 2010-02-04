<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.celebtwit.helpers.*" %>
<%@ page import="com.celebtwit.systemprops.SystemProperty" %>
<%@ page import="com.celebtwit.dao.Twitpost" %>
<%@ page import="com.celebtwit.util.Str" %>
<%@ page import="com.celebtwit.util.Time" %>
<%@ page import="com.celebtwit.embed.JsCelebMentions" %>
<%@ page import="com.celebtwit.embed.JsDifferentCelebs" %>
<%@ page import="com.celebtwit.util.Num" %>
<%@ page import="com.celebtwit.util.Util" %>
<%@ page import="com.celebtwit.htmluibeans.*" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.celebtwit.ads.AdUtil" %>

<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "";
String metaKeywords = "";
String metaDescription = "";
String navtab = "home";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>

<%
String filename = "celebsTweetedByMostDifferentCelebs";
String typeTitle = ""+Pagez.getUserSession().getPl().getCelebiscalled()+"s tweeted by the most different "+Pagez.getUserSession().getPl().getCelebiscalled()+"s";
String titleTime = "";
if (1==1){
    String time = request.getParameter("time");
    if (time==null || time.equals("") || time.equals("null")){time="alltime";}
    if (time.equals("alltime")){titleTime="All Time";}
    if (time.equals("thismonth")){titleTime="This Month";}
    if (time.equals("last31days")){titleTime="Last 31 Days";}
    if (time.equals("thisweek")){titleTime="This Week";}
    if (time.equals("last7days")){titleTime="Last 7 Days";}
    if (time.equals("yesterday")){titleTime="Yesterday";}
    if (time.equals("today")){titleTime="Today";}
}
pagetitle = typeTitle+ ": Twitter Stats for "+titleTime+"";
%>



<%@ include file="/template/header.jsp" %>



            <div class="roundedBox" style="width:630px;">
                <center><font class="largefont" style="font-size:30px;"><%=typeTitle%></font></center><br/>
                <table cellpadding="3" cellspacing="0" border="0" width="100%">
                    <tr>
                        <td valign="top">
                            <div>
                                <font class="tinyfont">
                                    <%String qs = "";%>
                                    <%String time = request.getParameter("time");%>
                                    <%if (time==null || time.equals("") || time.equals("null")){time="alltime";}%>
                                    <%String addToStyle = "";%>
                                    <%String boldStyle = "font-weight:bold; background:#ffffff;";%>
                                    <%if (time.equals("alltime")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                    <a href="/stats/<%=filename%>/when/alltime/<%=qs%>" style="<%=addToStyle%>">all time</a> |
                                    <%if (time.equals("thismonth")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                    <a href="/stats/<%=filename%>/when/thismonth/<%=qs%>" style="<%=addToStyle%>">this month</a> |
                                    <%if (time.equals("last31days")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                    <a href="/stats/<%=filename%>/when/last31days/<%=qs%>" style="<%=addToStyle%>">last 31 days</a> |
                                    <%if (time.equals("thisweek")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                    <a href="/stats/<%=filename%>/when/thisweek/<%=qs%>" style="<%=addToStyle%>">this week</a> |
                                    <%if (time.equals("last7days")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                    <a href="/stats/<%=filename%>/when/last7days/<%=qs%>" style="<%=addToStyle%>">last 7 days</a> |
                                    <%if (time.equals("yesterday")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                    <a href="/stats/<%=filename%>/when/yesterday/<%=qs%>" style="<%=addToStyle%>">yesterday</a> |
                                    <%if (time.equals("today")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                    <a href="/stats/<%=filename%>/when/today/<%=qs%>" style="<%=addToStyle%>">today</a>
                                </font>
                            </div>
                            <%String statsTime = "";%>
                            <%if (time.equals("alltime")){statsTime="all time";}%>
                            <%if (time.equals("thismonth")){statsTime="this month";}%>
                            <%if (time.equals("last31days")){statsTime="last 31 days";}%>
                            <%if (time.equals("thisweek")){statsTime="this week";}%>
                            <%if (time.equals("last7days")){statsTime="last 7 days";}%>
                            <%if (time.equals("yesterday")){statsTime="yesterday";}%>
                            <%if (time.equals("today")){statsTime="today";}%>
                            <font class="largefont" style="font-size:20px;">stats <%=statsTime%></font>
                            <br clear="all"/><br/><br/>
                            <%=StatsOutputCached.celebsTweetedByMostDifferentCelebs(Pagez.getUserSession().getPl(), request.getParameter("time"), 500, Util.booleanFromString(request.getParameter("refresh")), true)%>
                        </td>
                        <td valign="top">
                            <img src="/images/clear.gif" alt="" width="1" height="85"/><br/>
                            <%=AdUtil.get160x600()%>
                        </td>
                    </tr>
                </table>
            </div>











<%@ include file="/template/footer.jsp" %>
