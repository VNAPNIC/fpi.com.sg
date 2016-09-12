package com.nankai.fpi.fragment.gallery;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import  com.nankai.fpi.MainActivity;
import  com.nankai.fpi.R;
import  com.nankai.fpi.customview.TouchImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by Nankai on 9/10/2016.
 */
public class FullScreenImageAdapter extends PagerAdapter {

    private Handler handler;
    private Activity activity;
    private List<String> imagePaths;
    private LayoutInflater inflater;
    private ProgressBar progressBar;

    public FullScreenImageAdapter(Activity activity) {
        imagePaths = new ArrayList<>();
        this.activity = activity;
        handler = new Handler();
    }

    @Override
    public int getCount() {
        if (null == imagePaths)
            return 0;
        return this.imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (null != imagePaths && imagePaths.size() > 0) {
            handler.removeCallbacks(null);
            final TouchImageView imgDisplay;
            Button btnClose;
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                    false);
            progressBar = (ProgressBar) viewLayout.findViewById(R.id.progress);
            imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
            btnClose = (Button) viewLayout.findViewById(R.id.btnClose);

            Transformation transformation = new Transformation() {
                @Override
                public Bitmap transform(Bitmap source) {
                    int targetWidth = imgDisplay.getWidth();

                    double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                    int targetHeight = (int) (targetWidth * aspectRatio);
                    Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                    if (result != source) {
                        // Same bitmap is returned if sizes are the same
                        source.recycle();
                    }
                    return result;
                }

                @Override
                public String key() {
                    return "transformation" + " desiredWidth";
                }
            };

            if (position == 0) {
                progressBar.setVisibility(View.GONE);
                Picasso.with(activity)
                        .load(imagePaths.get(position))
                        .error(android.R.drawable.stat_notify_error)
                        .placeholder(R.drawable.progress_animation)
                        .transform(transformation)
                        .into(imgDisplay);
            } else {
                progressBar.setVisibility(View.VISIBLE);
                Picasso.with(activity)
                        .load(imagePaths.get(position))
                        .fit()
                        .centerCrop()
                        .error(android.R.drawable.stat_notify_error)
                        .transform(transformation)
                        .into(imgDisplay
                                , new Callback() {
                            @Override
                            public void onSuccess() {
                                //TODO progress
                                Log.d("Nankai", "onSuccess");
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                //TODO progress
                                Log.d("Nankai", "onError");
                                progressBar.setVisibility(View.GONE);
                            }


                        });
            }
            // close button click event
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });

            ((ViewPager) container).addView(viewLayout);
            return viewLayout;
        } else {
            return new View(activity);
        }
    }

    public void setImagePaths(List<String> imagePaths) {
        if (null == imagePaths)
            imagePaths = new ArrayList<>();
        else
            this.imagePaths.clear();

        this.imagePaths.addAll(imagePaths);
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}