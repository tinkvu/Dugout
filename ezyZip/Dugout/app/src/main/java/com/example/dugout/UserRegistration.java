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

public class  UserRegistration extends AppCompatActivity {
    EditText fname,lname,place,post,email,pin,phone,password;
    Button b;
    SharedPreferences sp;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        fname=findViewById(R.id.editTextTextPersonName5);
        lname=findViewById(R.id.editTextTextPersonName6);
        place=findViewById(R.id.editTextTextPersonName7);
        post=findViewById(R.id.editTextTextPersonName8);
        email=findViewById(R.id.editTextTextEmailAddress);
        pin=findViewById(R.id.editTextNumber);
        phone=findViewById(R.id.editTextNumber2);
        password=findViewById(R.id.editTextTextPassword2);

        b=findViewById(R.id.button11);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String name1=fname.getText().toString();
            String name2=lname.getText().toString();
            String pname=place.getText().toString();
            String npost=post.getText().toString();
            String mail=email.getText().toString();
            String npin=pin.getText().toString();
            String nphone=phone.getText().toString();
            String pwd=password.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(UserRegistration.this);
                url = "http://" + sp.getString("ip","") + ":5000/UserRegistration";

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
//                                String lid = json.getString("id");
//                                SharedPreferences.Editor edp = sp.edit();
//                                edp.putString("lid", lid);
//                                edp.commit();
                                Toast.makeText(UserRegistration.this, "Registration Success!", Toast.LENGTH_SHORT).show();
                                Intent ik = new Intent(getApplicationContext(), Home.class);
                                startActivity(ik);

                            } else {

                                Toast.makeText(UserRegistration.this, "Registration Failed!", Toast.LENGTH_SHORT).show();

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
                        params.put("firstname", name1);
                        params.put("lastname", name2);
                        params.put("place", pname);
                        params.put("post", npost);
                        params.put("pin", npin);
                        params.put("email", mail);
                        params.put("pnum", nphone);
                        params.put("password", pwd);





                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
}