<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmluibeans.AccountIndex" %>
<%@ page import="com.celebtwit.htmlui.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.celebtwit.dao.hibernate.HibernateUtil" %>
<%@ page import="org.hibernate.criterion.Restrictions" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.celebtwit.util.Time" %>
<%@ page import="org.hibernate.criterion.Order" %>
<%@ page import="com.celebtwit.cache.providers.CacheFactory" %>
<%@ page import="com.celebtwit.systemprops.SystemProperty" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "";
String navtab = "account";
String acl = "account";
%>
<%
AccountIndex accountIndex = (AccountIndex) Pagez.getBeanMgr().get("AccountIndex");
%>
<%@ include file="/template/auth.jsp" %>

<%@ include file="/template/header.jsp" %>


Account Home


<%@ include file="/template/footer.jsp" %>


