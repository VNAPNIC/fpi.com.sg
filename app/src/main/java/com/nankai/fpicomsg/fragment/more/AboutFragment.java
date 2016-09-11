package com.nankai.fpicomsg.fragment.more;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.nankai.fpicomsg.BaseFragment;
import com.nankai.fpicomsg.MainActivity;
import com.nankai.fpicomsg.R;
import com.nankai.fpicomsg.customview.NankaiWebView;

/**
 * Created by Nankai on 9/11/2016.
 */
public class AboutFragment extends BaseFragment {
    private NankaiWebView webView;
    private String conver_url = "file:///android_asset/webviews/%s.html";

    public static AboutFragment newInstantiate() {
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setupView() {
        return R.layout.fragment_about;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = (NankaiWebView) view.findViewById(R.id.webView);
        webView.getSettings().setDomStorageEnabled(true);
        String formatUrl = String.format(conver_url, "about");
        Log.d("Nankai", formatUrl);
        webView.loadUrl(formatUrl);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (isAdded()) {
            ((MainActivity) context).setTitle(getActivity().getResources().getString(R.string.about));
            ((MainActivity) context).enableBack(true);
        }
    }
}
