package com.north.light.libdatesel.v2.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.north.light.libdatesel.R;
import com.north.light.libdatesel.bean.LibDateSelResult;
import com.north.light.libdatesel.v2.DateMainV2;
import com.north.light.libdatesel.v2.adapter.LibDateXV2MonthAdapter;
import com.north.light.libdatesel.v2.bean.LibDateDayInMonthDetailInfoV2;
import com.north.light.libdatesel.v2.bean.LibDateXV2MonthInfo;
import com.north.light.libdatesel.v2.memory.LibDateMemoryInfoV2;
import com.north.light.libdatesel.v2.model.LibDateCalendarManagerV2;
import com.north.light.libdatesel.v2.widget.LibCalendarRecyV2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: lzt
 * @Date: 2022/1/7 18:40
 * @Description:年月选择--月份fragment
 */
public class LibDateXV2MonthFragment extends LibDateXV2BaseFragment {
    private static final String TAG = LibDateXV2MonthFragment.class.getSimpleName();
    private LibCalendarRecyV2 mRecyclerView;
    private LibDateXV2MonthAdapter mInfoAdapter;

    private ImageView mLeftIV;
    private ImageView mRightTV;
    private TextView mYearTV;
    private TextView mMonthTV;
    private LinearLayout mInfoRoot;

    /**
     * 当前recyclerview的位置
     */
    private AtomicInteger mCurrentPos = new AtomicInteger(0);
    private String cacheYM = "";
    /**
     * 是否拖拽中
     */
    private AtomicBoolean mIsDagger = new AtomicBoolean(false);


    public static LibDateXV2MonthFragment newInstance() {
        Bundle bundle = new Bundle();
        LibDateXV2MonthFragment fragment = new LibDateXV2MonthFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lib_date_x_v2_month;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
        initEvent();
    }

