package com.google.android.gms.samples.vision.ocrreader;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import static android.widget.Toast.makeText;


public class DatabaseHelper extends SQLiteOpenHelper {

    Context con;
    public static final String Databse_Name = "Ocr.db";

    //Employee Table
    public static final String Employee  = "Employee";
    public static final String NAME = "NAME";
    public static final String PHONE = "PHONE";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";


    //Product Table
    public static final String ProductDetails   = "ProductDetails";
    public static final String PID = "PID";
    public static final String PNAME = "PNAME";
    public static final String  PDATE = "PDATE";
    public static final String  PTYPE = "PTYPE";
    public static final String PEMAIL = "PEMAIL";
    public static final String NOTIFY = "NOTIFY";
    public static final String NOTIFY_DATE = "NOTIFY_DATE";
    public static final String FLAG = "FLAG";


    //Time Table
    public static final String TimeTable   = "TimeTable";
    public static final String NTIME = "NTIME";
    public static final String EEMAIL = "EEMAIL";


    public DatabaseHelper(Context context) {
        super(context, Databse_Name, null, 1);
        con = context;
        SQLiteDatabase db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE EMPLOYEE(NAME TEXT,PHONE INTEGER,EMAIL TEXT PRIMARY KEY,PASSWORD TEXT)");
        db.execSQL("CREATE TABLE ProductDetails(PID INTEGER PRIMARY KEY,EEMAIL TEXT,PNAME TEXT,PDATE TEXT,PTYPE TEXT,PEMAIL TEXT,NOTIFY TEXT,NOTIFY_DATE TEXT,FLAG TEXT)");
        db.execSQL("CREATE TABLE TimeTable(NTIME TEXT,EEMAIL TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+Employee);
        db.execSQL("DROP TABLE IF EXISTS"+ProductDetails);
        db.execSQL("DROP TABLE IF EXISTS"+TimeTable);
        onCreate(db);
    }

    public boolean insertEmp(String name,long phone,String email,String password){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues c = new ContentValues();
        c.put(NAME,name);
        c.put(PHONE,phone);
        c.put(EMAIL,email);
        c.put(PASSWORD,password);
        long result = db.insert(Employee,null,c);

        if(result == -1)
            return false;
        else {
            return true;
        }
    }

    public boolean checkEmail(String email) {

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor ans = db.rawQuery("select * from Employee where EMAIL = "+email,null);

        Cursor ans =db.query(Employee,new String[]{PASSWORD},EMAIL + " = '"+email+"'",null,null,null,null);

        if(ans.getCount() == 0) {
            return true;
        }
        else
            return  false;

    }
    public boolean fetchLoginDetails(String uname,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor ans =db.query(Employee,new String[]{},EMAIL + " = '"+uname+"'" +  " and " + PASSWORD + " = '"+password+"'"  ,null,null,null,null);

        if(ans.getCount() == 0)
            return false;
        else
            return true;

    }

    public Cursor showItemDetails(String item,String mail){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor ans = db.query(ProductDetails,new String[]{}, PTYPE + " = '"+item+"' and EEMAIL = '"+mail+"'" ,null,null,null,null);

        return ans;
    }

    public void showMessage(String title,String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
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

    public boolean setTime(String ntime,String mail){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(NTIME,ntime);
        long result = db.update(TimeTable,c,EEMAIL+" = '"+mail+"'",null);

        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean setForFirstTime(String mail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(NTIME,"10:00");
        c.put(EEMAIL,mail);
        long result = db.insert(TimeTable,null,c);

        if(result == -1)
            return false;
        else
            return true;
    }

    public String showTime(String mail){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select * From TimeTable where EEMAIL = '"+mail+"'",null);
        if(c.getCount() >0 ) {
            c.moveToFirst();
            return c.getString(0);
        }else
            return null;

    }

    public boolean getTime(String mail){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select * From "+TimeTable+" where EEMAIL = '"+mail+"'",null);
        if(c.getCount() >0 )
            return true;
        else
            return false;
    }

    public Cursor getDateNotification(String date,String mail){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor ans = db.query(ProductDetails,new String[]{PTYPE,PID,PEMAIL,NOTIFY,PNAME}, NOTIFY_DATE + " = '"+date+"' and EEMAIL = '"+mail+"'" ,null,null,null,null);

        return ans;
    }

    public boolean getTimeNotification(String time,String mail){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor ans = db.rawQuery("SELECT * From TimeTable where EEMAIL = '"+mail+"' AND NTIME = '"+time+"'",null);
        if(ans.getCount() == 0)
            return false;
        else
            return true;
    }

    public boolean insertDemo(String mail,String name,String date,String type,String email,String notify,String notifyDate,String flag){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(EEMAIL,mail);
        c.put(PNAME,name);
        c.put(PDATE,date);
        c.put(PTYPE,type);
        c.put(PEMAIL,email);
        c.put(NOTIFY,notify);
        c.put(NOTIFY_DATE,notifyDate);
        c.put(FLAG,flag);

        long result = db.insert(ProductDetails,null,c);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor showDetails(String mail) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor cs = db.rawQuery("select * from "+ProductDetails, null);
        Cursor cs = db.rawQuery("SELECT " + " * "
                + "  FROM " + ProductDetails + " where EEMAIL = '"+mail+"' ORDER BY PDATE", null);
        return cs;
    }

    public Cursor showTimeDetails(String mail) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cs = db.rawQuery("select NTIME from "+TimeTable+" where EEMAIL = '"+mail+"'", null);

        //Cursor cs = db.query(ProductDetails, rank, null, null, null, null, NOTIFY+" DESC");
        return cs;
    }


    public boolean deleteDetails(int id,String mail) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(ProductDetails,PID + " =  '"+id+"' AND EEMAIL = '"+mail+"'",null);
        return  true;
    }

    public Cursor showProductDetails(int id,String mail){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cs = db.rawQuery("select * from "+ProductDetails+" where EEMAIL = '"+mail+"' and PID = '"+id+"'", null);
        return cs;
    }

    public boolean updateDate(String ndate,String pdate,int id,String mail)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues c = new ContentValues();
        c.put(NOTIFY_DATE,ndate);
        c.put(PDATE,pdate);

        db.update(ProductDetails,c,PID +" = '"+id+"' AND EEMAIL = '"+mail+"'",null);
        return  true;
    }
}
