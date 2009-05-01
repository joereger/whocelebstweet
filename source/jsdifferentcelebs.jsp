<%@ page import="com.celebtwit.util.Str" %>
<%@ page import="com.celebtwit.helpers.FindTwitFromTwitterusername" %>
<%@ page import="com.celebtwit.dao.Twit" %>
<%@ page import="com.celebtwit.helpers.CountUniqueCelebsWhoMentioned" %>
<%@ page import="com.celebtwit.systemprops.SystemProperty" %>
<%@ page import="com.celebtwit.embed.JsDifferentCelebs" %>
<%@ page import="com.celebtwit.htmlui.Pagez" %>
<%
    Twit twit = FindTwitFromTwitterusername.find(request.getParameter("twitterusername"));
    String twitterusername =FindTwitFromTwitterusername.cleanTwitterusername(request.getParameter("twitterusername"));

    String o = JsDifferentCelebs.get(twit, twitterusername, Pagez.getUserSession().getPl());

    String output = "";
    output = Str.cleanForjavascriptAndReplaceDoubleQuoteWithSingle(o);
    output = output.replaceAll("\\n", "\"+\\\n\"");
    output = output.replaceAll("\\r", "\"+\\\n\"");
    output = "document.write(\""+output+"\");"+"\n";

    out.print(output);
%>