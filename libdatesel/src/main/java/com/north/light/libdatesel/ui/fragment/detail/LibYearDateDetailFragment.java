package com.north.light.libdatesel.ui.fragment.detail;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.north.light.libdatesel.R;
import com.north.light.libdatesel.adapter.LibMonthInYearAdapter;
import com.north.light.libdatesel.bean.MonthInYearDetailInfo;
import com.north.light.libdatesel.memory.DateMemoryInfo;
import com.north.light.libdatesel.model.CalendarManager;
import com.north.light.libdatesel.ui.fragment.LibDateYearFragment;
import com.north.light.libdatesel.utils.CalendarTrainUtils;
import com.north.light.libdatesel.widget.LibMonthInYearDecoration;

import java.util.List;
import java.util.Map;

/**
 * Created by lzt
 * time 2021/6/17 9:42
 *
 * @author lizhengting
 * 描述：年份详情fragment
 */
public class LibYearDateDetailFragment extends LibDateDetailXBaseFragment {
    private final String TAG = getClass().getSimpleName();
    /**
     * bundle传参标识
     */
    private final static String DATA_POSITION = "DATA_POSITION";
    /**
     * 当前fragment的位置
     */
    private int mCurrentPos = 0;
    /**
     * info adapter
     */
    private LibMonthInYearAdapter mInfoAdapter;
    /**
     * info recycler view
     */
    private RecyclerView mInfoRecyclerView;


    /**
     * @param position 0-10
     */
    public static LibYearDateDetailFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(DATA_POSITION, position);
        LibYearDateDetailFragment fragment = new LibYearDateDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lib_date_detail_year;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
        initEvent();
    }

    private void initEvent() {
        mInfoAdapter.setOnClickListener(new LibMonthInYearAdapter.OnClickListener() {
            @Override
            public void changeToMonth(String year, String month) {
                DateMemoryInfo.getInstance().setCurrentYear(year);
                DateMemoryInfo.getInstance().setCurrentMonth(month);
                ((LibDateYearFragment) getParentFragment()).changeContent(2);
            }
        });
    }


    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            getData();
        }
    }


    private void initView() {
        mInfoAdapter = new LibMonthInYearAdapter();
        mInfoRecyclerView = getRootView().findViewById(R.id.fragment_lib_date_detail_year_content);
        mInfoRecyclerView.addItemDecoration(new LibMonthInYearDecoration(20, 20, 20));
        mInfoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mInfoRecyclerView.setAdapter(mInfoAdapter);
    }

    /**
     * 初始化数据
     */
    private void getData() {
        try {
            mCurrentPos = getArguments().getInt(DATA_POSITION, 0);
            String currentYear = getCurrentYear();
            Log.d(TAG, "数据获取时间1:" + System.currentTimeMillis());
            Map<String, MonthInYearDetailInfo> infoMap = CalendarManager.getInstance().getMonthByYear(currentYear);
            Log.d(TAG, "数据获取时间2:" + System.currentTimeMillis());
            List<MonthInYearDetailInfo> trainResult = CalendarTrainUtils.getYearList(infoMap);
            mInfoAdapter.setData(trainResult);
        } catch (Exception e) {
            Log.d(TAG, "数据获取错误:" + e.getMessage());
        }
    }
}