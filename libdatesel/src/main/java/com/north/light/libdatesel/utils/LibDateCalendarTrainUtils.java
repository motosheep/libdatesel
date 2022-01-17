package com.north.light.libdatesel.utils;

import com.north.light.libdatesel.v1.bean.LibDateDayInMonthDetailInfo;
import com.north.light.libdatesel.v1.bean.LibDateMonthInYearDetailInfo;
import com.north.light.libdatesel.v1.widget.LibDateDivCalendarDetailInfo;

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
public class LibDateCalendarTrainUtils implements Serializable {


    /**
     * 数据转换--月份
     */
    public static List<LibDateDivCalendarDetailInfo> getMonthList(List<LibDateDayInMonthDetailInfo> org) {
        if (org == null || org.size() == 0) {
            return new ArrayList<>();
        }
        List<LibDateDivCalendarDetailInfo> result = new ArrayList<>();
        for (LibDateDayInMonthDetailInfo cache : org) {
            LibDateDivCalendarDetailInfo info = new LibDateDivCalendarDetailInfo();
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
    public static List<LibDateMonthInYearDetailInfo> getYearList(Map<String, LibDateMonthInYearDetailInfo> map) {
        if (map == null) {
            return new ArrayList<>();
        }
        List<LibDateMonthInYearDetailInfo> result = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            result.add(map.get(String.valueOf((i + 1))));
        }
        return result;
    }
}
