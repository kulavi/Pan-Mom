//Asking user to enter food data and storing it in the local db
package com.Pan_Mom_Home;

import java.util.Calendar;



import android.content.DialogInterface;
import android.app.AlertDialog;
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

public class Enter_Food_data extends Activity {

	Button btndone,btncancel;
	EditText edtdate,edtwhen,edtwht_u_eat,edthow_much;

	
	DatabaseHelper data;
	Pan_mom_session mysession;
	int  selected_index,flag,selected_id;
	int mYear,mMonth,mDay;
	static final int DATE_DIALOG_ID = 1;
	static final int TIME_DIALOG_ID = 0;
	private int mHour;
	private int mMinute;
	int intuserid;
	String struname,strulogin;
	String [] strdate,strwhen,strhow_much,strwht_u_eat,strfood_sync;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.enter_food_data);
		
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
            if(mysession.gettracker_flag().toString().equals("view"))
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
        	
        	System.out.println("IN IF....flag.."+flag);
        }
        else
        {
        	System.out.println("IN ELSE..........");
        	Bundle bun=getIntent().getExtras();
        	 if(mysession.gettracker_flag().toString().equals("view"))
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
        	
        	System.out.println("IN ELSE...flag."+flag);
        	System.out.println("IN ELSE..selectedid."+selected_id);
        	
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
		   edtwhen=(EditText) findViewById(R.id.when);
		   edthow_much=(EditText) findViewById(R.id.how_much);
		   edtwht_u_eat=(EditText) findViewById(R.id.wht_u_eat);

		   edtdate.setText(
                   new StringBuilder()
                   // Month is 0 based so add 1
                   .append(mDay).append("-")
                   .append(mMonth + 1).append("-")                  
                   .append(mYear).append(" "));
		   
		   btncancel.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) 
				{
					Intent i=new Intent(Enter_Food_data.this, MyTrackers.class);
					Bundle bun=new Bundle();
					bun.putString("UNAME",struname);
					bun.putInt("USERID", intuserid);
					i.putExtras(bun);
			    	startActivity(i);
			
				}
			});
		   
		  
		  if(selected_index>0){
				getfood_log(strulogin,selected_index);
				btndone.setText("Update");
			}
		 
		   
		   btndone.setOnClickListener(new OnClickListener() {
			
				public void onClick(View arg0)
				{
					mysession.settracker_flag("Enter");
					if(flag==1)
					{
			              updatebp_data();
			              btndone.setText("Submit");
					}
					else
					{
						insertfood_data();}
					
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
		   edtwhen.setOnClickListener(new OnClickListener() {
				
			
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
		edtwhen.setText(new StringBuilder()
		.append(mHour-12).append(":")
		.append(mMinute).append(" PM"));
 	   }
    	else
    	{
    		edtwhen.setText(new StringBuilder()
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
    name:insertfood_data
    return type:void
    date:12-12-2011
    purpose:inserting data into local db*/
	 public void insertfood_data()
	    {
	    	 //strulogin="1";
	    	String strdate=edtdate.getText().toString(); 
	    	String strwhen=edtwhen.getText().toString();
	    	String strhow_much=edthow_much.getText().toString();
	    	String strwht_u_eat=edtwht_u_eat.getText().toString();
	    	String strfood_sync="N";
	    	
	    	data.InsertFood(strulogin, strdate, strwhen, strwht_u_eat, strhow_much, strfood_sync);
	    	
	    	Toast.makeText(this, "Food data inserted Successfully!!!!",Toast.LENGTH_SHORT).show();
	
	    	edtdate.setText("");
	    	edtwhen.setText("");
	    	edthow_much.setText("");
	    	edtwht_u_eat.setText("");
	    	
	    	
	    	
	    }
	 /*Type :Function
	    name:updatebp_data
	    return type:void
	    date:12-12-2011
	    purpose:updating data to local db*/
	 public void updatebp_data()
	    {
		 String strdate=edtdate.getText().toString(); 
	    	String strwhen=edtwhen.getText().toString();
	    	String strhow_much=edthow_much.getText().toString();
	    	String strwht_u_eat=edtwht_u_eat.getText().toString();
	    	String strfood_sync="N";
	   	data.update_food(Integer.parseInt(strulogin),selected_id, strdate, strwhen,strwht_u_eat, strhow_much, strfood_sync);
	   	System.out.println("IN funcyiiiiiiii..."+strulogin);
	   	System.out.println("IN funcyiiiiiiii..."+selected_id);
	   	Toast.makeText(Enter_Food_data.this,"Data Updated successfully",Toast.LENGTH_SHORT).show();
	   	 flag=0;
	   	edtdate.setText("");
    	edtwhen.setText("");
    	edthow_much.setText("");
    	edtwht_u_eat.setText("");
	    }
	 /*Type :Function
	    name:getfood_log
	    return type:void
	    date:12-12-2011
	    purpose:fetching data from local db*/
	 public void getfood_log(String user_login_id,int bpid)
		{
			
			System.out.println("in funnnnnnnn getbp : "+user_login_id);
			System.out.println("in funnnnnnnn getbp : "+bpid);
			int aa=Integer.parseInt(strulogin);
			System.out.println("in funnnnnnnn getbp aa : "+aa);
			
			Cursor cur=data.getFood_data(Integer.parseInt(strulogin));
			
			strdate=new String[cur.getCount()];
			strwht_u_eat=new String[cur.getCount()];
			strwhen=new String[cur.getCount()];
			strhow_much=new String[cur.getCount()];
			
		
			 
			int i=0;

	    	while(cur.moveToNext())
	    	{
	    		
	    		//String strulogin1=cur.getString(1); 
	    		String strdate1=cur.getString(2);
	    		String strwhen1=cur.getString(3); 
	    		String strwht_u_eat1=cur.getString(4);
	    		String strhow_much1=cur.getString(5);
	    		

	        	strdate[i]=strdate1;
	        	strwhen[i]=strwhen1;
	        	strwht_u_eat[i]=strwht_u_eat1;
	        	strhow_much[i]=strhow_much1;
	        
	        	
	        
	        	
	        	System.out.println("bundle valuesssssssssssss:  ");
	        	System.out.println("bDate:"+strdate[i]);
	        	System.out.println("bTime:"+strwhen[i]);
	        	System.out.println("bSystolic:"+strwht_u_eat[i]);
	        	System.out.println("bDiastolic:"+strhow_much[i]);
	      
	        	i++;
	        	
	    		
	    	}
	    	cur.close();
	    	
	    	edtdate.setText(strdate[(selected_index-1)]);
	    	edtwhen.setText(strwhen[(selected_index-1)]);
	    	edtwht_u_eat.setText(strwht_u_eat[(selected_index-1)]);
	    	edthow_much.setText(strhow_much[(selected_index-1)]);
	    	
		}
	 //overriding devices back button
	 @Override
		public void onBackPressed()
		{
			System.out.println("BACK BUTTON PRESSED!!!!");
		}
}

