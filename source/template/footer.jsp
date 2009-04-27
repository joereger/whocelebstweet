<%@ page import="com.celebtwit.pageperformance.PagePerformanceUtil" %>
<%@ page import="com.celebtwit.systemprops.InstanceProperties" %>

<% if (Pagez.getUserSession()!=null && Pagez.getUserSession().getPl()!=null) { %>
    <% if (Pagez.getUserSession().getPl().getCustomdomain1().equalsIgnoreCase("www.whocelebstweet.com")) { %>
        <%@ include file="footer-celebtwit.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getCustomdomain1().equalsIgnoreCase("www.whoathletestweet.com")) { %>
        <%@ include file="footer-athletes.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getCustomdomain1().equalsIgnoreCase("www.whopoliticianstweet.com")) { %>
        <%@ include file="footer-politicians.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getCustomdomain1().equalsIgnoreCase("www.whotechpunditsstweet.com")) { %>
        <%@ include file="footer-techpundits.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getCustomdomain1().equalsIgnoreCase("www.whotriathletestweet.com")) { %>
        <%@ include file="footer-triathletes.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getCustomdomain1().equalsIgnoreCase("www.whocycliststweet.com")) { %>
        <%@ include file="footer-cyclists.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getCustomdomain1().equalsIgnoreCase("www.whoceostweet.com")) { %>
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
