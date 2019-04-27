package com.example.backgroundchanger.send;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.backgroundchanger.activity.ChooseBackground;
import com.example.backgroundchanger.activity.ProcessActivity;

public class SendImageToActivity {
    private static final String TAG = "SendImageToActivity";
    public static void sendPathToActivityForProcess(String path, Context context) {
        Log.d(TAG,"sendPathToActivityForProcess");
        Intent intent = new Intent(context, ProcessActivity.class);
        intent.putExtra("pathToImage", path);
        context.startActivity(intent);
    }

    public static void sendPathToActivityForChooseBackground(String path, Context context) {
        Log.d(TAG,"sendPathToActivityForChooseBackground");
        Intent intent = new Intent(context, ChooseBackground.class);
        intent.putExtra("pathToImage", path);
        context.startActivity(intent);
    }
}
