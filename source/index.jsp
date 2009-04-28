<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmluibeans.PublicIndex" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.celebtwit.helpers.*" %>
<%@ page import="com.celebtwit.dao.Twitpost" %>


<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "";
String navtab = "home";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>
<%@ include file="/template/header.jsp" %>

<%
StartDateEndDate sted = new StartDateEndDate(request.getParameter("time"));
%>

<%if (!Pagez.getUserSession().isSisterPl()){%>
    <div class="roundedBox" style="width:630px;">
        <div style="float:right">
            <%String qs = "";%>
            <font class="tinyfont">
                <%String time = request.getParameter("time");%>
                <%if (time==null || time.equals("") || time.equals("null")){time="alltime";}%>
                <%String addToStyle = "";%>
                <%String boldStyle = "font-weight:bold; background:#ffffff;";%>
                <%if (time.equals("alltime")){addToStyle=boldStyle;}else{addToStyle="";}%>
                <a href="/index.jsp?time=alltime<%=qs%>" style="<%=addToStyle%>">all time</a> |
                <%if (time.equals("thismonth")){addToStyle=boldStyle;}else{addToStyle="";}%>
                <a href="/index.jsp?time=thismonth<%=qs%>" style="<%=addToStyle%>">this month</a> |
                <%if (time.equals("last31days")){addToStyle=boldStyle;}else{addToStyle="";}%>
                <a href="/index.jsp?time=last31days<%=qs%>" style="<%=addToStyle%>">last 31 days</a> |
                <%if (time.equals("thisweek")){addToStyle=boldStyle;}else{addToStyle="";}%>
                <a href="/index.jsp?time=thisweek<%=qs%>" style="<%=addToStyle%>">this week</a> |
                <%if (time.equals("last7days")){addToStyle=boldStyle;}else{addToStyle="";}%>
                <a href="/index.jsp?time=last7days<%=qs%>" style="<%=addToStyle%>">last 7 days</a> |
                <%if (time.equals("yesterday")){addToStyle=boldStyle;}else{addToStyle="";}%>
                <a href="/index.jsp?time=yesterday<%=qs%>" style="<%=addToStyle%>">yesterday</a> |
                <%if (time.equals("today")){addToStyle=boldStyle;}else{addToStyle="";}%>
                <a href="/index.jsp?time=today<%=qs%>" style="<%=addToStyle%>">today</a>
            </font>
        </div><br/>
        <table cellpadding="0" cellspacing="10" border="0">
            <tr>
                <td valign="top" width="50%">
                    <img src="/images/infoicon-celebtonon.png" alt="" width="110" height="64" align="left">
                    <font class="mediumfont" style="color:#666666;">non-<%=Pagez.getUserSession().getPl().getCelebiscalled()%>s tweeted the most by <%=Pagez.getUserSession().getPl().getCelebiscalled()%>s</font>
                </td>
                <td valign="top">
                    <img src="/images/infoicon-celebtoceleb.png" alt="" width="110" height="64" align="left">
                    <font class="mediumfont" style="color:#666666;"><%=Pagez.getUserSession().getPl().getCelebiscalled()%>s tweeted the most by other <%=Pagez.getUserSession().getPl().getCelebiscalled()%>s</font>
                </td>
            </tr>
            <tr>
                <td valign="top" width="50%">
                    <%
                    if (true){
                        ArrayList<TwitMention> twitMentions = GetTwitsByMentioned.get(sted.getStartDate(), sted.getEndDate(), 0, false, 15, Pagez.getUserSession().getPl().getPlid());
                        for (Iterator<TwitMention> iterator=twitMentions.iterator(); iterator.hasNext();) {
                            TwitMention twitMention = iterator.next();
                            %><font class="normalfont" style="font-weight:bold;"><a href="/twitter/<%=twitMention.getTwit().getTwitterusername()%>/">@<%=twitMention.getTwit().getTwitterusername()%></a></font><font class="tinyfont"> in <%=twitMention.getMentions()%> <%=Pagez.getUserSession().getPl().getCelebiscalled()%> tweets<br/></font><%
                        }
                    }
                    %>
                </td>
                <td valign="top">
                    <%
                    if (true){
                        ArrayList<TwitMention> twitMentions = GetTwitsByMentioned.get(sted.getStartDate(), sted.getEndDate(), 0, true, 15, Pagez.getUserSession().getPl().getPlid());
                        for (Iterator<TwitMention> iterator=twitMentions.iterator(); iterator.hasNext();) {
                            TwitMention twitMention = iterator.next();
                            %><font class="normalfont" style="font-weight:bold;"><a href="/twitter/<%=twitMention.getTwit().getTwitterusername()%>/">@<%=twitMention.getTwit().getRealname()%></a></font><font class="tinyfont"> in <%=twitMention.getMentions()%> <%=Pagez.getUserSession().getPl().getCelebiscalled()%> tweets</font><br/><%
                        }
                    }
                    %>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <img src="/images/clear.gif" alt="" width="1" height="10" align="left">
                </td>
            </tr>
            <tr>
                <td valign="top" width="50%">
                    <img src="/images/infoicon-manycelebstonon.png" alt="" width="110" height="64" align="left">
                    <font class="mediumfont" style="color:#666666;">non-<%=Pagez.getUserSession().getPl().getCelebiscalled()%>s tweeted by the most different <%=Pagez.getUserSession().getPl().getCelebiscalled()%>s</font>
                </td>
                <td valign="top">
                    <img src="/images/infoicon-manycelebstoceleb.png" alt="" width="110" height="64" align="left">
                    <font class="mediumfont" style="color:#666666;"><%=Pagez.getUserSession().getPl().getCelebiscalled()%>s tweeted by the most different <%=Pagez.getUserSession().getPl().getCelebiscalled()%>s</font>
                </td>
            </tr>
            <tr>
                <td valign="top" width="50%">
                    <%
                    if (true){
                        ArrayList<TwitUniqueCelebsMentioning> twitUniques = GetTwitsByUniqueCelebsMentioning.get(sted.getStartDate(), sted.getEndDate(), false, 15, Pagez.getUserSession().getPl().getPlid());
                        for (Iterator<TwitUniqueCelebsMentioning> iterator=twitUniques.iterator(); iterator.hasNext();) {
                            TwitUniqueCelebsMentioning twitUnique = iterator.next();
                            %><font class="normalfont" style="font-weight:bold;"><a href="/twitter/<%=twitUnique.getTwit().getTwitterusername()%>/">@<%=twitUnique.getTwit().getTwitterusername()%></a></font><font class="tinyfont"> tweeted by <%=twitUnique.getUniquecelebsmentioning()%> <%=Pagez.getUserSession().getPl().getCelebiscalled()%>s</font><br/><%
                        }
                    }
                    %>
                </td>
                <td valign="top">
                    <%
                    if (true){
                        ArrayList<TwitUniqueCelebsMentioning> twitUniques = GetTwitsByUniqueCelebsMentioning.get(sted.getStartDate(), sted.getEndDate(), true, 15, Pagez.getUserSession().getPl().getPlid());
                        for (Iterator<TwitUniqueCelebsMentioning> iterator=twitUniques.iterator(); iterator.hasNext();) {
                            TwitUniqueCelebsMentioning twitUnique = iterator.next();
                            %><font class="normalfont" style="font-weight:bold;"><a href="/twitter/<%=twitUnique.getTwit().getTwitterusername()%>/">@<%=twitUnique.getTwit().getRealname()%></a></font><font class="tinyfont"> tweeted by <%=twitUnique.getUniquecelebsmentioning()%> <%=Pagez.getUserSession().getPl().getCelebiscalled()%>s</font><br/><%
                        }
                    }
                    %>
                </td>
            </tr>
        </table>
    </div>
<%}%>

