package com.north.light.libdatesel;

import android.app.Activity;
import android.content.Intent;

import com.north.light.libdatesel.bean.DateSelResult;
import com.north.light.libdatesel.ui.LibSelDateActivity;
import com.north.light.libdatesel.ui.LibSelDateNormalActivity;

import java.text.SimpleDateFormat;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by lzt
 * time 2020/6/22
 * 描述：时间管理外部调用类
 */
public class DateMain {
    private CopyOnWriteArrayList<DateSelInfoCallBack> mListenerList = new CopyOnWriteArrayList<>();


    private static final class SingleHolder {
        private static final DateMain mInstance = new DateMain();
    }

    public static DateMain getInstance() {
        return SingleHolder.mInstance;
    }

    /**
     * 回调的结果
     */
    public void onSelData(DateSelResult result) {
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
     * */
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
    public void showNormal(Activity activity, int type) {
        Intent intent = new Intent(activity, LibSelDateNormalActivity.class);
        intent.putExtra(LibSelDateNormalActivity.CODE_REQUEST, type);
        activity.startActivity(intent);
    }


    /**
     * 地址选择回调-------------------------------------------------------------------------------
     */
    private Long tranTime(DateSelResult date) {
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

    public interface DateSelInfoCallBack {
        void Date(DateSelResult result);

        void timeStamp(Long time);

        void cancel();
    }

    public void setOnDateListener(DateSelInfoCallBack mListener) {
        if (mListener == null) {
            return;
        }
        mListenerList.add(mListener);
    }

    public void removeDateListener(DateSelInfoCallBack mListener) {
        if (mListener == null) {
            return;
        }
        mListenerList.remove(mListener);
    }


}
