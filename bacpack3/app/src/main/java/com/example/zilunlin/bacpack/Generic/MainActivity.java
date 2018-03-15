package com.example.zilunlin.bacpack.Generic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zilunlin.bacpack.Config;
import com.example.zilunlin.bacpack.DB.UserCredentialsDBHandler;
import com.example.zilunlin.bacpack.R;
import com.example.zilunlin.bacpack.RecyclerViewAdapter;
import com.example.zilunlin.bacpack.UserInfo.User;
import com.example.zilunlin.bacpack.network.AppController;
import com.example.zilunlin.bacpack.network.SimpleDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<HashMap<String,String>> eventList = new ArrayList<HashMap<String, String>>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    ProgressDialog pg;
    private String user_id, user_name,user_type,user_permission;
    UserCredentialsDBHandler mUserCredentialsDBHandler = new UserCredentialsDBHandler(this);
    private List<User> userCredentials;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //Sends user to the home page
                    Toast.makeText(getApplicationContext(), "Bad internet connection...!" , Toast.LENGTH_LONG).show();
                    return true;

                case R.id.navigation_dashboard:
                    //Sends user to functionalities
                    Intent toDashboard = new Intent(MainActivity.this, Dashboard.class);
                    startActivity(toDashboard);
                    return true;

                case R.id.navigation_notifications:
                    //Sends user to somewhere else
                    Toast.makeText(getApplicationContext(), "Bad internet connection...!" , Toast.LENGTH_LONG).show();
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Gets user info into a list by fetching the database
        userCredentials = mUserCredentialsDBHandler.getUserCredentials();
        //Checks for User's info and whether if an account is logged in.
        if (!userCredentials.isEmpty()) {
            user_name = mUserCredentialsDBHandler.getUserCredentials().get(0).toString();
            user_id = mUserCredentialsDBHandler.getUserCredentials().get(1).toString();
            user_type = mUserCredentialsDBHandler.getUserCredentials().get(2).toString();
            user_permission = mUserCredentialsDBHandler.getUserCredentials().get(3).toString();
        }else {
            //generic user, no need for login
            user_id = "2";
        }

        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        pg=new ProgressDialog(MainActivity.this);
        pg.setMessage("Please wait..");
        pg.setTitle("Backpack");
        pg.setCancelable(false);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        GetAllEvents();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.event_filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                eventList.clear();
                GetAllEvents();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void  GetAllEvents(){

        String tag_string_req = "req_getAllEvents";

        pg.show();


        StringRequest strReq = new StringRequest(Request.Method.GET, Config.URL_GET_ALL_EVENT+user_id, new Response.Listener<String>() {


            @Override

            public void onResponse(String response) {
                // progressDialog.dismiss();
                Log.i("responseTest",response);
                pg.dismiss();

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
                        events.put(joined, "false");
                        events.put(approval, "1");
                        eventList.add(events);
                    }

                    mAdapter = new RecyclerViewAdapter(MainActivity.this,eventList, user_id);
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
                pg.dismiss();

            }
        }) {};
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        Log.i("URL", strReq.toString());
    }
    }









