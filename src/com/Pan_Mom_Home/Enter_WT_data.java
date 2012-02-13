//Asking user to enter weight data and storing it in the local db
package com.Pan_Mom_Home;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.Toast;

public class Enter_WT_data extends Activity {
	/** Called when the activity is first created. */
	Spinner sprcondition1,sprcondition2;
	Button btndone,btncancel;
	EditText edtdate,edtweight,edtbody_fat;
	int  selected_index,flag,selected_id;;
	DatabaseHelper data;
	Pan_mom_session mysession;
	String [] strweight_kg,strdate,strbody_fat,strcondi1,strcondi2;
	int mYear,mMonth,mDay;
	static final int DATE_DIALOG_ID = 1;
	String strulogin,struname;
	int intuserid;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.enter_wt_data);
		
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
            if(mysession.gettracker_flag().toString().equals("viewwt"))
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
        	if(mysession.gettracker_flag().toString().equals("viewwt"))
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
        
        /*inflating the views to use in this class*/
		   btndone=(Button) findViewById(R.id.done); 
		   btncancel=(Button) findViewById(R.id.cancel);        
		   edtdate=(EditText) findViewById(R.id.date);
		   edtweight=(EditText) findViewById(R.id.weight);
		   sprcondition1=(Spinner) findViewById(R.id.condition1);
		   sprcondition2=(Spinner) findViewById(R.id.condition2);
		   edtbody_fat=(EditText) findViewById(R.id.body_fat);
		   		   
		   edtdate.setText(
	                 new StringBuilder()
	                 .append(mDay).append("-")
	                 .append(mMonth + 1).append("-")               
	                 .append(mYear).append(" "));
		   
		sprcondition1=(Spinner) findViewById(R.id.condition1);
		ArrayAdapter<CharSequence> conditioon_workout = ArrayAdapter.createFromResource(
                this, R.array.condition1, android.R.layout.simple_spinner_item);
		conditioon_workout.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 sprcondition1.setAdapter(conditioon_workout);
		
		 sprcondition2=(Spinner) findViewById(R.id.condition2);
		ArrayAdapter<CharSequence> conditioon_meal = ArrayAdapter.createFromResource(
                this, R.array.condition2, android.R.layout.simple_spinner_item);
		conditioon_meal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 sprcondition2.setAdapter(conditioon_meal);
		 
		 
		 btncancel.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) 
				{
					Intent i=new Intent(Enter_WT_data.this, MyTrackers.class);
					Bundle bun=new Bundle();
					bun.putString("UNAME",struname);
					bun.putInt("USERID", intuserid);
					i.putExtras(bun);
			    	startActivity(i);
			
				}
			});
		 if(selected_index>0){
				getweight_log(strulogin);
				 btndone.setText("Update");
			}
		 
		 btndone.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					mysession.settracker_flag("Enterwt");
					if(flag==1)
					{
			              updatewt_data();
			              btndone.setText("Submit");
					}
					else
					{
					
			    		insertweight_data();
			    	 }
		      
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
	}
	//Creating Date picker dialog and time picker dialog
	@Override
 protected Dialog onCreateDialog(int id) {
         switch (id) {

         case DATE_DIALOG_ID:
                 return new DatePickerDialog(this,
                         mDateSetListener,
                         mYear, mMonth, mDay);
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
                 .append(mDay).append("-")
                 .append(mMonth + 1).append("-")               
                 .append(mYear).append(" "));
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
 /*Type :Function
 name:insertweight_data
 return type:void
 date:12-12-2011
 purpose:inserting data into local db*/
 
	public void insertweight_data()
    {
    	 //strulogin="1";
    	 String strweight_kg=edtweight.getText().toString();
    	 String strdate=edtdate.getText().toString(); 
    	 String strc1=sprcondition1.getSelectedItem().toString();
    	 String strc2=sprcondition2.getSelectedItem().toString();
    	 String strbody_fat=edtbody_fat.getText().toString();
    	 String strwt_sync="N";  
    	 
    	
    	data.InsertWeight(strulogin, strweight_kg, strdate, strc1, strc2, strbody_fat, strwt_sync);
    	
    	Toast.makeText(this, "WEIGHT DATA INSERTED SUCCESSFULLY!!!!",Toast.LENGTH_SHORT).show();
    	edtweight.setText("");
    	edtdate.setText("");
    	edtbody_fat.setText("");
    	sprcondition1.setSelection(0);
    	sprcondition2.setSelection(0);
    	 	
    	
    }
	/*Type :Function
    name:updatewt_data
    return type:void
    date:12-12-2011
    purpose:updating data to local db*/
	 public void updatewt_data()
	    {
		 String strweight_kg=edtweight.getText().toString();
    	 String strdate=edtdate.getText().toString(); 
    	 String strc1=sprcondition1.getSelectedItem().toString();
    	 String strc2=sprcondition2.getSelectedItem().toString();
    	 String strbody_fat=edtbody_fat.getText().toString();
    	 String strwt_sync="N";  
	   	data.update_wt(Integer.parseInt(strulogin),selected_id,strweight_kg,strdate, strc1, strc2,strbody_fat,strwt_sync);
	   	
	   	Toast.makeText(Enter_WT_data.this,"Data Updated successfully",Toast.LENGTH_SHORT).show();
	   	 flag=0;
	   	edtweight.setText("");
    	edtdate.setText("");
    	edtbody_fat.setText("");
    	sprcondition1.setSelection(0);
    	sprcondition2.setSelection(0);
	    }
	 /*Type :Function
	    name:getweight_log
	    return type:void
	    date:12-12-2011
	    purpose:fetching data from local db*/
	public void getweight_log(String user_login_id)
	{
					
		Cursor cur=data.getWt_data(Integer.parseInt(user_login_id));
		
		strdate=new String[cur.getCount()];
		strweight_kg=new String[cur.getCount()];
		strcondi1=new String[cur.getCount()];
		strcondi2=new String[cur.getCount()];
		strbody_fat=new String[cur.getCount()];

		int i=0;

    	while(cur.moveToNext())
    	{
    		
    		//String strulogin1=cur.getString(1); 
    		
    		String strweight_kg1=cur.getString(2); 
    		String strdate1=cur.getString(3);
    		String strcondi_1=cur.getString(4);
    		String strcondi_2=cur.getString(5);
    		String strbody_fat1=cur.getString(6);
    		
        	strdate[i]=strdate1;
        	strweight_kg[i]=strweight_kg1;
        	strcondi1[i]=strcondi_1;
        	strcondi2[i]=strcondi_2;
        	strbody_fat[i]=strbody_fat1;
        
            
        	i++;
        	
    		
    	}
    	cur.close();
    	
    	edtdate.setText(strdate[(selected_index-1)]);
    	edtweight.setText(strweight_kg[(selected_index-1)]);
    	edtbody_fat.setText(strbody_fat[(selected_index-1)]);
    	
    	//Condition1
    	
    	if(strcondi1[(selected_index-1)].equals("Before workout"))
    	{
    		sprcondition1.setSelection(0);
    	}
    	if(strcondi1[(selected_index-1)].equals("After workout"))
    	{
    		sprcondition1.setSelection(1);
    	}
    	if(strcondi1[(selected_index-1)].equals("workout nonspecific"))
    	{
    		sprcondition1.setSelection(2);
    	}
    	
    	//Condition2
    	
    	if(strcondi2[(selected_index-1)].equals("Before Meal"))
    	{
    		sprcondition2.setSelection(0);
    	}
    	if(strcondi2[(selected_index-1)].equals("After Meal"))
    	{
    		sprcondition2.setSelection(1);
    	}
    	if(strcondi2[(selected_index-1)].equals("Meal no-specific"))
    	{
    		sprcondition2.setSelection(2);
    	}
    	
    	
    	
    	
	}
	 //overriding devices back button
	@Override
	public void onBackPressed()
	{
		System.out.println("BACK BUTTON PRESSED!!!!");
	}
}
