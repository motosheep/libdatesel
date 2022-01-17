package com.north.light.libdatesel.v1.ui.fragment.detail;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;

import com.north.light.libdatesel.v1.DateMainV1;
import com.north.light.libdatesel.R;
import com.north.light.libdatesel.v1.bean.LibDateDayInMonthDetailInfo;
import com.north.light.libdatesel.bean.LibDateSelResult;
import com.north.light.libdatesel.v1.memory.LibDateMemoryInfo;
import com.north.light.libdatesel.utils.LibDateCalendarTrainUtils;
import com.north.light.libdatesel.v1.widget.LibDateDivCalendarDetailInfo;
import com.north.light.libdatesel.v1.widget.LibDateDivCalendarDetailView;
import com.north.light.libdatesel.widget.LibDateViewPager;

import java.util.List;

/**
 * Created by lzt
 * time 2021/6/16 15:01
 *
 * @author lizhengting
 * 描述：月份详情fragment
 */
public class LibMonthDetailFragment extends LibDateDetailXBaseFragment {
    private final String TAG = getClass().getSimpleName();
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
    private LibDateDivCalendarDetailView mCalendarView;
    /**
     * 当前fragment年月事件key
     */
    private String mEventKey;

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
        mCalendarView.setCurrentTimeColorRes(DateMainV1.getInstance().getCurrentTimeColorRes());

        mCalendarView.setUnSelTimeColorRes(DateMainV1.getInstance().getUnSelTimeColorRes());
        mCalendarView.setSelTimeColorRes(DateMainV1.getInstance().getSelTimeColorRes());
        mCalendarView.setUnSelTimeColorNoCurRes(DateMainV1.getInstance().getUnSelTimeColorNoCurRes());

        mCalendarView.setCurTxSelRes(DateMainV1.getInstance().getCurTxSelRes());
        mCalendarView.setCurTxUnSelRes(DateMainV1.getInstance().getCurTxUnSelRes());
        mCalendarView.setNoCurTxSelRes(DateMainV1.getInstance().getNoCurTxSelRes());
        mCalendarView.setNoCurTxUnSelRes(DateMainV1.getInstance().getNoCurTxUnSelRes());

        mCalendarView.setCurTxDefaultRes(DateMainV1.getInstance().getTodayTxDefault());
        mCalendarView.setPointCurRes(DateMainV1.getInstance().getCurPointDefault());
        mCalendarView.setPointOtherSelRes(DateMainV1.getInstance().getCurPointSel());
        mCalendarView.setPointOtherUnSelRes(DateMainV1.getInstance().getCurPointUnSel());

        mCalendarView.setOnDateClickListener(new LibDateDivCalendarDetailView.OnClickListener() {
            @Override
            public void dayDetail(LibDateDivCalendarDetailInfo info) {
                //结束并且回调
                Log.d(TAG, "选择的日期：" + info.getYear() + info.getMonth() + info.getDay());
                LibDateSelResult result = new LibDateSelResult();
                result.setYear(info.getYear());
                result.setMonth(info.getMonth());
                result.setDay(info.getDay());
                DateMainV1.getInstance().onSelData(result);
            }

            @Override
            public void monthDetail(String year, String month) {

            }

            @Override
            public void widgetHeight(int height) {
                //设置viewpager高度
                setViewPagerHeight();
            }
        });
        //根据当前的位置，获取需要查询数据的年份和月份
        getData();
    }

    private void setViewPagerHeight() {
        try {
            if (!isFragmentVisible()) {
                return;
            }
            LibDateViewPager parentViewPager = getParentViewpager();
            int height = mCalendarView.getWidgetHeight();
            if (parentViewPager != null && height != 0) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) parentViewPager.getLayoutParams();
                parentViewPager.setAnimHeight(height, layoutParams);
            }
        } catch (Exception e) {
            Log.d(TAG, "setViewPagerHeight error：" + e.getMessage());
        }
    }

    /**
     * 获取数据
     * change by lzt 20210617增加缓存逻辑判断，如果存在，则直接return
     */
    private void getData() {
        try {
            String[] typeArray = getYearAndMonth(position);
            String year = typeArray[0];
            String month = typeArray[1];
            String currentYear = typeArray[2];
            String currentMonth = typeArray[3];
            String currentDay = typeArray[4];
            List<LibDateDayInMonthDetailInfo> info = LibDateMemoryInfo.getInstance().getMonth(year, month);
            List<LibDateDivCalendarDetailInfo> finalList = LibDateCalendarTrainUtils.getMonthList(info);
            mEventKey = year + month;
            List<String> eventList = DateMainV1.getInstance().getEventDateList(mEventKey);
            if (eventList != null && eventList.size() != 0) {
                for (LibDateDivCalendarDetailInfo cacheDate : finalList) {
                    for (String eventStr : eventList) {
                        String targetYM = cacheDate.getYear() + cacheDate.getMonth() + cacheDate.getDay();
                        if (!TextUtils.isEmpty(eventStr) && !TextUtils.isEmpty(targetYM)) {
                            if (eventStr.equals(targetYM)) {
                                cacheDate.setHadEvent(1);
                            }
                        }
                    }
                }
            }
            mCalendarView.setData(finalList, currentYear + currentMonth + currentDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 刷新当前月数据
     */
    public void updateEvent(String yearMonthKey, List<String> info) {
        if (TextUtils.isEmpty(mEventKey) || TextUtils.isEmpty(yearMonthKey) || info == null || info.size() == 0
                || !mEventKey.equals(yearMonthKey)) {
            return;
        }
        DateMainV1.getInstance().setEventDateList(mEventKey, info);
        if (!isFragmentVisible()) {
            return;
        }
        getData();
    }
}
