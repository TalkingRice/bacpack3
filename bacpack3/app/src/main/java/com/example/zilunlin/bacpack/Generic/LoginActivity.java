package com.example.zilunlin.bacpack.Generic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {
    String password;
    String user_id;
    Button button;
    EditText getIdText, getPasswordText;
    private ArrayList<HashMap<String,String>> userLogin = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getIdText = (EditText) findViewById(R.id.IdInput);
        getPasswordText = (EditText) findViewById(R.id.passwordInput);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(loginListener);

    }
    private View.OnClickListener loginListener = new View.OnClickListener() {
        public void onClick(View v) {
            user_id = getIdText.getText().toString();
            password = getPasswordText.getText().toString();
            logIn(user_id,password);
        }
    };

    private void logIn(final String user_id, final String password){

        StringRequest strReq = new StringRequest(Request.Method.GET, Config.URL_USER_LOGIN+1, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.i("userResponse",response);

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("result");
                    int length = result.length();

                    HashMap<String, String> user;
                    for (int i = 0; i < length; i++) {
                        JSONObject jo = result.getJSONObject(i);
                        String id = jo.getString("id");
                        String name = jo.getString("name");
                        String user_type = jo.getString("user_type");
                        String password = jo.getString("password");
                        String permission = jo.getString("permission");

                        user = new HashMap<>();
                        user.put("id",id);
                        user.put("name", name);
                        user.put("user_type", user_type);
                        user.put("password", password);
                        user.put("permission", permission);
                        userLogin.add(user);
                    }
                    if (password.equals(userLogin.get(0).get("password").toString()) && user_id.equals(userLogin.get(0).get("id"))){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("user_id", userLogin.get(0).get("id"));
                        intent.putExtra("user_name", userLogin.get(0).get("name"));
                        intent.putExtra("user_type",userLogin.get(0).get("user_type"));
                        intent.putExtra("user_permission", userLogin.get(0).get("permission"));
                        startActivity(intent);

                    }if (password.isEmpty() || user_id.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Please fill in all info", Toast.LENGTH_SHORT).show();
                    }if (!password.equals(userLogin.get(0).get("password").toString()) || !user_id.equals(userLogin.get(0).get("id"))){
                        Toast.makeText(LoginActivity.this, "ID or password incorrect", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Bad internet connection");
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Bad internet connection...!" , Toast.LENGTH_LONG).show();

            }
        }) {};
        AppController.getInstance().addToRequestQueue(strReq);
        Log.i("URL", strReq.toString());
    }
}
