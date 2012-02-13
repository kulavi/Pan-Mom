//Fetching week info  from local db and showing it to the user
package com.Pan_Mom_Home;



import java.util.Calendar;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageButton;
import android.widget.TextView;

public class Week extends Activity {
	
	TextView text,text1,text2;
	ImageButton rig,lef;
	
	DatabaseHelper data;
	Pan_mom_session mysession;
	

	int mYear,mMonth,mDay;
	int date,month,year,allday=0,week,reminday;
	int arr[]={31,28,31,30,31,30,31,31,30,31,30,31};
	int[] intweek_id;
	String[] strweek_info_lady,strweek_info_baby;
	int i,intuserid;
	String struname,struserid,strpwd,strduedate,str_lmp,str_duedate;
	 
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.weeks40);
	   
	        rig=(ImageButton) findViewById(R.id.imageButton1);
	        lef=(ImageButton) findViewById(R.id.imageButton2);
	        text = (TextView) findViewById(R.id.text);
	        
	        
	        data=new DatabaseHelper(this);
	        mysession=new Pan_mom_session(this);
	        
	        getweek_info();
	        struname=mysession.getUserName();
	        struserid=mysession.getUserId();
	        strpwd=mysession.getPwd();
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
	        
	        
	        	get_lmp_and_due_date_from_db();
		
	        	final Calendar c = Calendar.getInstance();
			    mYear = c.get(Calendar.YEAR);
	            mMonth = c.get(Calendar.MONTH);
		        mDay = c.get(Calendar.DAY_OF_MONTH);
		       
		        String current_date=mDay+"-"+(mMonth+1)+"-"+mYear;
		        String[] dts_current=current_date.split("-");
		        
		        int mnth2=Integer.parseInt(dts_current[0].trim());
				  int day2=Integer.parseInt(dts_current[1].trim());
				  int year2=Integer.parseInt(dts_current[2].trim());
				 
				  Calendar thatday_current = Calendar.getInstance();
		          thatday_current.set(year2,day2,mnth2);
		        
		        Calendar thatDay_lmp = Calendar.getInstance();
				 thatDay_lmp.set(year, month, date);
		        
				 long diff_lmp_and_current_date=thatday_current.getTimeInMillis()-thatDay_lmp.getTimeInMillis();
				  long days = diff_lmp_and_current_date / (24 * 60 * 60 * 1000);
				  int ss=(int)days;
				  week=ss/7;
				  reminday=ss%7;
				  if(reminday!=0){week=week+1;}
				  
				  System.out.println("weeeeeeeeeeeeeeeeeekkkkkkkkkkkkkkkkkk  is :"+week);
		      
	        i=week;
	        i=i-1;
	        
	        text.setText("WEEK: "+(intweek_id[i]));
	        
	        text1 = (TextView) findViewById(R.id.text1);
	        text2 = (TextView) findViewById(R.id.text2);
	        text1.setText(strweek_info_lady[i]);
	        text2.setText(strweek_info_baby[i]);
	        rig.setOnClickListener(new OnClickListener() {
	    		
	          	 
	        	public void onClick(View v) {
	    		    if(i==0){i=39;}  
	    		    else{
	    			i--;} 
	    			text = (TextView) findViewById(R.id.text);
	    	        text.setText("WEEK: "+intweek_id[i]);
	    	        text1.setText(strweek_info_lady[i]);
	    	        text2.setText(strweek_info_baby[i]);
	    			
	    		}
	    	});
	        lef.setOnClickListener(new OnClickListener() {
	    		
	        	public void onClick(View v) {
	      		  if(i==39){i=0;}
	      		  else{
	      			i++;}
	      			 text = (TextView) findViewById(R.id.text);
	      		        text.setText("WEEK: "+intweek_id[i]);
	      		        text1.setText(strweek_info_lady[i]);
		    	        text2.setText(strweek_info_baby[i]);
	      		}
	    	});
	        
	        
	    }
	 /*Type :Function
	    name:getweek_info
	    return type:void
	    date:12-12-2011
	    purpose:fetching week data from local db*/
	 public void getweek_info()
		{
			Cursor cur=data.getWeek_Info_data();
			
			intweek_id=new int[cur.getCount()];
			strweek_info_lady=new String[cur.getCount()];
			strweek_info_baby=new String[cur.getCount()];
						
			 
			int i=0;

	    	while(cur.moveToNext())
	    	{
	    		
	    	
	    		int intweekid1=cur.getInt(0); 
	    		String strweek_info_lady1=cur.getString(1); 
	    		String strweek_info_baby1=cur.getString(2);
	    		
	    	
	    
	        	
	        	
	    		
	    		intweek_id[i]=intweekid1;
	    		strweek_info_lady[i]=strweek_info_lady1;        	
	    		strweek_info_baby[i]=strweek_info_baby1;
	        	
	        	
	        	
	        	
	        	
	        	System.out.println("WEEK ID:"+intweek_id[i]);
	        	System.out.println("1....:"+strweek_info_lady[i]);
	        	System.out.println("2....:"+strweek_info_baby[i]);
	        	
	        	
	        	i++;
	        	
	    		
	    	}
	    	cur.close();
	    	
		}
	 /*Type :Function
	    name:get_lmp_and_due_date_from_db
	    return type:void
	    date:12-12-2011
	    purpose:fetching lmp and due date from local db*/
	 public void get_lmp_and_due_date_from_db()
	    {
	    	Cursor cur1=data.getreg_info(intuserid);
	    	
	    	while(cur1.moveToNext())
	    	{
	    		 str_lmp=cur1.getString(6);
	    		 str_duedate=cur1.getString(7);
	    		
	    	}
	    	
	    	cur1.close();
	    	String[] arr_lmp=str_lmp.split("-");
	    	
	    	date=Integer.parseInt(arr_lmp[0].trim());
	    	month=Integer.parseInt(arr_lmp[1].trim());
	    	year=Integer.parseInt(arr_lmp[2].trim());
	    	
	    }
	//overriding devices back button
	 @Override
		public void onBackPressed()
		{
			System.out.println("BACK BUTTON PRESSED!!!!");
		}

}
