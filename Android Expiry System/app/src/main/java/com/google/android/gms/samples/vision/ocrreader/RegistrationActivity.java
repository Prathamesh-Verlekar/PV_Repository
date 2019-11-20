package com.google.android.gms.samples.vision.ocrreader;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    EditText name,phoneNumber,emailID,password,confirmPassword;
    Button register,clearButton;

    DatabaseHelper myHelper;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        relativeLayout= (RelativeLayout) findViewById(R.id.activity_registration) ;
        myHelper = new DatabaseHelper(this);
        name = (EditText) findViewById(R.id.userName);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        emailID = (EditText) findViewById(R.id.emailID);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        register = (Button) findViewById(R.id.registerButton);
        clearButton = (Button) findViewById(R.id.clearButton);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        helperCalls();
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

    public void helperCalls(){

        register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isEditNull(name, phoneNumber, emailID, password, confirmPassword);
                    }
                }
        );

        clearButton.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        clear();
                    }
                }
        );
    }

    public void phoneValidation(EditText e) {
        if (phoneNumber.getText().toString().length() != 10) {
            Snackbar.make(relativeLayout, "Invalid Number,Must Be 10 Digits", Snackbar.LENGTH_LONG).show();
        }
        else {
            passWordMatch(password,confirmPassword);
        }
    }

    public void emailValidation(EditText e){

        String match = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(!emailID.getText().toString().matches(match)) {
            Snackbar.make(relativeLayout, "Please Fallow Email Standards", Snackbar.LENGTH_LONG).show();
            e.setText("");

        }
        else {
            boolean idExist = myHelper.checkEmail(emailID.getText().toString());
            if(idExist){
                boolean insertAns = myHelper.insertEmp(name.getText().toString(),
                        Long.parseLong(phoneNumber.getText().toString()),
                        emailID.getText().toString(),
                        password.getText().toString());
                if(insertAns) {
                    Snackbar.make(relativeLayout, "Registration Completed", Snackbar.LENGTH_LONG).show();
                    finish();
                    clear();
                }else
                    Snackbar.make(relativeLayout, "Error:Please Try Again", Snackbar.LENGTH_LONG).show();
            }else {
                Snackbar.make(relativeLayout, "Email ID Already Exists", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    public void passWordMatch(EditText a,EditText b){
        if( !a.getText().toString().equals(b.getText().toString())) {
            Snackbar.make(relativeLayout, "Password Does Not Match", Snackbar.LENGTH_LONG).show();
            a.setText("");
            b.setText("");
        }
        else{
            emailValidation(emailID);
        }
    }

    public void isEditNull(EditText na,EditText ph,EditText emal,EditText p,EditText cp){
        if(na.getText().toString().compareTo("") == 0 ||
                ph.getText().toString().compareTo("") == 0 ||
                emal.getText().toString().compareTo("") == 0 ||
                p.getText().toString().compareTo("") == 0 ||
                cp.getText().toString().compareTo("") == 0)
        {
            Snackbar.make(relativeLayout, "Field's Vaccant", Snackbar.LENGTH_LONG).show();
        }
        else {
            phoneValidation(ph);
        }
    }
    public  void clear(){
        phoneNumber.setText("");
        emailID.setText("");
        password.setText("");
        confirmPassword.setText("");
        name.setText("");
        name.requestFocus();
    }
    public void showMessage(String title,String Message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(Message);
        builder.setTitle(title);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        builder.show();
    }

}
