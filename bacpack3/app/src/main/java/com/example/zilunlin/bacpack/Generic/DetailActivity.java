package com.example.zilunlin.bacpack.Generic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zilunlin.bacpack.Config;
import com.example.zilunlin.bacpack.R;
import com.example.zilunlin.bacpack.network.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {
    String event_id;
    TextView tName,tDesc,tType, tParticipants, tDate, tPrice, tLocation, tOrganizer;
    private ArrayList<HashMap<String,String>> eventList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            event_id = getIntent().getStringExtra("event_id");

        }else {
            Intent noId = new Intent(this, MainActivity.class);
            startActivity(noId);
        }

        tName = (TextView) findViewById(R.id.event_name);
        tDesc = (TextView) findViewById(R.id.event_description);
        tType = (TextView) findViewById(R.id.event_type);
        tParticipants = (TextView) findViewById(R.id.event_participants);
        tDate = (TextView) findViewById(R.id.event_date);
        tPrice = (TextView) findViewById(R.id.event_price);
        tLocation = (TextView) findViewById(R.id.event_location);
        tOrganizer = (TextView) findViewById(R.id.event_organizer);


        getEventInfo();
    }

    public void getEventInfo(){
        StringRequest strReq = new StringRequest(Request.Method.GET, Config.URL_GET_SPECIFIC_EVENT+ event_id, new Response.Listener<String>() {


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
                        String name = jo.getString(Config.TAG_EVENT_NAME);
                        String descrip = jo.getString("descrip");
                        String price = jo.getString(Config.TAG_EVENT_PRICE);
                        String location = jo.getString(Config.TAG_EVENT_LOCATION);
                        String date = jo.getString(Config.TAG_EVENT_START_DT);
                        String event_type = jo.getString(Config.TAG_EVENT_TYPE);
                        String participant_num = jo.getString("participant_num");
                        String vacancies = jo.getString("vacancies");
                        String organizer_name = jo.getString("organizer_name");

                        events = new HashMap<>();
                        events.put(Config.TAG_EVENT_NAME, name);
                        events.put(Config.TAG_EVENT_START_DT, date);
                        events.put(Config.TAG_EVENT_TYPE, event_type);
                        events.put(Config.TAG_EVENT_LOCATION, location);
                        events.put(Config.TAG_EVENT_PRICE, price);
                        events.put("descrip", descrip);
                        events.put("participant_num", participant_num);
                        events.put("vacancies", vacancies);
                        events.put("organizer_name", organizer_name);
                        eventList.add(events);

                    }

//                    set the text view values.
                    tName.setText(eventList.get(0).get("name"));
                    tDesc.setText(eventList.get(0).get("descrip"));
                    tPrice.setText(eventList.get(0).get("price"));
                    tLocation.setText(eventList.get(0).get("location"));

                    tDate.setText(eventList.get(0).get("start_datetime"));
                    tType.setText(eventList.get(0).get("event_type"));
                    tParticipants.setText(eventList.get(0).get("participant_num"));

                }
                catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Bad internet connection");
                Toast.makeText(DetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Bad internet connection...!" , Toast.LENGTH_LONG).show();

            }
        }) {};
        AppController.getInstance().addToRequestQueue(strReq);
        Log.i("URL", strReq.toString());
    }
}
