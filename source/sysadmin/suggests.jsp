<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmluibeans.SysadminSystemProps" %>
<%@ page import="com.celebtwit.htmlui.*" %>
<%@ page import="com.celebtwit.dbgrid.Grid" %>
<%@ page import="com.celebtwit.dbgrid.GridCol" %>
<%@ page import="java.util.ArrayList" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Suggestions";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>


<%@ include file="/template/header.jsp" %>

    <%
    List suggests = HibernateUtil.getSession().createQuery("from Suggest order by suggestid desc").setCacheable(true).list();
    %>


    <%if (suggests==null || suggests.size()==0){%>
        <font class="normalfont">No Suggestions!</font>
    <%} else {%>
        <%
            ArrayList<GridCol> cols=new ArrayList<GridCol>();
            cols.add(new GridCol("id", "<$suggestid$>", false, "", "tinyfont"));
            cols.add(new GridCol("Pl", "<a href=\"/sysadmin/privatelabeledit.jsp?plid=<$plid$>\"><$plid$></a>", false, "", "tinyfont"));
            cols.add(new GridCol("name", "<$realname$>", false, "", "tinyfont"));
            cols.add(new GridCol("twitter", "<$twitterusername$>", false, "", "tinyfont"));
            cols.add(new GridCol("submitteremail", "<$submitteremail$>", false, "", "tinyfont"));
            cols.add(new GridCol("reason", "<$reason$>", false, "", "tinyfont"));
        %>
        <%=Grid.render(suggests, cols, 200, "/sysadmin/suggests.jsp", "page")%>
    <%}%>





<%@ include file="/template/footer.jsp" %>