package com.example.dugout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewTurfInfo extends AppCompatActivity {
    TextView turfame,place,post,pin,phonenumber;
    SharedPreferences sp;
    Button b1,b2,b3,b4;
    String Lid;
    ImageView t1;
int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_turf_info);
        turfame=findViewById(R.id.textView6);
        place=findViewById(R.id.textView8);
        post=findViewById(R.id.textView11);
        pin=findViewById(R.id.textView12);
        phonenumber=findViewById(R.id.textView14);
        b3=findViewById(R.id.button9);
        b2=findViewById(R.id.button12);
        b1=findViewById(R.id.button17);
        b4=findViewById(R.id.button16);
        t1=findViewById(R.id.imageView2);
        Lid=getIntent().getStringExtra("l_id");
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        turfame.setText(getIntent().getStringExtra("tname"));
        place.setText(getIntent().getStringExtra("place"));
        post.setText(getIntent().getStringExtra("post"));
        pin.setText(getIntent().getStringExtra("pin"));
        phonenumber.setText(getIntent().getStringExtra("phone"));
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?q="+getIntent().getStringExtra("lati")+","+getIntent().getStringExtra("longi")));
                startActivity(intent);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ald=new AlertDialog.Builder(ViewTurfInfo.this);
                ald.setTitle("Turf book")
                        .setPositiveButton("Book", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                Intent i=new Intent(getApplicationContext(),AddBookingSlot.class);
                                i.putExtra("lid",Lid);
                                startActivity(i);





                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {






                            }
                        });

                AlertDialog al=ald.create();
                al.show();










            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),ViewFacilities.class);
                startActivity(i);

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),ViewGallery.class);
                startActivity(i);

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),ViewTurf.class);
                startActivity(i);

            }
        });









    }
}