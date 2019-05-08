package com.example.backgroundchanger.merge;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class MergeBitmap {
    public static Bitmap mergeImage(Bitmap background, Bitmap front, float x, float y) {
        Bitmap result = Bitmap.createBitmap(background.getWidth(), background.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        canvas.drawBitmap(background, 0f, 0f, null);
        canvas.drawBitmap(front, x, y, null);

        return result;
    }
}
