package com.example.backgroundchanger.add;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.File;

public class AddToGallery {
    private static final String TAG = "AddToGallery";
    public static void galleryAddPic(File file, Context context) {
        Log.d(TAG,"galleryAddPic");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }
}
