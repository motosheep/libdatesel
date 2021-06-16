package com.north.light.libdatesel.widget;

import android.content.Context;
import android.util.AttributeSet;
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

    private void init() {
        setGravity(Gravity.CENTER_HORIZONTAL);
        setOrientation(LinearLayout.VERTICAL);
    }


    /**
     * 设置数据
     *
     * @param currentTime 格式yyyyMMdd
     */
    public void setData(List<DivCalendarDetailInfo> org, String currentTime) throws Exception {
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
        titleRootParams.setMargins(0, 0, 0, 20);
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
            titleDesc.setTextSize(10);
            titleDesc.setText(changeDigitalToChinese(i + 1));
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
            contentRootParams.setMargins(0, 0, 0, 60);
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
                imgParams.height = 120;
                imgParams.width = 120;
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
                }
                detailTV.setTextSize(18);
                detailTV.setText(detailInfo.getDay());
                final int finalCount = count;
                detailTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.detail(detailInfo);
                        }
                        for (int j = 0; j < mData.size(); j++) {
                            if (j == finalCount) {
                                //当前选中的
                                mData.get(finalCount).setCurrentSel(1);
                                mSelBgList.get(j).setImageResource(R.drawable.shape_date_sel_day_of_month_sel_bg);
                            } else {
                                if(mData.get(j).getCurrentSel()==1){
                                    mData.get(j).setCurrentSel(0);
                                    mSelBgList.get(j).setImageResource(R.drawable.shape_date_sel_day_of_month_alpha_bg);
                                }
                            }
                        }
                    }
                });
                count++;
            }
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
                return "七";
        }
        return "";
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mListener = null;
    }

    //监听事件
    public interface OnClickListener {
        public void detail(DivCalendarDetailInfo info);
    }

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }
}
