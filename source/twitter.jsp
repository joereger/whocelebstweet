<%@ page import="com.celebtwit.cachedstuff.CachedStuff" %>
<%@ page import="com.celebtwit.cachedstuff.GetCachedStuff" %>
<%@ page import="com.celebtwit.cachedstuff.TwitterTweetlist" %>
<%@ page import="com.celebtwit.helpers.FindTwitFromTwitterusername" %>
<%@ page import="com.celebtwit.helpers.StatsOutputCached" %>
<%@ page import="com.celebtwit.util.Num" %>
<%@ page import="com.celebtwit.util.Util" %>
<%@ page import="org.apache.log4j.Logger" %>

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
Twit twit = FindTwitFromTwitterusername.find(request.getParameter("twitterusername"));
String twitterusername =FindTwitFromTwitterusername.cleanTwitterusername(request.getParameter("twitterusername"));
String twitimageurl = "/images/clear.gif";
String pagetitleName = twitterusername;
if (twit!=null){
    twitterusername = twit.getTwitterusername();
    twitimageurl = twit.getProfile_image_url();
    if (twit.getIsceleb()){
        pagetitleName = twit.getRealname();
    }
}
//Get bigger version of the profile image
twitimageurl = twitimageurl.replaceAll("_normal", "");
%>
<%
pagetitle = pagetitleName + "'s Twitter Tweets on "+Pagez.getUserSession().getPl().getName()+"!  Always up-to-date!";
if (Pagez.getUserSession().isSisterPl()){
    pagetitle = pagetitleName + "'s Twitter Tweets on "+Pagez.getUserSession().getPl().getSistername()+"! Always up-to-date!";
}
%>
<%
if (twit!=null && twit.getIsceleb()){
    metaDescription = "Twitter updates by "+twit.getRealname()+".  See Tweets, profile pictures and statistics of who they Tweet. "+twit.getDescription();
} else {
    metaDescription = "Twitter updates by " + twitterusername+".  We track and create Twitter statistics.";
}
%>
<%
if (twit!=null && twit.getIsceleb()){
    metaKeywords = twit.getDescription();
}
%>
<%@ include file="/template/header.jsp" %>

<%--<%--%>
    <%--AdNetworkFactory.testRandom();--%>
<%--%>--%>
<%--RANDOM TEST DONE!--%>


<%
String subnav_page = "tweets";
String subnav_title = "@"+twitterusername;
if (twit!=null && twit.getIsceleb()){subnav_title = twit.getRealname();}
String subnav_twitterusername = twitterusername;
%>
<%@ include file="/celeb_subnav.jsp" %>




