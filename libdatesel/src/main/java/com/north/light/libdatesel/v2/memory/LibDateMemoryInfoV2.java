package com.north.light.libdatesel.v2.memory;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * @Author: lzt
 * @Date: 2022/1/7 8:25
 * @Description:V2内存数据管理类
 */
public class LibDateMemoryInfoV2 implements Serializable {
    /**
     * 当前选中的日期
     */
    private String mCurYear = "";
    private String mCurMonth = "";
    private String mCurDay = "";
    /**
     * 当前滑动到的日期
     */
    private String mShowYear = "";
    private String mShowMonth = "";

    private static final class SingleHolder {
        private static final LibDateMemoryInfoV2 mInstance = new LibDateMemoryInfoV2();
    }

    public static LibDateMemoryInfoV2 getInstance() {
        return SingleHolder.mInstance;
    }

    /**
     * 清空数据
     */
    public void clear() {
        mCurYear = "";
        mCurMonth = "";
        mCurDay = "";
        mShowYear = "";
        mShowMonth = "";
        LibCalendarMonthViewV2Memory.getInstance().clearAll();
    }

    public String getCurDate() {
        if (TextUtils.isEmpty(mCurYear) || TextUtils.isEmpty(mCurMonth) || TextUtils.isEmpty(mCurDay)) {
            return "";
        }
        return mCurYear + mCurMonth + mCurDay;
    }

    public void setCurDate(String year, String month, String day) {
        this.mCurYear = year;
        this.mCurMonth = month;
        this.mCurDay = day;
    }

    public String[] getYMD() {
        String[] info = new String[3];
        info[0] = mCurYear;
        info[1] = mCurMonth;
        info[2] = mCurDay;
        return info;
    }

    public void setShowDate(String year, String month) {
        this.mShowYear = year;
        this.mShowMonth = month;
    }

    public String[] getShowYM() {
        String[] info = new String[2];
        info[0] = mShowYear;
        info[1] = mShowMonth;
        return info;
    }
}
