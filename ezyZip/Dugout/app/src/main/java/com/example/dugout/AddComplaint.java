package com.example.dugout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddComplaint extends AppCompatActivity {
    EditText c;
    Button b;
    String url;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);
        c=findViewById(R.id.editTextTextPersonName3);
        b=findViewById(R.id.button10);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String comp=c.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(AddComplaint.this);
                url = "http://" + sp.getString("ip","") + ":5000/AddComplaints";

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
                                Toast.makeText(AddComplaint.this, "Complaint sent successfully!", Toast.LENGTH_SHORT).show();
                                    Intent ik = new Intent(getApplicationContext(), Home.class);
                                startActivity(ik);

                            } else {

                                Toast.makeText(AddComplaint.this, "Invalid", Toast.LENGTH_SHORT).show();

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
                        params.put("complaint", comp);
                        params.put("userid", sp.getString("lid",""));

                        return params;
                    }
                };
                queue.add(stringRequest);

            }
        });

    }
}