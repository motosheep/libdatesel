package com.north.light.libdatesel.ui;

import android.os.Bundle;
import android.widget.NumberPicker;

import com.north.light.libdatesel.R;
import com.north.light.libdatesel.widget.DivNumberPicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class LibSelDateActivity extends LibDateBaseActivity {
    public static final String CODE_REQUEST = "CODE_REQUEST";
    //1年月日时分秒，2年月日时分 3年月日时，4年月日，5时分秒，6时分
    private int mType = 1;

    private DivNumberPicker mYearPicker;
    private DivNumberPicker mMonthPicker;
    private DivNumberPicker mDayPicker;
    private DivNumberPicker mHourPicker;
    private DivNumberPicker mMinutePicker;
    private DivNumberPicker mSecondPicker;

    //数据集合
    private List<String> mYearList = new ArrayList<>();
    private List<String> mMonthList = new ArrayList<>();
    private List<String> mDayList = new ArrayList<>();
    private List<String> mHourList = new ArrayList<>();
    private List<String> mMinuteList = new ArrayList<>();
    private List<String> mSecondList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_sel_date);
        initView();
        initEvent();
    }

    private void initEvent() {

    }

    private void initView() {
        mType = getIntent().getIntExtra(CODE_REQUEST, 1);
        //控件初始化
        mYearPicker = findViewById(R.id.activity_lib_sel_date_content_year);
        mMonthPicker = findViewById(R.id.activity_lib_sel_date_content_month);
        mDayPicker = findViewById(R.id.activity_lib_sel_date_content_day);
        mHourPicker = findViewById(R.id.activity_lib_sel_date_content_hour);
        mMinutePicker = findViewById(R.id.activity_lib_sel_date_content_minute);
        mSecondPicker = findViewById(R.id.activity_lib_sel_date_content_second);
        //数据集合初始化
        //年
        Calendar startCal = Calendar.getInstance();
        startCal.setTimeInMillis(0);
        Calendar curCal = Calendar.getInstance();
        int startYear = startCal.get(Calendar.YEAR);
        int endYear = curCal.get(Calendar.YEAR);
        for (int i = endYear; i >= startYear; i--) {
            mYearList.add(i + "");
        }
        //月
        for (int i = 1; i <= 12; i++) {
            mMonthList.add(i + "");
        }
        //日__默认每个月31日，后续选择的时候动态改变
        for (int i = 1; i <= 31; i++) {
            mDayList.add(i + "");
        }
        //时
        for (int i = 1; i <= 24; i++) {
            mHourList.add(i + "");
        }
        //分
        for (int i = 1; i <= 60; i++) {
            mMinuteList.add(i + "");
        }
        //秒
        for (int i = 1; i <= 60; i++) {
            mSecondList.add(i + "");
        }
        //设置数据----------------------------------------------------------
        mYearPicker.setDisplayedValues(mYearList.toArray(new String[mYearList.size()]));
        mYearPicker.setMaxValue(mYearList.size() - 1); //设置最大值，最大值是datas[3]
        mYearPicker.setWrapSelectorWheel(true);
        mYearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止输入

        mMonthPicker.setDisplayedValues(mMonthList.toArray(new String[mMonthList.size()]));
        mMonthPicker.setMaxValue(mMonthList.size() - 1); //设置最大值
        mMonthPicker.setWrapSelectorWheel(true);
        mMonthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止输入

        mDayPicker.setDisplayedValues(mDayList.toArray(new String[mDayList.size()]));
        mDayPicker.setMaxValue(mDayList.size() - 1); //设置最大值
        mDayPicker.setWrapSelectorWheel(true);
        mDayPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止输入

        mHourPicker.setDisplayedValues(mHourList.toArray(new String[mHourList.size()]));
        mHourPicker.setMaxValue(mHourList.size() - 1); //设置最大值
        mHourPicker.setWrapSelectorWheel(true);
        mHourPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止输入

        mMinutePicker.setDisplayedValues(mMinuteList.toArray(new String[mMinuteList.size()]));
        mMinutePicker.setMaxValue(mMinuteList.size() - 1); //设置最大值
        mMinutePicker.setWrapSelectorWheel(true);
        mMinutePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止输入

        mSecondPicker.setDisplayedValues(mSecondList.toArray(new String[mSecondList.size()]));
        mSecondPicker.setMaxValue(mSecondList.size() - 1); //设置最大值
        mSecondPicker.setWrapSelectorWheel(true);
        mSecondPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止输入

    }


}
