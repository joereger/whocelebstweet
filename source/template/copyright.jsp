<%@ page import="com.celebtwit.htmlui.Pagez" %>
<%@ page import="com.celebtwit.systemprops.InstanceProperties" %>
<table width="100%" cellspacing="0" border="0" cellpadding="0">
<tr>
    <tr>
        <td valign="top" align="right">
            <center><font class="tinyfont">Copyright 2009 - 2010. All rights reserved.</font> <font class="tinyfont" style="color: #cccccc; padding-right: 10px;">At Your Service is a Server Called: <%=InstanceProperties.getInstancename()%> which built this page in: <a href="/pageperformance.jsp" style="color: #999999;"><%=Pagez.getElapsedTime()%> milliseconds</a></font></center>
        </td>
    </tr>
</table>