package com.example.root.fusion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class frag extends android.support.v4.app.ListFragment implements AdapterView.OnItemClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragtest,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String [] fraglist={"National Level T.P.P","Webby Award","Code-Ex","Dream Job","Text-It","Treasure Hunt","Techno-Dart","Ad-Mania","Circuitrix","Mock Parliament",""};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,fraglist);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if( position==0)
        {
            Intent i=new Intent(this.getActivity(),Tpp.class);
            startActivity(i);
        }
        else if(position==1)
        {
            Intent i=new Intent(this.getActivity(),webby.class);
            startActivity(i);
        }
        else if(position==2)
        {
            Intent i=new Intent(this.getActivity(),Codex.class);
            startActivity(i);
        } else if(position==3)
        {
            Intent i=new Intent(this.getActivity(),Dreamjob.class);
            startActivity(i);
        } else if(position==4)
        {
            Intent i=new Intent(this.getActivity(),Textit.class);
            startActivity(i);
        } else if(position==5)
        {
            Intent i=new Intent(this.getActivity(),Treasure.class);
            startActivity(i);
        } else if(position==6)
        {
            Intent i=new Intent(this.getActivity(),Techno.class);
            startActivity(i);
        } else if(position==7)
        {
            Intent i=new Intent(this.getActivity(),Ad_mania.class);
            startActivity(i);
        } else if(position==8)
        {
            Intent i=new Intent(this.getActivity(),Circuitrix.class);
            startActivity(i);
        } else if(position==9)
        {
            Intent i=new Intent(this.getActivity(),MockP.class);
            startActivity(i);
        }
    }
}
