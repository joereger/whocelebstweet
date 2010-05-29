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
pagetitle = pagetitleName + "'s Twitter Profile Picture on "+Pagez.getUserSession().getPl().getName()+"!";
if (Pagez.getUserSession().isSisterPl()){
    pagetitle = pagetitleName + "'s Twitter Profile Picture on "+Pagez.getUserSession().getPl().getSistername()+"!";
}
%>
<%
metaDescription = "See the big version of "+pagetitleName+"'s Twitter Profile picture.";
%>
<%
if (twit!=null && twit.getIsceleb()){
    metaKeywords = "picture twitter profile image "+twit.getDescription();
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
        <td valign="top">
            <!-- Start Left Col -->
            <div class="roundedBox" style="width:630px;">
                <center>


                <%if (twit!=null && twit.getTwitid()>0){%>
                    <%if (twit.getIsceleb()){%>
                        <a href="/twitter/<%=twit.getTwitterusername()%>/"><img src="<%=twitimageurl%>" alt="<%=twit.getRealname()%>'s Twitter Profile Picture" style="border: 10px solid #ffffff;"></a>
                    <%}%>
                <%}%>
                <br/>
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
                String tweetThisStatus = "check out this picture " + "http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/picture/";
                tweetThisStatus = URLEncoder.encode(tweetThisStatus, "UTF-8");
                %>
                <font class="smallfont"><a href="javascript:toggleLink();" style="text-decoration: underline; color: #0000ff;">Link to this Page</a> | <a href="http://twitter.com/home?status=<%=tweetThisStatus%>" style="text-decoration: underline; color: #0000ff;" target="_blank">Tweet This</a></font>
                <div id="toggleLink" style="display: none; text-align: center;">
                    <textarea rows="1" cols="40" name="linkurl" id="linkurl" style="font-size:9px;" onclick="javascript:document.getElementById('linkurl').select();"><%=Str.cleanForHtml("http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+twitterusername+"/picture/")%></textarea>
                    <br/><font class="tinyfont">Copy and paste the above URL into your blog, email, im or website to link to this page.</font>
                </div>
                <br/><br/>
                <%=AdUtil.get336x280PROFILEPIC()%>
                </center>
            </div>
            <!-- End Left Col -->
        </td>

    </tr>
</table>










<%@ include file="/template/footer.jsp" %>
