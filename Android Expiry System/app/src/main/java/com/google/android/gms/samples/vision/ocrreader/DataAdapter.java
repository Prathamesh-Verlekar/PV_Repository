package com.google.android.gms.samples.vision.ocrreader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Dell on 2/13/2017.
 */





public class DataAdapter extends ArrayAdapter {

    String[] name;
    String[] type;
    String[] date;
    Context con;
    String n,t,d;

    TextView dname,dtype,DExDate;

    public DataAdapter(Context context, String[] name, String[] type, String[] date) {
        super(context,R.layout.list_row_layout,name);

        this.name = name;
        this.type = type;
        this.date = date;
        con = context;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(con);
        View v = inflater.inflate(R.layout.list_row_layout,null,true);

        dname = (TextView) v.findViewById(R.id.nameView);
        dtype = (TextView) v.findViewById(R.id.typeProduct);
        DExDate = (TextView) v.findViewById(R.id.expiryDate);

        dname.setText(Html.fromHtml("<b>Name : </b>"+" "+(name[position])));
        dtype.setText(Html.fromHtml("<b>Type : </b>"+" "+(type[position])));
        DExDate.setText(Html.fromHtml("<b>Expiry Date : </b>"+" "+(date[position])));
        return  v;
    }
}
