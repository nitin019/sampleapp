package com.example.ni3.sampleapp.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ni3.sampleapp.R;
import com.example.ni3.sampleapp.apicommon.Utility;
import com.example.ni3.sampleapp.Model.AlbumBean;
import com.example.ni3.sampleapp.Model.PostBean;
import com.example.ni3.sampleapp.Model.UserBean;
import com.example.ni3.sampleapp.views.adapter.AlbumAdapter;
import com.example.ni3.sampleapp.views.adapter.PostAdapter;
import com.example.ni3.sampleapp.views.adapter.UsersAdapter;
import com.example.ni3.sampleapp.apicommon.APIs;
import com.example.ni3.sampleapp.apicommon.AsyncHttp;
import com.example.ni3.sampleapp.apicommon.GetAsyncResult;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetAsyncResult, UsersAdapter.ItemClick {
    RequestParams rparams;
    private RecyclerView userList, mAlbumList;
    private ListView mPostList, mTodolist;
    ArrayList<PostBean> mPostArrayList;
    ArrayList<AlbumBean> albumlist;
    ArrayList<UserBean> mUserList;
    public static UsersAdapter.ItemClick itemClick;
    LinearLayout mDatalay;
    TextView mSelectUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemClick = this;
        if (Utility.isNetworkAvailable(this)) {
            getUserData();
        } else {
            Utility.showMsg(this);
        }
        initViews();
        setListeners();

    }

    private void setListeners() {

        mPostList.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        mTodolist.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void initViews() {
        mAlbumList = (RecyclerView) findViewById(R.id.albumslist);
        userList = (RecyclerView) findViewById(R.id.userlist);
        userList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        mAlbumList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        mPostList = (ListView) findViewById(R.id.postlist);
        mTodolist = (ListView) findViewById(R.id.todolist);
        mSelectUser = (TextView) findViewById(R.id.selectuser);
        mDatalay = (LinearLayout) findViewById(R.id.datalay);
    }

    private void getUserData() {
        Utility.showprogress(MainActivity.this, "Loading...");
        new AsyncHttp(this, APIs.users, this, "users", "get");
    }

    @Override
    public void asyncResponse(String result, String masterCall) {
        Utility.dismiss();
        Log.e("Response", "" + result);
        JSONArray jArray = null;
        if (masterCall.equalsIgnoreCase("users")) {
            try {
                jArray = new JSONArray(result);
                mUserList = new ArrayList<>();
                for (int i = 0; i < jArray.length(); i++) {
                    UserBean uBean = new UserBean();
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    String id = jsonObject.optString("id");
                    String name = jsonObject.optString("name");
                    String username = jsonObject.optString("username");
                    String email = jsonObject.optString("email");

                    uBean.setEmail(email);
                    uBean.setId(id);
                    uBean.setUsername(username);
                    uBean.setName(name);
                    mUserList.add(uBean);
                }
                UsersAdapter uAdapter = new UsersAdapter(mUserList);
                userList.setAdapter(uAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (masterCall.equalsIgnoreCase("posts")) {
            Log.e("Response", "posts" + result);
            mDatalay.setVisibility(View.VISIBLE);
            mSelectUser.setVisibility(View.GONE);
            setResponse(result, mPostList, false);
            if (Utility.isNetworkAvailable(this)) {
                new AsyncHttp(this, APIs.albums + userid, this, "albums", "get");
            } else {
                Utility.showMsg(this);
            }

        } else if (masterCall.equalsIgnoreCase("albums")) {
            Log.e("Response", "Albumms" + result);
            try {
                jArray = new JSONArray(result);
                albumlist = new ArrayList<>();
                for (int i = 0; i < jArray.length(); i++) {
                    AlbumBean aBean = new AlbumBean();
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    aBean.setId(jsonObject.optString("id"));
                    aBean.setUserId(jsonObject.optString("userId"));
                    aBean.setTitle(jsonObject.optString("title"));
                    albumlist.add(aBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AlbumAdapter albumAdapter = new AlbumAdapter(this, albumlist);
            mAlbumList.setAdapter(albumAdapter);
            if (Utility.isNetworkAvailable(this)) {
                new AsyncHttp(this, APIs.todos + userid, this, "todos", "get");
            } else {
                Utility.showMsg(this);

            }
        } else if (masterCall.equalsIgnoreCase("todos")) {
            Log.e("Response", "Todos" + result);
            setResponse(result, mTodolist, true);
        }
    }

    private void setResponse(String result, ListView mPostList, Boolean todo) {
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(result);
            mPostArrayList = new ArrayList<>();
            for (int i = 0; i < jArray.length(); i++) {
                PostBean pBean = new PostBean();
                JSONObject jsonObject = jArray.getJSONObject(i);
                pBean.setBody(jsonObject.optString("body"));
                pBean.setId(jsonObject.optString("id"));
                pBean.setUserId(jsonObject.optString("userId"));
                pBean.setTitle(jsonObject.optString("title"));

                mPostArrayList.add(pBean);
            }
            PostAdapter pAdapter = new PostAdapter(MainActivity.this, 0, mPostArrayList, todo);
            mPostList.setAdapter(pAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    String userid;

    @Override
    public void itemClick(int p) {

        if(Utility.isNetworkAvailable(this)) {
            Utility.showprogress(MainActivity.this, "Loading...");
            userid = mUserList.get(p).getId();
            new AsyncHttp(this, APIs.posts + userid, this, "posts", "get");
        }
        else{
            Utility.showMsg(this);
        }
        }
}
