package com.example.changer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.changer.modify.ModifyImage;
import com.example.changer.R;
import com.example.changer.parse.FromFileBitmap;

import java.io.File;

public class ProcessActivity extends AppCompatActivity {

    private static final String TAG = "ProcessActivity";
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
            //TODO process the image and push result to ChooseBackground activity

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
