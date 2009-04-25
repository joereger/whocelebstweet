package com.celebtwit.helpers;

import com.celebtwit.util.Time;

import java.util.Date;
import java.util.Calendar;

/**
 * User: Joe Reger Jr
 * Date: Apr 23, 2009
 * Time: 9:34:58 AM
 */
public class StartDateEndDate {

    private Date startDate;
    private Date endDate;

    public static int TYPE_ALLTIME = 1;
    public static int TYPE_THISMONTH = 2;
    public static int TYPE_LAST31DAYS = 3;
    public static int TYPE_THISWEEK = 4;
    public static int TYPE_LAST7DAYS = 5;
    public static int TYPE_YESTERDAY = 6;
    public static int TYPE_TODAY = 7;

    public StartDateEndDate(int TYPE){
        load(TYPE);
    }

    public StartDateEndDate(String typeFriendlyName){
        if (typeFriendlyName==null){
            load(0); //load() will choose default
            return;
        }
        if (typeFriendlyName.equals("thismonth")){
            load(TYPE_THISMONTH);
        } else if (typeFriendlyName.equals("last31days")){
            load(TYPE_LAST31DAYS);
        } else if (typeFriendlyName.equals("thisweek")){
            load(TYPE_THISWEEK);
        } else if (typeFriendlyName.equals("last7days")){
            load(TYPE_LAST7DAYS);
        } else if (typeFriendlyName.equals("yesterday")){
            load(TYPE_YESTERDAY);
        } else if (typeFriendlyName.equals("today")){
            load(TYPE_TODAY);
        } else {
            load(0);  //load() will choose default
        }
    }

    private void load(int TYPE){
        if (TYPE==TYPE_THISMONTH){
            startDate = Time.xMonthsAgoStart(Calendar.getInstance(), 0).getTime();
            endDate = new Date();
        } else if (TYPE==TYPE_LAST31DAYS){
            startDate = Time.xDaysAgoStart(Calendar.getInstance(), 31).getTime();
            endDate = new Date();
        } else if (TYPE==TYPE_THISWEEK){
            startDate = Time.xWeeksAgoStart(Calendar.getInstance(), 0).getTime();
            endDate = new Date();
        } else if (TYPE==TYPE_LAST7DAYS){
            startDate = Time.xDaysAgoStart(Calendar.getInstance(), 7).getTime();
            endDate = new Date();
        } else if (TYPE==TYPE_YESTERDAY){
            startDate = Time.xDaysAgoStart(Calendar.getInstance(), 1).getTime();
            endDate = Time.xDaysAgoEnd(Calendar.getInstance(), 1).getTime();
        } else if (TYPE==TYPE_TODAY){
            startDate = Time.xDaysAgoStart(Calendar.getInstance(), 0).getTime();
            endDate = new Date();
        } else {
            //Default to TYPE_ALLTIME
            startDate = Time.xYearsAgoStart(Calendar.getInstance(), 25).getTime();
            endDate = new Date();
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate=startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate=endDate;
    }
}
