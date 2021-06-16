package com.north.light.libdatesel.ui.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.north.light.libdatesel.R;
import com.north.light.libdatesel.bean.DayInMonthDetailInfo;
import com.north.light.libdatesel.model.CalendarManager;
import com.north.light.libdatesel.utils.CalendarTrainUtils;
import com.north.light.libdatesel.widget.DivCalendarDetailView;

import java.util.List;

/**
 * Created by lzt
 * time 2021/6/15 11:33
 *
 * @author lizhengting
 * 描述：月份fragment
 */
public class LibDateMonthFragment extends LibDateXBaseFragment {
    private DivCalendarDetailView mDetailView;

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
        initView();
    }

    private void initView() {
        try {
            mDetailView = getRootView().findViewById(R.id.fragment_lib_date_month_content);
            Log.d("result", "时间1：" + System.currentTimeMillis());
            List<DayInMonthDetailInfo> info = CalendarManager.getInstance().getDayByMonth("2021", "6");
            Log.d("result", "时间2：" + System.currentTimeMillis());
            Log.d("result", new Gson().toJson(info));
            String time = CalendarManager.getInstance().getYear(0) + CalendarManager.getInstance().getMonth(0) +
                    CalendarManager.getInstance().getDay(0);
            mDetailView.setData(CalendarTrainUtils.getMonthList(info), time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
