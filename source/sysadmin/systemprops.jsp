<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmluibeans.SysadminSystemProps" %>
<%@ page import="com.celebtwit.htmlui.*" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "SystemProps... Be Careful!!!";
String metaKeywords = "";
String metaDescription = "";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
    SysadminSystemProps sysadminSystemProps=(SysadminSystemProps) Pagez.getBeanMgr().get("SysadminSystemProps");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
        try {
            sysadminSystemProps.setBaseurl(Textbox.getValueFromRequest("baseurl", "baseurl", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.setIssslon(Textbox.getValueFromRequest("issslon", "issslon", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.setDostattweets(Textbox.getValueFromRequest("dostattweets", "dostattweets", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.setSendxmpp(Textbox.getValueFromRequest("sendxmpp", "sendxmpp", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.setSmtpoutboundserver(Textbox.getValueFromRequest("smtpoutboundserver", "smtpoutboundserver", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.saveProps();
            Pagez.getUserSession().setMessage("Save complete.");
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%@ include file="/template/header.jsp" %>


    <form action="/sysadmin/systemprops.jsp" method="post">
        <input type="hidden" name="dpage" value="/sysadmin/systemprops.jsp">
        <input type="hidden" name="action" value="save">

        <table cellpadding="0" cellspacing="0" border="0">

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">Base Url</font>
                    <br/>
                    <font class="tinyfont">The base url that this instance is installed at.  No http:// and no trailing slash!  Ex: www.pingfit.com</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("baseurl", sysadminSystemProps.getBaseurl(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">SendXMPP</font>
                    <br/>
                    <font class="tinyfont">0 or 1.  Whether or not to send XMPP notifications from this installation.</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("sendxmpp", sysadminSystemProps.getSendxmpp(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">SmtpOutboundServer</font>
                    <br/>
                    <font class="tinyfont">Smtp server name or ip address to use to send email.  Default is 'localhost'</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("smtpoutboundserver", sysadminSystemProps.getSmtpoutboundserver(), 255, 35, "", "")%>
                </td>
            </tr>






            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">IsSSLOn</font>
                    <br/>
                    <font class="tinyfont">0 or 1.  Whether SSL is installed at the server level for this install.</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("issslon", sysadminSystemProps.getIssslon(), 255, 35, "", "")%>
                </td>
            </tr>


            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">DoStatTweets</font>
                    <br/>
                    <font class="tinyfont">0 or 1.  Whether to send stat tweets to celebs from this server (careful, should usually be 0.)</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("dostattweets", sysadminSystemProps.getDostattweets(), 255, 35, "", "")%>
                </td>
            </tr>



            <tr>
                <td valign="top">
                </td>
                <td valign="top">
                    <br/><br/>
                    <input type="submit" class="formsubmitbutton" value="Save System Props">
                </td>
             </tr>

        </table>

    </form>

<%@ include file="/template/footer.jsp" %>