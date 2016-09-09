package com.nankai.fpicomsg.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontTextView extends TextView {

    public FontTextView(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "MyriadPro-Regular.otf");
        setTypeface(tf, Typeface.NORMAL);
    }
}