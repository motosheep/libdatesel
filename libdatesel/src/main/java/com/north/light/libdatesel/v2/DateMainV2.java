package com.north.light.libdatesel.v2;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.north.light.libdatesel.R;
import com.north.light.libdatesel.api.DateMainApi;
import com.north.light.libdatesel.bean.LibDateSelResult;
import com.north.light.libdatesel.callback.DateSelInfoCallBack;
import com.north.light.libdatesel.v2.memory.LibDateMemoryInfoV2;
import com.north.light.libdatesel.v2.ui.LibDateNormalV2Activity;
import com.north.light.libdatesel.v2.ui.LibDateXV2Activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: lzt
 * @Date: 2022/1/7 8:12
 * @Description:
 */
public class DateMainV2 implements DateMainApi {
    private CopyOnWriteArrayList<DateSelInfoCallBack> mListenerList = new CopyOnWriteArrayList<>();
    /**
     * 选中样式-----------------------------------------------------------------
     */
    private int titleColor = R.color.color_99000000,
            curTimeBg = R.color.color_e63385FF,
            curTimeTx = R.color.color_FFFFFF,
            selBg = R.color.color_e63385FF,
            selTx = R.color.color_FFFFFF,
            defBg = R.color.color_FFFFFF,
            defTx = R.color.color_99000000,
            eventCur = R.color.color_99000000,
            eventSelCur = R.color.color_FFFFFF,
            eventDef = R.color.color_99000000;

    //有事件的日期
    private HashMap<String, List<String>> mEventDateList = new HashMap<>();

    //选择的年数正负100
    private int limit = 100;

    private static final class SingleHolder {
        private static final DateMainV2 mInstance = new DateMainV2();
    }

    public static DateMainV2 getInstance() {
        return SingleHolder.mInstance;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    /**
     * 时间转换
     */
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

    //impl------------------------------------------------------------------------------------------

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

    @Override
    public void cancel() {
        for (DateSelInfoCallBack listener : mListenerList) {
            if (listener != null) {
                //把时间转换为时间戳
                listener.cancel();
            }
        }
    }

    @Override
    public void show(Activity activity, int type) {
        showNormal(activity, type);
    }

    /**
     * 启动地址选择
     *
     * @param type 1年月日时分秒，2年月日时分 3年月日时，4年月日，5时分秒，6时分，7年月，8年
     */
    @Override
    public void showNormal(Activity activity, int type) {
        Intent intent = new Intent(activity, LibDateNormalV2Activity.class);
        intent.putExtra(LibDateNormalV2Activity.CODE_REQUEST, type);
        activity.startActivity(intent);
    }

    @Override
    public void showXSel(Activity activity) {
        Intent intent = new Intent(activity, LibDateXV2Activity.class);
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


    @Override
    public void setXRes(int currentTimeRes, int selRes, int unSelRes, int selTxRes, int unSelTxRes,
                        int noCurSelTxRes, int noCurUnSelTxRes, int todayDefault, int curPointDefault,
                        int curPointSel, int curPointUnSel) {

    }

    /**
     * 设置日期选择界面V2
     */
    @Override
    public void setXRes(int titleColor, int curTimeBg, int curTimeTx, int selBg, int selTx, int defBg,
                        int defTx, int eventCur, int eventSelCur, int eventDef) {
        this.titleColor = titleColor;
        this.curTimeBg = curTimeBg;
        this.curTimeTx = curTimeTx;
        this.selBg = selBg;
        this.selTx = selTx;
        this.defBg = defBg;
        this.defTx = defTx;
        this.eventCur = eventCur;
        this.eventSelCur = eventSelCur;
        this.eventDef = eventDef;
    }

    @Override
    public void clearDateInfo() {
        LibDateMemoryInfoV2.getInstance().clear();
    }

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

    public int getTitleColor() {
        return titleColor;
    }

    public int getCurTimeBg() {
        return curTimeBg;
    }

    public int getCurTimeTx() {
        return curTimeTx;
    }

    public int getSelBg() {
        return selBg;
    }

    public int getSelTx() {
        return selTx;
    }

    public int getDefBg() {
        return defBg;
    }

    public int getDefTx() {
        return defTx;
    }

    public int getEventCur() {
        return eventCur;
    }

    public int getEventSelCur() {
        return eventSelCur;
    }

    public int getEventDef() {
        return eventDef;
    }
}
