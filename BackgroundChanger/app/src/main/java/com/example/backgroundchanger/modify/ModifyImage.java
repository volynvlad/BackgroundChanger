package com.example.backgroundchanger.modify;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

public class ModifyImage {
    private static final String TAG = "ModifyImage";

    public static Bitmap rotateLeft(Bitmap bitmap) {
        Log.d(TAG, "rotateLeft");
        Matrix matrix = new Matrix();
        matrix.preRotate(270);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }

    public static Bitmap rotateRight(Bitmap bitmap) {
        Log.d(TAG, "rotateRight");
        Matrix matrix = new Matrix();
        matrix.preRotate(90);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }

    public static Bitmap reflectHorizontally(Bitmap bitmap) {
        Log.d(TAG, "reflectHorizontally");
        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }

    public static Bitmap reflectVertically(Bitmap bitmap) {
        Log.d(TAG, "reflectVertically");
        Matrix matrix = new Matrix();
        matrix.postScale(1, -1);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }
}
