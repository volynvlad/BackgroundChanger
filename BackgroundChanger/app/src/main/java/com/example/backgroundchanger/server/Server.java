package com.example.backgroundchanger.server;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.backgroundchanger.activity.ChooseBackground;
import com.example.backgroundchanger.parse.FromBitmapString;


import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class Server {
    private static final String TAG = "Server";
    public static void getTest(Context context, String url) {
        Log.d(TAG, "getTest");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> Toast.makeText(context, "My response", Toast.LENGTH_LONG).show(),
                error -> {
                    Log.e(TAG, "Rest Response " + error.toString());
                    Toast.makeText(context, "Error to connent to " + url, Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(request);
    }
    public static void getImage(Context context, String url) {
        Log.d(TAG, "getImage");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest objectRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    Toast.makeText(context, "response successfully", Toast.LENGTH_LONG).show();
                    Bitmap bitmap = FromBitmapString.getBitmapFromString(response);
                    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                    byte[] bytes = bStream.toByteArray();
                    Intent intent = new Intent(context, ChooseBackground.class);
                    intent.putExtra("image", bytes);
                    context.startActivity(intent);
                },
                error -> {
                    Log.e(TAG, "Rest Response " + error.toString());
                    Toast.makeText(context, "Error to connect to " + url, Toast.LENGTH_LONG).show();
                }

        );
        requestQueue.add(objectRequest);
    }

    public static void sendImage(Context context, String url, String image) {
        Log.d(TAG, "sendImage");
        ProgressDialog progressDialog = new ProgressDialog(context);
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest objectRequest = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    if (response == null) {
                        Toast.makeText(context, "response is null", Toast.LENGTH_LONG).show();
                    } else {
                        progressDialog.dismiss();
                        if (response.equals("true")) {
                            Toast.makeText(context, "Uploaded Successful", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Some error occurred!", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                error -> {
                    Log.e(TAG, "Rest Response " + error.toString());
                    Toast.makeText(context, "Error to connect to " + url, Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Log.d(TAG, "getParams");
                Map<String, String> parameters = new HashMap<>();
                Log.d(TAG, "parameter - " + image);
                parameters.put("image", image);
                return parameters;
            }
        };
        queue.add(objectRequest);
    }
}
