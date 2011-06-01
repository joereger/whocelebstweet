<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmluibeans.PublicIndex" %>
<%@ page import="com.celebtwit.helpers.*" %>
<%@ page import="com.celebtwit.dao.Twitpost" %>
<%@ page import="com.celebtwit.htmluibeans.PublicIndexWhoPanel" %>
<%@ page import="com.celebtwit.util.Num" %>
<%@ page import="com.celebtwit.ads.AdUtil" %>
<%@ page import="com.celebtwit.cachedstuff.CachedStuff" %>
<%@ page import="com.celebtwit.cachedstuff.IndexTweetlist" %>
<%@ page import="com.celebtwit.cachedstuff.GetCachedStuff" %>
<%@ page import="com.celebtwit.cache.providers.CacheFactory" %>
<%@ page import="org.infinispan.Cache" %>
<%@ page import="java.util.*" %>
<%@ page import="com.celebtwit.cache.providers.infinispan.InfinispanProvider" %>
<%@ page import="org.infinispan.tree.NodeKey" %>
<%@ page import="org.infinispan.container.entries.InternalCacheEntry" %>
<%@ page import="org.infinispan.atomic.AtomicHashMap" %>
<%@ page import="org.infinispan.tree.TreeCache" %>
<%@ page import="org.infinispan.tree.Fqn" %>
<%@ page import="org.infinispan.util.FastCopyHashMap" %>
<%@ page import="com.celebtwit.cachedstuff.CacheLogger" %>
<%@ page import="org.infinispan.context.Flag" %>


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
        valincache = String.valueOf(obj);
    } catch (Exception ex){
        logger.error("", ex);
    }
%>

<br/><br/>
<font class="mediumfont">Server: <%=InstanceProperties.getInstancename()%> Valincache: <%=valincache%></font>
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

<br/>
<br/>
<font face="Courier,Monospace" size=-2>

<b>Simple Get-------------</b><br/>
<%
    if (1==1){
        //Cache cache = InfinispanProvider.getCacheSimple();
        TreeCache treeCache = InfinispanProvider.getCache();
        Fqn fqn = Fqn.fromString("/"+group);
        Object obj = treeCache.get(fqn, key);
        %>val=<%=String.valueOf(obj)%><br/><br/><%
    }
%>
<br/><br/>
<b>FQN Exists-------------</b><br/>
<%
    if (1==1){
        TreeCache treeCache = InfinispanProvider.getCache();
        Fqn fqn = Fqn.fromString("/"+group);
        boolean fqnExists = treeCache.exists(fqn);
        %>fqnExists=<%=fqnExists%><br/><%
    }
%>




<%--<br/><br/>--%>
<%--<b>Cache Get, no flags-------------</b><br/>--%>
<%--<%--%>
    <%--if (1==1){--%>
        <%--Cache cache = InfinispanProvider.getCache();--%>
        <%--Fqn fqn = Fqn.fromString("/" + group);--%>
        <%--Object element = cache.get(fqn, key);--%>
        <%--if (element!=null){--%>
            <%--%>treeCache.get(fqn, key)=<%=String.valueOf(element)%><%--%>
        <%--} else {--%>
            <%--%>treeCache.get(fqn, key)=null<%--%>
        <%--}--%>
    <%--}--%>
<%--%>--%>


<%--<br/><br/>--%>
<%--<b>Cache Get, Flag.FORCE_SYNCHRONOUS-------------</b><br/>--%>
<%--<%--%>
    <%--if (1==1){--%>
        <%--//Cache cache = InfinispanProvider.getCacheSimple();--%>
        <%--TreeCache treeCache = InfinispanProvider.getCache();--%>
        <%--Fqn fqn = Fqn.fromString("/" + group);--%>
        <%--Object element = treeCache.get(fqn, key, Flag.FORCE_SYNCHRONOUS);--%>
        <%--if (element!=null){--%>
            <%--%>treeCache.get(fqn, key)=<%=String.valueOf(element)%><%--%>
        <%--} else {--%>
            <%--%>treeCache.get(fqn, key)=null<%--%>
        <%--}--%>
    <%--}--%>
