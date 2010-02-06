<%@ page import="com.celebtwit.htmluibeans.Registration" %>
<%@ page import="com.celebtwit.pingfm.PingfmUpdate" %>
<%@ page import="com.celebtwit.systemprops.BaseUrl" %>
<%@ page import="com.celebtwit.systemprops.SystemProperty" %>
<%@ page import="com.celebtwit.util.RandomString" %>
<%@ page import="com.celebtwit.util.Time" %>
<%@ page import="org.apache.log4j.Logger" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Sign Up for an Account";
String metaKeywords = "";
String metaDescription = "";
String navtab = "youraccount";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>
<%
Registration registration = (Registration)Pagez.getBeanMgr().get("Registration");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("register")) {
        try {
            registration.setEmail(Textbox.getValueFromRequest("email", "Email", true, DatatypeString.DATATYPEID));
            registration.setFirstname(Textbox.getValueFromRequest("firstname", "First Name", true, DatatypeString.DATATYPEID));
            registration.setNickname(Textbox.getValueFromRequest("nickname", "Nickname", true, DatatypeString.DATATYPEID));
            registration.setJ_captcha_response(Textbox.getValueFromRequest("j_captcha_response", "Squiggly Letters", true, DatatypeString.DATATYPEID));
            registration.setCaptchaId(request.getParameter("captchaId"));
            registration.setLastname(Textbox.getValueFromRequest("lastname", "Last Name", true, DatatypeString.DATATYPEID));
            registration.setPassword(TextboxSecret.getValueFromRequest("password", "Password", true, DatatypeString.DATATYPEID));
            registration.setPasswordverify(TextboxSecret.getValueFromRequest("passwordverify", "Password Verify", true, DatatypeString.DATATYPEID));
            registration.registerAction();
            //Redir if https is on
            if (SystemProperty.getProp(SystemProperty.PROP_ISSSLON).equals("1")) {
                try {
                    logger.debug("redirecting to https - " + BaseUrl.get(true) + "/account/index.jsp?firsttime=1");
                    Pagez.sendRedirect(BaseUrl.get(true) + "/account/index.jsp?firsttime=1");
                    return;
                } catch (Exception ex) {
                    logger.error("", ex);
                    //@todo setIsfirsttimelogin(true) on AccountIndex bean
                    Pagez.sendRedirect("/account/index.jsp?firsttime=1");
                    return;
                }
            } else {
                //@todo setIsfirsttimelogin(true) on AccountIndex bean
                Pagez.sendRedirect("/account/index.jsp?firsttime=1");
                return;
            }
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%
    String captchaId=RandomString.randomAlphanumeric(10);
%>
<%@ include file="/template/header.jsp" %>


<%
//    try{
//        if (Pagez.getUserSession().getPl().getPingfmapikey()!=null && !Pagez.getUserSession().getPl().getPingfmapikey().equals("")){
//            logger.debug("sending to ping.fm");
//            PingfmUpdate pfm = new PingfmUpdate(Pagez.getUserSession().getPl().getPingfmapikey().trim(), "Hey, testing @"+ Time.nowInGmtString(), Pagez.getUserSession().getPl().getPlid());
//            pfm.update();
//        }
//    } catch (Exception ex){
//        logger.error("", ex);
//    }
%>

        <div style="width: 250px; float: right; padding-left: 20px;">
            <div class="rounded" style="padding: 15px; margin: 5px; background: #e6e6e6;">
                <font class="mediumfont" style="color: #333333">Existing Users</font><br/>
                <font class="smallfont">If you've already got an account you can simply log in.</font><br/>
                <div class="rounded" style="padding: 15px; margin: 5px; background: #ffffff;">
                    <%=GreenRoundedButton.get("<a href=\"/login.jsp\"><font class=\"subnavfont\" style=\"color: #ffffff; font-weight: bold;\">Log In</font></a>")%>
                </div>
            </div>
        </div>
        <form action="/registration.jsp" method="post">
            <input type="hidden" name="dpage" value="/registration.jsp">
            <input type="hidden" name="action" value="register">
            <input type="hidden" name="captchaId" value="<%=captchaId%>">

                <font class="mediumfont" style="color: #333333">Get started!</font>
                <br/>
                <font class="smallfont">Sign Up is free.  On this page we collect some basic information.  Your account is completely free to set up and explore.</font><br/><br/>

                <table cellpadding="0" cellspacing="0" border="0">

                    <tr>
                        <td valign="top">
                            <font class="formfieldnamefont">First Name</font>
                        </td>
                        <td valign="top">
                            <%=Textbox.getHtml("firstname", registration.getFirstname(), 255, 35, "", "")%>
                        </td>
                    </tr>

                    <tr>
                        <td valign="top">
                            <font class="formfieldnamefont">Last Name</font>
                        </td>
                        <td valign="top">
                            <%=Textbox.getHtml("lastname", registration.getLastname(), 255, 35, "", "")%>
                        </td>
                    </tr>

                    <tr>
                        <td valign="top">
                            <font class="formfieldnamefont">Nickname</font>
                        </td>
                        <td valign="top">
                            <%=Textbox.getHtml("nickname", registration.getNickname(), 255, 35, "", "")%>
                        </td>
                    </tr>

                    <tr>
                        <td valign="top">
                            <font class="formfieldnamefont">Email</font>
                        </td>
                        <td valign="top">
                            <%=Textbox.getHtml("email", registration.getEmail(), 255, 35, "", "")%>
                        </td>
                    </tr>

                    <tr>
                        <td valign="top">
                            <font class="formfieldnamefont">Password</font>
                        </td>
                        <td valign="top">
                            <%=TextboxSecret.getHtml("password", registration.getPassword(), 255, 35, "", "")%>
                        </td>
                    </tr>

                    <tr>
                        <td valign="top">
                            <font class="formfieldnamefont">Password Verify</font>
                        </td>
                        <td valign="top">
                            <%=TextboxSecret.getHtml("passwordverify", registration.getPasswordverify(), 255, 35, "", "")%>
                        </td>
                    </tr>


                    <tr>
                        <td valign="top">
                            <font class="formfieldnamefont">Prove You're a Human</font>
                        </td>
                        <td valign="top">
                            <div style="border: 1px solid #ccc; padding: 3px;">
                            <%=Textbox.getHtml("j_captcha_response", registration.getJ_captcha_response(), 255, 35, "", "")%>
                            <br/>
                            <font class="tinyfont">(type the squiggly letters that appear below)</font>
                            <br/>
                            <table cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                    <td><img src="/images/clear.gif" alt="" width="1" height="100"></img></td>
                                    <td style="background: url(/images/loading-captcha.gif);">
                                        <img src="/images/clear.gif" alt="" width="200" height="1"></img><br/>
                                        <img src="/jcaptcha?captchaId=<%=captchaId%>" width="200" height="100"/>
                                    </td>
                                </tr>
                            </table>
                            </div>
                        </td>
                    </tr>



                    <tr>
                        <td valign="top">
                        </td>
                        <td valign="top">
                            <br/><br/>
                            <input type="submit" class="formsubmitbutton" value="Sign Up">
                        </td>
                    </tr>

                </table>
        </form>


<%@ include file="/template/footer.jsp" %>