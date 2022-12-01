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

public class custom_image extends BaseAdapter{
    private Context context;

    ArrayList<String> a;

    SharedPreferences sp;



    public custom_image(Context applicationContext, ArrayList<String> a) {
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
            gridView=inflator.inflate(R.layout.activity_custom_image, null);

        }
        else
        {
            gridView=(View)convertview;

        }
        ImageView tv1=(ImageView) gridView.findViewById(R.id.imageView);





        sp= PreferenceManager.getDefaultSharedPreferences(context);




        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        java.net.URL thumb_u;
        try {


            thumb_u = new java.net.URL("http://"+sp.getString("ip","")+":5000/static/fimg/"+a.get(position));
            Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
            tv1.setImageDrawable(thumb_d);

        }
        catch (Exception e)
        {
            Log.d("errsssssssssssss",""+e);
        }




        return gridView;

    }

}







