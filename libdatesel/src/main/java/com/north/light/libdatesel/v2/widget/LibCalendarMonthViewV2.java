package com.north.light.libdatesel.v2.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.north.light.libdatesel.R;
import com.north.light.libdatesel.utils.LibDateListSpilt;
import com.north.light.libdatesel.v2.DateMainV2;
import com.north.light.libdatesel.v2.bean.LibDateDayInMonthDetailInfoV2;
import com.north.light.libdatesel.v2.memory.LibCalendarMonthViewV2Memory;
import com.north.light.libdatesel.v2.memory.LibDateMemoryInfoV2;
import com.north.light.libdatesel.v2.model.LibDateCalendarManagerV2;
import com.north.light.libdatesel.widget.LibDateBaseView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lzt
 * @Date: 2022/1/7 9:52
 * @Description:日历月份控件--显示全月的数据
 */
public class LibCalendarMonthViewV2 extends LibDateBaseView {
    /**
     * 控件宽度
     */
    private int mWidth = 0;
    /**
     * 绘制的日期数据集合
     */
    private List<LibDateDayInMonthDetailInfoV2> mData = new ArrayList<>();
    /**
     * 日期事件:yyyyMMdd
     */
    private List<String> mEventList = new ArrayList<>();
    /**
     * 监听
     */
    private OnWidgetParamsCallBack mListener;
    private OnDateClickListener mClickListener;
    /**
     * 点击区域集合
     */
    private List<ClickRectInfo> mClickRectList = new ArrayList<>();
    /**
     * 是否显示上个月的日期
     */
    private boolean mShowLastMonth = false;
    /**
     * 是否需要传输高到外部
     */
    private boolean mNeedToNotifyHeight = false;
    /**
     * 模式--1月份模式 2年份模式
     */
    private int mMode = 1;
    /**
     * 当前的年月
     */
    private String mYear, mMonth;
    /**
     * 是否需要回调首次选中的日期
     */
    private boolean mNeedToCallBackFirstSel = false;
    /**
     * 日期缓存--用于回调
     */
    private LibDateDayInMonthDetailInfoV2 mDayCallBackCache;

    //touch---------------------------------------------------------------------------------------
    private float mTouchX, mTouchY;
    private ClickPointInfo mClickPoint;

    //canvas---------------------------------------------------------------------------------------
    //文字画笔
    private Paint mTxPaint = new Paint();
    //每个格的高度
    private int mEleHeight = 0;
    //每个格的宽度
    private int mEleWidth = 0;
    //顶部标题高度
    private int mEleTitleHeight = 0;
    //标题------------------------------------------------------------------------------------------
    //标题文字大小
    private int mTitleTxSize = 30;
    //标题文字颜色
    private int mTitleTxColor = R.color.color_000000;
    //日历内容--------------------------------------------------------------------------------------
    //文字大小
    private int mContentTxSize = 40;
    //今天大小
    private int mContentCurTxSize = 36;
    //文字默认颜色
    private int mContentTxColor = R.color.color_000000;
    //非本月文字颜色
    private int mContentTxNoCurColor = R.color.color_4D000000;
    //当前日期文字颜色
    private int mContentTxCurColor = R.color.color_FFFFFF;
    //选中日期文字颜色
    private int mContentTxSelColor = R.color.color_FFFFFF;
    //点击内容--------------------------------------------------------------------------------------
    //点击的圆画笔
    private Paint mClickPaint = new Paint();
    //今天的画笔
    private Paint mTodayPaint = new Paint();
    //点击的圆的颜色
    private int mClickCircleColor = R.color.color_3385FF;
    //当前日期圆的默认颜色
    private int mCircleCurColor = R.color.color_e63385FF;
    //事件--------------------------------------------------------------------------------------
    //事件画笔
    private Paint mEventPaint = new Paint();
    //有事件的颜色
    private int mEventExistColor = R.color.color_3385FF;
    //有事件且选中
    private int mEventSelColor = R.color.color_FFFFFF;
    //有事件且为当天
    private int mEventCurColor = R.color.color_FFFFFF;
    //事件的半径
    private int mEventRadius = 6;
    //canvas---------------------------------------------------------------------------------------

