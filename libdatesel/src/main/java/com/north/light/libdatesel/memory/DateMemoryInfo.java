package com.north.light.libdatesel.memory;

import java.io.Serializable;

/**
 * Created by lzt
 * time 2021/6/16 16:03
 *
 * @author lizhengting
 * 描述：数据内存信息
 */
public class DateMemoryInfo implements Serializable {
    /**
     * 当前选择的年份
     */
    private String currentYear = "";
    /**
     * 当前选择的月份
     */
    private String currentMonth = "";

    private static class SingleHolder {
        static DateMemoryInfo mInstance = new DateMemoryInfo();
    }

    public static DateMemoryInfo getInstance() {
        return SingleHolder.mInstance;
    }

    /**
     * 清空数据--控件退出时调用
     * */
    public void clear(){
        setCurrentYear("");
        setCurrentMonth("");
    }

    public String getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }

    public String getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(String currentYear) {
        this.currentYear = currentYear;
    }
}
