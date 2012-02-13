//Dashboard screen 
package com.Pan_Mom_Home;

//import tracker.weight.wt.R;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class Dashboard extends Custom_window_tab {
	
	String struname,strpwd,struserid;
	int intuserid;
	
	Pan_mom_session mysession;
	DatabaseHelper data;
	
	Button btnhome;
	TabHost tabHost;
	
    /** Called when the activity is first created. */  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);
        
        /*initializing classes*/
        data=new DatabaseHelper(this);
		mysession=new Pan_mom_session(this);
				
		/*getting data from session*/
        struname=mysession.getUserName();
        struserid=mysession.getUserId();
        strpwd=mysession.getPwd();
        /*if there are no values in the session then getting data from previous activity*/
        if(struserid.equals(""))
        {
        	System.out.println("SAVED USER ID IS IN IF...."+struserid);
        	if(struname.equals(""))
	        {
        		Bundle bun=getIntent().getExtras();
                struname=bun.getString("UNAME");
                strpwd=bun.getString("PWD");
                intuserid=bun.getInt("USERID");
	        }
        }
        else
        {
        intuserid=Integer.parseInt(struserid);
        System.out.println("SAVED USER ID IS IN ELSE...."+intuserid);
        }  
      
        setTabs();
   
    }
    /*Type :Function
    name:setTabs
    return type:void
    date:12-12-2011
    purpose:Setting tabs by calling addTab function*/
    private void setTabs() {
		tabHost = getTabHost();
		
		addTab("DashBoard", R.drawable.dashboardtab);
		addTab("40 Weeks", R.drawable.calendartab);
		addTab("Photos", R.drawable.cameratab);
	}
    /*Type :Function
    name:addTab
    return type:void
    date:12-12-2011
    purpose:Setting tabs with intents*/
	private void addTab(String labelId, int drawableId) {
		Intent i = null;
		if(labelId.equals("DashBoard"))
		{
		 i=new Intent(this,Pregnancy.class);
        Bundle bun1=new Bundle();
        bun1.putString("UNAME", struname);
        System.out.println("DASHBOARD:UNAME:"+struname);
        bun1.putString("PWD", strpwd);
        bun1.putInt("USERID", intuserid);  
        i.putExtras(bun1);
		}
		if(labelId.equals("40 Weeks"))
		{
			 i=new Intent(this,Week.class);
	        Bundle bun2= new Bundle();
	        bun2.putString("UNAME", struname);
	        bun2.putString("PWD", strpwd);
	        bun2.putInt("USERID", intuserid);
	        i.putExtras(bun2);
		}
		if(labelId.equals("Photos"))
		{
			 i=new Intent(this,Photo.class);
	        Bundle bun3= new Bundle();
	        bun3.putString("UNAME", struname);
	        bun3.putInt("USERID", intuserid);
	        i.putExtras(bun3);
		}
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);		  
		
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);
		
		spec.setIndicator(tabIndicator);
		spec.setContent(i);
		tabHost.addTab(spec);
		
	}
	
	//overriding devices back button
    @Override
	public void onBackPressed()
	{
		System.out.println("BACK BUTTON PRESSED!!!!");
	}
    
    
}
