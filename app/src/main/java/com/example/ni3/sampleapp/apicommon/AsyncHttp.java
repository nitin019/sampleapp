package com.example.ni3.sampleapp.apicommon;

import android.content.Context;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;


public class AsyncHttp {

    //  private ProgressDialog progressDialog;
    private GetAsyncResult listener;
    private String mastercall, type;
    RequestParams params;
    ;

    public AsyncHttp(Context context, String url, GetAsyncResult listener, String mastercall, String type) {

        this.mastercall = mastercall;
        this.listener = listener;
        this.mastercall = mastercall;
        this.listener = listener;
        this.type = type;
        info(context, params, url);
    }

    private void info(Context context, RequestParams params, String url) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(60000);
        if (type.equalsIgnoreCase("get")) {
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Utility.dismiss();
                    listener.asyncResponse(new String(responseBody), mastercall);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Utility.dismiss();
                    if (responseBody!=null)
                    listener.asyncResponse(new String(responseBody), mastercall);
                }
            });

        } else {
            client.post(url, params, new AsyncHttpResponseHandler() {


                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Utility.dismiss();
                    listener.asyncResponse(new String(responseBody), mastercall);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Utility.dismiss();
                    try {
                        if (responseBody != null)
                            listener.asyncResponse(new String(responseBody), mastercall);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onRetry(int retryNo) {
                    // called when request is retried
                }
            });


        }
    }
}



