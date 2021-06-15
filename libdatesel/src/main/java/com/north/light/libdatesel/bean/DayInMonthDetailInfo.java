package com.north.light.libdatesel.bean;

import java.io.Serializable;

/**
 * Created by lzt
 * time 2021/6/15 16:04
 *
 * @author lizhengting
 * 描述：月份中，日期的详细信息
 */
public class DayInMonthDetailInfo implements Serializable {

    /**
     * 星期几：0为星期天
     */
    private String dayOfWeek;
    /**
     * 号数
     */
    private String dayOfNum;
    /**
     * 月份
     */
    private String month;
    /**
     * 当月天数/补充天数
     */
    private String monthDay;
    /**
     * 数据类型：1当月数据 2上个月补充的数据 3下个月补充的数据
     */
    private int dataType = 1;

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDayOfNum() {
        return dayOfNum;
    }

    public void setDayOfNum(String dayOfNum) {
        this.dayOfNum = dayOfNum;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(String monthDay) {
        this.monthDay = monthDay;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}
