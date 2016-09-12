package com.nankai.fpi.fragment.more;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import  com.nankai.fpi.BaseFragment;
import  com.nankai.fpi.MainActivity;
import  com.nankai.fpi.R;
import  com.nankai.fpi.customview.FontTextView;

/**
 * Created by namIT on 9/9/2016.
 */
public class MoreFragment extends BaseFragment implements View.OnClickListener {
    private FontTextView about, contact;

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
        about = (FontTextView) view.findViewById(R.id.about);
        contact = (FontTextView) view.findViewById(R.id.contact);
        about.setOnClickListener(this);
        contact.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about:
                common.replaceFragment((MainActivity) getActivity(), AboutFragment.newInstantiate(), R.id.content);
                break;
            case R.id.contact:
                common.replaceFragment((MainActivity) getActivity(), ContactFragment.newInstantiate(), R.id.content);
                break;

        }
    }
}
