//Asking user to enter activity data and storing it in the local db
package com.Pan_Mom_Home;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class Enter_Activity_data extends Activity {

	
	Button btndone,btncancel;
	EditText edtdate,edtetime,edtwtime,edtecalories,edtwcalories,edtdistance;
	
	DatabaseHelper data;
	Pan_mom_session mysession;
	int  selected_index,selected_id,flag1;
	
	int mYear,mMonth,mDay;
	String flag;
	static final int DATE_DIALOG_ID = 1;
	static final int TIME_DIALOG_ID = 0;
	private int mHour;
	private int mMinute;

	String[] strid,strdate,stretime,strwtime,strecalories,strwcalories,strdistance,strwt_sync;
	String strulogin,struname;
	int intuserid;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.enter_activity_data);
		
		
		/*initializing classes*/
		data=new DatabaseHelper(this);
		mysession=new Pan_mom_session(this);
		
		/*getting data from session*/
		strulogin=mysession.getUserId();
        System.out.println("SAVED USER ID IS...."+strulogin);
        /*if there are no values in the session then getting data from previous activity*/
        if(strulogin.equals(""))
        {
        	
        	Bundle bun=getIntent().getExtras();
            struname=bun.getString("UNAME");
            intuserid=bun.getInt("USERID");
            strulogin=Integer.toString(intuserid);
            if(mysession.gettracker_flag().toString().equals("viewactivity"))
            {
            selected_index=bun.getInt("Rowid");    
            flag1=bun.getInt("Flagid");
            selected_id=bun.getInt("Selectedid");
            }
            else 
            {
            	selected_id=0;
            	selected_index=0;
            	flag1=0;
            }
        	System.out.println("IN IF..........");
        }
        else
        {
        	System.out.println("IN ELSE..........");
        	Bundle bun=getIntent().getExtras();
        	 if(mysession.gettracker_flag().toString().equals("viewactivity"))
             {
             selected_index=bun.getInt("Rowid");    
             flag1=bun.getInt("Flagid");
             selected_id=bun.getInt("Selectedid");
             }
             else
             {
             	selected_id=0;
             	selected_index=0;
             	flag1=0;
             }
        	System.out.println("IN ELSE ..Selected index ....."+selected_index);
        	System.out.println("IN ELSE ...strlogin......"+strulogin);
        	
        }
        
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
        	
		
		/*inflating the views to use in this class*/
		   btndone=(Button) findViewById(R.id.done); 
		   btncancel=(Button) findViewById(R.id.cancel);        
		   edtdate=(EditText) findViewById(R.id.date);
		   edtetime=(EditText) findViewById(R.id.eduration);
		   edtwtime=(EditText) findViewById(R.id.wduration);
		   edtecalories=(EditText) findViewById(R.id.ecalori);
		   edtwcalories=(EditText) findViewById(R.id.wcalori);
		   edtdistance=(EditText) findViewById(R.id.distance);
   
		   edtdate.setText(
                   new StringBuilder()                  
                   .append(mDay).append("-")
                   .append(mMonth + 1).append("-")                   
                   .append(mYear).append(" "));
		   
		   if(selected_index>0){
				getactivity_log(strulogin);
				btndone.setText("Update");
			}
		   
		   btncancel.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) 
				{
					Intent i=new Intent(Enter_Activity_data.this, MyTrackers.class);
					Bundle bun=new Bundle();
					bun.putString("UNAME",struname);
					bun.putInt("USERID", intuserid);
					i.putExtras(bun);
			    	startActivity(i);
			
				}
			});
		   if(selected_index>0){
				getactivity_log(strulogin,selected_index);
			}
			
			btndone.setOnClickListener(new OnClickListener() {
				
			
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mysession.settracker_flag("Enteractivity");
					if(flag1==1){
			              updatebp_data();
			              btndone.setText("Submit");
					}
					else{
						insertActivities_data();}
					
				}
			});
			edtdate.setOnClickListener(new OnClickListener() {
				
			
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Window window = getWindow();
			        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			        
				    
					showDialog(DATE_DIALOG_ID);
					
					
				}
			});
			edtetime.setOnClickListener(new OnClickListener() {
				
			
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Window window = getWindow();
			        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
				    
			        flag="1";
			        showDialog(TIME_DIALOG_ID);
					
					
				}
			});
			edtwtime.setOnClickListener(new OnClickListener() {
				
			
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Window window = getWindow();
			        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
				    
			        flag="2";
			        showDialog(TIME_DIALOG_ID);
					
					
				}
			});
	}
	
	//Creating Date picker dialog and time picker dialog
	@Override
    protected Dialog onCreateDialog(int id) {
            switch (id) {

            case DATE_DIALOG_ID:
                    return new DatePickerDialog(this,
                            mDateSetListener,
                            mYear, mMonth, mDay);
                    
            case TIME_DIALOG_ID: return new TimePickerDialog(this,
    				mTimeSetListener, mHour, mMinute, false);
            }
            return null;
    }
    protected void onPrepareDialog(int id, Dialog dialog) {
            switch (id) {

            case DATE_DIALOG_ID:
                    ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                    break;
            }
    }  
    /*Type :Function
    name:updateDisplay
    return type:void
    date:12-12-2011
    purpose:setting the user selected date to edittext*/
    private void updateDisplay() {
            edtdate.setText(
                    new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mDay).append("-")
                    .append(mMonth + 1).append("-")                   
                    .append(mYear).append(" "));
    }
    /*Type :Function
    name:updateDisplay1
    return type:void
    date:12-12-2011
    purpose:setting the user selected time to edittext*/
    private void updateDisplay1() {
    	
    	if(mHour>12)
 	   {
		edtetime.setText(new StringBuilder()
		.append(mHour-12).append(":")
		.append(mMinute).append(" PM"));
 	   }
    	else
    	{
    		edtetime.setText(new StringBuilder()
    		.append(mHour).append(":")
    		.append(mMinute).append(" AM"));
    	}
		
	}
    /*Type :Function
    name:updateDisplay2
    return type:void
    date:12-12-2011
    purpose:setting the user selected time to edittext*/
    private void updateDisplay2() {
    	if(mHour>12)
  	   {
 		edtwtime.setText(new StringBuilder()
 		.append(mHour-12).append(":")
 		.append(mMinute).append(" PM"));
  	   }
     	else
     	{
     		edtwtime.setText(new StringBuilder()
     		.append(mHour).append(":")
     		.append(mMinute).append(" AM"));
     	}
		
	}
  

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

         
			public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth)
           	{
				// TODO Auto-generated method stub
				mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                updateDisplay();
			}
    };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
		new TimePickerDialog.OnTimeSetListener() {
  
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			
			if(flag.equals("1"))
			{
			updateDisplay1();
			}
			if(flag.equals("2"))
			{
				updateDisplay2();
			}
		}

		
		
	};
	
	 /*Type :Function
    name:insertActivities_data
    return type:void
    date:12-12-2011
    purpose:inserting data into local db*/
	public void insertActivities_data()
    {
    	 //String strulogin="1"; 
    	 String strdate=edtdate.getText().toString(); 
    	 String stretime=edtetime.getText().toString();
    	 String strwtime=edtwtime.getText().toString();
    	 String strecalori=edtecalories.getText().toString();
    	 String strwcalori=edtwcalories.getText().toString();
    	 String strdistance=edtdistance.getText().toString();
    	 String stractivity_sync="N";
    	 
    	
    	data.InsertActivity(strulogin, strdate, stretime, strecalori, strwtime, strwcalori, strdistance, stractivity_sync);
    	
    	Toast.makeText(this, "Activity DATA INSERTED SUCCESSFULLY!!!!",Toast.LENGTH_SHORT).show();
    	
    	
    	edtdate.setText("");
		edtetime.setText("");
		edtwtime.setText("");
		edtecalories.setText("");
		edtwcalories.setText("");
		edtdistance.setText("");
    	
    	
    	
    }
	/*Type :Function
    name:updatebp_data
    return type:void
    date:12-12-2011
    purpose:updating data to local db*/
	public void updatebp_data()
    {
	    String strdate=edtdate.getText().toString(); 
    	String strtime=edtetime.getText().toString();
    	String strwtime=edtwtime.getText().toString();
    	String strecal=edtecalories.getText().toString();
    	String strwcal=edtwcalories.getText().toString();
    	String strdist=edtdistance.getText().toString();
    	String strfood_sync="N";
   	data.update_activity(Integer.parseInt(strulogin),selected_id, strdate, strtime,strecal, strwtime, strwcal,strdist,strfood_sync);
   	System.out.println("IN funcyiiiiiiii..."+strulogin);
   	System.out.println("IN funcyiiiiiiii..."+selected_id);
   	Toast.makeText(Enter_Activity_data.this,"Data Updated successfully",Toast.LENGTH_SHORT).show();
   	 flag1=0;
   	edtdate.setText("");
   	edtetime.setText("");
   	edtwtime.setText("");
   	edtecalories.setText("");
   	edtwcalories.setText("");
   	edtdistance.setText("");
   	
    }
	/*Type :Function
    name:getactivity_log
    return type:void
    date:12-12-2011
    purpose:fetching data from local db*/
	public void getactivity_log(String user_login_id)
	{
		
		
		
		Cursor cur=data.getActivity_data(Integer.parseInt(strulogin));
		
		
		strid=new String[cur.getCount()];
		strdate=new String[cur.getCount()];
		stretime=new String[cur.getCount()];
		strwtime=new String[cur.getCount()];
		strecalories=new String[cur.getCount()];
		strwcalories=new String[cur.getCount()];
		strdistance=new String[cur.getCount()];
		strwt_sync=new String[cur.getCount()];
		
		int i=0;
    	while(cur.moveToNext())
    	{
    		
    		String strid1=cur.getString(0);
    		//String strulogin1=cur.getString(1); 
    		String strdate1=cur.getString(2);
    		String stretime1=cur.getString(3); 
    		String strecalori1=cur.getString(4);
    		String strwtime1=cur.getString(5);
    		String strwcalori1=cur.getString(6);
    		String strdistance1=cur.getString(7);
    		
    		
    		strid[i]=strid1;
        	strdate[i]=strdate1;
        	stretime[i]=stretime1;
        	strwtime[i]=strwtime1;
        	strecalories[i]=strecalori1;
        	strwcalories[i]=strwcalori1;
        	strdistance[i]=strdistance1;
    		
        	System.out.println("ID IS:"+strid[i]);
        	//System.out.println("ULogin:"+strulogin1);
        	System.out.println("Date:"+strdate[i]);
        	System.out.println("Time:"+stretime[i]);
        	System.out.println("Systolic:"+strwtime[i]);
        	System.out.println("Diastolic:"+strecalories[i]);
        	System.out.println("Pulse Count:"+strwcalories[i]);
        	System.out.println("Hand:"+strdistance[i]);
        	//System.out.println("Flag:"+strbp_sync1);
        	
        	i++;
        	
    		
    	}
    	cur.close();
    	
    	
    	if(mysession.getactivity_tag().equals("Exercise"))
    	{
    		
    		edtdate.setText(strdate[(selected_index-1)]);
    		edtetime.setText(stretime[(selected_index-1)]);
    		edtecalories.setText(strecalories[(selected_index-1)]);
    		
    	}
    	if(mysession.getactivity_tag().equals("Walking"))
    	{
    		edtdate.setText(strdate[(selected_index-1)]);
    		edtwtime.setText(strwtime[(selected_index-1)]);
    		edtwcalories.setText(strwcalories[(selected_index-1)]);
    		edtdistance.setText(strdistance[(selected_index-1)]);
    	}
	}
	
	/*Type :Function
    name:getactivity_log
    return type:void
    date:12-12-2011
    purpose:fetching data from local db*/
	 public void getactivity_log(String user_login_id,int bpid)
		{
			
			System.out.println("in funnnnnnnn getbp : "+user_login_id);
			System.out.println("in funnnnnnnn getbp : "+bpid);
			int aa=Integer.parseInt(strulogin);
			System.out.println("in funnnnnnnn getbp aa : "+aa);
			
			Cursor cur=data.getActivity_data(Integer.parseInt(strulogin));
			
			strdate=new String[cur.getCount()];
			stretime=new String[cur.getCount()];
			strwtime=new String[cur.getCount()];
			strecalories=new String[cur.getCount()];
			strwcalories=new String[cur.getCount()];
			strdistance=new String[cur.getCount()];
			
			
		
			 
			int i=0;

	    	while(cur.moveToNext())
	    	{
	    		
	    		//String strulogin1=cur.getString(1); 
	    		String strdate1=cur.getString(2);
	    		String stretime1=cur.getString(3); 
	    		String strecalories1=cur.getString(4);
	    		String strwtime1=cur.getString(5);	    		
	    		String strwcalories1=cur.getString(6);
	    		String strdistance1=cur.getString(7);
	    		
	    		

	        	strdate[i]=strdate1;
	        	stretime[i]=stretime1;
	        	strwtime[i]=strwtime1;
	        	strecalories[i]=strecalories1;
	        	strwcalories[i]=strwcalories1;
	        	strdistance[i]=strdistance1;
	        
	        	
	        
	        	
	        	System.out.println("bundle valuesssssssssssss:  ");
	        	System.out.println("bDate:"+strdate[i]);
	        	
	      
	        	i++;
	        	
	    		
	    	}
	    	cur.close();
	    	
	    	edtdate.setText(strdate[(selected_index-1)]);
	    	edtetime.setText(stretime[(selected_index-1)]);
	    	edtecalories.setText(strecalories[(selected_index-1)]);
	    	edtwtime.setText(strwtime[(selected_index-1)]);
	    	edtwcalories.setText(strwcalories[(selected_index-1)]);
	    	edtdistance.setText(strdistance[(selected_index-1)]);
	    	
		}
	
	 //overriding devices back button
	@Override
	public void onBackPressed()
	{
		System.out.println("BACK BUTTON PRESSED!!!!");
	}
}
