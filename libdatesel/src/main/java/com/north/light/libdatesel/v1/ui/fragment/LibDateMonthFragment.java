package com.north.light.libdatesel.v1.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.north.light.libdatesel.R;
import com.north.light.libdatesel.v1.memory.LibDateMemoryInfo;
import com.north.light.libdatesel.v1.model.LibDateCalendarManager;
import com.north.light.libdatesel.v1.ui.fragment.detail.LibMonthDetailFragment;
import com.north.light.libdatesel.widget.LibDateViewPager;

import java.util.List;

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
    private LibDateViewPager mViewPager;
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
    /**
     * 左箭头
     */
    private ImageView mArrowLeft;
    /**
     * 右箭头
     */
    private ImageView mArrowRight;
    /**
     * 原始的年月
     */
    private String mOriYearMonth = "";
    /**
     * 滑动触发间距
     */
    private int mSlideInterval = 100;

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


    public LibDateViewPager getViewPager() {
        return mViewPager;
    }


    /**
     * 是否自动选中当前日期
     */
    public boolean autoSelCur() {
        return false;
    }

    /**
     * 获取当前的年份和月份
     */
    public String[] getCurrentData(int position) {
        //设置返回数据
        String[] result = new String[5];
        result[0] = selectYear;
        result[1] = LibDateCalendarManager.getInstance().getFixString(position + 1);
        result[2] = LibDateCalendarManager.getInstance().getFixString(Integer.parseInt((currentYear)));
        result[3] = LibDateCalendarManager.getInstance().getFixString(Integer.parseInt((currentMonth)));
        result[4] = LibDateCalendarManager.getInstance().getFixString(Integer.parseInt((currentDay)));
        return result;
    }

    private void initView() {
        try {
            if (autoSelCur()) {
                String year = LibDateCalendarManager.getInstance().getYear(0);
                String month = LibDateCalendarManager.getInstance().getMonth(0);
                String day = LibDateCalendarManager.getInstance().getDay(0);
                LibDateMemoryInfo.getInstance().setCurrentSelDate(year + month + day);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mYearTV = getRootView().findViewById(R.id.fragment_lib_date_month_title_year);
        mMonthTV = getRootView().findViewById(R.id.fragment_lib_date_month_title_month);
        mChangeYearLL = getRootView().findViewById(R.id.fragment_lib_date_month_title_change_year);
        mViewPager = getRootView().findViewById(R.id.fragment_lib_date_month_content);
        mArrowLeft = getRootView().findViewById(R.id.fragment_lib_date_month_title_arrow_left);
        mArrowRight = getRootView().findViewById(R.id.fragment_lib_date_month_title_arrow_right);
        switch (titleOrg()) {
            case 1:
                LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) mChangeYearLL.getLayoutParams();
                layoutParams1.gravity = (Gravity.CENTER | Gravity.START);
                mChangeYearLL.setLayoutParams(layoutParams1);
                mArrowLeft.setVisibility(View.GONE);
                mArrowRight.setVisibility(View.GONE);
                break;
            case 2:
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) mChangeYearLL.getLayoutParams();
                layoutParams2.gravity = (Gravity.CENTER);
                mChangeYearLL.setLayoutParams(layoutParams2);
                mArrowLeft.setVisibility(View.VISIBLE);
                mArrowRight.setVisibility(View.VISIBLE);
                break;
            case 3:
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) mChangeYearLL.getLayoutParams();
                layoutParams3.gravity = (Gravity.CENTER | Gravity.END);
                mChangeYearLL.setLayoutParams(layoutParams3);
                mArrowLeft.setVisibility(View.GONE);
                mArrowRight.setVisibility(View.GONE);
                break;
        }
        try {
            selectYear = LibDateMemoryInfo.getInstance().getCurrentYear();
            if (TextUtils.isEmpty(selectYear)) {
                selectYear = LibDateCalendarManager.getInstance().getYear(0);
                LibDateMemoryInfo.getInstance().setCurrentYear(selectYear);
            }
            selectMonth = LibDateMemoryInfo.getInstance().getCurrentMonth();
            if (TextUtils.isEmpty(selectMonth)) {
                selectMonth = LibDateCalendarManager.getInstance().getMonth(0);
                LibDateMemoryInfo.getInstance().setCurrentMonth(selectMonth);
            }
            currentYear = LibDateCalendarManager.getInstance().getYear(0);
            currentMonth = LibDateCalendarManager.getInstance().getMonth(0);
            currentDay = LibDateCalendarManager.getInstance().getDay(0);
            //初始化当前年月
            mOriYearMonth = currentYear + currentMonth;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        mViewPager.setOffscreenPageLimit(3);
        updateTime();
        //移动position到对应的月份
        mViewPager.setCurrentItem(Integer.parseInt(selectMonth) - 1, false);
    }

    private void initEvent() {
        mArrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //向左
                int itemPos = mViewPager.getCurrentItem();
                if (itemPos == 0) {
                    //左滑上一年
                    changeYear(2);
                    updateTime();
                } else {
                    mViewPager.setCurrentItem(itemPos - 1);
                }
            }
        });
        mArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPos = mViewPager.getCurrentItem();
                if (itemPos == 11) {
                    //右滑下一年
                    changeYear(1);
                    updateTime();
                } else {
                    mViewPager.setCurrentItem(itemPos + 1);
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //设置当前选择的月份
                LibDateMemoryInfo.getInstance().setCurrentMonth(LibDateCalendarManager.getInstance().getFixString((position + 1)));
                selectMonth = LibDateMemoryInfo.getInstance().getCurrentMonth();
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
                        mTouchSlideLeft = (mTouchXPos - event.getX() < 0) && (event.getX() - mTouchXPos) > mSlideInterval && (mViewPager.getCurrentItem() == 0);
                        mTouchSlideRight = (mTouchXPos - event.getX() > 0) && (mTouchXPos - event.getX()) > mSlideInterval && (mViewPager.getCurrentItem() == 11);
                        mTouchXPos = 0;
                        break;
                }
                return false;
            }
        });
        mChangeYearLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canChangeYear()) {
                    //切换到年份视图
                    changeContent(1);
                }
            }
        });
    }

    /**
     * 回到当前月份
     */
    public void gotoNow() {
        try {
            selectYear = LibDateCalendarManager.getInstance().getYear(0);
            LibDateMemoryInfo.getInstance().setCurrentYear(selectYear);
            selectMonth = LibDateCalendarManager.getInstance().getMonth(0);
            LibDateMemoryInfo.getInstance().setCurrentMonth(selectMonth);
            currentYear = LibDateCalendarManager.getInstance().getYear(0);
            currentMonth = LibDateCalendarManager.getInstance().getMonth(0);
            currentDay = LibDateCalendarManager.getInstance().getDay(0);
            mViewPager.setAdapter(null);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 切换上/下一年
     *
     * @param type 1下一年 2上一年
     */
    private void changeYear(int type) {
        if (type == 1) {
            //转换年份
            Integer selYear = Integer.valueOf(LibDateMemoryInfo.getInstance().getCurrentYear());
            selYear = selYear + 1;
            //更新缓存数据
            selectYear = String.valueOf(selYear);
            LibDateMemoryInfo.getInstance().setCurrentYear(String.valueOf(selYear));
            mViewPager.setCurrentItem(0, false);
        } else if (type == 2) {
            Integer selYear = Integer.valueOf(LibDateMemoryInfo.getInstance().getCurrentYear());
            selYear = selYear - 1;
            //更新缓存数据
            selectYear = String.valueOf(selYear);
            LibDateMemoryInfo.getInstance().setCurrentYear(String.valueOf(selYear));
            mViewPager.setCurrentItem(11, false);
        }
    }

    /**
     * 更新年份和月份
     */
    private void updateTime() {
        try {
            String year = LibDateMemoryInfo.getInstance().getCurrentYear().trim();
            String month = LibDateMemoryInfo.getInstance().getCurrentMonth().trim();
            mYearTV.setText(year + "年");
            mMonthTV.setText(month + "月");
            yearAndMonthUpdate(year, month, mYearTV, mMonthTV);
            //判断选择年月是否为当前年月
            if (!TextUtils.isEmpty(mOriYearMonth)) {
                String curYear = LibDateMemoryInfo.getInstance().getCurrentYear();
                String curMonth = LibDateMemoryInfo.getInstance().getCurrentMonth();
                if (!TextUtils.isEmpty(curYear) && !TextUtils.isEmpty(curMonth)) {
                    isCurrentDate(mOriYearMonth.equals(curYear + curMonth), curYear, curMonth);
                }
            }
        } catch (Exception e) {

        }
    }


    //子类继承扩展方法---------------------------------------------------------------------------------

    /**
     * 更新子fragment的事件
     */
    public void updateEvent(String yearMonthKey, List<String> info) {
        try {
            for (Fragment frag : getChildFragmentManager().getFragments()) {
                if (frag != null && frag instanceof LibMonthDetailFragment) {
                    ((LibMonthDetailFragment) frag).updateEvent(yearMonthKey, info);
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 设置滑动触发间距
     */
    public void setSlideInterval(int interval) {
        if (interval <= 0) {
            return;
        }
        this.mSlideInterval = interval;
    }

    /**
     * 页面是否当前年
     */
    public void isCurrentDate(boolean current, String year, String month) {

    }

    /**
     * 年月控件的方向
     * return 1中居左 2居中 3右居中
     */
    public int titleOrg() {
        return 1;
    }

    /**
     * 能否切换年月
     */
    public boolean canChangeYear() {
        return true;
    }

    /**
     * 年份和月份回调
     */
    public void yearAndMonthUpdate(String year, String month, TextView yearTV, TextView monthTV) {

    }


}
