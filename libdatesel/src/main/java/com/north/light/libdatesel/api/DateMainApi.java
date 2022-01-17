package com.north.light.libdatesel.api;

import android.app.Activity;

import com.north.light.libdatesel.bean.LibDateSelResult;
import com.north.light.libdatesel.callback.DateSelInfoCallBack;

import java.util.List;

/**
 * @Author: lzt
 * @Date: 2022/1/7 8:08
 * @Description:
 */
public interface DateMainApi {

    /**
     * 回调的结果
     */
    public void onSelData(LibDateSelResult result);

    /**
     * 取消回调
     */
    public void cancel();


    /**
     * 启动地址选择
     *
     * @param type 1年月日时分秒，2年月日时分 3年月日时，4年月日，5时分秒，6时分，7年月
     */
    public void show(Activity activity, int type);

    /**
     * 启动地址选择
     *
     * @param type 1年月日时分秒，2年月日时分 3年月日时，4年月日，5时分秒，6时分，7年月，8年
     */
    public void showNormal(Activity activity, int type);

    /**
     * 启动新版日期选择视觉
     */
    public void showXSel(Activity activity);

    /**
     * 设置年月界面事件--string list :year+month
     */
    public void setEventDateList(String key, List<String> mEventDateList);

    public List<String> getEventDateList(String key);

    /**
     * 设置日期选择界面（X UI)的资源--若要生效，需要在设置页面前调用
     */
    public void setXRes(int currentTimeRes, int selRes, int unSelRes, int selTxRes, int unSelTxRes,
                        int noCurSelTxRes, int noCurUnSelTxRes, int todayDefault,
                        int curPointDefault, int curPointSel, int curPointUnSel);

    /**
     * 设置日期选择界面V2
     */
    public void setXRes(int titleColor,
                        int curTimeBg, int curTimeTx,
                        int selBg, int selTx,
                        int defBg, int defTx,
                        int eventCur, int eventSelCur, int eventDef);

    /**
     * 清空日期选择的数据--退出时使用
     */
    public void clearDateInfo();

    public void setOnDateListener(DateSelInfoCallBack mListener);

    public void removeDateListener(DateSelInfoCallBack mListener);

}
