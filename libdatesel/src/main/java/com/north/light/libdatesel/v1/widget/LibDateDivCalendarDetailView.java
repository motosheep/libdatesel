package com.north.light.libdatesel.v1.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.north.light.libdatesel.R;
import com.north.light.libdatesel.v1.memory.LibDateMemoryInfo;
import com.north.light.libdatesel.v1.model.LibDateCalendarManager;
import com.north.light.libdatesel.utils.LibDateListSpilt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lzt
 * time 2021/6/16 10:04
 *
 * @author lizhengting
 * 描述：日历详情页控件
 */
public class LibDateDivCalendarDetailView extends LinearLayout {
    /**
     * 监听
     */
    private OnClickListener mListener;

    /**
     * 背景图片集合
     */
    private List<ImageView> mSelBgList = new ArrayList<>();
    /**
     * 红点控件集合
     */
    private List<ImageView> mPointList = new ArrayList<>();
    /**
     * 文字控件集合
     */
    private List<TextView> mSelTxList = new ArrayList<>();

    /**
     * 传入的数据
     */
    private List<LibDateDivCalendarDetailInfo> mData = new ArrayList<>();

    /**
     * 绘制布局runnable
     */
    private DataRunnable mDataRunnable;

    /**
     * 模式：1月份模式  2年份模式
     */
    private int mode = 1;
    /**
     * 是否显示上个月的日期
     */
    private Boolean mShowLastMonth = false;


    //控件实际宽高
    private int mWidth;
    private AtomicInteger mHeight;
    private AtomicInteger mHeightTreeCount = new AtomicInteger();

    /**
     * 高度参数缓存--存在外部容器布局高度，少于当前代码新增的高度，设置高度缓存，用于失效时使用
     */
    private int mTitleHeightCache = 0;
    private int mContentHeightCache = 0;


    // 选中样式-------------------------------------------------------------------------
    //当前时间背景
    private int mCurrentTimeColorRes = R.drawable.shape_date_sel_day_of_month_default_bg;
    //本月选中背景
    private int mSelTimeColorRes = R.drawable.shape_date_sel_day_of_month_sel_bg;
    //本月没有选中背景
    private int mUnSelTimeColorRes = R.drawable.shape_date_sel_day_of_month_alpha_bg;
    //非本月没有选中背景
    private int mUnSelTimeColorNoCurRes = R.drawable.shape_date_sel_day_of_month_alpha_bg;
    //本月文字
    private int mCurTxSelRes = R.color.color_3385FF;
    private int mCurTxUnSelRes = R.color.color_99000000;
    private int mCurTxDefaultRes = R.color.color_3385FF;
    //非本月文字
    private int mNoCurTxSelRes = R.color.color_3385FF;
    private int mNoCurTxUnSelRes = R.color.color_4D000000;
    //文字标题颜色
    private int mTitleColor = R.color.color_000000;
    //红点颜色--默认当前日期颜色，选中颜色，非选中颜色
    private int mPointCurRes = R.color.color_3385FF;
    private int mPointOtherSelRes = R.color.color_EBEAEA;
    private int mPointOtherUnSelRes = R.color.color_4D000000;

    public int getUnSelTimeColorRes() {
        return mUnSelTimeColorRes;
    }

    public void setUnSelTimeColorRes(int mUnSelTimeColorRes) {
        this.mUnSelTimeColorRes = mUnSelTimeColorRes;
    }

    public int getTitleColor() {
        return mTitleColor;
    }

    public int getCurrentTimeColorRes() {
        return mCurrentTimeColorRes;
    }

    public int getSelTimeColorRes() {
        return mSelTimeColorRes;
    }

    public void setCurrentTimeColorRes(int mCurrentTimeColorRes) {
        this.mCurrentTimeColorRes = mCurrentTimeColorRes;
    }

    public void setSelTimeColorRes(int mSelTimeColorRes) {
        this.mSelTimeColorRes = mSelTimeColorRes;
    }

    public int getUnSelTimeColorNoCurRes() {
        return mUnSelTimeColorNoCurRes;
    }

