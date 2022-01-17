package com.north.light.libdatesel.v1.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.north.light.libdatesel.v1.DateMainV1;
import com.north.light.libdatesel.R;
import com.north.light.libdatesel.base.LibDateBaseActivity;
import com.north.light.libdatesel.bean.LibDateSelResult;
import com.north.light.libdatesel.widget.LibDateDateNumberPickerView;
import com.north.light.libdatesel.widget.LibDateDivNumberPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LibSelDateActivity extends LibDateBaseActivity {
    public static final String CODE_REQUEST = "CODE_REQUEST";
    //1年月日时分秒，2年月日时分 3年月日时，4年月日，5时分秒，6时分，7年月
    private int mType = 1;

    private LibDateDivNumberPicker mYearPicker;
    private LibDateDivNumberPicker mMonthPicker;
    private LibDateDivNumberPicker mDayPicker;
    private LibDateDivNumberPicker mHourPicker;
    private LibDateDivNumberPicker mMinutePicker;
    private LibDateDivNumberPicker mSecondPicker;

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
        try {
            initView();
            initEvent();
        } catch (Exception e) {
            Toast.makeText(LibSelDateActivity.this, "日期控件错误:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initEvent() throws Exception {
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateMainV1.getInstance().cancel();
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
                LibDateSelResult selResult = new LibDateSelResult();
                //1年月日时分秒，2年月日时分 3年月日时，4年月日，5时分秒，6时分，7年月
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
                        selResult.setHour(mHourNumList.get(hourPos).toString());
                        break;
                    case 4:
                        selResult.setYear(mYearNumList.get(yearPos).toString());
                        selResult.setMonth(mMonthNumList.get(monthPos).toString());
                        selResult.setDay(mDayNumList.get(dayPos).toString());
                        break;
                    case 5:
                        selResult.setHour(mHourNumList.get(hourPos).toString());
                        selResult.setMinute(mMinuteNumList.get(minutePos).toString());
                        selResult.setSecond(mSecondNumList.get(secondPos).toString());
                        break;
                    case 6:
                        selResult.setHour(mHourNumList.get(hourPos).toString());
                        selResult.setMinute(mMinuteNumList.get(minutePos).toString());
                        break;
                    case 7:
                        selResult.setYear(mYearNumList.get(yearPos).toString());
                        selResult.setMonth(mMonthNumList.get(monthPos).toString());
                        break;
                }
                DateMainV1.getInstance().onSelData(selResult);
                finish();
            }
        });

        //选择年会影响到日__选择年以后，重置月份，重置日
        mYearPicker.setOnValueChangedListener(new LibDateDateNumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(LibDateDateNumberPickerView picker, int oldVal, int newVal) {
                try {
                    if (Calendar.getInstance().get(Calendar.YEAR) == mYearNumList.get(newVal)) {
                        //当前年
                        resetMonth(2);
                        setDayByMonth(mYearNumList.get(newVal), mMonthNumList.get(0));
                    } else {
                        //非当前年，重置月份
                        resetMonth(1);
                        setDayByMonth(mYearNumList.get(newVal), mMonthNumList.get(0));
                    }
                } catch (Exception e) {
                    Toast.makeText(LibSelDateActivity.this, "日期控件错误:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        //选择月会影响到日
        mMonthPicker.setOnValueChangedListener(new LibDateDateNumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(LibDateDateNumberPickerView picker, int oldVal, int newVal) {
                //设置日
                try {
                    setDayByMonth(mYearNumList.get(mYearPicker.getValue()), mMonthNumList.get(newVal));
                } catch (Exception e) {
                    Toast.makeText(LibSelDateActivity.this, "日期控件错误:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    /**
     * 是否重置月份__非当前年重置月份
     *
     * @param type 1重置 2不重置
     */
    private void resetMonth(int type) {
        try {
            mMonthList.clear();
            mMonthNumList.clear();
            int startMonth;
            if (type == 1) {
                startMonth = 1;
            } else {
                startMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
            }
            for (int i = startMonth; i <= 12; i++) {
                mMonthList.add(String.format("%02d", i) + "月");
                mMonthNumList.add(i);
            }
            mMonthPicker.refreshByNewDisplayedValues(mMonthList.toArray(new String[mMonthList.size()]));
            mMonthPicker.setMaxValue(mMonthList.size() - 1); //设置最大值
            mMonthPicker.setValue(0);
        } catch (Exception e) {
            Toast.makeText(LibSelDateActivity.this, "日期控件错误:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * 传入月份设置日期
     */
    private void setDayByMonth(int year, int month) {
        try {
            Calendar calendar = Calendar.getInstance();
            int startMonth = calendar.get(Calendar.MONTH) + 1;
            calendar.set(Calendar.YEAR, year);
            //注意减去1，是因为月份从0开始
            calendar.set(Calendar.MONTH, month - 1);
            int curDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//当前月份有多少天
            mDayList.clear();
            mDayNumList.clear();
            if (startMonth == month) {
                //如果选中的是当前月份，则计算日期时需要注意
                int curDay2 = calendar.get(Calendar.DATE);//日
                for (int i = curDay2; i <= curDay; i++) {
                    mDayList.add(String.format("%02d", i) + "日");
                    mDayNumList.add(i);
                }
            } else {
                for (int i = 1; i <= curDay; i++) {
                    mDayList.add(String.format("%02d", i) + "日");
                    mDayNumList.add(i);
                }
            }
            mDayPicker.refreshByNewDisplayedValues(mDayList.toArray(new String[mDayList.size()]));
            mDayPicker.setMaxValue(mDayList.size() - 1); //设置最大值
            mDayPicker.setValue(0);
        } catch (Exception e) {
            Toast.makeText(LibSelDateActivity.this, "日期控件错误:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initView() throws Exception {
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
        } else if (mType == 7) {
            mDayPicker.setVisibility(View.GONE);
            mHourPicker.setVisibility(View.GONE);
            mMinutePicker.setVisibility(View.GONE);
            mSecondPicker.setVisibility(View.GONE);
        }

        //数据集合初始化
        //年
        Calendar startCal = Calendar.getInstance();
//        startCal.setTimeInMillis(0);
        Calendar curCal = Calendar.getInstance();
        int startYear = startCal.get(Calendar.YEAR);
        int endYear = curCal.get(Calendar.YEAR) + 1;

        for (int i = startYear; i <= endYear; i++) {
            mYearList.add(i + "年");
            mYearNumList.add(i);
        }
        //月
        int startMonth = curCal.get(Calendar.MONTH);
        for (int i = startMonth + 1; i <= 12; i++) {
            mMonthList.add(String.format("%02d", i) + "月");
            mMonthNumList.add(i);
        }
        //日
        int curDay = curCal.getActualMaximum(Calendar.DAY_OF_MONTH);//当前月份有多少天
        int curDay2 = curCal.get(Calendar.DATE);//日
        for (int i = curDay2; i <= curDay; i++) {
            mDayList.add(String.format("%02d", i) + "日");
            mDayNumList.add(i);
        }
        //时
        for (int i = 1; i <= 24; i++) {
            mHourList.add(String.format("%02d", i) + "时");
            mHourNumList.add(i);
        }
        //分
        for (int i = 0; i < 60; i++) {
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

        mYearPicker.refreshByNewDisplayedValues(mYearList.toArray(new String[mYearList.size()]));
        mYearPicker.setMaxValue(mYearList.size() - 1); //设置最大值
        mYearPicker.setWrapSelectorWheel(true);
        mYearPicker.setValue(0);

        mMonthPicker.refreshByNewDisplayedValues(mMonthList.toArray(new String[mMonthList.size()]));
        mMonthPicker.setMaxValue(mMonthList.size() - 1); //设置最大值
        mMonthPicker.setWrapSelectorWheel(true);
        mMonthPicker.setValue(Math.max(0, cMonth));

        mDayPicker.refreshByNewDisplayedValues(mDayList.toArray(new String[mDayList.size()]));
        mDayPicker.setMaxValue(mDayList.size() - 1); //设置最大值
        mDayPicker.setWrapSelectorWheel(true);
        mDayPicker.setValue(Math.max(0, cDay));

        mHourPicker.refreshByNewDisplayedValues(mHourList.toArray(new String[mHourList.size()]));
        mHourPicker.setMaxValue(mHourList.size() - 1); //设置最大值
        mHourPicker.setWrapSelectorWheel(true);
        mHourPicker.setValue(Math.max(0, cHour));

        mMinutePicker.refreshByNewDisplayedValues(mMinuteList.toArray(new String[mMinuteList.size()]));
        mMinutePicker.setMaxValue(mMinuteList.size() - 1); //设置最大值
        mMinutePicker.setWrapSelectorWheel(true);
        mMinutePicker.setValue(Math.max(0, cMinute));

        mSecondPicker.refreshByNewDisplayedValues(mSecondList.toArray(new String[mSecondList.size()]));
        mSecondPicker.setMaxValue(mSecondList.size() - 1); //设置最大值
        mSecondPicker.setWrapSelectorWheel(true);
        mSecondPicker.setValue(Math.max(0, cSecond));

    }


}
