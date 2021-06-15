package com.north.light.libdatesel.ui.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.north.light.libdatesel.R;
import com.north.light.libdatesel.bean.DayInMonthDetailInfo;
import com.north.light.libdatesel.bean.MonthInYearDetailInfo;
import com.north.light.libdatesel.model.CalendarManager;

import java.util.List;
import java.util.Map;

/**
 * Created by lzt
 * time 2021/6/15 11:33
 *
 * @author lizhengting
 * 描述：年份选择fragment
 */
public class LibDateYearFragment extends LibDateXBaseFragment {

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        try {
            Log.d("result", "时间1：" + System.currentTimeMillis());
            List<DayInMonthDetailInfo> info = CalendarManager.getInstance().getDayByMonth("2021","1");
            Log.d("result", "时间2：" + System.currentTimeMillis());
            Log.d("result", new Gson().toJson(info));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
