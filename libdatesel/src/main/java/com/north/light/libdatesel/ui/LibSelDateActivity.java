package com.north.light.libdatesel.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.north.light.libdatesel.DateMain;
import com.north.light.libdatesel.R;
import com.north.light.libdatesel.base.LibDateBaseActivity;
import com.north.light.libdatesel.bean.DateSelResult;
import com.north.light.libdatesel.widget.DivNumberPicker;

import java.util.ArrayList;
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

    private TextView mCancel;
    private TextView mConfirm;

    //数据集合__混合文字
    private List<String> mYearList = new ArrayList<>();
    private List<String> mMonthList = new ArrayList<>();
    private List<String> mDayList = new ArrayList<>();
    private List<String> mHourList = new ArrayList<>();
    private List<String> mMinuteList = new ArrayList<>();
    private List<String> mSecondList = new ArrayList<>();
    //纯数字
    private List<Integer> mYearNumList = new ArrayList<>();
    private List<Integer> mMonthNumList = new ArrayList<>();
    private List<Integer> mDayNumList = new ArrayList<>();
    private List<Integer> mHourNumList = new ArrayList<>();
    private List<Integer> mMinuteNumList = new ArrayList<>();
    private List<Integer> mSecondNumList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_sel_date);
        initView();
        initEvent();
    }

    private void initEvent() {
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据回调
                int yearPos = mYearPicker.getValue();
                int monthPos = mMonthPicker.getValue();
                int dayPos = mDayPicker.getValue();
                int hourPos = mHourPicker.getValue();
                int minutePos = mMinutePicker.getValue();
                int secondPos = mSecondPicker.getValue();
                DateSelResult selResult = new DateSelResult();
                //1年月日时分秒，2年月日时分 3年月日时，4年月日，5时分秒，6时分
                switch (mType) {
                    case 1:
                        selResult.setYear(mYearNumList.get(yearPos).toString());
                        selResult.setMonth(mMonthNumList.get(monthPos).toString());
                        selResult.setDay(mDayNumList.get(dayPos).toString());
                        selResult.setHour(mHourNumList.get(hourPos).toString());
                        selResult.setMinute(mMinuteNumList.get(minutePos).toString());
                        selResult.setSecond(mSecondNumList.get(secondPos).toString());
                        break;
                    case 2:
                        selResult.setYear(mYearNumList.get(yearPos).toString());
                        selResult.setMonth(mMonthNumList.get(monthPos).toString());
                        selResult.setDay(mDayNumList.get(dayPos).toString());
                        selResult.setHour(mHourNumList.get(hourPos).toString());
                        selResult.setMinute(mMinuteNumList.get(minutePos).toString());
                        break;
                    case 3:
                        selResult.setYear(mYearNumList.get(yearPos).toString());
                        selResult.setMonth(mMonthNumList.get(monthPos).toString());
                        selResult.setDay(mDayNumList.get(dayPos).toString());
                        break;
                    case 4:
                        selResult.setYear(mYearNumList.get(yearPos).toString());
                        selResult.setMonth(mMonthNumList.get(yearPos).toString());
                        selResult.setDay(mDayNumList.get(yearPos).toString());
                        break;
                    case 5:
                        selResult.setHour(mHourNumList.get(hourPos).toString());
                        selResult.setMinute(mMinuteNumList.get(minutePos).toString());
                        selResult.setSecond(mSecondNumList.get(secondPos).toString());
                    case 6:
                        selResult.setHour(mHourNumList.get(hourPos).toString());
                        selResult.setMinute(mMinuteNumList.get(minutePos).toString());
                        break;
                }
                DateMain.getInstance().onSelData(selResult);
                finish();
            }
        });

        //选择年会影响到日__选择年以后，重置月份，重置日
        mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mMonthPicker.setValue(0);
                setDayByMonth(mYearNumList.get(newVal), mMonthNumList.get(0));
            }
        });
        //选择月会影响到日
        mMonthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //设置日
                setDayByMonth(mYearNumList.get(mYearPicker.getValue()), mMonthNumList.get(newVal));
            }
        });
    }

    /**
     * 传入月份设置日期
     */
    private void setDayByMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        //注意减去1，是因为月份从0开始
        calendar.set(Calendar.MONTH, month - 1);
        int curDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//当前月份有多少天
        mDayList.clear();
        mDayNumList.clear();
        for (int i = 1; i <= curDay; i++) {
            mDayList.add(String.format("%02d", i) + "日");
            mDayNumList.add(i);
        }
        mDayPicker.setDisplayedValues(null);
        mDayPicker.setMaxValue(mDayList.size() - 1); //设置最大值
        mDayPicker.setDisplayedValues(mDayList.toArray(new String[mDayList.size()]));
        mDayPicker.setValue(0);
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
        mCancel = findViewById(R.id.activity_lib_sel_date_cancel);
        mConfirm = findViewById(R.id.activity_lib_sel_date_confirm);
        //根据type现实ui__1年月日时分秒，2年月日时分 3年月日时，4年月日，5时分秒，6时分
        if (mType == 2) {
            mSecondPicker.setVisibility(View.GONE);
        } else if (mType == 3) {
            mMinutePicker.setVisibility(View.GONE);
            mSecondPicker.setVisibility(View.GONE);
        } else if (mType == 4) {
            mHourPicker.setVisibility(View.GONE);
            mMinutePicker.setVisibility(View.GONE);
            mSecondPicker.setVisibility(View.GONE);
        } else if (mType == 5) {
            mYearPicker.setVisibility(View.GONE);
            mMonthPicker.setVisibility(View.GONE);
            mDayPicker.setVisibility(View.GONE);
        } else if (mType == 6) {
            mYearPicker.setVisibility(View.GONE);
            mMonthPicker.setVisibility(View.GONE);
            mDayPicker.setVisibility(View.GONE);
            mHourPicker.setVisibility(View.GONE);
        }

        //数据集合初始化
        //年
        Calendar startCal = Calendar.getInstance();
        startCal.setTimeInMillis(0);
        Calendar curCal = Calendar.getInstance();
        int startYear = startCal.get(Calendar.YEAR);
        int endYear = curCal.get(Calendar.YEAR);

        for (int i = endYear; i >= startYear; i--) {
            mYearList.add(i + "年");
            mYearNumList.add(i);
        }
        //月
        for (int i = 1; i <= 12; i++) {
            mMonthList.add(String.format("%02d", i) + "月");
            mMonthNumList.add(i);
        }
        //日
        int curDay = curCal.getActualMaximum(Calendar.DAY_OF_MONTH);//当前月份有多少天
        for (int i = 1; i <= curDay; i++) {
            mDayList.add(String.format("%02d", i) + "日");
            mDayNumList.add(i);
        }
        //时
        for (int i = 1; i <= 24; i++) {
            mHourList.add(String.format("%02d", i) + "时");
            mHourNumList.add(i);
        }
        //分
        for (int i = 1; i <= 60; i++) {
            mMinuteList.add(String.format("%02d", i) + "分");
            mMinuteNumList.add(i);
        }
        //秒
        for (int i = 1; i <= 60; i++) {
            mSecondList.add(String.format("%02d", i) + "秒");
            mSecondNumList.add(i);
        }
        //设置数据----------------------------------------------------------
        //获取当前初始化数据__月日时分秒
        int cMonth = curCal.get(Calendar.MONTH);//从0开始
        int cDay = curCal.get(Calendar.DATE) - 1;//日
        int cHour = curCal.get(Calendar.HOUR_OF_DAY) - 1;//时
        int cMinute = curCal.get(Calendar.MINUTE) - 1;//分
        int cSecond = curCal.get(Calendar.SECOND) - 1;//秒

        mYearPicker.setDisplayedValues(mYearList.toArray(new String[mYearList.size()]));
        mYearPicker.setMaxValue(mYearList.size() - 1); //设置最大值
        mYearPicker.setWrapSelectorWheel(true);
        mYearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止输入

        mMonthPicker.setDisplayedValues(mMonthList.toArray(new String[mMonthList.size()]));
        mMonthPicker.setMaxValue(mMonthList.size() - 1); //设置最大值
        mMonthPicker.setWrapSelectorWheel(true);
        mMonthPicker.setValue(cMonth);
        mMonthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止输入

        mDayPicker.setDisplayedValues(mDayList.toArray(new String[mDayList.size()]));
        mDayPicker.setMaxValue(mDayList.size() - 1); //设置最大值
        mDayPicker.setWrapSelectorWheel(true);
        mDayPicker.setValue(cDay);
        mDayPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止输入

        mHourPicker.setDisplayedValues(mHourList.toArray(new String[mHourList.size()]));
        mHourPicker.setMaxValue(mHourList.size() - 1); //设置最大值
        mHourPicker.setWrapSelectorWheel(true);
        mHourPicker.setValue(cHour);
        mHourPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止输入

        mMinutePicker.setDisplayedValues(mMinuteList.toArray(new String[mMinuteList.size()]));
        mMinutePicker.setMaxValue(mMinuteList.size() - 1); //设置最大值
        mMinutePicker.setWrapSelectorWheel(true);
        mMinutePicker.setValue(cMinute);
        mMinutePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止输入

        mSecondPicker.setDisplayedValues(mSecondList.toArray(new String[mSecondList.size()]));
        mSecondPicker.setMaxValue(mSecondList.size() - 1); //设置最大值
        mSecondPicker.setWrapSelectorWheel(true);
        mSecondPicker.setValue(cSecond);
        mSecondPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //禁止输入

    }


}
