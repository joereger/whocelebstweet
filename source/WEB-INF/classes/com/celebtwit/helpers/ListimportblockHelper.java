package com.celebtwit.helpers;

import com.celebtwit.dao.Listimportblock;
import com.celebtwit.dao.hibernate.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: Feb 5, 2010
 * Time: 5:51:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListimportblockHelper {


    public static boolean isBlocked(String twitterusername){
        List<Listimportblock> listimportblocks = HibernateUtil.getSession().createCriteria(Listimportblock.class)
                                                   .add(Restrictions.eq("twitterusername", twitterusername.toLowerCase()))
                                                   .setCacheable(true)
                                                   .list();
        if (listimportblocks!=null && listimportblocks.size()>0){
            return true;
        }
        return false;
    }

    public static void removeBlock(String twitterusername){
        HibernateUtil.getSession().createQuery("delete Listimportblock l where l.twitterusername='"+twitterusername.toLowerCase()+"'").executeUpdate();
    }

    public static void addBlock(String twitterusername){
        Logger logger = Logger.getLogger(ListimportblockHelper.class);
        if (!isBlocked(twitterusername)){
            try{
                Listimportblock lib = new Listimportblock();
                lib.setTwitterusername(twitterusername.toLowerCase());
                lib.save();
            } catch (Exception ex){
                logger.error("", ex);
            }
        }
    }


}
