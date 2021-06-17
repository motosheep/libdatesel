package com.north.light.libdatesel.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.RecyclerView.ItemDecoration;

/**
 * Created by lzt
 * time 2021/6/17 10:54
 *
 * @author lizhengting
 * 描述：
 */
public class LibMonthInYearDecoration extends ItemDecoration {
    int top = 0;
    int left = 0;
    int right = 0;

    public LibMonthInYearDecoration(int top, int left, int right) {
        this.top = top;
        this.left = left;
        this.right = right;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildLayoutPosition(view);

        try {
            int rest = position % 3;
            outRect.top = top;
            if (rest == 0) {
                outRect.left = left;
                outRect.right = right / 2;
            } else if (rest == 2) {
                outRect.left = left / 2;
                outRect.right = right;
            } else {
                outRect.left = left / 2;
                outRect.right = right / 2;
            }
        } catch (Exception e) {

        }
    }
}
