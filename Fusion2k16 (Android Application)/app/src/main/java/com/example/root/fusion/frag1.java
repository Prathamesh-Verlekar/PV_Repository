package com.example.root.fusion;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

/**
 * Created by root on 7/2/16.
 */
public class frag1 extends android.support.v4.app.ListFragment implements AdapterView.OnItemClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragtest1,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String [] fraglist={"Maiboli","Dance+ (Solo/Group)","Antakshari","Mozart's Disguise","RAP - Ping!","Rock the Ramp","Har Gali main Shor hai","Mr & Miss Fusion","Dramebaaz","Short-cut",""};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,fraglist);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // TODO Auto-generated method stub
        if( position==0)
        {
            Intent i=new Intent(this.getActivity(),Maiboli.class);
            startActivity(i);
        }
        else if(position==1)
        {
            Intent i=new Intent(this.getActivity(),Dance.class);
            startActivity(i);
        }
        else if(position==2)
        {
            Intent i=new Intent(this.getActivity(),Antakshari.class);
            startActivity(i);
        } else if(position==3)
        {
            Intent i=new Intent(this.getActivity(),Mozart.class);
            startActivity(i);
        } else if(position==4)
        {
            Intent i=new Intent(this.getActivity(),Rap.class);
            startActivity(i);
        } else if(position==5)
        {
            Intent i=new Intent(this.getActivity(),Rock.class);
            startActivity(i);
        } else if(position==6)
        {
            Intent i=new Intent(this.getActivity(),Hargali.class);
            startActivity(i);
        } else if(position==7)
        {
            Intent i=new Intent(this.getActivity(),MrMrs.class);
            startActivity(i);
        } else if(position==8)
        {
            Intent i=new Intent(this.getActivity(),Dramebaaz.class);
            startActivity(i);
        } else if(position==9)
        {
            Intent i=new Intent(this.getActivity(),Shortcut.class);
            startActivity(i);
        }
            }
    }

