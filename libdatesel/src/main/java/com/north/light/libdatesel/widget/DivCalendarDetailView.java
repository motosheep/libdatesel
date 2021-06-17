package com.north.light.libdatesel.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.north.light.libdatesel.R;
import com.north.light.libdatesel.utils.ListSpilt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzt
 * time 2021/6/16 10:04
 *
 * @author lizhengting
 * 描述：日历详情页控件
 */
public class DivCalendarDetailView extends LinearLayout {
    /**
     * 监听
     */
    private OnClickListener mListener;

    /**
     * 背景图片集合
     */
    private List<ImageView> mSelBgList = new ArrayList<>();

    /**
     * 传入的数据
     */
    private List<DivCalendarDetailInfo> mData = new ArrayList<>();

    /**
     * 绘制布局runnable
     */
    private DataRunnable mDataRunnable;

    /**
     * 模式：1月份模式  2年份模式
     */
    private int mode = 1;

    //控件实际宽高
    private int width;
    private int height;


    public DivCalendarDetailView(Context context) {
        this(context, null);
    }

    public DivCalendarDetailView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DivCalendarDetailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    private void init() {
        setGravity(Gravity.CENTER_HORIZONTAL);
        setOrientation(LinearLayout.VERTICAL);
    }

    /**
     * 设置模式，不同模式点击的效果也不同
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * 设置数据
     *
     * @param currentTime 格式yyyyMMdd
     */
    public void setData(final List<DivCalendarDetailInfo> org, final String currentTime) throws Exception {
        if (mDataRunnable != null) {
            this.removeCallbacks(mDataRunnable);
            mDataRunnable = null;
        }
        mDataRunnable = new DataRunnable(org, currentTime);
        this.post(mDataRunnable);
    }

