package com.celebtwit.startup;

import com.celebtwit.cache.providers.CacheFactory;
import com.celebtwit.dao.Pl;
import com.celebtwit.dao.User;
import com.celebtwit.dao.Userrole;
import com.celebtwit.dao.hibernate.HibernateSessionQuartzCloser;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.dao.hibernate.NumFromUniqueResult;
import com.celebtwit.pageperformance.PagePerformanceUtil;
import com.celebtwit.scheduledjobs.SystemStats;
import com.celebtwit.systemprops.InstanceProperties;
import com.celebtwit.systemprops.SystemProperty;
import com.celebtwit.systemprops.WebAppRootDir;
import com.celebtwit.xmpp.SendXMPPMessage;
import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * User: Joe Reger Jr
 * Date: Apr 17, 2006
 * Time: 10:50:54 AM
 */
public class ApplicationStartup implements ServletContextListener {

    private static boolean ishibernateinitialized = false;
    private static boolean iswabapprooddirdiscovered = false;
    private static boolean isdatabasereadyforapprun = false;
    private static boolean isappstarted = false;

    Logger logger = Logger.getLogger(this.getClass().getName());
    private static Scheduler scheduler = null;

    public void contextInitialized(ServletContextEvent cse) {
       System.out.println("CelebTwit: Application initialized");
       System.out.print(printBug());
       //Shut down mbeans, if they're running
       shutdownCacheMBean();

       //Configure some dir stuff
        WebAppRootDir ward = new WebAppRootDir(cse.getServletContext());
        iswabapprooddirdiscovered = true;
        //Connect to database
        if (InstanceProperties.haveValidConfig()){
            //Run pre-hibernate db upgrades
            DbVersionCheck dbvcPre = new DbVersionCheck();
            dbvcPre.doCheck(DbVersionCheck.EXECUTE_PREHIBERNATE);
            //Set infinispan jgroups vars
            String jgroupstcpaddress="127.0.0.1";
            try {
                InetAddress addr = InetAddress.getLocalHost();
                byte[] ipAddr = addr.getAddress();
                String hostname = addr.getHostName();
                String ipAddrStr = "";
                for (int i=0; i<ipAddr.length; i++) {
                    if (i > 0) {
                        ipAddrStr += ".";
                    }
                    ipAddrStr += ipAddr[i]&0xFF;
                }
                jgroupstcpaddress=ipAddrStr;
            } catch (UnknownHostException e) {
                logger.error("", e);
            } catch (Exception ex){
                logger.error("", ex);
            }
            System.setProperty("jgroups.tcp.address", jgroupstcpaddress);
            System.out.println("jgroups.tcp.address="+jgroupstcpaddress);
            System.setProperty("jgroups.tcpping.initial_hosts", InstanceProperties.getJgroupstcppinginitialhosts());
            System.out.println("jgroups.tcpping.initial_hosts="+InstanceProperties.getJgroupstcppinginitialhosts());
            System.setProperty("jgroups.tcp.port", InstanceProperties.getJgroupstcpport());
            System.out.println("jgroups.tcp.port="+InstanceProperties.getJgroupstcpport());
            //Initialize object cache so it only creates one instance of itself
            CacheFactory.getCacheProvider().get("applicationstartup", "applicationstartup");
            //Set up hibernate
            HibernateUtil.getSession();
            ishibernateinitialized = true;
            //Run post-hibernate db upgrades
            DbVersionCheck dbvcPost = new DbVersionCheck();
            dbvcPost.doCheck(DbVersionCheck.EXECUTE_POSTHIBERNATE);
            //Check to make sure we're good to go
            if (RequiredDatabaseVersion.getHavecorrectversion()){
                isdatabasereadyforapprun = true;
                isappstarted = true;
            }
            //Configure Log4j
            //Logger.getRootLogger().setLevel();
        } else {
            logger.info("InstanceProperties.haveValidConfig()=false");
        }
        //Set up pl
        guaranteeAtLeastOnePlExists();
        //Load SystemProps
        SystemProperty.refreshAllProps();
        //Refresh SystemStats
        SystemStats ss = new SystemStats();
        try{ss.execute(null);}catch(Exception ex){logger.error("",ex);}
        //Usercheck
        userCheck();
        //Initialize Quartz
        initQuartz(cse.getServletContext());
        //Add Quartz listener
        try{
            SchedulerFactory schedFact = new StdSchedulerFactory();
            schedFact.getScheduler().addGlobalJobListener(new HibernateSessionQuartzCloser());
        } catch (Exception ex){logger.error("",ex);}
        //Report to log and XMPP
        logger.info("WebAppRootDir = " + WebAppRootDir.getWebAppRootPath());
        logger.info("CelebTwit Application Started!  Tweet tweet bitches!");
        SendXMPPMessage xmpp = new SendXMPPMessage(SendXMPPMessage.GROUP_SYSADMINS, "CelebTwit Application started! ("+WebAppRootDir.getUniqueContextId()+")");
        xmpp.send();
    }

