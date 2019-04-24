package com.example.backgroundchanger.lab;

import android.content.Context;

import com.example.backgroundchanger.data.Data;
import com.example.backgroundchanger.entity.Picture;

import java.util.ArrayList;
import java.util.List;

public class PictureLab {
    private static PictureLab sPictureLab;

    private ArrayList<Picture> mPictures;

    public static PictureLab get(Context context) {
        if (sPictureLab == null) {
            sPictureLab = new PictureLab(context);
        }
        return sPictureLab;
    }

    private PictureLab (Context context) {
        mPictures = new ArrayList<>();
        for (int i = 0; i < Data.picturePaths.length; i++) {
            Picture picture = new Picture();
            picture.setPicturePath(Data.picturePaths[i]);
        }
    }

    public List<Picture> getPictures() {
        return mPictures;
    }

    public Picture getPicture(int id) {
        for(Picture picture : mPictures) {
            if (picture.getId() == id) {
                return picture;
            }
        }
        return null;
    }
}
