<%@ page import="com.celebtwit.pageperformance.PagePerformanceUtil" %>
<%@ page import="com.celebtwit.systemprops.InstanceProperties" %>

<% if (Pagez.getUserSession()!=null && Pagez.getUserSession().getPl()!=null) { %>
    <% if (Pagez.getUserSession().getPl().getCustomdomain1().equals("www.whoathletestweet.com")) { %>
        <%@ include file="footer-athletes.jsp" %>
    <% } else { %>
        <%@ include file="footer-celebtwit.jsp" %>
    <% }%>
<% } else { %>
    <%@ include file="footer-celebtwit.jsp" %>
<% }%>

<%
    //Performance recording
    try {
        String prePendPageId = "";
        long elapsedtimeFooter = Pagez.getElapsedTime();
        PagePerformanceUtil.add(prePendPageId+request.getServletPath(), InstanceProperties.getInstancename(), elapsedtimeFooter);
    } catch (Exception ex) {
        logger.error("", ex);
    }

%>
