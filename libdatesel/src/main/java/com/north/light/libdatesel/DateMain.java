package com.north.light.libdatesel;

import android.app.Activity;
import android.content.Intent;

import com.north.light.libdatesel.bean.DateSelResult;
import com.north.light.libdatesel.ui.LibSelDateActivity;

import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by lzt
 * time 2020/6/22
 * 描述：时间管理外部调用类
 */
public class DateMain {
    private CopyOnWriteArrayList<AddressSelInfoCallBack> mListenerList = new CopyOnWriteArrayList<>();

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
            for (AddressSelInfoCallBack listener : mListenerList) {
                if (listener != null) {
                    listener.Address(result);
                }
            }
        }
    }

    /**
     * 启动地址选择
     *
     * @param type 1年月日时分秒，2年月日时分 3年月日时，4年月日，5时分秒，6时分
     */
    public void show(Activity activity, int type) {
        Intent intent = new Intent(activity, LibSelDateActivity.class);
        intent.putExtra(LibSelDateActivity.CODE_REQUEST, type);
        activity.startActivity(intent);
    }


    /**
     * 地址选择回调-------------------------------------------------------------------------------
     */
    public interface AddressSelInfoCallBack {
        void Address(DateSelResult result);
    }

    public void setOnAddressListener(AddressSelInfoCallBack mListener) {
        if (mListener == null) {
            return;
        }
        mListenerList.add(mListener);
    }

    public void removeAddressListener(AddressSelInfoCallBack mListener) {
        if (mListener == null) {
            return;
        }
        mListenerList.remove(mListener);
    }


    public static void main(String[] args){
        int year = Calendar.getInstance().get(Calendar.YEAR);
    }

}