    //getter setter--------------------------------------------------------------------------------


    public int getContentCurTxSize() {
        return mContentCurTxSize;
    }

    public void setMode(int mode) {
        this.mMode = mode;
    }

    public int getEventRadius() {
        return mEventRadius;
    }

    public void setEventRadius(int mEventRadius) {
        this.mEventRadius = mEventRadius;
    }

    public int getEventCurColor() {
        return mEventCurColor;
    }

    public void setEventCurColor(int mEventCurColor) {
        this.mEventCurColor = mEventCurColor;
    }

    public int getEventExistColor() {
        return mEventExistColor;
    }

    public void setEventExistColor(int mEventExistColor) {
        this.mEventExistColor = mEventExistColor;
    }

    public int getEventSelColor() {
        return mEventSelColor;
    }

    public void setEventSelColor(int mEventNoExistColor) {
        this.mEventSelColor = mEventNoExistColor;
    }

    public int getCircleCurColor() {
        return mCircleCurColor;
    }

    public void setCircleCurColor(int mCircleCurColor) {
        this.mCircleCurColor = mCircleCurColor;
    }

    public int getContentTxSelColor() {
        return mContentTxSelColor;
    }

    public void setContentTxSelColor(int mContentTxSelColor) {
        this.mContentTxSelColor = mContentTxSelColor;
    }

    public int getClickCircleColor() {
        return mClickCircleColor;
    }

    public void setClickCircleColor(int mClickCircle) {
        this.mClickCircleColor = mClickCircle;
    }

    public void setContentTxCurColor(int mContentTxCurColor) {
        this.mContentTxCurColor = mContentTxCurColor;
    }

    public int getContentTxCurColor() {
        return mContentTxCurColor;
    }

    public int getContentTxNoCurColor() {
        return mContentTxNoCurColor;
    }

    public void setContentTxNoCurColor(int mContentTxNoCurColor) {
        this.mContentTxNoCurColor = mContentTxNoCurColor;
    }

    public int getContentTxColor() {
        return mContentTxColor;
    }

    public int getContentTxSize() {
        return mContentTxSize;
    }

    public int getTitleTxSize() {
        return mTitleTxSize;
    }

    public void setTitleTxSize(int mTitleTxSize) {
        this.mTitleTxSize = mTitleTxSize;
    }

    public void setTitleTxColor(int mTitleTxColor) {
        this.mTitleTxColor = mTitleTxColor;
    }

    public int getTitleTxColor() {
        return mTitleTxColor;
    }
    //getter setter--------------------------------------------------------------------------------


    public LibCalendarMonthViewV2(Context context) {
        super(context);
        init();
    }

