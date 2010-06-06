<%@ page import="com.celebtwit.cachedstuff.CachedStuff" %>
<%@ page import="com.celebtwit.cachedstuff.Chatter" %>
<%@ page import="com.celebtwit.cachedstuff.GetCachedStuff" %>
<%@ page import="com.celebtwit.dao.Mention" %>
<%@ page import="com.celebtwit.dao.Twitpost" %>
<%@ page import="com.celebtwit.helpers.FindTwitFromTwitterusername" %>
<%@ page import="com.celebtwit.helpers.TwitpostAsHtml" %>
<%@ page import="com.celebtwit.util.Str" %>
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
Twit twit1 = FindTwitFromTwitterusername.find(request.getParameter("twitterusername1"));
String twitterusername1 =FindTwitFromTwitterusername.cleanTwitterusername(request.getParameter("twitterusername1"));
String nameOnScreen1 = twitterusername1;
String twit1imageurl = "/images/clear.gif";
int twitid1 = 0;
boolean twit1isceleb = false;
String pagetitleName1 = twitterusername1;
if (twit1!=null){
    twitterusername1 = twit1.getTwitterusername();
    twit1imageurl = twit1.getProfile_image_url();
    twitid1 = twit1.getTwitid();
    if (twit1.getIsceleb()){
        pagetitleName1 = twit1.getRealname();
        nameOnScreen1 = twit1.getRealname();
        twit1isceleb = true;
    }
}
%>
<%
Twit twit2 = FindTwitFromTwitterusername.find(request.getParameter("twitterusername2"));
String twitterusername2 =FindTwitFromTwitterusername.cleanTwitterusername(request.getParameter("twitterusername2"));
String nameOnScreen2 = twitterusername2;
String twit2imageurl = "/images/clear.gif";
int twitid2 = 0;
boolean twit2isceleb = false;
String pagetitleName2 = twitterusername2;
if (twit2!=null){
    twitterusername2 = twit2.getTwitterusername();
    twit2imageurl = twit2.getProfile_image_url();
    twitid2 = twit2.getTwitid();
    if (twit2.getIsceleb()){
        pagetitleName2 = twit2.getRealname();
        nameOnScreen2 = twit2.getRealname();
        twit2isceleb = true;
    }
}
%>
<%
pagetitle = pagetitleName1 + " and "+pagetitleName2+"'s Twitter Chatter Back-and-Forth on "+Pagez.getUserSession().getPl().getName()+"!";
%>
<%
metaDescription = "Twitter chatter between "+pagetitleName1+" and "+pagetitleName2+". See what they say to each other, one on each side of the screen for easy viewing.";
%>
<%
metaKeywords = pagetitleName1 + " " + pagetitleName2 + " " + nameOnScreen1 + " " + nameOnScreen2;
%>
<%@ include file="/template/header.jsp" %>


<script language="javascript">
function toggleLink() {
    var ele = document.getElementById("toggleLink");
    if(ele.style.display == "block") {
        ele.style.display = "none";
    } else {
        ele.style.display = "block";
    }
}
</script>



<div class="roundedBox" style="width:630px;">
    <center>
        <%
        String tweetThisStatus = "chatter between @"+twitterusername1+" and @"+twitterusername2+ " at " + "http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/chatter/"+twitterusername1+"/"+twitterusername2+"/";
        tweetThisStatus = URLEncoder.encode(tweetThisStatus, "UTF-8");
        %>
        <font class="smallfont"><a href="javascript:toggleLink();" style="text-decoration: underline; color: #0000ff;">Link to this Page</a> | <a href="http://twitter.com/home?status=<%=tweetThisStatus%>" style="text-decoration: underline; color: #0000ff;" target="_blank">Tweet This</a></font>
        <div id="toggleLink" style="display: none; text-align: center;">
            <textarea rows="1" cols="80" name="linkurl" id="linkurl" style="font-size:9px;" onclick="javascript:document.getElementById('linkurl').select();"><%=Str.cleanForHtml("http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/chatter/"+twitterusername1+"/"+twitterusername2+"/")%></textarea>
            <br/><font class="tinyfont">Copy and paste the above URL into your blog, email, im or website to link to this page.</font>
        </div>
    </center>
    <center><font class="largefont">twitter chatter between</font><br/></center>
    <table cellpadding="10" cellspacing="5" border="0" width="100%">
        <tr>
            <td valign="top" width="200" align="left">
                <a href="/twitter/<%=twitterusername1%>/picture/"><img src="<%=twit1imageurl%>" width="48" height="48" style="border: 10px solid #ffffff;" align="left"></a>
                <font class="mediumfont"> <a href="/twitter/<%=twitterusername1%>/">@<%=nameOnScreen1%></a></font>
                <%if (!twit1isceleb){%>
                    <br/><font class="smallfont">note: @<%=twitterusername1%> is not a <%=Pagez.getUserSession().getPl().getCelebiscalled()%> so we can't show their tweets</font>
                <%} %>
            </td>
            <td valign="top" width="100" align="left">
                <img src="/images/chatter-arrows.png" alt="" width="100" height="48" border="0">
            </td>
            <td valign="top" width="200" align="right">
                <a href="/twitter/<%=twitterusername2%>/picture/"><img src="<%=twit2imageurl%>" width="48" height="48" style="border: 10px solid #ffffff;" align="right"></a>
                <font class="mediumfont"><a href="/twitter/<%=twitterusername2%>/">@<%=nameOnScreen2%></a> </font>
                <%if (!twit2isceleb){%>
                    <br/><font class="smallfont">note: @<%=twitterusername2%> is not a <%=Pagez.getUserSession().getPl().getCelebiscalled()%> so we can't show their tweets</font>
                <%} %>
            </td>
        </tr>
    </table>
</div>

<table cellpadding="3" cellspacing="0" border="0" width="100%">
    <tr>
        <td valign="top">

            <div class="roundedBoxXXX" style="width:100%;">
                <center><font class="mediumfont">what they're saying to each other</font></center>
                <br/><br/>
                <%
                CachedStuff cs = new Chatter(twitid1, twitid2, Pagez.getUserSession().getAdNetworkName());
                Chatter obj = (Chatter) GetCachedStuff.get(cs, Pagez.getUserSession().getPl());
                String chatterHtml = obj.getHtml();
                %>
                <%=chatterHtml%>
            </div>
            
        </td>
    </tr>
</table>





<%@ include file="/template/footer.jsp" %>
