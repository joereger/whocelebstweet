<% if (Pagez.getUserSession()!=null && Pagez.getUserSession().getPl()!=null) { %>
    <% if (Pagez.getUserSession().getPl().getCustomdomain1().equalsIgnoreCase("www.whocelebstweet.com")) { %>
        <%@ include file="header-celebtwit.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getCustomdomain1().equalsIgnoreCase("www.whoathletestweet.com")) { %>
        <%@ include file="header-athletes.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getCustomdomain1().equalsIgnoreCase("www.whopoliticianstweet.com")) { %>
        <%@ include file="header-politicians.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getCustomdomain1().equalsIgnoreCase("www.whotechpunditsstweet.com")) { %>
        <%@ include file="header-techpundits.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getCustomdomain1().equalsIgnoreCase("www.whotriathletestweet.com")) { %>
        <%@ include file="header-triathletes.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getCustomdomain1().equalsIgnoreCase("www.whocycliststweet.com")) { %>
        <%@ include file="header-cyclists.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getCustomdomain1().equalsIgnoreCase("www.whoceostweet.com")) { %>
        <%@ include file="header-ceos.jsp" %>
    <% } else { %>
        <%@ include file="header-celebtwit.jsp" %>
    <% }%>
<% } else { %>
    <%@ include file="header-celebtwit.jsp" %>
<% }%>
