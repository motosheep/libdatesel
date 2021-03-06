package com.north.light.libdatesel.v2.model;


import com.north.light.libdatesel.v2.bean.LibDateDayInMonthDetailInfoV2;
import com.north.light.libdatesel.v2.bean.LibDateMonthInYearDetailInfoV2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzt
 * time 2021/6/15 14:17
 *
 * @author lizhengting
 * 描述：日历管理类
 */
public class LibDateCalendarManagerV2 {
    /**
     * 一周开始的是：周一or周日
     */
    private int startMode = 1;

    private static class SingleHolder {
        static LibDateCalendarManagerV2 mInstance = new LibDateCalendarManagerV2();
    }

    public static LibDateCalendarManagerV2 getInstance() {
        return SingleHolder.mInstance;
    }

    public static void main(String[] arg) {
        Calendar cal = Calendar.getInstance();
        //赋值时年月日时分秒常用的6个值，注意月份下标从0开始，所以取月份要+1
        System.out.println("年:" + cal.get(Calendar.YEAR));
        System.out.println("月:" + (cal.get(Calendar.MONTH) + 1));
        System.out.println("日:" + cal.get(Calendar.DAY_OF_MONTH));
        System.out.println("时:" + cal.get(Calendar.HOUR_OF_DAY));
        System.out.println("分:" + cal.get(Calendar.MINUTE));
        System.out.println("秒:" + cal.get(Calendar.SECOND));
    }

    /**
     * 设置一周开始的是：周一or周日
     */
    public void setStartMode(int type) {
        startMode = type;
    }

    /**
     * 获取日历开始的日期
     */
    public int getStartMode() {
        return startMode;
    }

    /**
     * 日历头部控件
     * 根据数字转换为中文
     */
    public String changeDigitalToChinese(int org) {
        if (startMode == 1) {
            switch (org) {
                case 1:
                    return "周一";
                case 2:
                    return "周二";
                case 3:
                    return "周三";
                case 4:
                    return "周四";
                case 5:
                    return "周五";
                case 6:
                    return "周六";
                case 7:
                    return "周日";
            }
        } else if (startMode == 2) {
            switch (org) {
                case 1:
                    return "周日";
                case 2:
                    return "周一";
                case 3:
                    return "周二";
                case 4:
                    return "周三";
                case 5:
                    return "周四";
                case 6:
                    return "周五";
                case 7:
                    return "周六";
            }
        }
        return "";
    }

    /**
     * 获取当前年月日
     */
    public String getCurYMD(int interval) throws Exception {
        String year = getYear(interval);
        String month = getMonth(interval);
        String day = getDay(interval);
        return year + month + day;
    }

    /**
     * 获取当前年份
     *
     * @param interval 0当前年
     *                 负数，当前年减去数值
     *                 正数，当前年加上数值
     */
    public String getYear(int interval) throws Exception {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return getFixString(currentYear + interval);
    }

    /**
     * 获取当前月份
     */
    public String getMonth(int interval) throws Exception {
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        return getFixString(currentMonth + interval);
    }

    /**
     * 获取当前日
     */
    public String getDay(int interval) throws Exception {
        int currentMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return getFixString(currentMonth + interval);
    }

    /**
     * 获取某一年下的月份信息
     *
     * @return Map
     */
    public Map<String, LibDateMonthInYearDetailInfoV2> getMonthByYear(String year) throws Exception {
        Date time = new SimpleDateFormat("yyyy").parse(year);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        Map<String, LibDateMonthInYearDetailInfoV2> result = new HashMap<>();
        //计算每个月的天数，星期几，
        //一年一定是从1月开始，到12月结束的
        SimpleDateFormat mDayFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cacheCal = Calendar.getInstance();
        for (int i = 1; i <= 12; i++) {
            LibDateMonthInYearDetailInfoV2 detailInfo = new LibDateMonthInYearDetailInfoV2();
            String dayTotal = year + "-" + getFixString(i) + "-01";
            cacheCal.setTime(mDayFormat.parse(dayTotal));
            //月份的天数
            int dayOfMonth = cacheCal.getActualMaximum(Calendar.DAY_OF_MONTH);
            detailInfo.setDayOfMonth(String.valueOf(dayOfMonth));
            detailInfo.setYear(year);
            detailInfo.setMonth(getFixString(i));
            List<LibDateDayInMonthDetailInfoV2> monthList = getDayByMonth(year, getFixString(i));
            detailInfo.setDayInfoList(monthList);
            result.put(String.valueOf(i), detailInfo);
        }
        return result;
    }

