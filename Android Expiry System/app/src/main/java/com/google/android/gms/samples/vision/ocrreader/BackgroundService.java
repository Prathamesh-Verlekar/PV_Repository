package com.google.android.gms.samples.vision.ocrreader;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Dell on 2/24/2017.
 */

public class BackgroundService extends Service {



    Timer timer;
    TimerTask timerTask;
    Handler handler=new Handler();
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat sdft=new SimpleDateFormat("HH:mm");
    DatabaseHelper databaseHelper;
    Cursor cursor,cursor1;
    Date dt = new Date();
    View v;
    String databseTime[];
    int i=0,id = 46512;
    String b = "";

    boolean timeAvailable;
    boolean hourAvailable=false;
    LinearLayout linearLayout;
    SharedPreferences sharedPreferences;
    String uid;
    String email,to,sub,body,name;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getSharedPreferences("OCRDEMO", Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("uid","");
        databaseHelper = new DatabaseHelper(this);
        timer = new Timer();
        demo();
        timer.schedule(timerTask,0,5000);
    }
    public void demo(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(!timeAvailable) {
                            getNotification();
                        }

                    if(!hourAvailable){
                            getHourNotification();
                        }
                    }
                });
            }
        };
    }

    public void getNotification(){

        Date dt = new Date();
        String currdate = sdf.format(dt.getTime());
        String curTime = sdft.format(dt.getTime());
        timeAvailable  = databaseHelper.getTimeNotification(curTime,uid);

        if(timeAvailable) {
            cursor = databaseHelper.getDateNotification(currdate,uid);
            if(cursor.getCount() == 0){
                //Toast.makeText(BackgroundService.this,"Table Is Empty",Toast.LENGTH_LONG).show();
            }
            else
            {
//                Toast.makeText(BackgroundService.this,"caught time",Toast.LENGTH_LONG).show();
                while (cursor.moveToNext()) {

                    if(cursor.getString(2).equals("Yes")){
                        if(cursor.getString(3).equals("1 Month")){
                            b = "Product "+cursor.getString(4) + " Going To Expire In One Month";

                            email(b);

                        }
                        else if(cursor.getString(3).equals("1 Week")){
                            b = "Product "+cursor.getString(4) + " Going To Expire In One Week";

                            email(b);

                        }else if(cursor.getString(3).equals("1 Day")){
                             b = cursor.getString(4) + " Going To Expire In One Day";
                             email(b);
                        }else{

                            b = cursor.getString(4) + " Going To Expire In One Hour";
                            email(b);
                        }
                    }
                    if (cursor.getString(0).equals("FOOD") || cursor.getString(0).equals("DRUG"))
                    {
                        notify_food(""+cursor.getInt(1),cursor.getString(0),cursor.getString(4));
                    }
                    else
                    {
                        notifty_doc(""+cursor.getString(1),cursor.getString(0),cursor.getString(4));
                    }

                    timer.cancel();
                    timer = new Timer();
                    demo();
                    timer.schedule(timerTask, 75000, 5000);
                }
            }
        }
        timeAvailable = false;
    }

    public void getHourNotification(){
        hourAvailable=true;
        dt = new Date();
        String currdate = sdf.format(dt.getTime());

        Cursor c = databaseHelper.showDetails(uid);
        if(c.getCount() == 0){
//           Toast.makeText(BackgroundService.this,"Table Is Empty",Toast.LENGTH_LONG).show();
        }
        else {
            while (c.moveToNext())
            {

                if(c.getString(6).equals("1 Hour") && c.getString(7).equals(currdate)){
                    Cursor timeCs = databaseHelper.showTimeDetails(uid);
                    dt = new Date();
                    String curTime = sdft.format(dt.getTime());
                    cursor = databaseHelper.showDetails(uid);

                    if(timeCs.getCount() == 0){
//                        Toast.makeText(BackgroundService.this,"No Time Details",Toast.LENGTH_LONG).show();
                    }else {
                        timeCs.moveToFirst();
                        Calendar cal = Calendar.getInstance();

                        try{
                            Date exdate = sdft.parse(timeCs.getString(0));
                            cal.setTime(exdate);
                            cal.add(Calendar.HOUR_OF_DAY, -1);
                            String h = ""+cal.get(Calendar.HOUR_OF_DAY);
                            String m = ""+cal.get(Calendar.MINUTE);
                            if(h.length() == 1) {
                                h = "0"+h;
                            }
                            if(m.length() == 1){
                                m = "0"+m;
                            }

                            String s = h +":"+m;
                             if (s.equals(curTime))
                             {
                                 Cursor cAns = databaseHelper.showDetails(uid);
                                if(cAns.getCount() == 0){
                                //    Toast.makeText(BackgroundService.this,"Empty",Toast.LENGTH_LONG).show();
                                }else{
                                    while (cAns.moveToNext()){
                                        if (cAns.getString(4).equals("FOOD") || cAns.getString(4).equals("DRUG")) {
                                            notify_food(""+cAns.getInt(0),cAns.getString(4),cAns.getString(2));
                                        }
                                        else
                                        {
                                            if(cAns.getString(6).equals("1 Day") || cAns.getString(6).equals("1 Hour")){
                                                notifyDay(""+cAns.getInt(0),cAns.getString(4),cAns.getString(2));

                                            }else {
                                                notifty_doc(""+cAns.getInt(0),cAns.getString(4),cAns.getString(2));
                                            }

                                        }
                                    }
                                }
                            }
                             else
                             {
                            }
                        }catch (Exception e){
                        }
                    }
                }

                timer.cancel();
                timer = new Timer();
                demo();
                timer.schedule(timerTask, 75000, 5000);
            }
        }
        hourAvailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public int random()
    {
        String ans="";
        int[] i=new int[]{0,1,2,3,4,5,6,7,8,9};
        Random rand=new Random();
        ans+=rand.nextInt(i.length);
        ans+=rand.nextInt(i.length);
        ans+=rand.nextInt(i.length);
        ans+=rand.nextInt(i.length);
        return Integer.parseInt(ans);
    }

    public void notifty_doc(String id,String type,String pname)
    {
        int notifcationID = random();
        Intent extend = new Intent(BackgroundService.this, NotificationActivity.class);
        extend.putExtra("pid",id);
        extend.putExtra("action","extend");
        extend.putExtra("NID",notifcationID);

        PendingIntent pextend =
                PendingIntent.getActivity(BackgroundService.this, random(),
                        extend, PendingIntent.FLAG_CANCEL_CURRENT);


        Intent post = new Intent(BackgroundService.this, NotificationActivity.class);
        post.putExtra("pid", id);
        post.putExtra("action", "post");
        post.putExtra("NID", notifcationID);

        PendingIntent ppost =
                PendingIntent.getActivity(BackgroundService.this, random(),
                        post, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationManager nm = (NotificationManager)
                BackgroundService.this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new
                Notification.Builder(BackgroundService.this);

        builder.setContentIntent(null)
                .setSmallIcon(R.drawable.icon2)
                .setTicker("Money Lender")
                .setWhen(System.currentTimeMillis())
                .setContentTitle(pname)
                .setContentText("Type : "+type)
                .addAction(R.drawable.cal, "Renew", pextend)
                .addAction(R.drawable.cal,"Postpone", ppost);

        Notification n = builder.getNotification();
        nm.notify("moneyl", notifcationID, n);
    }

    public void notify_food(String id,String type,String pname)
    {
        int notifcationID = random();
        Intent consume = new Intent(BackgroundService.this, NotificationActivity.class);
        consume.putExtra("pid",id);
        consume.putExtra("action", "consume");
        consume.putExtra("NID", notifcationID);

        PendingIntent pconsume =
                PendingIntent.getActivity(BackgroundService.this, random(),
                        consume, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationManager nm = (NotificationManager)
                BackgroundService.this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new
                Notification.Builder(BackgroundService.this);

        builder.setContentIntent(null)
                .setSmallIcon(R.drawable.icon2)
                .setTicker("Money Lender")
                .setWhen(System.currentTimeMillis())
                .setContentTitle(pname)
                .setContentText("Type : "+type)
                .addAction(R.drawable.dinnerimg, "Consume", pconsume);

        Notification n = builder.getNotification();
        nm.notify("moneyl", notifcationID, n);
    }

    public void notifyDay(String id,String type,String pname){
        int notifcationID = random();
        Intent dextend = new Intent(BackgroundService.this, NotificationActivity.class);
        dextend.putExtra("pid",id);
        dextend.putExtra("action","extend");
        dextend.putExtra("NID",notifcationID);

        PendingIntent pextend =
                PendingIntent.getActivity(BackgroundService.this, random(),
                        dextend, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager nm = (NotificationManager)
                BackgroundService.this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new
                Notification.Builder(BackgroundService.this);

        builder.setContentIntent(null)
                .setSmallIcon(R.drawable.icon2)
                .setTicker("Money Lender")
                .setWhen(System.currentTimeMillis())
                .setContentTitle(pname)
                .setContentText("Type : "+type)
                .addAction(R.drawable.cal, "Renew", pextend);

        Notification n = builder.getNotification();
        nm.notify("moneyl", notifcationID, n);
    }

    public void email(String text){
         try {

            to= URLEncoder.encode(uid, "utf-8");
            name= URLEncoder.encode("Android Expiry", "utf-8");
            sub= URLEncoder.encode("Expiry Notification", "utf-8");
            body= URLEncoder.encode(text, "utf-8");
            email="http://expirymail.nevontech.com/?to="+to+"&name="+name+"&sub="+sub+"&mail="+body;
        } catch (UnsupportedEncodingException e) {
//            Toast.makeText(this, "con-"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        StringRequest request1 =new StringRequest(email, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
//                Toast.makeText(BackgroundService.this,response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(BackgroundService.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(BackgroundService.this);
        requestQueue.add(request1);
    }


}
