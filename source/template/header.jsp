<%@ page import="com.celebtwit.dao.Pl" %>
<%@ page import="com.celebtwit.privatelabel.*" %>
<%@ page import="com.celebtwit.htmlui.*" %>
<%
    String finalMetaKeywords = "";
    if (metaKeywords!=null && !metaKeywords.equals("")){ finalMetaKeywords = metaKeywords; }
%>

<%
    String finalMetaDescription = "";
    if (metaDescription!=null && !metaDescription.equals("")){ finalMetaDescription = metaDescription; }
%>

<%
Pl plHeaderFooter = PlFinder.find(request);
if (Pagez.getUserSession()==null){Pagez.setUserSession(new UserSession()); logger.debug("Pagez.getUserSession() was null so created new UserSession()");}
Pagez.getUserSession().setIsSisterPl(PlFinder.isSisterPl(request, plHeaderFooter));
logger.debug("Pagez.getUserSession().isSisterPl()="+Pagez.getUserSession().isSisterPl());
%>

<% if (plHeaderFooter!=null) { %>
    <% if (plHeaderFooter.getName().equalsIgnoreCase("whocelebstweet.com")) { %>
        <%@ include file="header-celebtwit.jsp" %>
    <% } else if (plHeaderFooter.getName().equalsIgnoreCase("whoathletestweet.com")) { %>
        <%@ include file="header-athletes.jsp" %>
    <% } else if (plHeaderFooter.getName().equalsIgnoreCase("whopoliticianstweet.com")) { %>
        <%@ include file="header-politicians.jsp" %>
    <% } else if (plHeaderFooter.getName().equalsIgnoreCase("whotechpunditstweet.com")) { %>
        <%@ include file="header-techpundits.jsp" %>
    <% } else if (plHeaderFooter.getName().equalsIgnoreCase("peachtweets.com")) { %>
        <%@ include file="header-atlantatechpundits.jsp" %>
    <% } else if (plHeaderFooter.getName().equalsIgnoreCase("whotriathletestweet.com")) { %>
        <%@ include file="header-triathletes.jsp" %>
    <% } else if (plHeaderFooter.getName().equalsIgnoreCase("whocycliststweet.com")) { %>
        <%@ include file="header-cyclists.jsp" %>
    <% } else if (plHeaderFooter.getName().equalsIgnoreCase("whomodelstweet.com")) { %>
        <%@ include file="header-models.jsp" %>
    <% } else if (plHeaderFooter.getName().equalsIgnoreCase("whopornstarstweet.com")) { %>
        <%@ include file="header-pornstars.jsp" %>
    <% } else if (plHeaderFooter.getName().equalsIgnoreCase("whoceostweet.com")) { %>
        <%@ include file="header-ceos.jsp" %>
    <% } else { %>
        <%@ include file="header-celebtwit.jsp" %>
    <% }%>
<% } else { %>
    <%@ include file="header-celebtwit.jsp" %>
<% }%>