    /**
     * 设置数据
     */
    public void setDataRunnable(final List<DivCalendarDetailInfo> org, final String currentTime) {
        removeAllViews();
        if (org == null || org.size() == 0) {
            return;
        }
        mSelBgList.clear();
        mData.clear();
        mData.addAll(org);
        //顶部的控件
        LinearLayout titleRoot = new LinearLayout(getContext());
        addView(titleRoot);
        LinearLayout.LayoutParams titleRootParams = (LayoutParams) titleRoot.getLayoutParams();
        titleRootParams.width = LayoutParams.MATCH_PARENT;
        titleRootParams.height = LayoutParams.WRAP_CONTENT;
        titleRootParams.setMargins(0, 0, 0, width / 54);
        titleRoot.setLayoutParams(titleRootParams);
        titleRoot.setGravity(Gravity.CENTER);
        titleRoot.setOrientation(LinearLayout.HORIZONTAL);
        //添加头部控件
        for (int i = 0; i < 7; i++) {
            TextView titleDesc = new TextView(getContext());
            titleRoot.addView(titleDesc);
            LinearLayout.LayoutParams descParams = (LayoutParams) titleDesc.getLayoutParams();
            descParams.weight = 1;
            descParams.height = LayoutParams.WRAP_CONTENT;
            descParams.width = 0;
            titleDesc.setLayoutParams(descParams);
            titleDesc.setGravity(Gravity.CENTER);
            titleDesc.setTextColor(getContext().getResources().getColor(R.color.color_4D000000));
            titleDesc.setTextSize(width / 90);
            titleDesc.setText(changeDigitalToChinese(i + 1));
            titleDesc.setTypeface(Typeface.DEFAULT_BOLD);
        }
        //内容控件
        List<List<DivCalendarDetailInfo>> listSplitRes = ListSpilt.splitList(mData, 7);
        int count = 0;
        for (int s = 0; s < listSplitRes.size(); s++) {
            final List<DivCalendarDetailInfo> splitList = listSplitRes.get(s);
            LinearLayout contentRoot = new LinearLayout(getContext());
            addView(contentRoot);
            LinearLayout.LayoutParams contentRootParams = (LayoutParams) contentRoot.getLayoutParams();
            contentRootParams.width = LayoutParams.MATCH_PARENT;
            contentRootParams.height = LayoutParams.WRAP_CONTENT;
            contentRootParams.setMargins(0, 0, 0, width / 18);
            contentRoot.setLayoutParams(contentRootParams);
            contentRoot.setGravity(Gravity.CENTER);
            contentRoot.setOrientation(LinearLayout.HORIZONTAL);
            for (int i = 0; i < splitList.size(); i++) {
                final DivCalendarDetailInfo detailInfo = splitList.get(i);
                //判断是否为当前日期
                if ((detailInfo.getYear() + detailInfo.getMonth() + detailInfo.getDay()).equals(currentTime)) {
                    detailInfo.setCurrentDay(1);
                } else {
                    detailInfo.setCurrentDay(0);
                }
                RelativeLayout contentRel = new RelativeLayout(getContext());
                contentRoot.addView(contentRel);
                LinearLayout.LayoutParams contentRelParams = (LayoutParams) contentRel.getLayoutParams();
                contentRelParams.weight = 1;
                contentRelParams.height = LayoutParams.WRAP_CONTENT;
                contentRelParams.width = 0;
                contentRel.setLayoutParams(contentRelParams);
                //背景图片
                ImageView mBg = new ImageView(getContext());
                contentRel.addView(mBg);
                RelativeLayout.LayoutParams imgParams = (RelativeLayout.LayoutParams) mBg.getLayoutParams();
                imgParams.height = width / 9;
                imgParams.width = width / 9;
                imgParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                mBg.setLayoutParams(imgParams);
                if (detailInfo.getCurrentDay() == 1) {
                    mBg.setBackgroundResource(R.drawable.shape_date_sel_day_of_month_default_bg);
                }
                mSelBgList.add(mBg);
                //文字
                TextView detailTV = new TextView(getContext());
                contentRel.addView(detailTV);
                RelativeLayout.LayoutParams descParams = (RelativeLayout.LayoutParams) detailTV.getLayoutParams();
                descParams.height = LayoutParams.WRAP_CONTENT;
                descParams.width = LayoutParams.WRAP_CONTENT;
                descParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                detailTV.setLayoutParams(descParams);

                if (detailInfo.getCurrentMonth() == 1) {
                    detailTV.setTextColor(getContext().getResources().getColor(R.color.color_99000000));
                } else {
                    detailTV.setTextColor(getContext().getResources().getColor(R.color.color_4D000000));
                    if (mode == 2) {
                        //年份模式隐藏补充的数据
                        detailTV.setVisibility(View.INVISIBLE);
                    }
                }
                detailTV.setTypeface(Typeface.DEFAULT_BOLD);
                detailTV.setTextSize(width / 56);
                detailTV.setText(detailInfo.getDay());
                final int finalCount = count;
                detailTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            if (mode == 1) {
                                mListener.dayDetail(detailInfo);
                            }
                        }
                        if (mode == 1) {
                            for (int j = 0; j < mData.size(); j++) {
                                if (j == finalCount) {
                                    //当前选中的
                                    mData.get(finalCount).setCurrentSel(1);
                                    mSelBgList.get(j).setImageResource(R.drawable.shape_date_sel_day_of_month_sel_bg);
                                } else {
                                    if (mData.get(j).getCurrentSel() == 1) {
                                        mData.get(j).setCurrentSel(0);
                                        mSelBgList.get(j).setImageResource(R.drawable.shape_date_sel_day_of_month_alpha_bg);
                                    }
                                }
                            }
                        }

                    }
                });
                count++;
            }
        }
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == 2) {
                    try {
                        if (mListener != null) {
                            for (DivCalendarDetailInfo cache : mData) {
                                if (cache.getCurrentMonth() == 1) {
                                    mListener.monthDetail(cache.getYear(), cache.getMonth());
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {

                    }
                }
            }
        });
    }

    /**
     * 清空选中的数据
     */
    public void clearSelectStatus() {
        for (int j = 0; j < mData.size(); j++) {
            if (mData.get(j).getCurrentSel() == 1) {
                //当前选中的
                mData.get(j).setCurrentSel(0);
                mSelBgList.get(j).setImageResource(R.drawable.shape_date_sel_day_of_month_alpha_bg);
            }
        }
    }

    /**
     * 设置数据runnable
     */
    private class DataRunnable implements Runnable {
        List<DivCalendarDetailInfo> org;
        String currentTime;

        public DataRunnable(final List<DivCalendarDetailInfo> org, final String currentTime) {
            this.org = org;
            this.currentTime = currentTime;
        }

        @Override
        public void run() {
            setDataRunnable(org, currentTime);
        }
    }

    /**
     * 根据数字转换为中文
     */
    private String changeDigitalToChinese(int org) {
        switch (org) {
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "日";
        }
        return "";
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mDataRunnable != null) {
            this.removeCallbacks(mDataRunnable);
            mDataRunnable = null;
        }
        super.onDetachedFromWindow();
        mListener = null;
    }

    //监听事件
    public interface OnClickListener {
        public void dayDetail(DivCalendarDetailInfo info);

        public void monthDetail(String year, String month);
    }

    public void setOnDateClickListener(OnClickListener listener) {
        mListener = listener;
    }
}
