package com.example.dugout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class Complaints extends AppCompatActivity {
    ListView L1;
    Button b;
    SharedPreferences sp;
    String url;
    ArrayList<String> date,complaint,reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        L1= findViewById(R.id.complaints);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b=findViewById(R.id.button19);
        url ="http://"+sp.getString("ip","")+":5000/ViewComplaints";
        RequestQueue queue = Volley.newRequestQueue(Complaints.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    date= new ArrayList<>(ar.length());
                    complaint= new ArrayList<>(ar.length());
                    reply= new ArrayList<>(ar.length());

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        date.add(jo.getString("date"));
                        complaint.add(jo.getString("complaint"));
                        reply.add(jo.getString("reply"));
//                        lid.add(jo.getString("login_id"));

                    }

//                    ArrayAdapter<String> ad=new ArrayAdapter<String>(Complaints.this,android.R.layout.simple_spinner_item,t_name);
//                    l1.setAdapter(ad);
//
//
//                    l1.setOnItemClickListener(ViewTurf.this);




                    L1.setAdapter(new Custom3(Complaints.this,date,complaint,reply));
//                    l1.setOnItemClickListener(viewuser.this);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid",sp.getString("lid",""));



                return params;
            }
        };
        queue.add(stringRequest);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),AddComplaint.class);
                startActivity(i);

            }
        });




    }
}