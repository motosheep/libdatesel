package com.north.light.libdatesel.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.north.light.libdatesel.R;
import com.north.light.libdatesel.memory.DateMemoryInfo;
import com.north.light.libdatesel.model.CalendarManager;
import com.north.light.libdatesel.ui.fragment.detail.LibMonthDetailFragment;

/**
 * Created by lzt
 * time 2021/6/15 11:33
 *
 * @author lizhengting
 * 描述：月份fragment
 */
public class LibDateMonthFragment extends LibDateXBaseFragment {
    private final String TAG = LibDateMonthFragment.this.getClass().getSimpleName();
    /**
     * viewpager
     */
    private ViewPager mViewPager;
    /**
     * 当前的年份
     */
    private String currentYear = "";
    /**
     * 当前的月份
     */
    private String currentMonth = "";
    /**
     * 当前的日期
     */
    private String currentDay = "";
    /**
     * 选择的年份
     */
    private String selectYear = "";
    /**
     * 选择的月份
     */
    private String selectMonth = "";
    /**
     * viewpager移动时的缓存参数
     */
    private float mTouchXPos = 0;
    /**
     * viewpager是否左滑标识：true左滑
     */
    private boolean mTouchSlideLeft = false;
    /**
     * viewpager是否右滑标识：true右滑
     */
    private boolean mTouchSlideRight = false;
    /**
     * 年份显示控件
     */
    private TextView mYearTV;
    /**
     * 月份显示控件
     */
    private TextView mMonthTV;
    /**
     * 切换到年份显示的控件
     */
    private LinearLayout mChangeYearLL;

    public static LibDateMonthFragment newInstance() {
        Bundle bundle = new Bundle();
        LibDateMonthFragment fragment = new LibDateMonthFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lib_date_month;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
        initEvent();
    }

    /**
     * 获取当前的年份和月份
     */
    public String[] getCurrentData(int position) {
        //设置返回数据
        String[] result = new String[5];
        result[0] = selectYear;
        result[1] = CalendarManager.getInstance().getFixString(position + 1);
        result[2] = CalendarManager.getInstance().getFixString(Integer.parseInt((currentYear)));
        result[3] = CalendarManager.getInstance().getFixString(Integer.parseInt((currentMonth)));
        result[4] = CalendarManager.getInstance().getFixString(Integer.parseInt((currentDay)));
        return result;
    }

    private void initView() {
        mYearTV = getRootView().findViewById(R.id.fragment_lib_date_month_title_year);
        mMonthTV = getRootView().findViewById(R.id.fragment_lib_date_month_title_month);
        mChangeYearLL = getRootView().findViewById(R.id.fragment_lib_date_month_title_change_year);
        try {
            selectYear = DateMemoryInfo.getInstance().getCurrentYear();
            if (TextUtils.isEmpty(selectYear)) {
                selectYear = CalendarManager.getInstance().getYear(0);
                DateMemoryInfo.getInstance().setCurrentYear(selectYear);
            }
            selectMonth = DateMemoryInfo.getInstance().getCurrentMonth();
            if (TextUtils.isEmpty(selectMonth)) {
                selectMonth = CalendarManager.getInstance().getMonth(0);
                DateMemoryInfo.getInstance().setCurrentMonth(selectMonth);
            }
            currentYear = CalendarManager.getInstance().getYear(0);
            currentMonth = CalendarManager.getInstance().getMonth(0);
            currentDay = CalendarManager.getInstance().getDay(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mViewPager = getRootView().findViewById(R.id.fragment_lib_date_month_content);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return LibMonthDetailFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return 12;
            }
        });
        updateTime();
        //移动position到对应的月份
        mViewPager.setCurrentItem(Integer.parseInt(selectMonth) - 1, false);
    }

    private void initEvent() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //设置当前选择的月份
                DateMemoryInfo.getInstance().setCurrentMonth(CalendarManager.getInstance().getFixString((position + 1)));
                selectMonth = DateMemoryInfo.getInstance().getCurrentMonth();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 0) {
                    int itemPos = mViewPager.getCurrentItem();
                    if (itemPos == 0 && mTouchSlideLeft) {
                        //左滑上一年
                        changeYear(2);
                    } else if (itemPos == 11 && mTouchSlideRight) {
                        //右滑下一年
                        changeYear(1);
                    }
                    updateTime();
                }
            }
        });
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        if (mTouchXPos == 0) {
                            mTouchXPos = event.getX();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        mTouchSlideLeft = (mTouchXPos - event.getX() < 0) && (event.getX() - mTouchXPos) > 200 && (mViewPager.getCurrentItem() == 0);
                        mTouchSlideRight = (mTouchXPos - event.getX() > 0) && (mTouchXPos - event.getX()) > 200 && (mViewPager.getCurrentItem() == 11);
                        mTouchXPos = 0;
                        break;
                }
                return false;
            }
        });
        mChangeYearLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换到年份视图
                changeContent(1);
            }
        });
    }

    /**
     * 切换上/下一年
     *
     * @param type 1下一年 2上一年
     */
    private void changeYear(int type) {
        if (type == 1) {
            //转换年份
            Integer selYear = Integer.valueOf(DateMemoryInfo.getInstance().getCurrentYear());
            selYear = selYear + 1;
            //更新缓存数据
            selectYear = String.valueOf(selYear);
            DateMemoryInfo.getInstance().setCurrentYear(String.valueOf(selYear));
            mViewPager.setCurrentItem(0, false);
        } else if (type == 2) {
            Integer selYear = Integer.valueOf(DateMemoryInfo.getInstance().getCurrentYear());
            selYear = selYear - 1;
            //更新缓存数据
            selectYear = String.valueOf(selYear);
            DateMemoryInfo.getInstance().setCurrentYear(String.valueOf(selYear));
            mViewPager.setCurrentItem(11, false);
        }
    }

    /**
     * 更新年份和月份
     */
    private void updateTime() {
        mYearTV.setText(DateMemoryInfo.getInstance().getCurrentYear() + "年");
        mMonthTV.setText(DateMemoryInfo.getInstance().getCurrentMonth() + "月");
    }
}
