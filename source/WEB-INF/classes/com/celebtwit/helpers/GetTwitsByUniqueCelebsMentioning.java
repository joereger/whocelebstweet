package com.celebtwit.helpers;

import com.celebtwit.util.Time;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.dao.Twit;

import java.util.*;

/**
 * User: Joe Reger Jr
 * Date: Apr 21, 2009
 * Time: 1:19:10 PM
 */
public class GetTwitsByUniqueCelebsMentioning {



    public static ArrayList<TwitUniqueCelebsMentioning> get(Date startDate, Date endDate, boolean ismentionedaceleb, int numbertoget){
        ArrayList<TwitUniqueCelebsMentioning> out = new ArrayList<TwitUniqueCelebsMentioning>();
        String emptyStr = "";
        String startDateStr = Time.dateformatfordb(Time.getCalFromDate(startDate));
        String endDateStr = Time.dateformatfordb(Time.getCalFromDate(endDate));
        //Set onlyshowcelebs = false to show only non-celebs
        String ismentionedacelebStr = " and mention.ismentionedaceleb=false ";
        if (ismentionedaceleb){
            ismentionedacelebStr = " and mention.ismentionedaceleb=true ";
        }
        //Query
        List results = HibernateUtil.getSession().createQuery("select count(distinct twitidceleb) as numOfUnique, mention.twitidmentioned from Mention mention where mention.created_at>'"+startDateStr+"' and mention.created_at<'"+endDateStr+"' "+ismentionedacelebStr+" group by mention.twitidmentioned order by count(distinct twitidceleb) desc"+emptyStr).setMaxResults(numbertoget).list();
        for (Iterator iterator=results.iterator(); iterator.hasNext();) {
            Object[] row = (Object[])iterator.next();
            Long numOfUnique = (Long)row[0];
            int twitidmentioned = (Integer)row[1];
            Twit twit = Twit.get(twitidmentioned);
            TwitUniqueCelebsMentioning twitCelebsMentioning = new TwitUniqueCelebsMentioning();
            twitCelebsMentioning.setTwit(twit);
            twitCelebsMentioning.setUniquecelebsmentioning(numOfUnique.intValue());
            out.add(twitCelebsMentioning);
        }



        return out;
    }






}