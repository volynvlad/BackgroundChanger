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
                    try {
                        String getName = response.getJSONObject("name").toString();
                        String getImage = response.getJSONObject("image").toString();

                        Log.d(TAG, "response - " + getName);
                        Log.d(TAG, "response - " + getImage);

                        Intent intent = new Intent(context, ChooseBackground.class);
                        intent.putExtra("image", getImage);
                        intent.putExtra("name", getName);

                        context.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
