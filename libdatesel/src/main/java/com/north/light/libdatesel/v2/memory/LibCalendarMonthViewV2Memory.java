package com.north.light.libdatesel.v2.memory;

import android.text.TextUtils;

import com.north.light.libdatesel.v2.widget.LibDrawTxInfo;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @Author: lzt
 * @Date: 2022/1/10 18:12
 * @Description:
 */
public class LibCalendarMonthViewV2Memory implements Serializable {
    private HashMap<String, HashMap<String, LibDrawTxInfo>> mTxInfoMap = new HashMap<>();
    private HashMap<String, Integer> mTxHeightMap = new HashMap<>();

    private static final class SingleHolder {
        private static final LibCalendarMonthViewV2Memory mInstance = new LibCalendarMonthViewV2Memory();
    }

    public static LibCalendarMonthViewV2Memory getInstance() {
        return SingleHolder.mInstance;
    }

    /**
     * 清空所有数据
     */
    public void clearAll() {
        mTxInfoMap.clear();
        mTxHeightMap.clear();
    }

    //设置日历信息--------------------------------------------------------------------------------------------

    /**
     * 设置日历文字缓存
     */
    public void setTXYMCache(String year, String month, String key, LibDrawTxInfo info) {
        if (TextUtils.isEmpty(year) || TextUtils.isEmpty(month) || TextUtils.isEmpty(key) || info == null) {
            return;
        }
        String parentKey = year + month;
        HashMap<String, LibDrawTxInfo> parentValue = mTxInfoMap.get(parentKey);
        if (parentValue == null) {
            parentValue = new HashMap<>();
        }
        parentValue.put(key, info);
        mTxInfoMap.put(year + month, parentValue);
    }

    /**
     * 清空日历文字缓存
     */
    public void clearTXYMCache(String year, String month) {
        if (TextUtils.isEmpty(year) || TextUtils.isEmpty(month)) {
            return;
        }
        mTxInfoMap.remove(year + month);
    }

    /**
     * 获取日历文字信息
     */
    public LibDrawTxInfo getTXYMCache(String year, String month, String key) {
        if (TextUtils.isEmpty(year) || TextUtils.isEmpty(month) || TextUtils.isEmpty(key)) {
            return null;
        }
        String parentKey = year + month;
        HashMap<String, LibDrawTxInfo> parentValue = mTxInfoMap.get(parentKey);
        if (parentValue == null) {
            return null;
        }
        return parentValue.get(key);
    }

    //日历高度------------------------------------------------------------------------------------------

    /**
     * 获取控件高度
     */
    public Integer getTXHeight(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        return  mTxHeightMap.get(key);
    }

    /**
     * 设置控件高度
     */
    public void setTxHeight(String key, int height) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        mTxHeightMap.put(key, height);
    }
}
