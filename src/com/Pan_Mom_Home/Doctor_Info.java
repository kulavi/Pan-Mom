//Asking for doctor information and storing in local db
package com.Pan_Mom_Home;

import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class Doctor_Info extends CustomWindow {
     
	Button btndone,btncancel,btnhome;;
	EditText edtdrname,edtdrphn,edtdrothrphn,edtdradd,edthosp_name,edthosp_phn,edthosp_add;
	
	DatabaseHelper data;
	Pan_mom_session mysession;
		
	int intuserid;
	String strulogin,struname;
	String strdrname,strdrphn,strdrothrphn,strdradd,strhosp_name,strhosp_phn,strhosp_add,strdr_sync;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.doctor_info);
		
		DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
		Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
        /*initializing classes*/
		data=new DatabaseHelper(this);  
		mysession=new Pan_mom_session(this);
		
		
		/*getting data from session*/
		strulogin=mysession.getUserId();
		struname=mysession.getUserName();
        System.out.println("SAVED USER ID IS...."+strulogin);
        /*if there are no values in the session then getting data from previous activity*/
        if(strulogin.equals(""))
        {
        	
        	Bundle bun=getIntent().getExtras();
            struname=bun.getString("UNAME"); 
            intuserid=bun.getInt("USERID");
            strulogin=Integer.toString(intuserid);
        	System.out.println("IN IF..........");
        }
        else
        {
        	
        	System.out.println("IN ELSE..........");
        	intuserid=Integer.parseInt(strulogin);
        	
        }
		
        
        
        /*inflating the views to use in this class*/
		   btndone=(Button) findViewById(R.id.done); 
		   btncancel=(Button) findViewById(R.id.cancel);   
		   
		   edtdrname=(EditText) findViewById(R.id.name);
		   edtdrphn=(EditText) findViewById(R.id.phone);
		   edtdrothrphn=(EditText) findViewById(R.id.other_phone);
		   edtdradd=(EditText) findViewById(R.id.address);
		   edthosp_name=(EditText) findViewById(R.id.hname);
		   edthosp_phn=(EditText) findViewById(R.id.hphone);
		   edthosp_add=(EditText) findViewById(R.id.haddress);
		   
		   
		   
		   btncancel.setOnClickListener(new OnClickListener() {
	    		
			   
	    		public void onClick(View v) {
	    			// TODO Auto-generated method stub
	    			
	    			home();
	    			
	    			
	    		}
	    	});
		   
		   btndone.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
			    		insertdoctor_data();
		            			
					
				}
			});
		   
		   
	}

	/*Type :Function
    name:insertdoctor_data
    return type:void
    date:12-12-2011
    purpose:Insrting doctor info into the local db*/
	 public void insertdoctor_data()
	    {
		     strulogin="1";  	 
		     strdrname=edtdrname.getText().toString(); 
		     strdrphn=edtdrphn.getText().toString();
		     strdrothrphn=edtdrothrphn.getText().toString();
		     strdradd=edtdradd.getText().toString();
		     strhosp_name=edthosp_name.getText().toString();
		     strhosp_phn=edthosp_phn.getText().toString();
		     strhosp_add=edthosp_add.getText().toString();
		     strdr_sync="N";
	    	
	    	
	    	data.InsertDoctor_Info(strulogin, strdrname, strdrphn, strdrothrphn, strdradd, strhosp_name, strhosp_phn, strhosp_add, strdr_sync);
	    	
	    	Toast.makeText(this, "Doctor Info saved Successfully!!!!",Toast.LENGTH_LONG).show();
	    	
	    	

	    	
	    	Cursor cur=data.getDoctor_Info(Integer.parseInt(strulogin));
	    	while(cur.moveToNext())
	    	{
	    		String strulogin1=cur.getString(1);
	    		String strdrname1=cur.getString(2); 
	    		String strdrphn1=cur.getString(3); 
	    		String strdrothrphn1=cur.getString(4);
	    		String strdradd1=cur.getString(5);
	    		String strhospname1=cur.getString(6);
	    		String strhospphn1=cur.getString(7);
	    		String strhospadd1=cur.getString(8);
	    		String strdr_sync1=cur.getString(9);
	    		
	    		System.out.println("ULogin:"+strulogin1);
	        	System.out.println("Name:"+strdrname1);	        	
	        	System.out.println("Phone:"+strdrphn1);
	        	System.out.println("Other Phone:"+strdrothrphn1);
	        	System.out.println("Address:"+strdradd1);
	        	System.out.println("Hospital Name:"+strhospname1);
	        	System.out.println("Hospital Phone:"+strhospphn1);
	        	System.out.println("Hospital Address:"+strhospadd1);
	        	System.out.println("Doctor_sync:"+strdr_sync1);
	        	
	    	}
	    	
	    }
	 /*Type :Function
	    name:home
	    return type:void
	    date:12-12-2011
	    purpose:returning to dashboard*/
	 public void home()
	    {
	    	Intent i=new Intent(Doctor_Info.this, Dashboard.class);
	    	Bundle bun=new Bundle();
			bun.putString("UNAME",struname);
			//bun.putString("PWD",edtpwd.getText().toString());
			bun.putInt("USERID", intuserid);
			i.putExtras(bun);
	    	startActivity(i);
	    }
	 //overriding the devices back button
	 @Override
	 public void onBackPressed()
		{
		 	Intent i=new Intent(Doctor_Info.this, Dashboard.class);
	    	Bundle bun=new Bundle();
			bun.putString("UNAME",struname);		
			bun.putInt("USERID", intuserid);
			i.putExtras(bun);
	    	startActivity(i);
		
		}
             	
}
