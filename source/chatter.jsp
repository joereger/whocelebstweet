<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmluibeans.PublicIndex" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.celebtwit.helpers.*" %>
<%@ page import="com.celebtwit.systemprops.SystemProperty" %>
<%@ page import="com.celebtwit.dao.Twitpost" %>
<%@ page import="com.celebtwit.util.Str" %>
<%@ page import="com.celebtwit.util.Time" %>
<%@ page import="com.celebtwit.ads.AdUtil" %>
<%@ page import="com.celebtwit.embed.JsCelebMentions" %>
<%@ page import="com.celebtwit.embed.JsDifferentCelebs" %>
<%@ page import="com.celebtwit.dao.Mention" %>
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
Twit twit1 = FindTwitFromTwitterusername.find(request.getParameter("twitterusername1"));
String twitterusername1 =FindTwitFromTwitterusername.cleanTwitterusername(request.getParameter("twitterusername1"));
String nameOnScreen1 = twitterusername1;
String twit1imageurl = "/images/clear.gif";
int twitid1 = 0;
String pagetitleName1 = twitterusername1;
if (twit1!=null){
    twitterusername1 = twit1.getTwitterusername();
    twit1imageurl = twit1.getProfile_image_url();
    twitid1 = twit1.getTwitid();
    if (twit1.getIsceleb()){
        pagetitleName1 = twit1.getRealname();
        nameOnScreen1 = twit1.getRealname();
    }
}
%>
<%
Twit twit2 = FindTwitFromTwitterusername.find(request.getParameter("twitterusername2"));
String twitterusername2 =FindTwitFromTwitterusername.cleanTwitterusername(request.getParameter("twitterusername2"));
String nameOnScreen2 = twitterusername2;
String twit2imageurl = "/images/clear.gif";
int twitid2 = 0;
String pagetitleName2 = twitterusername2;
if (twit2!=null){
    twitterusername2 = twit2.getTwitterusername();
    twit2imageurl = twit2.getProfile_image_url();
    twitid2 = twit2.getTwitid();
    if (twit2.getIsceleb()){
        pagetitleName2 = twit2.getRealname();
        nameOnScreen2 = twit2.getRealname();
    }
}
%>
<%
pagetitle = pagetitleName1 + " and "+pagetitleName2+"'s Twitter Chatter Back-and-Forth on "+Pagez.getUserSession().getPl().getName()+"!";
%>
<%
metaDescription = "Twitter chatter between "+pagetitleName1+" and "+pagetitleName2+". ";
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
                <img src="<%=twit1imageurl%>" width="48" height="48" border="0" align="left">
                <font class="mediumfont"> <a href="/twitter/<%=twitterusername1%>/">@<%=nameOnScreen1%></a></font>
            </td>
            <td valign="top" width="100" align="left">
                <img src="/images/chatter-arrows.png" alt="" width="100" height="48" border="0">
            </td>
            <td valign="top" width="200" align="right">
                <img src="<%=twit2imageurl%>" width="48" height="48" border="0" align="right">
                <font class="mediumfont"><a href="/twitter/<%=twitterusername2%>/">@<%=nameOnScreen2%></a> </font>
            </td>
        </tr>
    </table>
</div>

<table cellpadding="3" cellspacing="0" border="0" width="100%">
    <tr>
        <td valign="top">

            <div class="roundedBoxXXX" style="width:420px;">
                <font class="mediumfont">what they're saying to each other</font>
                <br/><br/>
                <%
                    String empty = "";
                    List<Mention> mentions = HibernateUtil.getSession().createQuery(empty + "from Mention where (twitidceleb='"+twitid1+"' and twitidmentioned='"+twitid2+"') or (twitidceleb='"+twitid2+"' and twitidmentioned='"+twitid1+"')").setMaxResults(200).list();
                    ArrayList<Integer> twitpostids = new ArrayList<Integer>();
                    for (Iterator<Mention> menIt=mentions.iterator(); menIt.hasNext();) {
                        Mention mention=menIt.next();
                        twitpostids.add(mention.getTwitpostid());
                    }
                    if (twitpostids.size()>0){
                        List<Twitpost> twitposts = HibernateUtil.getSession().createCriteria(Twitpost.class)
                                                           .add(Restrictions.in("twitpostid", twitpostids))
                                                           .addOrder(Order.desc("twitterguid"))
                                                           .setCacheable(true)
                                                           .list();
                        for (Iterator<Twitpost> tpIt=twitposts.iterator(); tpIt.hasNext();) {
                            Twitpost twitpost=tpIt.next();
                            %><%=TwitpostAsHtml.get(twitpost, 400)%><%
                        }
                    } else {
                        %><br/><font class="normalfont">No chatter between them!</font><%
                    }
                %>
            </div>
            
        </td>
        <td valign="top" width="160">
            <img src="/images/clear.gif" alt="" width="1" height="90"><br/>
            <%=AdUtil.get160x600()%>
        </td>
    </tr>
</table>





<%@ include file="/template/footer.jsp" %>
