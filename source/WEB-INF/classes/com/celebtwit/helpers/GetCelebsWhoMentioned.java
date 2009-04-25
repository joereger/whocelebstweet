package com.celebtwit.helpers;

import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.dao.Twit;
import com.celebtwit.util.Str;
import com.celebtwit.util.Time;

import java.util.*;

/**
 * User: Joe Reger Jr
 * Date: Apr 21, 2009
 * Time: 1:19:10 PM
 */
public class GetCelebsWhoMentioned {

    public static ArrayList<TwitCelebWhoMentioned> get(Date startDate, Date endDate, int twitidmentioned,  int numbertoget){
        ArrayList<TwitCelebWhoMentioned> out = new ArrayList<TwitCelebWhoMentioned>();
        String emptyStr = "";
        String startDateStr = Time.dateformatfordb(Time.getCalFromDate(startDate));
        String endDateStr = Time.dateformatfordb(Time.getCalFromDate(endDate));
        //Query
        List results = HibernateUtil.getSession().createQuery("select sum(1) as numOfMentions, mention.twitidceleb from Mention mention where mention.created_at>'"+startDateStr+"' and mention.created_at<'"+endDateStr+"' and mention.twitidmentioned='"+twitidmentioned+"' group by mention.twitidceleb order by sum(1) desc"+emptyStr).setMaxResults(numbertoget).list();
        for (Iterator iterator=results.iterator(); iterator.hasNext();) {
            Object[] row = (Object[])iterator.next();
            Long numOfMentions = (Long)row[0];
            int twitidceleb = (Integer)row[1];
            Twit twit = Twit.get(twitidceleb);
            TwitCelebWhoMentioned twitCelebWhoMentioned = new TwitCelebWhoMentioned();
            twitCelebWhoMentioned.setTwit(twit);
            twitCelebWhoMentioned.setMentions(numOfMentions.intValue());
            out.add(twitCelebWhoMentioned);
        }
        return out;
    }

 



}