<% if (Pagez.getUserSession()!=null && Pagez.getUserSession().getPl()!=null) { %>
    <% if (Pagez.getUserSession().getPl().getCustomdomain1().equalsIgnoreCase("www.whoathletestweet.com")) { %>
        <%@ include file="header-athletes.jsp" %>
    <% } else { %>
        <%@ include file="header-celebtwit.jsp" %>
    <% }%>
<% } else { %>
    <%@ include file="header-celebtwit.jsp" %>
<% }%>
