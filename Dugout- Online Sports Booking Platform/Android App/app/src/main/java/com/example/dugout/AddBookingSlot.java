package com.example.dugout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddBookingSlot extends AppCompatActivity {
//    Time fromt,tot;
    TextView t1;
    EditText t,fromt,tot;
    RadioButton r1,r2,r3;
    SharedPreferences sp;
    String url;
    Button b;
    TimePickerDialog mTimePicker;
    DatePickerDialog datepicker;
    String type="",ftime="",ttime="",date="",lid;
//    String slot[]={"00 to 1","1 to 2","2 to 3","3 to 4","4 to 5","5 to 6","6 to 7","7 to 8","8 to 9","9 to 10","10 to 11","11 to 12","12 to 13","13 to 14","14 to 15","15 to 16","16 to 17","17 to 18","18 to 19","19 to 20","20 to 21","21 to 22","22 to 23","23 to 00"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking_slot);
        fromt=findViewById(R.id.fromTime);
        tot=findViewById(R.id.toTime);
        t=findViewById(R.id.editTextDate);
        r1=findViewById(R.id.r1);
        r2=findViewById(R.id.r2);
        r3=findViewById(R.id.r3);
        t1=findViewById(R.id.textView16);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        url ="http://"+sp.getString("ip","TurfBooking")+":5000";
        b=findViewById(R.id.button18);
        lid=getIntent().getStringExtra("lid");

        fromt.setInputType(InputType.TYPE_NULL);
        fromt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = 0;

                mTimePicker = new TimePickerDialog(AddBookingSlot.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        fromt.setText(selectedHour + ":" + "00");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });



        tot.setInputType(InputType.TYPE_NULL);
        tot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = 0;

                mTimePicker = new TimePickerDialog(AddBookingSlot.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tot.setText(selectedHour + ":" + "00");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        t.setInputType(InputType.TYPE_NULL);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(AddBookingSlot.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                t.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.ttype);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

//                Toast.makeText(getApplicationContext(), "========="+checkedId, Toast.LENGTH_SHORT).show();
                if(R.id.r1==checkedId)
                {
                    type=r1.getText().toString();
                }

                if(R.id.r2==checkedId)
                {
                    type=r2.getText().toString();
                }

                if(R.id.r3==checkedId)
                {
                    type=r3.getText().toString();
                }
                ftime=fromt.getText().toString();
                ttime=tot.getText().toString();
                date=t.getText().toString();




                RequestQueue queue = Volley.newRequestQueue(AddBookingSlot.this);
                url = "http://"+sp.getString("ip","")+":5000/ViewRate";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            String res = json.getString("task");

                           t1.setText(res);
                        } catch (JSONException e) {
//                            Toast.makeText(AddBookingSlot.this, ""+e, Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


//                        Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("type", type);
                        params.put("ftime",ftime);
                        params.put("ttime", ttime);
                        params.put("date",date);
                        params.put("type",type);
                        params.put("tid",getIntent().getStringExtra("lid"));


                        return params;
                    }
                };
                queue.add(stringRequest);




            }
        });
        b=findViewById(R.id.button18);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ava=t1.getText().toString();

                if(ava.equalsIgnoreCase("Not Available"))
                {}
                else {

                    RequestQueue queue1 = Volley.newRequestQueue(AddBookingSlot.this);
                    String url1 = "http://" + sp.getString("ip", "") + ":5000/TurfBooking";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("Your booking is still pending! Wait for the Turf Owner confirmation.")) {
                                    Toast.makeText(AddBookingSlot.this, "Your booking is still pending", Toast.LENGTH_SHORT).show();
                                    Intent ik = new Intent(getApplicationContext(), Home.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(AddBookingSlot.this, "Booking failed", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "Error" + e, Toast.LENGTH_LONG).show();


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

                            params.put("uid", sp.getString("lid", ""));
                            params.put("tid", lid);
                            params.put("type", type);
                            params.put("ftime", ftime);
                            params.put("ttime", ttime);
                            params.put("date", date);


                            return params;
                        }
                    };
                    queue1.add(stringRequest1);


                }








            }
        });


    }
}