<%@ page import="com.celebtwit.ebay.FindAuctions" %>
<%@ page import="com.celebtwit.htmluibeans.SysadminIndex" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.util.*" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "SysAdmin Home";
String metaKeywords = "";
String metaDescription = "";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
    SysadminIndex sysadminIndex=(SysadminIndex) Pagez.getBeanMgr().get("SysadminIndex");
%>
<%@ include file="/template/header.jsp" %>



    

    <div class="rounded" style="padding: 15px; margin: 8px; background: #e6e6e6;">
        <%=sysadminIndex.getServermemory()%>
    </div>




    <%

        FindAuctions findServlet = new FindAuctions();
        ArrayList<java.util.Map<String, String>> foundItems = (ArrayList)findServlet.find("shoes");

    %>
    foundItems.size()=<%=foundItems.size()%>

<%
        out.println("<table>");
		for (Iterator i = foundItems.iterator(); i.hasNext();){
			Map item = (HashMap) i.next();
			out.println("<tr>");
			out.println("<td>");
			//out.print("DEBUG: " + item.get("ItemURL"));
			out.println("<a href='" + item.get("ItemURL") + "'>");
			out.println("<img src='" + item.get("GalleryURL") + "'>");
			out.println("</a>");
			out.println("<td>");
			out.println("<a href='" + item.get("ItemURL") + "'>");
			out.println(item.get("Title"));
			out.println("</a>");
			out.println("<td>");
			out.println(item.get("ConvertedCurrentPrice"));
			out.println("<td>");
			out.println(item.get("ShippingServiceCost"));
			out.println("<td>");
			out.println(item.get("Total"));
			out.println("<td>");
			out.println(item.get("Currency"));
			out.println("<td>");
			out.println(item.get("TimeLeft"));
			out.println("<td>");
			out.println(item.get("EndTime"));
			out.println("</tr>");
        }
        out.println("</table>");
%>









<%@ include file="/template/footer.jsp" %>