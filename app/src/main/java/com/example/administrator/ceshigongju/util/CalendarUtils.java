package com.example.administrator.ceshigongju.util;

import android.text.TextUtils;

import com.licheedev.myutils.LogPlus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/21.
 */

public class CalendarUtils {

    /**
     * 获取年月日(2017-06-01)
     *
     * @return
     */
    public static String getCurrentDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取年月日(2017-06-01 12:02)
     *
     * @return
     */
    public static String getCurrentDHMS() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取年月(201706)
     *
     * @return
     */
    public static String getCurrentMonth() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取年(2017)
     *
     * @return
     */
    public static String getCurrentYear() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取月(06)
     *
     * @return
     */
    public static String getCurrentMonth1() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取当月的 天数
     */
    public static int getCurrentMonthDay() {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 将标准时间转换成时间戳
     *
     * @param time
     * @return
     */
    public static long getUnixDate(String time) {

        long unixTime = 0;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
            unixTime = date.getTime() / 1000;
            LogPlus.e("时间戳 :", String.valueOf(unixTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return unixTime;
    }


    /**
     * 将标准时间转换成时间戳
     *
     * @param time
     * @return
     */
    public static long getUnixDateHM(String time) {

        long unixTime = 0;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(time);
            unixTime = date.getTime() / 1000;
            LogPlus.e("时间戳 :", String.valueOf(unixTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return unixTime;
    }


    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14 HH:mm"）
     *
     * @param time
     * @return
     */
    public static String getUnixToTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        @SuppressWarnings("unused") long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc * 1000L));
        return times;

    }


    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14 HH:mm:ss"）
     *
     * @param time
     * @return
     */
    public static String getUnixToTimeHMS(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused") long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc * 1000L));
        return times;

    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14"）
     *
     * @param time
     * @return
     */
    public static String getUnixToTimeYMD(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressWarnings("unused") long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc * 1000L));
        return times;

    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"06-14"）
     *
     * @param time
     * @return
     */
    public static String getUnixToTimeMD(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat sdr = new SimpleDateFormat("MM-dd");
        @SuppressWarnings("unused") long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc * 1000L));
        return times;

    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"10:00"）
     *
     * @param time
     * @return
     */
    public static String getUnixToTimeHM(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat sdr = new SimpleDateFormat("HH:mm");
        @SuppressWarnings("unused") long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc * 1000L));
        return times;

    }
    
   /* public static void main(String [] args){
        getGapCount1("1502812800","1503158400");
    }
*/

    /**
     * 计算两个时间相隔的天数  时间格式为 yyyy-MM-dd
     *
     * @param timeStart
     * @param timeEnd
     * @return
     */
    public static int getGapCount(String timeStart, String timeEnd) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = df.parse(timeStart);
            d2 = df.parse(timeEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println((d2.getTime() - d1.getTime()) / (60 * 60 * 1000 * 24));
        int time = (int) ((d2.getTime() - d1.getTime()) / (60 * 60 * 1000 * 24));
        return time;
    }


    /**
     * 计算两个时间相隔的天数  时间格式为时间戳 1402733340
     *
     * @param timeStart
     * @param timeEnd
     * @return
     */
    public static int getGapCount1(String timeStart, String timeEnd) {

        String sTime = getUnixToTime(timeStart);
        String eTime = getUnixToTime(timeEnd);
        return getGapCount(sTime, eTime);
    }

}
