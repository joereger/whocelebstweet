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
<%@ page import="com.celebtwit.cache.providers.CacheFactory" %>


<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "CacheTest";
String metaKeywords = "";
String metaDescription = "";
String navtab = "home";
String acl = "public";
%>



<%
    String key = "cachetest";
    String group = "cachetest";
%>

<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("update")) {
        CacheFactory.getCacheProvider().put(key, group, String.valueOf(request.getParameter("newvalforcache")));
        Pagez.getUserSession().setMessage("Cache value updated to "+String.valueOf(request.getParameter("newvalforcache"))+".");
    }
%>

<%@ include file="/template/auth.jsp" %>
<%@ include file="/template/header.jsp" %>

<%
    String valincache = "";
    try{
        Object obj = CacheFactory.getCacheProvider().get(key, group);
        //if (obj!=null){
            valincache = String.valueOf(obj);
        //}
    } catch (Exception ex){
        logger.error("", ex);
    }
%>

<br/><br/>
<font class="mediumfont">valincache = <%=valincache%></font>
<br/><br/>
<form action="/cachetest.jsp" method="post">
    <input type="hidden" name="dpage" value="/cachetest.jsp">
    <input type="hidden" name="action" value="update">

    <table cellpadding="5" cellspacing="0" border="0">

        <tr>
            <td valign="top">
                <font class="formfieldnamefont">Value</font>
            </td>
            <td valign="top">
                <%=Textbox.getHtml("newvalforcache", valincache, 255, 20, "", "")%>
            </td>
        </tr>

        <tr>
            <td valign="top">
            </td>
            <td valign="top">
                <input type="submit" class="formsubmitbutton" value="Update">
            </td>
        </tr>

    </table>

</form>




<%@ include file="/template/footer.jsp" %>
