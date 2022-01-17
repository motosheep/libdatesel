package com.north.light.libdatesel.v1.memory;

import com.north.light.libdatesel.v1.bean.LibDateDayInMonthDetailInfo;
import com.north.light.libdatesel.v1.bean.LibDateMonthInYearDetailInfo;
import com.north.light.libdatesel.v1.model.LibDateCalendarManager;

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
public class LibDateMemoryInfo implements Serializable {
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
    private Map<String, Map<String, LibDateMonthInYearDetailInfo>> mYearMemory = new HashMap<>();
    /**
     * 月份缓存
     */
    private Map<String, List<LibDateDayInMonthDetailInfo>> mMonthMemory = new HashMap<>();
    /**
     * 当前手动选中的日期
     * */
    private String currentSelDate = "";

    private static class SingleHolder {
        static LibDateMemoryInfo mInstance = new LibDateMemoryInfo();
    }

    /**
     * 获取年份数据
     */
    public Map<String, LibDateMonthInYearDetailInfo> getYear(String year) throws Exception {
        if (mYearMemory.get(year) != null) {
            return mYearMemory.get(year);
        }
        Map<String, LibDateMonthInYearDetailInfo> info = LibDateCalendarManager.getInstance().getMonthByYear(year);
        mYearMemory.put(year, info);
        return info;
    }

    /**
     * 获取月份数据
     */
    public List<LibDateDayInMonthDetailInfo> getMonth(String year, String month) throws Exception {
        if (mMonthMemory.get(year + month) != null) {
            return mMonthMemory.get(year + month);
        }
        List<LibDateDayInMonthDetailInfo> info = LibDateCalendarManager.getInstance().getDayByMonth(year, month);
        mMonthMemory.put(year + month, info);
        return info;
    }

    public static LibDateMemoryInfo getInstance() {
        return SingleHolder.mInstance;
    }

    /**
     * 清空数据--控件退出时调用
     */
    public void clear() {
        setCurrentYear("");
        setCurrentMonth("");
        setCurrentSelDate("");
        mYearMemory.clear();
        mMonthMemory.clear();
    }

    public String getCurrentSelDate() {
        return currentSelDate;
    }

    public void setCurrentSelDate(String currentSelDate) {
        this.currentSelDate = currentSelDate;
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
