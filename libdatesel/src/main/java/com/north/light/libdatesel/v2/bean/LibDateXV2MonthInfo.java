package com.north.light.libdatesel.v2.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lzt
 * @Date: 2022/1/7 18:55
 * @Description:
 */
public class LibDateXV2MonthInfo {
    private String year;
    private String month;
    private List<String> eventList = new ArrayList<>();

    public List<String> getEventList() {
        return eventList;
    }

    public void setEventList(List<String> eventList) {
        this.eventList = eventList;
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
}
