package com.example.ni3.sampleapp.views.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ni3.sampleapp.Model.ImageBean;
import com.example.ni3.sampleapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private ArrayList<ImageBean> mData;
    private LayoutInflater mInflater;
    Activity con;

    // data is passed into the constructor
    public ImageAdapter(Activity context, ArrayList<ImageBean> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.con = context;

    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.imageitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        String image = mData.get(position).getThumbnailUrl();


        //holder.mPic.setEnabled(true);
        if (image != null && !image.equalsIgnoreCase(""))
            Picasso.with(con).load(image).resize(200, 200).into(holder.mPic);



    }


    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        ImageView mPic;



        public ViewHolder(View itemView) {
            super(itemView);
            mPic = (ImageView) itemView.findViewById(R.id.img);


        }

    }


    // convenience method for getting data at click position
    public String getItem(int id) {
        return "";
    }


}



