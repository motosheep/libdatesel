package com.north.light.libdatesel.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.north.light.libdatesel.R;
import com.north.light.libdatesel.base.LibDateBaseActivity;
import com.north.light.libdatesel.base.LibStatusBarUtils;
import com.north.light.libdatesel.ui.fragment.LibDateMonthFragment;
import com.north.light.libdatesel.ui.fragment.LibDateYearFragment;

/**
 * 多功能日期选择
 */
public class LibSelDateXActivity extends LibDateBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_sel_date_x);
        initView();
        initEvent();
    }

    private void initView() {
        LibStatusBarUtils.setStatusTextColor(this, true);
        int statusBarHeight = LibStatusBarUtils.getStatusBarHeight(this);
        Log.d(getClass().getSimpleName(), "状态栏高度" + statusBarHeight);
    }

    private void initEvent() {
        //加载年费fragment
        changeContent(1);
    }

    /**
     * 切换显示的fragment
     *
     * @param type 1年份 2月份
     */
    public void changeContent(int type) {
        switch (type) {
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_right_in,R.anim.slide_left_out,R.anim.slide_left_in,R.anim.slide_right_out)
                        .replace(R.id.activity_lib_sel_date_content, LibDateYearFragment.newInstance())
                        .commitAllowingStateLoss();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_right_in,R.anim.slide_left_out,R.anim.slide_left_in,R.anim.slide_right_out)
                        .replace(R.id.activity_lib_sel_date_content, LibDateMonthFragment.newInstance())
                        .commitAllowingStateLoss();
                break;
        }
    }
}
