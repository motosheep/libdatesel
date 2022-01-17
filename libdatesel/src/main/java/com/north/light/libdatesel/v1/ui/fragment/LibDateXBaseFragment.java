package com.north.light.libdatesel.v1.ui.fragment;

import com.north.light.libdatesel.base.LibDateBaseFragment;
import com.north.light.libdatesel.v1.ui.LibSelDateXActivity;

/**
 * Created by lzt
 * time 2021/6/15 11:32
 *
 * @author lizhengting
 * 描述：基础日期选择库
 */
public abstract class LibDateXBaseFragment extends LibDateBaseFragment {

    /**
     * 切换fragment
     *
     * @param type 1年份 2月份
     */
    public void changeContent(int type) {
        try {
            if (getActivity() instanceof LibSelDateXActivity) {
                ((LibSelDateXActivity) getActivity()).changeContent(type);
            }
        } catch (Exception e) {
        }
    }

}
