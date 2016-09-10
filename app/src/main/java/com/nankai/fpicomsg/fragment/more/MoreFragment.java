package com.nankai.fpicomsg.fragment.more;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nankai.fpicomsg.BaseFragment;
import com.nankai.fpicomsg.R;

/**
 * Created by namIT on 9/9/2016.
 */
public class MoreFragment extends BaseFragment {

    public static MoreFragment newInstantiate() {
        MoreFragment fragment = new MoreFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setupView() {
        return R.layout.fragment_more;
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
