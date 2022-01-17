package com.north.light.libdatesel.v2.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: lzt
 * @Date: 2022/1/11 12:36
 * @Description:
 */
public class LibCalendarRecyV2 extends RecyclerView {
    //触摸x pos
    private float touchX;
    private OnRecyTouchListener listener;
    //左右滑动标识
    private int slideTag = 1;


    public LibCalendarRecyV2(@NonNull Context context) {
        super(context);
        init();
    }

    public LibCalendarRecyV2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LibCalendarRecyV2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        if (touchX > event.getX()) {
                            slideTag = 1;
                        } else {
                            slideTag = 2;
                        }
                        touchX = 0;
                        break;
                    case MotionEvent.ACTION_DOWN:
                        touchX = event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (touchX == 0) {
                            touchX = event.getX();
                        }
                        break;
                }
                return false;
            }
        });
        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case SCROLL_STATE_IDLE:
                        if (listener != null) {
                            listener.stop(recyclerView, newState, slideTag);
                        }
                        break;
                    case SCROLL_STATE_DRAGGING:
                    case SCROLL_STATE_SETTLING:
                        if (listener != null) {
                            listener.run(recyclerView, newState, slideTag);
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.listener = null;
    }

    public void setOnRecyTouchListener(OnRecyTouchListener listener) {
        this.listener = listener;
    }

    //监听org:1左边 2右边
    public interface OnRecyTouchListener {

        public void stop(RecyclerView recyclerView, int newState, int org);

        public void run(RecyclerView recyclerView, int newState, int org);
    }
}
