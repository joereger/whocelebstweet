<%@ page import="com.celebtwit.dao.Twitpost" %>
<%@ page import="com.celebtwit.helpers.FindTwitFromTwitterusername" %>
<%@ page import="com.celebtwit.helpers.TwitpostAsHtml" %>
<%@ page import="com.celebtwit.util.Str" %>
<%@ page import="com.celebtwit.util.Time" %>
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
            String sidebar_pageurl = "http://"+ Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/tweet/"+twitpost.getTwitterguid()+"/";
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
                                        <%=tweet%>
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
