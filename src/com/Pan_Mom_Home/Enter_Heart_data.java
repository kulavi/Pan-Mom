//Asking user to enter Heart data and storing it in the local db
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

public class Enter_Heart_data extends Activity {
     
	Button btndone,btncancel;
	EditText edtdate,edttime1,edttime2,edttime3,edtnor_hrt_rate,edtres_hrt_rate,edtexr_hrt_rate;
	
	DatabaseHelper data;
	Pan_mom_session mysession;
	int  selected_index;
	int mYear,mMonth,mDay;
	static final int DATE_DIALOG_ID = 1;
	static final int TIME_DIALOG_ID = 0;
	String time_flag;
	
	private int mHour;
	private int mMinute;
	int intuserid,flag,selected_id;	
	String struname,strulogin;
	String[] strdate,strtime1,strtime2,strtime3,strnor_hrt_rate,strres_hrt_rate,strexr_hrt_rate,strheart_sync;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.enter_heart_data);
		
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
            if(mysession.gettracker_flag().toString().equals("viewheart"))
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
            strulogin=Integer.toString(intuserid);
        	System.out.println("IN IF..........");
        	System.out.println("IN IF ..Selected index ....."+selected_index);
        	System.out.println("IN IF ...strlogin......"+strulogin);
        }
        else
        {
        	System.out.println("IN ELSE..........");
        	Bundle bun=getIntent().getExtras();
        	if(mysession.gettracker_flag().toString().equals("viewheart"))
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
		   edttime1=(EditText) findViewById(R.id.time1);
		   edttime2=(EditText) findViewById(R.id.time2);
		   edttime3=(EditText) findViewById(R.id.time3);
		   edtres_hrt_rate=(EditText) findViewById(R.id.res_hrt_rate);
		   edtnor_hrt_rate=(EditText) findViewById(R.id.nor_hrt_rate);
		   edtexr_hrt_rate=(EditText) findViewById(R.id.exr_hrt_rate);
		   
		   edtdate.setText(
                   new StringBuilder()
                   .append(mDay).append("-")
                   .append(mMonth + 1).append("-")                   
                   .append(mYear).append(" "));
		   
		   btncancel.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) 
				{
					Intent i=new Intent(Enter_Heart_data.this, MyTrackers.class);
					Bundle bun=new Bundle();
					bun.putString("UNAME",struname);
					bun.putInt("USERID", intuserid);
					i.putExtras(bun);
			    	startActivity(i);
			
				}
			});
		   if(selected_index>0){
				getheart_log(strulogin,selected_index);
				 btndone.setText("Update");
			}
		   
		   btndone.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					mysession.settracker_flag("Enterheart");
					if(flag==1){
			              updateheart_data();
			              btndone.setText("Submit");
					}
					else{
						insertheart_data();}
					
					
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
		   edttime1.setOnClickListener(new OnClickListener() {
				
			
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Window window = getWindow();
			        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
				    time_flag="1";
			        showDialog(TIME_DIALOG_ID);
			        
					
					
				}
			});
		   edttime2.setOnClickListener(new OnClickListener() {
				
			
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Window window = getWindow();
			        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
				    time_flag="2";
			        showDialog(TIME_DIALOG_ID);
			        
					
					
				}
			});
		   edttime3.setOnClickListener(new OnClickListener() {
				
		
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Window window = getWindow();
			        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
				    time_flag="3";
			        showDialog(TIME_DIALOG_ID);
			        
					
					
				}
			});
	}
	//Creating Date picker dialog and time picker dialog
	@Override
    protected Dialog onCreateDialog(int id) {
            switch (id) {

            case DATE_DIALOG_ID:return new DatePickerDialog(this,
                            mDateSetListener,mYear, mMonth, mDay);
                    
            case TIME_DIALOG_ID: return new TimePickerDialog(this,
    				mTimeSetListener, mHour, mMinute, false);
            
            }
            return null;
    }
	/*Type :Function
    name:updateDisplay
    return type:void
    date:12-12-2011
    purpose:setting the user selected date to edittext*/
    private void updateDisplay() {
            edtdate.setText(
                    new StringBuilder()
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
 		edttime1.setText(new StringBuilder()
 		.append(mHour-12).append(":")
 		.append(mMinute).append(" PM"));
  	   }
     	else
     	{
     		edttime1.setText(new StringBuilder()
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
 		edttime2.setText(new StringBuilder()
 		.append(mHour-12).append(":")
 		.append(mMinute).append(" PM"));
  	   }
     	else
     	{
     		edttime2.setText(new StringBuilder()
     		.append(mHour).append(":")
     		.append(mMinute).append(" AM"));
     	}
		
	}
    /*Type :Function
    name:updateDisplay3
    return type:void
    date:12-12-2011
    purpose:setting the user selected time to edittext*/
    private void updateDisplay3() {
    	if(mHour>12)
  	   {
 		edttime3.setText(new StringBuilder()
 		.append(mHour-12).append(":")
 		.append(mMinute).append(" PM"));
  	   }
     	else
     	{
     		edttime3.setText(new StringBuilder()
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
			
			if(time_flag.equals("1"))
			{
			updateDisplay1();
			}
		
			if(time_flag.equals("2"))
			{
				updateDisplay2();
			}
			
			if(time_flag.equals("3"))
			{
				updateDisplay3();
			}
		}

		
		
	};

	/*Type :Function
    name:insertheart_data
    return type:void
    date:12-12-2011
    purpose:inserting data into local db*/
	 public void insertheart_data()
	    {
		     //strulogin="1";  	 
		    String strdate=edtdate.getText().toString(); 
		    String strtime1=edttime1.getText().toString();
		    String strtime2=edttime2.getText().toString();
		    String strtime3=edttime3.getText().toString();
		    String strres_hrt_rate=edtres_hrt_rate.getText().toString();
		    String strnor_hrt_rate=edtnor_hrt_rate.getText().toString();
		    String strexr_hrt_rate=edtexr_hrt_rate.getText().toString();
		    String strheart_sync="N";
	    	
	    	
	    	data.InsertHeart( strulogin, strdate, strres_hrt_rate, strtime1, strnor_hrt_rate, strtime2, strexr_hrt_rate, strtime3, strheart_sync);
	    	
	    	Toast.makeText(this, "Heart data inserted Successfully!!!!",Toast.LENGTH_SHORT).show();
	    	
	    	edtdate.setText("");
	    	edttime1.setText("");
	    	edttime2.setText("");
	    	edttime3.setText("");
	    	edtres_hrt_rate.setText("");
	    	edtnor_hrt_rate.setText("");
	    	edtexr_hrt_rate.setText("");

	    	
	    	
	    	
	    }
	 /*Type :Function
	    name:updateheart_data
	    return type:void
	    date:12-12-2011
	    purpose:updating data to local db*/
	 public void updateheart_data()
	    {
		 String strdate=edtdate.getText().toString(); 
		    String strtime1=edttime1.getText().toString();
		    String strtime2=edttime2.getText().toString();
		    String strtime3=edttime3.getText().toString();
		    String strres_hrt_rate=edtres_hrt_rate.getText().toString();
		    String strnor_hrt_rate=edtnor_hrt_rate.getText().toString();
		    String strexr_hrt_rate=edtexr_hrt_rate.getText().toString();
		    String strheart_sync="N";
	   	data.update_heart(Integer.parseInt(strulogin),selected_id, strdate, strres_hrt_rate,strtime1, strnor_hrt_rate, strtime2,strexr_hrt_rate,strtime3,strheart_sync);
	   	System.out.println("IN funcyiiiiiiii..."+strulogin);
	   	System.out.println("IN funcyiiiiiiii..."+selected_id);
	   	Toast.makeText(Enter_Heart_data.this,"Data Updated successfully",Toast.LENGTH_SHORT).show();
	   	 flag=0;
	   	edtdate.setText("");
	   	edtdate.setText("");
    	edttime1.setText("");
    	edttime2.setText("");
    	edttime3.setText("");
    	edtres_hrt_rate.setText("");
    	edtnor_hrt_rate.setText("");
    	edtexr_hrt_rate.setText("");
 	
	    }
	 /*Type :Function
	    name:getheart_log
	    return type:void
	    date:12-12-2011
	    purpose:fetching data from local db*/
	 public void getheart_log(String user_login_id,int bpid)
		{
			
			System.out.println("in funnnnnnnn getbp : "+user_login_id);
			System.out.println("in funnnnnnnn getbp : "+bpid);
			int aa=Integer.parseInt(strulogin);
			System.out.println("in funnnnnnnn getbp aa : "+aa);
			
			Cursor cur=data.getHeart_data(Integer.parseInt(strulogin));
			
			strdate=new String[cur.getCount()];
			strtime1=new String[cur.getCount()];
			strtime2=new String[cur.getCount()];
			strtime3=new String[cur.getCount()];
			strnor_hrt_rate=new String[cur.getCount()];
			strres_hrt_rate=new String[cur.getCount()];
			strexr_hrt_rate=new String[cur.getCount()];
			
		
			 
			int i=0;

	    	while(cur.moveToNext())
	    	{
	    		
	    		//String strulogin1=cur.getString(1); 
	    		String strdate1=cur.getString(2);
	    		String strres_hrt_rate1=cur.getString(3);
	    		String strtime11=cur.getString(4); 
	    		String strnor_hrt_rate1=cur.getString(5);
	    		String strtime21=cur.getString(6);
	    		String strexr_hrt_rate1=cur.getString(7);
	    		String strtime31=cur.getString(8);
	    		
	    		
	    		
	    	
	    		

	        	strdate[i]=strdate1;
	        	strres_hrt_rate[i]=strres_hrt_rate1;
	        	strtime1[i]=strtime11;
	        	strnor_hrt_rate[i]=strnor_hrt_rate1;
	        	strtime2[i]=strtime21;
	        	strexr_hrt_rate[i]=strexr_hrt_rate1;
	        	strtime3[i]=strtime31;
	        	
	        	System.out.println("bundle valuesssssssssssss:  ");
	        	System.out.println("bDate:"+strdate[i]);
	        	
	        	i++;
	        	
	    		
	    	}
	    	cur.close();
	    	
	    	edtdate.setText(strdate[(selected_index-1)]);
	    	edtres_hrt_rate.setText(strres_hrt_rate[(selected_index-1)]);
	    	edttime1.setText(strtime1[(selected_index-1)]);
	    	edtnor_hrt_rate.setText(strnor_hrt_rate[(selected_index-1)]);
	    	edttime2.setText(strtime2[(selected_index-1)]);
	    	edtexr_hrt_rate.setText(strexr_hrt_rate[(selected_index-1)]);
	    	edttime3.setText(strtime3[(selected_index-1)]);
	    
	    	
		}
	 //overriding devices back button
	 @Override
		public void onBackPressed()
		{
			System.out.println("BACK BUTTON PRESSED!!!!");
		}
             	
}