    public void contextDestroyed(ServletContextEvent cse) {
        //Record last of pageperformance numbers
        try{PagePerformanceUtil.recordAndFlush();}catch(Exception ex){logger.error("", ex);}
        //Notify sysadmins
        SendXMPPMessage xmpp = new SendXMPPMessage(SendXMPPMessage.GROUP_SYSADMINS, "CelebTwit Application shut down! ("+WebAppRootDir.getUniqueContextId()+")");
        xmpp.send();
        //Shut down Hibernate
        try{
            HibernateUtil.closeSession();
            HibernateUtil.killSessionFactory();
        } catch (Exception ex){logger.error("",ex);}
        //Shut down MBeans
        shutdownCacheMBean();
        //Log it
        System.out.println("CelebTwit: Application shut down! ("+InstanceProperties.getInstancename()+")");
    }

    public static void initQuartz(ServletContext sc){
        //
        //If there are errors in this code, check the org.quartz.ee.servlet.QuartzInitializerServlet
        //I grabbed this code from there instead of having the app server call it from web.xml
        //Potential problem with web.xml is that I may not add my listeners quickly enough.
        //
        Logger logger = Logger.getLogger(ApplicationStartup.class);
        logger.debug("Quartz Initializing");
        String QUARTZ_FACTORY_KEY = "org.quartz.impl.StdSchedulerFactory.KEY";
		StdSchedulerFactory factory;
		try {

			String configFile = null;
			if (configFile != null) {
				factory = new StdSchedulerFactory(configFile);
			} else {
				factory = new StdSchedulerFactory();
			}

			// Should the Scheduler being started now or later
			String startOnLoad = null;
			if (startOnLoad == null || (Boolean.valueOf(startOnLoad).booleanValue())) {
				// Start now
				scheduler = factory.getScheduler();
				scheduler.start();
				logger.debug("Quartz Scheduler has been started");
			} else {
				logger.debug("Quartz Scheduler has not been started - Use scheduler.start()");
			}

			logger.debug("Quartz Scheduler Factory stored in servlet context at key: " + QUARTZ_FACTORY_KEY);
			sc.setAttribute(QUARTZ_FACTORY_KEY, factory);

		} catch (Exception e) {
			logger.error("Quartz failed to initialize", e);
		}
    }

    public static void shutdownCacheMBean(){
        Logger logger = Logger.getLogger(ApplicationStartup.class);
        try{
            ArrayList servers = MBeanServerFactory.findMBeanServer(null);
            for (Iterator it = servers.iterator(); it.hasNext(); ) {
                try{
                    MBeanServer mBeanServer = (MBeanServer)it.next();
                    //List of beans to log
                    Set mBeanNames = mBeanServer.queryNames(null, null);
                    for (Iterator iterator = mBeanNames.iterator(); iterator.hasNext();) {
                        ObjectName objectName = (ObjectName) iterator.next();
                        //logger.debug("MBean -> Name:"+objectName.getCanonicalName()+" Domain:"+objectName.getDomain());
                        if (objectName.getCanonicalName().indexOf("CelebTwit-TreeCache-Cluster")>-1){
                            try{
                                logger.info("Unregistering MBean: "+objectName.getCanonicalName());
                                mBeanServer.unregisterMBean(objectName);
                            } catch (Exception ex){
                                logger.error("",ex);
                            }
                        }
                    }
                } catch (Exception ex){
                    logger.error("",ex);
                }
            }
        } catch (Exception ex){
            logger.error("",ex);
        }
    }

    public static void shutdownMBean(String name){
        Logger logger = Logger.getLogger(ApplicationStartup.class);
        try{
            ArrayList servers = MBeanServerFactory.findMBeanServer(null);
            for (Iterator it = servers.iterator(); it.hasNext(); ) {
                try{
                    MBeanServer mBeanServer = (MBeanServer)it.next();
                    //Do the remove
                    ObjectName tcObject = new ObjectName(name);
                    if (mBeanServer.isRegistered(tcObject)){
                        logger.info(tcObject.getCanonicalName()+" was already registered");
                        try{
                            logger.info("Unregistering MBean: "+tcObject.getCanonicalName());
                            mBeanServer.unregisterMBean(tcObject);
                        } catch (Exception ex){
                            logger.error("",ex);
                        }
                    } else {
                        logger.info(tcObject.getCanonicalName()+" was *not* already registered");
                    }
                } catch (Exception ex){
                    logger.error("",ex);
                }
            }
        } catch (Exception ex){
            logger.error("",ex);
        }
    }

