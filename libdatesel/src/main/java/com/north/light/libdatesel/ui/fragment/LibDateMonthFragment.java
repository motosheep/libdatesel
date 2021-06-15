package com.north.light.libdatesel.ui.fragment;

import android.os.Bundle;

import com.north.light.libdatesel.R;

/**
 * Created by lzt
 * time 2021/6/15 11:33
 *
 * @author lizhengting
 * 描述：月份fragment
 */
public class LibDateMonthFragment extends LibDateXBaseFragment {

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
}
