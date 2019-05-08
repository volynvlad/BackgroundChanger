package com.example.backgroundchanger.server;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.backgroundchanger.activity.ChooseBackground;

import org.json.JSONException;
import org.json.JSONObject;

public class Server {
    private static final String TAG = "Server";
    public static void sendImage(Context context, String image, String name) {
        //192.168.100.17
        final String url = "http://10.42.0.1:8000/";
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
                    Intent intent = new Intent(context, ChooseBackground.class);
                    intent.putExtra("image",response.toString().substring(10, response.toString().length() - 2));
                    intent.putExtra("name", name.substring(0, name.length() - 4));
                    context.startActivity(intent);
                },
                error -> {
                    Log.e(TAG, "Rest Response " + error.toString());
                    Toast.makeText(context, "Network error. Check your internet connection", Toast.LENGTH_LONG).show();
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
