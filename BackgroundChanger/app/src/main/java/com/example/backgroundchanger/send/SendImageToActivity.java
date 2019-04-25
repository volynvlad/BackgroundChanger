package com.example.backgroundchanger.send;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.backgroundchanger.activity.ProcessActivity;

public class SendImageToActivity {
    private static final String TAG = "SendImageToActivity";
    public static void sendPathToActivity(String path, Context context) {
        Log.d(TAG,"sendPathToActivity");
        Intent intent = new Intent(context, ProcessActivity.class);
        intent.putExtra("pathToImage", path);
        context.startActivity(intent);
    }
}
