package com.nankai.fpi;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import  com.nankai.fpi.common.Common;
import  com.nankai.fpi.customview.FontTextView;
import  com.nankai.fpi.fragment.MainFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FontTextView title;
    private ImageView back;
    private Common common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        common = new Common();
        title = (FontTextView) findViewById(R.id.title);
        back = (ImageView) findViewById(R.id.back);
        common.addFragment(this, MainFragment.newInstantiate(), R.id.content);
        back.setOnClickListener(this);
    }

    public void setTitle(String strTitle) {
        title.setText(strTitle);
    }

    public void enableBack(boolean isOn) {
        if (isOn) {
            back.setVisibility(View.VISIBLE);
        } else {
            back.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            finish();
        } else {
            super.onBackPressed();
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
            if (fragment instanceof MainFragment) {
                enableBack(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
