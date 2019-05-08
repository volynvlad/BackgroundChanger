package com.example.backgroundchanger.parse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.media.ExifInterface;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

public class BitmapFromFile {
    private static final String TAG = "BitmapFromFile";
    public static Bitmap getBitmapFromFile(Context context, File file, int width, int height) {
        Log.d(TAG,"getBitmapFromFile");

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW/width, photoH/height);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);

        try {
            ExifInterface exif = new ExifInterface(file.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            }
            else if (orientation == 3) {
                matrix.postRotate(180);
            }
            else if (orientation == 8) {
                matrix.postRotate(270);
            }
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        catch (FileNotFoundException e) {
            Log.e(TAG,"Exception " + e);
            Toast.makeText(context, "No such file or directory", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Log.e(TAG,"Exception " + e);
        }

        return bitmap;
    }
}