<%--%>--%>


<br/><br/>
<b>TreeCache Get, no flags-------------</b><br/>
<%
    if (1==1){
        TreeCache treeCache = InfinispanProvider.getCache();
        Fqn fqn = Fqn.fromString("/"+group);
        Object element = treeCache.get(fqn, key);
        if (element!=null){
            %>treeCache.get(fqn, key)=<%=String.valueOf(element)%><%
        } else {
            %>treeCache.get(fqn, key)=null<%
        }
    }
%>


<br/><br/>
<b>TreeCache Get, Flag.FORCE_SYNCHRONOUS-------------</b><br/>
<%
    if (1==1){
        //Cache cache = InfinispanProvider.getCacheSimple();
        TreeCache treeCache = InfinispanProvider.getCache();
        Fqn fqn = Fqn.fromString("/" + group);
        Object element = treeCache.get(fqn, key, Flag.FORCE_SYNCHRONOUS);
        if (element!=null){
            %>treeCache.get(fqn, key)=<%=String.valueOf(element)%><%
        } else {
            %>treeCache.get(fqn, key)=null<%
        }
    }
%>




<%--<br/><br/>--%>
<%--<b>Iterate Cache-------------</b><br/>--%>

<%--<%--%>
    <%--if (1==1){--%>
        <%--Cache cache = InfinispanProvider.getCacheSimple();--%>
        <%--java.util.Set entries = cache.entrySet();--%>
        <%--for (Iterator iterator = entries.iterator(); iterator.hasNext();) {--%>
            <%--Object entry = iterator.next();--%>
            <%--InternalCacheEntry cacheEntry = (InternalCacheEntry)entry;--%>
            <%--NodeKey nodeKey = (NodeKey)cacheEntry.getKey();--%>
            <%--String fqn = nodeKey.getFqn().toString();--%>
            <%--AtomicHashMap val = (AtomicHashMap)cacheEntry.getValue();--%>
            <%--%>--%>
            <%--fqn=<%=String.valueOf(fqn)%><br/>--%>
            <%--nodeKey=<%=nodeKey.toString()%><br/>--%>
            <%--val.toString()=<%=val.toString()%><br/>--%>
            <%--val.isEmpty()=<%=val.isEmpty()%><br/>--%>
            <%--start iterate val.entrySet()--<br/>--%>
            <%--<%--%>
                <%--Iterator ents = val.entrySet().iterator();--%>
                <%--while (ents.hasNext()) {--%>
                  <%--Map.Entry thisEntry = (Map.Entry) ents.next();--%>
                  <%--Object k = thisEntry.getKey();--%>
                  <%--Object v = thisEntry.getValue();--%>
                  <%--%> k=<%=String.valueOf(k)%> v=<%=String.valueOf(v)%><br/><%--%>
                <%--}--%>
            <%--%>--%>
            <%--end iterate val.entrySet()--<br/>--%>
            <%--<%--%>
            <%--for (Iterator iterator1 = val.values().iterator(); iterator1.hasNext();) {--%>
                <%--Object value = iterator1.next();--%>
                <%--%>value.toString()=<%=value.toString()%><br/><%--%>
            <%--}--%>
            <%--%>--%>
            <%--<%--%>
            <%--if (1==2){--%>
            <%--for (NodeKey.Type type : nodeKey.getContents().values()){--%>
                <%--%>type.toString()=<%=type.toString()%><br/>type.name()=<%=type.name()%><br/><%--%>
            <%--}--%>
            <%--}--%>
            <%--%>--%>
            <%--<br/><br/>--%>

            <%--<%--%>
        <%--}--%>
    <%--}--%>
<%--%>--%>





</font>



<%@ include file="/template/footer.jsp" %>
