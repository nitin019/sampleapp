package com.example.ni3.sampleapp.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ni3.sampleapp.R;

public class DetailActivity extends AppCompatActivity {
    TextView mTitle, mBody;
    ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();
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

    private void initViews() {
        mBack=(ImageView)findViewById(R.id.backbtn);
        mTitle = (TextView) findViewById(R.id.titletxt);
        mBody = (TextView) findViewById(R.id.bodytxt);
        if (getIntent().getExtras() != null) {
            mTitle.setText(getIntent().getExtras().getString("TITLE"));
            mBody.setText(getIntent().getExtras().getString("BODY"));
        }
    }
}
