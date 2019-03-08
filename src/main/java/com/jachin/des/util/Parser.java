package com.jachin.des.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Jachin
 * @since 2019/3/6 17:16
 */
public class Parser {
    public static String parseString(String value, String defaultValue) {
        try {
            if (value == null) {
                return defaultValue;
            }
            if(value.trim().isEmpty()){
                return defaultValue;
            }
            return value.trim();
        } catch (Exception exp) {
            return defaultValue;
        }
    }

    public static int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception exp) {
            return defaultValue;
        }
    }

    public static boolean parseBoolean(String value, boolean defaultValue) {
        try {
            if (value == null) {
                return defaultValue;
            }
            value = value.trim();
            if (value.equalsIgnoreCase("true")) {
                return true;
            }
            if (value.equalsIgnoreCase("false")) {
                return false;
            }
            return defaultValue;
        } catch (Exception exp) {
            return defaultValue;
        }
    }

    public static long parseLong(String value, long defaultValue) {
        try {
            return Long.parseLong(value);
        } catch (Exception exp) {
            return defaultValue;
        }
    }

    public static float parseFloat(String value, float defaultValue) {
        try {
            return Float.parseFloat(value);
        } catch (Exception exp) {
            return defaultValue;
        }
    }

    public static Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception exp) {
            return null;
        }
    }

    public static double parseDouble(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (Exception exp) {
            return defaultValue;
        }
    }

    public static Calendar parseCalendar(Timestamp src) {
        if (src == null) {
            return null;
        }
        Calendar dst = Calendar.getInstance();
        dst.setTimeInMillis(src.getTime());
        return dst;
    }

    public static Calendar parseCalendar(java.sql.Date src) {
        if (src == null) {
            return null;
        }

        Calendar dst = Calendar.getInstance();
        dst.setTimeInMillis(src.getTime());
        return dst;
    }

    // such as 'yyyy-MM-dd HH:mm:ss'
    public static Calendar parseCalendar(String src, String fmt) {
        Calendar dst = Calendar.getInstance();
        try {
            SimpleDateFormat df = new SimpleDateFormat(fmt);
            Date date = df.parse(src);
            dst.setTime(date);
        } catch (Exception exp) {
            return null;
        }
        // dst.setTimeZone(TimeZone.getTimeZone("GMT-0"));
        return dst;
    }

    public static Calendar parseCalendar(java.util.Date src) {
        Calendar clr = Calendar.getInstance();
        clr.setTime(src);
        return clr;
    }

    public static String parseString(java.util.Date src) {
        return parseString(src, "yyyy-MM-dd HH:mm:ss");
    }

    public static String parseString(java.util.Date src, String fmt) {
        if(src == null) {
            return "";
        }

        SimpleDateFormat dt = new SimpleDateFormat(fmt);
        return dt.format(src);
    }

    public static String parseString(Calendar src) {
        if (src == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(src.getTime());
    }

    public static String parseString(Calendar src, String fmt) {
        if (src == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(fmt);
        return df.format(src.getTime());
    }

    public static String parseSimpleTime(Calendar src) {
        if( src == null ){
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(src.getTime());
    }

    public static String parseDateString(long lt, String fmt){
        int ltLen = new Long(lt).toString().length();
        if(ltLen!=10 && ltLen!=13){
            return "";
        }
        if(ltLen==10){
            lt*=1000;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(lt);
        return Parser.parseString(cal, fmt);
    }

    public static final class DateFormat {
        public static final int YEAR = 1;			// 2010年 / 2010
        public static final int MONTH = 2;			// 10月 / Oct
        public static final int DAY = 3;			// 23日 / 23
        public static final int DAY_OF_WEEK = 4;	// 星期六 / Sat
        public static final int AM_PM = 5;			// 下午 / PM
        public static final int MD = 6;				// 10月23日 / Oct 23
        public static final int MDW = 7;			// 10月23日(星期六) / Sat, Oct 23
        public static final int YMD = 8;			// 2010年10月23日 / Oct 23, 2010
        public static final int YMDW = 9;			// 2010年10月23日(星期六) / Sat, Oct 23, 2010
        public static final int AP_H = 10;			// 下午2点 / 2 PM
        public static final int AP_HM = 11;			// 下午2:19 / 2:19 PM
        public static final int AP_HMS = 12;		// 下午2:19:01 / 2:19:01 PM
        public static final int YMDW_AP_HM = 13;	// 2010年10月23日(星期六) 下午2:19 / Sat, Oct 23, 2010 2:19 PM
        public static final int YMDW_AP_HMS = 14;	// 2010年10月23日(星期六) 下午2:19:01 / Sat, Oct 23, 2010 2:19:01 PM
        public static final int SIMPLE_YMD_HMS = 15;		// 2010-10-23 14:19:01
    }

    public static String parseTime(Calendar src, int format, int lcid) {
        if (src == null) {
            return "";
        }
        if (lcid == 1) {
            switch (format)
            {
                case DateFormat.YEAR:
                    return src.get(Calendar.YEAR) + "年";
                case DateFormat.MONTH:
                    return (src.get(Calendar.MONTH) + 1) + "月";
                case DateFormat.DAY:
                    return src.get(Calendar.DATE) + "日";
                case DateFormat.DAY_OF_WEEK:
                    switch (src.get(Calendar.DAY_OF_WEEK)) {
                        case Calendar.MONDAY : return "星期一";
                        case Calendar.TUESDAY: return "星期二";
                        case Calendar.WEDNESDAY : return "星期三";
                        case Calendar.THURSDAY : return "星期四";
                        case Calendar.FRIDAY : return "星期五";
                        case Calendar.SATURDAY : return "星期六";
                        case Calendar.SUNDAY : return "星期日";
                    }
                    return "";
                case DateFormat.AM_PM:
                    if (src.get(Calendar.AM_PM) == Calendar.AM) {
                        if(src.get(Calendar.HOUR) >= 0 && src.get(Calendar.HOUR) < 7 ){
                            return "凌晨";
                        }
                        return "上午";
                    } else {
                        if (src.get(Calendar.HOUR) == 0) {
                            return "中午";
                        } else {
                            if(src.get(Calendar.HOUR) >= 7 && src.get(Calendar.HOUR) < 12){
                                return "晚上";
                            }
                            return "下午";
                        }
                    }
                case DateFormat.MD:
                    return parseTime(src, DateFormat.MONTH, lcid) + parseTime(src, DateFormat.DAY, lcid);
                case DateFormat.MDW:
                    return parseTime(src, DateFormat.MONTH, lcid) + parseTime(src, DateFormat.DAY, lcid) + "(" + parseTime(src, DateFormat.DAY_OF_WEEK, lcid) + ")";
                case DateFormat.YMD:
                    return parseTime(src, DateFormat.YEAR, lcid) + parseTime(src, DateFormat.MONTH, lcid) + parseTime(src, DateFormat.DAY, lcid);
                case DateFormat.YMDW:
                    return parseTime(src, DateFormat.YEAR, lcid) + parseTime(src, DateFormat.MONTH, lcid) + parseTime(src, DateFormat.DAY, lcid) + "(" + parseTime(src, DateFormat.DAY_OF_WEEK, lcid) + ")";
                case DateFormat.AP_H:
                    if (src.get(Calendar.AM_PM) == Calendar.AM) {
                        if(src.get(Calendar.HOUR) >= 0 && src.get(Calendar.HOUR) < 7 ){
                            return "凌晨" + src.get(Calendar.HOUR) + "点";
                        }
                        return "上午" + src.get(Calendar.HOUR) + "点";
                    } else {
                        int hour = src.get(Calendar.HOUR);
                        if (hour == 0) {
                            return "中午12点";
                        } else {
                            if(hour >= 7 && hour < 12){
                                return "晚上" + hour + "点";
                            }
                            return "下午" + hour + "点";
                        }
                    }
                case DateFormat.AP_HM:
                    if (src.get(Calendar.AM_PM) == Calendar.AM) {
                        if(src.get(Calendar.HOUR) >= 0 && src.get(Calendar.HOUR) < 7 ){
                            return "凌晨" + src.get(Calendar.HOUR) + ":" + String.format("%02d", src.get(Calendar.MINUTE));
                        }
                        return "上午" + src.get(Calendar.HOUR) + ":" + String.format("%02d", src.get(Calendar.MINUTE));
                    } else {
                        int hour = src.get(Calendar.HOUR);
                        if (hour == 0) {
                            return "中午12:" + String.format("%02d", src.get(Calendar.MINUTE));
                        } else {
                            if(hour >= 7 && hour < 12){
                                return "晚上" + hour + ":" + String.format("%02d", src.get(Calendar.MINUTE));
                            }
                            return "下午" + hour + ":" + String.format("%02d", src.get(Calendar.MINUTE));
                        }
                    }
                case DateFormat.AP_HMS:
                    return parseTime(src, DateFormat.AP_HM, lcid) + ":" + String.format("%02d", src.get(Calendar.SECOND));
                case DateFormat.YMDW_AP_HM:
                    return parseTime(src, DateFormat.YMDW, lcid) + " " + parseTime(src, DateFormat.AP_HM, lcid);
                case DateFormat.YMDW_AP_HMS:
                    return parseTime(src, DateFormat.YMDW, lcid) + " " + parseTime(src, DateFormat.AP_HMS, lcid);
                case DateFormat.SIMPLE_YMD_HMS:
                    String date = String.format("%d-%02d-%02d", src.get(Calendar.YEAR), (src.get(Calendar.MONTH) + 1), src.get(Calendar.DATE));
                    String time = String.format("%02d:%02d:%02d", src.get(Calendar.HOUR_OF_DAY), src.get(Calendar.MINUTE), src.get(Calendar.SECOND));
                    return date + " " + time;
            }
            return "";
        } else {
            switch (format)
            {
                case DateFormat.YEAR:
                    return Integer.toString(src.get(Calendar.YEAR));
                case DateFormat.MONTH:
                    switch (src.get(Calendar.MONTH)) {
                        case Calendar.JANUARY: return "Jan";
                        case Calendar.FEBRUARY: return "Feb";
                        case Calendar.MARCH: return "Mar";
                        case Calendar.APRIL: return "Apr";
                        case Calendar.MAY: return "May";
                        case Calendar.JUNE: return "Jun";
                        case Calendar.JULY: return "Jul";
                        case Calendar.AUGUST: return "Aug";
                        case Calendar.SEPTEMBER: return "Sep";
                        case Calendar.OCTOBER: return "Oct";
                        case Calendar.NOVEMBER: return "Nov";
                        case Calendar.DECEMBER: return "Dec";
                    }
                    return "";
                case DateFormat.DAY:
                    return Integer.toString(src.get(Calendar.DATE) + 1);
                case DateFormat.DAY_OF_WEEK:
                    switch (src.get(Calendar.DAY_OF_WEEK)) {
                        case Calendar.MONDAY : return "Mon";
                        case Calendar.TUESDAY: return "Tue";
                        case Calendar.WEDNESDAY : return "Wed";
                        case Calendar.THURSDAY : return "Thu";
                        case Calendar.FRIDAY : return "Fri";
                        case Calendar.SATURDAY : return "Sat";
                        case Calendar.SUNDAY : return "Sun";
                    }
                    return "";
                case DateFormat.AM_PM:
                    if (src.get(Calendar.AM_PM) == Calendar.AM) {
                        return "AM";
                    } else {
                        return "PM";
                    }
                case DateFormat.MD:
                    return parseTime(src, DateFormat.MONTH, lcid) + " " + parseTime(src, DateFormat.DAY, lcid);
                case DateFormat.MDW:
                    return parseTime(src, DateFormat.DAY_OF_WEEK, lcid) + ", " + parseTime(src, DateFormat.MD, lcid);
                case DateFormat.YMD:
                    return parseTime(src, DateFormat.MD, lcid) + ", " + parseTime(src, DateFormat.YEAR, lcid);
                case DateFormat.YMDW:
                    return parseTime(src, DateFormat.DAY_OF_WEEK, lcid) + ", " + parseTime(src, DateFormat.YMD, lcid);
                case DateFormat.AP_H:
                    if (src.get(Calendar.AM_PM) == Calendar.AM) {
                        return src.get(Calendar.HOUR) + " AM";
                    } else {
                        int hour = src.get(Calendar.HOUR);
                        if (hour == 0) {
                            return "12 PM";
                        } else {
                            return hour + " PM";
                        }
                    }
                case DateFormat.AP_HM:
                    if (src.get(Calendar.AM_PM) == Calendar.AM) {
                        return src.get(Calendar.HOUR) + ":" + String.format("%02d", src.get(Calendar.MINUTE)) + " AM";
                    } else {
                        int hour = src.get(Calendar.HOUR);
                        if (hour == 0) {
                            return "12:" + String.format("%02d", src.get(Calendar.MINUTE)) + " PM";
                        } else {
                            return hour + ":" + String.format("%02d", src.get(Calendar.MINUTE)) + " PM";
                        }
                    }
                case DateFormat.AP_HMS:
                    if (src.get(Calendar.AM_PM) == Calendar.AM) {
                        return src.get(Calendar.HOUR) + ":" + String.format("%02d", src.get(Calendar.MINUTE)) + ":" + String.format("%02d", src.get(Calendar.SECOND)) + " AM";
                    } else {
                        int hour = src.get(Calendar.HOUR);
                        if (hour == 0) {
                            return "12:" + String.format("%02d", src.get(Calendar.MINUTE)) + ":" + String.format("%02d", src.get(Calendar.SECOND)) + " PM";
                        } else {
                            return hour + ":" + String.format("%02d", src.get(Calendar.MINUTE)) + ":" + String.format("%02d", src.get(Calendar.SECOND)) + " PM";
                        }
                    }
                case DateFormat.YMDW_AP_HM:
                    return parseTime(src, DateFormat.YMDW, lcid) + " " + parseTime(src, DateFormat.AP_HM, lcid);
                case DateFormat.YMDW_AP_HMS:
                    return parseTime(src, DateFormat.YMDW, lcid) + " " + parseTime(src, DateFormat.AP_HMS, lcid);
            }
            return "";
        }
    }

    public static String parseDateString(Calendar src) {
        if (src == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(src.getTime());
    }


    public static String parseDateString(Calendar src,String fmt) {
        if (src == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(fmt);
        return df.format(src.getTime());
    }


    public static String parseMinuteString(Calendar src) {
        return parseDateString(src, "yyyy-MM-dd HH:mm");
    }

    public static String parseSecondString(Calendar src) {
        return parseDateString(src, "yyyy-MM-dd HH:mm:ss");
    }

    public static Timestamp parseTimestamp(Calendar src) {
        return new Timestamp(src.getTimeInMillis());
    }

    public static String parseEmailDomain(String email) {
        if (email == null) {
            return "";
        }
        int pos = email.lastIndexOf('@');
        if (pos <= 0 || pos >= email.length() - 1) {
            return "";
        }
        return email.substring(pos + 1);
    }

    public static String parseUnit(Integer data){

        return parseUnit((long)data,null);
    }

    //parseFileSize
    public static String parseUnit(Long data){
//		String stringData = "0";
//		String stringUnit = "B";
//		if( data == null || data <= 0){
//			return stringData + stringUnit;
//		}
//		if(data >= 1024){
//			double kb = ((double)data) / 1024;
//			if( kb >= 1024 ){
//				double mb = kb/1024;
//				if( mb >= 1024 ){
//					double gb = mb/1024;
//					stringData = gb + "";
//					stringUnit = "GB";
//				}else{
//					stringData = mb + "";
//					stringUnit = "MB";
//				}
//			}else{
//				stringData = kb + "";
//				stringUnit = "KB";
//			}
//		}else{
//			stringData = data + "";
//			stringUnit = "B";
//		}
//
//		int pointIndex = stringData.indexOf(".");
//		String finalResult = "";
//		if( pointIndex == -1){
//			finalResult = stringData + stringUnit;
//		}else{
//			if( pointIndex + 2>stringData.length() -1){
//				finalResult = stringData + stringUnit;
//			}else{
//				stringData = stringData.substring(0,pointIndex+3);
//				finalResult = stringData + stringUnit;
//			}
//		}
//
//		return finalResult;
        return parseUnit(data,null);
    }

    //data:传入的字节数, unit:需要转换的单位
    public static String parseUnit(Long data,String unit){
        String finalUnit = "B";
        double calcTmp = (double)data;
        if(unit== null){
            if(data >= 1024){
                double tmpKB = Math.round( (calcTmp/1024)*100 ) / 100d;
                if(tmpKB >= 1024){
                    double tmpMB = Math.round( ( (calcTmp/1024)/1024 )*100 ) / 100d;
                    if(tmpMB >= 1024){
                        finalUnit = "GB";
                        return Math.round(  (((calcTmp/1024)/1024)/1024) * 100 ) / 100d + finalUnit;
                    }else{
                        finalUnit = "MB";
                        return tmpMB + finalUnit;
                    }
                }else{
                    finalUnit = "KB";
                    return tmpKB + finalUnit;
                }
            }else{
                finalUnit = "B";
                return data + finalUnit;
            }
        }else if(unit == "B"){
            return data + finalUnit;
        }else if(unit == "KB" || unit == "K"){
            finalUnit = "KB";
            return Math.round( (calcTmp/1024)*100 ) / 100d + finalUnit;
        }else if(unit == "MB" || unit == "M"){
            finalUnit = "MB";
            return Math.round( ( (calcTmp/1024)/1024 )*100 ) / 100d + finalUnit;
        }else if(unit == "GB" || unit =="G"){
            finalUnit = "GB";
            return Math.round(  (((calcTmp/1024)/1024)/1024) * 100 ) / 100d + finalUnit;
        }else{
            return data + finalUnit;
        }
    }
}
