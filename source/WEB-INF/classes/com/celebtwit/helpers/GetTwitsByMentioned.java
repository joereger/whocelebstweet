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
public class GetTwitsByMentioned {

    public static ArrayList<TwitMention> get(Date startDate, Date endDate, int twitidcelebwhomentioned, boolean ismentionedaceleb,  int numbertoget, int plid){
        ArrayList<TwitMention> out = new ArrayList<TwitMention>();
        String emptyStr = "";
        String startDateStr = Time.dateformatfordb(Time.getCalFromDate(startDate));
        String endDateStr = Time.dateformatfordb(Time.getCalFromDate(endDate));
        //Set twitidcelebwhomentioned = 0 to rank based on all
        String twitidcelebwhomentionedStr = "";
        if (twitidcelebwhomentioned>0){
            twitidcelebwhomentionedStr = " and mention.twitidceleb='"+twitidcelebwhomentioned+"' ";
        }
        //Set onlyshowcelebs = false to show only non-celebs
        String ismentionedacelebStr = " and mention.ismentionedaceleb=false ";
        if (ismentionedaceleb){
            ismentionedacelebStr = " and mention.ismentionedaceleb=true ";
        }
        //Query
        List results = HibernateUtil.getSession().createQuery("select sum(1) as numOfMentions, mention.twitidmentioned from Mention mention where mention.plid='"+plid+"' and mention.created_at>'"+startDateStr+"' and mention.created_at<'"+endDateStr+"' "+twitidcelebwhomentionedStr+" "+ismentionedacelebStr+" group by mention.twitidmentioned order by sum(1) desc"+emptyStr).setMaxResults(numbertoget).list();
        for (Iterator iterator=results.iterator(); iterator.hasNext();) {
            Object[] row = (Object[])iterator.next();
            Long numOfMentions = (Long)row[0];
            int twitidmentioned = (Integer)row[1];
            Twit twit = Twit.get(twitidmentioned);
            TwitMention twitMention = new TwitMention();
            twitMention.setTwit(twit);
            twitMention.setMentions(numOfMentions.intValue());
            out.add(twitMention);
        }
        return out;
    }





}
