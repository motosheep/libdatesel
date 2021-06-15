package com.north.light.libdatesel.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by lzt
 * time 2021/6/15 11:33
 *
 * @author lizhengting
 */
public abstract class LibDateBaseFragment extends Fragment {
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = LayoutInflater.from(getContext()).inflate(getLayoutId(), container, false);
        }
        return mRootView;
    }

    protected abstract int getLayoutId();
}