    public LibCalendarMonthViewV2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LibCalendarMonthViewV2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initXRes();
        //初始化画笔
        //今天的画笔---------------------------------------------------------------------------------
        if (mTodayPaint == null) {
            mTodayPaint = new Paint();
        }
        mTodayPaint.setStyle(Paint.Style.FILL);
        mTodayPaint.setStrokeWidth(1);
        mTodayPaint.setAntiAlias(true);
        mTodayPaint.setDither(true);
        mTodayPaint.setColor(getResources().getColor(getCircleCurColor()));
        mTodayPaint.setTextAlign(Paint.Align.CENTER);
        //点击的圆画笔-------------------------------------------------------------------------------
        if (mClickPaint == null) {
            mClickPaint = new Paint();
        }
        mClickPaint.setStyle(Paint.Style.FILL);
        mClickPaint.setStrokeWidth(1);
        mClickPaint.setAntiAlias(true);
        mClickPaint.setDither(true);
        mClickPaint.setColor(getResources().getColor(getClickCircleColor()));
        mClickPaint.setTextAlign(Paint.Align.CENTER);
        //事假的画笔-------------------------------------------------------------------------------
        if (mEventPaint == null) {
            mEventPaint = new Paint();
        }
        mEventPaint.setStyle(Paint.Style.FILL);
        mEventPaint.setStrokeWidth(1);
        mEventPaint.setAntiAlias(true);
        mEventPaint.setDither(true);
        mEventPaint.setTextAlign(Paint.Align.CENTER);
        //文字画笔-------------------------------------------------------------------------------
        if (mTxPaint == null) {
            mTxPaint = new Paint();
        }
        mTxPaint.setStyle(Paint.Style.FILL);
        mTxPaint.setStrokeWidth(1);
        mTxPaint.setAntiAlias(true);
        mTxPaint.setDither(true);
        mTxPaint.setTextSize(getTitleTxSize());
        mTxPaint.setColor(getResources().getColor(getTitleTxColor()));
        mTxPaint.setTextAlign(Paint.Align.CENTER);
        mTxPaint.setFakeBoldText(true);


        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchX = event.getX();
                        mTouchY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float mvX = event.getX();
                        float mvY = event.getY();
                        if (Math.abs(mvX - mTouchX) > 10 ||
                                Math.abs(mvY - mTouchY) > 10) {
                            return false;
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        float upX = event.getX();
                        float upY = event.getY();
                        if (Math.abs(upX - mTouchX) > 10 ||
                                Math.abs(upY - mTouchY) > 10) {
                            return false;
                        }
                        //处理点击事件
                        if (mMode == 1) {
                            dealMonthClickEvent(upX, upY);
                        } else if (mMode == 2) {
                            dealYearClickEvent();
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        return false;
                }
                return true;
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        LibCalendarMonthViewV2Memory.getInstance().clearTXYMCache(mYear, mMonth);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        Log.d("onDraw", "onAttachedToWindow：" + (mYear + mMonth));
        mClickPoint = null;
        mNeedToCallBackFirstSel = false;
        mNeedToNotifyHeight = false;
        super.onAttachedToWindow();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        long startTime = System.currentTimeMillis();
        measurePosition(mData);
        drawCurDay(canvas);
        drawCircle(canvas);
        drawText(canvas, mWidth);
        drawPoint(canvas);
        Log.d("onDraw", "绘制时间：" + (mYear + mMonth) + "--耗时：" + (System.currentTimeMillis() - startTime));
    }


