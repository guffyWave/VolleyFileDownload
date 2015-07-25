package com.appxperts.textfxexperiment;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by gufran on 7/24/15.
 */
public class TextFXExperimentApplication extends Application {

    private static TextFXExperimentApplication textFXExperimentApplication;
    private RequestQueue mRequestQueue;
    private static String TAG = "TextFXExperimentApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        textFXExperimentApplication = this;
    }

    public static TextFXExperimentApplication getInstance() {
        return textFXExperimentApplication;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}
