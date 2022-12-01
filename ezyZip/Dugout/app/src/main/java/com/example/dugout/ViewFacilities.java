package com.example.dugout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class ViewFacilities extends AppCompatActivity {
    ListView l;
    String url;
    SharedPreferences sp;
    ArrayList<String> fac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_facilities);
        l=findViewById(R.id.facility);

        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url ="http://"+sp.getString("ip","")+":5000/TurfFacilities";




        RequestQueue queue = Volley.newRequestQueue(ViewFacilities.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            // Display the response string.
//            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

            Log.d("+++++++++++++++++",response);
            try {
                String fact[]=response.split(",");

//                Toast.makeText(getApplicationContext(),"fac"+fact[1],Toast.LENGTH_LONG).show();

                JSONArray ar=new JSONArray(response);

                fac= new ArrayList<>(ar.length());
                for(int i=0;i<ar.length();i++)
                {
                    JSONObject jo=ar.getJSONObject(i);
                    fac.add(jo.getString("facility"));


                }









                l.setAdapter(new customimage2(ViewFacilities.this,fac));

            }
            catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Error"+e,Toast.LENGTH_LONG).show();

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
                params.put("turf_id",sp.getString("turfid",""));



                return params;
            }
        };

        queue.add(stringRequest);


    }

}