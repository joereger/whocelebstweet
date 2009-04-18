<%@ page import="com.celebtwit.pageperformance.PagePerformanceUtil" %>
<%@ page import="com.celebtwit.systemprops.InstanceProperties" %>
<%@ include file="footer-celebtwit.jsp" %>

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
