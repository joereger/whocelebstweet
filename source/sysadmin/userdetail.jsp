<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmluibeans.SysadminUserDetail" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.celebtwit.dbgrid.GridCol" %>
<%@ page import="com.celebtwit.dbgrid.Grid" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.celebtwit.htmlui.*" %>
<%@ page import="com.celebtwit.htmluibeans.StaticVariables" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "User: "+((SysadminUserDetail) Pagez.getBeanMgr().get("SysadminUserDetail")).getEmail();
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
SysadminUserDetail sysadminUserDetail = (SysadminUserDetail)Pagez.getBeanMgr().get("SysadminUserDetail");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
        try {
            sysadminUserDetail.setFirstname(Textbox.getValueFromRequest("firstname", "First Name", true, DatatypeString.DATATYPEID));
            sysadminUserDetail.setLastname(Textbox.getValueFromRequest("lastname", "Last Name", true, DatatypeString.DATATYPEID));
            sysadminUserDetail.setEmail(Textbox.getValueFromRequest("email", "Email", false, DatatypeString.DATATYPEID));
            sysadminUserDetail.save();
            Pagez.getUserSession().setMessage("User details saved");
        } catch (com.celebtwit.htmlui.ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("togglesysadmin")) {
        try {
            sysadminUserDetail.setActivitypin(Textbox.getValueFromRequest("activitypin", "Activity Pin", false, DatatypeString.DATATYPEID));
            sysadminUserDetail.togglesysadminprivs();
        } catch (com.celebtwit.htmlui.ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("deleteuser")) {
        try {
            sysadminUserDetail.setActivitypin(Textbox.getValueFromRequest("activitypin", "Activity Pin", false, DatatypeString.DATATYPEID));
            sysadminUserDetail.deleteuser();
            Pagez.getUserSession().setMessage("User deleted");
            Pagez.sendRedirect("/sysadmin/userlist.jsp");
        } catch (com.celebtwit.htmlui.ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("toggleisenabled")) {
        try {
            sysadminUserDetail.toggleisenabled();
        } catch (com.celebtwit.htmlui.ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>

<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("passwordresetemail")) {
        try {
            sysadminUserDetail.sendresetpasswordemail();
        } catch (com.celebtwit.htmlui.ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("reactivationemail")) {
        try {
            sysadminUserDetail.reactivatebyemail();
        } catch (com.celebtwit.htmlui.ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>

<%@ include file="/template/header.jsp" %>



        <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td valign="top" width="50%">

                        <div class="rounded" style="padding: 15px; margin: 5px; background: #BFFFBF;">
                            <form action="/sysadmin/userdetail.jsp" method="post">
                                <input type="hidden" name="dpage" value="/sysadmin/userdetail.jsp">
                                <input type="hidden" name="action" value="save">
                                <input type="hidden" name="userid" value="<%=sysadminUserDetail.getUserid()%>">
                                <table cellpadding="0" cellspacing="0" border="0">
                                    <tr>
                                        <td valign="top">
                                            <font class="formfieldnamefont">First Name</font>
                                        </td>
                                        <td valign="top">
                                            <%=Textbox.getHtml("firstname", sysadminUserDetail.getFirstname(), 255, 35, "", "")%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td valign="top">
                                            <font class="formfieldnamefont">Last Name</font>
                                        </td>
                                        <td valign="top">
                                            <%=Textbox.getHtml("lastname", sysadminUserDetail.getLastname(), 255, 35, "", "")%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td valign="top">
                                            <font class="formfieldnamefont">Email</font>
                                        </td>
                                        <td valign="top">
                                            <%=Textbox.getHtml("email", sysadminUserDetail.getEmail(), 255, 35, "", "")%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td valign="top">

                                        </td>
                                        <td valign="top">
                                            <input type="submit" class="formsubmitbutton" value="Save User Details">
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                        <div class="rounded" style="padding: 15px; margin: 5px; background: #BFFFBF;">
                            <form action="/sysadmin/userdetail.jsp" method="post">
                                <input type="hidden" name="dpage" value="/sysadmin/userdetail.jsp">
                                <input type="hidden" name="action" value="passwordresetemail">
                                <input type="hidden" name="userid" value="<%=sysadminUserDetail.getUserid()%>">
                                <input type="submit" class="formsubmitbutton" value="Send Password Reset Email">
                            </form>
                        </div>
                        <div class="rounded" style="padding: 15px; margin: 5px; background: #BFFFBF;">
                            <form action="/sysadmin/userdetail.jsp" method="post">
                                <input type="hidden" name="dpage" value="/sysadmin/userdetail.jsp">
                                <input type="hidden" name="action" value="reactivationemail">
                                <input type="hidden" name="userid" value="<%=sysadminUserDetail.getUserid()%>">
                                <input type="submit" class="formsubmitbutton" value="Force Re-Activation By Email">
                            </form>
                        </div>
                        <div class="rounded" style="padding: 15px; margin: 5px; background: #BFFFBF;">
                            <form action="/sysadmin/userdetail.jsp" method="post">
                                <input type="hidden" name="dpage" value="/sysadmin/userdetail.jsp">
                                <input type="hidden" name="action" value="researcherremainingbalanceoperations">
                                <input type="hidden" name="userid" value="<%=sysadminUserDetail.getUserid()%>">
                                <input type="submit" class="formsubmitbutton" value="ResearcherRemainingBalanceOperations">
                            </form>
                            <br/>
                            <font class="tinyfont">This will process account balances, remaining impressions, credit card transfers, etc for only this account.  Only does something if this user has a researcher record.</font>
                        </div>
                        <div class="rounded" style="padding: 15px; margin: 5px; background: #BFFFBF;">
                            <form action="/sysadmin/userdetail.jsp" method="post">
                                <input type="hidden" name="dpage" value="/sysadmin/userdetail.jsp">
                                <input type="hidden" name="action" value="togglesysadmin">
                                <input type="hidden" name="userid" value="<%=sysadminUserDetail.getUserid()%>">
                                <%if (sysadminUserDetail.getIssysadmin()){%>
                                    <font class="mediumfont">User is a Sysadmin.</font>
                                <%} else {%>
                                    <font class="mediumfont">User is not a Sysadmin.</font>
                                <%}%>
                                <br/>
                                <input type="submit" class="formsubmitbutton" value="Toggle Sysadmin Privileges">
                                <%=Textbox.getHtml("activitypin", String.valueOf(sysadminUserDetail.getActivitypin()), 255, 25, "", "")%>
                                <br/>
                                <font class="tinyfont">You must type "yes, i want to do this" in the box to make this happen</font>
                            </form>
                        </div>
                        <div class="rounded" style="padding: 15px; margin: 5px; background: #BFFFBF;">
                            <form action="/sysadmin/userdetail.jsp" method="post">
                                <input type="hidden" name="dpage" value="/sysadmin/userdetail.jsp">
                                <input type="hidden" name="action" value="deleteuser">
                                <input type="hidden" name="userid" value="<%=sysadminUserDetail.getUserid()%>">
                                <br/>
                                <input type="submit" class="formsubmitbutton" value="Delete User">
                                <%=Textbox.getHtml("activitypin", String.valueOf(sysadminUserDetail.getActivitypin()), 255, 25, "", "")%>
                                <br/>
                                <font class="tinyfont">You must type "yes, i want to do this" in the box to make this happen</font>
                            </form>
                        </div>
                    </td>
                 
                    <td valign="top" width="50%">

                        <div class="rounded" style="padding: 15px; margin: 5px; background: #BFFFBF;">
                            <form action="/sysadmin/userdetail.jsp" method="post">
                                <input type="hidden" name="dpage" value="/sysadmin/userdetail.jsp">
                                <input type="hidden" name="action" value="toggleisenabled">
                                <input type="hidden" name="userid" value="<%=sysadminUserDetail.getUserid()%>">
                                <%if (sysadminUserDetail.getIsenabled()){%>
                                    <font class="mediumfont">This Account is Currently Enabled.</font>
                                    <br/>
                                    <input type="submit" class="formsubmitbutton" value="Disable Account">
                                <%} else {%>
                                    <font class="mediumfont">This Account is Currently Disabled.</font>
                                    <br/>
                                    <input type="submit" class="formsubmitbutton" value="Enable Account">
                                <%}%>
                            </form>
                        </div>




                   
                    </td>
                </tr>
            </table>











<%@ include file="/template/footer.jsp" %>


