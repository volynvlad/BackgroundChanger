package com.example.backgroundchanger.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.backgroundchanger.add.AddToGallery;
import com.example.backgroundchanger.merge.MergeBitmap;
import com.example.backgroundchanger.create.FileCreator;
import com.example.backgroundchanger.parse.BitmapFromFile;
import com.example.backgroundchanger.parse.BitmapFromString;
import com.example.backgroundchanger.parse.FileFromBitmap;
import com.example.backgroundchanger.parse.FromUriRealPath;
import com.example.changer.R;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ChooseBackground extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = "ChooseBackground";
    private static final int REQUEST_GALLERY_PHOTO = 2;

    ImageView mImageView;

    ImageButton mGalleryButton;
    ImageButton mSaveButton;

    Bitmap mResultBitmap;
    Bitmap mBackgroundBitmap;
    Bitmap mFrontBitmap;

    float x;
    float y;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_background);

        mImageView      =   findViewById(R.id.cute_image);
        mGalleryButton  =   findViewById(R.id.gallery_button);
        mSaveButton     =   findViewById(R.id.save_button);

        String imageString  = Objects.requireNonNull(getIntent().getExtras()).getString("image");
        String imageName    = getIntent().getExtras().getString("name");

        mGalleryButton.setOnClickListener(v -> {
            Log.d(TAG,"onClick to gallery");
            openGallery();
        });

        mSaveButton.setOnClickListener(v -> {
            Log.d(TAG, "onClick to save");
            if (mFrontBitmap == null) {
                Toast.makeText(this, "choose background", Toast.LENGTH_SHORT).show();
            } else {
                saveImage(imageName);
            }
        });

        mFrontBitmap = BitmapFromString.getBitmapFromString(imageString);
        mImageView.setOnTouchListener(this::onTouch);

        if (mBackgroundBitmap == null) {
            mImageView.setImageBitmap(mFrontBitmap);
        }/*
        if (mBackgroundBitmap != null && mFrontBitmap != null) {
            //xOfFront = (mBackgroundBitmap.getWidth() - mFrontBitmap.getWidth()) / 2;
            //yOfFront = (mBackgroundBitmap.getWidth() - mFrontBitmap.getWidth()) / 2;
            mResultBitmap = MergeBitmap.mergeImage(mBackgroundBitmap, mFrontBitmap, xOfFront, yOfFront);
            mImageView.setImageBitmap(mResultBitmap);
        }*/
    }

    private void openGallery() {
        Log.d(TAG,"openGallery");
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    private void saveImage(String imageName) {
        Log.d(TAG, "saveImage");
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Changer/Processed";
        File file = FileCreator.createFile(path, imageName, ".jpg");
        try {
            FileFromBitmap.getFile(file, mResultBitmap);
            AddToGallery.galleryAddPic(file, this);
            Toast.makeText(this, "Saved to " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_PHOTO) {
                Log.d(TAG, "operation was successful REQUEST_GALLERY_PHOTO");
                Uri pickedImage = data.getData();
                File file = new File(FromUriRealPath.getRealPathFromURI(pickedImage, this));
                mBackgroundBitmap = BitmapFromFile.getBitmapFromFile(this, file, mImageView.getMaxWidth(), mImageView.getMaxHeight());

                if (mBackgroundBitmap == null) {
                    Log.d(TAG, "back bitmap - null");
                }

                if (mFrontBitmap == null) {
                    Log.d(TAG, "cut bitmap - null");
                }

                if (mBackgroundBitmap != null && mFrontBitmap != null) {
                    //xOfFront = (mBackgroundBitmap.getWidth() - mFrontBitmap.getWidth()) / 2;
                    //yOfFront = (mBackgroundBitmap.getWidth() - mFrontBitmap.getWidth()) / 2;
                    mResultBitmap = MergeBitmap.mergeImage(mBackgroundBitmap, mFrontBitmap,
                            x - mFrontBitmap.getWidth()  / 2,
                            y - mFrontBitmap.getHeight() / 2);
                    mImageView.setImageBitmap(mResultBitmap);
                }

            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG, "onTouch");

        int ev = event.getAction();
        if (ev == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
            return true;
        } else if (ev == MotionEvent.ACTION_UP) {
            x = event.getX();
            y = event.getY();
        } else if (ev == MotionEvent.ACTION_MOVE) {
            x = event.getX();
            y = event.getY();
        }

        if (mBackgroundBitmap != null && mFrontBitmap != null) {
            mResultBitmap = MergeBitmap.mergeImage(mBackgroundBitmap, mFrontBitmap,
                    x - mFrontBitmap.getWidth()  / 2,
                    y - mFrontBitmap.getHeight() / 2 - mBackgroundBitmap.getHeight() / 2);
            mImageView.setImageBitmap(mResultBitmap);
        }


        return super.onTouchEvent(event);
    }
}
