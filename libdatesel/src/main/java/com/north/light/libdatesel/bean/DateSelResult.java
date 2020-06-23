package com.north.light.libdatesel.bean;

import java.io.Serializable;

/**
 * Created by lzt
 * time 2020/6/22
 * 描述：时间选择结果回调
 */
public class DateSelResult implements Serializable {
    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;
    private String second;

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

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }
}
