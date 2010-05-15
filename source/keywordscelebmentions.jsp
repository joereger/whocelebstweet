<%@ page import="com.celebtwit.dao.Keyword" %>
<%@ page import="com.celebtwit.helpers.FindTwitFromTwitterusername" %>
<%@ page import="com.celebtwit.keywords.KeywordHelpers" %>
<%@ page import="com.celebtwit.util.Str" %>
<%@ page import="com.celebtwit.util.Time" %>
<%@ page import="com.celebtwit.util.Util" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.ArrayList" %>

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
pagetitle = "Things "+pagetitleName+" Talks About on Twitter";
%>
<%
metaDescription = "A list of things that "+pagetitleName+" Talks About on Twitter.  We search the tweets so you don't have to!";
%>
<%
metaKeywords = pagetitleName+" twitter discusses talks mentions";
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
            String sidebar_pageurl = "http://"+ Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/talksabout/";
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
                                        <br/><br/>
                                        <font class="mediumfont">
                                        <ul>
                                        <%
                                            ArrayList<Keyword> keywords = KeywordHelpers.getKeywordsACelebHasMentioned(twit);
                                            for (Iterator<Keyword> keywordIterator = keywords.iterator(); keywordIterator.hasNext();) {
                                                Keyword keyword = keywordIterator.next();
                                                %><li><a href="/twitter/<%=twitterusername%>/talksabout/<%=URLEncoder.encode(keyword.getKeyword(), "UTF-8")%>/"><%=keyword.getKeyword()%></a></li><%
                                            }
                                        %>
                                        </ul>
                                        </font>
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