    public void setUnSelTimeColorNoCurRes(int mUnSelTimeColorNoCurRes) {
        this.mUnSelTimeColorNoCurRes = mUnSelTimeColorNoCurRes;
    }

    public int getCurTxSelRes() {
        return mCurTxSelRes;
    }

    public void setCurTxSelRes(int mCurTxSelRes) {
        this.mCurTxSelRes = mCurTxSelRes;
    }

    public int getCurTxUnSelRes() {
        return mCurTxUnSelRes;
    }

    public void setCurTxUnSelRes(int mCurTxUnSelRes) {
        this.mCurTxUnSelRes = mCurTxUnSelRes;
    }

    public int getNoCurTxSelRes() {
        return mNoCurTxSelRes;
    }

    public void setNoCurTxSelRes(int mNoCurTxSelRes) {
        this.mNoCurTxSelRes = mNoCurTxSelRes;
    }

    public int getNoCurTxUnSelRes() {
        return mNoCurTxUnSelRes;
    }

    public int getCurTxDefaultRes() {
        return mCurTxDefaultRes;
    }

    public void setCurTxDefaultRes(int mCurTxDefaultRes) {
        this.mCurTxDefaultRes = mCurTxDefaultRes;
    }

    public void setNoCurTxUnSelRes(int mNoCurTxUnSelRes) {
        this.mNoCurTxUnSelRes = mNoCurTxUnSelRes;
    }

    public int getPointCurRes() {
        return mPointCurRes;
    }

    public int getPointOtherSelRes() {
        return mPointOtherSelRes;
    }

    public int getPointOtherUnSelRes() {
        return mPointOtherUnSelRes;
    }

    public void setPointCurRes(int mPointCurRes) {
        this.mPointCurRes = mPointCurRes;
    }

    public void setPointOtherSelRes(int mPointOtherSelRes) {
        this.mPointOtherSelRes = mPointOtherSelRes;
    }

    public void setPointOtherUnSelRes(int mPointOtherUnSelRes) {
        this.mPointOtherUnSelRes = mPointOtherUnSelRes;
    }

    // 选中样式-------------------------------------------------------------------------


    public int getWidgetHeight() {
        return mHeight.intValue();
    }

    public LibDateDivCalendarDetailView(Context context) {
        this(context, null);
    }

    public LibDateDivCalendarDetailView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LibDateDivCalendarDetailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
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
     * 设置是否显示上个月日期的标识
     */
    public void setShowLastMonth(Boolean mShowLastMonth) {
        this.mShowLastMonth = mShowLastMonth;
    }

