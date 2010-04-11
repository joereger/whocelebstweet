<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmluibeans.PublicIndex" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.celebtwit.helpers.*" %>
<%@ page import="com.celebtwit.dao.Twitpost" %>
<%@ page import="com.celebtwit.htmluibeans.PublicIndexWhoPanel" %>
<%@ page import="com.celebtwit.util.Num" %>
<%@ page import="com.celebtwit.ads.AdUtil" %>
<%@ page import="com.celebtwit.cachedstuff.CachedStuff" %>
<%@ page import="com.celebtwit.cachedstuff.IndexTweetlist" %>
<%@ page import="com.celebtwit.cachedstuff.GetCachedStuff" %>


<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Test";
String metaKeywords = "";
String metaDescription = "";
String navtab = "home";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>
<%@ include file="/template/header.jsp" %>


<%
String rightCol = PublicRightcolListCelebs.generateHtml(Pagez.getUserSession().getPl());
%>
<%=rightCol%>


<br/><br/><br/>
<%
    IndexTweetlist tweetList = new IndexTweetlist(1, Pagez.getUserSession().getAdNetworkName());
    tweetList.refresh(Pagez.getUserSession().getPl());
%>
<%=tweetList.getHtml()%>




<%@ include file="/template/footer.jsp" %>
