package com.example.zilunlin.bacpack.Generic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zilunlin.bacpack.R;

public class Dashboard extends AppCompatActivity {
    String user_id,user_name,user_type,user_permission;
    TextView name, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        name = (TextView)findViewById(R.id.userName);
        desc = (TextView)findViewById(R.id.desc);

        Bundle extras = getIntent().getExtras();
        if (!extras.get("user_id").equals("2")) {
            user_id = getIntent().getStringExtra("user_id");
            user_name = getIntent().getStringExtra("user_name");
            user_type = getIntent().getStringExtra("user_type");
            user_permission = getIntent().getStringExtra("user_permission");
            name.setText("Hi, "+user_name+" welcome back!");
            desc.setText("Name: "+user_name+ " | User ID: " + user_id);
        }else {
            user_id = "2";
            name.setText("Hi, Log in to unlock more");
            desc.setText("Click here");
        }


        View grade,upload,approve,todo,user,calendar,joined,created;
        grade = findViewById(R.id.gradeRow);
        grade.setOnClickListener(itemListener);

        upload = findViewById(R.id.uploadRow);
        upload.setOnClickListener(itemListener);

        approve = findViewById(R.id.approveRow);
        approve.setOnClickListener(itemListener);

        user = findViewById(R.id.userRow);
        user.setOnClickListener(itemListener);

        calendar = findViewById(R.id.calendarRow);
        calendar.setOnClickListener(itemListener);

        todo = findViewById(R.id.todoRow);
        todo.setOnClickListener(itemListener);

        joined = findViewById(R.id.joinedEventsRow);
        joined.setOnClickListener(itemListener);

        created = findViewById(R.id.userEventsRow);
        created.setOnClickListener(itemListener);

        if (user_id == "2"){
            joined.setVisibility(View.GONE);
            created.setVisibility(View.GONE);
            approve.setVisibility(View.GONE);
        }
        if (user_permission =="0"){
            approve.setVisibility(View.GONE);
        }
    }

    View.OnClickListener itemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (user_id!="2"){
            int item = v.getId();
            Class activity;
            switch (item) {
                case R.id.uploadRow:
                    activity = Upload.class;
                    Intent toUpload = new Intent(Dashboard.this, Dashboard.class);
                    toUpload.putExtra("user_id",user_id);
                    toUpload.putExtra("user_type",user_type);
                    toUpload.putExtra("user_name",user_name);
                    toUpload.putExtra("user_permission", user_name);

                    startActivity(toUpload);
                    break;
                case R.id.approveRow:
                    activity = ApprovalActivity.class;
                    break;
                case R.id.todoRow:
                    activity = TodoActivity.class;
                    break;
                case R.id.calendarRow:
                    activity = TimetableActivity.class;
                    break;
                case R.id.gradeRow:
                    activity = GradeActivity.class;
                    break;
                case R.id.userRow:
                    activity = UserProfileActivity.class;
                    break;
                case R.id.userEventsRow:
                    if (user_id!="2") {
                        activity = UserEventsActivity.class;
                    }else{
                        activity = LoginActivity.class;
                    }
                    break;
                case R.id.joinedEventsRow:
                    if (user_id!="2") {
                        activity = JoinedActivity.class;
                    }else{
                        activity = LoginActivity.class;
                    }
                    break;
                default:
                    activity=Dashboard.class;
                    Toast.makeText(Dashboard.this, "Select an item boyeee", Toast.LENGTH_SHORT).show();
            }
            Intent Switcheroo = new Intent(Dashboard.this,activity);
            Switcheroo.putExtra("user_id", user_id);
            startActivity(Switcheroo);
        }else{
                Intent toLogIn = new Intent(Dashboard.this, LoginActivity.class);
                startActivity(toLogIn);
            }
    }
};}
