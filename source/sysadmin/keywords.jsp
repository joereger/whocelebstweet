<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmluibeans.SysadminUserList" %>
<%@ page import="com.celebtwit.dbgrid.GridCol" %>
<%@ page import="com.celebtwit.dbgrid.Grid" %>
<%@ page import="com.celebtwit.htmlui.*" %>
<%@ page import="com.celebtwit.util.Time" %>
<%@ page import="com.celebtwit.util.Num" %>
<%@ page import="com.celebtwit.helpers.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.celebtwit.scheduledjobs.GetTwitterPosts" %>
<%@ page import="com.celebtwit.cache.html.DbcacheexpirableCache" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.celebtwit.dao.*" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Keywords";
String metaKeywords = "";
String metaDescription = "";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>

<%
    Keyword keyword = new Keyword();
    keyword.setIslocation(false);
    keyword.setKeyword("");
    keyword.setSincetwitpostid(0);
    if (request.getParameter("keywordid")!=null && Num.isinteger(request.getParameter("keywordid"))){
        keyword = Keyword.get(Integer.parseInt(request.getParameter("keywordid")));
    }
%>

<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
        try {
            String keywordStr = Textbox.getValueFromRequest("keyword", "Keyword", true, DatatypeString.DATATYPEID);
            Boolean islocation = CheckboxBoolean.getValueFromRequest("islocation");
            //Search for another one
            List<Keyword> keywords = HibernateUtil.getSession().createCriteria(Keyword.class)
                                               .add(Restrictions.eq("keyword", keywordStr.toLowerCase()))
                                               .setCacheable(true)
                                               .list();
            for (Iterator<Keyword> kwIt=keywords.iterator(); kwIt.hasNext();) {
                Keyword keyword1=kwIt.next();
                //If one already exists use it.
                keyword = keyword1;
            }
            //If the keyword existed previously and the keyword text has been edited
            boolean wasEdited = false;
            if (keyword.getKeywordid()>0 && !keywordStr.toLowerCase().equals(keyword.getKeyword().toLowerCase())){
                wasEdited = true;
            }
            //Set stuff in keyword
            keyword.setKeyword(keywordStr.toLowerCase());
            keyword.setIslocation(islocation);
            if (wasEdited){
                keyword.setSincetwitpostid(0);
                HibernateUtil.getSession().createQuery("delete Keywordmention m where m.keywordid='"+ keyword.getKeywordid()+"'").executeUpdate();
            }
            keyword.save();
            //Msg and then redir
            Pagez.getUserSession().setMessage("Keyword "+ keyword.getKeyword()+" Saved!");
            Pagez.sendRedirect("/sysadmin/keywords.jsp");
            return;
        } catch (com.celebtwit.htmlui.ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        } 
    }
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("delete")) {
        try {
            //Make sure any already-mentions are *not* marked as being about a celeb, now that this keyword is no longer one
            HibernateUtil.getSession().createQuery("delete Keywordmention m where m.keywordid='"+ keyword.getKeywordid()+"'").executeUpdate();
            keyword.delete();
            Pagez.getUserSession().setMessage("Keyword Deleted!");
            Pagez.sendRedirect("/sysadmin/keywords.jsp");
            return;
        } catch (Exception ex) {
            logger.error("", ex);
            Pagez.getUserSession().setMessage(ex.getMessage());
        }
    }
%>
<%@ include file="/template/header.jsp" %>


    <form action="/sysadmin/keywords.jsp" method="post">
        <input type="hidden" name="dpage" value="/sysadmin/keywords.jsp">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="keywordid" value="<%=request.getParameter("keywordid")%>">

        <table cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">Keyword</font>
                </td>
                <td valign="top">
                    <font class="formfieldnamefont">IsLocation?</font>
                </td>
                <td valign="top">

                </td>
            </tr>
            <tr>
                <td valign="top">
                    <%=Textbox.getHtml("keyword", keyword.getKeyword(), 255, 25, "", "")%>
                    <br/>
                    <%if (keyword.getKeywordid()>0){%><a href="/sysadmin/keywords.jsp?keywordid=<%=keyword.getKeywordid()%>&action=delete"><font class="tinyfont">delete</font></a><%}%>
                </td>
                <td valign="top">
                    <%=CheckboxBoolean.getHtml("islocation", keyword.getIslocation(), "", "")%>
                </td>
                <td valign="top">
                    <input type="submit" class="formsubmitbutton" value="Save Keyword">
                </td>
            </tr>

        </table>
    </form>

        <br/>
        <%
        List<Keyword> keywords = HibernateUtil.getSession().createCriteria(Keyword.class)
                                           .addOrder(Order.asc("keyword"))
                                           .setMaxResults(5000)
                                           .setCacheable(true)
                                           .list();
        %>
        <%if (keywords==null || keywords.size()==0){%>
            <font class="normalfont">No keywords!</font>
        <%} else {%>
            <%
                ArrayList<GridCol> cols=new ArrayList<GridCol>();
                cols.add(new GridCol("Keywordid", "<a href=\"/sysadmin/keywords.jsp?keywordid=<$keywordid$>\"><$keywordid$></a>", false, "", "tinyfont"));
                cols.add(new GridCol("Keyword", "<$keyword$>", false, "", "tinyfont"));
                cols.add(new GridCol("IsLocation?", "<$islocation$>", false, "", "tinyfont"));
                cols.add(new GridCol("sincetwitpostid", "<$sincetwitpostid$>", false, "", "tinyfont"));
            %>
            <%=Grid.render(keywords, cols, 5000, "/sysadmin/keywords.jsp", "page")%>
        <%}%>






<%@ include file="/template/footer.jsp" %>



