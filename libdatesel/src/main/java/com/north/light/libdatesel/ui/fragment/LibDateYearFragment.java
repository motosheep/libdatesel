package com.north.light.libdatesel.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.north.light.libdatesel.R;
import com.north.light.libdatesel.bean.MonthInYearDetailInfo;
import com.north.light.libdatesel.memory.DateMemoryInfo;
import com.north.light.libdatesel.model.CalendarManager;

import java.util.Map;

/**
 * Created by lzt
 * time 2021/6/15 11:33
 *
 * @author lizhengting
 * 描述：年份选择fragment
 */
public class LibDateYearFragment extends LibDateXBaseFragment {
    /**
     * 切换到月份显示控件
     */
    private LinearLayout mChangeMonthLL;
    /**
     * 当前年份控件
     */
    private TextView mCurrentYearTV;

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
        mChangeMonthLL = getRootView().findViewById(R.id.fragment_lib_date_year_title_root);
        mCurrentYearTV = getRootView().findViewById(R.id.fragment_lib_date_year_title_year);
        try {
            updateUI();
            Log.d("result", "时间1：" + System.currentTimeMillis());
            Map<String, MonthInYearDetailInfo> info = CalendarManager.getInstance().getMonthByYear("2021");
            Log.d("result", "时间2：" + System.currentTimeMillis());
            Log.d("result", new Gson().toJson(info));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initEvent() {
        mChangeMonthLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换到月份
                changeContent(2);
            }
        });
    }

    /**
     * 更新视觉
     */
    private void updateUI() {
        String currentYear = DateMemoryInfo.getInstance().getCurrentYear();
        mCurrentYearTV.setText(currentYear + "年");
    }

}
