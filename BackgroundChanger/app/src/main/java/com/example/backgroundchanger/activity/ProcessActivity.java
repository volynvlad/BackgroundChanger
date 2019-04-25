package com.example.backgroundchanger.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.backgroundchanger.modify.ModifyImage;
import com.example.backgroundchanger.parse.FromFileBitmap;
import com.example.backgroundchanger.server.Server;
import com.example.changer.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ProcessActivity extends AppCompatActivity {

    private static final String TAG = "ProcessActivity";
    //178.121.87.189
    private static final String URL = "http://192.168.100.17:8000/";
    ImageView mImageView;

    ImageButton mRotateLeft;
    ImageButton mRotateRight;
    ImageButton mReflectionHorizontal;
    ImageButton mReflectionVertical;
    ImageButton mNext;

    Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        mImageView = findViewById(R.id.get_image);

        mRotateLeft = findViewById(R.id.rotate_left);
        mRotateRight = findViewById(R.id.rotate_right);
        mReflectionHorizontal = findViewById(R.id.reflection_horizontal);
        mReflectionVertical = findViewById(R.id.reflection_vertical);
        mNext = findViewById(R.id.next);

        mRotateLeft.setOnClickListener(v -> {
            Log.d(TAG, "rotate left");
            mBitmap = ModifyImage.rotateLeft(mBitmap);
            mImageView.setImageBitmap(mBitmap);
        });

        mRotateRight.setOnClickListener(v -> {
            Log.d(TAG, "rotate right");
            mBitmap = ModifyImage.rotateRight(mBitmap);
            mImageView.setImageBitmap(mBitmap);
        });

        mReflectionHorizontal.setOnClickListener(v -> {
            Log.d(TAG, "reflection horizontal");
            mBitmap = ModifyImage.reflectHorizontally(mBitmap);
            mImageView.setImageBitmap(mBitmap);
        });

        mReflectionVertical.setOnClickListener(v -> {
            Log.d(TAG, "reflection vertical");
            mBitmap = ModifyImage.reflectVertically(mBitmap);
            mImageView.setImageBitmap(mBitmap);
        });

        mNext.setOnClickListener(v -> {
            Log.d(TAG, "next");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] imageBytes = stream.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            Server.sendImage(this, URL, encodedImage);
        });
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        Log.d(TAG, "onWindowFocusChanged");
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Intent intent = getIntent();
            String path = intent.getExtras().getString("pathToImage");
            Log.d(TAG, "path : " + path);
            mImageView = findViewById(R.id.get_image);
            File file = new File(path);
            mBitmap = FromFileBitmap.getBitmapFromFile(file, mImageView.getWidth(), mImageView.getHeight());

            mImageView.setImageBitmap(mBitmap);
        }
    }

    @Override
    protected void onStart() {
        Log.d(TAG,"onStart()");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG,"onRestart()");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }
}