    /**
     * 初始化所有坐标
     */
    private void measurePosition(List<LibDateDayInMonthDetailInfoV2> data) {
        if (mWidth == 0) {
            return;
        }
        if (data == null || data.size() == 0) {
            return;
        }
        List<List<LibDateDayInMonthDetailInfoV2>> info = LibDateListSpilt.splitList(data, 7);
        //当前年月日
        try {
            mClickRectList.clear();
            //控件高度统计
            int totalHeight = 0;
            //初始化格子的宽高
            mEleWidth = mWidth / 7;
            mEleHeight = mWidth / 7;
            mEleTitleHeight = mEleHeight / 2;
            totalHeight = totalHeight + mEleTitleHeight;
            totalHeight = totalHeight + info.size() * mEleHeight;
            for (int i = 0; i < data.size(); i++) {
                int realContentHorPos = (i % 7);
                int realContentVerPOs = (i / 7);
                //点击区域赋值
                ClickRectInfo clickInfo = new ClickRectInfo();
                clickInfo.setStartX(realContentHorPos * mEleWidth);
                clickInfo.setEndX((realContentHorPos + 1) * mEleWidth);
                clickInfo.setStartY(mEleTitleHeight + ((realContentVerPOs) * mEleHeight));
                clickInfo.setEndY(mEleTitleHeight + ((realContentVerPOs + 1) * mEleHeight));
                if (data.get(i).getDataType() == 1) {
                    clickInfo.setCanClick(true);
                    clickInfo.setCanDrawContent(true);
                } else {
                    if (!mShowLastMonth) {
                        //不显示上个月--表明上个月的区域也是不可点击
                        clickInfo.setCanClick(false);
                        clickInfo.setCanDrawContent(false);
                    } else {
                        clickInfo.setCanClick(true);
                        clickInfo.setCanDrawContent(true);
                    }
                }
                //默认选中
                if (clickInfo.isCanDrawContent()) {
                    String selDate = LibDateMemoryInfoV2.getInstance().getCurDate();
                    String ymd = data.get(i).getYear() + data.get(i).getMonth() + data.get(i).getDayOfNum();
                    if (selDate.equals(ymd)) {
                        //设置当前为选中
                        //回调选中的日期
                        if (!mNeedToCallBackFirstSel) {
                            mNeedToCallBackFirstSel = true;
                            mClickPoint = new ClickPointInfo();
                            mClickPoint.setClickX(clickInfo.getStartX() + 1);
                            mClickPoint.setClickY(clickInfo.getStartY() + 1);
                            callbackSelInfo(data.get(i), true);
                        }
                    }
                }
                mClickRectList.add(clickInfo);
            }
            if (mListener != null && !mNeedToNotifyHeight) {
                mNeedToNotifyHeight = true;
                final int finalTotalHeight = totalHeight;
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListener.height(finalTotalHeight);
                    }
                }, 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 真正绘制
     */
    private void drawText(Canvas canvas, int mWidth) {
        if (mWidth == 0) {
            return;
        }
        if (mData == null || mData.size() == 0) {
            return;
        }
        //当前年月日
        try {
            //绘制标题-------------------------------------------------------------------------------
            //设置画笔
            mTxPaint.setTextSize(getTitleTxSize());
            mTxPaint.setColor(getResources().getColor(getTitleTxColor()));
            //标题高度
            for (int i = 0; i < 7; i++) {
                canvas.save();
                String title = LibDateCalendarManagerV2.getInstance().changeDigitalToChinese(i + 1);
                canvas.drawText(title, i * mEleWidth + mEleWidth / 2, mEleTitleHeight / 3 * 2, mTxPaint);
                canvas.restore();
            }
            //绘制内容-----------------------------------------------------------------------------
            for (int i = 0; i < mClickRectList.size(); i++) {
                ClickRectInfo clickRectInfo = mClickRectList.get(i);
                if (clickRectInfo.isCanDrawContent()) {
                    int textSize = getContentTxSize();
                    //能进行绘制才会执行--------------------------------------------------------------
                    LibDrawTxInfo txInfo = LibCalendarMonthViewV2Memory.getInstance().getTXYMCache(mYear, mMonth, String.valueOf(i));
                    if (txInfo != null) {
                        mTxPaint.setColor(txInfo.getColor());
                        mTxPaint.setTextSize(txInfo.getSize());
                        canvas.save();
                        canvas.drawText(txInfo.getTx(), txInfo.getPosX(), txInfo.getPoxY(), mTxPaint);
                        canvas.restore();
                        continue;
                    }
                    LibDateDayInMonthDetailInfoV2 contentInfo = mData.get(i);
                    int realContentHorPos = (i % 7);
                    int realContentVerPOs = (i / 7);
                    //点击区域赋值
                    ClickRectInfo clickInfo = new ClickRectInfo();
                    clickInfo.setStartX(realContentHorPos * mEleWidth);
                    clickInfo.setEndX((realContentHorPos + 1) * mEleWidth);
                    clickInfo.setStartY(mEleTitleHeight + ((realContentVerPOs) * mEleHeight));
                    clickInfo.setEndY(mEleTitleHeight + ((realContentVerPOs + 1) * mEleHeight));
                    //字体颜色设置------------------------------------------------------------------
                    String text = contentInfo.getDayOfNum();
                    //判断是否在点击区域
                    int finalColor = getResources().getColor(getContentTxSelColor());
                    if (mClickPoint != null && isInClickRect(clickInfo, mClickPoint.getClickX(), mClickPoint.getClickY())) {
                        //点击区域
                        finalColor = (getResources().getColor(getContentTxSelColor()));
                        if (isToday(i)) {
                            //当前月--今天------
                            text = "今天";
                            textSize = getContentCurTxSize();
                        }
                    } else {
                        //不是点击区域
                        if (contentInfo.getDataType() != 1) {
                            finalColor = (getResources().getColor(getContentTxNoCurColor()));
                        } else {
                            if (isToday(i)) {
                                //当前月--今天------
                                finalColor = (getResources().getColor(getContentTxCurColor()));
                                text = "今天";
                                textSize = getContentCurTxSize();
                            } else {
                                //默认字体----
                                finalColor = (getResources().getColor(getContentTxColor()));
                            }
                        }
                    }
                    mTxPaint.setTextSize(textSize);
                    //字体颜色设置------------------------------------------------------------------
                    int textHeight = getTxHeight(text, mTxPaint);
                    int realHeight = (mEleHeight / 2) - (textHeight / 2);
                    int drawHeight = mEleTitleHeight + ((realContentVerPOs + 1) * mEleHeight - realHeight);
                    int drawWidth = realContentHorPos * mEleWidth + mEleWidth / 2;
                    mTxPaint.setColor(finalColor);
                    //开始绘制
                    canvas.save();
                    canvas.drawText(text, drawWidth, drawHeight, mTxPaint);
                    canvas.restore();
                    //保存信息---------------------------------------------------------------------
                    LibDrawTxInfo saveInfo = new LibDrawTxInfo();
                    saveInfo.setPosX(drawWidth);
                    saveInfo.setColor(finalColor);
                    saveInfo.setPoxY(drawHeight);
                    saveInfo.setTx(text);
                    saveInfo.setSize(textSize);
                    LibCalendarMonthViewV2Memory.getInstance().setTXYMCache(mYear, mMonth,
                            String.valueOf(i), saveInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 画圆--今天
     */
    private void drawCurDay(Canvas canvas) {
        try {
            for (int i = 0; i < mClickRectList.size(); i++) {
                ClickRectInfo cache = mClickRectList.get(i);
                if (cache.isCanDrawContent()) {
                    if (isToday(i)) {
                        //是否今天
                        int radius = (cache.getEndX() - cache.getStartX()) / 2;
                        int circleStartX = cache.getStartX() + radius;
                        int circleStartY = cache.getStartY() + radius;
                        canvas.save();
                        canvas.drawCircle(circleStartX, circleStartY, radius / 3 * 2, mTodayPaint);
                        canvas.restore();
                        break;
                    }
                }
            }
        } catch (Exception e) {

        }
    }


    /**
     * 画圆--点击事件
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        if (mClickPoint == null) {
            return;
        }
        try {
            float upX = mClickPoint.getClickX();
            float upY = mClickPoint.getClickY();
            for (int i = 0; i < mClickRectList.size(); i++) {
                ClickRectInfo cache = mClickRectList.get(i);
                if (cache.isCanDrawContent()) {
                    if (isInClickRect(cache, upX, upY)) {
                        //是否选中
                        int radius = (cache.getEndX() - cache.getStartX()) / 2;
                        int circleStartX = cache.getStartX() + radius;
                        int circleStartY = cache.getStartY() + radius;
                        canvas.save();
                        canvas.drawCircle(circleStartX, circleStartY, radius / 3 * 2, mClickPaint);
                        canvas.restore();
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 画点--事件的点
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        try {
            if (mEventList == null || mEventList.size() == 0) {
                return;
            }
            for (int i = 0; i < mClickRectList.size(); i++) {
                ClickRectInfo cache = mClickRectList.get(i);
                if (cache.isCanDrawContent()) {
                    LibDateDayInMonthDetailInfoV2 contentInfo = mData.get(i);
                    String contentYMD = contentInfo.getYear() + contentInfo.getMonth() + contentInfo.getDayOfNum();
                    for (String event : mEventList) {
                        if (event.equals(contentYMD)) {
                            int startX = cache.getStartX();
                            int endX = cache.getEndX();
                            int startY = cache.getStartY();
                            int endY = cache.getEndY();
                            int radius = (endX - startX) / 2;
                            if (mClickPoint != null && isInClickRect(cache, mClickPoint.getClickX(), mClickPoint.getClickY())) {
                                //选中的
                                mEventPaint.setColor(getResources().getColor(getEventSelColor()));
                                canvas.drawCircle(startX + radius, endY - radius / 5 * 3, getEventRadius(), mEventPaint);
                            } else if (isToday(i)) {
                                //当前日期
                                mEventPaint.setColor(getResources().getColor(getEventCurColor()));
                                canvas.drawCircle(startX + radius, endY - radius / 5 * 3, getEventRadius(), mEventPaint);
                            } else {
                                //其他当天
                                mEventPaint.setColor(getResources().getColor(getEventExistColor()));
                                canvas.drawCircle(startX + radius, endY - radius / 5 * 3, getEventRadius(), mEventPaint);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 当前数据是否为今天
     */
    private boolean isToday(int position) {
        try {
            LibDateDayInMonthDetailInfoV2 contentInfo = mData.get(position);
            String currentYMD = LibDateCalendarManagerV2.getInstance().getCurYMD(0);
            String contentYMD = contentInfo.getYear() + contentInfo.getMonth() + contentInfo.getDayOfNum();
            if (contentYMD.equals(currentYMD)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断坐标是否在点击事件内
     */
    private boolean isInClickRect(ClickRectInfo cache, float eventX, float eventY) {
        int startX = cache.getStartX();
        int endX = cache.getEndX();
        int startY = cache.getStartY();
        int endY = cache.getEndY();
        if (eventX < endX && eventX > startX && eventY > startY && eventY < endY) {
            return true;
        }
        return false;
    }

    /**
     * 获取文字高度
     */
    private int getTxHeight(String temp, Paint paint) {
        try {
            Integer cacheHeight = LibCalendarMonthViewV2Memory.getInstance().getTXHeight(temp);
            if (cacheHeight != null) {
                return cacheHeight;
            }
            Rect rect = new Rect();
            paint.getTextBounds(temp, 0, temp.length(), rect);
            int height = rect.height();
            setTxHeight(temp, height);
            return height;
        } catch (Exception e) {
            return 0;
        }
    }


    /**
     * 设置文字宽度
     */
    private void setTxHeight(String tx, Integer height) {
        if (TextUtils.isEmpty(tx)) {
            return;
        }
        LibCalendarMonthViewV2Memory.getInstance().setTxHeight(tx, height);
    }

    /**
     * 年份点击事件
     */
    private void dealYearClickEvent() {
        if (mClickListener != null) {
            mClickListener.year(mYear, mMonth);
        }
    }


    /**
     * 月份点击事件
     */
    private void dealMonthClickEvent(float upX, float upY) {
        try {
            for (int i = 0; i < mClickRectList.size(); i++) {
                ClickRectInfo cache = mClickRectList.get(i);
                int startX = cache.getStartX();
                int endX = cache.getEndX();
                int startY = cache.getStartY();
                int endY = cache.getEndY();
                if (upX < endX && upX > startX && upY > startY && upY < endY && cache.isCanClick()) {
                    //点击范围内--通知刷新
                    mClickPoint = new ClickPointInfo();
                    mClickPoint.setClickX(upX);
                    mClickPoint.setClickY(upY);
                    clearTxCacheWithPos();
                    postInvalidate();
                    //回调选中的日期
                    callbackSelInfo(mData.get(i), false);
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 清空某个位置下的数据
     */
    private void clearTxCacheWithPos() {
        try {
            LibCalendarMonthViewV2Memory.getInstance().clearTXYMCache(mYear, mMonth);
        } catch (Exception e) {

        }
    }

    /**
     * 回调选中结果
     */
    private void callbackSelInfo(LibDateDayInMonthDetailInfoV2 info, boolean reduceRepeat) {
        if (info == null || TextUtils.isEmpty(info.toString())) {
            return;
        }
        if (mClickListener == null) {
            return;
        }
        try {
            if (mDayCallBackCache == null) {
                mDayCallBackCache = info;
                //回调
                mClickListener.date(info);
            } else {
                if (reduceRepeat) {
                    if (mDayCallBackCache.toString().equals(info.toString())) {
                        return;
                    }
                }
                mDayCallBackCache = info;
                //回调
                mClickListener.date(info);
            }
        } catch (Exception e) {

        }
    }

    //外部调用---------------------------------------------------------------------------------------

    /***
     * 设置颜色资源
     * */
    public void initXRes() {
        setTitleTxColor(DateMainV2.getInstance().getTitleColor());
        //事件颜色
        setEventCurColor(DateMainV2.getInstance().getEventCur());
        setEventExistColor(DateMainV2.getInstance().getEventDef());
        setEventSelColor(DateMainV2.getInstance().getEventSelCur());
        //当前背景颜色
        setCircleCurColor(DateMainV2.getInstance().getCurTimeBg());
        //点击背景颜色
        setClickCircleColor(DateMainV2.getInstance().getSelBg());
        //文字颜色
        setContentTxSelColor(DateMainV2.getInstance().getSelTx());
        setContentTxNoCurColor(DateMainV2.getInstance().getDefTx());
        setContentTxCurColor(DateMainV2.getInstance().getCurTimeTx());
    }

    /**
     * 设置数据--判断数据，并在视图完全显示后，开始绘制--头部，内容都绘制
     * 必须在绘制完成后，调用
     */
    public void setData(final List<LibDateDayInMonthDetailInfoV2> data, final String year, final String month) {
        if (data == null || data.size() == 0) {
            return;
        }
        mYear = year;
        mMonth = month;
        mData.clear();
        mData.addAll(data);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mWidth = getMeasuredWidth();
                mNeedToNotifyHeight = false;
                postInvalidateDelayed(100);
            }
        }, 100);
    }

    /**
     * 更新事件集合
     */
    public void setEvent(final List<String> eventList) {
        if (eventList == null || eventList.size() == 0) {
            return;
        }
        mWidth = getMeasuredWidth();
        mEventList.clear();
        mEventList.addAll(eventList);
        postInvalidate();
    }

    //监听事件-------------------------------------------------------------------------------------
    public interface OnWidgetParamsCallBack {
        void height(int height);
    }

    public void setOnWidgetParamsCallBack(OnWidgetParamsCallBack listener) {
        mListener = listener;
    }

    public interface OnDateClickListener {
        void date(LibDateDayInMonthDetailInfoV2 info);

        void year(String year, String month);
    }

    public void setOnDateClickListener(OnDateClickListener listener) {
        mClickListener = listener;
    }

    //点击的区域
    public static class ClickPointInfo implements Serializable {
        private float clickX;
        private float clickY;

        public float getClickX() {
            return clickX;
        }

        public void setClickX(float clickX) {
            this.clickX = clickX;
        }

        public float getClickY() {
            return clickY;
        }

        public void setClickY(float clickY) {
            this.clickY = clickY;
        }
    }

    //点击区域集合
    public static class ClickRectInfo implements Serializable {
        private int startX;
        private int endX;
        private int startY;
        private int endY;
        //是否可点击
        private boolean canClick = true;
        //是否需要绘制
        private boolean canDrawContent = true;

        public boolean isCanDrawContent() {
            return canDrawContent;
        }

        public void setCanDrawContent(boolean canDrawContent) {
            this.canDrawContent = canDrawContent;
        }

        public boolean isCanClick() {
            return canClick;
        }

        public void setCanClick(boolean canClick) {
            this.canClick = canClick;
        }

        public int getStartX() {
            return startX;
        }

        public void setStartX(int startX) {
            this.startX = startX;
        }

        public int getEndX() {
            return endX;
        }

        public void setEndX(int endX) {
            this.endX = endX;
        }

        public int getStartY() {
            return startY;
        }

        public void setStartY(int startY) {
            this.startY = startY;
        }

        public int getEndY() {
            return endY;
        }

        public void setEndY(int endY) {
            this.endY = endY;
        }
    }
}
