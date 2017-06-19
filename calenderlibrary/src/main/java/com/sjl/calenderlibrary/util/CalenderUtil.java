package com.sjl.calenderlibrary.util;

import com.sjl.calenderlibrary.bean.CalenderBean;

import java.util.Calendar;
import java.util.Date;

/**
 * CalenderUtil
 *
 * @author SJL
 * @date 2017/6/16
 */

public class CalenderUtil {
    public static CalenderBean getCalender(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        CalenderBean calenderBean = new CalenderBean(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.DAY_OF_WEEK)-1, getWholeMonth(calendar));
        return calenderBean;
    }

    /**
     * 获取当月天数
     *
     * @param calendar
     * @return
     */
    private static int getWholeMonth(Calendar calendar) {
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }

    public static CalenderBean getCalender(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return getCalender(calendar.getTime());
    }

    public static CalenderBean getCalender(int year, int month) {
        return getCalender(year, month, 1);
    }

    /**
     * 上个月
     * @param year
     * @param month
     * @return
     */
    public static CalenderBean getPreCalender(int year,int month){
        month--;
        if(month<=0){
            month=12;
            year--;
        }
        return getCalender(year,month);
    }

    /**
     * 下个月
     * @param year
     * @param month
     * @return
     */
    public static CalenderBean getNextCalender(int year,int month){
        month++;
        if(month>12){
            month=1;
            year++;
        }
        return getCalender(year,month);
    }
}
