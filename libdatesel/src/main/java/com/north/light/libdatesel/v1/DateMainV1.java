package com.north.light.libdatesel.v1;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.north.light.libdatesel.R;
import com.north.light.libdatesel.api.DateMainApi;
import com.north.light.libdatesel.callback.DateSelInfoCallBack;
import com.north.light.libdatesel.bean.LibDateSelResult;
import com.north.light.libdatesel.v1.memory.LibDateMemoryInfo;
import com.north.light.libdatesel.v1.ui.LibSelDateActivity;
import com.north.light.libdatesel.v1.ui.LibSelDateNormalActivity;
import com.north.light.libdatesel.v1.ui.LibSelDateXActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by lzt
 * time 2020/6/22
 * 描述：时间管理外部调用类
 */
public class DateMainV1 implements DateMainApi {
    private CopyOnWriteArrayList<DateSelInfoCallBack> mListenerList = new CopyOnWriteArrayList<>();
    /**
     * 选中样式-----------------------------------------------------------------
     */
    private int mCurrentTimeColorRes = R.drawable.shape_date_sel_day_of_month_default_bg;
    //本月选中背景
    private int mSelTimeColorRes = R.drawable.shape_date_sel_day_of_month_sel_bg;
    //本月没有选中背景
    private int mUnSelTimeColorRes = R.drawable.shape_date_sel_day_of_month_alpha_bg;
    //非本月没有选中背景
    private int mUnSelTimeColorNoCurRes = R.drawable.shape_date_sel_day_of_month_alpha_bg;
    //本月文字
    private int mCurTxSelRes = R.color.color_99000000;
    private int mCurTxUnSelRes = R.color.color_99000000;
    //非本月文字
    private int mNoCurTxSelRes = R.color.color_4D000000;
    private int mNoCurTxUnSelRes = R.color.color_4D000000;
    //当前日期文字默认颜色
    private int mTodayTxDefault = R.color.color_99000000;
    //事件提示颜色
    private int mCurPointDefault = R.color.color_99000000;
    private int mCurPointSel = R.color.color_99000000;
    private int mCurPointUnSel = R.color.color_99000000;
    //有事件的日期
    private HashMap<String, List<String>> mEventDateList = new HashMap<>();

    private static final class SingleHolder {
        private static final DateMainV1 mInstance = new DateMainV1();
    }

    public static DateMainV1 getInstance() {
        return SingleHolder.mInstance;
    }

    /**
     * 回调的结果
     */
    @Override
    public void onSelData(LibDateSelResult result) {
        if (result != null && mListenerList.size() != 0) {
            for (DateSelInfoCallBack listener : mListenerList) {
                if (listener != null) {
                    listener.Date(result);
                    //把时间转换为时间戳
                    listener.timeStamp(tranTime(result));
                }
            }
        }
    }

    /**
     * 取消回调
     */
    @Override
    public void cancel() {
        for (DateSelInfoCallBack listener : mListenerList) {
            if (listener != null) {
                //把时间转换为时间戳
                listener.cancel();
            }
        }
    }


    /**
     * 启动地址选择
     *
     * @param type 1年月日时分秒，2年月日时分 3年月日时，4年月日，5时分秒，6时分，7年月
     */
    @Override
    public void show(Activity activity, int type) {
        Intent intent = new Intent(activity, LibSelDateActivity.class);
        intent.putExtra(LibSelDateActivity.CODE_REQUEST, type);
        activity.startActivity(intent);
    }

    /**
     * 启动地址选择
     *
     * @param type 1年月日时分秒，2年月日时分 3年月日时，4年月日，5时分秒，6时分，7年月，8年
     */
    @Override
    public void showNormal(Activity activity, int type) {
        Intent intent = new Intent(activity, LibSelDateNormalActivity.class);
        intent.putExtra(LibSelDateNormalActivity.CODE_REQUEST, type);
        activity.startActivity(intent);
    }

    /**
     * 启动新版日期选择视觉
     */
    @Override
    public void showXSel(Activity activity) {
        Intent intent = new Intent(activity, LibSelDateXActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 设置年月界面事件--string list :year+month
     */
    @Override
    public void setEventDateList(String key, List<String> mEventDateList) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        this.mEventDateList.put(key, mEventDateList);
    }

    @Override
    public List<String> getEventDateList(String key) {
        if (TextUtils.isEmpty(key)) {
            return new ArrayList<>();
        }
        return mEventDateList.get(key);
    }

    /**
     * 设置日期选择界面（X UI)的资源--若要生效，需要在设置页面前调用
     */
    @Override
    public void setXRes(int currentTimeRes, int selRes, int unSelRes, int selTxRes, int unSelTxRes,
                        int noCurSelTxRes, int noCurUnSelTxRes, int todayDefault,
                        int curPointDefault, int curPointSel, int curPointUnSel) {
        mCurrentTimeColorRes = currentTimeRes;
        mSelTimeColorRes = selRes;
        mUnSelTimeColorRes = unSelRes;
        mUnSelTimeColorNoCurRes = unSelRes;
        mCurTxSelRes = selTxRes;
        mCurTxUnSelRes = unSelTxRes;
        mNoCurTxSelRes = noCurSelTxRes;
        mNoCurTxUnSelRes = noCurUnSelTxRes;
        mTodayTxDefault = todayDefault;
        mCurPointDefault = curPointDefault;
        mCurPointSel = curPointSel;
        mCurPointUnSel = curPointUnSel;
    }

    @Override
    public void setXRes(int titleColor, int curTimeBg, int curTimeTx, int selBg, int selTx, int defBg, int defTx, int eventCur, int eventSelCur, int eventDef) {

    }

    /**
     * 清空日期选择的数据
     */
    @Override
    public void clearDateInfo() {
        LibDateMemoryInfo.getInstance().clear();
    }

    public int getCurPointDefault() {
        return mCurPointDefault;
    }

    public int getCurPointSel() {
        return mCurPointSel;
    }

    public int getCurPointUnSel() {
        return mCurPointUnSel;
    }

    public int getTodayTxDefault() {
        return mTodayTxDefault;
    }

    public int getCurrentTimeColorRes() {
        return mCurrentTimeColorRes;
    }

    public int getSelTimeColorRes() {
        return mSelTimeColorRes;
    }

    public int getUnSelTimeColorRes() {
        return mUnSelTimeColorRes;
    }

    public int getUnSelTimeColorNoCurRes() {
        return mUnSelTimeColorNoCurRes;
    }

    public int getCurTxSelRes() {
        return mCurTxSelRes;
    }

    public int getCurTxUnSelRes() {
        return mCurTxUnSelRes;
    }

    public int getNoCurTxSelRes() {
        return mNoCurTxSelRes;
    }

    public int getNoCurTxUnSelRes() {
        return mNoCurTxUnSelRes;
    }

    private Long tranTime(LibDateSelResult date) {
        if (date != null) {
            String time = date.getYear() + date.getMonth() + date.getDay() + date.getHour() + date.getMinute() + date.getSecond();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            try {
                //秒时间戳
                return dateFormat.parse(time).getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0L;
    }
    /**
     * 地址选择回调-------------------------------------------------------------------------------
     */

    @Override
    public void setOnDateListener(DateSelInfoCallBack mListener) {
        if (mListener == null) {
            return;
        }
        mListenerList.add(mListener);
    }

    @Override
    public void removeDateListener(DateSelInfoCallBack mListener) {
        if (mListener == null) {
            return;
        }
        mListenerList.remove(mListener);
    }


}
