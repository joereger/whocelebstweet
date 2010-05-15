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
