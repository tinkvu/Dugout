package com.example.dugout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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

public class ViewGallery extends AppCompatActivity
{
    ListView l1;
//    Spinner S1;
    SharedPreferences sp;
    String url ,turf_id;
    ArrayList<String> t_id,t_name,g_id,g_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_gallery);
        l1=findViewById(R.id.photos);
//        S1=findViewById(R.id.spinner2);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url ="http://"+sp.getString("ip","")+":5000/ViewGallery";


v_gallery();


    }




    private void v_gallery() {

        url ="http://"+sp.getString("ip","")+":5000/ViewGallery";
//        s1.setOnItemSelectedListener(Monitoring_signal.this);
        RequestQueue queue = Volley.newRequestQueue(ViewGallery.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    g_id= new ArrayList<>(ar.length());
                    g_img= new ArrayList<>(ar.length());
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        g_id.add(jo.getString("gallery_id"));
                        g_img.add(jo.getString("gallery"));


                    }
                    l1.setAdapter(new custom_image(ViewGallery.this,g_img));



                } catch (JSONException e) {

                    Toast.makeText(getApplicationContext(),"Error"+e,Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
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