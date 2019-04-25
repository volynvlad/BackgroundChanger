package com.example.backgroundchanger.create;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileCreator {
    private static final String TAG = "FileCreator";
    public static File createFile() {
        Log.d(TAG,"createFile");
        String timeStamp = new SimpleDateFormat("yyyyMMdd__HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        // Get the directory for the user's public pictures directory.
        try {
            final File dir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES) + "/Changer/Photo");
            if (!dir.exists()) {
                dir.mkdirs();
            }
        } catch (Exception e) {
            Log.e(TAG, "creating error! " + e.getMessage());
        }
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + "/Changer/Photo", imageFileName + ".jpg");
        Log.i(TAG, file.getAbsolutePath());
        return file;
    }
}
