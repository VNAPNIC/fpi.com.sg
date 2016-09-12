package com.nankai.fpi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import  com.nankai.fpi.common.Common;

/**
 * Created by namIT on 9/9/2016.
 */
public abstract class BaseFragment extends Fragment {
    protected View rootView;
    protected Common common;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        common = new Common();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(setupView(),container,false);
        return rootView;
    }

    protected abstract int setupView();
}
