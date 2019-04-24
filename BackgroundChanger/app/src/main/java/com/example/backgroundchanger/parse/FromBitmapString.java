package com.example.backgroundchanger.parse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class FromBitmapString {
    private static Bitmap getBitmapFromString(String stringPicture) {
        byte[] decodedString = Base64.decode(stringPicture, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
