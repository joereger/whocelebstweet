<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmluibeans.PublicIndex" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.celebtwit.helpers.*" %>
<%@ page import="com.celebtwit.systemprops.SystemProperty" %>
<%@ page import="com.celebtwit.dao.Twitpost" %>
<%@ page import="com.celebtwit.util.Str" %>
<%@ page import="com.celebtwit.util.Time" %>
<%@ page import="com.celebtwit.embed.JsCelebMentions" %>
<%@ page import="com.celebtwit.embed.JsDifferentCelebs" %>

<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "";
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

<%@ include file="/template/header.jsp" %>

<%
StartDateEndDate sted = new StartDateEndDate(request.getParameter("time"));
%>

<div class="roundedBox" style="width:630px;">
    <%if (twit!=null && twit.getIsceleb()){%>
        <img src="<%=twitimageurl%>" width="48" height="48" border="0" align="left">
        <font class="largefont"> <%=twit.getRealname()%> </font>
    <%} else {%>
        <font class="largefont"> @<%=twitterusername%> </font>
    <%}%>
    <a href="http://www.twitter.com/<%=twitterusername%>/"><img src="/images/twitter-16x16.png" width=16 height=16 border=0></a>
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
    <center>
        <table cellpadding="10" cellspacing="5" border="0">
            <tr>
                <td valign="top" width="200">
                    <%=JsCelebMentions.get(twit, twitterusername)%>
                    <a href="javascript:toggleA();"><font class="tinyfont">embed in your blog/website</font></a>
                    <div id="toggleTextA" style="display: none">
                        <input type="text" name="embedB" value="<%=Str.cleanForHtml("<script src=\"http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/js/celebtweets/\"></script>")%>" size="25">
                        <br/><font class="smallfont">Copy and paste this code into your blog or website to display the box.</font>
                    </div>
                </td>
                <td valign="top" width="200">
                    <%=JsDifferentCelebs.get(twit, twitterusername)%>
                    <a href="javascript:toggleB();"><font class="tinyfont">embed in your blog/website</font></a>
                    <div id="toggleTextB" style="display: none">
                        <input type="text" name="embedB" value="<%=Str.cleanForHtml("<script src=\"http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/js/differentcelebs/\"></script>")%>" size="25">
                        <br/><font class="smallfont">Copy and paste this code into your blog or website to display the box.</font>
                    </div>
                </td>
            </tr>
        </table>
    </center>
</div>


