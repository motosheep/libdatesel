package com.north.light.libdatesel.memory;

import com.north.light.libdatesel.bean.DayInMonthDetailInfo;
import com.north.light.libdatesel.bean.MonthInYearDetailInfo;
import com.north.light.libdatesel.model.CalendarManager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    /**
     * 年份缓存
     */
    private Map<String, Map<String, MonthInYearDetailInfo>> mYearMemory = new HashMap<>();
    /**
     * 月份缓存
     */
    private Map<String, List<DayInMonthDetailInfo>> mMonthMemory = new HashMap<>();

    private static class SingleHolder {
        static DateMemoryInfo mInstance = new DateMemoryInfo();
    }

    /**
     * 获取年份数据
     */
    public Map<String, MonthInYearDetailInfo> getYear(String year) throws Exception {
        if (mYearMemory.get(year) != null) {
            return mYearMemory.get(year);
        }
        Map<String, MonthInYearDetailInfo> info = CalendarManager.getInstance().getMonthByYear(year);
        mYearMemory.put(year, info);
        return info;
    }

    /**
     * 获取月份数据
     */
    public List<DayInMonthDetailInfo> getMonth(String year, String month) throws Exception {
        if (mMonthMemory.get(year + month) != null) {
            return mMonthMemory.get(year + month);
        }
        List<DayInMonthDetailInfo> info = CalendarManager.getInstance().getDayByMonth(year, month);
        mMonthMemory.put(year + month, info);
        return info;
    }

    public static DateMemoryInfo getInstance() {
        return SingleHolder.mInstance;
    }

    /**
     * 清空数据--控件退出时调用
     */
    public void clear() {
        setCurrentYear("");
        setCurrentMonth("");
        mYearMemory.clear();
        mMonthMemory.clear();
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
