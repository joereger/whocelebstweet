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
                String tweetThisStatus = "check out " + "http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/tweet/"+twitpost.getTwitterguid()+"/";
                tweetThisStatus = URLEncoder.encode(tweetThisStatus, "UTF-8");
                %>
                <font class="smallfont"><a href="javascript:toggleLink();" style="text-decoration: underline; color: #0000ff;">Link to this Page</a> | <a href="http://twitter.com/home?status=<%=tweetThisStatus%>" style="text-decoration: underline; color: #0000ff;" target="_blank">Tweet This</a></font>
                <div id="toggleLink" style="display: none; text-align: left;">
                    <textarea rows="1" cols="40" name="linkurl" id="linkurl" style="font-size:9px;" onclick="javascript:document.getElementById('linkurl').select();"><%=Str.cleanForHtml("http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/tweet/"+twitpost.getTwitterguid()+"/")%></textarea>
                    <br/><font class="tinyfont">Copy and paste the above URL into your blog, email, im or website to link to this page.</font>
                </div>
                <%if (twit!=null && twit.getIsceleb()){%>
                    <br/><br/><a href="/twitter/<%=twitterusername%>/picture/"><img src="<%=twitimageurl%>" width="190" style="border: 10px solid #ffffff;" alt="<%=twit.getRealname()%>"></a>
                <%}%>
                <br clear="all"/><br/>
                <%--<%if (twit!=null && twit.getDescription().length()>0){%>--%>
                    <%--<font class="normalfont" style="font-size:15px; color:#666666;"><%=twit.getDescription()%></font>--%>
                    <%--<br/>--%>
                <%--<%}%>--%>
                <%--<%if (twit!=null && twit.getWebsite_url().length()>0){%>--%>
                    <%--<font class="smallfont">--%>
                    <%--<a href="<%=twit.getWebsite_url()%>" target="_blank"  style="text-decoration: underline; color: #0000ff;">Website</a>--%>
                    <%--|--%>
                    <%--<a href="http://www.twitter.com/<%=twitterusername%>/" target="_blank"  style="text-decoration: underline; color: #0000ff;">Twitter</a>--%>
                    <%--</font>--%>
                    <%--<br/>--%>
                <%--<%} else { %>--%>
                    <%--<font class="smallfont">--%>
                    <%--<a href="http://www.twitter.com/<%=twitterusername%>/" target="_blank"  style="text-decoration: underline; color: #0000ff;">Twitter</a>--%>
                    <%--</font>--%>
                    <%--<br/>--%>
                <%--<%}%>--%>
                <%--<%if (twit!=null && twit.getFollowers_count()>0){%>--%>
                    <%--<font class="smallfont"><%=twit.getFollowers_count()%> followers</font>--%>
                    <%--<br/>--%>
                <%--<%}%>--%>
                <%--<%if (twit!=null && twit.getStatuses_count()>0){%>--%>
                    <%--<font class="smallfont"><%=twit.getStatuses_count()%> updates</font>--%>
                    <%--<br/>--%>
                <%--<%}%>--%>
                <%if (twit!=null && twit.getIsceleb()){%>
                    <font class="normalfont" style="font-weight:bold;"><a href="/twitter/<%=twit.getTwitterusername()%>/">@<%=twitterusername%>'s Profile</a></font>
                    <br/>
                <%}%>
                </center>
                <br/><br/><br/>
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
                                        <a href="/twitter/<%=twitterusername%>/"><font class="mediumfont">see all of @<%=twitterusername%>'s tweets</font></a>
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
