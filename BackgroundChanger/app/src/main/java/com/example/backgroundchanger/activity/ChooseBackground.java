package com.example.backgroundchanger.activity;

import android.graphics.Bitmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.backgroundchanger.parse.BitmapFromString;
import com.example.changer.R;

public class ChooseBackground extends AppCompatActivity {
    ImageView mImageView;

    ImageButton mGalleryButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_background);

        mImageView = findViewById(R.id.cute_image);
        mGalleryButton = findViewById(R.id.gallery_button);

        Bitmap bitmap;
        String imageString = getIntent().getExtras().getString("image");
        String imageName = getIntent().getExtras().getString("name");

        bitmap = BitmapFromString.getBitmapFromString(imageString);

        mImageView.setImageBitmap(bitmap);
    }
}
