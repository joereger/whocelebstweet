<%@ page import="com.celebtwit.cachedstuff.*" %>


<font class="largefont" style="font-size:60px;"> <%=subnav_title%> </font>
<br/>
<div class="celebsubnav normalfont">
    <a href="/twitter/<%=subnav_twitterusername%>/who/">Who @<%=subnav_twitterusername%> Tweets</a>
    <%
    if (twit!=null){
        CachedStuff cs = new CelebsSubnavKeywordsLink(twit);
        CelebsSubnavKeywordsLink obj = (CelebsSubnavKeywordsLink) GetCachedStuff.get(cs, Pagez.getUserSession().getPl());
        String subnav_keywordslink = obj.getHtml();
        %><%=subnav_keywordslink%><%
    }
    %>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <a href="/twitter/<%=subnav_twitterusername%>/">All Tweets</a>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <a href="/twitter/<%=subnav_twitterusername%>/picture/">Profile Pic</a>
</div>
<br/>
<br/>