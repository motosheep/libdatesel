package com.north.light.libdatesel.v1.ui;

import android.os.Bundle;
import android.view.WindowManager;

import com.north.light.libdatesel.R;
import com.north.light.libdatesel.base.LibDateBaseActivity;
import com.north.light.libdatesel.base.LibDateStatusBarUtils;
import com.north.light.libdatesel.v1.memory.LibDateMemoryInfo;
import com.north.light.libdatesel.v1.ui.fragment.LibDateMonthFragment;
import com.north.light.libdatesel.v1.ui.fragment.LibDateYearFragment;

/**
 * 多功能日期选择
 * 年/月 -- 模式选择
 * change by lzt 20211229 周日开始or周一开始的模式
 */
public class LibSelDateXActivity extends LibDateBaseActivity {
    /**
     * 当前fragment的位置:1年份 2月份
     */
    private int mCurrentFragmentPos = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_sel_date_x);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.color_FFFFFF));
        LibDateStatusBarUtils.setStatusTextColor(this, true);
        initView();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        LibDateMemoryInfo.getInstance().clear();
        super.onDestroy();
    }

    private void initView() {

    }

    private void initEvent() {
        //加载年费fragment
        changeContent(2);
    }

    @Override
    public void onBackPressed() {
        if (mCurrentFragmentPos != 2) {
            changeContent(2);
            return;
        }
        super.onBackPressed();
    }

    /**
     * 切换显示的fragment
     *
     * @param type 1年份 2月份
     */
    public void changeContent(int type) {
        mCurrentFragmentPos = type;
        switch (type) {
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_lib_sel_date_content, LibDateYearFragment.newInstance())
                        .commitAllowingStateLoss();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.lib_date_scale_in, R.anim.lib_date_scale_out)
                        .replace(R.id.activity_lib_sel_date_content, LibDateMonthFragment.newInstance())
                        .commitAllowingStateLoss();
                break;
        }
    }
}
