<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmluibeans.PublicIndex" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.celebtwit.helpers.*" %>
<%@ page import="com.celebtwit.dao.Twitpost" %>
<%@ page import="com.celebtwit.htmluibeans.PublicIndexWhoPanel" %>
<%@ page import="com.celebtwit.htmluibeans.PublicIndexTweetlist" %>
<%@ page import="com.celebtwit.util.Num" %>
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
<%@ include file="/template/header.jsp" %>



<%if (!Pagez.getUserSession().isSisterPl()){%>
    <div class="roundedBox" style="width:630px;">
        <div style="float:right">
            <%String qs = "";%>
            <font class="tinyfont">
                <%String time = request.getParameter("time");%>
                <%if (time==null || time.equals("") || time.equals("null")){time="alltime";}%>
                <%String addToStyle = "";%>
                <%String boldStyle = "font-weight:bold; background:#ffffff;";%>
                <%if (time.equals("alltime")){addToStyle=boldStyle;}else{addToStyle="";}%>
                <a href="/index.jsp?time=alltime<%=qs%>" style="<%=addToStyle%>">all time</a> |
                <%if (time.equals("thismonth")){addToStyle=boldStyle;}else{addToStyle="";}%>
                <a href="/index.jsp?time=thismonth<%=qs%>" style="<%=addToStyle%>">this month</a> |
                <%if (time.equals("last31days")){addToStyle=boldStyle;}else{addToStyle="";}%>
                <a href="/index.jsp?time=last31days<%=qs%>" style="<%=addToStyle%>">last 31 days</a> |
                <%if (time.equals("thisweek")){addToStyle=boldStyle;}else{addToStyle="";}%>
                <a href="/index.jsp?time=thisweek<%=qs%>" style="<%=addToStyle%>">this week</a> |
                <%if (time.equals("last7days")){addToStyle=boldStyle;}else{addToStyle="";}%>
                <a href="/index.jsp?time=last7days<%=qs%>" style="<%=addToStyle%>">last 7 days</a> |
                <%if (time.equals("yesterday")){addToStyle=boldStyle;}else{addToStyle="";}%>
                <a href="/index.jsp?time=yesterday<%=qs%>" style="<%=addToStyle%>">yesterday</a> |
                <%if (time.equals("today")){addToStyle=boldStyle;}else{addToStyle="";}%>
                <a href="/index.jsp?time=today<%=qs%>" style="<%=addToStyle%>">today</a>
            </font>
        </div><br/>

        <%=PublicIndexWhoPanel.getHtml(Pagez.getUserSession().getPl(), request.getParameter("time"), request.getParameter("refresh"))%>

    </div>
<%}%>


<%if (!Pagez.getUserSession().isSisterPl()){%>
    <font class="largefont">recent <%=Pagez.getUserSession().getPl().getCelebiscalled()%> tweets</font>
    <br/><br/>
<%}%>
<table cellpadding="3" cellspacing="0" border="0" width="100%">
    <tr>
        <td valign="top">
            <div style="width:480px; padding: 0px; overflow:hidden;">
                    <%
                        int tweetsPage = 1;
                        if (Num.isinteger(request.getParameter("tweetsPage"))){ tweetsPage = Integer.parseInt(request.getParameter("tweetsPage")); }
                    %>
                    <%=PublicIndexTweetlist.getHtml(Pagez.getUserSession().getPl(), tweetsPage, request.getParameter("refresh"))%>
                    <br/><br/>
                    <a href="/index.jsp?tweetsPage=<%=tweetsPage+1%>"><font class="normalfont">older tweets >></font></a>
            </div>

        </td>
        <td valign="top" width="160">
            <%if (!Pagez.getUserSession().isSisterPl()){%>
                <!--<img src="/images/clear.gif" alt="" width="1" height="70"><br/>-->
            <%}%>
            <%=AdUtil.get160x600()%>
        </td>
    </tr>
</table>






<%@ include file="/template/footer.jsp" %>
