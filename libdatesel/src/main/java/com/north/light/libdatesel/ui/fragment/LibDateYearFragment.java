package com.north.light.libdatesel.ui.fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.north.light.libdatesel.ui.fragment.detail.LibYearDateDetailFragment;

/**
 * Created by lzt
 * time 2021/6/15 11:33
 *
 * @author lizhengting
 * 描述：年份选择fragment
 */
public class LibDateYearFragment extends LibDateXBaseFragment {
    private final String TAG = getClass().getSimpleName();
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
     * 切换到月份显示控件
     */
    private LinearLayout mChangeMonthLL;
    /**
     * 当前年份控件
     */
    private TextView mCurrentYearTV;
    /**
     * viewpager
     */
    private ViewPager mContentViewPager;
    /**
     * 当前的年份
     */
    private String mCurrentYear;
    /**
     * viewpager当前位置
     */
    private int mViewPagerCurPos = 1;

    public static LibDateYearFragment newInstance() {
        Bundle bundle = new Bundle();
        LibDateYearFragment fragment = new LibDateYearFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lib_date_year;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
        initEvent();
    }

    private void initView() {
        mCurrentYear = DateMemoryInfo.getInstance().getCurrentYear();
        mChangeMonthLL = getRootView().findViewById(R.id.fragment_lib_date_year_title_root);
        mCurrentYearTV = getRootView().findViewById(R.id.fragment_lib_date_year_title_year);
        mContentViewPager = getRootView().findViewById(R.id.fragment_lib_date_year_content);
        /**
         * viewpager里面有10个fragment.10年一个轮回
         * */
        mContentViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return LibYearDateDetailFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return 10;
            }
        });
    }


    private void initEvent() {
        mChangeMonthLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换到月份
                changeContent(2);
            }
        });
        mContentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 0) {
                    int itemPos = mContentViewPager.getCurrentItem();
                    if (itemPos == 0 && mTouchSlideLeft) {
                        mCurrentYear = String.valueOf(Integer.parseInt(mCurrentYear) - 1);
                        itemPos = culMovePos();
                        mViewPagerCurPos = itemPos;
                        mContentViewPager.setCurrentItem(itemPos, false);
                    } else if (itemPos == 9 && mTouchSlideRight) {
                        mCurrentYear = String.valueOf(Integer.parseInt(mCurrentYear) + 1);
                        itemPos = culMovePos();
                        mViewPagerCurPos = itemPos;
                        mContentViewPager.setCurrentItem(itemPos, false);
                    } else {
                        int interval = itemPos - mViewPagerCurPos;
                        mViewPagerCurPos = itemPos;
                        mCurrentYear = String.valueOf(Integer.parseInt(mCurrentYear) + interval);
                    }
                    updateUI();
                    Log.d(TAG, "state:" + state);
                }
            }
        });
        mContentViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        if (mTouchXPos == 0) {
                            mTouchXPos = event.getX();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        mTouchSlideLeft = (mTouchXPos - event.getX() < 0) && (event.getX() - mTouchXPos) > 100 && (mContentViewPager.getCurrentItem() == 0);
                        mTouchSlideRight = (mTouchXPos - event.getX() > 0) && (mTouchXPos - event.getX()) > 100 && (mContentViewPager.getCurrentItem() == 9);
                        mTouchXPos = 0;
                        break;
                }
                return false;
            }
        });
        //移动到当前年份的位置
        mViewPagerCurPos = culMovePos();
        mContentViewPager.setCurrentItem(mViewPagerCurPos, false);
        updateUI();
    }

    /**
     * 根据年份，计算出当前需要移动的位置
     */
    private int culMovePos() {
        try {
            int curYear = Integer.parseInt(mCurrentYear);
            return curYear % 10;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 更新视觉
     */
    private void updateUI() {
        mCurrentYearTV.setText(mCurrentYear + "年");
    }

    /**
     * 获取当前年份
     */
    public String getCurrentYear(int position) {
        if (position > mViewPagerCurPos) {
            return String.valueOf(Integer.parseInt(mCurrentYear) + 1);
        } else if (position < mViewPagerCurPos) {
            return String.valueOf(Integer.parseInt(mCurrentYear) - 1);
        }
        return mCurrentYear;
    }
}
