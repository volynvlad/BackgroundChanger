package com.example.backgroundchanger.entity;


import com.example.changer.R;

import java.util.concurrent.atomic.AtomicInteger;

public class Picture {
    private int picturePath;
    private int id;
    private static AtomicInteger nextId = new AtomicInteger();

    {
        id = nextId.incrementAndGet();
    }

    public Picture() {
        this.picturePath = R.drawable.picture_1;
    }

    public int getPicturePath() {
        return picturePath;
    }

    public int getId() {
        return id;
    }

    public void setPicturePath(int picturePath) {
        this.picturePath = picturePath;
    }
}
