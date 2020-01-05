package com.treasures.cn.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DateTimeUtil {
    public static Date dateFromTimeStamp(long timeStampVal) {
        return new Date(timeStampVal);
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATETIME_HM);
        }
    };
    private final static ThreadLocal<SimpleDateFormat> dateFormater1 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE_FORMATE);
        }
    };

    public static String formatFromTimeStamp(long timeStampVal) {
        return format(new Date(timeStampVal));
    }

    private final static String DATE_FORMATE = "yyyy-MM-dd";

    public static String formatDate(Date date) {
        return format(date, DATE_FORMATE);
    }

    private final static String DATETIME_FORMATE = "yyyy-MM-dd HH:mm:ss";
    private final static String DATETIME_HHMM = "HH:mm";
    private final static String DATETIME_MMDD = "MM/dd";
    private final static String DATETIME_HHMMSS = "HH:mm:ss";
    private final static String DATETIME_HZ = "yyyy年MM月dd日 HH:mm:ss";
    private final static String DATETIME_HM = "yyyy/MM/dd HH:mm:ss";

    public static String formatServerTime(Date date) {
        return DateTimeUtil.format(date, "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    }

    public static String formatDateTime(Date date) {
        return format(date, DATETIME_FORMATE);
    }

    public static String format(Date time) {
        return format(time, null);
    }

    public static String format(Date time, String formatText) {
        if (time == null) {
            return "";
        }
        if (formatText == null) {
            formatText = DATETIME_FORMATE;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(formatText);
        return formatter.format(time);
    }

    public static String formatHHMM(Date time) {
        return format(time, DATETIME_HHMM);
    }

    public static String formatMMDD(Date time) {
        return format(time, DATETIME_MMDD);
    }

    public static String formatHHMMSS(Date time) {
        return format(time, DATETIME_HHMMSS);
    }

    public static String formatHZ(Date time) {
        return format(time, DATETIME_HZ);
    }

    public static String formatHM(Date time) {
        return format(time, DATETIME_HM);
    }

    public static Date parse(String text, String format) throws BusiException {
        if (format == null) {
            format = DATETIME_FORMATE;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(text);
        } catch (ParseException e) {
            throw new BusiException("text format error", e);
        }
    }

    //搜索时间
    public static String searchTime(Date time) {
        if (time == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String text = formatter.format(time);
        text += " 00:00:00";
        return text;
    }

    //当天起始时间
    public static Date startTime(Date time) throws BusiException {
        if (time == null) {
            return time;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String text = formatter.format(time);
        text += " 00:00:00";
        formatter.applyPattern("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter.parse(text);
        } catch (ParseException e) {
            throw new BusiException("text format error", e);
        }
    }

    //当天结束时间
    public static Date endTime(Date time) throws BusiException {
        if (time == null) {
            return time;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String text = formatter.format(time);
        text += " 23:59:59";
        formatter.applyPattern("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter.parse(text);
        } catch (ParseException e) {
            throw new BusiException("text format error", e);
        }
    }

    public static String startTimeStr(Date time) {
        if (time == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String text = formatter.format(time);
        text += " 00:00:00";
        return text;
    }

    public static String endTimeStr(Date time) {
        if (time == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String text = formatter.format(time);
        text += " 23:59:59";
        return text;
    }

    public static int diffDays(Date start, Date end) {
        if (start == null || end == null) {
            return 0;
        }
        return (int) ((end.getTime() - start.getTime()) / (1000 * 24 * 60 * 60));
    }

    public static int diffSeconds(Date start, Date end) {
        if (start == null || end == null) {
            return 0;
        }
        return (int) ((end.getTime() - start.getTime()) / 1000);
    }

    public static Date addSeconds(Date time, int offset) {
        return addIntevals(time, Calendar.SECOND, offset);
    }

    public static Date addMinutes(Date time, int offset) {
        return addIntevals(time, Calendar.MINUTE, offset);
    }

    public static Date addHours(Date time, int offset) {
        return addIntevals(time, Calendar.HOUR, offset);
    }

    public static Date addDays(Date time, int offset) {
        return addIntevals(time, Calendar.DAY_OF_MONTH, offset);
    }

    public static Date addMonths(Date time, int offset) {
        return addIntevals(time, Calendar.MONTH, offset);
    }

    public static Date addYears(Date time, int offset) {
        return addIntevals(time, Calendar.YEAR, offset);
    }

    private static Date addIntevals(Date time, int field, int offset) {
        if (time == null || offset == 0) {
            return time;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(field, offset);
        return cal.getTime();
    }

    public static Date startWeekDay(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date startMonthDay(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 将字符串转为日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {

        try {
            if (sdate.contains("/")) {
                return dateFormater.get().parse(sdate);
            } else {
                return dateFormater1.get().parse(sdate);
            }

        } catch (ParseException | NullPointerException e) {
            return new Date();
        }
    }

    /**
     * 判断是否是当天
     *
     * @param date
     * @return
     */
    public static boolean isSameDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (Calendar.getInstance().get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
            if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
                return true;
            } else
                return false;
        } else {
            return false;
        }
    }

    /**
     * 是否24小时之内
     * @return
     */
    public static boolean is24HInner(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar currentCalendar = Calendar.getInstance();//当前时间
        //必须当年当月
        if (currentCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                && currentCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
            Date add1DayDate = addDays(calendar.getTime(), 1);
            return diffSeconds(add1DayDate, currentCalendar.getTime()) > 0;
        }
        return false;
    }

    public static long createUserId(){
        long id = 0;
        String idStr = format(new Date(), "yyyyMMddHH");
        int index = new Random().nextInt(10);
        idStr += index;
        return Long.parseLong(idStr);
    }
}
