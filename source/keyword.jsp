<%@ page import="com.celebtwit.dao.Keyword" %>
<%@ page import="com.celebtwit.dao.Keywordmention" %>
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
Keyword keyword = KeywordHelpers.getKeywordByString(request.getParameter("keyword"));
if (keyword==null){
    Pagez.sendRedirect("/");
    return;
}
%>
<%
pagetitle = "List of "+Pagez.getUserSession().getPl().getCelebiscalled()+"s Who Talk About "+keyword.getKeyword()+" on Twitter";
%>
<%
metaDescription = "We search millions of tweets to create this list of "+Pagez.getUserSession().getPl().getCelebiscalled()+"s who talk about "+keyword.getKeyword()+" on Twitter.";
%>
<%
metaKeywords = keyword.getKeyword()+" "+Pagez.getUserSession().getPl().getCelebiscalled()+" twitter discusses talks mentions";
%>
<%@ include file="/template/header.jsp" %>




<font class="largefont" style="font-size:60px;"><%=keyword.getKeyword()%></font>

<table cellpadding="3" cellspacing="0" border="0" width="100%">
    <tr>
        <td valign="top" width="200">
            <!-- Start Left Col -->
            <div class="roundedBox" style="width:200px;">
                <center>
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
                String tweetThisStatus = "check out " + "http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/talksabout/"+URLEncoder.encode(keyword.getKeyword(), "UTF-8")+"/";
                tweetThisStatus = URLEncoder.encode(tweetThisStatus, "UTF-8");
                %>
                <font class="smallfont"><a href="javascript:toggleLink();" style="text-decoration: underline; color: #0000ff;">Link to this Page</a> | <a href="http://twitter.com/home?status=<%=tweetThisStatus%>" style="text-decoration: underline; color: #0000ff;" target="_blank">Tweet This</a></font>
                <div id="toggleLink" style="display: none; text-align: left;">
                    <textarea rows="1" cols="40" name="linkurl" id="linkurl" style="font-size:9px;" onclick="javascript:document.getElementById('linkurl').select();"><%=Str.cleanForHtml("http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/talksabout/"+URLEncoder.encode(keyword.getKeyword(), "UTF-8")+"/")%></textarea>
                    <br/><font class="tinyfont">Copy and paste the above URL into your blog, email, im or website to link to this page.</font>
                </div>
                <br clear="all"/><br/>
                </center>
            </div>
            <!-- End Left Col -->
        </td>
        <td valign="top" width="430">
            <!-- Start Middle Col -->

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
                                            ArrayList<Twit> twits = KeywordHelpers.getCelebsWhoMentionKeyword(keyword, Pagez.getUserSession().getPl());
                                            for (Iterator<Twit> tIt = twits.iterator(); tIt.hasNext();) {
                                                Twit twit = tIt.next();
                                                %><li><a href="/twitter/<%=twit.getTwitterusername()%>/talksabout/<%=URLEncoder.encode(keyword.getKeyword(), "UTF-8")%>/"><%=twit.getRealname()%></a></li><%
                                            }
                                        %>
                                        </ul>
                                        </font>



                                </div>
                            </td>
                        </tr>
                    </table>

            <br/><br/><br/><br/>
            <center>
            <%=AdUtil.get400x600()%>
            </center>
            <!-- End Middle Col -->
        </td>
    </tr>
</table>










<%@ include file="/template/footer.jsp" %>
