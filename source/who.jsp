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
<%@ page import="java.net.URLEncoder" %>

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
%>
<%
pagetitle = "Who "+pagetitleName + " Tweets (Twitter Stats for "+titleTime+")";
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
        <td valign="top">
            <div class="roundedBox" style="width:630px;">
                <script language="javascript">
                function toggleLink() {
                    var ele = document.getElementById("toggleLink");
                    if(ele.style.display == "block") {
                        ele.style.display = "none";
                    } else {
                        ele.style.display = "block";
                    }
                }
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
                <%if (twit!=null && twit.getIsceleb()){%>
                    <img src="<%=twitimageurl%>" width="48" height="48" border="0" align="left" alt="<%=twit.getRealname()%>">
                    <font class="largefont"> <%=twit.getRealname()%> </font>
                <%} else {%>
                    <font class="largefont"> @<%=twitterusername%> </font>
                <%}%>
                <br clear="all"/>
                <%
                String tweetThisStatus = "check out " + "http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/who/";
                tweetThisStatus = URLEncoder.encode(tweetThisStatus, "UTF-8");
                %>
                <font class="smallfont"><a href="javascript:toggleLink();" style="text-decoration: underline; color: #0000ff;">Link to this Page</a> | <a href="http://twitter.com/home?status=<%=tweetThisStatus%>" style="text-decoration: underline; color: #0000ff;" target="_blank">Tweet This</a></font>
                <div id="toggleLink" style="display: none; text-align: left;">
                    <textarea rows="1" cols="80" name="linkurl" id="linkurl" style="font-size:9px;" onclick="javascript:document.getElementById('linkurl').select();"><%=Str.cleanForHtml("http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/who/")%></textarea>
                    <br/><font class="tinyfont">Copy and paste the above URL into your blog, email, im or website to link to this page.</font>
                </div>
                <br><br>
                <center>
                    <table cellpadding="3" cellspacing="0" border="0">
                        <tr>
                            <td valign="top" width="*">
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
                                        <font class="smallfont"><a href="/twitter/<%=twit.getTwitterusername()%>/">@<%=twitterusername%>'s Recent Tweets</a></font>
                                        <br/>
                                    <%}%>
                            </td>
                            <td valign="top" width="160">
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
                            <td valign="top" width="150">
                                <%=JsCelebMentions.get(twit, twitterusername, Pagez.getUserSession().getPl())%>
                                <a href="javascript:toggleA();"><font class="tinyfont">+ embed in your blog/website</font></a>
                                <div id="toggleTextA" style="display: none">
                                    <input type="text" name="embedB" value="<%=Str.cleanForHtml("<script src=\"http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/js/celebtweets/\"></script>")%>" size="25">
                                    <br/><font class="smallfont">Copy and paste this code into your blog or website to display the box.</font>
                                </div>
                            </td>
                            <td valign="top" width="150">
                                <%=JsDifferentCelebs.get(twit, twitterusername, Pagez.getUserSession().getPl())%>
                                <a href="javascript:toggleB();"><font class="tinyfont">+ embed in your blog/website</font></a>
                                <div id="toggleTextB" style="display: none">
                                    <input type="text" name="embedB" value="<%=Str.cleanForHtml("<script src=\"http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/js/differentcelebs/\"></script>")%>" size="25">
                                    <br/><font class="smallfont">Copy and paste this code into your blog or website to display the box.</font>
                                </div>
                            </td>
                        </tr>
                    </table>
                </center>
            </div>

        </td>
        </tr>
        <tr>
        <td valign="top">
            <%if (1==1 || !Pagez.getUserSession().isSisterPl()){%>
                <%if (twit!=null && twit.getTwitid()>0){%>
                    <div class="roundedBox" style="width:630px;">
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
                        <%=PublicTwitterWhoPanel.getHtml(twit, twitterusername, Pagez.getUserSession().getPl(), request.getParameter("time"), request.getParameter("refresh"))%>
                    </div>
                <%}%>
            <%}%>

            <%if (twit!=null && twit.getTwitid()>0){%>
                <%if (twit.getIsceleb()){%>
                    <font class="mediumfont"><%=twit.getRealname()%>'s recent tweets</font>
                    <br/><br/>
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
                                        <a href="/twitter/<%=twitterusername%>/?tweetsPage=<%=tweetsPage+1%>"><font class="normalfont">older tweets >></font></a>
                                </div>
                            </td>


                            <td valign="top">
                                <script type="text/javascript"><!--
                                google_ad_client = "pub-9883617370563969";
                                /* 160x600, Skyscraper */
                                google_ad_slot = "2576530148";
                                google_ad_width = 160;
                                google_ad_height = 600;
                                //-->
                                </script>
                                <script type="text/javascript"
                                src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
                                </script>
                            </td>

                        </tr>
                    </table>
                <%}%>
            <%}%>
        </td>
    </tr>
</table>










<%@ include file="/template/footer.jsp" %>
