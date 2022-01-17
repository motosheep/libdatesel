package com.north.light.libdatesel.callback;

import com.north.light.libdatesel.bean.LibDateSelResult;

/**
 * @Author: lzt
 * @Date: 2022/1/7 8:07
 * @Description:数据回调
 */
public interface DateSelInfoCallBack {
    void Date(LibDateSelResult result);

    void timeStamp(Long time);

    void cancel();

}
