package cn.dlc.commonlibrary.utils;

import android.text.TextUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lzy on 2017/4/20 0020.
 */

public class TimeUtil {

    public static final long ONE_WEEK = 7 * 24 * 60 * 60 * 1000L;
    public static final long ONE_DAY = 24 * 60 * 60 * 1000L;
    public static final long ONE_DAY_SECOND = 24 * 60 * 60L;
    public static final long HALF_DAY = ONE_DAY / 2;
    public static final long ONE_HOUR = 3600 * 1000;
    public static final long ONE_HOUR_SECOND = 3600;
    public static final long ONE_MINUTE = 60 * 1000;
    public static final long ONE_MINUTE_SECOND = 60;
    /**
     * 时区偏移
     */
    public static final long TIME_OFFSET = getUnixTime(1970, 1, 1, 0);
    public static final long TIME_OFFSET_SECOND = TIME_OFFSET / 1000;

    /**
     * 转换linux时间戳
     *
     * @param year 年
     * @param month 月，1~12
     * @param day 日
     * @param hour 时，0~23
     * @param minute 分，0~59
     * @param second 秒，0~59
     * @param millisecond 毫秒，0~999
     * @return
     */
    public static Calendar getCalendar(int year, int month, int day, int hour, int minute,
        int second, int millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
        return calendar;
    }

    public static Calendar getCalendar(int year, int month, int day, int hour, int minute,
        int second) {
        return getCalendar(year, month, day, hour, minute, second, 0);
    }

    public static Calendar getCalendar(int year, int month, int day, int hour, int minute) {
        return getCalendar(year, month, day, hour, minute, 0, 0);
    }

    public static Calendar getCalendar(int year, int month, int day, int hour) {
        return getCalendar(year, month, day, hour, 0, 0, 0);
    }

    public static Calendar getCalendar(int year, int month, int day) {
        return getCalendar(year, month, day, 0, 0, 0, 0);
    }

    /**
     * 获取unix时间戳时间
     *
     * @param timeInMillis unix时间戳，毫秒
     * @return
     */
    public static Calendar getCalendar(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return calendar;
    }

    /**
     * 获取精确到日的时间，时分秒毫秒会被清零
     *
     * @param calendar
     * @return
     */
    public static Calendar getDateCalendar(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 获取精确到日的时间，时分秒毫秒会被清零
     *
     * @param timeMillis
     * @return
     */
    public static Calendar getDateCalendar(long timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14"）
     *
     * @param time
     * @return
     */
    public static String getUnixToTime(String time) {
        if (time==null|| TextUtils.isEmpty(time)){
            return "";
        }
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc * 1000L));
        return times;

    }

    public static long getUnixTime(int year, int month, int day, int hour, int minute, int second,
        int millisecond) {
        return getCalendar(year, month, day, hour, minute, second, millisecond).getTimeInMillis();
    }

    public static long getUnixTime(int year, int month, int day, int hour, int minute, int second) {
        return getCalendar(year, month, day, hour, minute, second, 0).getTimeInMillis();
    }

    public static long getUnixTime(int year, int month, int day, int hour, int minute) {
        return getCalendar(year, month, day, hour, minute, 0, 0).getTimeInMillis();
    }

    public static long getUnixTime(int year, int month, int day, int hour) {
        return getCalendar(year, month, day, hour, 0, 0, 0).getTimeInMillis();
    }

    public static long getUnixSecond(int year, int month, int day, int hour, int minute,
        int second) {
        return getCalendar(year, month, day, hour, minute, second, 0).getTimeInMillis() / 1000;
    }

    public static long getUnixSecond(int year, int month, int day, int hour, int minute) {
        return getCalendar(year, month, day, hour, minute, 0, 0).getTimeInMillis() / 1000;
    }

    public static long getUnixSecond(int year, int month, int day, int hour) {
        return getCalendar(year, month, day, hour, 0, 0, 0).getTimeInMillis() / 1000;
    }

    public static long getUnixSecond(int year, int month, int day) {
        return getCalendar(year, month, day, 0, 0, 0, 0).getTimeInMillis() / 1000;
    }


}
