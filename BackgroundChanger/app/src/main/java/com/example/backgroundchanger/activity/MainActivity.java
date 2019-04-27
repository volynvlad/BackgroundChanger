package com.example.backgroundchanger.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.backgroundchanger.add.AddToGallery;
import com.example.backgroundchanger.create.FileCreator;
import com.example.backgroundchanger.parse.BitmapFromFile;
import com.example.backgroundchanger.parse.FromUriRealPath;
import com.example.backgroundchanger.send.SendImageToActivity;
import com.example.changer.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    ImageButton mCameraButton;
    ImageButton mGalleryButton;

    ImageView mImageView;

    File mCurrentFile;

    Bitmap mBitmap;

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_GALLERY_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        mCameraButton = findViewById(R.id.camera_button);
        mGalleryButton = findViewById(R.id.gallery_button);

        mImageView = findViewById(R.id.main_image);

        mCameraButton.setOnClickListener(v -> {
            Log.d(TAG,"onClick to camera");
            onRequestPermissionsResult(REQUEST_TAKE_PHOTO);
        });

        mGalleryButton.setOnClickListener(v -> {
            Log.d(TAG,"onClick to gallery");
            onRequestPermissionsResult(REQUEST_GALLERY_PHOTO);
        });

    }

    private void openGallery() {
        Log.d(TAG,"openGallery");
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    private void openCamera() {
        Log.d(TAG,"openCamera");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            mCurrentFile = null;
            mCurrentFile = FileCreator.createFile();
            if (mCurrentFile != null) {
                Uri photoURI = FileProvider.getUriForFile(
                        this,
                        "com.example.changer.fileprovider",
                        mCurrentFile
                );
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                Log.d(TAG, "operation was successful REQUEST_TAKE_PHOTO");
                Log.d(TAG, "width - " + mImageView.getWidth() + " height - " + mImageView.getHeight());
                mBitmap = BitmapFromFile.getBitmapFromFile(mCurrentFile, mImageView.getWidth(), mImageView.getHeight());
                AddToGallery.galleryAddPic(mCurrentFile, this);
                SendImageToActivity.sendPathToActivity(mCurrentFile.getAbsolutePath(), this);
            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Log.d(TAG, "operation was successful REQUEST_GALLERY_PHOTO");
                Log.d(TAG, "width - " + mImageView.getWidth() + " height - " + mImageView.getHeight());
                Uri pickedImage = data.getData();
                File file = new File(FromUriRealPath.getRealPathFromURI(pickedImage, this));
                mBitmap = BitmapFromFile.getBitmapFromFile(file, mImageView.getMaxWidth(), mImageView.getMaxHeight());
                SendImageToActivity.sendPathToActivity(file.getAbsolutePath(), this);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode) {
        Log.d(TAG,"onRequestPermissionsResult");
        Dexter
                .withActivity(this)
                .withPermissions(
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        Log.d(TAG,"onPermissionsChecked");
                        if(report.areAllPermissionsGranted()) {
                            switch (requestCode) {
                                case REQUEST_TAKE_PHOTO: {
                                    openCamera();
                                    return;
                                }
                                case REQUEST_GALLERY_PHOTO: {
                                    openGallery();
                                }
                            }
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage(
                "This app needs permission " +
                " to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            MainActivity.this.openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() called");
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
