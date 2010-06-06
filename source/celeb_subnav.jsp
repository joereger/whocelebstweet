<%@ page import="com.celebtwit.cachedstuff.*" %>


<%
boolean subnav_isceleb = false;
if (twit!=null && twit.getIsceleb()){
    subnav_isceleb = true;
}
%>

<font class="largefont" style="font-size:60px;"> <a href="/twitter/<%=subnav_twitterusername%>/" style="color:#000000;"><%=subnav_title%></a> </font>
<br/>

<%if (subnav_isceleb){%>
    <div class="celebsubnav normalfont">
        <%
        if (subnav_isceleb){
            %><a href="/twitter/<%=subnav_twitterusername%>/who/">Who @<%=subnav_twitterusername%> Tweets</a><%
        } else {
            //Do something different for non-celebs here
        }
        %>
        <%
        if (twit!=null){
            CachedStuff cs = new CelebsSubnavKeywordsLink(twit);
            CelebsSubnavKeywordsLink obj = (CelebsSubnavKeywordsLink) GetCachedStuff.get(cs, Pagez.getUserSession().getPl());
            String subnav_keywordslink = obj.getHtml();
            %><%=subnav_keywordslink%><%
        }
        %>
        <%
        if (subnav_isceleb){
            %>&nbsp;&nbsp;&nbsp;&nbsp;<a href="/twitter/<%=subnav_twitterusername%>/">All Tweets</a><%
        }
        %>
        <%
        if (subnav_isceleb){
            %>&nbsp;&nbsp;&nbsp;&nbsp;<a href="/twitter/<%=subnav_twitterusername%>/picture/">Profile Pic</a><%
        }
        %>
    </div>
    <br/>
<%} %>
<br/>