<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmluibeans.SysadminUserList" %>
<%@ page import="com.celebtwit.dbgrid.GridCol" %>
<%@ page import="com.celebtwit.dbgrid.Grid" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.celebtwit.htmlui.*" %>
<%@ page import="com.celebtwit.util.Time" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.celebtwit.util.Num" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Celebs";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>

<%
    Twit twit = new Twit();
    twit.setIsceleb(true);
    twit.setLastprocessed(Time.xYearsAgoStart(Calendar.getInstance(), 25).getTime());
    twit.setProfile_image_url("");
    twit.setSince_id("1");
    twit.setTwitterusername("");
    if (request.getParameter("twitid")!=null && Num.isinteger(request.getParameter("twitid"))){
        twit = Twit.get(Integer.parseInt(request.getParameter("twitid")));
    }
%>

<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
        try {
            String twitterusername = Textbox.getValueFromRequest("twitterusername", "Twitter Username", true, DatatypeString.DATATYPEID);
            String realname = Textbox.getValueFromRequest("realname", "Real Name", true, DatatypeString.DATATYPEID);
            //Search for another one named twitterusername
            List<Twit> twits = HibernateUtil.getSession().createCriteria(Twit.class)
                                               .add(Restrictions.eq("twitterusername", twitterusername))
                                               .setCacheable(true)
                                               .list();
            for (Iterator<Twit> twitIterator=twits.iterator(); twitIterator.hasNext();) {
                Twit twit1=twitIterator.next();
                //If one already exists with this twitterusername, use it.
                twit = twit1;
            }
            //Set stuff in twit
            twit.setTwitterusername(twitterusername);
            twit.setRealname(realname);
            twit.setIsceleb(true);
            twit.save();
            HibernateUtil.getSession().createQuery("update Mention m set ismentionedaceleb=true where m.twitidmentioned='"+twit.getTwitid()+"'").executeUpdate();
            Pagez.getUserSession().setMessage("Celeb Saved!");
            Pagez.sendRedirect("/sysadmin/celebs.jsp");
            return;
        } catch (com.celebtwit.htmlui.ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("delete")) {
        try {
            HibernateUtil.getSession().createQuery("delete Mention m where m.twitidmentioned='"+twit.getTwitid()+"'").executeUpdate();
            HibernateUtil.getSession().createQuery("delete Mention m where m.twitidceleb='"+twit.getTwitid()+"'").executeUpdate();
            HibernateUtil.getSession().createQuery("delete Twitpost t where t.twitid='"+twit.getTwitid()+"'").executeUpdate();
            twit.delete();
            Pagez.getUserSession().setMessage("Celeb Deleted!");
            Pagez.sendRedirect("/sysadmin/celebs.jsp");
            return;
        } catch (Exception ex) {
            logger.error("", ex);
            Pagez.getUserSession().setMessage(ex.getMessage());
        }
    }
%>
<%@ include file="/template/header.jsp" %>


    <form action="/sysadmin/celebs.jsp" method="post">
        <input type="hidden" name="dpage" value="/sysadmin/celebs.jsp">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="twitid" value="<%=request.getParameter("twitid")%>">

        <table cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">Real Name</font>
                </td>
                <td valign="top">
                    <font class="formfieldnamefont">Twitter Username</font>
                </td>
                <td valign="top">

                </td>
            </tr>
            <tr>
                <td valign="top">
                    <%=Textbox.getHtml("realname", twit.getRealname(), 255, 25, "", "")%>
                    <br/>
                    <%if (twit.getTwitid()>0){%><a href="/sysadmin/celebs.jsp?twitid=<%=twit.getTwitid()%>&action=delete"><font class="tinyfont">delete</font></a><%}%>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("twitterusername", twit.getTwitterusername(), 255, 25, "", "")%>
                </td>
                <td valign="top">
                    <input type="submit" class="formsubmitbutton" value="Save Celeb">
                </td>
            </tr>
        </table>
    </form>

        <br/>
        <%
        List<Twit> twits = HibernateUtil.getSession().createCriteria(Twit.class)
                                           .add(Restrictions.eq("isceleb", true))
                                           .addOrder(Order.asc("realname"))
                                           .setMaxResults(1000)
                                           .setCacheable(true)
                                           .list();
        %>
        <%if (twits==null || twits.size()==0){%>
            <font class="normalfont">No celebs!</font>
        <%} else {%>
            <%
                ArrayList<GridCol> cols=new ArrayList<GridCol>();
                cols.add(new GridCol("Twitid", "<a href=\"/sysadmin/celebs.jsp?twitid=<$twitid$>\"><$twitid$></a>", false, "", "tinyfont"));
                cols.add(new GridCol("Real Name", "<$realname$>", false, "", "tinyfont"));
                cols.add(new GridCol("Twitter Username", "<$twitterusername$>", false, "", "tinyfont"));
            %>
            <%=Grid.render(twits, cols, 1000, "/sysadmin/celebs.jsp", "page")%>
        <%}%>






<%@ include file="/template/footer.jsp" %>



