package com.example.backgroundchanger.create;

import android.util.Log;

import java.io.File;

public class FileCreator {
    private static final String TAG = "FileCreator";
    public static File createFile(String path, String imageFileName, String format) {
        Log.d(TAG,"createFile");
        // Get the directory for the user's public pictures directory.
        try {
            final File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

        } catch (Exception e) {
            Log.e(TAG, "creating error! " + e.getMessage());
        }
        File file = new File(path, imageFileName + format);
        Log.i(TAG, file.getAbsolutePath());
        return file;
    }
}
