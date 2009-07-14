<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmluibeans.PublicIndex" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.celebtwit.helpers.*" %>
<%@ page import="com.celebtwit.htmlui.*" %>
<%@ page import="com.celebtwit.dao.*" %>


<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "";
String metaKeywords = "";
String metaDescription = "";
String navtab = "home";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>

<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("suggest")) {
        try {
            Suggest suggest = new Suggest();
            suggest.setRealname(Textbox.getValueFromRequest("realname", "Name", false, DatatypeString.DATATYPEID));
            suggest.setTwitterusername(Textbox.getValueFromRequest("twitterusername", "Twitter Name", false, DatatypeString.DATATYPEID));
            suggest.setSubmitteremail(Textbox.getValueFromRequest("submitteremail", "Email", false, DatatypeString.DATATYPEID));
            suggest.setReason(Textarea.getValueFromRequest("reason", "Details/Why?", false));
            suggest.setPlid(Pagez.getUserSession().getPl().getPlid());
            suggest.save();
            Pagez.sendRedirect("/suggest-thanks.jsp");
            return;
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>

<%@ include file="/template/header.jsp" %>



    <form action="/suggest.jsp" method="post">
            <input type="hidden" name="dpage" value="/suggest.jsp">
            <input type="hidden" name="action" value="suggest">


                <font class="mediumfont" style="color: #333333">Suggest Somebody!</font>
                <br/>
                <font class="smallfont">It's hard to keep up with all the new peeps twittering these days.  We truly appreciate your pointers to new and exciting people on Twitter.</font><br/><br/>

                <table cellpadding="8" cellspacing="0" border="0">

                    <tr>
                        <td valign="top">
                            <font class="formfieldnamefont">Name</font>
                        </td>
                        <td valign="top">
                            <%=Textbox.getHtml("realname", "", 255, 35, "", "")%>
                            <br/>
                            <font class="tinyfont">The name of the person you're suggesting.</font>
                            <br/>
                            <font class="tinyfont">example: Lance Armstrong</font>
                        </td>
                    </tr>

                    <tr>
                        <td valign="top">
                            <font class="formfieldnamefont">Twitter Name</font>
                        </td>
                        <td valign="top">
                            <%=Textbox.getHtml("twitterusername", "", 255, 35, "", "")%>
                            <br/>
                            <font class="tinyfont">The twitter name... what comes after http://twitter.com/</font>
                            <br/>
                            <font class="tinyfont">example: lancearmstrong</font>
                        </td>
                    </tr>

                    <tr>
                        <td valign="top">
                            <font class="formfieldnamefont">Your Email</font>
                        </td>
                        <td valign="top">
                            <%=Textbox.getHtml("submitteremail", "", 255, 35, "", "")%>
                            <br/>
                            <font class="tinyfont">Your email address.  We won't spam you... just want it in case we have questions.</font>
                        </td>
                    </tr>

                    <tr>
                        <td valign="top">
                            <font class="formfieldnamefont">Details/Why?</font>
                        </td>
                        <td valign="top">
                            <%=Textarea.getHtml("reason", "", 5, 35, "", "")%>
                            <br/>
                            <font class="tinyfont">Why you're submitting this person.  Why you think this isn't a fake Twitter account.  Anything else relevant.</font>
                        </td>
                    </tr>




                    <tr>
                        <td valign="top">
                        </td>
                        <td valign="top">
                            <br/><br/>
                            <input type="submit" class="formsubmitbutton" value="Send Suggestion!">
                        </td>
                    </tr>

                </table>
        </form>




<%@ include file="/template/footer.jsp" %>
