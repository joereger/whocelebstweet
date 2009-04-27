<%@ page import="com.celebtwit.pageperformance.PagePerformanceUtil" %>
<%@ page import="com.celebtwit.systemprops.InstanceProperties" %>

<% if (Pagez.getUserSession()!=null && Pagez.getUserSession().getPl()!=null) { %>
    <% if (Pagez.getUserSession().getPl().getName().equalsIgnoreCase("whocelebstweet.com")) { %>
        <%@ include file="footer-celebtwit.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getName().equalsIgnoreCase("whoathletestweet.com")) { %>
        <%@ include file="footer-athletes.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getName().equalsIgnoreCase("whopoliticianstweet.com")) { %>
        <%@ include file="footer-politicians.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getName().equalsIgnoreCase("whotechpunditstweet.com")) { %>
        <%@ include file="footer-techpundits.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getName().equalsIgnoreCase("whotriathletestweet.com")) { %>
        <%@ include file="footer-triathletes.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getName().equalsIgnoreCase("whocycliststweet.com")) { %>
        <%@ include file="footer-cyclists.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getName().equalsIgnoreCase("whoceostweet.com")) { %>
        <%@ include file="footer-ceos.jsp" %>
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
