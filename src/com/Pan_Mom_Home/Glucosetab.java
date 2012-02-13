package com.Pan_Mom_Home;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Glucosetab extends Custom_window_tab {
	
	String struname;
	int intuserid;
	int  selected_index,flag,selected_id;	
	Button btnhome;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);
        
        Bundle bun=getIntent().getExtras();
        struname=bun.getString("UNAME");
        intuserid=bun.getInt("USERID");
        selected_index=bun.getInt("Rowid");
        selected_id=bun.getInt("Selectedid");
        flag=bun.getInt("Flagid");
        
        /*btnhome=(Button) findViewById(R.id.icon);
        this.title.setText("Glucose Tracker");
        this.icon.setBackgroundResource(R.drawable.home);*/
       
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
       
        TabSpec firstTabSpec = tabHost.newTabSpec("tid1");
        TabSpec secondTabSpec = tabHost.newTabSpec("tid1");
        
        Intent i=new Intent(this,Enter_Glucose_data.class);
        Bundle bun1=new Bundle();
        bun1.putString("UNAME", struname);
        bun1.putInt("USERID", intuserid);
        bun1.putInt("Rowid",selected_index);
        bun1.putInt("Flagid", flag);
        bun1.putInt("Selectedid", selected_id);
        i.putExtras(bun1);
        firstTabSpec.setIndicator("Glucose").setContent(i);
        
        
        Intent i1=new Intent(this,View_Glucose_Log.class);
        Bundle bun2=new Bundle();
        bun2.putString("UNAME", struname);
        bun2.putInt("USERID", intuserid);
        i1.putExtras(bun2);
        secondTabSpec.setIndicator("Glucose Log").setContent(i1);
     
        /*firstTabSpec.setIndicator("Glucose").setContent(new Intent(this,Enter_Glucose_data.class));
        secondTabSpec.setIndicator("Glucose Log").setContent(new Intent(this,View_Glucose_Log.class));*/
     
        tabHost.addTab(firstTabSpec);
        tabHost.addTab(secondTabSpec);
        
       /* btnhome.setOnClickListener(new OnClickListener() {
    		
    	
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			
    			home();
    			
    			
    		}
    	});*/
        
        
    }
    
    /*public void home()
    {
    	Intent i=new Intent(Glucosetab.this, Dashboard.class);
    	Bundle bun=new Bundle();
		bun.putString("UNAME",struname);
		//bun.putString("PWD",edtpwd.getText().toString());
		bun.putInt("USERID", intuserid);
		i.putExtras(bun);
    	startActivity(i);
    }*/
    @Override
	public void onBackPressed()
	{
		System.out.println("BACK BUTTON PRESSED!!!!");
	}
}

