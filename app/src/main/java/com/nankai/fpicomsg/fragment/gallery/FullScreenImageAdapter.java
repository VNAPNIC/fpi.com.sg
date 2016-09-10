package com.nankai.fpicomsg.fragment.gallery;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nankai.fpicomsg.R;
import com.nankai.fpicomsg.customview.TouchImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by Nankai on 9/10/2016.
 */
public class FullScreenImageAdapter extends PagerAdapter {

    private Activity activity;
    private List<String> imagePaths;
    private LayoutInflater inflater;

    public FullScreenImageAdapter(Activity activity) {
        imagePaths = new ArrayList<>();
        this.activity = activity;
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
            final TouchImageView imgDisplay;
            Button btnClose;
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                    false);

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

            Picasso.with(activity)
                    .load(imagePaths.get(position))
                    .error(android.R.drawable.stat_notify_error)
                    .transform(transformation)
                    .into(imgDisplay, new Callback() {
                        @Override
                        public void onSuccess() {
                            //TODO progress
                        }

                        @Override
                        public void onError() {
                           //TODO progress
                        }
                    });

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