    /**
     * 获取某个月份下的日期信息
     * change by lzt 20211229 增加对于年份一月份的判断，以及十二月份的判断
     *
     * @param year  格式yyyy-MM
     * @param month 格式yyyy-MM
     */
    public List<LibDateDayInMonthDetailInfoV2> getDayByMonth(String year, String month) throws Exception {
        String yearAndMonth = year + "-" + month;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(format.parse(yearAndMonth + "-01"));
        //该月有多少天
        int dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<LibDateDayInMonthDetailInfoV2> result = new ArrayList<>();
        for (int i = 1; i <= dayOfMonth; i++) {
            //判断1号是否为星期一，否则补充上个月数据
            calendar.setTime(format.parse(yearAndMonth + "-" + getFixString(i)));
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            Calendar monthCalendar = Calendar.getInstance();
            //上个月补充的日期
            if (startMode == 1) {
                //补充数据---------------1号不是星期一，则补充上个月最后那几天
                if (i == 1 && dayOfWeek != 1) {
                    //上个月
                    String[] lastMonth = getLastMonthWithDate(year, month, -1);
                    monthCalendar.setTime(format.parse(lastMonth[0] + "-" + lastMonth[1] + "-01"));
                    int lastMonthTotalDay = monthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    //1号不是星期一,补充前面的日期
                    int supplyCount = dayOfWeek - 1;
                    if (dayOfWeek == 0) {
                        supplyCount = 6;
                    }
                    for (int sup = 0; sup < supplyCount; sup++) {
                        LibDateDayInMonthDetailInfoV2 supCache = new LibDateDayInMonthDetailInfoV2();
                        supCache.setDataType(2);
                        supCache.setDayOfNum(getFixString(lastMonthTotalDay - sup));
                        monthCalendar.setTime(format.parse(lastMonth[0] + "-" + lastMonth[1] + "-" + (lastMonthTotalDay - sup)));
                        int lastDayOfWeek = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
                        supCache.setDayOfWeek(getFixString(lastDayOfWeek));
                        supCache.setYear(lastMonth[0]);
                        supCache.setMonth(lastMonth[1]);
                        supCache.setMonthDay(String.valueOf(supplyCount));
                        result.add(0, supCache);
                    }
                }
            } else if (startMode == 2) {
                //补充数据---------------1号不是星期日，则补充上个月最后那几天
                if (i == 1 && dayOfWeek != 0) {
                    //上个月
                    String[] lastMonth = getLastMonthWithDate(year, month, -1);
                    monthCalendar.setTime(format.parse(lastMonth[0] + "-" + lastMonth[1] + "-01"));
                    int lastMonthTotalDay = monthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    //1号不是星期日,补充前面的日期
                    int supplyCount = dayOfWeek;
                    for (int sup = 0; sup < supplyCount; sup++) {
                        LibDateDayInMonthDetailInfoV2 supCache = new LibDateDayInMonthDetailInfoV2();
                        supCache.setDataType(2);
                        supCache.setDayOfNum(getFixString(lastMonthTotalDay - sup));
                        monthCalendar.setTime(format.parse(lastMonth[0] + "-" + lastMonth[1] + "-" + (lastMonthTotalDay - sup)));
                        int lastDayOfWeek = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
                        supCache.setDayOfWeek(getFixString(lastDayOfWeek));
                        supCache.setYear(lastMonth[0]);
                        supCache.setMonth(lastMonth[1]);
                        supCache.setMonthDay(String.valueOf(supplyCount));
                        result.add(0, supCache);
                    }
                }
            }
            //正常数据---------------
            LibDateDayInMonthDetailInfoV2 normalCache = new LibDateDayInMonthDetailInfoV2();
            normalCache.setDataType(1);
            normalCache.setMonth(getFixString(Integer.parseInt(month)));
            normalCache.setMonthDay(String.valueOf(dayOfMonth));
            normalCache.setDayOfWeek(getFixString(dayOfWeek));
            normalCache.setDayOfNum(getFixString(i));
            normalCache.setYear(year);
            result.add(normalCache);
            if (startMode == 1) {
                //补充数据---------------最后一天并且不为星期天，补充
                if (i == dayOfMonth && dayOfWeek != 0) {
                    //上个月
                    String[] futureMonth = getLastMonthWithDate(year, month, 1);
                    monthCalendar.setTime(format.parse(futureMonth[0] + "-" + futureMonth[1] + "-01"));
                    int futureMonthTotalDay = monthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    //补充的天数
                    int futureCount = 7 - dayOfWeek;
                    for (int sup = 0; sup < futureCount; sup++) {
                        LibDateDayInMonthDetailInfoV2 supCache = new LibDateDayInMonthDetailInfoV2();
                        supCache.setDataType(3);
                        supCache.setDayOfNum(getFixString(sup + 1));
                        monthCalendar.setTime(format.parse(futureMonth[0] + "-" + futureMonth[1] + "-" + (sup + 1)));
                        int futureDayOfWeek = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
                        supCache.setDayOfWeek(getFixString(futureDayOfWeek));
                        supCache.setMonth(futureMonth[1]);
                        supCache.setMonthDay(String.valueOf(futureCount));
                        supCache.setYear(futureMonth[0]);
                        result.add(supCache);
                    }
                }
            } else if (startMode == 2) {
                //补充数据---------------最后一天并且不为星期一，补充
                if (i == dayOfMonth && dayOfWeek != 6) {
                    //上个月
                    String[] futureMonth = getLastMonthWithDate(year, month, 1);
                    monthCalendar.setTime(format.parse(futureMonth[0] + "-" + futureMonth[1] + "-01"));
                    int futureMonthTotalDay = monthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    //补充的天数
                    int futureCount = 6 - dayOfWeek;
                    for (int sup = 0; sup < futureCount; sup++) {
                        LibDateDayInMonthDetailInfoV2 supCache = new LibDateDayInMonthDetailInfoV2();
                        supCache.setDataType(3);
                        supCache.setDayOfNum(getFixString(sup + 1));
                        monthCalendar.setTime(format.parse(futureMonth[0] + "-" + futureMonth[1] + "-" + (sup + 1)));
                        int futureDayOfWeek = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
                        supCache.setDayOfWeek(getFixString(futureDayOfWeek));
                        supCache.setMonth(futureMonth[1]);
                        supCache.setMonthDay(String.valueOf(futureCount));
                        supCache.setYear(futureMonth[0]);
                        result.add(supCache);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获取日期的上一个月or下一个月
     *
     * @param type -1上一个月 1下一个月
     */
    private String[] getLastMonthWithDate(String year, String month, int type) throws Exception {
        int mm = Integer.parseInt(month);
        int yy = Integer.parseInt(year);
        if (type == -1) {
            if (mm == 1) {
                mm = 12;
                yy = yy - 1;
            } else {
                mm = mm - 1;
            }
        } else if (type == 1) {
            //下一个月
            if (mm == 12) {
                mm = 1;
                yy = yy + 1;
            } else {
                mm = mm + 1;
            }
        }
        String[] result = new String[2];
        result[0] = getFixString(yy);
        result[1] = getFixString(mm);
        return result;
    }


    /**
     * 获取两位数格式的字符串，不够则补充0
     */
    public String getFixString(int org) {
        String format = String.format("%02d", org);
        return format;
    }
}
