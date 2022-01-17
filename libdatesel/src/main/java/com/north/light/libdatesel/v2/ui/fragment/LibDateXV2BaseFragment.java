package com.north.light.libdatesel.v2.ui.fragment;

import com.north.light.libdatesel.base.LibDateBaseFragment;
import com.north.light.libdatesel.v2.ui.LibDateXV2Activity;

/**
 * @Author: lzt
 * @Date: 2022/1/8 14:56
 * @Description:年月选择v2 fragment
 */
public abstract class LibDateXV2BaseFragment extends LibDateBaseFragment {

    /**
     * 年份切换
     * 1月份
     */
    public void changeFragment(int type) {
        try {
            if (getActivity() instanceof LibDateXV2Activity) {
                ((LibDateXV2Activity) getActivity()).changeFragment(type);
            }
        } catch (Exception e) {

        }
    }
}
