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
<%@ page import="com.celebtwit.ads.AdUtil" %>
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
Twitpost twitpost = null;
StringBuffer tweet = new StringBuffer();
if (twit!=null){
    if (request.getParameter("twitterguid")!=null){
        List<Twitpost> twitposts = HibernateUtil.getSession().createCriteria(Twitpost.class)
                                       .add(Restrictions.eq("twitid", twit.getTwitid()))
                                       .add(Restrictions.eq("twitterguid", request.getParameter("twitterguid")))
                                       .setCacheable(true)
                                       .list();
        for (Iterator<Twitpost> tpIt=twitposts.iterator(); tpIt.hasNext();) {
            Twitpost tp=tpIt.next();
            twitpost = tp;
            tweet.append(TwitpostAsHtml.get(tp, 380));
        }
    } else {
        //No twitpostid specified
    }
}
%>
<%
pagetitle = pagetitleName + "'s Twitter Tweets on "+Pagez.getUserSession().getPl().getName()+"!  Always up-to-date!";
if (Pagez.getUserSession().isSisterPl()){
    pagetitle = pagetitleName + "'s Twitter Tweets on "+Pagez.getUserSession().getPl().getSistername()+"! Always up-to-date!";
}
if (twitpost!=null && twit!=null && twit.getIsceleb()){
    pagetitle = twit.getRealname()+" says: \"" + twitpost.getPost()+"\" on Twitter";
}
%>
<%
if (twitpost!=null && twit!=null && twit.getIsceleb()){
    metaDescription = "This is a Twitter post.  On "+Time.dateformatcompactwithtime(twitpost.getCreated_at())+" "+pagetitle;
} else {
    metaDescription = "Twitter post by " + twitterusername+".";
}
%>
<%
if (twit!=null && twit.getIsceleb()){
    metaKeywords = twit.getDescription();
}
%>
<%@ include file="/template/header.jsp" %>



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
                <%if (twit!=null && twit.getIsceleb()){%>
                    <center><img src="<%=twitimageurl%>" width="48" height="48" border="0" align="left" alt="<%=twit.getRealname()%>"></center>
                <%}%>
                <br/>
                <%if (twit!=null && twit.getIsceleb()){%>
                    <font class="smallfont"><a href="/twitter/<%=twit.getTwitterusername()%>/">More About <%=twit.getRealname()%></a></font>
                <%} else { %>
                    <font class="smallfont"><a href="/twitter/<%=twitterusername%>/">More About <%=twitterusername%></a></font>
                <%}%>
                <br/>
                <br/><br/><br/><br/><br/>
                <center>
                <%=AdUtil.get160x600()%>
                </center>
            </div>
            <!-- End Left Col -->
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
                                        <%=tweet%>
                                        <br/><br/>
                                        <a href="/twitter/<%=twitterusername%>/"><font class="mediumfont">all of @<%=twitterusername%>'s tweets >></font></a>
                                </div>
                            </td>
                        </tr>
                    </table>
                <%}%>
            <%}%>
            <br/><br/>
            <center>
            <%=AdUtil.get400x600()%>
            <%=AdUtil.get336x280()%>
            </center>
            <!-- End Middle Col -->
        </td>
    </tr>
</table>










<%@ include file="/template/footer.jsp" %>
