package com.example.ni3.sampleapp.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.ni3.sampleapp.Model.ImageBean;
import com.example.ni3.sampleapp.R;
import com.example.ni3.sampleapp.apicommon.Utility;
import com.example.ni3.sampleapp.views.adapter.ImageAdapter;
import com.example.ni3.sampleapp.apicommon.APIs;
import com.example.ni3.sampleapp.apicommon.AsyncHttp;
import com.example.ni3.sampleapp.apicommon.GetAsyncResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImagesActivity extends AppCompatActivity implements GetAsyncResult {
    RecyclerView mRecycler;
    ArrayList<ImageBean> imageList;
    ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        initViews();
        if (Utility.isNetworkAvailable(this)) {
            getImages();
        } else {
            Utility.showMsg(this);
        }

        setListeners();
    }

    private void setListeners() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void getImages() {
        Utility.showprogress(ImagesActivity.this, "Loading...");
        new AsyncHttp(this, APIs.photos + getIntent().getExtras().getString("ALBUMID"), this, "Images", "get");
    }


    private void initViews() {
        mBack = (ImageView) findViewById(R.id.backbtn);
        mRecycler = (RecyclerView) findViewById(R.id.mlist);
    }

    @Override
    public void asyncResponse(String result, String masterCall) {
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(result);
            imageList = new ArrayList<>();
            for (int i = 0; i < jArray.length(); i++) {
                ImageBean pBean = new ImageBean();
                JSONObject jsonObject = jArray.getJSONObject(i);
                pBean.setAlbumId(jsonObject.optString("albumId"));
                pBean.setId(jsonObject.optString("id"));
                pBean.setUrl(jsonObject.optString("url"));
                pBean.setTitle(jsonObject.optString("title"));
                pBean.setThumbnailUrl(jsonObject.optString("thumbnailUrl"));
                imageList.add(pBean);
            }
            mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
            ImageAdapter imageAdapter = new ImageAdapter(this, imageList);
            mRecycler.setAdapter(imageAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
