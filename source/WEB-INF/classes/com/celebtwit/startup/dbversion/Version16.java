package com.celebtwit.startup.dbversion;

import com.celebtwit.db.Db;
import com.celebtwit.startup.UpgradeDatabaseOneVersion;
import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Nov 26, 2006
 * Time: 11:57:46 AM
 */
public class Version16 implements UpgradeDatabaseOneVersion {

    Logger logger = Logger.getLogger(this.getClass().getName());

    public void doPreHibernateUpgrade(){
        logger.debug("doPreHibernateUpgrade() start");

        logger.debug("doPreHibernateUpgrade() finish");
    }

    public void doPostHibernateUpgrade(){
        logger.debug("doPostHibernateUpgrade() start");


        //-----------------------------------
        //-----------------------------------
        int countdddssf = Db.RunSQLUpdate("UPDATE twit SET pl01='0', pl02='0', pl03='0', pl04='0', pl05='0'");
        //-----------------------------------
        //-----------------------------------

        logger.debug("done updating twit pl01,pl02,etc");

        //-----------------------------------
        //-----------------------------------
        int countddd = Db.RunSQLUpdate("UPDATE twitpost SET pl01='0', pl02='0', pl03='0', pl04='0', pl05='0'");
        //-----------------------------------
        //-----------------------------------

        logger.debug("done updating twitpost pl01,pl02,etc");

        //-----------------------------------
        //-----------------------------------
        String[][] rstTwits= Db.RunSQL("SELECT twitid FROM twit WHERE isceleb=true ORDER BY twitid ASC");
        //-----------------------------------
        //-----------------------------------
        if (rstTwits!=null && rstTwits.length>0){
            for(int i=0; i<rstTwits.length; i++){
                int twitid = Integer.parseInt(rstTwits[i][0]);

                logger.debug("processing twitid["+(i+1)+"] of ["+rstTwits.length+"]");
                
                int pl01 = 0;
                int pl02 = 0;
                int pl03 = 0;
                int pl04 = 0;
                int pl05 = 0;

                //-----------------------------------
                //-----------------------------------
                String[][] rstTwitpls= Db.RunSQL("SELECT plid FROM twitpl WHERE twitid='"+twitid+"'");
                //-----------------------------------
                //-----------------------------------
                if (rstTwitpls!=null && rstTwitpls.length>0){
                    for(int j=0; j<rstTwitpls.length; j++){
                        int plid = Integer.parseInt(rstTwitpls[j][0]);
                        if (pl01==0){
                            pl01 = plid;
                        } else if (pl02==0){
                            pl02 = plid;
                        } else if (pl03==0){
                            pl03 = plid;
                        } else if (pl04==0){
                            pl04 = plid;
                        } else if (pl05==0){
                            pl05 = plid;
                        } else {
                            logger.error("twitid in >5 pls!! twitid="+twitid);
                        }
                    }
                }

                //-----------------------------------
                //-----------------------------------
                int countdddss = Db.RunSQLUpdate("UPDATE twit SET pl01='"+pl01+"', pl02='"+pl02+"', pl03='"+pl03+"', pl04='"+pl04+"', pl05='"+pl05+"' WHERE twitid='"+twitid+"'");
                //-----------------------------------
                //-----------------------------------

                logger.debug("processing twitid["+(i+1)+"] of ["+rstTwits.length+"] done updating twit");

                //-----------------------------------
                //-----------------------------------
                int countdddsd = Db.RunSQLUpdate("UPDATE twitpost SET pl01='"+pl01+"', pl02='"+pl02+"', pl03='"+pl03+"', pl04='"+pl04+"', pl05='"+pl05+"' WHERE twitid='"+twitid+"'");
                //-----------------------------------
                //-----------------------------------

                logger.debug("processing twitid["+(i+1)+"] of ["+rstTwits.length+"] done updating twitpost");
            }
        }



        logger.debug("doPostHibernateUpgrade() finish");
    }


    //Sample sql statements

    //-----------------------------------
    //-----------------------------------
    //int count = Db.RunSQLUpdate("CREATE TABLE `pltemplate` (`pltemplateid` int(11) NOT NULL auto_increment, logid int(11), plid int(11), type int(11), templateid int(11), PRIMARY KEY  (`pltemplateid`)) ENGINE=MyISAM DEFAULT CHARSET=latin1;");
    //-----------------------------------
    //-----------------------------------

    //-----------------------------------
    //-----------------------------------
    //int count = Db.RunSQLUpdate("ALTER TABLE megachart CHANGE daterangesavedsearchid daterangesavedsearchid int(11) NOT NULL default '0'");
    //-----------------------------------
    //-----------------------------------

    //-----------------------------------
    //-----------------------------------
    //int count = Db.RunSQLUpdate("ALTER TABLE account DROP gps");
    //-----------------------------------
    //-----------------------------------

    //-----------------------------------
    //-----------------------------------
    //int count = Db.RunSQLUpdate("ALTER TABLE megalogtype ADD isprivate int(11) NOT NULL default '0'");
    //-----------------------------------
    //-----------------------------------

    //-----------------------------------
    //-----------------------------------
    //int count = Db.RunSQLUpdate("DROP TABLE megafielduser");
    //-----------------------------------
    //-----------------------------------

    //-----------------------------------
    //-----------------------------------
    //int count = Db.RunSQLUpdate("CREATE INDEX name_of_index ON table (field1, field2)");
    //-----------------------------------
    //-----------------------------------


}