package com.example.backgroundchanger.merge;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class MergeBitmap {
    public static Bitmap mergeImage(Bitmap b1, Bitmap b2) {
        Bitmap mBitmap = Bitmap.createBitmap(b1.getWidth(), b1.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);

        int adWDelta = b1.getWidth() - b2.getWidth()/2 ;
        int adHDelta = b1.getHeight() - b2.getHeight()/2;

        canvas.drawBitmap(b1, 0, 0, null);
        canvas.drawBitmap(b2, adWDelta, adHDelta, null);

        return mBitmap;
    }
}
