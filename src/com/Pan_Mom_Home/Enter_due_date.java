package com.Pan_Mom_Home;


import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.R.integer;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


public class Enter_due_date extends CustomWindow {
	
	private static final String NAMESPACE = "http://tempuri.org/";      
	private static final String URL = "http://192.168.1.85/pan_mom/Service.asmx"; 
	
	private static final String SOAP_ACTION = "http://tempuri.org/user_registration";      
	private static final String METHOD_NAME = "user_registration";  
	
	
	Button btnhome,btncalculate,btnsave;
	EditText edtduedate;
	
	DatabaseHelper data;
	Pan_mom_session mysession;
	InputStream is1 = null;
	int dpd,dpm,dpy;
    java.util.Date d1= null;
    java.util.Date d2 = null ;
    java.util.Date d3 = null ;
    int mDay1,mMonth1,mYear1;
    int duedate,duemonth = 0;
    String [] arr_due_date;
    String stredd1,strage1,strocc_s1,strocc_h1,strmo_s1,strmo_h1,strmo_g1,strname1,strdate1,retrived_id;
    String strlmp1;
	int arrmonth[]={31,28,31,30,31,30,31,31,30,31,30,31};
	int arrmonth1[]={31,29,31,30,31,30,31,31,30,31,30,31};
	int mYear,mMonth,mDay;
	int intuserid;
	String strlmp="0-0-0",strdue_date,strdue_dates,strulogin;;
	String struname,strpwd,return_flag,str_lmp,str_duedate;
	static final int DATE_DIALOG_ID = 1;
	final static int REQ_CODE = 1;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_due_date); 
        
        data=new DatabaseHelper(this);
        mysession=new Pan_mom_session(this);
                    
        return_flag=mysession.getreturnflag();
        struname=mysession.getUserName();
        strpwd=mysession.getPwd();
        
        strulogin=mysession.getUserId();
        System.out.println("SAVED USER ID IS...."+strulogin);
        
        this.icon.setVisibility(View.GONE);
        try
        {
        	
        	Cursor cur=data.getreg_info(Integer.parseInt(strulogin));
			while(cur.moveToNext())
			{
				strdue_dates=cur.getString(7);
				System.out.println("1.DUE DATE TO B SET IS:"+strdue_dates);
			}
        }
        catch (Exception e) {
			// TODO: handle exception
        	strdue_dates="";
        	System.out.println("2.DUE DATE TO B SET IS:"+strdue_dates);
        	
		}
        if(strulogin.equals(""))
        {  
        	Bundle bun1=getIntent().getExtras();
            struname=bun1.getString("UNAME");
            strpwd=bun1.getString("PWD");
            intuserid=bun1.getInt("USERID");
            return_flag=bun1.getString("RETURNFLAG");
            strulogin=Integer.toString(intuserid);
            System.out.println("IN IF..........");
            System.out.println("PASSED USER NAME IS:"+struname);
            System.out.println("PASSED USER PASSWORD IS:"+strpwd);
            System.out.println("PASSED USER ID IS:"+strulogin);
            System.out.println("PASSED USER ID IS:"+strpwd);
        	
        }
        else
        {
        	intuserid=Integer.parseInt(strulogin);
        	System.out.println("IN ELSE..........");
        } 
        
                
        //this.title.setText("Enter Due Date");
        //this.icon.setVisibility(View.INVISIBLE);
        
        //btnhome=(Button) findViewById(R.id.icon);
        btncalculate=(Button) findViewById(R.id.btncalculate);
        btnsave=(Button) findViewById(R.id.btnsave);
        edtduedate=(EditText) findViewById(R.id.edtduedate);
        
        edtduedate.setText(strdue_dates);
        System.out.println("DUE DATE TO B SET IS:"+strdue_dates);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
     
        btncalculate.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
				Intent i=new Intent(Enter_due_date.this, Calculate_due_date.class);
		    	startActivityForResult(i,REQ_CODE);
				
				
			}
		});
        btnsave.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
				
				if((edtduedate.getText().toString()).equals(""))
		    	{
		    		Toast.makeText(Enter_due_date.this,"Please Enter Due Date!!",Toast.LENGTH_LONG).show();
		    	}
		    	else
	            {	
		    	
		    		if(checkInternetConnection()==false)
		    		{
		    			Toast.makeText(Enter_due_date.this, "Please Turn on the Internet Connection", Toast.LENGTH_LONG).show();
		    		}
		    		else
		    		{
		    		arr_due_date=new String[3];
		            
		            arr_due_date=edtduedate.getText().toString().split("-");

		            dpd=Integer.parseInt(arr_due_date[0].trim());
		            dpm=Integer.parseInt(arr_due_date[1].trim());
		            dpy=Integer.parseInt(arr_due_date[2].trim());
		             
		            Calendar c11 = Calendar.getInstance();  
		            int aa=c11.get(Calendar.YEAR);
		            int bb=c11.get(Calendar.MONTH);
		            int cc=c11.get(Calendar.DAY_OF_MONTH);
		            String sss1=aa+"-"+(bb+1)+"-"+cc;
		            
		             Calendar c = Calendar.getInstance();  
		             mYear1 = c.get(Calendar.YEAR);
		             mMonth1 = c.get(Calendar.MONTH);
		             mDay1= c.get(Calendar.DAY_OF_MONTH);
		            
		             c.add(Calendar.DAY_OF_MONTH, +7);
		             c.add(Calendar.MONTH,+9);
		             
		             int s1=c.get(Calendar.MONTH);
		             int s2=c.get(Calendar.DAY_OF_MONTH);
		             int s3=c.get(Calendar.YEAR);
		             String valid_range_date=s3+"-"+s1+"-"+s2;
		         
		           String user_entered_date=dpy+"-"+dpm+"-"+dpd;
		           
		           System.out.println("VALID RANGE DATE IS:"+valid_range_date);
		           System.out.println("ENTERED DUE DATE IS:"+user_entered_date);
		           System.out.println("Today  DATE IS:"+sss1);
		           SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		    			try 
		    			{
		    				 
		    					d1 = df.parse(valid_range_date);
		    				     
		    					d2 = df.parse(user_entered_date);  
		    					
		    					d3=df.parse(sss1);  
		    			} 
		    			 catch (java.text.ParseException e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			}
		    			
		    		if((d2.before(d1)) && !d2.before(d3))
		    		{
		    		
		    		System.out.println("LMP IS:"+strlmp);
		    		if(!strlmp.equals("0-0-0"))
		    		{
		    			System.out.println("LMP FROM IF IS:"+strlmp);
		    			data.updatelmp_and_edd(struname, strpwd, strlmp, edtduedate.getText().toString());
		    		}
				 
		    		else
					{
		    			
		    			   
		    			 Calendar thatDay = Calendar.getInstance();
		    		       thatDay.set(Calendar.DAY_OF_MONTH,dpd);
		    		       thatDay.set(Calendar.MONTH,dpm); // 0-11 so 1 less
		    		       thatDay.set(Calendar.YEAR,dpy);	
		    		       
		    		       thatDay.add(Calendar.DAY_OF_MONTH,-7);
		    		        thatDay.add(Calendar.MONTH,-9);
		    		        
		    		        int s11=thatDay.get(Calendar.MONTH);
		    		        int s21=thatDay.get(Calendar.DAY_OF_MONTH);
		    		        int s31=thatDay.get(Calendar.YEAR);
		    		       
		    		       

		    	         strlmp=s21+"-"+(s11)+"-"+s31;
		    	         
		    	         System.out.println("LMP FROM ELSE IS:"+strlmp);
		    	         
		    	         data.updatelmp_and_edd(struname, strpwd, strlmp, edtduedate.getText().toString());
		    	         
		    	         
					}
		    		
		    		
		    		fun();
		    		
		    		ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
  					nameValuePairs1.add(new BasicNameValuePair("retrived_id",retrived_id));
  					nameValuePairs1.add(new BasicNameValuePair("username1",strname1));
  					nameValuePairs1.add(new BasicNameValuePair("lmp",strlmp1));
  					nameValuePairs1.add(new BasicNameValuePair("duedate",stredd1));
  					nameValuePairs1.add(new BasicNameValuePair("age",strage1));	       
  			        nameValuePairs1.add(new BasicNameValuePair("occupself",strocc_s1));
  			        nameValuePairs1.add(new BasicNameValuePair("occuphus",strocc_h1));
  			        nameValuePairs1.add(new BasicNameValuePair("mobself",strmo_s1));
  			        nameValuePairs1.add(new BasicNameValuePair("mobhus",strmo_h1));
  			        nameValuePairs1.add(new BasicNameValuePair("mobguard",strmo_g1));
  			      nameValuePairs1.add(new BasicNameValuePair("date",strdate1));
  			      	try
  			         {
  			                HttpClient httpclient = new DefaultHttpClient();
  			                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/xpprofiles.php");
  			                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
  			                HttpResponse response = httpclient.execute(httppost);
  			                HttpEntity entity = response.getEntity();
  			                is1 = entity.getContent();
  			              
  			         }
  			         catch(Exception e)
  			         {
  			        	 
  			         }
			       	
		    		
  			       Toast.makeText(Enter_due_date.this,"Data Saved Successfully!!", Toast.LENGTH_LONG).show();
		    		
		    		
		    		if(return_flag.equals("Setting"))
					{
						Intent i=new Intent();
						Bundle bun=new Bundle();
						bun.putString("UNAME",struname);
		 				bun.putString("PWD", strpwd);
						bun.putInt("USERID",intuserid);
						i.putExtras(bun);  
						setResult(RESULT_OK, i);  
						finish();
					}
					else
					{
					Intent i=new Intent(Enter_due_date.this,Dashboard.class);
					Bundle bun=new Bundle();
					bun.putString("UNAME",struname);
					bun.putString("PWD", strpwd);
					bun.putInt("USERID",intuserid);
					i.putExtras(bun);
					startActivity(i);
					}
		    		}
		    		else
		    		{
		    			Toast.makeText(Enter_due_date.this, "Only dates within nine months from now can be entered as due date", Toast.LENGTH_SHORT).show();
		    		}
					
				
		    		}
				
				
	            }
				
			}
		});
        edtduedate.setOnClickListener(new OnClickListener() {
			
		
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
				Window window = getWindow();
		        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
			    
				showDialog(DATE_DIALOG_ID);
				
				
			}
		});
        
        
        
        
        
        
        
	}
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQ_CODE){

			if (resultCode == RESULT_OK){

				Bundle bun1=data.getExtras();
				strlmp=bun1.getString("LMP");
		      	strdue_date=bun1.getString("DUEDATE");
		      	System.out.println("LMP FROM PREVIOUS FORM:"+strlmp);
		      	System.out.println("CALCULATED DUE DATE FROM LMP:"+strdue_date);
		      	edtduedate.setText(strdue_date);

			} 
		  
		
	    	    
	  }
	}
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
    private void updateDisplay() {
            edtduedate.setText(
                    new StringBuilder()
                    // Month is 0 based so add 1
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

    
       
    
    public void sync_regi_data()
    {
    	
   		
   		 try {      
   			  		  
   			    
   	    	 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
   		 		
   	    	
   	    	 	
   		 		/*request.addProperty("r_date",current_date); 
   		 		request.addProperty("r_login",strulogin);
   		 		request.addProperty("r_email",stremail);
   		 		request.addProperty("r_pwd",strpwd);
   		 		request.addProperty("r_name",strname);
   		 		request.addProperty("r_lmp",null);   
   		 		request.addProperty("r_edd",null); 
   		 		request.addProperty("r_age",strage);
   		 		request.addProperty("r_occ_s",strocc_s);
   		 		request.addProperty("r_occ_h",strocc_h);
   		 		request.addProperty("r_mo_s",strmo_s);
		 		request.addProperty("r_mo_h",strmo_h);
		 		request.addProperty("r_mo_g",strmo_g);*/
   		 		
   		 		
   		 			         		 
   		 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

   		 		envelope.setOutputSoapObject(request); 
   		 		envelope.dotNet=true; 
   		 		envelope.encodingStyle = SoapSerializationEnvelope.XSD;
   		 		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
   		 	
   		 		androidHttpTransport.call(SOAP_ACTION, envelope);
   		 		Toast.makeText(Enter_due_date.this, "Mail send successfully!!!!!!!",Toast.LENGTH_SHORT).show();
   		 		
   		 		//data.updatemail(insmid, onlyname);
   		    	 
   		 }
   	 	    catch (Exception e)    
   	        {
   			  System.out.println("Error msg:"+e.getMessage());
   	        
   	        }
   	 

    }

    public void fun()
    {
    	Cursor cur=data.getreg_info(intuserid);
    	while(cur.moveToNext())
    	{
    		 strdate1=cur.getString(1);
    		String strulogin1=cur.getString(2); 
    		String stremail1=cur.getString(3); 
    		String strpwd1=cur.getString(4);
    		 strname1=cur.getString(5);
    		strlmp1=cur.getString(6);
    		stredd1=cur.getString(7);
    		 strage1=cur.getString(8);
    		 strocc_s1=cur.getString(9);
    		 strocc_h1=cur.getString(10);
    		 strmo_s1=cur.getString(11);
    		 strmo_h1=cur.getString(12);
    		 strmo_g1=cur.getString(13);
    		 retrived_id=cur.getString(14);
        	
        	System.out.println("Date:"+strdate1);
        	System.out.println("ULogin:"+strulogin1);
        	System.out.println("Email:"+stremail1);
        	System.out.println("Password:"+strpwd1);
        	System.out.println("Name:"+strname1);
        	System.out.println("LMP:"+strlmp1);
        	System.out.println("EDD:"+stredd1);
        	System.out.println("Age:"+strage1);
        	System.out.println("Self Occupation:"+strocc_s1);
        	System.out.println("Husband Occupation:"+strocc_h1);
        	System.out.println("Self No:"+strmo_s1);
        	System.out.println("Husband No:"+strmo_h1);
        	System.out.println("Guardian No:"+strmo_g1);
    		
    	}
    }
    private boolean checkInternetConnection() {
		   ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
		   if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() &&    conMgr.getActiveNetworkInfo().isConnected()) {
		         return true;
		   } else {
		         System.out.println("Internet Connection Not Present");
		       return false;
		   }
		}
	public void home()
    {
    	Intent i=new Intent(Enter_due_date.this, Pan_MomActivity.class);
    	startActivity(i);
    }
	@Override
	public void onBackPressed()
	{
		System.out.println("BACK BUTTON PRESSED!!!!");
	}

}
