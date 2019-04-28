package com.example.backgroundchanger.merge;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class MergeBitmap {
    public static Bitmap mergeImage(Bitmap background, Bitmap front) {
        Bitmap result = Bitmap.createBitmap(background.getWidth(), background.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        int widthBack  = background.getWidth();
        int widthFront = front.getWidth();
        int move = (widthBack - widthFront) / 2;

        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(front, move, move, null);

        return result;
    }
}