    private static void guaranteeAtLeastOnePlExists(){
        Logger logger = Logger.getLogger(ApplicationStartup.class);
        String emptyStr = "";
        List pls = HibernateUtil.getSession().createQuery("from Pl"+emptyStr).list();
        if (pls==null){logger.info("pls==null");} else if (pls.size()<=0){logger.info("pls.size()<=0");}
        if (pls==null || pls.size()<=0){
            Pl pl = new Pl();
            pl.setName("whoCelebsTweet.com");
            pl.setCelebiscalled("celeb");
            pl.setCustomdomain1("www.whocelebstweet.com");
            pl.setCustomdomain2("whocelebstweet.com");
            pl.setCustomdomain3("www.whocelebstwitter.com");
            pl.setTwitterusername("whocelebstweet");
            pl.setListownerscreenname1("");
            pl.setListid1("");
            pl.setListownerscreenname2("");
            pl.setListid2("");
            pl.setListownerscreenname3("");
            pl.setListid3("");
            pl.setCommasepadnetworks("");
            pl.setIsdisplayotherplson(true);
            pl.setPingfmapikey("");
            pl.setSisterdomain1("");
            pl.setSisterdomain2("");
            pl.setSisterdomain3("");
            pl.setSistername("");
            pl.setCommasepadnetworks("");
            pl.setTwitteraccesstoken("");
            pl.setTwitteraccesstokensecret("");
            try{pl.save();}catch(Exception ex){logger.error(ex);}
        }
    }


    public static boolean getIswabapprooddirdiscovered() {
        return iswabapprooddirdiscovered;
    }

    public static boolean getIshibernateinitialized() {
        return ishibernateinitialized;
    }

    public static boolean getIsdatabasereadyforapprun() {
        return isdatabasereadyforapprun;
    }

    public static boolean getIsappstarted() {
        return isappstarted;
    }

