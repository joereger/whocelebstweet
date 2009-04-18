<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmlui.Pagez" %>
<%@ page import="com.celebtwit.util.Str" %>
<%@ page import="com.celebtwit.htmluibeans.SysadminIndex" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "SysAdmin Home";
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