    /**
     * 设置数据
     *
     * @param currentTime 格式yyyyMMdd
     */
    public void setData(final List<LibDateDivCalendarDetailInfo> org, final String currentTime) throws Exception {
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
    public void setDataRunnable(final List<LibDateDivCalendarDetailInfo> org, final String currentTime) {
        removeAllViews();
        if (org == null || org.size() == 0) {
            return;
        }
        mTitleHeightCache = 0;
        mContentHeightCache = 0;
        mSelTxList.clear();
        mSelBgList.clear();
        mPointList.clear();
        mData.clear();
        mData.addAll(org);
        //顶部的控件
        final LinearLayout titleRoot = new LinearLayout(getContext());
        addView(titleRoot);
        LayoutParams titleRootParams = (LayoutParams) titleRoot.getLayoutParams();
        titleRootParams.width = LayoutParams.MATCH_PARENT;
        titleRootParams.height = LayoutParams.WRAP_CONTENT;
        final int titleRootMBottom = mWidth / 54;
        titleRootParams.setMargins(0, 0, 0, titleRootMBottom);
        titleRoot.setLayoutParams(titleRootParams);
        titleRoot.setGravity(Gravity.CENTER);
        titleRoot.setOrientation(LinearLayout.HORIZONTAL);
        //添加头部控件
        for (int i = 0; i < 7; i++) {
            TextView titleDesc = new TextView(getContext());
            titleRoot.addView(titleDesc);
            LayoutParams descParams = (LayoutParams) titleDesc.getLayoutParams();
            descParams.weight = 1;
            descParams.height = LayoutParams.WRAP_CONTENT;
            descParams.width = 0;
            titleDesc.setLayoutParams(descParams);
            titleDesc.setGravity(Gravity.CENTER);
            titleDesc.setTextColor(getContext().getResources().getColor(getTitleColor()));
            titleDesc.setTextSize(mWidth / 90);
            titleDesc.setText(LibDateCalendarManager.getInstance().changeDigitalToChinese(i + 1));
            titleDesc.setTypeface(Typeface.DEFAULT_BOLD);
        }
        //内容控件
        final List<List<LibDateDivCalendarDetailInfo>> listSplitRes = LibDateListSpilt.splitList(mData, 7);
        int count = 0;
        if (mHeightTreeCount == null) {
            mHeightTreeCount = new AtomicInteger(0);
        }
        if (mHeight == null) {
            mHeight = new AtomicInteger(0);
        }
        mHeightTreeCount.set(0);
        mHeight.set(0);
        final ViewTreeObserver titleRootTree = titleRoot.getViewTreeObserver();
        titleRootTree.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                titleRootTree.removeOnGlobalLayoutListener(this);
                int height = titleRoot.getMeasuredHeight();
                if (height != 0) {
                    mTitleHeightCache = height;
                }
                if (mTitleHeightCache != 0) {
                    mHeightTreeCount.incrementAndGet();
                    mHeight.addAndGet(mTitleHeightCache);
                    mHeight.addAndGet(titleRootMBottom);
                    if (mHeightTreeCount.intValue() == listSplitRes.size() + 1) {
                        //总高度
                        if (mListener != null) {
                            mListener.widgetHeight(mHeight.intValue());
                        }
                    }
                }
            }
        });
        for (int s = 0; s < listSplitRes.size(); s++) {
            final List<LibDateDivCalendarDetailInfo> splitList = listSplitRes.get(s);
            final LinearLayout contentRoot = new LinearLayout(getContext());
            addView(contentRoot);
            LayoutParams contentRootParams = (LayoutParams) contentRoot.getLayoutParams();
            contentRootParams.width = LayoutParams.MATCH_PARENT;
            contentRootParams.height = mWidth / 7;
            final int contentRootMBottom = 0;
            contentRootParams.setMargins(0, 0, 0, contentRootMBottom);
            contentRoot.setLayoutParams(contentRootParams);
            contentRoot.setGravity(Gravity.CENTER);
            contentRoot.setOrientation(LinearLayout.HORIZONTAL);
            for (int i = 0; i < splitList.size(); i++) {
                final LibDateDivCalendarDetailInfo detailInfo = splitList.get(i);
                //判断是否为当前日期
                if ((detailInfo.getYear() + detailInfo.getMonth() + detailInfo.getDay()).equals(currentTime)) {
                    detailInfo.setCurrentDay(1);
                } else {
                    detailInfo.setCurrentDay(0);
                }
                //容器布局
                RelativeLayout contentRel = new RelativeLayout(getContext());
                contentRoot.addView(contentRel);
                LayoutParams contentRelParams = (LayoutParams) contentRel.getLayoutParams();
                contentRelParams.weight = 1;
                contentRelParams.height = LayoutParams.WRAP_CONTENT;
                contentRelParams.width = 0;
                contentRel.setLayoutParams(contentRelParams);
                //背景图片
                ImageView mBg = new ImageView(getContext());
                contentRel.addView(mBg);
                RelativeLayout.LayoutParams imgParams = (RelativeLayout.LayoutParams) mBg.getLayoutParams();
                imgParams.height = mWidth / 9;
                imgParams.width = mWidth / 9;
                imgParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                mBg.setLayoutParams(imgParams);
                if (detailInfo.getCurrentDay() == 1) {
                    if (mode == 1) {
                        mBg.setBackgroundResource(getCurrentTimeColorRes());
                    } else if (mode == 2) {
                        try {
                            String year = LibDateCalendarManager.getInstance().getYear(0);
                            //年份模式下，若当前月，不符合，则不显示选中
                            if (detailInfo.getCurrentMonth() == 1 && detailInfo.getYear().equals(year)) {
                                mBg.setBackgroundResource(getCurrentTimeColorRes());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                mSelBgList.add(mBg);
                //文字
                final TextView detailTV = new TextView(getContext());
                contentRel.addView(detailTV);
                RelativeLayout.LayoutParams descParams = (RelativeLayout.LayoutParams) detailTV.getLayoutParams();
                descParams.height = LayoutParams.WRAP_CONTENT;
                descParams.width = LayoutParams.WRAP_CONTENT;
                descParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                detailTV.setLayoutParams(descParams);
                detailTV.setTypeface(Typeface.DEFAULT_BOLD);
                detailTV.setTextSize(mWidth / 56);
                if (detailInfo.getCurrentDay() == 1) {
                    detailTV.setTextSize(mWidth / 70);
                    detailTV.setText(getCurDayTx());
                } else {
                    detailTV.setText(detailInfo.getDay());
                }
                detailTV.setGravity(Gravity.CENTER);
                final int finalCount = count;
                mSelTxList.add(detailTV);
                //红点
                final ImageView point = new ImageView(getContext());
                contentRel.addView(point);
                RelativeLayout.LayoutParams pointParams = (RelativeLayout.LayoutParams) point.getLayoutParams();
                pointParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                pointParams.height = 12;
                pointParams.width = 12;
                pointParams.topMargin = (int) (mWidth / 11.3);
                point.setLayoutParams(pointParams);
                if (detailInfo.getCurrentDay() == 1) {
                    point.setImageResource(getPointCurRes());
                } else {
                    point.setImageResource(getPointOtherUnSelRes());
                }
                point.setVisibility(View.INVISIBLE);
                mPointList.add(point);
                //控件隐藏显示设置
                //文字控件隐藏or显示------------------------------------------------------------------
                if (detailInfo.getCurrentMonth() == 1) {
                    if (detailInfo.getCurrentDay() == 1) {
                        detailTV.setTextColor(getContext().getResources().getColor(getCurTxDefaultRes()));
                    } else {
                        detailTV.setTextColor(getContext().getResources().getColor(getCurTxUnSelRes()));
                    }
                } else {
                    detailTV.setTextColor(getContext().getResources().getColor(getNoCurTxUnSelRes()));
                    if (mode == 2) {
                        //年份模式隐藏补充的数据
                        detailTV.setVisibility(View.INVISIBLE);
                    }
                    if (!mShowLastMonth) {
                        //不显示非本月数据
                        detailTV.setVisibility(View.INVISIBLE);
                    }
                }
                //红点控件隐藏or显示-----------------------------------------------------------------
                if (mShowLastMonth) {
                    if (detailInfo.getHadEvent() == 1) {
                        //有事件
                        point.setVisibility(View.VISIBLE);
                    } else {
                        //无事件
                        point.setVisibility(View.INVISIBLE);
                    }
                } else {
                    //不显示上个月
                    if (detailInfo.getCurrentMonth() == 1) {
                        if (detailInfo.getHadEvent() == 1) {
                            //有事件
                            point.setVisibility(View.VISIBLE);
                        } else {
                            //无事件
                            point.setVisibility(View.INVISIBLE);
                        }
                    }
                }
                if (mode == 1) {
                    contentRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!mShowLastMonth && mData.get(finalCount).getCurrentMonth() != 1) {
                                return;
                            }
                            //先设置所有数据都为未选中
                            for (int j = 0; j < mData.size(); j++) {
                                if (mData.get(j).getCurrentSel() == 1) {
                                    mData.get(j).setCurrentSel(0);
                                    if (mData.get(j).getCurrentMonth() == 1) {
                                        if (mData.get(j).getCurrentDay() == 1) {
                                            mSelTxList.get(j).setTextColor(getContext().getResources().getColor(getCurTxDefaultRes()));
                                            mPointList.get(j).setImageResource(getPointCurRes());
                                        } else {
                                            mSelTxList.get(j).setTextColor(getContext().getResources().getColor(getCurTxUnSelRes()));
                                            mPointList.get(j).setImageResource(getPointOtherUnSelRes());
                                        }
                                    } else {
                                        mSelTxList.get(j).setTextColor(getContext().getResources().getColor(getNoCurTxUnSelRes()));
                                        mPointList.get(j).setImageResource(getPointOtherUnSelRes());
                                    }
                                    mSelBgList.get(j).setImageResource(getUnSelTimeColorRes());
                                }
                            }
                            //再当前位置设置为选中
                            mData.get(finalCount).setCurrentSel(1);
                            if (mData.get(finalCount).getCurrentMonth() == 1) {
                                mSelTxList.get(finalCount).setTextColor(getContext().getResources().getColor(getCurTxSelRes()));
                            } else {
                                mSelTxList.get(finalCount).setTextColor(getContext().getResources().getColor(getNoCurTxSelRes()));
                            }
                            mSelBgList.get(finalCount).setImageResource(getSelTimeColorRes());
                            mPointList.get(finalCount).setImageResource(getPointOtherSelRes());
                            String year = mData.get(finalCount).getYear();
                            String month = mData.get(finalCount).getMonth();
                            String day = mData.get(finalCount).getDay();
                            LibDateMemoryInfo.getInstance().setCurrentSelDate(year + month + day);
                            //点击事件监听
                            if (mListener != null) {
                                mListener.dayDetail(mData.get(finalCount));
                            }
                        }
                    });
                    //非自动选中今天的情况--自动选中曾经选中的数据
                    String yearMonthDay = LibDateMemoryInfo.getInstance().getCurrentSelDate();
                    String curYearMonthDay = detailInfo.getYear() + detailInfo.getMonth() + detailInfo.getDay();
                    if (!TextUtils.isEmpty(yearMonthDay) && !TextUtils.isEmpty(curYearMonthDay) &&
                            yearMonthDay.equals(curYearMonthDay)) {
                        //模拟点击一次
                        contentRel.performClick();
                    }
                }
                count++;
            }
            final ViewTreeObserver contentRootTree = contentRoot.getViewTreeObserver();
            contentRootTree.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    contentRootTree.removeOnGlobalLayoutListener(this);
                    int height = contentRoot.getMeasuredHeight();
                    if (height != 0) {
                        mContentHeightCache = height;
                    }
                    if (mContentHeightCache != 0) {
                        mHeightTreeCount.incrementAndGet();
                        mHeight.addAndGet(mContentHeightCache);
                        mHeight.addAndGet(contentRootMBottom);
                        if (mHeightTreeCount.intValue() == listSplitRes.size() + 1) {
                            //总高度
                            if (mListener != null) {
                                mListener.widgetHeight(mHeight.intValue());
                            }
                        }
                    }
                }
            });
        }
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == 2) {
                    try {
                        if (mListener != null) {
                            for (LibDateDivCalendarDetailInfo cache : mData) {
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
        requestLayout();
    }

    private String getCurDayTx() {
        return "今天";
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    /**
     * 清空选中的数据
     */
    public void clearSelectStatus() {
        for (int j = 0; j < mData.size(); j++) {
            if (mData.get(j).getCurrentSel() == 1) {
                //当前选中的
                mData.get(j).setCurrentSel(0);
                if (mData.get(j).getCurrentMonth() == 1) {
                    mSelBgList.get(j).setImageResource(getUnSelTimeColorRes());
                    mSelTxList.get(j).setTextColor(getContext().getResources().getColor(getCurTxUnSelRes()));
                } else {
                    mSelBgList.get(j).setImageResource(getUnSelTimeColorNoCurRes());
                    mSelTxList.get(j).setTextColor(getContext().getResources().getColor(getNoCurTxUnSelRes()));
                }
            }
        }
    }

    /**
     * 设置数据runnable
     */
    private class DataRunnable implements Runnable {
        List<LibDateDivCalendarDetailInfo> org;
        String currentTime;

        public DataRunnable(final List<LibDateDivCalendarDetailInfo> org, final String currentTime) {
            this.org = org;
            this.currentTime = currentTime;
        }

        @Override
        public void run() {
            setDataRunnable(org, currentTime);
        }
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
        public void dayDetail(LibDateDivCalendarDetailInfo info);

        public void monthDetail(String year, String month);

        public void widgetHeight(int height);
    }

    public void setOnDateClickListener(OnClickListener listener) {
        mListener = listener;
    }
}