<table cellpadding="3" cellspacing="0" border="0" width="100%">
    <tr>
        <td valign="top" width="200">


            <%
            String sidebar_twitterusername = twitterusername;
            Twit sidebar_twit = twit;
            String sidebar_twitimageurl = twitimageurl;
            String sidebar_pageurl = "http://"+ Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/";
            %>
            <%@ include file="/celeb_sidebar.jsp" %>

        </td>
        <td valign="top">
            <!-- Start Middle Col -->
            <%if (twit!=null && twit.getTwitid()>0 && twit.getIsceleb()){%>
                    <%--<font class="mediumfont"><%=twit.getRealname()%>'s recent tweets</font>--%>
                    <%--<br/><br/>--%>
                    <table cellpadding="3" cellspacing="0" border="0" width="100%">
                        <tr>
                            <td valign="top">
                                <div class="roundedBoxNoRound" style="width:410px; overflow:hidden;">
                                        <%
                                            int tweetsPage = 1;
                                            if (Num.isinteger(request.getParameter("tweetsPage"))){ tweetsPage = Integer.parseInt(request.getParameter("tweetsPage")); }
                                        %>
                                        <%
                                        CachedStuff cs = new TwitterTweetlist(twit, tweetsPage, Pagez.getUserSession().getAdNetworkName());
                                        TwitterTweetlist obj = (TwitterTweetlist) GetCachedStuff.get(cs, Pagez.getUserSession().getPl());
                                        String tweetlist = obj.getHtml();
                                        %>
                                        <%=tweetlist%>
                                        <br/><br/>
                                        <a href="/twitter/<%=twitterusername%>/?tweetsPage=<%=tweetsPage+1%>"><font class="mediumfont">older tweets >></font></a>
                                </div>
                            </td>
                        </tr>
                    </table>
            <%} else {%>
                <table cellpadding="3" cellspacing="0" border="0" width="100%">
                    <tr>
                        <td valign="top">
                            <div class="roundedBox" style="width:360px; overflow:hidden;">
                                <%--<font class="mediumfont">@<%=twitterusername%> isn't listed as a tracked <%=Pagez.getUserSession().getPl().getCelebiscalled()%> so we don't have their tweets but you can still see <a href="/twitter/<%=twitterusername%>/who/" style="text-decoration: underline; color: #0000ff;">who's tweeted them</a>!</font>--%>
                                <%--<br/><br/>--%>
                                <%--<center>--%>
                                    <%--<%=AdUtil.get336x280TWITNOTCELEB()%>--%>
                                <%--</center>--%>

                                <%--  START NON-CELEB MAIN PAGE  --%>
                                <%
                                String filename = "celebsWhoTweetedTwit";
                                String typeTitle = "@"+pagetitleName+" has been tweeted by these "+Pagez.getUserSession().getPl().getCelebiscalled()+"s";
                                %>
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
                                                    <a href="/twitterstats/<%=filename%>/<%=twitterusername%>/when/alltime/<%=qs%>" style="<%=addToStyle%>">all time</a> |
                                                    <%if (time.equals("thismonth")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                                    <a href="/twitterstats/<%=filename%>/<%=twitterusername%>/when/thismonth/<%=qs%>" style="<%=addToStyle%>">this month</a> |
                                                    <%if (time.equals("last31days")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                                    <a href="/twitterstats/<%=filename%>/<%=twitterusername%>/when/last31days/<%=qs%>" style="<%=addToStyle%>">last 31 days</a> |
                                                    <%if (time.equals("thisweek")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                                    <a href="/twitterstats/<%=filename%>/<%=twitterusername%>/when/thisweek/<%=qs%>" style="<%=addToStyle%>">this week</a> |
                                                    <%if (time.equals("last7days")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                                    <a href="/twitterstats/<%=filename%>/<%=twitterusername%>/when/last7days/<%=qs%>" style="<%=addToStyle%>">last 7 days</a> |
                                                    <%if (time.equals("yesterday")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                                    <a href="/twitterstats/<%=filename%>/<%=twitterusername%>/when/yesterday/<%=qs%>" style="<%=addToStyle%>">yesterday</a> |
                                                    <%if (time.equals("today")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                                    <a href="/twitterstats/<%=filename%>/<%=twitterusername%>/when/today/<%=qs%>" style="<%=addToStyle%>">today</a>
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
                                            <font class="largefont" style="font-size:20px; color:#666666;">stats <%=statsTime%></font>
                                            <br clear="all"/><br/><br/><font class="largefont" style="font-size:30px;"><%=typeTitle%></font>
                                            <br clear="all"/><br/><br/>
                                            <%=StatsOutputCached.celebsWhoTweetedTwit(twit, twitterusername, Pagez.getUserSession().getPl(), request.getParameter("time"), 500, Util.booleanFromString(request.getParameter("refresh")), true)%>
                                            <br clear="all"/><br/><br/>
                                            <%=AdUtil.get336x280TWITNOTCELEB()%>
                                        </td>
                                        <%--<td valign="top">--%>
                                            <%--<img src="/images/clear.gif" alt="" width="1" height="85"/><br/>--%>
                                            <%--<%=AdUtil.get160x600TWITTERSTATS()%>--%>
                                        <%--</td>--%>
                                    </tr>
                                </table>

                                <%--  END NON-CELEB MAIN PAGE  --%>
                            </div>
                        </td>
                    </tr>
                </table>
            <%}%>
            <!-- End Middle Col -->
        </td>
    </tr>
</table>










<%@ include file="/template/footer.jsp" %>
