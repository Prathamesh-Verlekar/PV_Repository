package com.example.root.fusion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/OpenSans_Light.ttf");
        TextView textView= (TextView) findViewById(R.id.txt1);
        TextView textView1=(TextView) findViewById(R.id.events);
        textView.setTypeface(typeface);
        textView1.setTypeface(typeface);*/
        LinearLayout textView= (LinearLayout) findViewById(R.id.main);

        overrideFonts(getApplicationContext(),textView);

    }
    private void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView ) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans_Light.ttf"));
            }
        } catch (Exception e) {
        }
    }
    public void doSomething(View v){

        if(v.getId()==R.id.image1){
       Intent i = new Intent(this,About_fusion.class);
       startActivity(i);
        }
        if(v.getId()==R.id.image2){
            Intent i2 = new Intent(this,about_us.class);
            startActivity(i2);
        }
        if(v.getId()==R.id.image3){
            Intent i2 = new Intent(this,tabtest.class);
            startActivity(i2);
        }
        if(v.getId()==R.id.image5){
            Intent i2 = new Intent(this,Visitweb.class);
            startActivity(i2);
        }

        if(v.getId()==R.id.image6){
            Intent i2 = new Intent(this,Web.class);
            startActivity(i2);
        }
        if(v.getId()==R.id.cont){
            Intent i2 = new Intent(this,Contactus.class);
            startActivity(i2);
        }
        if(v.getId()==R.id.dev){
            Intent i2 = new Intent(this,Developed.class);
            startActivity(i2);
        }
        if(v.getId()==R.id.scduele){
            Intent i2 = new Intent(this,schedule.class);
            startActivity(i2);
        }

    }
}