    private void initEvent() {
        mLeftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //左滑
                int realPos = Math.max(0, mCurrentPos.decrementAndGet());
                mRecyclerView.smoothScrollToPosition(realPos);
            }
        });
        mRightTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //右滑
                int realPos = Math.min(mInfoAdapter.getItemCount(), mCurrentPos.incrementAndGet());
                mRecyclerView.smoothScrollToPosition(realPos);
            }
        });
        mInfoRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示年份布局
                changeFragment(2);
            }
        });
        mRecyclerView.setOnRecyTouchListener(new LibCalendarRecyV2.OnRecyTouchListener() {
            @Override
            public void stop(RecyclerView recyclerView, int newState, int org) {
                mIsDagger.set(false);
                try {
                    if (recyclerView.getChildCount() > 0) {
                        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                        if (layoutManager instanceof LinearLayoutManager) {
                            int currentPosition = 0;
                            if (org == 1) {
                                currentPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                            } else {
                                currentPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                            }
                            Log.d(TAG, "pos:" + currentPosition);
                            //更新当前月份
                            mCurrentPos.set(currentPosition);
                            updateYMByPos(currentPosition);
                            setRecyHeight();
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void run(RecyclerView recyclerView, int newState, int org) {
                mIsDagger.set(true);
            }
        });
        mInfoAdapter.setOnClickListener(new LibDateXV2MonthAdapter.OnClickListener() {
            @Override
            public void date(LibDateDayInMonthDetailInfoV2 info) {
                if (info != null) {
                    LibDateMemoryInfoV2.getInstance().setCurDate(info.getYear(), info.getMonth(), info.getDayOfNum());
                    //子类回调
                    updateSelDate(info.getYear(), info.getMonth(), info.getDayOfNum());
                    //数据回调
                    LibDateSelResult result = new LibDateSelResult();
                    result.setYear(info.getYear());
                    result.setMonth(info.getMonth());
                    result.setDay(info.getDayOfNum());
                    DateMainV2.getInstance().onSelData(result);
                }
            }

            @Override
            public void year(String year, String month) {

            }

            @Override
            public void heightChange() {
                setRecyHeight();
            }
        });
    }

    /**
     * 设置高度
     */
    private synchronized void setRecyHeight() {
        if (mIsDagger.get()) {
            return;
        }
        int height = mInfoAdapter.getHeightWithPos(mCurrentPos.intValue());
        if (height <= 0) {
            return;
        }
        LibDateXV2MonthFragment.this.measureHeight(height);
    }

    /**
     * 根据位置更新年月信息
     */
    private void updateYMByPos(int currentPosition) {
        try {
            LibDateXV2MonthInfo info = mInfoAdapter.getInfoByPos(currentPosition);
            if (info != null) {
                LibDateMemoryInfoV2.getInstance().setShowDate(info.getYear(), info.getMonth());
                mYearTV.setText(info.getYear() + ".");
                mMonthTV.setText(info.getMonth());
                //选择年月回调
                updateYM(info.getYear(), info.getMonth());
            }
        } catch (Exception e) {
            Log.d(TAG, "updateYMByPos:" + e.getMessage());
        }
    }

    /**
     * 根据文字更新年月信息
     */
    private void updateYMByYM(String year, String month) {
        try {
            LibDateMemoryInfoV2.getInstance().setShowDate(year, month);
            mYearTV.setText(year + ".");
            mMonthTV.setText(month);
            //选择年月回调
            updateYM(year, month);
        } catch (Exception e) {
            Log.d(TAG, "updateYMByYM:" + e.getMessage());
        }
    }

    private void initView() {
        mInfoRoot = getRootView().findViewById(R.id.fragment_lib_date_x_v2_month_info_root);
        mLeftIV = getRootView().findViewById(R.id.fragment_lib_date_x_v2_month_arrow_left);
        mRightTV = getRootView().findViewById(R.id.fragment_lib_date_x_v2_month_arrow_right);
        mYearTV = getRootView().findViewById(R.id.fragment_lib_date_x_v2_month_year);
        mMonthTV = getRootView().findViewById(R.id.fragment_lib_date_x_v2_month_month);
        //初始化当前日期为选中
        mRecyclerView = getRootView().findViewById(R.id.fragment_lib_date_x_v2_month_content);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        mInfoAdapter = new LibDateXV2MonthAdapter(getContext(), 1);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mInfoAdapter);
        //初始化数据
        try {
            long startTime = System.currentTimeMillis();
            List<LibDateXV2MonthInfo> allList = new ArrayList<>();
            String[] ymdArray = LibDateMemoryInfoV2.getInstance().getYMD();
            String cYear = ymdArray[0];
            if (TextUtils.isEmpty(cYear)) {
                cYear = LibDateCalendarManagerV2.getInstance().getYear(0);
            }
            String cMonth = ymdArray[1];
            if (TextUtils.isEmpty(cMonth)) {
                cMonth = LibDateCalendarManagerV2.getInstance().getMonth(0);
            }
            String cDay = ymdArray[2];
            if (TextUtils.isEmpty(cDay)) {
                cDay = LibDateCalendarManagerV2.getInstance().getDay(0);
            }
            LibDateMemoryInfoV2.getInstance().setCurDate(cYear, cMonth, cDay);
            //获取前后一百年
            int curYear = Integer.parseInt(cYear);
            int limit = DateMainV2.getInstance().getLimit();
            for (int i = curYear - limit; i <= curYear + limit; i++) {
                for (int j = 1; j <= 12; j++) {
                    String year = LibDateCalendarManagerV2.getInstance().getFixString(i);
                    String month = LibDateCalendarManagerV2.getInstance().getFixString(j);
                    LibDateXV2MonthInfo re = new LibDateXV2MonthInfo();
                    re.setYear(year);
                    re.setMonth(month);
                    allList.add(allList.size(), re);
                }
            }
            Log.d(TAG, "数据获取时间:" + (System.currentTimeMillis() - startTime));
            //设置到adapter
            mInfoAdapter.setData(allList);
            //跳转到当前年份
            mCurrentPos.set(mInfoAdapter.gotoCurrentDate(cYear, cMonth));
            mRecyclerView.scrollToPosition(mCurrentPos.intValue());
            updateYMByYM(cYear, cMonth);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateYM(String year, String month) {
        try {
            if (TextUtils.isEmpty(cacheYM)) {
                cacheYM = year + month;
                updateYearAndMonth(year, month);
            } else {
                if (cacheYM.equals(year + month)) {
                    return;
                }
                cacheYM = year + month;
                updateYearAndMonth(year, month);
            }
        } catch (Exception e) {

        }
    }

    //子类继承---------------------------------------------------------------------------------------
    public void updateYearAndMonth(String year, String month) {

    }

    public void updateSelDate(String year, String month, String day) {

    }

    public void setEvent(String year, String month, List<String> eventList) {
        if (mInfoAdapter != null) {
            mInfoAdapter.updateEvent(year, month, eventList);
        }
    }

    public void measureHeight(int height) {
        try {
            if (getRecyclerView() != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getRecyclerView().getLayoutParams();
                if (layoutParams.height == height) {
                    return;
                }
                layoutParams.height = height;
                getRecyclerView().setLayoutParams(layoutParams);
            }
        } catch (Exception e) {

        }
    }
}
