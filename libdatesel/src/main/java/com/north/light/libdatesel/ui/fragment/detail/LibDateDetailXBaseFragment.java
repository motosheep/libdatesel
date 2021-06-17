package com.north.light.libdatesel.ui.fragment.detail;

import com.north.light.libdatesel.base.LibDateBaseFragment;
import com.north.light.libdatesel.ui.fragment.LibDateMonthFragment;
import com.north.light.libdatesel.ui.fragment.LibDateYearFragment;

/**
 * Created by lzt
 * time 2021/6/15 11:32
 *
 * @author lizhengting
 * 描述：基础详情日期选择库
 */
public abstract class LibDateDetailXBaseFragment extends LibDateBaseFragment {


    /**
     * 获取当前fragment的年份和月份--用于月份详情
     */
    public String[] getYearAndMonth(int position) {
        try {
            return ((LibDateMonthFragment) getParentFragment()).getCurrentData(position);
        } catch (Exception e) {
            return new String[5];
        }
    }

    /**
     * 获取当前年份
     */
    public String getCurrentYear(int position) {
        try {
            return ((LibDateYearFragment) getParentFragment()).getCurrentYear(position);
        } catch (Exception e) {
            return "";
        }
    }

}
