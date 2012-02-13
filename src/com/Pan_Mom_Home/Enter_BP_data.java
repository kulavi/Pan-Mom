//Asking user to enter bp data and storing it in the local db
package com.Pan_Mom_Home;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class Enter_BP_data extends Activity {

	Spinner sprhand;
	Button btndone,btncancel;
	EditText edtdate,edttime,edtsystolic,edtdiastolic,edtpulse_count;
	String[] strsystolic,strdate,strtime,strdiastolic,strhand,strpulase_count,strwt_sync;
	DatabaseHelper data;
	Pan_mom_session mysession;
	
	int mYear,mMonth,mDay;
	static final int DATE_DIALOG_ID = 1;
	static final int TIME_DIALOG_ID = 0;
	private int mHour;
	private int mMinute;
	int  selected_index,flag;
	String strulogin,struname;
	int intuserid,selected_id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.enter_bp_data);
		
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
            if(mysession.gettracker_flag().toString().equals("viewbp"))
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
            System.out.println("IN IF.........."+strulogin);
        	System.out.println("IN IF.........."+selected_index);
        	System.out.println("IN IF....flag.."+flag);
        }
        else
        {
        	System.out.println("IN ELSE..........");
        	Bundle bun=getIntent().getExtras();
        	if(mysession.gettracker_flag().toString().equals("viewbp"))
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
        	System.out.println("IN ELSE...index."+selected_index);
        	System.out.println("IN ELSE...flag."+flag);
        	
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
		   edtsystolic=(EditText) findViewById(R.id.systolic);
		   edtdiastolic=(EditText) findViewById(R.id.diastolic);
		   edtpulse_count=(EditText) findViewById(R.id.pulse_count);
   
		   edtdate.setText(
                   new StringBuilder()                  
                   .append(mDay).append("-")
                   .append(mMonth + 1).append("-")                   
                   .append(mYear).append(" "));
		   edtdate.setInputType(0);
		   edttime.setInputType(0);

		   sprhand=(Spinner) findViewById(R.id.hand);
		   ArrayAdapter<CharSequence> conditioon_workout = ArrayAdapter.createFromResource(
	                this, R.array.hand, android.R.layout.simple_spinner_item);
			conditioon_workout.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sprhand.setAdapter(conditioon_workout);
			
			
			System.out.println("IN Enter bp loggggggggggggggggggg selected_index:  "+selected_index);
			if(selected_index>0){
				getbp_log(strulogin,selected_index);
				btndone.setText("Update");
			}
			
			
			btncancel.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) 
				{
					Intent i=new Intent(Enter_BP_data.this, MyTrackers.class);
					Bundle bun=new Bundle();
					bun.putString("UNAME",struname);
					bun.putInt("USERID", intuserid);
					i.putExtras(bun);
			    	startActivity(i);
			
				}
			});
			
			
			btndone.setOnClickListener(new OnClickListener() {
				
		
				public void onClick(View v) {
					mysession.settracker_flag("Enterbp");
					if(flag==1){
			              updatebp_data();
			              btndone.setText("Submit");
					}
					else{
						insertbp_data();}
		       
				}
			});
			edtdate.setOnClickListener(new OnClickListener() {
				
			
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Window window = getWindow();
			        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
			       // InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		           // imm.hideSoftInputFromWindow(edtdate.getApplicationWindowToken(), 0);
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
    name:insertbp_data
    return type:void
    date:12-12-2011
    purpose:inserting data into local db*/
	public void insertbp_data()
    {
    	 //String strulogin="1"; 
    	 String strdate=edtdate.getText().toString(); 
    	 String strtime=edttime.getText().toString();
    	 String strsystolic=edtsystolic.getText().toString();
    	 String strdiastolic=edtdiastolic.getText().toString();
    	 String strpulsecount=edtpulse_count.getText().toString();
    	 String strthand=sprhand.getSelectedItem().toString();
    	 String strbp_sync="N";
   
    	
    	data.InsertBP(strulogin, strdate, strtime, strsystolic, strdiastolic, strpulsecount, strthand, strbp_sync);
    	
    	Toast.makeText(this, "BP DATA INSERTED SUCCESSFULLY!!!!",Toast.LENGTH_SHORT).show();
    	
    	edtdate.setText("");
    	edttime.setText("");
    	edtsystolic.setText("");
    	edtdiastolic.setText("");
    	edtpulse_count.setText("");
    	
    	
    }
	/*Type :Function
    name:updatebp_data
    return type:void
    date:12-12-2011
    purpose:updating data to local db*/
	public void updatebp_data()
    {
		String strdate=edtdate.getText().toString(); 
   	 String strtime=edttime.getText().toString();
   	 String strsystolic=edtsystolic.getText().toString();
   	 String strdiastolic=edtdiastolic.getText().toString();
   	 String strpulsecount=edtpulse_count.getText().toString();
   	 String strthand=sprhand.getSelectedItem().toString();
   	 String strbp_sync="N";
   	data.update_bp(Integer.parseInt(strulogin),selected_id, strdate, strtime, strsystolic, strdiastolic, strpulsecount, strthand, strbp_sync);
   	System.out.println("IN funcyiiiiiiii..."+strulogin);
   	System.out.println("IN funcyiiiiiiii..."+selected_index);
   	Toast.makeText(Enter_BP_data.this,"Data Updated successfully",Toast.LENGTH_SHORT).show();
   	edtdate.setText("");
	edttime.setText("");
	edtsystolic.setText("");
	edtdiastolic.setText("");
	edtpulse_count.setText("");
    }
	/*Type :Function
    name:getbp_log
    return type:void
    date:12-12-2011
    purpose:fetching data from local db*/
	public void getbp_log(String user_login_id,int bpid)
	{
		
		System.out.println("in funnnnnnnn getbp : "+user_login_id);
		System.out.println("in funnnnnnnn getbp : "+bpid);
		int aa=Integer.parseInt(strulogin);
		System.out.println("in funnnnnnnn getbp aa : "+aa);
		
		Cursor cur=data.getBP_data(Integer.parseInt(strulogin));
		
		strdate=new String[cur.getCount()];
		strtime=new String[cur.getCount()];
		strsystolic=new String[cur.getCount()];
		strdiastolic=new String[cur.getCount()];
		strpulase_count=new String[cur.getCount()];
		strhand=new String[cur.getCount()];
		strwt_sync=new String[cur.getCount()];
		 
		int i=0;

    	while(cur.moveToNext())
    	{
    		
    		//String strulogin1=cur.getString(1); 
    		String strdate1=cur.getString(2);
    		String strtime1=cur.getString(3); 
    		String strsystolic1=cur.getString(4);
    		String strdiastolic1=cur.getString(5);
    		String strpulsecount1=cur.getString(6);
    		String strhand1=cur.getString(7);
    		//String strbp_sync1=cur.getString(8);
    		    		
    		    	

        	strdate[i]=strdate1;
        	strtime[i]=strtime1;
        	strsystolic[i]=strsystolic1;
        	strdiastolic[i]=strdiastolic1;
        	strpulase_count[i]=strpulsecount1;
        	strhand[i]=strhand1;
        	
        	
        
        	
        	System.out.println("bundle valuesssssssssssss:  ");
        	System.out.println("bDate:"+strdate[i]);
        	System.out.println("bTime:"+strtime[i]);
        	System.out.println("bSystolic:"+strsystolic[i]);
        	System.out.println("bDiastolic:"+strdiastolic[i]);
        	System.out.println("bPulse Count:"+strpulase_count[i]);
        	System.out.println("bHand:"+strhand[i]);
        	//System.out.println("Flag:"+strbp_sync1);
       	
        	i++;
        	
    		
    	}
    	cur.close();
    	
    	edtdate.setText(strdate[(selected_index-1)]);
    	edttime.setText(strtime[(selected_index-1)]);
    	edtsystolic.setText(strsystolic[(selected_index-1)]);
    	edtdiastolic.setText(strdiastolic[(selected_index-1)]);
    	edtpulse_count.setText(strpulase_count[(selected_index-1)]);
    	if(strhand[(selected_index-1)].equals("Right"))
    	{
    	sprhand.setSelection(0);
    	}
    	else
    	{
    		sprhand.setSelection(1);
    	}
	}
	 //overriding devices back button
	@Override
	public void onBackPressed()
	{
		Intent i=new Intent(Enter_BP_data.this, MyTrackers.class);
		Bundle bun=new Bundle();
		bun.putString("UNAME",struname);
		bun.putInt("USERID", intuserid);
		i.putExtras(bun);
    	startActivity(i);
		
		
	}
}
