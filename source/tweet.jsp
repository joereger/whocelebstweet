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
                <%if (twit!=null && twit.getIsceleb()){%>
                    <font class="smallfont"><a href="/twitter/<%=twit.getTwitterusername()%>/">More About <%=twit.getRealname()%></a></font>
                <%} else { %>
                    <font class="smallfont"><a href="/twitter/<%=twitterusername%>/">More About <%=twitterusername%></a></font>
                <%}%>
                <br/>
                <br/><br/><br/><br/><br/>
                <center>
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
            <script type="text/javascript"><!--
            google_ad_client = "pub-9883617370563969";
            /* 336x280, created 8/3/09 */
            google_ad_slot = "6301770143";
            google_ad_width = 336;
            google_ad_height = 280;
            //-->
            </script>
            <script type="text/javascript"
            src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
            </script>
            </center>
            <!-- End Middle Col -->
        </td>
    </tr>
</table>










<%@ include file="/template/footer.jsp" %>
