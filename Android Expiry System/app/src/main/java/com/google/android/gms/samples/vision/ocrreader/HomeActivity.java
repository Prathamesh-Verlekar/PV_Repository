package com.google.android.gms.samples.vision.ocrreader;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Stack;

public class HomeActivity extends AppCompatActivity {
    DatabaseHelper myHelper;
    FloatingActionButton floatingActionButton;
    Button save,cancel;
    TextView timeView;
    int t_hours,t_minutes;
    Spinner spinner;
    Dialog d;
    ListView listView ;
    Cursor res;
    String[] productName,productType,expiryDate;

    ArrayAdapter<CharSequence> adapter;



    Notification.Builder noti;
    PendingIntent pendingIntent;

    SharedPreferences sharedPreferences;
    String uid;

    int id = 1;
    Button b;

    SharedPreferences.Editor editor;

    CoordinatorLayout coordinatorLayout;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         sharedPreferences = getSharedPreferences("OCRDEMO", Context.MODE_PRIVATE);
         uid = sharedPreferences.getString("uid","");
         myHelper = new DatabaseHelper(this);

         coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

         if(!myHelper.getTime(uid))
         {
             myHelper.setForFirstTime(uid);
         }

         spinner = (Spinner)findViewById(R.id.displaySpinner);

        listView = (ListView) findViewById(R.id.myListView);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.addFloatButton);


         adapter = ArrayAdapter.createFromResource(this,R.array.display_list, android.R.layout.simple_spinner_item);
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinner.setAdapter(adapter);

        Intent serviceIntent = new Intent(HomeActivity.this,BackgroundService.class);
         startService(serviceIntent);

        floatingActionButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeActivity.this,ProductDetails.class);
                        startActivity(intent);
                    }
                }
        );
        selection();
    }
    public void selection(){
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (spinner.getSelectedItemPosition()){
                            case 0 :
                                res = myHelper.showDetails(uid);
                                showListDetails();
                                break;
                            case 1 :
                                res = myHelper.showItemDetails("FOOD",uid);
                                showListDetails();
                                break;
                            case 2 :
                                res = myHelper.showItemDetails("DRUG",uid);
                                showListDetails();
                                break;
                            case 3 :
                                res = myHelper.showItemDetails("DOCUMENT",uid);
                                showListDetails();
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );
    }
    @Override
    protected void onResume() {
        super.onResume();
        res = myHelper.showDetails(uid);
        showListDetails();
    }
    public void showListDetails(){
         if(res.getCount() == 0) {
             listView.setAdapter(null);
             Snackbar.make(coordinatorLayout,"Products Not Available.",Snackbar.LENGTH_LONG).show();
         }
        else {
             productName = new String[res.getCount()];
             productType = new String[res.getCount()];
             expiryDate = new String[res.getCount()];

             int i=0;
             while (res.moveToNext())
             {
                 productName[i] = res.getString(2);
                 productType[i] = res.getString(4);
                 expiryDate[i] = res.getString(3);
                 i++;
             }
             DataAdapter adapt=new DataAdapter(HomeActivity.this,productName,productType,expiryDate);
             listView.setAdapter(adapt);
         }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.setTime){
            timeDialog();
        }else if(item.getItemId() == R.id.logOut){

            editor = sharedPreferences.edit();
            editor.putString("uid","");
            editor.commit();
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void timeDialog(){
        d = new Dialog(HomeActivity.this);
        d.setContentView(R.layout.time_dialog_layout);
       timeView = (TextView) d.findViewById(R.id.timeView);
       timeView.setText(""+myHelper.showTime(uid));
        String t[]=timeView.getText().toString().split(":");
        t_hours=Integer.parseInt(t[0]);
        t_minutes=Integer.parseInt(t[1]);
        save = (Button) d.findViewById(R.id.saveButton);
        cancel = (Button) d.findViewById(R.id.cancelLayoutButton);

        eventCalls();
        d.show();
    }

    public void eventCalls(){
        cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.cancel();
                    }
                }
        );

        timeView.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        showDialog(1);
                        return false;
                    }
                }
        );


        save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         boolean ans = myHelper.setTime(timeView.getText().toString(),uid);
                        if(ans)
                            d.cancel();
                    }
                }
        );

    }
    public Dialog onCreateDialog(int id){

        if(id == 1)
            return new TimePickerDialog(HomeActivity.this,kTimePicker,t_hours,t_minutes,true);

        return null;
    }
    public TimePickerDialog.OnTimeSetListener kTimePicker = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String h = "" + hourOfDay;
            String m = "" + minute;

            if(m.length() == 1){
                m = "0"+m;
            }
            if (h.length() == 1){
                h = "0"+h;
            }
             String time = h+":"+m;
            timeView.setText(time);
        }
    };


}
