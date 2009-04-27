<%@ page import="com.celebtwit.htmlui.Pagez" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;CHARSET=iso-8859-1"/>
    <%
        String finalTitle = "whoTriathletesTweet.com - See Who Triathletes Tweet on Twitter! Have they tweeted you?";
        if (pagetitle!=null && !pagetitle.equals("")){
            finalTitle = pagetitle;
        }
    %>
    <title><%=finalTitle%></title>
    <link rel="stylesheet" type="text/css" href="/css/basic.css"/>
    <link rel="stylesheet" type="text/css" href="/css/Triathletes.css"/>
    <meta name="description" content="<%=finalTitle%> Triathletes on Twitter!  See who the triathletes are twittering!"/>
    <meta name="keywords" content="triathletes triathlon celebrity twitter twit tweet celeb lance armstrong oprah"/>
    <script type="text/JavaScript" src="/js/curvycorners/curvycorners.js"></script>

</head>
<body LEFTMARGIN="0" TOPMARGIN="0" MARGINWIDTH="0" MARGINHEIGHT="0"><center>

    <%@ include file="message.jsp" %>
    <%@ include file="nav.jsp" %>

<table width="850" cellspacing="0" border="0" cellpadding="5">
<tr>
    <td>
    <div style="text-align: left;">
    <a href="/">
    <img src="/images/logoTriathletes.gif" alt="whoTriathletesTweet.com" width="478" height="132" border="0">
    </a>   

    <br/><br/><br/>

<!-- Start Body -->
<table cellpadding="0" cellspacing="5" border="0">
    <tr>
        <td valign="top" width="660">