<%if (!Pagez.getUserSession().isSisterPl()){%>
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
                    <a href="/twitter/<%=twitterusername%>/<%=qs%>" style="<%=addToStyle%>">all time</a> |
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
                <table cellpadding="10" cellspacing="0" border="0">
                    <%if (twit.getIsceleb()){%>
                        <tr>
                            <td valign="top" width="50%">
                                <%if (twit!=null && twit.getIsceleb()){%>
                                    <font class="mediumfont">non-<%=Pagez.getUserSession().getPl().getCelebiscalled()%>s tweeted by @<%=twit.getRealname()%></font>
                                <%} else {%>
                                    <font class="mediumfont">non-<%=Pagez.getUserSession().getPl().getCelebiscalled()%>s tweeted by @<%=twitterusername%></font>
                                <%}%>
                                <br/><br/>
                                <%
                                if (true){
                                    ArrayList<TwitMention> twitMentions = GetTwitsByMentioned.get(sted.getStartDate(), sted.getEndDate(), twit.getTwitid(), false, 15, Pagez.getUserSession().getPl().getPlid());
                                    for (Iterator<TwitMention> iterator=twitMentions.iterator(); iterator.hasNext();) {
                                        TwitMention twitMention = iterator.next();
                                        %><font class="normalfont" style="font-weight:bold;"><a href="/twitter/<%=twitMention.getTwit().getTwitterusername()%>/">@<%=twitMention.getTwit().getTwitterusername()%></a></font><font class="tinyfont"> <%=twitMention.getMentions()%> tweets</font><br/><%
                                    }
                                }
                                %>
                            </td>
                            <td valign="top">
                                <%if (twit!=null && twit.getIsceleb()){%>
                                    <font class="mediumfont"><%=Pagez.getUserSession().getPl().getCelebiscalled()%>s tweeted by @<%=twit.getRealname()%></font>
                                <%} else {%>
                                    <font class="mediumfont"><%=Pagez.getUserSession().getPl().getCelebiscalled()%>s tweeted by @<%=twitterusername%></font>
                                <%}%>
                                <br/><font class="tinyfont">click to see <%=Pagez.getUserSession().getPl().getCelebiscalled()%>-to-<%=Pagez.getUserSession().getPl().getCelebiscalled()%> chatter</font>
                                <br/><br/>
                                <%
                                if (true){
                                    ArrayList<TwitMention> twitMentions = GetTwitsByMentioned.get(sted.getStartDate(), sted.getEndDate(), twit.getTwitid(), true, 15, Pagez.getUserSession().getPl().getPlid());
                                    for (Iterator<TwitMention> iterator=twitMentions.iterator(); iterator.hasNext();) {
                                        TwitMention twitMention = iterator.next();
                                        %><font class="normalfont" style="font-weight:bold;"><a href="/chatter/<%=twit.getTwitterusername()%>/<%=twitMention.getTwit().getTwitterusername()%>/">@<%=twitMention.getTwit().getRealname()%></a></font><font class="tinyfont"> <%=twitMention.getMentions()%> tweets</font><br/><%
                                    }
                                }
                                %>
                            </td>
                        </tr>
                    <%}%>
                    <tr>
                        <td valign="top" width="50%">
                            <script type="text/javascript">
                            google_ad_client = "pub-9883617370563969";
                            /* 250x250 WhoCelebsTwitter */
                            google_ad_slot = "3938353254";
                            google_ad_width = 250;
                            google_ad_height = 250;
                            //
                            </script>
                            <script type="text/javascript"
                            src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
                            </script>
                        </td>
                        <td valign="top">
                            <%if (twit!=null && twit.getIsceleb()){%>
                                <font class="mediumfont"><%=Pagez.getUserSession().getPl().getCelebiscalled()%>s who've tweeted @<%=twit.getRealname()%></font>
                            <%} else {%>
                                <font class="mediumfont"><%=Pagez.getUserSession().getPl().getCelebiscalled()%>s who've tweeted @<%=twitterusername%></font>
                            <%}%>
                            <br/><font class="tinyfont">click to see <%=Pagez.getUserSession().getPl().getCelebiscalled()%>-to-<%=Pagez.getUserSession().getPl().getCelebiscalled()%> chatter</font>
                            <br/><br/>
                            <%
                            if (true){
                                ArrayList<TwitCelebWhoMentioned> twitUniques = GetCelebsWhoMentioned.get(sted.getStartDate(), sted.getEndDate(), twit.getTwitid(), 15, Pagez.getUserSession().getPl().getPlid());
                                for (Iterator<TwitCelebWhoMentioned> iterator=twitUniques.iterator(); iterator.hasNext();) {
                                    TwitCelebWhoMentioned twitCelebWhoMentioned = iterator.next();
                                    %><font class="normalfont" style="font-weight:bold;"><a href="/chatter/<%=twit.getTwitterusername()%>/<%=twitCelebWhoMentioned.getTwit().getTwitterusername()%>/">@<%=twitCelebWhoMentioned.getTwit().getRealname()%></a></font><font class="tinyfont"> <%=twitCelebWhoMentioned.getMentions()%> tweets</font><br/><%
                                }
                            }
                            %>
                        </td>
                    </tr>
                </table>
        </div>
    <%}%>
<%}%>

<%if (twit!=null && twit.getTwitid()>0){%>
    <%if (twit.getIsceleb()){%>
        <table cellpadding="3" cellspacing="0" border="0" width="100%">
            <tr>
                <td valign="top">

                    <div class="roundedBoxNoRound" style="width:420px; overflow:hidden;">
                            <font class="largefont">@<%=twit.getRealname()%>'s recent tweets</font>
                            <br/><br/>
                            <%
                            List<Twitpost> twitposts = HibernateUtil.getSession().createCriteria(Twitpost.class)
                                                               .add(Restrictions.eq("twitid", twit.getTwitid()))
                                                               .addOrder(Order.desc("created_at"))
                                                               .setMaxResults(100)
                                                               .setCacheable(true)
                                                               .list();
                                for (Iterator<Twitpost> tpIt=twitposts.iterator(); tpIt.hasNext();) {
                                    Twitpost twitpost=tpIt.next();
                                    %><%=TwitpostAsHtml.get(twitpost, 400)%><%
                                }
                            %>
                    </div>
                </td>
                <td valign="top" width="160">
                    <img src="/images/clear.gif" alt="" width="1" height="70"><br/>
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





<%@ include file="/template/footer.jsp" %>
