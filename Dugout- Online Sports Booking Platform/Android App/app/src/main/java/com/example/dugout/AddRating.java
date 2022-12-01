package com.example.dugout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
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

public class AddRating extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RatingBar r;
//    TextView t;
    Button send;
    String url,tid;
    Spinner s1;
    SharedPreferences sp;
    ArrayList<String> turfid,turname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rating);
        r=findViewById(R.id.ratingBar);
//        t=findViewById(R.id.textView32);
        send=findViewById(R.id.button13);
        s1=findViewById(R.id.spinner2);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url ="http://"+sp.getString("ip", "") + ":5000/ViewAllTurfs";
        s1.setOnItemSelectedListener(this);
        RequestQueue queue = Volley.newRequestQueue(AddRating.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    turfid= new ArrayList<>();
                    turname= new ArrayList<>();



                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        turfid.add(jo.getString("login_id"));
                        turname.add(jo.getString("turf_name"));
//                        Toast.makeText(AddRating.this, "BRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR", Toast.LENGTH_SHORT).show();



                    }
                     ArrayAdapter<String> ad=new ArrayAdapter<String>(AddRating.this,android.R.layout.simple_list_item_1,turname);
                    s1.setAdapter(ad);


//                    L2.setOnItemClickListener(View_Complaints_Reports.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AddRating.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();




                return params;
            }
        };
        queue.add(stringRequest);




        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String rating=r.getRating()+"";
                sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                RequestQueue queue = Volley.newRequestQueue(AddRating.this);
                url = "http://" + sp.getString("ip","") + ":5000/AddRating";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            String res = json.getString("task");

                            if (res.equalsIgnoreCase("success")) {
                                Toast.makeText(AddRating.this, "Thank You for your response!", Toast.LENGTH_SHORT).show();
                                Intent ik = new Intent(getApplicationContext(), Home.class);
                                startActivity(ik);

                            } else {

                                Toast.makeText(AddRating.this, "Invalid", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("rating", rating);
                        params.put("turf_id", tid);
                        params.put("userid", sp.getString("lid",""));

                        return params;
                    }
                };
                queue.add(stringRequest);


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tid=turfid.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}