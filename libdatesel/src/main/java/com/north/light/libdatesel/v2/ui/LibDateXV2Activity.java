package com.north.light.libdatesel.v2.ui;

import android.os.Bundle;

import com.north.light.libdatesel.R;
import com.north.light.libdatesel.base.LibDateBaseActivity;
import com.north.light.libdatesel.v2.DateMainV2;
import com.north.light.libdatesel.v2.ui.fragment.LibDateXV2MonthFragment;


/**
 * 选择年月页面
 */
public class LibDateXV2Activity extends LibDateBaseActivity {
    /**
     * 当前type
     */
    private int mType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_date_x_v2);
        initView();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DateMainV2.getInstance().clearDateInfo();
    }

    private void initView() {

    }

    private void initEvent() {
        changeFragment(1);
    }

    @Override
    public void onBackPressed() {
        if (mType != 1) {
            changeFragment(1);
            return;
        }
        super.onBackPressed();
    }

    /**
     * 设置fragment
     */
    public void changeFragment(int type) {
        this.mType = type;
        if (type == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_lib_date_x_v2_content,
                    LibDateXV2MonthFragment.newInstance()).commitAllowingStateLoss();
        }
    }
}