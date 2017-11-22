/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.strongme.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 *
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 根据开始结束日期生成yyyyMM格式的日期字符串列表
     * 如果开始日期大于结束日期返回list为空，如果相等返回该开始结束日期
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getDateListByStartEndTime(Date startDate, Date endDate) {
        List<String> dateStr = Lists.newArrayList();
        if (startDate == null && endDate != null) {
            dateStr.add(DateFormatUtils.format(endDate, "yyyyMM"));
        } else if (startDate != null && endDate == null) {
            dateStr.add(DateFormatUtils.format(startDate, "yyyyMM"));
        } else if (startDate != null && endDate != null) {
            int month = getMonthDiff(endDate, startDate);
            if (month != -1) {
                dateStr.add(DateFormatUtils.format(startDate, "yyyyMM"));
                for (int i = 0; i < month; i++) {
                    dateStr.add(DateFormatUtils.format(addMonths(startDate, i + 1), "yyyyMM"));
                }
            }
        }
        return dateStr;
    }

    /**
     * 获取两个日期相差的月数
     *
     * @param d1 较大的日期
     * @param d2 较小的日期
     * @return 如果d1>d2返回 月数差 否则返回0
     */
    public static int getMonthDiff(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        if (c1.getTimeInMillis() < c2.getTimeInMillis()) return -1;
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 假设 d1 = 2015-8-16  d2 = 2011-9-30
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) yearInterval--;
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) monthInterval--;
        monthInterval %= 12;
        return yearInterval * 12 + monthInterval;
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null) return "";
        String formatDate = null;
        formatDate = DateFormatUtils.format(date, pattern);
        return formatDate;
    }

    public static String formateDateThisMonth() {
        return formatDate(new Date(), "yyyy-MM");
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    public static String getYear(Date date) {
        if (date == null) return "";
        return formatDate(date, "yyyy");
    }


    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    public static String getMonth(Date date) {

        if (date == null) return "";
        return formatDate(date, "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDateT(Date date, String parsePatterns) {
        SimpleDateFormat sdf = new SimpleDateFormat(parsePatterns);
        String s = sdf.format(date);
        return parseDate(s);
    }

    public static Date parseDate() {
        try {
            return parseDate(new Date().toString(), "yyyy-MM");
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDateEnd() {
        return parseDateEnd(new Date());
    }

    public static Date parseDateEnd(Date date) {
        try {
            String year = formatDate(date, "yyyy");
            return parseDate(year + "-12", "yyyy-MM");
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseDateStart() {
        return parseDateStart(new Date());
    }

    public static Date parseDateStart(Date date) {
        try {
            String year = formatDate(date, "yyyy");
            return parseDate(year + "-01", "yyyy-MM");
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseDateX(Object str, String pattern) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), pattern);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }


    /**
     * 获取每个月最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(date);
        final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date lastDate = cDay1.getTime();
        lastDate.setDate(lastDay);
        return lastDate;
    }

    /**
     * 获取一年最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfYear(Date date) {
        String year = getYear(date);
        try {
            return parseDate(year + "-12-31", "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    public static Long currentMiliSeconds() {
        return System.currentTimeMillis();
    }


    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    public static double getMinuteDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60);
    }


    /**
     * 指定年份的月列表  201601
     *
     * @param year
     * @return
     */
    public static Map<String, Object> loadWholeYearMonth(String year) {
        Map<String, Object> result = Maps.newLinkedHashMap();
        for (int i = 1; i <= 12; i++) {
            String month = String.valueOf(i);
            if (i < 10) {
                month = "0" + month;
            }
            String key = String.format("%s-%s", year, month);
            String value = String.format("%s年%s月", year, month);
            result.put(key, value);
        }
        return result;
    }

    public static Map<String, Object> loadWholeYearMonth() {
        return loadWholeYearMonth(getYear());
    }

    public static List<String> loadWholeYearMonthList(String year) {
        List<String> result = Lists.newLinkedList();
        for (int i = 1; i <= 12; i++) {
            String month = String.valueOf(i);
            if (i < 10) {
                month = "0" + month;
            }
            result.add(String.format("%s%s", year, month));
        }
        return result;
    }

    public static List<String> loadWholeYearMonthList() {
        return loadWholeYearMonthList(getYear());
    }

    /**
     * 获取从1月到指定月份到表后缀列表
     *
     * @param year
     * @param endMonth
     * @return
     */
    public static List<String> loadYearMonthListByEndMonth(String year, String endMonth) {
        List<String> result = Lists.newArrayList();
        int endMonthInteger = Integer.valueOf(endMonth);
        if (endMonthInteger < 1 || endMonthInteger > 12) return result;
        for (int i = 1; i <= endMonthInteger; i++) {
            String month = String.valueOf(i);
            if (i < 10) {
                month = "0" + month;
            }
            result.add(String.format("%s%s", year, month));
        }
        return result;
    }

    public static List<String> loadYearMonthListByEndMonth(String endMonth) {
        List<String> result = loadYearMonthListByEndMonth(DateUtils.getYear(), endMonth);
        return result;
    }

    public static List<String> loadYearRestMonthListByStartMonth(String year, String startMonth) {
        List<String> result = Lists.newArrayList();
        int startMonthInteger = Integer.valueOf(startMonth);
        if (startMonthInteger < 1 || startMonthInteger > 12) return result;
        for (int i = startMonthInteger; i < 13; i++) {
            String month = String.valueOf(i);
            if (i < 10) {
                month = "0" + month;
            }
            result.add(String.format("%s%s", year, month));
        }
        return result;
    }

    public static List<String> loadYearRestMonthListByStartMonth(String startMonth) {
        return loadYearRestMonthListByStartMonth(DateUtils.getYear(), startMonth);
    }

    public static Date cutDay(Date date) {
        return parseDate(formatDate(date, "yyyy-MM"));
    }

    private static void setMinTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setMaxTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
    }
}
