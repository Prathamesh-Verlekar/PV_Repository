package com.google.android.gms.samples.vision.ocrreader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    DatabaseHelper myHelper;
    Button loginButton,signInButton;
    EditText loginUserName,loginPassword;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("OCRDEMO", Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("uid","");

        if(uid.compareTo("")!=0)
        {
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            setContentView(R.layout.activity_login2);

            myHelper = new DatabaseHelper(this);
            loginUserName = (EditText) findViewById(R.id.loginUserName);
            loginPassword = (EditText) findViewById(R.id.loginPassword);

            loginButton = (Button) findViewById(R.id.loginButton);
            signInButton = (Button) findViewById(R.id.signIn);
            demo();
        }

    }

    public void isEditNull(EditText a,EditText b){
        if(a.getText().toString().compareTo("") == 0 ||
                b.getText().toString().compareTo("") == 0) {
            Snackbar.make(a, "Please Enter UserName And Password", Snackbar.LENGTH_LONG).show();
        }
        else {
            boolean idExist = myHelper.fetchLoginDetails(loginUserName.getText().toString(),loginPassword.getText().toString());
                if(idExist){

                    editor = sharedPreferences.edit();
                    editor.putString("uid",loginUserName.getText().toString());
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Snackbar.make(a, "Username Or Password Does Not Match", Snackbar.LENGTH_LONG).show();
                    loginUserName.setText("");
                    loginPassword.setText("");
                }
            }
        }


    public void demo(){

        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isEditNull(loginUserName,loginPassword);
                    }
                }
        );


        signInButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
