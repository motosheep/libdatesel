package com.north.light.libdatesel.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzt
 * time 2021/6/15 14:58
 *
 * @author lizhengting
 * 描述：某一年下月份详情信息
 */
public class MonthInYearDetailInfo implements Serializable {
    /**
     * 月份的天数
     */
    private String dayOfMonth;
    /**
     * 月份
     * */
    private String month;
    /**
     * 年份
     * */
    private String year;
    /**
     * 日期相关信息
     */
    private List<DayInfo> dayInfoList = new ArrayList<>();

    public static class DayInfo implements Serializable {
        /**
         * 天
         */
        private String day;
        /**
         * 星期几:0开始，星期天开始
         */
        private String dayOfWeek;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getDayOfWeek() {
            return dayOfWeek;
        }

        public void setDayOfWeek(String dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
        }
    }


    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public List<DayInfo> getDayInfoList() {
        return dayInfoList;
    }

    public void setDayInfoList(List<DayInfo> dayInfoList) {
        this.dayInfoList = dayInfoList;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
