<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.celebtwit.htmluibeans.SysadminManuallyRunScheduledTask" %>
<%@ page import="com.celebtwit.htmlui.*" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Manually Run Scheduled Task";
String metaKeywords = "";
String metaDescription = "";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
SysadminManuallyRunScheduledTask sysadminManuallyRunScheduledTask = (SysadminManuallyRunScheduledTask)Pagez.getBeanMgr().get("SysadminManuallyRunScheduledTask");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("run")) {
        try {
            if (request.getParameter("task")!=null){
                if (request.getParameter("task").equals("DeleteOldPersistentlogins")){
                    sysadminManuallyRunScheduledTask.runDeleteOldPersistentlogins();
                } else if (request.getParameter("task").equals("SystemStats")){
                    sysadminManuallyRunScheduledTask.runSystemStats();
                } else if (request.getParameter("task").equals("PagePerformanceRecordAndFlush")){
                    sysadminManuallyRunScheduledTask.runPagePerformanceRecordAndFlush();
                } else if (request.getParameter("task").equals("GetTwitterPosts")){
                    sysadminManuallyRunScheduledTask.runGetTwitterPosts();
                } else if (request.getParameter("task").equals("MakeFriends")){
                    sysadminManuallyRunScheduledTask.runMakeFriends();
                } else if (request.getParameter("task").equals("StatsTweet")){
                    sysadminManuallyRunScheduledTask.runStatsTweet();
                } else {
                    throw new ValidationException("task not found.");    
                }
            } else {
                throw new ValidationException("task not defined.");
            }
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%@ include file="/template/header.jsp" %>




<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=DeleteOldPersistentlogins"><font class="mediumfont">DeleteOldPersistentlogins</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=SystemStats"><font class="mediumfont">SystemStats</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=PagePerformanceRecordAndFlush"><font class="mediumfont">PagePerformanceRecordAndFlush</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=MakeFriends"><font class="mediumfont">MakeFriends</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=StatsTweet"><font class="mediumfont">StatsTweet</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=GetTwitterPosts"><font class="mediumfont">GetTwitterPosts</font></a>


<%@ include file="/template/footer.jsp" %>