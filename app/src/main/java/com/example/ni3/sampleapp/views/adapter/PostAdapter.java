package com.example.ni3.sampleapp.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ni3.sampleapp.Model.PostBean;
import com.example.ni3.sampleapp.R;
import com.example.ni3.sampleapp.views.activities.DetailActivity;

import java.util.ArrayList;

/**
 * Created by Ni3 on 04-Jan-18.
 */

public class PostAdapter extends ArrayAdapter {
    ArrayList<PostBean> mList;
    Context context;
    Boolean todolist;

    public PostAdapter(@NonNull Context context, int resource, ArrayList<PostBean> mList, Boolean Todolist) {
        super(context, resource);
        this.context = context;
        this.mList = mList;
        this.todolist = Todolist;
    }

    @Override
    public int getCount() {
        return mList.size();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.textlay, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        if (!todolist)
            textView.setText(mList.get(position).getBody());
        else
            textView.setText(mList.get(position).getTitle());

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!todolist)
                    context.startActivity(new Intent(context, DetailActivity.class).putExtra("TITLE", mList.get(position).getTitle()).putExtra("BODY", mList.get(position).getBody()));
            else{

                }
            }
        });

        return rowView;
    }

}
