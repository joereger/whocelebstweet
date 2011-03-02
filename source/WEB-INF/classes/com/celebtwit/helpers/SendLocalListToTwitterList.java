package com.celebtwit.helpers;

import com.celebtwit.dao.Pl;
import com.celebtwit.dao.Twit;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.util.Num;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import twitter4j.Twitter;
import twitter4j.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: joereger
 * Date: Jan 16, 2010
 * Time: 10:16:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class SendLocalListToTwitterList {


    public static void send(Pl pl, String twitterusername, String twitterpassword, String listid){
        Logger logger = Logger.getLogger(SendLocalListToTwitterList.class);
//        if (!Num.isinteger(listid)){
//            logger.debug("listid not an int");
//            return;
//        }
//        //Connect to mama twitter with the twitterusername/password, not the ones from the pl... need permission on this list yo
//        Twitter twitter = new TwitterBaseImpl(twitterusername, twitterpassword);
//        //Get Twits to sync
//        //TwitplQuery
//        ArrayList<Integer> plidList = new ArrayList<Integer>();
//        plidList.add(pl.getPlid());
//        List<Twit> celebs = HibernateUtil.getSession().createCriteria(Twit.class)
//                                       .add(Restrictions.eq("isceleb", true))
//                                       .add(TwitPlHelper.getCrit(plidList))
//                                       .setMaxResults(1500)
//                                       .setCacheable(true)
//                                       .list();
//        for (Iterator<Twit> iterator=celebs.iterator(); iterator.hasNext();) {
//            Twit twit=iterator.next();
//            try{
//                String twitteruserid = GetTwitteruseridFromTwitterusername.get(twit, pl);
//                if (Num.isinteger(twitteruserid)){
//                    twitter.addUserListMember(Integer.parseInt(listid), Integer.parseInt(twitteruserid));
//                }
//            } catch (Exception ex){
//                logger.error("", ex);
//            }
//        }
    }







}
