package com.nankai.fpi.fragment.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import  com.nankai.fpi.AppController;
import  com.nankai.fpi.R;
import  com.nankai.fpi.common.Common;
import  com.nankai.fpi.common.State;
import  com.nankai.fpi.model.GralleryDetail;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nankai on 9/10/2016.
 */
public class GalleryDetailActivity extends AppCompatActivity {

    private Common common;
    public static final String KEY_ACTION = "action";
    public static final String KEY_POST_ID = "post_id";
    public static final String KEY_TIME = "time";
    public static final String KEY_ACCESS_KEY = "access_key";

    private String tag_json_obj = GalleryDetailActivity.class.getName();
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;
    private String postId;
    public static final String POST_ID = "key.post";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gallery_detail);
        common = new Common();
        Intent i = getIntent();
        postId = i.getStringExtra(POST_ID);

        initView();
        initData();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new FullScreenImageAdapter(this);
        viewPager.setAdapter(adapter);
    }

    private void initData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        final String currentDate = sdf.format(new Date());
        final String api = currentDate + State.API_KEY;
        try {
            final String md5 = common.getMd5(api);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, State.URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response.toString());
                            Gson gson = new Gson();
                            GralleryDetail gralleryDetail = gson.fromJson(response.toString(), GralleryDetail.class);
                            Log.d("Nankai", "gallery.data.detail : " + gralleryDetail.data.src.size());
                            adapter.setImagePaths(gralleryDetail.data.src);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error", error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_ACTION, "api_gallery_detail");
                    params.put(KEY_POST_ID, postId);
                    params.put(KEY_TIME, currentDate);
                    params.put(KEY_ACCESS_KEY, md5);
                    Log.d("Request", new JSONObject(params).toString());
                    return params;
                }

            };
            AppController.getInstance().addToRequestQueue(stringRequest,
                    tag_json_obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
