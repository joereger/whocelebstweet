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
<%@ page import="com.celebtwit.helpers.*" %>
<%@ page import="com.celebtwit.ads.*" %>
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
    pl.setListownerscreenname1("");
    pl.setListid1("");
    pl.setListownerscreenname2("");
    pl.setListid2("");
    pl.setListownerscreenname3("");
    pl.setListid3("");
    pl.setCommasepadnetworks("");
    pl.setIsdisplayotherplson(true);
    pl.setPingfmapikey("");
    pl.setTwitteraccesstoken("");
    pl.setTwitteraccesstokensecret("");
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
                pl.setListownerscreenname1(Textbox.getValueFromRequest("listownerscreenname1", "List Owner ScreenName 1", false, DatatypeString.DATATYPEID));
                pl.setListid1(Textbox.getValueFromRequest("listid1", "ListID1", false, DatatypeInteger.DATATYPEID));
                pl.setListownerscreenname2(Textbox.getValueFromRequest("listownerscreenname2", "List Owner ScreenName 2", false, DatatypeString.DATATYPEID));
                pl.setListid2(Textbox.getValueFromRequest("listid2", "ListID2", false, DatatypeInteger.DATATYPEID));
                pl.setListownerscreenname3(Textbox.getValueFromRequest("listownerscreenname3", "List Owner ScreenName 3", false, DatatypeString.DATATYPEID));
                pl.setListid3(Textbox.getValueFromRequest("listid3", "ListID3", false, DatatypeInteger.DATATYPEID));
                pl.setCommasepadnetworks(Textbox.getValueFromRequest("commasepadnetworks", "Ad Networks", false, DatatypeString.DATATYPEID).toLowerCase());
                pl.setIsdisplayotherplson(CheckboxBoolean.getValueFromRequest("isdisplayotherplson"));
                pl.setPingfmapikey(Textbox.getValueFromRequest("pingfmapikey", "Ping.fm API Key", false, DatatypeString.DATATYPEID));
                pl.setTwitteraccesstoken(Textbox.getValueFromRequest("twitteraccesstoken", "Twitter Access Token", false, DatatypeString.DATATYPEID));
                pl.setTwitteraccesstokensecret(Textbox.getValueFromRequest("twitteraccesstokensecret", "Twitter Access Token Secret", false, DatatypeString.DATATYPEID));
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

<%
    if (request.getParameter("action") != null) {
        try {
            if (request.getParameter("action").equals("exporttolist")){
                String twitterusername = Textbox.getValueFromRequest("twitterusername", "Twitter Username", true, DatatypeString.DATATYPEID);
                String twitterpassword = Textbox.getValueFromRequest("twitterpassword", "Twitter Password", true, DatatypeString.DATATYPEID);
                int listid = Textbox.getIntFromRequest("listid", "ListID", true, DatatypeInteger.DATATYPEID);
                SendLocalListToTwitterList.send(pl, twitterusername, twitterpassword, String.valueOf(listid));
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
                        <font class="formfieldnamefont">Ad Networks</font>
                        <br/><font class="tinyfont">Comma separated list of ad networks to display on this pl.  Blank is the default ad network.<br/><%=AdNetworkFactory.getAllAsString()%><br/>Set to NONE to use no ads</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("commasepadnetworks", pl.getCommasepadnetworks(), 255, 35, "", "")%>
                    </td>
                </tr>

                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Ping.fm API Key</font>
                        <br/><font class="tinyfont">Will send twitter updates through ping.fm</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("pingfmapikey", pl.getPingfmapikey(), 255, 35, "", "")%>
                    </td>
                </tr>

                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Display Other Pls?</font>
                        <br/><font class="tinyfont">Display links to other pls</font>
                    </td>
                    <td valign="top">
                        <%=CheckboxBoolean.getHtml("isdisplayotherplson", pl.getIsdisplayotherplson(), "", "")%>
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
                        <font class="formfieldnamefont">Twitter OAuth</font>
                    </td>
                    <td valign="top">
                        <a href="/twitterredirector?plid=<%=pl.getPlid()%>">Authorize Twitter</a>
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
                        <font class="formfieldnamefont">Twitter Access Token</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("twitteraccesstoken", pl.getTwitteraccesstoken(), 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Twitter Access Token Secret</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("twitteraccesstokensecret", pl.getTwitteraccesstokensecret(), 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Twitter List Owner Screen Name 1</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("listownerscreenname1", pl.getListownerscreenname1(), 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Twitter ListID 1</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("listid1", pl.getListid1(), 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Twitter List Owner Screen Name 2</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("listownerscreenname2", pl.getListownerscreenname2(), 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Twitter ListID 2</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("listid2", pl.getListid2(), 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Twitter List Owner Screen Name 3</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("listownerscreenname3", pl.getListownerscreenname3(), 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Twitter ListID 3</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("listid3", pl.getListid3(), 255, 35, "", "")%>
                    </td>
                </tr>



             </table>
             <input type="submit" class="formsubmitbutton" value="Save"><br/>




        </form>


        <br/><br/>
        <form action="/sysadmin/privatelabeledit.jsp" method="post">
            <input type="hidden" name="dpage" value="/sysadmin/privatelabeledit.jsp">
            <input type="hidden" name="action" value="exporttolist" id="action">
            <input type="hidden" name="plid" value="<%=pl.getPlid()%>">
            <table cellpadding="3" cellspacing="0" border="0">
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Twitterusername</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("twitterusername", "", 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Twitterpassword</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("twitterpassword", "", 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top" style="text-align:right;">
                        <font class="formfieldnamefont">Twitter ListID (Integer)</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("listid", "", 255, 35, "", "")%>
                    </td>
                </tr>
            </table>
             <input type="submit" class="formsubmitbutton" value="Export to List"><br/>




        </form>


<%@ include file="/template/footer.jsp" %>



