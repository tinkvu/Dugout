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

public class ViewTurf extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1,l2;
    SharedPreferences sp;
    ArrayList<String> t_name,place,post,pin,p_num,lati,longi,lid;
    String url,url1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_turf);
        l1=findViewById(R.id.list);
        l2=findViewById(R.id.turfall);

        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        viewall();
        url ="http://"+sp.getString("ip","")+":5000/ViewTurfs";
//        s1.setOnItemSelectedListener(Monitoring_signal.this);
        RequestQueue queue = Volley.newRequestQueue(ViewTurf.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    t_name= new ArrayList<>(ar.length());
                    place= new ArrayList<>(ar.length());
                    pin= new ArrayList<>(ar.length());
                    post= new ArrayList<>(ar.length());
                    p_num= new ArrayList<>(ar.length());
                    lati= new ArrayList<>(ar.length());
                    longi= new ArrayList<>(ar.length());
                    lid= new ArrayList<>(ar.length());
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        t_name.add(jo.getString("turf_name"));
                        place.add(jo.getString("place"));
                        post.add(jo.getString("post"));
                        pin.add(jo.getString("pin"));
                        p_num.add(jo.getString("mob"));
                        lati.add(jo.getString("latitude"));
                        longi.add(jo.getString("longitude"));
                        lid.add(jo.getString("login_id"));

                    }

//                    ArrayAdapter<String> ad=new ArrayAdapter<String>(ViewTurf.this,android.R.layout.simple_spinner_item,t_name);
//                    l1.setAdapter(ad);
//
//
                    l1.setOnItemClickListener(ViewTurf.this);
                    l1.setAdapter(new Custom3(ViewTurf.this,t_name,place,p_num));

                } catch (JSONException e) {
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
                params.put("lati",LocationService.lati);
                params.put("longi",LocationService.logi);



                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void viewall() {


        url ="http://"+sp.getString("ip","")+":5000/ViewAllTurfs";
//        s1.setOnItemSelectedListener(Monitoring_signal.this);
        RequestQueue queue = Volley.newRequestQueue(ViewTurf.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    t_name= new ArrayList<>(ar.length());
                    place= new ArrayList<>(ar.length());
                    pin= new ArrayList<>(ar.length());
                    post= new ArrayList<>(ar.length());
                    p_num= new ArrayList<>(ar.length());
                    lati= new ArrayList<>(ar.length());
                    longi= new ArrayList<>(ar.length());
                    lid= new ArrayList<>(ar.length());
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        t_name.add(jo.getString("turf_name"));
                        place.add(jo.getString("place"));
                        post.add(jo.getString("post"));
                        pin.add(jo.getString("pin"));
                        p_num.add(jo.getString("mob"));
                        lati.add(jo.getString("latitude"));
                        longi.add(jo.getString("longitude"));
                        lid.add(jo.getString("login_id"));

                    }

//                    ArrayAdapter<String> ad=new ArrayAdapter<String>(ViewTurf.this,android.R.layout.simple_spinner_item,t_name);
//                    l1.setAdapter(ad);
//
//
                    l2.setOnItemClickListener(ViewTurf.this);
                    l2.setAdapter(new Custom3(ViewTurf.this,t_name,place,p_num));

                } catch (JSONException e) {
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
                params.put("lati",LocationService.lati);
                params.put("longi",LocationService.logi);



                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i=new Intent(getApplicationContext(),ViewTurfInfo.class);


        SharedPreferences.Editor ed=sp.edit();
        ed.putString("turfid",lid.get(position));
        ed.commit();
        i.putExtra("tname",t_name.get(position));
        i.putExtra("place",place.get(position));
        i.putExtra("post",post.get(position));
        i.putExtra("pin",pin.get(position));
        i.putExtra("phone",p_num.get(position));
        i.putExtra("l_id",lid.get(position));
        i.putExtra("lati",lati.get(position));
        i.putExtra("longi",longi.get(position));







        startActivity(i);


    }

}
