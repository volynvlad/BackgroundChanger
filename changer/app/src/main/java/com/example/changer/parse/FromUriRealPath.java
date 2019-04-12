package com.example.changer.parse;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class FromUriRealPath {
    private static final String TAG = "FromUriRealPath";
    public static String getRealPathFromURI(Uri contentUri, Context context) {
        Log.d(TAG, "getRealPathFromURI");
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = context
                .getContentResolver()
                .query(contentUri,
                        proj,
                        null,
                        null,
                        null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }
}
