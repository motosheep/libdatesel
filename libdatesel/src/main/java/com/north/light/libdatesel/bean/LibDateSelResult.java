package com.north.light.libdatesel.bean;

import java.io.Serializable;

/**
 * Created by lzt
 * time 2020/6/22
 * 描述：时间选择结果回调
 */
public class LibDateSelResult implements Serializable {
    private String year = "0000";
    private String month = "01";
    private String day = "01";
    private String hour = "00";
    private String minute = "00";
    private String second = "00";

    public String getYear() {
        return String.format("%04d", Integer.parseInt(year));
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return String.format("%02d", Integer.parseInt(month));
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return String.format("%02d", Integer.parseInt(day));
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return String.format("%02d", Integer.parseInt(hour));
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return String.format("%02d", Integer.parseInt(minute));
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getSecond() {
        return String.format("%02d", Integer.parseInt(second));
    }

    public void setSecond(String second) {
        this.second = second;
    }
}
