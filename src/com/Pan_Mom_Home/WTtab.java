//Weight Tab containing two tabs as Enter Weight and Log
package com.Pan_Mom_Home;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class WTtab extends Custom_window_tab implements OnTabChangeListener{
	
	String struname;
	int intuserid,flag,selected_id;	;
	int  selected_index;	
	Button btnhome;
	TabHost tabHost ;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);
        
        
      //Getting data from previous activity
        Bundle bun=getIntent().getExtras();
        struname=bun.getString("UNAME");
        intuserid=bun.getInt("USERID");
        selected_index=bun.getInt("Rowid");
        selected_id=bun.getInt("Selectedid");
        flag=bun.getInt("Flagid");
        
        tabHost = (TabHost)findViewById(android.R.id.tabhost);

       
        TabSpec firstTabSpec = tabHost.newTabSpec("tid1");
        TabSpec secondTabSpec = tabHost.newTabSpec("tid1");
        
      //Setting activities to tabs
        Intent i=new Intent(this,Enter_WT_data.class);
        Bundle bun1=new Bundle();
        bun1.putString("UNAME", struname);
        bun1.putInt("USERID", intuserid);
        bun1.putInt("Rowid",selected_index);
        bun1.putInt("Flagid", flag);
        bun1.putInt("Selectedid", selected_id); 
        i.putExtras(bun1);
        firstTabSpec.setIndicator("Weight").setContent(i);
        
        
        Intent i1=new Intent(this,View_WT_Log.class);
        Bundle bun2=new Bundle();
        bun2.putString("UNAME", struname);
        bun2.putInt("USERID", intuserid);
        i1.putExtras(bun2);
        secondTabSpec.setIndicator("Weight Log").setContent(i1);
        
        
        
        
        /* Add tabSpec to the TabHost to display. */
        tabHost.addTab(firstTabSpec);
        tabHost.addTab(secondTabSpec);
        
        for(int i5=0;i5<tabHost.getTabWidget().getChildCount();i5++)
        {
        	tabHost.getTabWidget().getChildAt(i5).setBackgroundColor(Color.parseColor("#7392B5"));
        }
        
        tabHost.getTabWidget().setCurrentTab(1);
        tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#ffffff"));
       
        
       
        
    }
    
   
    public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		
		for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
        	tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#7392B5"));
        	
        } 
				
		tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#4E4E9C"));
		
	}
    
    
  //overriding the devices back button
    @Override
	public void onBackPressed()
	{
		System.out.println("BACK BUTTON PRESSED!!!!");
	}
}

