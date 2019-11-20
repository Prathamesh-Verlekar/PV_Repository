package com.google.android.gms.samples.vision.ocrreader;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NotificationActivity extends AppCompatActivity {

    Button extend,postpone;
    String id;
    TextView t;
    DatabaseHelper databaseHelper;
    RelativeLayout relativeLayout;

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
    Date d,exdate,weekDate;
    Calendar cal;

    int setYear,setMonth,setDay;
    Date dt;
    String uid,pid;
    String afterWeek;

    String currdate;
    Date exDate;

    Date textDate,dbDate = new Date();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String dateValue;

    DatePickerDialog datek,datepost;
    Intent extras;
    String action="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        sharedPreferences = getSharedPreferences("OCRDEMO", Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("uid","");
        t = (TextView) findViewById(R.id.dateView);
        databaseHelper = new DatabaseHelper(this);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_notification);
        extend= (Button) findViewById(R.id.n_extend);
        postpone= (Button) findViewById(R.id.n_extend);
        extras = getIntent();
        pid = extras.getStringExtra("pid");
        action = extras.getStringExtra("action");
        int nid = extras.getIntExtra("NID",0);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel("moneyl",nid);

        cal = Calendar.getInstance();
        d=new Date();
        t.setText(""+sdf.format(d.getTime()));

        datek=new DatePickerDialog(NotificationActivity.this,kDatePicker,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
        datepost=new DatePickerDialog(NotificationActivity.this,postponeDatePicker,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));

        if(action.equals("consume")){
            boolean value = databaseHelper.deleteDetails(Integer.parseInt(pid),uid);
            if(value) {
                showMessage("Success","Consumed..!!");
            }else {
//                Toast.makeText(this, "Please Try Again", Toast.LENGTH_SHORT).show();
            }

        }
        if(action.equals("extend")){
            datek.show();
        }
        if(action.equals("post")){
            datepost.show();
        }

        extend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datek.show();
            }
        });

        postpone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepost.show();
            }
        });
    }
    public DatePickerDialog.OnDateSetListener kDatePicker = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            setYear = year;
            String setMonth =""+ (month + 1);
            String setDay = ""+dayOfMonth;
            if(setMonth.length() == 1){
                setMonth = "0"+setMonth;
            }
            if(setDay.length() == 1){
                setDay = "0"+setDay;
            }
            dt = new Date();
            currdate = sdf.format(dt.getTime());
            t.setText(setYear+"/"+setMonth+"/"+setDay);
            dateValue = setYear+"/"+setMonth+"/"+setDay;
            if(t.getText().toString().equals(currdate)) {
                toast("Error:Date Must Not Be Current Date");
             }else{
                fidingType();
            }
        }
    };

    public void fidingType(){
        String text;
        try {
            Cursor cs = databaseHelper.showProductDetails(Integer.parseInt(pid),uid);
            if(cs.getCount() == 0){

            }else{
                exDate = sdf.parse(t.getText().toString());
                cal.setTime(exDate);
                while (cs.moveToNext()){
                    text = cs.getString(6);
                    if (text.equals("1 Month"))
                    {
                        cal.add(Calendar.MONTH, -1);
                        try {
                            String s = "" + (cal.get(Calendar.MONTH) + 1);
                            String dayS = "" + cal.get(Calendar.DAY_OF_MONTH);

                            if (s.length() == 1) {
                                s = "0" + (cal.get(Calendar.MONTH) + 1);
                            }
                            if(dayS.length() == 1){
                                dayS = "0" + cal.get(Calendar.DAY_OF_MONTH);
                            }

                            afterWeek = cal.get(Calendar.YEAR) + "/" + s + "/" + dayS;
                            d = new Date();
                            weekDate = sdf.parse(afterWeek);
                            boolean value = d.before(weekDate);

                            if (value == false)
                                toast("Date For Notification Is Gone");
                            else {
                                boolean ans = databaseHelper.updateDate(afterWeek,t.getText().toString(),Integer.parseInt(pid),uid);
                                if (ans){
                                    showMessage("Success","Date Extended");
                                }
//                                    showMessage("Failure","Error Occured While Extending");

                            }
                        }catch (Exception e){

                            e.printStackTrace();
                        }
                    }
                    else if (text.equals("1 Week"))
                    {
                        cal.add(Calendar.WEEK_OF_YEAR, -1);
                        try {
                            String s = "" + (cal.get(Calendar.MONTH) + 1);
                            String dayS = "" + cal.get(Calendar.DAY_OF_MONTH);

                            if (s.length() == 1) {
                                s = "0" + (cal.get(Calendar.MONTH) + 1);
                            }
                            if(dayS.length() == 1){
                                dayS = "0" + cal.get(Calendar.DAY_OF_MONTH);
                            }

                            afterWeek = cal.get(Calendar.YEAR) + "/" + s + "/" + dayS;
                            d = new Date();
                            weekDate = sdf.parse(afterWeek);
                            boolean value = d.before(weekDate);

                            if (value == false)
                                toast("Date For Notification Is Gone");
                            else {
                                boolean ans = databaseHelper.updateDate(afterWeek,t.getText().toString(),Integer.parseInt(pid),uid);
                                if (ans){
                                    showMessage("Success","Date Extended");
                                }
//                                    showMessage("Failure","Error Occured While Extending");

                            }
                        }catch (Exception e){

                            e.printStackTrace();
                        }
                    }else if (text.equals("1 Day")) {
                        cal.add(Calendar.DAY_OF_MONTH, -1);
                        calculation();
                    } else {
                        hourCalculations();
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public DatePickerDialog.OnDateSetListener postponeDatePicker = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            setYear = year;
            String setMonth =""+ (month + 1);
            String setDay = ""+dayOfMonth;
            if(setMonth.length() == 1){
                setMonth = "0"+setMonth;
            }
            if(setDay.length() == 1){
                setDay = "0"+setDay;
            }
            dt = new Date();
            currdate = sdf.format(dt.getTime());
            t.setText(setYear+"/"+setMonth+"/"+setDay);
            if(t.getText().toString().equals(currdate)) {
                toast("Error:Date Must Not Be Current Date");
            }else{
                postponeClaculations();
            }
        }
    };

    public  void hourCalculations(){

        Date compareDate = new Date();
        String currdate = sdf.format(compareDate.getTime());
        if(!currdate.equals(t.getText().toString()))
        {
            boolean ans = databaseHelper.updateDate(t.getText().toString(),t.getText().toString(),Integer.parseInt(pid),uid);
            if(ans)
                showMessage("Success","Date Extended");
//                showMessage("Failure","Error Occured While Extending");
        }
        else
            toast("Invalid Date");
    }


    public void calculation() {

        try {
            String s = "" + (cal.get(Calendar.MONTH) + 1);
            String dayS = "" + cal.get(Calendar.DAY_OF_MONTH);

            if (s.length() == 1) {
                s = "0" + (cal.get(Calendar.MONTH) + 1);
            }
            if(dayS.length() == 1){
                dayS = "0" + cal.get(Calendar.DAY_OF_MONTH);
            }

            afterWeek = cal.get(Calendar.YEAR) + "/" + s + "/" + dayS;
            d = new Date();
            weekDate = sdf.parse(afterWeek);
            boolean value = d.before(weekDate);

            if (value == false){
                toast("Date For Notification Is Gone");
            }
            else {
                boolean ans = databaseHelper.updateDate(afterWeek,t.getText().toString(),Integer.parseInt(pid),uid);
                if (ans){
                    showMessage("Success","Date Extended");
                }
                else{
//                    showMessage("Failure","Error Occured While Extending");
                }
            }
        }catch (Exception e){

            e.printStackTrace();
        }
    }

    public void postponeClaculations(){

            try{
                Cursor cs = databaseHelper.showProductDetails(Integer.parseInt(pid),uid);
                if(cs.getCount() == 0){
                  //  Toast.makeText(NotificationActivity.this,"Table is Empty ",Toast.LENGTH_LONG).show();
                }
                else
                {
                    while (cs.moveToNext()){
                        String dateText = cs.getString(3);
                        dbDate = sdf.parse(dateText);
                        textDate = sdf.parse(t.getText().toString());
                        boolean ans = textDate.before(dbDate);
                        if(!ans) {
                            toast("Exceeding The Expiry Date");
                         }else {
                            boolean updateAns = databaseHelper.updateDate(t.getText().toString(),t.getText().toString(),Integer.parseInt(pid),uid);
                        if(updateAns){
                            showMessage("Success","Sucessfully Postponed");
                        }
//                            showMessage("Failure","Error:Please Try Again");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showMessage(String title,String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(NotificationActivity.this);
        builder.setMessage(Message);
        builder.setTitle(title);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void toast(String mesg)
    {
        Toast.makeText(this,mesg, Toast.LENGTH_SHORT).show();
        if(action.equals("extend"))
        {
            extend.setVisibility(View.VISIBLE);
        }
        else if(action.equals("post"))
        {
            postpone.setVisibility(View.VISIBLE);
        }
    }
}
