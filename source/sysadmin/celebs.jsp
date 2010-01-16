<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmluibeans.SysadminUserList" %>
<%@ page import="com.celebtwit.dbgrid.GridCol" %>
<%@ page import="com.celebtwit.dbgrid.Grid" %>
<%@ page import="com.celebtwit.htmlui.*" %>
<%@ page import="com.celebtwit.util.Time" %>
<%@ page import="com.celebtwit.util.Num" %>
<%@ page import="com.celebtwit.dao.Twitpl" %>
<%@ page import="java.util.*" %>
<%@ page import="com.celebtwit.dao.Pl" %>
<%@ page import="com.celebtwit.scheduledjobs.GetTwitterPosts" %>
<%@ page import="com.celebtwit.cache.html.DbcacheexpirableCache" %>
<%@ page import="java.util.Date" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Celebs";
String metaKeywords = "";
String metaDescription = "";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>

<%
    Twit twit = new Twit();
    twit.setIsceleb(true);
    twit.setLastprocessed(Time.xYearsAgoStart(Calendar.getInstance(), 25).getTime());
    twit.setLaststatstweet(Time.xYearsAgoStart(Calendar.getInstance(), 25).getTime());
    twit.setProfile_image_url("");
    twit.setSince_id("1");
    twit.setTwitterusername("");
    twit.setTwitteruserid("");
    twit.setDescription("");
    twit.setWebsite_url("");
    twit.setStatuses_count(0);
    twit.setFollowers_count(0);
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
            //Make sure any already-mentions are marked as being about a celeb, now that this twit is one
            HibernateUtil.getSession().createQuery("update Mention m set ismentionedaceleb=true where m.twitidmentioned='"+twit.getTwitid()+"'").executeUpdate();
            //Delete any existing pl relationships
            for (Iterator<Twitpl> tplIt=twit.getTwitpls().iterator(); tplIt.hasNext();) {
                Twitpl twitpl=tplIt.next();
                tplIt.remove();
            }
            //Create Twitpls for those pls selected
            ArrayList<String> plidsSelected = Checkboxes.getValueFromRequest("plids", "Private Label", false);
            for (Iterator<String> plIt=plidsSelected.iterator(); plIt.hasNext();) {
                String plidStr=plIt.next();
                if (Num.isinteger(plidStr)){
                    Twitpl twitpl = new Twitpl();
                    twitpl.setTwitid(twit.getTwitid());
                    twitpl.setPlid(Integer.parseInt(plidStr));
                    twit.getTwitpls().add(twitpl);
                }
            }
            //Any edit to twit will force twitter api refresh
            if (twit.getTwitid()>0){
                HibernateUtil.getSession().createQuery("delete Mention m where m.twitidceleb='"+twit.getTwitid()+"'").executeUpdate();
                HibernateUtil.getSession().createQuery("delete Twitpost t where t.twitid='"+twit.getTwitid()+"'").executeUpdate();
            }
            //If it's being processed by GetTwitterPosts we need to note that it's been edited
            if (GetTwitterPosts.isProcessing(twit.getTwitid())){
                GetTwitterPosts.addToEditedDuringProcessing(twit.getTwitid());
            }
            //Set since_id to 1 so twitter api refreshes
            twit.setSince_id("1");
            //Refresh the twit to pick up the Twitpl changes
            twit.save();
            //Flush the right col list cache
            DbcacheexpirableCache.flush("PublicRightcolListCelebs.java");
            //Msg and then redir
            Pagez.getUserSession().setMessage("Celeb "+twit.getRealname()+" Saved!");
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
            //Make sure any already-mentions are *not* marked as being about a celeb, now that this twit is no longer one
            HibernateUtil.getSession().createQuery("update Mention m set ismentionedaceleb=false where m.twitidmentioned='"+twit.getTwitid()+"'").executeUpdate();
            //Delete mentions where this twit was acting as celeb... they don't count any more
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
            <tr>
                <td valign="top" colspan="3">
                    <%
                        ArrayList<String> values = new ArrayList<String>();
                        for (Iterator<Twitpl> plIt=twit.getTwitpls().iterator(); plIt.hasNext();) {
                            Twitpl twitpl=plIt.next();
                            values.add(String.valueOf(twitpl.getPlid()));
                        }
                    %>
                    <%
                        TreeMap<String, String> options = new TreeMap<String, String>();
                        List<Pl> pls = HibernateUtil.getSession().createCriteria(Pl.class)
                                .addOrder(Order.asc("name"))
                                .setCacheable(true)
                                .list();
                        for (Iterator<Pl> plIterator=pls.iterator(); plIterator.hasNext();) {
                            Pl pl=plIterator.next();
                            options.put(String.valueOf(pl.getPlid()), pl.getName());
                        }
                    %>
                    <%=Checkboxes.getHtml("plids", values, options, "", "")%>    
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



