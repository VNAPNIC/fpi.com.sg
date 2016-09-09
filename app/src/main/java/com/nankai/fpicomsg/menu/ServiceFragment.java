package com.nankai.fpicomsg.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.nankai.fpicomsg.BaseFragment;
import com.nankai.fpicomsg.R;
import com.nankai.fpicomsg.customview.FontTextView;
import com.nankai.fpicomsg.model.ServiceModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by namIT on 9/9/2016.
 */
public class ServiceFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ServiceAdapter serviceAdapter;
    private Gson gson;

    public static ServiceFragment newInstantiate() {
        ServiceFragment fragment = new ServiceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
    }

    @Override
    protected int setupView() {
        return R.layout.fragment_service;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.listview_service);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        serviceAdapter = new ServiceAdapter(getActivity());
        recyclerView.setAdapter(serviceAdapter);
        try {
            serviceAdapter.setServiceModelList(modelList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<ServiceModel> modelList() throws Exception {
        List<ServiceModel> serviceModels = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonElement rootElement = parser.parse(getJson(getActivity(), "service.json"));
        Log.d("Nankai", rootElement.toString());
        JsonArray mArrayJson = rootElement.getAsJsonArray();
        Log.d("Nankai", mArrayJson.toString());
        for (JsonElement mElement : mArrayJson) {
            ServiceModel listModel = gson.fromJson(mElement, ServiceModel.class);
            serviceModels.add(listModel);
        }
        return serviceModels;
    }

    public String getJson(Context context, String json) {
        String jsonString = parseFileToString(context, json);
        return jsonString;
    }

    public String parseFileToString(Context context, String filename) {
        try {
            InputStream stream = context.getAssets().open(filename);
            int size = stream.available();

            byte[] bytes = new byte[size];
            stream.read(bytes);
            stream.close();

            return new String(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private static class ServiceViewHolder extends RecyclerView.ViewHolder {
        public ImageView banner;
        public FontTextView title;
        public FontTextView content;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            this.banner = (ImageView) itemView.findViewById(R.id.banner_service);
            this.title = (FontTextView) itemView.findViewById(R.id.title_service);
            this.content = (FontTextView) itemView.findViewById(R.id.content_service);
        }
    }

    private class ServiceAdapter extends RecyclerView.Adapter<ServiceViewHolder> {
        private List<ServiceModel> serviceModelList;

        public ServiceAdapter(Context context) {
            serviceModelList = new ArrayList<>();
        }

        @Override
        public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_view, parent, false);
            ServiceViewHolder serviceModel = new ServiceViewHolder(view);
            return serviceModel;
        }

        @Override
        public void onBindViewHolder(ServiceViewHolder holder, int position) {
            if (null != serviceModelList && serviceModelList.size() > 0) {
                ServiceModel model = serviceModelList.get(position);
//                holder.banner.setBackgroundResource(model.image);
                holder.title.setText(Html.fromHtml(model.name));
                holder.content.setText(Html.fromHtml(model.text));
            } else {
                //TODO
            }
        }

        public void setServiceModelList(List<ServiceModel> serviceModelList) {
            if (null == serviceModelList)
                serviceModelList = new ArrayList<>();
            else
                this.serviceModelList.clear();

            this.serviceModelList.addAll(serviceModelList);
        }

        @Override
        public int getItemCount() {
            if (null == serviceModelList)
                return 0;
            return serviceModelList.size();
        }
    }
}
