package com.example.dugout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewBookings extends AppCompatActivity {
    ListView l1;
    SharedPreferences sp;
    String url;
    ArrayList<String> t_name,place,date,timeslot,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings);



        l1 = findViewById(R.id.viewBoookings);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());




        url = "http://" + sp.getString("ip", "") + ":5000/viewTurfBooking";

        RequestQueue queue = Volley.newRequestQueue(ViewBookings.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);

                    t_name = new ArrayList<>(ar.length());
                    place = new ArrayList<>(ar.length());
                    timeslot = new ArrayList<>(ar.length());
                    date = new ArrayList<>(ar.length());
                    status = new ArrayList<>(ar.length());
//                    p_num = new ArrayList<>(ar.length());
//                    lati = new ArrayList<>(ar.length());
//                    longi = new ArrayList<>(ar.length());

                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        t_name.add(jo.getString("turf_name"));
                        place.add(jo.getString("place"));
                        date.add(jo.getString("date"));
                        timeslot.add(jo.getString("timeslot"));
                        status.add(jo.getString("status"));
//                        lati.add(jo.getString("latitude"));
//                        longi.add(jo.getString("longitude"));
//                        lid.add(jo.getString("login_id"));

                    }


                    l1.setAdapter(new Custom5(ViewBookings.this, t_name,place,date,timeslot,status));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userid",sp.getString("lid",""));


                return params;
            }
        };
        queue.add(stringRequest);
    }






}