<%@ page import="com.celebtwit.ebay.FindAuctions" %>
<%@ page import="com.celebtwit.htmluibeans.SysadminIndex" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.util.*" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "SysAdmin Home";
String metaKeywords = "";
String metaDescription = "";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
    SysadminIndex sysadminIndex=(SysadminIndex) Pagez.getBeanMgr().get("SysadminIndex");
%>
<%@ include file="/template/header.jsp" %>



    

    <div class="rounded" style="padding: 15px; margin: 8px; background: #e6e6e6;">
        <%=sysadminIndex.getServermemory()%>
    </div>




    








<%@ include file="/template/footer.jsp" %>