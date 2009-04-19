<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmluibeans.PublicIndex" %>
<%@ page import="com.celebtwit.helpers.RoundCorners" %>


<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "";
String navtab = "home";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>
<%
    PublicIndex publicIndex=(PublicIndex) Pagez.getBeanMgr().get("PublicIndex");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("completeexercise")) {
        try {

        }catch(Exception ex){
            logger.error("", ex);
        }
    }
%>

<%@ include file="/template/header.jsp" %>


<script language="javascript" type="text/javascript">
  var resultsPanel       = RUZEE.ShadedBorder.create({ corner:15, border:8, borderOpacity:0.1 });
</script>
<style type="text/css">
    /* <![CDATA[ */
    #resultsPanel { margin:20px auto; padding:14px 20px; background:#FFF; color:#222; }
    #resultsPanel .sb-border { background:#FFF; }
    /* ]]> */
</style>


<%--<div id="trans-border2" style="width:400px;">--%>
    <%--<p>A semi-transparent fat border. Thanks, Ryan, for the idea and the code!</p>--%>
<%--</div>--%>


<div id="resultsPanel">
<table cellpadding="10" cellspacing="0" border="0">
    <tr>
        <td valign="top" width="50%">
            Non-celebs Most Mentioned<br/><br/>
            <%
            if (true){
                List<Twit> twits = HibernateUtil.getSession().createCriteria(Twit.class)
                                               .add(Restrictions.eq("isceleb", false))
                                               .addOrder(Order.desc("totalmentions"))
                                               .setMaxResults(10)
                                               .setCacheable(true)
                                               .list();
                for (Iterator<Twit> iterator=twits.iterator(); iterator.hasNext();) {
                    Twit twit=iterator.next();
                    %><a href="/twitter/<%=twit.getTwitterusername()%>/">@<%=twit.getTwitterusername()%></a>:<%=twit.getTotalmentions()%> mentions<br/><%
                }
            }
            %>
        </td>
        <td valign="top">
            Celebs Most Mentioned<br/><br/>
            <%
            if (true){
                List<Twit> twits = HibernateUtil.getSession().createCriteria(Twit.class)
                                               .add(Restrictions.eq("isceleb", true))
                                               .addOrder(Order.desc("totalmentions"))
                                               .setMaxResults(10)
                                               .setCacheable(true)
                                               .list();
                for (Iterator<Twit> iterator=twits.iterator(); iterator.hasNext();) {
                    Twit twit=iterator.next();
                    %><a href="/twitter/<%=twit.getTwitterusername()%>/">@<%=twit.getRealname()%></a>:<%=twit.getTotalmentions()%> mentions<br/><%
                }
            }
            %>
        </td>
    </tr>
    <tr>
        <td valign="top" width="50%">
            Non-celebs Mentioned by Most Celebs<br/><br/>
            <%
            if (true){
                List<Twit> twits = HibernateUtil.getSession().createCriteria(Twit.class)
                                               .add(Restrictions.eq("isceleb", false))
                                               .addOrder(Order.desc("totalcelebswhomentioned"))
                                               .setMaxResults(10)
                                               .setCacheable(true)
                                               .list();
                for (Iterator<Twit> iterator=twits.iterator(); iterator.hasNext();) {
                    Twit twit=iterator.next();
                    %><a href="/twitter/<%=twit.getTwitterusername()%>/">@<%=twit.getTwitterusername()%></a>:<%=twit.getTotalmentions()%> different celebs<br/><%
                }
            }
            %>
        </td>
        <td valign="top">
            Celebs Mentioned by Most Other Celebs<br/><br/>
            <%
            if (true){
                List<Twit> twits = HibernateUtil.getSession().createCriteria(Twit.class)
                                               .add(Restrictions.eq("isceleb", true))
                                               .addOrder(Order.desc("totalcelebswhomentioned"))
                                               .setMaxResults(10)
                                               .setCacheable(true)
                                               .list();
                for (Iterator<Twit> iterator=twits.iterator(); iterator.hasNext();) {
                    Twit twit=iterator.next();
                    %><a href="/twitter/<%=twit.getTwitterusername()%>/">@<%=twit.getRealname()%></a>:<%=twit.getTotalmentions()%> different celebs<br/><%
                }
            }
            %>
        </td>
    </tr>
</table>
</div>






<%@ include file="/template/footer.jsp" %>

<script language="javascript" type="text/javascript">
    resultsPanel.render('resultsPanel');
</script>