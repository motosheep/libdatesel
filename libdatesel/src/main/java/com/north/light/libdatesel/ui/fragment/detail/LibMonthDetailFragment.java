package com.north.light.libdatesel.ui.fragment.detail;

import android.os.Bundle;

import com.north.light.libdatesel.R;
import com.north.light.libdatesel.bean.DayInMonthDetailInfo;
import com.north.light.libdatesel.model.CalendarManager;
import com.north.light.libdatesel.utils.CalendarTrainUtils;
import com.north.light.libdatesel.widget.DivCalendarDetailView;

import java.util.List;

/**
 * Created by lzt
 * time 2021/6/16 15:01
 *
 * @author lizhengting
 * 描述：月份详情fragment
 */
public class LibMonthDetailFragment extends LibDateDetailXBaseFragment {
    /**
     * 当前fragment的月份位置
     */
    private int position = 0;
    /**
     * bundle传参标识
     */
    private final static String DATA_POSITION = "DATA_POSITION";
    /**
     * 日历显示控件
     */
    private DivCalendarDetailView mCalendarView;

    /**
     * @param position 月份所在一年中的位置
     */
    public static LibMonthDetailFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(DATA_POSITION, position);
        LibMonthDetailFragment fragment = new LibMonthDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lib_date_detail_month;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            initData();
        } else {
            release();
        }
    }

    @Override
    public void onDestroyView() {
        release();
        super.onDestroyView();
    }

    /**
     * 释放数据
     */
    private void release() {
        try {
            mCalendarView.clearSelectStatus();
        } catch (Exception e) {

        }
    }

    private void initData() {
        mCalendarView = getRootView().findViewById(R.id.fragment_lib_date_detail_month);
        try {
            position = getArguments().getInt(DATA_POSITION, 1);
        } catch (Exception e) {
            position = 1;
        }
        //根据当前的位置，获取需要查询数据的年份和月份
        getData();
    }

    /**
     * 获取数据
     */
    private void getData() {
        try {
            String[] typeArray = getYearAndMonth(position);
            String year = typeArray[0];
            String month = typeArray[1];
            String currentYear = typeArray[2];
            String currentMonth = typeArray[3];
            String currentDay = typeArray[4];
            List<DayInMonthDetailInfo> info = CalendarManager.getInstance().getDayByMonth(year, month);
            mCalendarView.setData(CalendarTrainUtils.getMonthList(info), currentYear + currentMonth + currentDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}