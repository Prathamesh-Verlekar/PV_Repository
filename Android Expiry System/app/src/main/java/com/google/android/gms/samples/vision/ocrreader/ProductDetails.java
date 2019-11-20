package com.google.android.gms.samples.vision.ocrreader;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProductDetails extends AppCompatActivity {

    Spinner typeSpinner,notifySpinner;
    ArrayAdapter<CharSequence> adapter,adapter2;

    private static final int RC_OCR_CAPTURE = 9003;

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");

    Date d,exdate,weekDate;
    Calendar cal;
    String afetrWeek;
    String month,day;

    Button addButton;
    RadioGroup radioGroup;
    EditText pname;
    TextView setDate;
    RadioButton radioButton;

    DatabaseHelper myHelper;

    static final int VALUE=1;
    int setYear,setMonth,setDay;
    Date dt;

    SharedPreferences sharedPreferences;
    String uid;

    ImageView imageView;
    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        relativeLayout = (RelativeLayout)findViewById(R.id.activity_product_details);

        sharedPreferences = getSharedPreferences("OCRDEMO", Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("uid","");

        myHelper = new DatabaseHelper(this);
        imageView = (ImageView) findViewById(R.id.myImageView);

        pname = (EditText) findViewById(R.id.pname);
        radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
        int id = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(id);

         setDate = (TextView) findViewById(R.id.setDate);
         cal = Calendar.getInstance();

        typeSpinner = (Spinner)findViewById(R.id.typeSpinner);
        notifySpinner = (Spinner)findViewById(R.id.notifySpinner);
        addButton = (Button) findViewById(R.id.addProductButton);

        adapter = ArrayAdapter.createFromResource(this,R.array.product_type, android.R.layout.simple_spinner_item);
        adapter2 = ArrayAdapter.createFromResource(this,R.array.notify_period, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSpinner.setAdapter(adapter);
        notifySpinner.setAdapter(adapter2);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        d=new Date();
        setDate.setText(""+sdf.format(d.getTime()));
        eventCalls();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    public void eventCalls(){

        setDate.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        showDialog(VALUE);
                        return false;
                    }
                }
        );

        addButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isFieldEmpty(pname)) {
                            dateCalculation();
                        }
                    }
                }
        );

        imageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ProductDetails.this, OcrCaptureActivity.class);
                        intent.putExtra(OcrCaptureActivity.AutoFocus, true);
                        intent.putExtra(OcrCaptureActivity.UseFlash, false);
                        startActivityForResult(intent, RC_OCR_CAPTURE);
                    }
                }
        );

    }
    public Dialog onCreateDialog(int ID)
    {
        if(ID == 1)
            return  new DatePickerDialog(ProductDetails.this,kDatePicker,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
        return null;
    }

    public DatePickerDialog.OnDateSetListener kDatePicker = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            setYear = year;
            String setMonth =""+ (month + 1);
            day = "" +dayOfMonth;
            if(setMonth.length() == 1){
                setMonth = "0"+setMonth;
            }
            if(day.length() == 1){
                day = "0"+day;
            }
            setDate.setText(setYear+"/"+setMonth+"/"+day);
        //   setDate.setText(s);

        }
    };

    public boolean isFieldEmpty(EditText e){
        if(e.getText().toString().compareTo("") == 0){
            Snackbar.make(relativeLayout,"Please Enter Product Name",Snackbar.LENGTH_LONG).show();
            return false;
        }
        else
            return true;
    }

    public void dateCalculation(){
            try {
                exdate = sdf.parse(setDate.getText().toString());
                cal.setTime(exdate);
                switch (notifySpinner.getSelectedItemPosition()){


                    case 0:
                        cal.add(Calendar.MONTH, -1);
                        String s_ = ""+(cal.get(Calendar.MONTH) + 1);
                        String dayS_ = "" +  cal.get(Calendar.DAY_OF_MONTH);
                        if(s_.length() == 1) {
                            s_ = "0" + (cal.get(Calendar.MONTH) + 1);
                        }
                        if(dayS_.length() == 1){
                            dayS_ = "0" + cal.get(Calendar.DAY_OF_MONTH);
                        }

                        afetrWeek = cal.get(Calendar.YEAR)+"/"+s_+"/"+dayS_;
                        d=new Date();
                        weekDate = sdf.parse(afetrWeek);
                        boolean value_ = d.before(weekDate);
                        if(value_ == false )
                            Toast.makeText(ProductDetails.this, "Date For Notification Is Gone", Toast.LENGTH_LONG).show();
                        else {
                            boolean ans = myHelper.insertDemo(uid,pname.getText().toString(),setDate.getText().toString(),typeSpinner.getSelectedItem().toString(),radioButton.getText().toString(),notifySpinner.getSelectedItem().toString(),afetrWeek,"0");
                            if(ans)
                                Snackbar.make(pname,"Product Added",Snackbar.LENGTH_LONG).show();
                            else
                                Snackbar.make(pname,"Error: Please Try Again",Snackbar.LENGTH_LONG).show();
                        }
                        break;

                    case 1:
                        cal.add(Calendar.WEEK_OF_YEAR, -1);
                        String s = ""+(cal.get(Calendar.MONTH) + 1);
                        String dayS = "" +  cal.get(Calendar.DAY_OF_MONTH);
                        if(s.length() == 1) {
                            s = "0" + (cal.get(Calendar.MONTH) + 1);
                        }
                        if(dayS.length() == 1){
                            dayS = "0" + cal.get(Calendar.DAY_OF_MONTH);
                        }
                        afetrWeek = cal.get(Calendar.YEAR)+"/"+s+"/"+dayS;
                        d=new Date();
                        weekDate = sdf.parse(afetrWeek);
                        boolean value = d.before(weekDate);

                        if(value == false )
                            Toast.makeText(ProductDetails.this, "Date For Notification Is Gone", Toast.LENGTH_LONG).show();
                        else {
                            boolean ans = myHelper.insertDemo(uid,pname.getText().toString(),setDate.getText().toString(),typeSpinner.getSelectedItem().toString(),radioButton.getText().toString(),notifySpinner.getSelectedItem().toString(),afetrWeek,"0");
                            if(ans)
                                Snackbar.make(pname,"Product Added",Snackbar.LENGTH_LONG).show();
                            else
                                Snackbar.make(pname,"Error: Please Try Again",Snackbar.LENGTH_LONG).show();
                        }
                        break;

                    case 2:
                        cal.add(Calendar.DAY_OF_MONTH, -1);
                        String s1 = ""+(cal.get(Calendar.MONTH) + 1);
                        String dayC = "" + cal.get(Calendar.DAY_OF_MONTH);
                        if(s1.length() == 1 ){
                            s1 = "0"+ (cal.get(Calendar.MONTH) + 1);
                        }
                        if(dayC.length() == 1){
                            dayC = "0"+ cal.get(Calendar.DAY_OF_MONTH) ;
                        }
                        afetrWeek = cal.get(Calendar.YEAR)+"/"+s1+"/"+dayC;
                        d=new Date();
                        weekDate = sdf.parse(afetrWeek);
                        boolean value1 = d.before(weekDate);

                        if(value1 == false ) {
                            Toast.makeText(ProductDetails.this, "Date For Notification Is Gone", Toast.LENGTH_LONG).show();
                        }else {
                            boolean ans = myHelper.insertDemo(uid,pname.getText().toString(),setDate.getText().toString(),typeSpinner.getSelectedItem().toString(),radioButton.getText().toString(),notifySpinner.getSelectedItem().toString(),afetrWeek,"0");

                            if(ans)
                                Snackbar.make(pname,"Product Added",Snackbar.LENGTH_LONG).show();
                            else
                                Snackbar.make(pname,"Error:Please Try Again",Snackbar.LENGTH_LONG).show();
                        }
                    break;
                    case 3:

                        Date currdate = new Date();
                        try{
                            Date d = sdf.parse(setDate.getText().toString());
                            boolean ans = currdate.before(d);
                            if(ans == false ) {
                                Toast.makeText(ProductDetails.this, "Date For Notification Is Gone", Toast.LENGTH_LONG).show();
                            }else {
                                boolean ins = myHelper.insertDemo(uid,pname.getText().toString(),setDate.getText().toString(),typeSpinner.getSelectedItem().toString(),radioButton.getText().toString(),notifySpinner.getSelectedItem().toString(),setDate.getText().toString(),"0");
                                if(ins)
                                    Snackbar.make(pname,"Product Added",Snackbar.LENGTH_LONG).show();
                                else
                                    Snackbar.make(pname,"Error:Please Try Again",Snackbar.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                    try{
                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd");
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                        Date d =  new Date();
                        d = sdf1.parse(text);
                        if(text.contains("/")) {
                            d = sdf.parse(text);
                        }else if(text.contains("-")){
                            d = sdf2.parse(text);
                        }else if(text.contains(".")){
                             d = sdf1.parse(text);
                        }
                        String date1 = sdf.format(d.getTime());
                        setDate.setText(date1);
                    }catch (Exception e){
                        Toast.makeText(ProductDetails.this,"Detected Text Is Not In Date Format..!",Toast.LENGTH_LONG).show();
                    }

                } else {
//                    statusMessage.setText(R.string.ocr_failure);
//                    Log.d(TAG, "No Text captured, intent data is null");
                }
            } else {
//                statusMessage.setText(String.format(getString(R.string.ocr_error),
//                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
