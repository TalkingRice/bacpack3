package com.example.zilunlin.bacpack.Generic;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zilunlin.bacpack.Config;
import com.example.zilunlin.bacpack.PriceDialogActivity;
import com.example.zilunlin.bacpack.R;
import com.example.zilunlin.bacpack.VacancyDialogFragment;
import com.example.zilunlin.bacpack.network.AppController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Upload extends AppCompatActivity implements PriceDialogActivity.Relay, NumberPicker.OnValueChangeListener {
    EditText nameText, priceText, descripText, typeText, dateTimeText, locationText, item_name, price;
    Switch recurring;
    Button submit;
    String prices = "Free";

    //setting variables for the date and time picker
    String start_datetime = " ";
    String end_datetime = "";
    int mHour, mMinutes;
    int mDay, mMonth, mYear;
    int mWeekday = '0';
    int round = 0;
    String user_name, user_id, user_type, user_permission;
    String vacancy = "Unlimited";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = getIntent().getStringExtra("user_id");
            user_name = "Zilun";
            user_type = getIntent().getStringExtra("user_type");
            user_permission = getIntent().getStringExtra("user_permission");
            Toast.makeText(Upload.this, user_name,Toast.LENGTH_SHORT).show();
        } else {
            //generic user, no need for login
            user_id = "2";
        }


        nameText = (EditText) findViewById(R.id.nameText);
        priceText = (EditText) findViewById(R.id.priceText);
        descripText = (EditText) findViewById(R.id.descripText);
        typeText = (EditText) findViewById(R.id.typeText);
        dateTimeText = (EditText) findViewById(R.id.dateTimeText);
        locationText = (EditText) findViewById(R.id.locationText);

        recurring = (Switch) findViewById(R.id.recurring);

        submit = (Button) findViewById(R.id.submitButton);

        dateTimeText.setKeyListener(null);
        dateTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTimeText();
            }
        });

        priceText.setKeyListener(null);
        priceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPriceText();
            }
        });

        typeText.setKeyListener(null);
        typeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTypeText();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validate = isEmpty(new EditText[]{nameText, descripText, dateTimeText, typeText, priceText, locationText});
                if (validate == false) {
                    setVacancy();
                    final String name = nameText.getText().toString();
                    final String price = priceText.getText().toString();
                    final String descrip = descripText.getText().toString();
                    final String type = typeText.getText().toString();
                    final String date_time = dateTimeText.getText().toString();
                    final String location = locationText.getText().toString();

                    //Joins event using volley
                    String tag_string_req = "req_joinEvents";
                    StringRequest strReq = new StringRequest(Request.Method.POST, Config.URL_ADD_EVENT, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // progressDialog.dismiss();
                            Log.i("uploadResponse", response);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("onErrorResponse: ", error.toString());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("event_name", name);
                            params.put("description", descrip);
                            params.put("price", price);
                            params.put("event_type", type);
                            params.put("organizer_id", user_id);
                             params.put("vacancy", vacancy);
                            params.put("location", location);
                            params.put("start_dt", date_time);
                            params.put("approval","0");
                            params.put("organizer_name", user_name);
                            Log.i("onClick: ", params.toString());
                            return params;

                        }
                    };
                    AppController.getInstance().addToRequestQueue(strReq);
                    Log.i("URL", strReq.toString());

                } else {
                    Toast.makeText(getApplicationContext(), "Please insert all data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //form validator
    private boolean isEmpty(EditText[] field){
        for (int i = 0; i < field.length; i++) {
            EditText currentField = field[i];
            if (currentField.getText().toString().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public void setDateTimeText() {
        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mWeekday = c.get(Calendar.DAY_OF_WEEK);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (round ==0) {
                            start_datetime = dayOfMonth + "-" + (month + 1) + "-" + year;
                            round++;
                            timepicker();
                        } else {
                            Toast.makeText(getApplicationContext(), "Pick an end date", Toast.LENGTH_SHORT).show();
                            end_datetime = dayOfMonth + "-" + (month + 1) + "-" + year;
                            dateTimeText.setText(start_datetime+" "+mHour+":"+mMinutes+":00" + " to " + end_datetime);
                            round = 0;
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void timepicker() {
        final Calendar c = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        mHour = i;
                        mMinutes = i1;
                        if (round <= 1){
                            setDateTimeText();
                        }

                }}, mHour, mMinutes, false);
                    timePickerDialog.show();

    }

    private void setPriceText(){
        FragmentManager manager = getFragmentManager();
        PriceDialogActivity priceDialogActivity = new PriceDialogActivity();
        priceDialogActivity.show(manager, "Dialog");
    }

    private void setTypeText(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("What type of event are you hosting?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        final CharSequence values[] = {"After-School Activity", "CAS", "Events"};
        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                typeText.setText(values[i]);
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public void setVacancy(){
        VacancyDialogFragment pickVacancy = new VacancyDialogFragment();
        pickVacancy.setOnValueChangeListener(this);
        pickVacancy.show(getSupportFragmentManager(), "Vacancy Picker");
    }
    @Override
    public void onDialogMessage(String message) {
        message.toString();
        if (message.equals("Preview:") || message.equals("free")) {
            priceText.setText("Free");
        } else {
            priceText.setText(message);
        }
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        vacancy = numberPicker.toString();
    }
}






