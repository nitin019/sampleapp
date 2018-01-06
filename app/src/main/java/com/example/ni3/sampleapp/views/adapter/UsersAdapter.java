package com.example.ni3.sampleapp.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ni3.sampleapp.views.activities.MainActivity;
import com.example.ni3.sampleapp.Model.UserBean;
import com.example.ni3.sampleapp.R;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {
 
    private List<UserBean> moviesList;
 
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mUser;
 
        public MyViewHolder(View view) {
            super(view);
            mUser =itemView.findViewById(R.id.imguser);
        }
    }


    public UsersAdapter(List<UserBean> moviesList) {
        this.moviesList = moviesList;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.itemClick.itemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
    public interface ItemClick{
        void itemClick(int p);
    }
}