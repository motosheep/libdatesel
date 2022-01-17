package com.north.light.libdatesel.v1.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.north.light.libdatesel.v1.DateMainV1;
import com.north.light.libdatesel.R;
import com.north.light.libdatesel.bean.LibDateSelResult;
import com.north.light.libdatesel.widget.LibDateDateNumberPickerView;
import com.north.light.libdatesel.widget.LibDateDivNumberPicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: lzt
 * @Date: 2021/9/12 11:28
 * @Description:通用日期选择fragment
 */
public class LibSelDataNormalFragment extends Fragment {
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
    public RelativeLayout mHeadLayout;
    public LinearLayout mContentLayout;

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

    /**
     * 根布局
     */
    private View mRootView;

    public static LibSelDataNormalFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(CODE_REQUEST, type);
        LibSelDataNormalFragment instance = new LibSelDataNormalFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_lib_sel_date, container, false);
        }
        return mRootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化控件
        initView();
        initEvent();
    }

    private void initEvent() {
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateMainV1.getInstance().onSelData(getCurSelTime());
                requireActivity().finish();
            }
        });

        //选择年会影响到日__选择年以后，重置月份，重置日
        mYearPicker.setOnValueChangedListener(new LibDateDateNumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(LibDateDateNumberPickerView picker, int oldVal, int newVal) {
                mMonthPicker.setValue(0);
                setDayByMonth(mYearNumList.get(newVal), mMonthNumList.get(0));
            }
        });
        //选择月会影响到日
        mMonthPicker.setOnValueChangedListener(new LibDateDateNumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(LibDateDateNumberPickerView picker, int oldVal, int newVal) {
                //设置日
                setDayByMonth(mYearNumList.get(mYearPicker.getValue()), mMonthNumList.get(newVal));
            }
        });
    }

    /**
     * 获取当前时间
     */
    public LibDateSelResult getCurSelTime() {
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
            case 6:
                selResult.setHour(mHourNumList.get(hourPos).toString());
                selResult.setMinute(mMinuteNumList.get(minutePos).toString());
                break;
            case 7:
                selResult.setYear(mYearNumList.get(yearPos).toString());
                selResult.setMonth(mMonthNumList.get(monthPos).toString());
                break;
        }
        return selResult;
    }

    /**
     * 获取当前时间戳
     */
    public Long getCurSelTimeStamp() {
        LibDateSelResult date = getCurSelTime();
        if (date != null) {
            String time = date.getYear() + date.getMonth() + date.getDay() + date.getHour() + date.getMinute() + date.getSecond();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            try {
                //秒时间戳
                Date date1 = dateFormat.parse(time);
                return date1.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0L;
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
        mDayPicker.refreshByNewDisplayedValues(mDayList.toArray(new String[mDayList.size()]));
        mDayPicker.setMaxValue(mDayList.size() - 1); //设置最大值
        mDayPicker.setValue(0);
    }

    private void initView() {
        mType = getArguments().getInt(CODE_REQUEST, 1);
        //控件初始化
        mHeadLayout = mRootView.findViewById(R.id.fragment_lib_sel_date_title);
        mContentLayout = mRootView.findViewById(R.id.fragment_lib_sel_date_content);
        mYearPicker = mRootView.findViewById(R.id.fragment_lib_sel_date_content_year);
        mMonthPicker = mRootView.findViewById(R.id.fragment_lib_sel_date_content_month);
        mDayPicker = mRootView.findViewById(R.id.fragment_lib_sel_date_content_day);
        mHourPicker = mRootView.findViewById(R.id.fragment_lib_sel_date_content_hour);
        mMinutePicker = mRootView.findViewById(R.id.fragment_lib_sel_date_content_minute);
        mSecondPicker = mRootView.findViewById(R.id.fragment_lib_sel_date_content_second);
        mCancel = mRootView.findViewById(R.id.fragment_lib_sel_date_cancel);
        mConfirm = mRootView.findViewById(R.id.fragment_lib_sel_date_confirm);

        mYearPicker.setShownCount(pickShowCount());
        mMonthPicker.setShownCount(pickShowCount());
        mDayPicker.setShownCount(pickShowCount());
        mHourPicker.setShownCount(pickShowCount());
        mMinutePicker.setShownCount(pickShowCount());
        mSecondPicker.setShownCount(pickShowCount());

        mYearPicker.setDividerColor(getResources().getColor(dividerColor()));
        mMonthPicker.setDividerColor(getResources().getColor(dividerColor()));
        mDayPicker.setDividerColor(getResources().getColor(dividerColor()));
        mHourPicker.setDividerColor(getResources().getColor(dividerColor()));
        mMinutePicker.setDividerColor(getResources().getColor(dividerColor()));
        mSecondPicker.setDividerColor(getResources().getColor(dividerColor()));


        mYearPicker.setNormalTextColor(getResources().getColor(normalTxColor()));
        mMonthPicker.setNormalTextColor(getResources().getColor(normalTxColor()));
        mDayPicker.setNormalTextColor(getResources().getColor(normalTxColor()));
        mHourPicker.setNormalTextColor(getResources().getColor(normalTxColor()));
        mMinutePicker.setNormalTextColor(getResources().getColor(normalTxColor()));
        mSecondPicker.setNormalTextColor(getResources().getColor(normalTxColor()));


        mYearPicker.setSelectedTextColor(getResources().getColor(selectorTxColor()));
        mMonthPicker.setSelectedTextColor(getResources().getColor(selectorTxColor()));
        mDayPicker.setSelectedTextColor(getResources().getColor(selectorTxColor()));
        mHourPicker.setSelectedTextColor(getResources().getColor(selectorTxColor()));
        mMinutePicker.setSelectedTextColor(getResources().getColor(selectorTxColor()));
        mSecondPicker.setSelectedTextColor(getResources().getColor(selectorTxColor()));

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

        mYearPicker.refreshByNewDisplayedValues(mYearList.toArray(new String[mYearList.size()]));
        mYearPicker.setMaxValue(mYearList.size() - 1); //设置最大值
        mYearPicker.setWrapSelectorWheel(true);

        mMonthPicker.refreshByNewDisplayedValues(mMonthList.toArray(new String[mMonthList.size()]));
        mMonthPicker.setMaxValue(mMonthList.size() - 1); //设置最大值
        mMonthPicker.setWrapSelectorWheel(true);
        mMonthPicker.setValue(cMonth);

        mDayPicker.refreshByNewDisplayedValues(mDayList.toArray(new String[mDayList.size()]));
        mDayPicker.setMaxValue(mDayList.size() - 1); //设置最大值
        mDayPicker.setWrapSelectorWheel(true);
        mDayPicker.setValue(cDay);

        mHourPicker.refreshByNewDisplayedValues(mHourList.toArray(new String[mHourList.size()]));
        mHourPicker.setMaxValue(mHourList.size() - 1); //设置最大值
        mHourPicker.setWrapSelectorWheel(true);
        mHourPicker.setValue(cHour);

        mMinutePicker.refreshByNewDisplayedValues(mMinuteList.toArray(new String[mMinuteList.size()]));
        mMinutePicker.setMaxValue(mMinuteList.size() - 1); //设置最大值
        mMinutePicker.setWrapSelectorWheel(true);
        mMinutePicker.setValue(cMinute);

        mSecondPicker.refreshByNewDisplayedValues(mSecondList.toArray(new String[mSecondList.size()]));
        mSecondPicker.setMaxValue(mSecondList.size() - 1); //设置最大值
        mSecondPicker.setWrapSelectorWheel(true);
        mSecondPicker.setValue(cSecond);

    }

    /**
     * picker显示条目行数
     */
    public int pickShowCount() {
        return 3;
    }

    /**
     * 分割线颜色
     */
    public int dividerColor() {
        return R.color.color_FFFFFF;
    }

    /**
     * 选中颜色
     */
    public int selectorTxColor() {
        return R.color.color_3385FF;
    }

    /**
     * 普通颜色
     */
    public int normalTxColor() {
        return R.color.color_000000;
    }
}
