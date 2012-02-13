//Asking user to enter HB data and storing it in the local db
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class Enter_HB_data extends Activity {

	Button btnhome,btndone,btncancel;
	EditText edtdate,edttime,edthemoglobin;
	
	DatabaseHelper data;
	Pan_mom_session mysession;
	int  selected_index,flag,selected_id;
	int mYear,mMonth,mDay;
	static final int DATE_DIALOG_ID = 1;
	static final int TIME_DIALOG_ID = 0;
	private int mHour;
	private int mMinute;
	String strulogin,struname;
	String [] strdate,strtime,strhemo,strhb_sync,strhid;
	int intuserid; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.enter_hb_data);
		
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
            if(mysession.gettracker_flag().toString().equals("viewhb"))
            {
            selected_index=bun.getInt("Rowid");    
            flag=bun.getInt("Flagid");
            selected_id=bun.getInt("Selectedid");
            }
            else 
            {
            	selected_id=0;
            	selected_index=0;
            	flag=0;
            }       
        	System.out.println("IN IF..........");
        	System.out.println("IN IF ..Selected index ....."+selected_index);
        	System.out.println("IN IF ...strlogin......"+strulogin);
        }
        else
        {
        	System.out.println("IN ELSE..........");
        	Bundle bun=getIntent().getExtras();
        	if(mysession.gettracker_flag().toString().equals("viewhb"))
            {
            selected_index=bun.getInt("Rowid");    
            flag=bun.getInt("Flagid");
            selected_id=bun.getInt("Selectedid");
            }
            else 
            {
            	selected_id=0;
            	selected_index=0;
            	flag=0;
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
		   edttime=(EditText) findViewById(R.id.time);
		   edthemoglobin=(EditText) findViewById(R.id.hemoglobin);
		   
		   edtdate.setText(
                   new StringBuilder()
                  
                   .append(mDay).append("-")
                   .append(mMonth + 1).append("-")                  
                   .append(mYear).append(" "));
		   
		   btncancel.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) 
				{
					Intent i=new Intent(Enter_HB_data.this, MyTrackers.class);
					Bundle bun=new Bundle();
					bun.putString("UNAME",struname);
					bun.putInt("USERID", intuserid);
					i.putExtras(bun);
			    	startActivity(i);
			
				}
			});
		   
		   
			  if(selected_index>0){
					gethb_log(strulogin,selected_index);
					 btndone.setText("Update");
				}
		   btndone.setOnClickListener(new OnClickListener() {
				
			
				public void onClick(View v) {
					mysession.settracker_flag("Enterhb");
					
					if(flag==1){
			              updateHB_data();
			              btndone.setText("Submit");
					}
					else{
						insertHB_data();}
					
					
					
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
		   edttime.setOnClickListener(new OnClickListener() {
				
		
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Window window = getWindow();
			        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
				    
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
		edttime.setText(new StringBuilder()
		.append(mHour-12).append(":")
		.append(mMinute).append(" PM"));
	   }
	   else
	   {
		   edttime.setText(new StringBuilder()
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
			updateDisplay1();
		}

		
		
	};
	/*Type :Function
    name:insertHB_data
    return type:void
    date:12-12-2011
    purpose:inserting data into local db*/
	public void insertHB_data()
    {
    	 // strulogin="1";
    	 String strhemoglobin=edthemoglobin.getText().toString();
    	 String strdate=edtdate.getText().toString(); 
    	 String strtime=edttime.getText().toString();
    	 String strhb_sync="N";
    	 
    	
    	data.InsertHB(strulogin, strhemoglobin, strdate, strtime, strhb_sync);
    	
    	Toast.makeText(this, "HB DATA INSERTED SUCCESSFULLY!!!!",Toast.LENGTH_SHORT).show();
    	
    	edtdate.setText("");
    	edttime.setText("");
    	edthemoglobin.setText("");
      	
    }
	/*Type :Function
    name:updateHB_data
    return type:void
    date:12-12-2011
    purpose:updating data to local db*/
	public void updateHB_data()
    {
		String strdate=edtdate.getText().toString(); 
    	String strtime=edttime.getText().toString();
    	String strHB=edthemoglobin.getText().toString();
    	String strHB_sync="N";
    	data.update_HB(Integer.parseInt(strulogin),selected_id, strdate, strtime,strHB,strHB_sync);
    	System.out.println("IN funcyiiiiiiii..."+strulogin);
    	System.out.println("IN funcyiiiiiiii..."+selected_id);
    	Toast.makeText(Enter_HB_data.this,"Data Updated successfully",Toast.LENGTH_SHORT).show();
    	flag=0;
    	edtdate.setText("");
    	edttime.setText("");
    	edthemoglobin.setText("");
    	
    }
	/*Type :Function
    name:gethb_log
    return type:void
    date:12-12-2011
    purpose:fetching data from local db*/
	 public void gethb_log(String user_login_id,int bpid)
		{
			
			System.out.println("in funnnnnnnn getbp : "+user_login_id);
			System.out.println("in funnnnnnnn getbp : "+bpid);
			int aa=Integer.parseInt(strulogin);
			System.out.println("in funnnnnnnn getbp aa : "+aa);
			
			Cursor cur=data.getHB_data(Integer.parseInt(strulogin));
	
			strdate=new String[cur.getCount()];
			strtime=new String[cur.getCount()];
			strhemo=new String[cur.getCount()];
			
			
		
			 
			int i=0;

	    	while(cur.moveToNext())
	    	{
	    		
	    		//String strulogin1=cur.getString(1); 
	    		String strhemo1=cur.getString(2);
	    		String strdate1=cur.getString(3);
	    		String strtime1=cur.getString(4); 
	    		
	    		
	    		

	        	strdate[i]=strdate1;
	        	strtime[i]=strtime1;
	        	strhemo[i]=strhemo1;
	      
	      
	        	i++;
	        	
	    		
	    	}
	    	cur.close();
	    	
	    	edtdate.setText(strdate[(selected_index-1)]);
	    	edttime.setText(strtime[(selected_index-1)]);
	    	edthemoglobin.setText(strhemo[(selected_index-1)]);
	    	
	    	
		}
	 //overriding devices back button
	@Override
	public void onBackPressed()
	{
		System.out.println("BACK BUTTON PRESSED!!!!");
	}
}
