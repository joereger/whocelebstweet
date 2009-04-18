<%@ page import="com.celebtwit.htmlui.Authorization" %>
<%@ page import="com.celebtwit.htmlui.Pagez" %>
<%
    boolean isauthorised=Authorization.check(acl);
    if (!isauthorised){
        if (Pagez.getUserSession()!=null && Pagez.getUserSession().getUser()!=null && Pagez.getUserSession().getIsloggedin()) {
            Pagez.sendRedirect("/notauthorized.jsp");
            return;
        } else {
            Pagez.sendRedirect("/login.jsp");
            return;
        }
    }
%>