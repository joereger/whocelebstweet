<%@ page import="au.com.bytecode.opencsv.CSVReader" %>
<%@ page import="com.celebtwit.keywords.UploadInputForm" %>
<%@ page import="com.celebtwit.keywords.UploadedKeyword" %>
<%@ page import="com.celebtwit.util.Num" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="org.apache.commons.fileupload.FileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.io.CharArrayReader" %>
<%@ page import="java.util.ArrayList" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Upload Keywords";
String metaKeywords = "";
String metaDescription = "";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>

<%
    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    logger.error("isMultipart="+isMultipart);
    String formCenter = "";
    ArrayList<UploadedKeyword> bulkusers = new ArrayList<UploadedKeyword>();
    if (isMultipart){
        try {
            int errorcount = 0;
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List /* FileItem */ items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    logger.error("isFormField "+item.getName()+" = "+item.toString());
                } else {
                    logger.error("!isFormField "+item.getName()+" = "+item.toString());
                    byte[] bytes = item.get();
                    char[] chars = (new String(bytes)).toCharArray();
                    logger.error("bytes.length="+bytes.length);
                    logger.error("chars length="+chars.length);
                    //Now we have the uploaded bytes
                    CSVReader reader = new CSVReader(new CharArrayReader(chars));
                    String[] nextLine;
                    int counter = 0;
                    while ((nextLine = reader.readNext()) != null) {
                        counter++;
                        System.out.println("Keyword: [" + nextLine[0] + "] Islocation: [" + nextLine[1] + "]");
                        if (nextLine[0]!=null && !nextLine[0].equals("KEYWORD")){
                            UploadedKeyword bulkuser = new UploadedKeyword(nextLine[0], nextLine[1]);
                            if (!bulkuser.getIsvalid()){errorcount++;}
                            bulkusers.add(bulkuser);
                        }
                    }
                }
            }
            formCenter = UploadInputForm.getForm(bulkusers);
            Pagez.getUserSession().setMessage("File uploaded. There are "+errorcount+" errors to correct below. You can make changes... we'll validate again before creating any keywords.");
        } catch (Exception ex) {
            logger.error("", ex);
        }
    } else {
        //Not multipart
        if (request.getParameter("action")!=null && request.getParameter("action").equals("save")) {
            try {
                bulkusers = new ArrayList<UploadedKeyword>();
                int numberofbulkusers = 0;
                if (Num.isinteger(request.getParameter("numberofbulkusers"))){
                    numberofbulkusers = Integer.parseInt(request.getParameter("numberofbulkusers"));
                }
                int errorcount = 0;
                for(int i=1; i<=numberofbulkusers; i++){
                    UploadedKeyword bulkuser = new UploadedKeyword(request.getParameter("keyword"+i), request.getParameter("islocation"+i));
                    if (!bulkuser.getIsvalid()){errorcount++;}
                    bulkusers.add(bulkuser);
                }
                if (errorcount==0){
                    for (Iterator<UploadedKeyword> bulkuserIterator = bulkusers.iterator(); bulkuserIterator.hasNext();) {
                        UploadedKeyword bulkuser = bulkuserIterator.next();
                        bulkuser.createUser();
                    }
                } else {
                    formCenter = UploadInputForm.getForm(bulkusers);
                    Pagez.getUserSession().setMessage("There are "+errorcount+" errors to correct below. You can make changes... we'll validate again before creating any keywords.");
                }
            } catch (Exception ex) {
                logger.error("", ex);
            }
        }
    }


%>



<%@ include file="/template/header.jsp" %>



<%if (formCenter.length()==0){%>
    <form action="/sysadmin/keywords-upload.jsp" method="post" enctype="multipart/form-data" class="niceform">
        <input type="hidden" name="dpage" value="/sysadmin/keywords-upload.jsp">
        <input type="hidden" name="action" value="upload">
        <table cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td valign="top">
                    <font class="formfieldnamefont"></font>
                </td>
                <td valign="top">
                    <input type="file" size="50" name="csvfile">
                    <br/>
                    <font class="tinyfont">
                        File must be a .csv (comma separated, quoted) file with two columns (KEYWORD, ISLOCATION).  The first row is ignored.
                        <br/>
                        Download a <a href="/sysadmin/keywords.csv">sample file</a>
                        <br/><br/>
                    </font>
                </td>
            </tr>
            <tr>
                <td valign="top">
                    <font class="formfieldnamefont"></font>
                </td>
                <td valign="top">
                    <input type="submit" class="formsubmitbutton sexybutton sexysimple sexyxxl" value="Upload">
                </td>
            </tr>
        </table>
    </form>
<%} %>

 <%if (formCenter.length()>0){%>
    <form action="/sysadmin/keywords-upload.jsp" method="post" class="niceform">
        <input type="hidden" name="dpage" value="/sysadmin/keywords-upload.jsp">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="numberofbulkusers" value="<%=bulkusers.size()%>">
        <input type="submit" class="formsubmitbutton sexybutton sexysimple sexyxxl" value="Create these Keywords">
        <div style="font-size: 9px;">
        <table cellpadding="0" cellspacing="0" border="0">
        <%=formCenter%>
        </table>
        </div>
    </form>
<%} %>




<%@ include file="/template/footer.jsp" %>



