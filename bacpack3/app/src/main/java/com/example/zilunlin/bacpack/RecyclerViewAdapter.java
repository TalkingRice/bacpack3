package com.example.zilunlin.bacpack;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zilunlin.bacpack.Generic.DetailActivity;
import com.example.zilunlin.bacpack.Generic.LoginActivity;
import com.example.zilunlin.bacpack.Generic.MainActivity;
import com.example.zilunlin.bacpack.network.AppController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zilun Lin on 9/2/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<HashMap<String, String>> eventList;
    String user_id;
    Context context;


    public RecyclerViewAdapter(Context context, ArrayList<HashMap<String, String>> eventList, String user_id) {
        this.eventList = eventList;
        Log.d("eventList", "Event list size" + eventList.size());
        Log.d("eventList", "" + eventList.toString());
        this.context = context;
        this.user_id = user_id;
    }

    public class ViewHolderText extends RecyclerView.ViewHolder {
        public TextView eventName, date, event_type;
        public ImageButton primary_button, negative_button;
        public ConstraintLayout linearLayout;


        public ViewHolderText(View view) {
            super(view);
            eventName = (TextView) view.findViewById(R.id.event_name);
            date = (TextView) view.findViewById(R.id.date);
            event_type = (TextView) view.findViewById(R.id.event_type);
            primary_button = (ImageButton) view.findViewById(R.id.primary_button);
            negative_button = (ImageButton) view.findViewById(R.id.disapprove);
            linearLayout = (ConstraintLayout) view.findViewById(R.id.item_layout);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == R.layout.list_item_joined) {
            View textItemView1 = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_joined, parent, false);
            return new ViewHolderText(textItemView1);
        } else if(viewType == R.layout.list_item_unapproved){
            View textItemView2 = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_unapproved, parent, false);
            return new ViewHolderText(textItemView2);
        }
        else {
            View textItemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ViewHolderText(textItemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ViewHolderText viewHolderText = (ViewHolderText) holder;

        final HashMap<String, String> single_map = eventList.get(position);
        viewHolderText.eventName.setText(single_map.get("name"));
        viewHolderText.date.setText(single_map.get("start_datetime"));
        viewHolderText.event_type.setText(single_map.get("event_type"));
        viewHolderText.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("event_id", single_map.get("event_id"));
                context.startActivity(intent);
            }
        });
        if (holder.getItemViewType() == R.layout.list_item_joined) {
            viewHolderText.primary_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);

                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String param1 = user_id;
                            String param2 = single_map.get("event_id");

                            String tag_string_req = "req_joinEvents";
                            StringRequest strReq = new StringRequest(Request.Method.GET, "http://192.168.0.103/android/CRUD/user/quitEventUser.php?id=" + param1 + "&event_id=" + param2, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // progressDialog.dismiss();
                                    Log.i("responseTest", response);

                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            }) {
                            };
                            AppController.getInstance().addToRequestQueue(strReq);
                            Log.i("URL", strReq.toString());
                            eventList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, getItemCount());
                        }
                    });


                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.setTitle("Are you sure that you want to quit this event?");
                    builder.setMessage("You can always join again in the Home page.");

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    Toast.makeText(context, "hi", Toast.LENGTH_LONG).show();
                }
            });
        } else if (holder.getItemViewType()==R.layout.list_item_unapproved){
            //handling the event when the user approves, first it launches a dialog to confirm
            viewHolderText.primary_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder confirmation = new AlertDialog.Builder(context);
                    confirmation.setTitle("Are you sure that you want to approve " + single_map.get("name") + "?");
                    confirmation.setMessage("Have you checked all of its details yet? (You can do so by clicking into the event)");
                    confirmation.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(context, "Approving event " + single_map.get("event_id"), Toast.LENGTH_SHORT).show();

                            //Joins event using volley
                            StringRequest strReq = new StringRequest(Request.Method.POST, Config.URL_APPROVE_EVENT, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // progressDialog.dismiss();
                                    Log.i("responseTest", response);
                                    eventList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, getItemCount());
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("event_id", single_map.get("event_id"));
                                    params.put("approval", "1");
                                    return params;
                                }
                            };
                            AppController.getInstance().addToRequestQueue(strReq);
                            Intent logIn = new Intent(context, MainActivity.class);
                            logIn.putExtra("user_id", user_id);
                            Log.i("URL", strReq.toString());
                        }
                    });
                    confirmation.show();
                }
            });
                viewHolderText.negative_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
        }
        else {
            //Handles when the user hasn't joined the event yet
            viewHolderText.primary_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user_id != "2") {
                        Toast.makeText(context, "Joining event" + user_id + "  " + single_map.get("event_id"), Toast.LENGTH_SHORT).show();

                        //Joins event using volley
                        String tag_string_req = "req_joinEvents";
                        StringRequest strReq = new StringRequest(Request.Method.POST, Config.URL_USER_JOIN_EVENT, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // progressDialog.dismiss();
                                Log.i("responseTest", response);
                                eventList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("event_id", single_map.get("event_id"));
                                params.put("id", user_id);
                                return params;
                            }
                        };
                        AppController.getInstance().addToRequestQueue(strReq);
                        Intent logIn = new Intent(context, MainActivity.class);
                        logIn.putExtra("user_id", user_id);
                        Log.i("URL", strReq.toString());


                    } else {
                        Intent logIn = new Intent(context, LoginActivity.class);
                        context.startActivity(logIn);
                    }

                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (eventList.get(position).get("approval").contains("1")) {
            if (eventList.get(position).get("joined").contains("false")) {
                return R.layout.list_item;
            } else {
                if (eventList.get(position).get("joined").contains("true")) {
                    return R.layout.list_item_joined;
                } else {
                    return R.layout.list_item;
                }
            }
        } else{
            return R.layout.list_item_unapproved;
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

}



