package com.north.light.libdatesel.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.north.light.libdatesel.R;
import com.north.light.libdatesel.widget.LibDateDateNumberPickerView;


/**
 * author:li
 * date:2020/6/21
 * desc:自定义number picker
 */
public class LibDateDivNumberPicker extends LibDateDateNumberPickerView {

    public LibDateDivNumberPicker(Context context) {
        super(context);
        initData();
    }

    public LibDateDivNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public LibDateDivNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        this.setDividerColor(getContext().getResources().getColor(R.color.color_FFFFFF));
        this.setNormalTextColor(getContext().getResources().getColor(R.color.color_000000));
        this.setSelectedTextColor(getContext().getResources().getColor(R.color.color_3385FF));
    }

    @Override
    public void setShownCount(int mShownCount) {
        super.setShownCount(mShownCount);
    }
}