package com.example.zilunlin.bacpack.Generic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zilunlin.bacpack.Config;
import com.example.zilunlin.bacpack.R;
import com.example.zilunlin.bacpack.RecyclerViewAdapter;
import com.example.zilunlin.bacpack.network.AppController;
import com.example.zilunlin.bacpack.network.SimpleDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JoinedActivity extends AppCompatActivity {
    private ArrayList<HashMap<String,String>> eventList = new ArrayList<HashMap<String, String>>();
    private RecyclerView recyclerView;
    private String user_id;
    private RecyclerViewAdapter mAdapter;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            user_id = getIntent().getStringExtra("user_id");
        } else {
            Intent logIn = new Intent(JoinedActivity.this, LoginActivity.class);
            startActivity(logIn);
        }
        setContentView(R.layout.activity_joined);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        GetJoinedEvents();
    }

    public void GetJoinedEvents(){

        String tag_string_req = "req_getJoinedEvents";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.URL_USER_lIST_EVENTS+user_id, new Response.Listener<String>(){
            @Override

            public void onResponse(String response) {
                // progressDialog.dismiss();
                Log.i("responseTest",response);

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
                    int length = result.length();

                    HashMap<String, String> events;
                    for (int i = 0; i < length; i++) {
                        JSONObject jo = result.getJSONObject(i);
                        String event_id = jo.getString(Config.TAG_EVENT_ID);
                        String name = jo.getString(Config.TAG_EVENT_NAME);
                        String date = jo.getString(Config.TAG_EVENT_START_DT);
                        String event_type = jo.getString(Config.TAG_EVENT_TYPE);
                        String joined = "joined";
                        String approval = "approval";

                        events = new HashMap<>();
                        events.put(Config.TAG_EVENT_ID, event_id);
                        events.put(Config.TAG_EVENT_NAME, name);
                        events.put(Config.TAG_EVENT_START_DT, date);
                        events.put(Config.TAG_EVENT_TYPE, event_type);
                        events.put(joined, "true");
                        events.put(approval, "1");
                        eventList.add(events);
                    }
                    mAdapter = new RecyclerViewAdapter(JoinedActivity.this,eventList, user_id);
                    recyclerView.setAdapter(mAdapter);
                }
                catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Bad internet connection");

            }
        }) {};
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
        Log.i("URL", stringRequest.toString());
    }

}
