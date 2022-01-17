package com.north.light.libdatesel.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * @Author: lzt
 * @Date: 2022/1/3 16:16
 * @Description:日期选择库自定义viewpager
 */
public class LibDateViewPager extends ViewPager {
    private ValueAnimator mHeightAnim;


    public LibDateViewPager(@NonNull Context context) {
        super(context);
    }

    public LibDateViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mHeightAnim != null) {
            mHeightAnim.end();
        }
        clearAnimation();
        super.onDetachedFromWindow();
    }

    /**
     * 设置高度
     */
    public void setAnimHeight(int targetHeight, final LinearLayout.LayoutParams layoutParams) {
        try {
            clearAnimation();
            if (targetHeight < 0) {
                return;
            }
            if (mHeightAnim != null) {
                mHeightAnim.end();
            }
            int trueHeight = getMeasuredHeight();
            mHeightAnim = ValueAnimator.ofFloat(trueHeight, targetHeight);
            mHeightAnim.setDuration(200);
            mHeightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float currentValue = (Float) animation.getAnimatedValue();
                    layoutParams.height = (int) currentValue;
                    setLayoutParams(layoutParams);
                }
            });
            mHeightAnim.start();
        } catch (Exception e) {

        }
    }
}
