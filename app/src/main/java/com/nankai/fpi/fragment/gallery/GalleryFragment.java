package com.nankai.fpi.fragment.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import  com.nankai.fpi.AppController;
import  com.nankai.fpi.BaseFragment;
import  com.nankai.fpi.MainActivity;
import  com.nankai.fpi.R;
import  com.nankai.fpi.common.State;
import  com.nankai.fpi.customview.FontTextView;
import  com.nankai.fpi.model.Datum;
import  com.nankai.fpi.model.Gallery;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by namIT on 9/9/2016.
 */
public class GalleryFragment extends BaseFragment {
    public static final String KEY_ACTION = "action";
    public static final String KEY_PAGE = "page";
    public static final String KEY_TIME = "time";
    public static final String KEY_ACCESS_KEY = "access_key";
    private String tag_json_obj = GalleryFragment.class.getName();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GalleryAdapter galleryAdapter;

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
        initView(view);
        initData();
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.gallery_list);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        galleryAdapter = new GalleryAdapter(getActivity());
        recyclerView.setAdapter(galleryAdapter);
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
                            try {
                                Gallery gallery = new Gallery();
                                JSONObject jsonObj = new JSONObject(response.toString());
                                gallery.message = jsonObj.getString("message");
                                gallery.status = jsonObj.getBoolean("status");
                                gallery.code = jsonObj.getInt("code");
                                // Getting JSON Array node
                                JSONArray datas = jsonObj.getJSONArray("data");
                                for (int i = 0; i < datas.length(); i++) {
                                    JSONObject c = datas.getJSONObject(i);
                                    Datum datum = new Datum();
                                    datum.countImages = c.getInt("count_images");
                                    if (c.getInt("count_images") > 0) {
                                        datum.title = c.getString("title");
                                        datum.postId = c.getInt("post_id");
                                        datum.featureImage = c.getString("feature_image");
                                        datum.termName = c.getString("term_name");
                                        gallery.data.add(datum);
                                    } else {
                                        //TODO
                                    }
                                }
                                Log.d("Nankai", "gallery.data: " + gallery.data.size());
                                galleryAdapter.setDatums(gallery.data);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                    params.put(KEY_ACTION, "gallery_api_list");
                    params.put(KEY_PAGE, "0");
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private class GalleryViewHolder extends RecyclerView.ViewHolder {
        public FontTextView termName;
        public ImageView imgNetWorkView;
        public CardView viewItem;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            termName = (FontTextView) itemView.findViewById(R.id.gallery_title);
            imgNetWorkView = (ImageView) itemView.findViewById(R.id.gallery_icon);
            viewItem = (CardView) itemView.findViewById(R.id.view_item);
        }
    }

    private class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {
        private List<Datum> datums;
        private Context context;

        public GalleryAdapter(Context context) {
            this.context = context;
            datums = new ArrayList<>();
        }

        @Override
        public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
            GalleryViewHolder viewHolder = new GalleryViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(GalleryViewHolder holder, int position) {
            if (datums != null && datums.size() > 0) {
                Datum item = datums.get(position);
                Picasso.with(context).load(item.featureImage).error(android.R.drawable.stat_notify_error).noFade().placeholder(R.drawable.images)
                        .into(holder.imgNetWorkView);
                holder.termName.setText(item.termName);
                holder.viewItem.setOnClickListener(new OnClick(item.postId + ""));
            } else {
                //TODO
            }
        }

        public void setDatums(List<Datum> datums) {
            if (null == datums)
                datums = new ArrayList<>();
            else
                this.datums.clear();

            this.datums.addAll(datums);
            notifyDataSetChanged();
        }

        private class OnClick implements View.OnClickListener {
            private String postId;

            public OnClick(String postId) {
                this.postId = postId;
            }

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), GalleryDetailActivity.class);
                i.putExtra(GalleryDetailActivity.POST_ID, postId);
                getActivity().startActivity(i);
            }
        }

        @Override
        public int getItemCount() {
            if (null == datums)
                return 0;
            return datums.size();
        }
    }
}
