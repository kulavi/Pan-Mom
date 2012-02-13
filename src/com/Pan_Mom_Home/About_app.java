//Information About app 
package com.Pan_Mom_Home;



import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class About_app extends CustomWindow{
	String tag,sdate;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        
			setContentView(R.layout.about_us_dialog);
			
			TextView slideshowDialog = (TextView) findViewById(R.id.slideshowDialog);
			TextView slideshowDialog1 = (TextView) findViewById(R.id.slideshowDialog1);
			
			 
		     /*purpose:Calculate the current date*/
			Calendar cal = new GregorianCalendar();
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);
			int day = cal.get(Calendar.DAY_OF_MONTH);    
			sdate=(+ day + "-" + (month+1)+ "-" +year);  
			try
			{     
			    String app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
			    System.out.println("text........"+app_ver);
			    slideshowDialog.setText("Pan Mom\nVersion - "+app_ver+"\n Date - "+sdate);
			}
			catch (NameNotFoundException e)
			{
			    Log.v(tag, e.getMessage());
			}

			//shows the dialog of information about panmom app
			slideshowDialog1.setText("\nPanMom provides whole 40 weeks info and progress during pregnancy....\n\nFor More Info Visit \n\nhttp://panmom.com/");
			
			
	        
	 }
	 
	 
	 /*Type :Function
     name:onClickHome
     return type:void
     date:12-12-2011
     purpose:Click function for home button*/
	 public void onClickHome (View v)
		{
		    goHome (this);
		}
	 
	 /*Type :Function
     name:goHome
     return type:void
     date:12-12-2011
     purpose:Displays the dashboard*/
	    public void goHome(Context context) 
		{
		    finish();
		}

}
