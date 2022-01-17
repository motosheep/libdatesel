package com.north.light.libdatesel.v1.bean;

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
public class LibDateMonthInYearDetailInfo implements Serializable {
    /**
     * 月份的天数
     */
    private String dayOfMonth;
    /**
     * 月份
     */
    private String month;
    /**
     * 年份
     */
    private String year;
    /**
     * 日期相关信息
     */
    private List<LibDateDayInMonthDetailInfo> dayInfoList = new ArrayList<>();

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public List<LibDateDayInMonthDetailInfo> getDayInfoList() {
        return dayInfoList;
    }

    public void setDayInfoList(List<LibDateDayInMonthDetailInfo> dayInfoList) {
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
