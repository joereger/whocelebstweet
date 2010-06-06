<%@ page import="com.celebtwit.helpers.FindTwitFromTwitterusername" %>
<%@ page import="com.celebtwit.dao.Twit" %><%
Twit twit = FindTwitFromTwitterusername.find(request.getParameter("twitterusername"));
if (twit!=null && twit.getIsceleb()){
    //Send to twitter page
    response.sendRedirect("/twitter/"+request.getParameter("twitterusername")+"/");
    return;
} else {
    //Send to the who page which makes a little more sense for non-celebs (well, same page)
    response.sendRedirect("/twitter/"+request.getParameter("twitterusername")+"/");
    return;
}
%>