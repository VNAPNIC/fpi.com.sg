package com.nankai.fpicomsg.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nankai.fpicomsg.BaseFragment;
import com.nankai.fpicomsg.R;

/**
 * Created by namIT on 9/9/2016.
 */
public class GalleryFragment extends BaseFragment {

    public static GalleryFragment newInstantiate() {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setupView() {
        return R.layout.fragment_gallery;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
