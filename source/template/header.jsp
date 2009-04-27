<% if (Pagez.getUserSession()!=null && Pagez.getUserSession().getPl()!=null) { %>
    <% if (Pagez.getUserSession().getPl().getName().equalsIgnoreCase("whocelebstweet.com")) { %>
        <%@ include file="header-celebtwit.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getName().equalsIgnoreCase("whoathletestweet.com")) { %>
        <%@ include file="header-athletes.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getName().equalsIgnoreCase("whopoliticianstweet.com")) { %>
        <%@ include file="header-politicians.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getName().equalsIgnoreCase("whotechpunditstweet.com")) { %>
        <%@ include file="header-techpundits.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getName().equalsIgnoreCase("whotriathletestweet.com")) { %>
        <%@ include file="header-triathletes.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getName().equalsIgnoreCase("whocycliststweet.com")) { %>
        <%@ include file="header-cyclists.jsp" %>
    <% } else if (Pagez.getUserSession().getPl().getName().equalsIgnoreCase("whoceostweet.com")) { %>
        <%@ include file="header-ceos.jsp" %>
    <% } else { %>
        <%@ include file="header-celebtwit.jsp" %>
    <% }%>
<% } else { %>
    <%@ include file="header-celebtwit.jsp" %>
<% }%>
