package com.north.light.libdatesel.v1.widget;

import java.io.Serializable;

/**
 * Created by lzt
 * time 2021/6/16 10:07
 *
 * @author lizhengting
 * 描述：自定义日历详情控件信息
 */
public class LibDateDivCalendarDetailInfo implements Serializable {
    /**
     * 年
     */
    private String year;
    /**
     * 月
     */
    private String month;
    /**
     * 日
     */
    private String day;
    /**
     * 是否为当前月：1是 0不是
     */
    private int currentMonth = 1;
    /**
     * 是否为当前日期--是否为当天：1是 0不是
     */
    public int currentDay = 0;
    /**
     * 是否选择：1是 0不是
     */
    public int currentSel = 0;
    /**
     * 今天是否有事情:0没有 1有
     */
    public int hadEvent = 0;

    public int getHadEvent() {
        return hadEvent;
    }

    public void setHadEvent(int hadEvent) {
        this.hadEvent = hadEvent;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public int getCurrentSel() {
        return currentSel;
    }

    public void setCurrentSel(int currentSel) {
        this.currentSel = currentSel;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
