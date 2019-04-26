package com.example.backgroundchanger.server;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.backgroundchanger.activity.ChooseBackground;
import com.example.backgroundchanger.parse.FromBitmapString;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class Server {
    private static final String TAG = "Server";
    public static void getImage(Context context, String url) {
        Log.d(TAG, "getImage");
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
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
        queue.add(request);
    }

    public static void sendImage(Context context, String url, String image, String name) {
        Log.d(TAG, "sendImage");

        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("image", image);
            jsonObject.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                response -> {
                    Log.d(TAG, "Rest Response " + response.toString());
                    Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                },
                error -> {
                    Log.e(TAG, "Rest Response " + error.toString());
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                }){
            @Override
            public String getBodyContentType() {
                return super.getBodyContentType();
            }

            @Override
            public byte[] getBody() {
                return super.getBody();
            }
        };
        queue.add(request);
    }
}
