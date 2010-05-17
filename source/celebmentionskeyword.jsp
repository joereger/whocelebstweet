<%@ page import="com.celebtwit.dao.Keyword" %>
<%@ page import="com.celebtwit.dao.Twitpost" %>
<%@ page import="com.celebtwit.helpers.FindTwitFromTwitterusername" %>
<%@ page import="com.celebtwit.helpers.TwitpostAsHtml" %>
<%@ page import="com.celebtwit.keywords.KeywordHelpers" %>
<%@ page import="com.celebtwit.util.Str" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.celebtwit.ads.AdNetworkNone" %>
<%@ page import="com.celebtwit.util.Num" %>

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
if (twit==null){
    Pagez.sendRedirect("/");
    return;
}
%>
<%
Keyword keyword = KeywordHelpers.getKeywordByString(request.getParameter("keyword"));
if (keyword==null){
    Pagez.sendRedirect("/twitter/"+twit.getTwitterusername()+"/talksabout/");
    return;
}
%>
<%
//Get keyword stats for use in description
String celebmentionskeywordstats = "";
if (twit!=null){
    CachedStuff cs = new CelebMentionsKeywordStats(twit, keyword);
    CelebMentionsKeywordStats obj = (CelebMentionsKeywordStats) GetCachedStuff.get(cs, Pagez.getUserSession().getPl());
    celebmentionskeywordstats = obj.getHtml();
}
%>
<%
pagetitle = ""+pagetitleName+" Talks About "+keyword.getKeyword()+" on Twitter";
%>
<%
metaDescription = celebmentionskeywordstats + " A list of tweets where "+pagetitleName+" talks about "+keyword.getKeyword()+" on Twitter.";
%>
<%
metaKeywords = keyword.getKeyword()+" "+pagetitleName+" twitter discusses talks mentions";
%>

<%@ include file="/template/header.jsp" %>



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
            String sidebar_pageurl = "http://"+ Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/talksabout/"+URLEncoder.encode(keyword.getKeyword(), "UTF-8")+"/";
            %>
            <%@ include file="/celeb_sidebar.jsp" %>

        </td>
        <td valign="top" width="430">
            <!-- Start Middle Col -->
            <%if (twit!=null && twit.getTwitid()>0){%>
                <%if (twit.getIsceleb()){%>
                    <%--<font class="mediumfont"><%=twit.getRealname()%>'s recent tweets</font>--%>
                    <%--<br/><br/>--%>
                    <table cellpadding="3" cellspacing="0" border="0" width="100%">
                        <tr>
                            <td valign="top">
                                <div class="roundedBoxNoRound" style="width:410px; overflow:hidden;">

                                        <font class="largefont"><%=pagetitle%></font>
                                        <br/>
                                        <font class="normalfont" style="font-weight:bold;"><a href="/talksabout/<%=URLEncoder.encode(keyword.getKeyword(), "UTF-8")%>/">Other <%=Pagez.getUserSession().getPl().getCelebiscalled()%>s Who Talk About <%=keyword.getKeyword()%></a></font>
                                        <br/><br/><br/>

                                        <center>
                                        <div class="mediumfont" style="color: #999999; background: #f9f9f9; padding: 15px; width: 400;">
                                            <%=celebmentionskeywordstats%>
                                        </div>
                                        </center>
                                        <br/><br/>

                                        <%
                                        if (twit!=null){
                                            CachedStuff cs = new CelebMentionsKeyword(twit, keyword);
                                            CelebMentionsKeyword obj = (CelebMentionsKeyword) GetCachedStuff.get(cs, Pagez.getUserSession().getPl());
                                            String celebmentionskeyword = obj.getHtml();
                                            %><%=celebmentionskeyword%><%
                                        }
                                        %>

                                </div>
                            </td>
                        </tr>
                    </table>
                <%}%>
            <%}%>
            <br/><br/><br/><br/>
            <center>
            <%=AdUtil.get400x600()%>
            </center>
            <!-- End Middle Col -->
        </td>
    </tr>
</table>










<%@ include file="/template/footer.jsp" %>
