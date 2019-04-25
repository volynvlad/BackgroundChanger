package com.example.backgroundchanger.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

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
        byte[] bytes = getIntent().getByteArrayExtra("image");
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        mImageView.setImageBitmap(bitmap);
    }
}
