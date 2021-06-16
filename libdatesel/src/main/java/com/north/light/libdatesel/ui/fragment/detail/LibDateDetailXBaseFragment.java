package com.north.light.libdatesel.ui.fragment.detail;

import com.north.light.libdatesel.base.LibDateBaseFragment;
import com.north.light.libdatesel.ui.LibSelDateXActivity;
import com.north.light.libdatesel.ui.fragment.LibDateMonthFragment;

/**
 * Created by lzt
 * time 2021/6/15 11:32
 *
 * @author lizhengting
 * 描述：基础详情日期选择库
 */
public abstract class LibDateDetailXBaseFragment extends LibDateBaseFragment {


    /**
     * 获取当前fragment的年份和月份
     */
    public String[] getYearAndMonth(int position) {
        try {
            return ((LibDateMonthFragment)getParentFragment()).getCurrentData(position);
        } catch (Exception e) {
            return new String[5];
        }
    }
}