    private static String printBug(){
        StringBuffer out = new StringBuffer();
        out.append("                          `  `  -  .  _                                   \n" +
                "      '  _                  `            .                                \n" +
                "       .     -  .  .  .  . - '             .                              \n" +
                "                                            `.                            \n" +
                "          ` .  .         .  .  .  .           .                           \n" +
                "                  `   `              `  `.      `                         \n" +
                "                . - .                   '         `.           .nMf       \n" +
                "               .      `               '  iXMMMMMM            .MMMM        \n" +
                "          ..       ..   `     .xxnMM' ,nMMMMMMMMMX  `      :MM XM         \n" +
                "           \"MMM> <MMMMMi  !MMMMMMMM',MMMMMMMMMMMMMX  `   .MMf xM'         \n" +
                "            `?MM.`MMMMMMMX.MMMMMMMhHMMMMMMMMMMMMMMM<MM  :MMP .MM          \n" +
                "              `Mh ?MMMMMMMMMMMMMMMMMP\"\"\"\"?MMMMMMMMMMMf 4MMM  MM>          \n" +
                "                Mh ?MMMP`\"\"MMMMMMMMM'.d$$Nu.`?MMMMMMM  MMMf dMM>          \n" +
                "                 \"k MMP e$b.'?MMMMf u$$$$$$$b `MMMMMMMM`MM  `MM>          \n" +
                "                  ?  M d$$$$b.`MMM '$$$$$$$$$b 'MMMMMMM 'M <  ML          \n" +
                "                   \\ 4 3$$$$$$b`MX $F\"\"$$$$$$$  MMMMMMMM  \\dk 4M          \n" +
                "                  . . L'$$F   $ `X $    $$$$$E 4MMMMMMMMM. \"f MMk         \n" +
                "             .x*\"\"` .dM. ?$ouu$ 'M \"$ed$$$$$$ '\" .:dMMMMMM. ?MMMMr        \n" +
                "            MM>4  xMMMhx. `?$$$ 4MM ?$$$$$P  xMMMMMMMMMMMMM  MMMMP        \n" +
                "            ?MM> MMMMMMMMMMMn  dMMMMx`?$\".dMMMMMMMMMMMM???M>              \n" +
                "                 'MP\"\"\"\"\"\"\"\"` HMMMMMMM~.dMMMMMMMMP\"\" .r xMM               \n" +
                "                  `MMM < 'F :MMMMMMMMMMMMMMMP\"`  d$ JMf MMM .             \n" +
                "                MM 'M :M '  MMMMMMMMMMMMnnndM\"  d$F MM  4M  M:            \n" +
                "              .MMMM  .MM  L 4MMMMMMMMP\"MMP\" .z$ $$k MM> 'M 4MM:           \n" +
                "             .MMMMP .MMM  $r'MMMMMMP  ` .zd$$$$ $$k MMX  ? 4MMM:          \n" +
                "            MMMMMf XMMMf 4$$ 'MMMM  $$$ 3$$$$$$k\"\"  4MM.   'MMMMh         \n" +
                "          .MMMMP  dMMMM   ^\" o \"\" u$$$$ 4??\"\"        MMMr  `MMMMMM:       \n" +
                "         dMMMMf :MMMMM\"         `\"\"``           ...; 'MMM:  `MMMMMML      \n" +
                "       xMMMMP\" :MMMMMf               ..;;;  .i!!!!!!!  MMMM   \"MMMMMM:    \n" +
                "     xMMMMMf  :MMMP\"\"             <!!!!!` !!!!!!!'`  r MMMMh   `MMMMMMh   \n" +
                "   :MMMMMP   xMMM\".u.          :!!!!!!!!i!!''` .u  $$F MMMMMh    ?MMMMMMx \n" +
                " .dMMMMM\"   XMMP  $$$$h .     '''''''```  .ue$$$$R $F.MMMMMMMM    ?MMMMMMM \n" +
                "MMMMMMM     MMM. '?$$$'J$$$$$$bbr.d$$$$$$;4$$$$$$$  :MMMMMMMMM     MMMMMMM \n" +
                "MMMMMP     MMMMMMM:  \" $$$$$$$$$'8$$$$$$$k`$$$P\" .nMMMMMMMMMMM     MMMMMMM \n" +
                "MMMMM      MMMMMMMMMMn:. `\"???$$ $$$$$????'\" .xdMMMMMMMMMMMMM\"     MMMMMMM \n" +
                "MMMM'     `MMMMMMMMMMMMMMMMnn...........ndMMMMMMMMMMMMMMMMMM       MMMMMMM \n" +
                "MMMM      `MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\"       XMMMMMMM \n" +
                "MMMM       MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\"        nMMMMMMMM \n" +
                "MMMMk      `MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMP\"          MMMMMMMMMM \n" +
                "MMMMMM.      \"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMP\"            uMMMMMMMMMMM \n" +
                "MMMMMMMM.      `\"\"MMMMMMMMMMMMMMMMMMMMMMMMMP\"\"             .dMMMMMMMMMMMMM \n" +
                "MMMMMMMMMh:         `\"\"\"\"???MMMMMMMPP\"\"\"                .nMMMMMMMMMMMMMMMM \n" +
                "MMMMMMMMMMMMx.                                      .xMMMMMMMMMMMMMMMMMMMM \n" +
                "MMMMMMMMMMMMMMMr                                :dMMMMMMMMMMMMMMMMMMMMMMMM \n" +
                "MMMMMMMMMMMP\"`                         ..:nnMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM \n" +
                "MMMMMMM\"\"                  ...xnnnnHMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM \n" +
                "MMMM\"         ....ndMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM \n" +
                "MMM     xnMMMM\"\"`\"\"\"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM \n" +
                "MMM   'MMMMMM     n  'MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM \n" +
                "MMM.   \"?MMMMMh:.xM>  :MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM \n" +
                "MMMMMn    '\"\"???\"\"   :MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM \n" +
                "MMMMMMMMhnx.......nHMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM \n\n\n");
        return out.toString();
    }

    private void userCheck(){
        //@todo remove once in production and working... or make the default user more like "Admin:adminpass"
        Logger logger = Logger.getLogger(this.getClass().getName());
        int users = NumFromUniqueResult.getInt("select count(*) from User");
        if (users==0){
            try{
                //Create user
                User user = new User();
                user.setChargemethod(0);
                user.setChargemethodcreditcardid(0);
                user.setCreatedate(new Date());
                user.setEmail("joe@joereger.com");
                user.setEmailactivationkey("aaassdd");
                user.setEmailactivationlastsent(new Date());
                user.setFacebookappremoveddate(new Date());
                user.setFacebookuserid(0);
                user.setFirstname("Joe");
                user.setIsactivatedbyemail(true);
                user.setIsenabled(true);
                user.setIsfacebookappremoved(false);
                user.setLastname("Reger");
                user.setNickname("Joe Reger");
                user.setPassword("physics");
                user.save();
                //Permissions
                Userrole userrole = new Userrole();
                userrole.setUserid(user.getUserid());
                userrole.setRoleid(Userrole.USER);
                userrole.save();
                Userrole userrole2 = new Userrole();
                userrole2.setUserid(user.getUserid());
                userrole2.setRoleid(Userrole.SYSADMIN);
                userrole2.save();
                Userrole userrole3 = new Userrole();
                userrole3.setUserid(user.getUserid());
                userrole3.setRoleid(Userrole.CUSTOMERSUPPORT);
                userrole3.save();
                //Refresh user
                user.refresh();
            } catch (Exception ex){
                logger.error("", ex);
            }
        }
    }


}
