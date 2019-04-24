package com.example.backgroundchanger.server;


import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.backgroundchanger.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final String TAG = "Server";
    public static void getImage(Context context, String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest objectRequest = new StringRequest(
                Request.Method.GET,
                url,
                null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Rest Response " + error.toString());
                    }
                }

        );
        requestQueue.add(objectRequest);
    }
    public static void setImage(Context context, String url, String image) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest objectRequest = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    if (response == null) {
                        Toast.makeText(context, "response is null", Toast.LENGTH_LONG);
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
                    Toast.makeText(context, "Error to connent to " + url, Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("image", image);
                return parameters;
            }
        };
        queue.add(objectRequest);
    }
}
