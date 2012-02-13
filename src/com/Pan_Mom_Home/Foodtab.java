//Food Tab containing two tabs as Enter Food and Log
package com.Pan_Mom_Home;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Foodtab extends Custom_window_tab {
	
	String struname;
	int intuserid;
	int  selected_index,flag,selected_id;	
	Button btnhome;
	Pan_mom_session mysession;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);
        
        mysession=new Pan_mom_session(this);
        
        
      //Getting data from previous activity
        Bundle bun=getIntent().getExtras();
        struname=bun.getString("UNAME");
        intuserid=bun.getInt("USERID");
        selected_index=bun.getInt("Rowid");
        selected_id=bun.getInt("Selectedid");
        flag=bun.getInt("Flagid");
        
       
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
       
        
        TabSpec firstTabSpec = tabHost.newTabSpec("tid1");
        TabSpec secondTabSpec = tabHost.newTabSpec("tid1");
        
       
      //Setting activities to tabs
        Intent i=new Intent(this,Enter_Food_data.class);
        Bundle bun1=new Bundle();
        bun1.putString("UNAME", struname);
        bun1.putInt("USERID", intuserid);
        bun1.putInt("Rowid",selected_index);
        bun1.putInt("Flagid", flag);
        bun1.putInt("Selectedid", selected_id);
        i.putExtras(bun1);
        firstTabSpec.setIndicator("Food").setContent(i);
        
        
        Intent i1=new Intent(this,View_Food_Log.class);
        Bundle bun2=new Bundle();
        bun2.putString("UNAME", struname);
        bun2.putInt("USERID", intuserid);
        i1.putExtras(bun2);
        secondTabSpec.setIndicator("Food Log").setContent(i1);
     
           
        tabHost.addTab(firstTabSpec);
        tabHost.addTab(secondTabSpec);
            
        
    }
            
  //overriding the devices back button
    @Override
	public void onBackPressed()
	{
		System.out.println("BACK BUTTON PRESSED!!!!");
	}

}