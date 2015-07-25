package com.appxperts.textfxexperiment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class MainActivity extends ActionBarActivity {

    int SHARE_INTENT = 2;
    int FILE_CHOOSE_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doWork(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, FILE_CHOOSE_RESULT_CODE);

        Toast.makeText(getApplicationContext(), "Chosing file !", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FILE_CHOOSE_RESULT_CODE) {
            if (resultCode == RESULT_OK) {

                // String video_path = "file:///storage/sdcard0/bird_1.mp4";

                Intent shareIntent = new Intent(Intent.ACTION_SEND);

                shareIntent.setType("video/mp4");
                //shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(video_path));
                shareIntent.putExtra(Intent.EXTRA_STREAM, data.getData());
                //  shareIntent.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");

                if (isPackageExisted("com.android.mms")) {
                    shareIntent.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");
                }

                startActivityForResult(Intent.createChooser(shareIntent, "Share Your Video"), SHARE_INTENT);
            }
        }

    }

    public boolean isPackageExisted(String targetPackage) {

        PackageManager pm = getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage,
                    PackageManager.GET_META_DATA);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public void downloadFile(View v) {

        String url = "http://www.normandiebulle.com/wp-content/uploads/2014/07/tintin.jpg";

        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, url, new Response.Listener<byte[]>() {
            @Override
            public void onResponse(byte[] response) {
                Log.d("GUFRAN", "Got Response  " + response.length);
                OutputStream f = null;
                try {
                    String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                    f = new FileOutputStream(baseDir + File.separator + "guffyTinTin.jpg");
                    f.write(response); //your bytes
                    f.flush();
                    f.close();
                    Log.d("GUFRAN", "Got Response  " + response.length);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("GUFRAN", "Volley Error ");
            }
        }, null);

//        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(),
//                new HurlStack());
//        mRequestQueue.add(request);

        TextFXExperimentApplication.getInstance().addToRequestQueue(request);


    }


}
