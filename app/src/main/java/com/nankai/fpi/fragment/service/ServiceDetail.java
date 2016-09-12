package com.nankai.fpi.fragment.service;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import  com.nankai.fpi.BaseFragment;
import  com.nankai.fpi.MainActivity;
import  com.nankai.fpi.R;
import  com.nankai.fpi.customview.NankaiWebView;

/**
 * Created by Nankai on 9/9/2016.
 */
public class ServiceDetail extends BaseFragment {
    public static final String URL_LOAD = "key.url";
    private NankaiWebView webView;
    private String url;
    private String conver_url = "file:///android_asset/webviews/%s.html";

    public static ServiceDetail newInstantiate(String url) {
        ServiceDetail fragment = new ServiceDetail();
        fragment.setArguments(newBundle(url));
        return fragment;
    }

    private static Bundle newBundle(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL_LOAD, url);
        return bundle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initial data from bundle
        if (savedInstanceState != null) {
            initDataFromBundle(savedInstanceState);
        } else {
            initDataFromBundle(getArguments());
        }
    }

    private void initDataFromBundle(Bundle bundle) {
        url = bundle.getString(URL_LOAD);
    }

    @Override
    protected int setupView() {
        return R.layout.fragment_service_detail;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = (NankaiWebView) view.findViewById(R.id.webView);
        webView.getSettings().setDomStorageEnabled(true);
        String formatUrl = String.format(conver_url, url);
        Log.d("Nankai", formatUrl);
        webView.loadUrl(formatUrl);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (isAdded()) {
            ((MainActivity) context).setTitle(getActivity().getResources().getString(R.string.menu_service_detail));
            ((MainActivity) context).enableBack(true);
        }
    }
}
