<%@ page import="com.celebtwit.util.Str" %>
<%@ page import="com.celebtwit.dao.Twit" %>
<%@ page import="com.celebtwit.helpers.FindTwitFromTwitterusername" %>
<%@ page import="com.celebtwit.helpers.CountMentionsByCelebs" %>
<%@ page import="com.celebtwit.systemprops.SystemProperty" %>
<%@ page import="com.celebtwit.embed.JsCelebMentions" %>
<%
    Twit twit = FindTwitFromTwitterusername.find(request.getParameter("twitterusername"));
    String twitterusername =FindTwitFromTwitterusername.cleanTwitterusername(request.getParameter("twitterusername"));

    String o = JsCelebMentions.get(twit, twitterusername);

    String output = "";
    output = Str.cleanForjavascriptAndReplaceDoubleQuoteWithSingle(o);
    output = output.replaceAll("\\n", "\"+\\\n\"");
    output = output.replaceAll("\\r", "\"+\\\n\"");
    output = "document.write(\""+output+"\");"+"\n";

    out.print(output);
%>