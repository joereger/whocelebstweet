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
<%@ page import="com.celebtwit.htmluibeans.*" %>

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
%>
<%
pagetitle = pagetitleName + "'s Twitter Tweets on "+Pagez.getUserSession().getPl().getName()+"!  Always up-to-date!";
if (Pagez.getUserSession().isSisterPl()){
    pagetitle = pagetitleName + "'s Twitter Tweets on "+Pagez.getUserSession().getPl().getSistername()+"! Always up-to-date!";
}
%>
<%
if (twit!=null && twit.getIsceleb()){
    metaDescription = "Twitter updates by "+twit.getRealname()+". "+twit.getDescription();
} else {
    metaDescription = "Twitter updates by " + twitterusername+".";
}
%>
<%
if (twit!=null && twit.getIsceleb()){
    metaKeywords = twit.getDescription();
}
%>
<%@ include file="/template/header.jsp" %>




<table cellpadding="3" cellspacing="0" border="0" width="100%">
    <tr>
        <td valign="top" width="200">
            <!-- Start Left Col -->
            <div class="roundedBox" style="width:200px;">
                <%if (twit!=null && twit.getIsceleb()){%>
                    <img src="<%=twitimageurl%>" width="48" height="48" border="0" align="left" alt="<%=twit.getRealname()%>">
                    <font class="largefont"> <%=twit.getRealname()%> </font>
                <%} else {%>
                    <font class="largefont"> @<%=twitterusername%> </font>
                <%}%>
                <br/>
                <script language="javascript">
                function toggleA() {
                    var ele = document.getElementById("toggleTextA");
                    if(ele.style.display == "block") {
                        ele.style.display = "none";
                    } else {
                        ele.style.display = "block";
                    }
                }
                function toggleB() {
                    var ele = document.getElementById("toggleTextB");
                    if(ele.style.display == "block") {
                        ele.style.display = "none";
                    } else {
                        ele.style.display = "block";
                    }
                }
                </script>
                    <table cellpadding="10" cellspacing="5" border="0">
                        <tr>
                            <td valign="top">
                                <%if (twit!=null && twit.getDescription().length()>0){%>
                                    <font class="normalfont"><%=twit.getDescription()%></font>
                                    <br/><br/>
                                <%}%>
                                <%if (twit!=null && twit.getWebsite_url().length()>0){%>
                                    <font class="smallfont">
                                    <a href="<%=twit.getWebsite_url()%>" target="_blank">Website</a>
                                    |
                                    <a href="http://www.twitter.com/<%=twitterusername%>/" target="_blank">Twitter</a>
                                    </font>
                                    <br/>
                                <%}%>
                                <%if (twit!=null && twit.getFollowers_count()>0){%>
                                    <font class="smallfont"><%=twit.getFollowers_count()%> followers</font>
                                    <br/>
                                <%}%>
                                <%if (twit!=null && twit.getStatuses_count()>0){%>
                                    <font class="smallfont"><%=twit.getStatuses_count()%> updates</font>
                                    <br/>
                                <%}%>
                                <%if (twit!=null && twit.getIsceleb()){%>
                                    <font class="smallfont"><a href="/twitter/<%=twit.getTwitterusername()%>/who/">Who @<%=twitterusername%> Tweets</a></font>
                                    <br/>
                                <%}%>

                                <br/>
                                <script type="text/javascript"><!--
                                google_ad_client = "pub-9883617370563969";
                                /* 160x90, link unit whocelebstweet smallfont */
                                google_ad_slot = "5409295921";
                                google_ad_width = 160;
                                google_ad_height = 90;
                                //-->
                                </script>
                                <script type="text/javascript"
                                src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
                                </script>

                            </td>
                        </tr>
                        <tr>
                            <td valign="top" width="175">
                                <%=JsCelebMentions.get(twit, twitterusername, Pagez.getUserSession().getPl())%>
                                <a href="javascript:toggleA();"><font class="tinyfont">embed in your blog/website</font></a>
                                <div id="toggleTextA" style="display: none">
                                    <input type="text" name="embedB" value="<%=Str.cleanForHtml("<script src=\"http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/js/celebtweets/\"></script>")%>" size="25">
                                    <br/><font class="smallfont">Copy and paste this code into your blog or website to display the box.</font>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td valign="top" width="175">
                                <%=JsDifferentCelebs.get(twit, twitterusername, Pagez.getUserSession().getPl())%>
                                <a href="javascript:toggleB();"><font class="tinyfont">embed in your blog/website</font></a>
                                <div id="toggleTextB" style="display: none">
                                    <input type="text" name="embedB" value="<%=Str.cleanForHtml("<script src=\"http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/js/differentcelebs/\"></script>")%>" size="25">
                                    <br/><font class="smallfont">Copy and paste this code into your blog or website to display the box.</font>
                                </div>
                            </td>
                        </tr>
                    </table>
            </div>


            <%if (1==1 || !Pagez.getUserSession().isSisterPl()){%>
                <%if (twit!=null && twit.getTwitid()>0){%>
                    <div class="roundedBox" style="width:200px;">
                        <div style="float:right">
                            <font class="tinyfont">
                                <%String qs = "";%>
                                <%String time = request.getParameter("time");%>
                                <%if (time==null || time.equals("") || time.equals("null")){time="alltime";}%>
                                <%String addToStyle = "";%>
                                <%String boldStyle = "font-weight:bold; background:#ffffff;";%>
                                <%if (time.equals("alltime")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                <a href="/twitter/<%=twitterusername%>/when/alltime/<%=qs%>" style="<%=addToStyle%>">all time</a> |
                                <%if (time.equals("thismonth")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                <a href="/twitter/<%=twitterusername%>/when/thismonth/<%=qs%>" style="<%=addToStyle%>">this month</a> |
                                <%if (time.equals("last31days")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                <a href="/twitter/<%=twitterusername%>/when/last31days/<%=qs%>" style="<%=addToStyle%>">last 31 days</a> |
                                <%if (time.equals("thisweek")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                <a href="/twitter/<%=twitterusername%>/when/thisweek/<%=qs%>" style="<%=addToStyle%>">this week</a> |
                                <%if (time.equals("last7days")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                <a href="/twitter/<%=twitterusername%>/when/last7days/<%=qs%>" style="<%=addToStyle%>">last 7 days</a> |
                                <%if (time.equals("yesterday")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                <a href="/twitter/<%=twitterusername%>/when/yesterday/<%=qs%>" style="<%=addToStyle%>">yesterday</a> |
                                <%if (time.equals("today")){addToStyle=boldStyle;}else{addToStyle="";}%>
                                <a href="/twitter/<%=twitterusername%>/when/today/<%=qs%>" style="<%=addToStyle%>">today</a>
                            </font>
                        </div><br/>
                        <%=PublicTwitterWhoPanelVertical.getHtml(twit, twitterusername, Pagez.getUserSession().getPl(), request.getParameter("time"), request.getParameter("refresh"))%>
                    </div>
                <%}%>
            <%}%>
            <!-- End Left Col -->
        </td>
        <td valign="top">
            <!-- Start Middle Col -->
            <%if (twit!=null && twit.getTwitid()>0){%>
            <%if (twit.getIsceleb()){%>
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
                                    <%=PublicTwitterTweetlist.getHtml(twit, tweetsPage, request.getParameter("refresh"))%>
                                    <br/><br/>
                                    <a href="/twitter/<%=twitterusername%>/?tweetsPage=<%=tweetsPage+1%>"><font class="mediumfont">older tweets >></font></a>
                            </div>
                        </td>
                    </tr>
                </table>
            <%}%>
        <%}%>
            <!-- End Middle Col -->
        </td>
    </tr>
</table>










<%@ include file="/template/footer.jsp" %>