<table cellpadding="3" cellspacing="0" border="0" width="100%">
    <tr>
        <td valign="top">

            <div style="width:420px; padding: 0px; overflow:hidden;">
                    <%if (!Pagez.getUserSession().isSisterPl()){%>
                        <font class="largefont">recent <%=Pagez.getUserSession().getPl().getCelebiscalled()%> tweets</font>
                        <br/><br/>
                    <%}%>
                    <%
                    List<Twitpost> twitposts = HibernateUtil.getSession().createCriteria(Twitpost.class)
                            .addOrder(Order.desc("created_at"))
                            .createCriteria("twit")
                            .createCriteria("twitpls")
                            .add(Restrictions.eq("plid", Pagez.getUserSession().getPl().getPlid()))
                            .setMaxResults(25)
                            .setCacheable(true)
                            .list();
                        for (Iterator<Twitpost> tpIt=twitposts.iterator(); tpIt.hasNext();) {
                            Twitpost twitpost=tpIt.next();
                            %><%=TwitpostAsHtml.get(twitpost, 400)%><%
                        }
                    %>
            </div>

        </td>
        <td valign="top" width="160">
            <%if (!Pagez.getUserSession().isSisterPl()){%>
                <img src="/images/clear.gif" alt="" width="1" height="70"><br/>
            <%}%>
            <script type="text/javascript"><!--
            google_ad_client = "pub-9883617370563969";
            /* 160x600, Skyscraper */
            google_ad_slot = "2576530148";
            google_ad_width = 160;
            google_ad_height = 600;
            //-->
            </script>
            <script type="text/javascript"
            src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
            </script>
        </td>
    </tr>
</table>






<%@ include file="/template/footer.jsp" %>
