package com.celebtwit.cache.providers.jboss;

import org.apache.log4j.Logger;
import org.jboss.cache.Node;
import org.jboss.cache.Fqn;

import java.util.Iterator;
import java.util.Set;
import java.util.Calendar;

import com.celebtwit.htmlui.UserSession;
import com.celebtwit.util.Str;
import com.celebtwit.util.DateDiff;

/**
 * Dumps a class to html
 */
public class CacheDumper {

    public static String getHtml(String fqn, int levelsToDisplay){
        Logger logger = Logger.getLogger(CacheDumper.class);
        try{
            Set childrenNames = JbossTreeCacheAOPProvider.getTreeCache().getChildrenNames(fqn);
            return dumpMap(childrenNames, 0, fqn, levelsToDisplay).toString();
        } catch (Exception ex){
            logger.debug(ex);
            return "Error retrieving cache: " + ex.getMessage();
        }
    }


    private static StringBuffer dumpMap(Set childrenNames, int nestinglevel, String fqnPrepend, int levelsToDisplay){
        Logger logger = Logger.getLogger(CacheDumper.class);
        StringBuffer out = new StringBuffer();
        nestinglevel++;
        if(childrenNames!=null){
            for (Iterator chilIterator = childrenNames.iterator(); chilIterator.hasNext();) {
                Object childName = chilIterator.next();
                String fqnFull = fqnPrepend+"/"+childName;
                if (fqnPrepend.equals("/")){
                    fqnFull = fqnPrepend + childName;
                }
                logger.debug("childName.toString()="+childName.toString()+" - fqnFull="+fqnFull);


                StringBuffer nestStrBuff = new StringBuffer();
                for(int i=0; i<=nestinglevel; i++){
                    nestStrBuff.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                }
                String nestStr = nestStrBuff.toString();

                out.append("<br/>"+nestStr+childName.toString());

                //UserSession Special Output
                try{
                    Object objInCache = (Object)JbossTreeCacheAOPProvider.getTreeCache().get(fqnFull);
                    if (objInCache!=null){
                        Class c = objInCache.getClass();
                        String s = c.getName();
                        logger.debug("c.getName()="+s);
                        if (objInCache instanceof Node){
                            Node node = (Node)objInCache;
                            Fqn fqn = node.getFqn();
                            logger.debug("fqn.getName()="+fqn.getName());
                            Set keys = node.getDataKeys();
                            for (Iterator iterator=keys.iterator(); iterator.hasNext();) {
                                Object o=iterator.next();
                                logger.debug("o.toString()="+o.toString());
                                out.append("<br/>"+nestStr+nestStr+"<font style=\"font-size: 9px; font-weight: bold;\">Key="+ Str.truncateString(o.toString(), 100)+"</font>");
                                Object nodeObj = JbossTreeCacheAOPProvider.getTreeCache().get(fqnFull, o);
                                if (nodeObj!=null){
                                    //UserSession
                                    if (nodeObj instanceof UserSession){
                                        UserSession userSession = (UserSession)nodeObj;
                                        if (userSession.getIsloggedin()){
                                            if (userSession.getUser()!=null){
                                                out.append("<br/>"+nestStr+nestStr+"<font style=\"font-size: 9px;\"><a href=\"userdetail.jsp?userid="+userSession.getUser().getUserid()+"\">"+userSession.getUser().getFirstname()+" "+userSession.getUser().getLastname()+"</a> (email="+userSession.getUser().getEmail()+")</font>");
                                            }
                                        }
                                        int secondsOld=DateDiff.dateDiff("second", Calendar.getInstance(), userSession.getCreatedate());
                                        out.append("<br/>"+nestStr+nestStr+"<font style=\"font-size: 9px;\">secondsold="+secondsOld+"</font>");
                                        out.append("<br/>"+nestStr+nestStr+"<font style=\"font-size: 9px;\">isloggedin="+userSession.getIsloggedin()+"</font>");
                                        out.append("<br clear='all'/>");
                                    }
                                }
                            }
                        }
                    } else {
                        logger.debug("No obj in cache with fqnFull="+fqnFull);
                    }
                } catch (Exception e){
                    //Nothing to do, it's not likely a UserSession
                    logger.debug(e);
                }

                try{
                    if (nestinglevel<=levelsToDisplay){
                        Set cNames = JbossTreeCacheAOPProvider.getTreeCache().getChildrenNames(fqnFull);
                        if (cNames!=null){
                            logger.debug("cNames.size()="+cNames.size()+" fqnFull="+fqnFull);
                        } else {
                            logger.debug("cNames==null fqnFull="+fqnFull);
                        }
                        out.append(dumpMap(cNames, nestinglevel, fqnFull, levelsToDisplay));
                    }
                } catch (org.jboss.cache.CacheException cex){
                    logger.debug(cex);
                }

            }
        }
        return out;
    }






}
