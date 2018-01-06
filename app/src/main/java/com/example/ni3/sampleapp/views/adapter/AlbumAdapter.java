package com.example.ni3.sampleapp.views.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ni3.sampleapp.Model.AlbumBean;
import com.example.ni3.sampleapp.R;
import com.example.ni3.sampleapp.views.activities.ImagesActivity;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {

    private List<AlbumBean> albumList;
Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mUser;
        TextView mTxt;

        public MyViewHolder(View view) {
            super(view);
            mUser =itemView.findViewById(R.id.imguser);
            mTxt=itemView.findViewById(R.id.txt);
        }
    }


    public AlbumAdapter(Context context, List<AlbumBean> albumList) {
        this.albumList = albumList;
        this.context=context;
    }

    @Override
    public AlbumAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.albumitem, parent, false);


        return new AlbumAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AlbumAdapter.MyViewHolder holder, final int position) {
    holder.mTxt.setText(albumList.get(position).getTitle());
    holder.mUser.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            context.startActivity(new Intent(context, ImagesActivity.class).putExtra("ALBUMID",albumList.get(position).getId()));
        }
    });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

}
