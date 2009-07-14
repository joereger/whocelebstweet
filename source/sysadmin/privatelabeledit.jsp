<%@ page import="com.celebtwit.cache.providers.CacheFactory" %>
<%@ page import="com.celebtwit.dao.Pl" %>
<%@ page import="com.celebtwit.privatelabel.PlFinder" %>
<%@ page import="com.celebtwit.privatelabel.PlVerification" %>
<%@ page import="com.celebtwit.util.Num" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.celebtwit.util.Util" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmlui.*" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Private Label";
String metaKeywords = "";
String metaDescription = "";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
    Pl pl = new Pl();
    pl.setName("whoCelebsTweet.com");
    pl.setSistername("");
    pl.setCelebiscalled("celeb");
    pl.setCustomdomain1("www.whocelebstweet.com");
    pl.setCustomdomain2("whocelebstweet.com");
    pl.setCustomdomain3("");
    pl.setSisterdomain1("");
    pl.setSisterdomain2("");
    pl.setSisterdomain3("");
    pl.setTwitterusername("");
    pl.setTwitterpassword("");
    if (request.getParameter("plid")!=null && Num.isinteger(request.getParameter("plid"))){
        pl = Pl.get(Integer.parseInt(request.getParameter("plid")));
    }
%>
<%
    if (request.getParameter("action") != null) {
        try {
            if (request.getParameter("action").equals("save")){

                pl.setName(Textbox.getValueFromRequest("name", "Name", true, DatatypeString.DATATYPEID));
                pl.setSistername(Textbox.getValueFromRequest("sistername", "Sister Name", false, DatatypeString.DATATYPEID));
                pl.setCelebiscalled(Textbox.getValueFromRequest("celebiscalled", "Celeb is Called", true, DatatypeString.DATATYPEID));
                pl.setCustomdomain1(Textbox.getValueFromRequest("customdomain1", "Customdomain1", true, DatatypeString.DATATYPEID).toLowerCase());
                pl.setCustomdomain2(Textbox.getValueFromRequest("customdomain2", "Customdomain2", false, DatatypeString.DATATYPEID).toLowerCase());
                pl.setCustomdomain3(Textbox.getValueFromRequest("customdomain3", "Customdomain3", false, DatatypeString.DATATYPEID).toLowerCase());
                pl.setSisterdomain1(Textbox.getValueFromRequest("sisterdomain1", "Sisterdomain1", false, DatatypeString.DATATYPEID).toLowerCase());
                pl.setSisterdomain2(Textbox.getValueFromRequest("sisterdomain2", "Sisterdomain2", false, DatatypeString.DATATYPEID).toLowerCase());
                pl.setSisterdomain3(Textbox.getValueFromRequest("sisterdomain3", "Sisterdomain3", false, DatatypeString.DATATYPEID).toLowerCase());
                pl.setTwitterusername(Textbox.getValueFromRequest("twitterusername", "Twitter Username", false, DatatypeString.DATATYPEID));
                pl.setTwitterpassword(Textbox.getValueFromRequest("twitterpassword", "Twitter Password", false, DatatypeString.DATATYPEID));
                //Validate data
                if (PlVerification.isValid(pl)){
                    pl.save();
                    CacheFactory.getCacheProvider().flush(PlFinder.CACHEGROUP);
                    Pagez.getUserSession().setMessage("Saved!");
                    Pagez.sendRedirect("/sysadmin/privatelabels.jsp");
                    return;
                } else {
                    Pagez.getUserSession().setMessage("Pl Fails Validation!");
                }
            }
        } catch (Exception vex) {
            Pagez.getUserSession().setMessage(vex.toString());
        }
    }
%>

<%@ include file="/template/header.jsp" %>



        <form action="/sysadmin/privatelabeledit.jsp" method="post">
            <input type="hidden" name="dpage" value="/sysadmin/privatelabeledit.jsp">
            <input type="hidden" name="action" value="save" id="action">
            <input type="hidden" name="plid" value="<%=pl.getPlid()%>">

            <table cellpadding="3" cellspacing="0" border="0">
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Name</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("name", pl.getName(), 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Celeb is called</font>
                        <br/><font class="tinyfont">Ex("celeb" or "athlete" or "politician")... should be singular and lower case</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("celebiscalled", pl.getCelebiscalled(), 255, 35, "", "")%>
                    </td>
                </tr>

                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Customdomain1</font>
                        <br/><font class="tinyfont">Ex("www.mypldomain.com")</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("customdomain1", pl.getCustomdomain1(), 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Customdomain2</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("customdomain2", pl.getCustomdomain2(), 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Customdomain3</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("customdomain3", pl.getCustomdomain3(), 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Sister Name</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("sistername", pl.getSistername(), 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Sisterdomain1</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("sisterdomain1", pl.getSisterdomain1(), 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Sisterdomain2</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("sisterdomain2", pl.getSisterdomain2(), 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Sisterdomain3</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("sisterdomain3", pl.getSisterdomain3(), 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Twitter Username</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("twitterusername", pl.getTwitterusername(), 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Twitter Password</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("twitterpassword", pl.getTwitterpassword(), 255, 35, "", "")%>
                    </td>
                </tr>








             </table>
             <input type="submit" class="formsubmitbutton" value="Save"><br/>




        </form>



<%@ include file="/template/footer.jsp" %>



