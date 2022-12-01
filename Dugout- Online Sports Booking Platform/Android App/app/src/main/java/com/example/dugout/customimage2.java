package com.example.dugout;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class customimage2 extends BaseAdapter{
    private Context context;

    ArrayList<String> a;

    SharedPreferences sp;



    public customimage2(Context applicationContext, ArrayList<String> a) {
        // TODO Auto-generated constructor stub
        this.context=applicationContext;
        this.a=a;



    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return a.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getItemViewType(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(context);
            gridView=inflator.inflate(R.layout.activity_customimage2, null);

        }
        else
        {
            gridView=(View)convertview;

        }
        ImageView tv1=(ImageView) gridView.findViewById(R.id.imageView3);

        TextView tv2=(TextView)gridView.findViewById(R.id.textView31);




        sp= PreferenceManager.getDefaultSharedPreferences(context);

        tv2.setText(a.get(position));

        if(a.get(position).equals("locker"))
        {
            tv1.setImageResource(R.drawable.locker);
        }

else  if(a.get(position).equals("washroom"))
        {
            tv1.setImageResource(R.drawable.washroom);
        }
        else  if(a.get(position).equals("prayer_room"))
        {
            tv1.setImageResource(R.drawable.prayer);
        }
        else  if(a.get(position).equals("parking"))
        {
            tv1.setImageResource(R.drawable.parking);
        }
        else  if(a.get(position).equals("gallery"))
        {
            tv1.setImageResource(R.drawable.gallery);
        }
        else  if(a.get(position).equals("security_camera"))
        {
            tv1.setImageResource(R.drawable.cctv);
        }
        else  if(a.get(position).equals("first_aid"))
        {
            tv1.setImageResource(R.drawable.fa);
        }
        else  if(a.get(position).equals("water"))
        {
            tv1.setImageResource(R.drawable.water);
        }
        else  if(a.get(position).equals("gym"))
        {
            tv1.setImageResource(R.drawable.dumbell);
        }
        else  if(a.get(position).equals("cafeteria"))
        {
            tv1.setImageResource(R.drawable.coffee);
        }
        else  if(a.get(position).equals("shower"))
        {
            tv1.setImageResource(R.drawable.shower);
        }
        else  if(a.get(position).equals("dressing_room"))
        {
            tv1.setImageResource(R.drawable.droom);
        }

        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



        return gridView;

    }

}







