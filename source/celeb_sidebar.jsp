<%@ page import="com.celebtwit.htmlui.Pagez" %>
<%@ page import="com.celebtwit.util.Str" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.celebtwit.embed.*" %>
<!-- Start Celeb_Sidebar -->
            <div class="roundedBox" style="width:200px;">



                <%if (twit!=null && sidebar_twit.getIsceleb()){%>
                    <%--<center><br/><br/><img src="<%=twitimageurl%>" width="48" height="48" border="0" align="middle" alt="<%=sidebar_twit.getRealname()%>"></center>--%>
                    <center><a href="/twitter/<%=sidebar_twitterusername%>/picture/"><img src="<%=twitimageurl%>" width="190"  style="border: 10px solid #ffffff;" align="middle" alt="<%=sidebar_twit.getRealname()%>"></a></center>
                <%}%>

                <script language="javascript">
                function toggleLink() {
                    var ele = document.getElementById("toggleLink");
                    if(ele.style.display == "block") {
                        ele.style.display = "none";
                    } else {
                        ele.style.display = "block";
                    }
                }
                function toggleA() {
                    var ele = document.getElementById("toggleTextA");
                    if(ele.style.display == "block") {
                        ele.style.display = "none";
                    } else {
                        ele.style.display = "block";
                    }
                }
                function toggleB() {
                    var ele = document.getElementById("toggleTextB");
                    if(ele.style.display == "block") {
                        ele.style.display = "none";
                    } else {
                        ele.style.display = "block";
                    }
                }
                </script>
                <%
                String tweetThisStatus = "check out " + sidebar_pageurl;
                tweetThisStatus = URLEncoder.encode(tweetThisStatus, "UTF-8");
                %>
                <center>
                <font class="smallfont"><a href="javascript:toggleLink();" style="text-decoration: underline; color: #0000ff;">Link to this Page</a> | <a href="http://twitter.com/home?status=<%=tweetThisStatus%>" style="text-decoration: underline; color: #0000ff;" target="_blank">Tweet This</a></font>
                <div id="toggleLink" style="display: none; text-align: center;">
                    <textarea rows="3" cols="30" name="linkurl" id="linkurl" style="font-size:9px;" onclick="javascript:document.getElementById('linkurl').select();"><%=Str.cleanForHtml(sidebar_pageurl)%></textarea>
                    <br/><font class="tinyfont">Copy and paste the above URL into your blog, email, im or website to link to this page.</font>
                </div>
                </center>

                <br/>
                <br clear="all">
                    <table cellpadding="10" cellspacing="5" border="0">
                        <tr>
                            <td valign="top">
                                <%if (sidebar_twit!=null && sidebar_twit.getDescription().length()>0){%>
                                    <font class="normalfont" style="font-size:15px; color:#666666;"><%=sidebar_twit.getDescription()%></font>
                                    <br/>
                                <%}%>
                                <%--<div style="margin-left:3px; width: 160px; background:#e6e6e6;"><font class="smallfont" style="font-size:11px; text-decoration: underline;">More Information</font></div>--%>
                                <div style="margin-left:0px; width:190px;">
                                <%if (sidebar_twit!=null && sidebar_twit.getWebsite_url().length()>0){%>
                                    <font class="smallfont">
                                    <a href="<%=sidebar_twit.getWebsite_url()%>" target="_blank"  style="text-decoration: underline; color: #0000ff;">Website</a>
                                    |
                                    <a href="http://www.twitter.com/<%=sidebar_twitterusername%>/" target="_blank"  style="text-decoration: underline; color: #0000ff;">Twitter</a>
                                    </font>
                                    <br/>
                                <%} else { %>
                                    <font class="smallfont">
                                    <a href="http://www.twitter.com/<%=sidebar_twitterusername%>/" target="_blank"  style="text-decoration: underline; color: #0000ff;">Twitter</a>
                                    </font>
                                    <br/>
                                <%}%>
                                <%if (sidebar_twit!=null && sidebar_twit.getFollowers_count()>0){%>
                                    <font class="smallfont"><%=sidebar_twit.getFollowers_count()%> followers</font>
                                    <br/>
                                <%}%>
                                <%if (sidebar_twit!=null && sidebar_twit.getStatuses_count()>0){%>
                                    <font class="smallfont"><%=sidebar_twit.getStatuses_count()%> updates</font>
                                    <br/>
                                <%}%>
                                </div>


                                <br/><br/>
                                <%=AdUtil.get160x90()%>

                                <br/><br/>
                                <%
                                if (twit!=null){
                                    CachedStuff cs = new CelebsSidebarKeywordsList(twit);
                                    CelebsSidebarKeywordsList obj = (CelebsSidebarKeywordsList) GetCachedStuff.get(cs, Pagez.getUserSession().getPl());
                                    String sidebar_keywordslist = obj.getHtml();
                                    %><%=sidebar_keywordslist%><%
                                }
                                %>



                            </td>
                        </tr>
                        <tr>
                            <td valign="top" width="175">
                                <%=JsCelebMentions.get(twit, sidebar_twitterusername, Pagez.getUserSession().getPl())%>
                                <a href="javascript:toggleA();"><font class="tinyfont">+ embed in your blog/website</font></a>
                                <div id="toggleTextA" style="display: none">
                                    <input type="text" name="embedB" value="<%=Str.cleanForHtml("<script src=\"http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+sidebar_twitterusername+"/js/celebtweets/\"></script>")%>" size="25">
                                    <br/><font class="smallfont">Copy and paste this code into your blog or website to display the box.</font>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td valign="top" width="175">
                                <%=JsDifferentCelebs.get(twit, sidebar_twitterusername, Pagez.getUserSession().getPl())%>
                                <a href="javascript:toggleB();"><font class="tinyfont">+ embed in your blog/website</font></a>
                                <div id="toggleTextB" style="display: none">
                                    <input type="text" name="embedB" value="<%=Str.cleanForHtml("<script src=\"http://"+Pagez.getUserSession().getPl().getCustomdomain1()+"/twitter/"+sidebar_twitterusername+"/js/differentcelebs/\"></script>")%>" size="25">
                                    <br/><font class="smallfont">Copy and paste this code into your blog or website to display the box.</font>
                                </div>
                            </td>
                        </tr>
                    </table>
            </div>

<!-- End Celeb_Sidebar -->