<%@ page import="com.celebtwit.pageperformance.PagePerformanceUtil" %>
<%@ page import="com.celebtwit.systemprops.InstanceProperties" %>

<% if (plHeaderFooter!=null) { %>
    <% if (plHeaderFooter.getName().equalsIgnoreCase("whocelebstweet.com")) { %>
        <%@ include file="footer-celebtwit.jsp" %>
    <% } else if (plHeaderFooter.getName().equalsIgnoreCase("whoathletestweet.com")) { %>
        <%@ include file="footer-athletes.jsp" %>
    <% } else if (plHeaderFooter.getName().equalsIgnoreCase("whopoliticianstweet.com")) { %>
        <%@ include file="footer-politicians.jsp" %>
    <% } else if (plHeaderFooter.getName().equalsIgnoreCase("whotechpunditstweet.com")) { %>
        <%@ include file="footer-techpundits.jsp" %>
    <% } else if (plHeaderFooter.getName().equalsIgnoreCase("peachtweets.com")) { %>
        <%@ include file="footer-atlantatechpundits.jsp" %>
    <% } else if (plHeaderFooter.getName().equalsIgnoreCase("whotriathletestweet.com")) { %>
        <%@ include file="footer-triathletes.jsp" %>
    <% } else if (plHeaderFooter.getName().equalsIgnoreCase("whocycliststweet.com")) { %>
        <%@ include file="footer-cyclists.jsp" %>
    <% } else if (plHeaderFooter.getName().equalsIgnoreCase("whomodelstweet.com")) { %>
        <%@ include file="footer-models.jsp" %>
    <% } else if (plHeaderFooter.getName().equalsIgnoreCase("whoceostweet.com")) { %>
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
