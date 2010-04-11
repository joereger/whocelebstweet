<%@ page import="com.celebtwit.cachedstuff.CachedStuff" %>
<%@ page import="com.celebtwit.cachedstuff.GetCachedStuff" %>
<%@ page import="com.celebtwit.cachedstuff.TwitterTweetlist" %>
<%@ page import="com.celebtwit.embed.JsCelebMentions" %>
<%@ page import="com.celebtwit.embed.JsDifferentCelebs" %>
<%@ page import="com.celebtwit.helpers.FindTwitFromTwitterusername" %>
<%@ page import="com.celebtwit.htmluibeans.PublicTwitterWhoPanelVertical" %>
<%@ page import="com.celebtwit.util.Num" %>
<%@ page import="com.celebtwit.util.Str" %>
<%@ page import="org.apache.log4j.Logger" %>
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

<%--<%--%>
    <%--AdNetworkFactory.testRandom();--%>
<%--%>--%>
<%--RANDOM TEST DONE!--%>

<%if (twit!=null && twit.getIsceleb()){%>
    <font class="largefont" style="font-size:60px;"> <%=twit.getRealname()%> </font>
<%} else {%>
    <font class="largefont" style="font-size:60px;"> @<%=twitterusername%> </font>
<%}%>
<table cellpadding="3" cellspacing="0" border="0" width="100%">
    <tr>
        <td valign="top" width="200">
            <!-- Start Left Col -->
            <div class="roundedBox" style="width:200px;">

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
                <%
                String tweetThisStatus = "check out " + "http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/";
                tweetThisStatus = URLEncoder.encode(tweetThisStatus, "UTF-8");
                %>
                <center>
                <font class="smallfont"><a href="javascript:toggleLink();" style="text-decoration: underline; color: #0000ff;">Link to this Page</a> | <a href="http://twitter.com/home?status=<%=tweetThisStatus%>" style="text-decoration: underline; color: #0000ff;" target="_blank">Tweet This</a></font>
                <div id="toggleLink" style="display: none; text-align: center;">
                    <textarea rows="3" cols="30" name="linkurl" id="linkurl" style="font-size:9px;" onclick="javascript:document.getElementById('linkurl').select();"><%=Str.cleanForHtml("http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/")%></textarea>
                    <br/><font class="tinyfont">Copy and paste the above URL into your blog, email, im or website to link to this page.</font>
                </div>
                </center>

                <%if (twit!=null && twit.getIsceleb()){%>
                    <%--<center><br/><br/><img src="<%=twitimageurl%>" width="48" height="48" border="0" align="middle" alt="<%=twit.getRealname()%>"></center>--%>
                    <center><br/><br/><img src="<%=twitimageurl%>" width="190" border="0" align="middle" alt="<%=twit.getRealname()%>"></center>
                <%}%>


                <br clear="all">
                    <table cellpadding="10" cellspacing="5" border="0">
                        <tr>
                            <td valign="top">
                                <%if (twit!=null && twit.getDescription().length()>0){%>
                                    <font class="normalfont" style="font-size:15px; color:#666666;"><%=twit.getDescription()%></font>
                                    <br/>
                                <%}%>
                                <%--<div style="margin-left:3px; width: 160px; background:#e6e6e6;"><font class="smallfont" style="font-size:11px; text-decoration: underline;">More Information</font></div>--%>
                                <div style="margin-left:0px; width:190px;">
                                <%if (twit!=null && twit.getWebsite_url().length()>0){%>
                                    <font class="smallfont">
                                    <a href="<%=twit.getWebsite_url()%>" target="_blank"  style="text-decoration: underline; color: #0000ff;">Website</a>
                                    |
                                    <a href="http://www.twitter.com/<%=twitterusername%>/" target="_blank"  style="text-decoration: underline; color: #0000ff;">Twitter</a>
                                    </font>
                                    <br/>
                                <%} else { %>
                                    <font class="smallfont">
                                    <a href="http://www.twitter.com/<%=twitterusername%>/" target="_blank"  style="text-decoration: underline; color: #0000ff;">Twitter</a>
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
                                <%if (1==1){%>
                                    <br/>
                                    <font class="normalfont" style="font-weight:bold;"><a href="/twitter/<%=twitterusername%>/who/"  style="text-decoration: underline; color: #0000ff;">@<%=twitterusername%>'s Stats</a></font>
                                    <br/>
                                <%}%>
                                </div>

                                <br/>
                                <%=AdUtil.get160x90()%>

                            </td>
                        </tr>
                        <tr>
                            <td valign="top" width="175">
                                <%=JsCelebMentions.get(twit, twitterusername, Pagez.getUserSession().getPl())%>
                                <a href="javascript:toggleA();"><font class="tinyfont">+ embed in your blog/website</font></a>
                                <div id="toggleTextA" style="display: none">
                                    <input type="text" name="embedB" value="<%=Str.cleanForHtml("<script src=\"http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/js/celebtweets/\"></script>")%>" size="25">
                                    <br/><font class="smallfont">Copy and paste this code into your blog or website to display the box.</font>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td valign="top" width="175">
                                <%=JsDifferentCelebs.get(twit, twitterusername, Pagez.getUserSession().getPl())%>
                                <a href="javascript:toggleB();"><font class="tinyfont">+ embed in your blog/website</font></a>
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
                        </div>
                        <font class="largefont" style="font-size:40px;">stats</font>
                        <br clear="all"/>
                        <%=PublicTwitterWhoPanelVertical.getHtml(twit, twitterusername, Pagez.getUserSession().getPl(), request.getParameter("time"), request.getParameter("refresh"))%>
                        <br/><br/>
                        <a href="/twitter/<%=twitterusername%>/who/"><font class="mediumfont">more stats >></font></a>
                    </div>
                <%}%>
            <%}%>
            <!-- End Left Col -->
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
                                        CachedStuff cs = new TwitterTweetlist(twit, tweetsPage);
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
                            <div class="roundedBoxNoRound" style="width:410px; overflow:hidden;">
                                <font class="mediumfont">@<%=twitterusername%> isn't listed as a tracked <%=Pagez.getUserSession().getPl().getCelebiscalled()%> so we don't have their tweets but you can still see <a href="/twitter/<%=twitterusername%>/who/" style="text-decoration: underline; color: #0000ff;">who's tweeted them</a>!</font>
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
