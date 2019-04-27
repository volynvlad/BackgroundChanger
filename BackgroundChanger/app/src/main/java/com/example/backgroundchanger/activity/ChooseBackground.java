package com.example.backgroundchanger.activity;

import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.example.backgroundchanger.send.SendImageToActivity;
import com.example.changer.R;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ChooseBackground extends AppCompatActivity {
    private static final String TAG = "ChooseBackground";
    private static final int REQUEST_GALLERY_PHOTO = 2;

    ImageView mImageView;

    ImageButton mGalleryButton;
    ImageButton mSaveButton;

    Bitmap mResultBitmap;
    Bitmap mBackgroundBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_background);

        mImageView      =   findViewById(R.id.cute_image);
        mGalleryButton  =   findViewById(R.id.gallery_button);
        mSaveButton     =   findViewById(R.id.save_button);

        String imageString = Objects.requireNonNull(getIntent().getExtras()).getString("image");
        String imageName = getIntent().getExtras().getString("name");

        mGalleryButton.setOnClickListener(v -> {
            Log.d(TAG,"onClick to gallery");
            openGallery();
        });

        mSaveButton.setOnClickListener(v -> {
            Log.d(TAG, "onClick to save");
            if (mResultBitmap == null) {
                Toast.makeText(this, "choose background", Toast.LENGTH_SHORT).show();
            } else {
                saveImage(imageName);
            }
        });

        mResultBitmap = BitmapFromString.getBitmapFromString(imageString);
        mImageView.setImageBitmap(mResultBitmap);
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
        AddToGallery.galleryAddPic(file, this);
        try {
            FileFromBitmap.getFile(file, mResultBitmap);
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
                Log.d(TAG, "width - " + mImageView.getWidth() + " height - " + mImageView.getHeight());
                Uri pickedImage = data.getData();
                File file = new File(FromUriRealPath.getRealPathFromURI(pickedImage, this));
                mBackgroundBitmap = BitmapFromFile.getBitmapFromFile(file, mImageView.getMaxWidth(), mImageView.getMaxHeight());

                if (mBackgroundBitmap == null) {
                    Log.d(TAG, "back bitmap - null");
                }

                if (mResultBitmap == null) {
                    Log.d(TAG, "cut bitmap - null");
                }

                if (mBackgroundBitmap != null && mResultBitmap != null) {
                    mResultBitmap = MergeBitmap.mergeImage(mBackgroundBitmap, mResultBitmap);
                    mImageView.setImageBitmap(mResultBitmap);
                }

            }
        }
    }
}
