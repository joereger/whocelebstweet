package com.celebtwit.helpers;

import com.celebtwit.dao.Twit;
import com.celebtwit.dao.Mention;
import com.celebtwit.dao.hibernate.HibernateUtil;
import com.celebtwit.dao.hibernate.NumFromUniqueResult;
import com.celebtwit.scheduledjobs.GetTwitterPosts;
import com.celebtwit.util.Time;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;

import java.util.List;
import java.util.Iterator;
import java.util.Date;
import java.util.Calendar;

/**
 * User: Joe Reger Jr
 * Date: Apr 21, 2009
 * Time: 1:15:00 PM
 */
public class CountUniqueCelebsWhoMentioned {

    public static int getAllTime(Twit twit, int plid){
        Date startDate = Time.xYearsAgoStart(Calendar.getInstance(), 25).getTime();
        Date endDate = new Date();
        return get(twit, startDate, endDate, plid);
    }

    public static int get(Twit twit, Date startDate, Date endDate, int plid){
        Logger logger = Logger.getLogger(GetTwitterPosts.class);
        if (twit==null || twit.getTwitid()==0){
            return 0;
        }
        String startDateStr = Time.dateformatfordb(Time.getCalFromDate(startDate));
        String endDateStr = Time.dateformatfordb(Time.getCalFromDate(endDate));

        int out = NumFromUniqueResult.getInt("select count(distinct twitidceleb) as numOfUnique from Mention mention where mention.plid='"+plid+"' and mention.twitidmentioned="+twit.getTwitid()+" and mention.created_at>'"+startDateStr+"' and mention.created_at<'"+endDateStr+"' ");

        return out;
    }


}
