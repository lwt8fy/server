package net.jeeshop.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;

public class DateUtil {
    private static Logger log = Logger.getLogger(DateUtil.class);

    public static Date strToDate(String src, String format) {
        Date temp = null;
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            temp = dateFormat.parse(src);
        } catch (ParseException e) {
            log.error(e);
        }
        return temp;
    }
 
    public static Date strToDate(String src) {
        return strToDate(src, "yyyy-MM-dd");
    }

    public static Date strToDatehhmmss(String src) {
        return strToDate(src, "yyyy-MM-dd HH:mm:ss");
    }

    public static String changeyyyymmddToDate(String src) {
        String str1 = src.substring(0, 4);
        String str2 = src.substring(4, 6);
        String str3 = src.substring(6, 8);
        String str = str1 + "-" + str2 + "-" + str3;
        return str;
    }

    public static String dateToStr(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    
    
    public static String dateToStr(Date date) {
        if (date != null) {
            return dateToStr(date, "yyyy-MM-dd");
        } else {
            return "";
        }
    }
    public static String dateToStrSS() {
    		return dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
    	
    }

    public DateUtil() {
    }

    /**
     * 功能：当前时间增加月数。
     * @param months 正值时时间延后，负值时时间提前。
     * @return Date
     */
    public static Date addMonth(Date date,int months){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH)+months);
        return new Date(c.getTimeInMillis());
    }
    /**
     * 添加天数
     * @param strDate
     * @param days
     * @return
     */
    public static Date addDay(Date date, int days) {
    	Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, c.get(Calendar.DATE)+days);
        return new Date(c.getTimeInMillis());
    }
    /**
     * 添加小时
     * @param strDate
     * @param days
     * @return
     */
    public static Date addHour(Date date, int hour) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	c.set(Calendar.HOUR, c.get(Calendar.HOUR)+hour);
    	return new Date(c.getTimeInMillis());
    }
    /**
     * 添加分钟
     */
    public static Date addMinute(Date date, int minute) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	c.set(Calendar.MINUTE, c.get(Calendar.MINUTE)+minute);
    	return new Date(c.getTimeInMillis());
    }
    /**
     * 添加分钟返回字符串到秒
     */
    public static String addMinutetoStr(Date date, int minute) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	c.set(Calendar.MINUTE, c.get(Calendar.MINUTE)+minute);
    	return dateToStr(new Date(c.getTimeInMillis()),"yyyy-MM-dd HH:mm:ss");
    }



    private static DateFormat getDateFormat() {
        return getDateFormat("yyyy-MM-dd");
    }

    private static DateFormat getDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    private static Date getFormatedDate(DateFormat df, String strDate) {
        try {
            return df.parse(strDate);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "\u65E5\u671F\u683C\u5F0F\u4E0D\u5BF9\uFF0C\u65E0\u6CD5\u89E3\u6790\u3002",
                    ex);
        }
    }

    public static Date getFormatedDate(String strDate) {
        return getFormatedDate(getDateFormat(), strDate);
    }

    public static Date yearAfter(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, day);
        return calendar.getTime();
    }

    public static Date dayAfter(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }

    public static Date monthAfter(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static Date monthBefore(Date date, int month) {
        return monthAfter(date, 0 - month);
    }

    public static Date yearBefore(Date date, int month) {
        return yearAfter(date, 0 - month);
    }

    public static Date dayBefore(Date date, int day) {
        return dayAfter(date, 0 - day);
    }

   
  

    public static String logFormatTransform(String time) {
        String newStr = "";
        if (time != null && !time.trim().equals("")) {
            newStr = time.substring(0, 4) + "-" + time.substring(4, 6) + "-"
                    + time.substring(6, 8);
            if (time.length() > 8)
                newStr = newStr + " " + time.substring(8, 10) + ":"
                        + time.substring(10, 12) + ":" + time.substring(12, 14);
        }
        return newStr;
    }
    public static int getYear(String strDate) {
        return Integer.parseInt(strDate.substring(0, 4));
    }

    public static int getMonth(String sDate) {
        return Integer.parseInt(sDate.substring(5, sDate.indexOf("-", 5)));
    }
    public static String getToday() {
        return getDateTimeString(System.currentTimeMillis(), 1);
    }
    public static String getDateTimeString(long mill, int format) {
        if (mill < 0L)
            return "";
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(mill);
        int year = cl.get(1);
        int month = cl.get(2) + 1;
        int day = cl.get(5);
        int hour = cl.get(11);
        int mm = cl.get(12);
        int ss = cl.get(13);
        int ms = cl.get(14);
        String ret = "";
        switch (format) {
            default:
                break;

            case 0: // '\0'
                ret = year + "-" + (month >= 10 ? "" + month : "0" + month);
                break;

            case 1: // '\001'
                ret = year + "-" + (month >= 10 ? "" + month : "0" + month) + "-"
                        + (day >= 10 ? "" + day : "0" + day);
                break;

            case 2: // '\002'
                ret = year + "-" + (month >= 10 ? "" + month : "0" + month) + "-"
                        + (day >= 10 ? "" + day : "0" + day) + " "
                        + (hour >= 10 ? "" + hour : "0" + hour) + ":"
                        + (mm >= 10 ? "" + mm : "0" + mm) + ":"
                        + (ss >= 10 ? "" + ss : "0" + ss);
                break;

            case 3: // '\003'
                String sMs;
                if (ms < 10)
                    sMs = "00" + ms;
                else if (ms < 100)
                    sMs = "0" + ms;
                else
                    sMs = "" + ms;
                sMs = "." + sMs;
                ret = year + "-" + (month >= 10 ? "" + month : "0" + month) + "-"
                        + (day >= 10 ? "" + day : "0" + day) + " "
                        + (hour >= 10 ? "" + hour : "0" + hour) + ":"
                        + (mm >= 10 ? "" + mm : "0" + mm) + ":"
                        + (ss >= 10 ? "" + ss : "0" + ss) + sMs;
                break;

            case 4: // '\004'
                ret = (year + "").substring(2)
                        + (month >= 10 ? "" + month : "0" + month)
                        + (day >= 10 ? "" + day : "0" + day);
                break;

            case 5: // '\005'
                ret = year + "" + (month >= 10 ? "" + month : "0" + month)
                        + (day >= 10 ? "" + day : "0" + day);
                break;

            case 6: // '\006'
                ret = year + "" + "\u5E74"
                        + (month >= 10 ? "" + month : "0" + month) + "\u6708"
                        + (day >= 10 ? "" + day : "0" + day) + "\u65E5";
                break;
        }
        return ret;
    }

    
    private static String BUSINESS_DATE_FOR_TEST = null;
    public static final int FORMAT_SHORTDATE = 0;
    public static final int FORMAT_DATE = 1;
    public static final int FORMAT_DATETIME = 2;
    public static final int FORMAT_DATETIMEMILLISECOND = 3;
    public static final int FORMAT_YYMMDD = 4;
    private static final int FORMAT_YYYYMMDD = 5;
    public static final int FORMAT_DATESTRING = 6;
    public static final int FORMAT_DATE_CBBS = 7;
    public static final int ONE_DAY_MILLISECOND = 0x5265c00;
    public static String GeneratorBeanId = "bizTime";
    public static String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    public static String FULL24_DATE_FORMAT = "yyyy-MM-dd hh24:mm:ss";
}
