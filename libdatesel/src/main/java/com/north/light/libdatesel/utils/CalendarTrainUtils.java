package com.north.light.libdatesel.utils;

import com.north.light.libdatesel.bean.DayInMonthDetailInfo;
import com.north.light.libdatesel.bean.MonthInYearDetailInfo;
import com.north.light.libdatesel.widget.DivCalendarDetailInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lzt
 * time 2021/6/16 10:34
 *
 * @author lizhengting
 * 描述：日历数据转换工具类
 */
public class CalendarTrainUtils implements Serializable {


    /**
     * 数据转换--月份
     */
    public static List<DivCalendarDetailInfo> getMonthList(List<DayInMonthDetailInfo> org) {
        if (org == null || org.size() == 0) {
            return new ArrayList<>();
        }
        List<DivCalendarDetailInfo> result = new ArrayList<>();
        for (DayInMonthDetailInfo cache : org) {
            DivCalendarDetailInfo info = new DivCalendarDetailInfo();
            info.setDay(cache.getDayOfNum());
            info.setMonth(cache.getMonth());
            info.setYear(cache.getYear());
            switch (cache.getDataType()) {
                case 1:
                    info.setCurrentMonth(1);
                    break;
                default:
                    info.setCurrentMonth(0);
                    break;
            }
            result.add(info);
        }
        return result;
    }

    /**
     * 数据转换--年份
     */
    public static List<MonthInYearDetailInfo> getYearList(Map<String, MonthInYearDetailInfo> map) {
        if (map == null) {
            return new ArrayList<>();
        }
        List<MonthInYearDetailInfo> result = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            result.add(map.get(String.valueOf((i + 1))));
        }
        return result;
    }
}
