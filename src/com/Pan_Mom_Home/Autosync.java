//Service class to sync all trackers to Server after a specific time interval

package com.Pan_Mom_Home;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class Autosync  extends Service {
	DatabaseHelper data;
	Timer timer;
	//String pan_user_id;
	InputStream is = null;
	InputStream is1 = null;
	InputStream is2 = null;
	String result = "",results = "",current_date;
	String temp="",retrived_id,item_id;
	 int temp1;
	
	private String panmom_reg_id;
	
	String strulogin,struname;
	String int_user_id;
	int intuserid,r_id;
	
	private static final String TAG = "MyService";

	private TimerTask updateTask = new TimerTask() {
	    @Override
	    public void run() {
	      Log.i(TAG, "Timer task doing work");
	     sync1();
	     
	    }
	  };
	
	
	public IBinder onBind(Intent intent) {  
		return null;
	}
	
	
	public void onCreate() {
		Log.d(TAG, "onCreate");
		
		data=new DatabaseHelper(this);
		
		
         //Scheduling the timer 
		 timer = new Timer("Smart Address Book");
		    timer.schedule(updateTask, 1000L, 300 * 1000L);
		 //timer.schedule(updateTask, 1000L, 10 * 1000L);
	}

	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		
	}

	public void onStart(Intent intent, int startid) {
		
		panmom_reg_id= intent.getStringExtra("pan_user_id");
        strulogin=intent.getStringExtra("user_login");
      
        System.out.println("sync 1!!!!!!!!!!!" +panmom_reg_id );
        System.out.println("sync 1!!!!!!!!!!!" +strulogin );
		Log.d(TAG, "onStart");
		 
	}
public void sync1()
{   call_calender();
	System.out.println("sync 1!!!!!!!!!!!");
	getwt_log();
	gethb_log();	
	getglucose_log(); 	
	getfood_log();	
	getheart_log();
	getbp_log();
	
	getactivity_log();
	 
}

/*Type :Function
name:call_calender
return type:String
date:12-12-2011
purpose:Calculates the current date*/
public String call_calender()
{
	final Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);
    
    int mHour = c.get(Calendar.HOUR_OF_DAY);
	int mMinute = c.get(Calendar.MINUTE);
	int msecond=c.get(Calendar.SECOND);
	
	
	String stime=mHour+":"+mMinute+":"+msecond;
    
    current_date=mYear+"-"+(mMonth+1)+"-"+mDay+" "+stime;
    
    System.out.println("CURRENT DATE IS:"+current_date);
    
    return current_date;
}

/*Type :Function
name:getactivity_log
return type:void
date:12-12-2011
purpose:get the activity trackers data from local and syncs it to the server*/
public void getactivity_log()
{
	Cursor cur=data.getActivity_data(Integer.parseInt(strulogin));
	

	while(cur.moveToNext())
	{
		
		
		String strbp_sync1=cur.getString(8);
		
		if(strbp_sync1.equals("N"))
		{
		
		ArrayList<NameValuePair> nameValuePairs_activity = new ArrayList<NameValuePair>();
		nameValuePairs_activity.add(new BasicNameValuePair("user_id",panmom_reg_id));
		nameValuePairs_activity.add(new BasicNameValuePair("current_date",call_calender()));
		
       	
      	try
         {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/activities_get.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs_activity));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is2 = entity.getContent();
              
         }
         catch(Exception e)
         {
        	 
         } 
         
         try
         {
         	BufferedReader reader = new BufferedReader(new InputStreamReader(is2,"iso-8859-1"),8);
         	StringBuilder sb = new StringBuilder();
         	String line ;
      
         	while ((line = reader.readLine()) != null) 
         	{
         		sb.append(line + "\n");
         	}
         	is2.close();
         	results=sb.toString();  
         }
         catch(Exception e)
         {
     	
         }
		         
         
          try
          {
	           	 
	             JSONArray jArray = new JSONArray(results);
	            
	             	int temp2 = 0;
	             	for(int i=0;i<jArray.length();i++)
	             	{
	             			JSONObject json_data = jArray.getJSONObject(i);
	             			temp2 = json_data.getInt("id");
	             					             			
                   		    System.out.println("iiiddddddddd   is ="+temp2);
	             	}
	            
	             	item_id=Integer.toString(temp2);
	             	System.out.println("retrived_item_id   is ="+item_id);
	            
	            
	       }
	      	catch(JSONException e)
	      	{
	           			//text1.setText("Invalid Login!!"); 
	      	}
		
		
		String strid1=cur.getString(0);
		//String strulogin1=cur.getString(1); 
		String strdate1=cur.getString(2);
		String stretime1=cur.getString(3); 
		String strecalori1=cur.getString(4);
		String strwtime1=cur.getString(5);
		String strwcalori1=cur.getString(6);
		String strdistance1=cur.getString(7);
		
		    		
		        	
		        	
    	System.out.println("ID IS:"+strid1);
    	//System.out.println("ULogin:"+strulogin1);
    	System.out.println("Date:"+strdate1);
    	System.out.println("exe_time:"+stretime1);
    	System.out.println("exe_cal:"+strecalori1);
    	System.out.println("Walk_time:"+strwtime1);
    	System.out.println("Walk_cal:"+strwcalori1);
    	System.out.println("Distance:"+strdistance1);
    	//System.out.println("Flag:"+strbp_sync1);
    	
    	
    	ArrayList<NameValuePair> nameValuePairs_activity1 = new ArrayList<NameValuePair>();
//////////////////////      	
    	
    	nameValuePairs_activity1.add(new BasicNameValuePair("item_id",item_id));
    	nameValuePairs_activity1.add(new BasicNameValuePair("pan_user_id",panmom_reg_id));
    	nameValuePairs_activity1.add(new BasicNameValuePair("distance",strdistance1));
    	nameValuePairs_activity1.add(new BasicNameValuePair("date",strdate1));
    	nameValuePairs_activity1.add(new BasicNameValuePair("duration",stretime1));
    	nameValuePairs_activity1.add(new BasicNameValuePair("duration1",strwtime1));
    	nameValuePairs_activity1.add(new BasicNameValuePair("cal_burned",strecalori1));
    	nameValuePairs_activity1.add(new BasicNameValuePair("cal_burned1",strwcalori1));
    	nameValuePairs_activity1.add(new BasicNameValuePair("current_date",call_calender()));
		
       	
      	try
         {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/activities_insert.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs_activity1));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is2 = entity.getContent();
              
         }
         catch(Exception e)
         {
        	 
         } 
    	
    	
         data.update_activity(Integer.parseInt(strulogin), Integer.parseInt(strid1), strdate1, stretime1, strecalori1, strwtime1, strwcalori1, strdistance1, "Y");
		}
	}
	cur.close();
	
	
	
}
/*Type :Function
name:getwt_log
return type:void
date:12-12-2011
purpose:get the weight trackers data from local and syncs it to the server*/
public void getwt_log()
{
	Cursor cur=data.getWt_data(Integer.parseInt(strulogin));
	

	while(cur.moveToNext())
	{
		  		
		String strwt_sync1=cur.getString(7);
		
		if(strwt_sync1.equals("N"))
		{
		
		ArrayList<NameValuePair> nameValuePairs_wt = new ArrayList<NameValuePair>();
		nameValuePairs_wt.add(new BasicNameValuePair("user_id",panmom_reg_id));
		nameValuePairs_wt.add(new BasicNameValuePair("current_date",call_calender()));
		
       	
      	try
         {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/weight_get.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs_wt));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is2 = entity.getContent();
              
         }
         catch(Exception e)
         {
        	 
         } 
         
         try
         {
         	BufferedReader reader = new BufferedReader(new InputStreamReader(is2,"iso-8859-1"),8);
         	StringBuilder sb = new StringBuilder();
         	String line ;
      
         	while ((line = reader.readLine()) != null) 
         	{
         		sb.append(line + "\n");
         	}
         	is2.close();
         	results=sb.toString();  
         }
         catch(Exception e)
         {
     	
         }
		         
         
          try
          {
	           	 
	             JSONArray jArray = new JSONArray(results);
	            
	             	int temp2 = 0;
	             	for(int i=0;i<jArray.length();i++)
	             	{
	             			JSONObject json_data = jArray.getJSONObject(i);
	             			temp2 = json_data.getInt("id");
	             					             			
                   		    System.out.println("iiiddddddddd   is ="+temp2);
	             	}
	            
	             	item_id=Integer.toString(temp2);
	             	System.out.println("retrived_item_id   is ="+item_id);
	            
	            
	       }
	      	catch(JSONException e)
	      	{
	           			//text1.setText("Invalid Login!!"); 
	      	}
		
		
		
	      	String strwid1=cur.getString(0);
    		//String strulogin1=cur.getString(1); 
    		String strweight_kg1=cur.getString(2);
    		String strdate1=cur.getString(3); 
    		String strc11=cur.getString(4);
    		String strc21=cur.getString(5);
    		String strbody_fat1=cur.getString(6);
    		
    		
    		        	
        	
        	
        	System.out.println("Weight In Kg:"+strweight_kg1);
        	System.out.println("Date:"+strdate1);
        	System.out.println("Condition1:"+strc11);
        	System.out.println("Condition2:"+strc21);
        	//System.out.println("Body fat:"+strbody_fat1);
    	
    	
    	ArrayList<NameValuePair> nameValuePairs_wt1 = new ArrayList<NameValuePair>();
    	nameValuePairs_wt1.add(new BasicNameValuePair("item_id",item_id));
    	nameValuePairs_wt1.add(new BasicNameValuePair("date",strdate1));
    	nameValuePairs_wt1.add(new BasicNameValuePair("weight",strweight_kg1));
    	nameValuePairs_wt1.add(new BasicNameValuePair("condition1",strc11));
    	nameValuePairs_wt1.add(new BasicNameValuePair("condition2",strc21));
    	nameValuePairs_wt1.add(new BasicNameValuePair("current_date",call_calender()));
    	nameValuePairs_wt1.add(new BasicNameValuePair("pan_user_id",panmom_reg_id));
		
       	
      	try
         {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/weight_insert.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs_wt1));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is2 = entity.getContent();
              
         }
         catch(Exception e)
         {
        	 
         } 
    	
    	data.update_wt(Integer.parseInt(strulogin), Integer.parseInt(strwid1), strweight_kg1, strdate1, strc11, strc21, strbody_fat1, "Y");
         
		}
		
	}
	cur.close();
	
}

/*Type :Function
name:getbp_log
return type:void
date:12-12-2011
purpose:get the bp trackers data from local and syncs it to the server*/
public void getbp_log()
{
	Cursor c_bp=data.getBP_data(Integer.parseInt(strulogin));
	
	
	while(c_bp.moveToNext())
	{
		
		String strbp_sync1=c_bp.getString(8);
		if(strbp_sync1.equals("N"))
		{
		
		ArrayList<NameValuePair> nameValuePairs_bp = new ArrayList<NameValuePair>();
		nameValuePairs_bp.add(new BasicNameValuePair("user_id",panmom_reg_id));
		nameValuePairs_bp.add(new BasicNameValuePair("current_date",call_calender()));
		
       	
      	try
         {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/bp_get.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs_bp));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is2 = entity.getContent();
              
         }
         catch(Exception e)
         {
        	 
         } 
         
         try
         {
         	BufferedReader reader = new BufferedReader(new InputStreamReader(is2,"iso-8859-1"),8);
         	StringBuilder sb = new StringBuilder();
         	String line ;
      
         	while ((line = reader.readLine()) != null) 
         	{
         		sb.append(line + "\n");
         	}
         	is2.close();
         	results=sb.toString();  
         }
         catch(Exception e)
         {
     	
         }
		         
         
          try
          {
	           	 
	             JSONArray jArray = new JSONArray(results);
	            
	             	int temp2 = 0;
	             	for(int i=0;i<jArray.length();i++)
	             	{
	             			JSONObject json_data = jArray.getJSONObject(i);
	             			temp2 = json_data.getInt("id");
	             					             			
                   		    System.out.println("iiiddddddddd   is ="+temp2);
	             	}
	            
	             	item_id=Integer.toString(temp2);
	             	System.out.println("retrived_item_id   is ="+item_id);
	            
	            
	       }
	      	catch(JSONException e)
	      	{
	           			//text1.setText("Invalid Login!!"); 
	      	}
		
		
		
		String strbpid1=c_bp.getString(0);
		//String strulogin1=c_bp.getString(1); 
		String strdate1=c_bp.getString(2);
		String strtime1=c_bp.getString(3); 
		String strsystolic1=c_bp.getString(4);
		String strdiastolic1=c_bp.getString(5);
		String strpulsecount1=c_bp.getString(6);
		String strhand1=c_bp.getString(7);
		
		    		
		        	
		
    	
    	//System.out.println("ULogin:"+strulogin1);
    	System.out.println("Date:"+strdate1);
    	System.out.println("Time:"+strtime1);
    	System.out.println("Systolic:"+strsystolic1);
    	System.out.println("Diastolic:"+strdiastolic1);
    	System.out.println("Pulse Count:"+strpulsecount1);
    	System.out.println("Hand:"+strhand1);
    	//System.out.println("Flag:"+strbp_sync1);
    	
    	
    	ArrayList<NameValuePair> nameValuePairs_bp1 = new ArrayList<NameValuePair>();
    	nameValuePairs_bp1.add(new BasicNameValuePair("item_id",item_id));
    	nameValuePairs_bp1.add(new BasicNameValuePair("date",strdate1));
    	nameValuePairs_bp1.add(new BasicNameValuePair("time",strtime1));
    	nameValuePairs_bp1.add(new BasicNameValuePair("hand",strhand1));
    	nameValuePairs_bp1.add(new BasicNameValuePair("pulse_count",strpulsecount1));
    	nameValuePairs_bp1.add(new BasicNameValuePair("sys",strsystolic1));
    	nameValuePairs_bp1.add(new BasicNameValuePair("diasystolic",strdiastolic1));
    	nameValuePairs_bp1.add(new BasicNameValuePair("current_date",call_calender()));
    	nameValuePairs_bp1.add(new BasicNameValuePair("pan_user_id",panmom_reg_id));
		
       	
      	try
         {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/bp_insert.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs_bp1));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is2 = entity.getContent();
              
         }
         catch(Exception e)
         {
        	 
         } 
    	
    	data.update_bp(Integer.parseInt(strulogin), Integer.parseInt(strbpid1), strdate1, strtime1, strsystolic1, strdiastolic1, strpulsecount1, strhand1, "Y");
         
		}
    	
		
	}
	c_bp.close();
	
}
/*Type :Function
name:getheart_log
return type:void
date:12-12-2011
purpose:get the heart trackers data from local and syncs it to the server*/

public void getheart_log()
{
	Cursor cur=data.getHeart_data(Integer.parseInt(strulogin));
	
	

	while(cur.moveToNext())
	{
		String strheart_sync1=cur.getString(9);
		if(strheart_sync1.equals("N"))
		{
		
		ArrayList<NameValuePair> nameValuePairs_heart = new ArrayList<NameValuePair>();
		nameValuePairs_heart.add(new BasicNameValuePair("user_id",panmom_reg_id));
		nameValuePairs_heart.add(new BasicNameValuePair("current_date",call_calender()));
		
       	
      	try
         {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/heart_get.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs_heart));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is2 = entity.getContent();
              
         }
         catch(Exception e)
         {
        	 
         } 
         
         try
         {
         	BufferedReader reader = new BufferedReader(new InputStreamReader(is2,"iso-8859-1"),8);
         	StringBuilder sb = new StringBuilder();
         	String line ;
      
         	while ((line = reader.readLine()) != null) 
         	{
         		sb.append(line + "\n");
         	}
         	is2.close();
         	results=sb.toString();  
         }
         catch(Exception e)
         {
     	
         }
		         
         
          try
          {
	           	 
	             JSONArray jArray = new JSONArray(results);
	            
	             	int temp2 = 0;
	             	for(int i=0;i<jArray.length();i++)
	             	{
	             			JSONObject json_data = jArray.getJSONObject(i);
	             			temp2 = json_data.getInt("id");
	             					             			
                   		    System.out.println("iiiddddddddd   is ="+temp2);
	             	}
	            
	             	item_id=Integer.toString(temp2);
	             	System.out.println("retrived_item_id   is ="+item_id);
	            
	            
	       }
	      	catch(JSONException e)
	      	{
	           			//text1.setText("Invalid Login!!"); 
	      	}
		
		
		String strhtid1=cur.getString(0);
		//String strulogin1=cur.getString(1);
		String strdate1=cur.getString(2); 
		String strRHR1=cur.getString(3); 
		String strRHR_time1=cur.getString(4);
		String strNHR1=cur.getString(5);
		String strNHR_time1=cur.getString(6);
		String strEHR1=cur.getString(7);
		String strEHR_time1=cur.getString(8);
		//String strheart_sync1=cur.getString(9);
		
		
		        	
		
    	
    	//System.out.println("ULogin:"+strulogin1);
    	System.out.println("Date:"+strdate1);	        	
    	System.out.println("Resting heart rate:"+strRHR1);
    	System.out.println("resting time:"+strRHR_time1);
    	System.out.println("Normal heart rate:"+strNHR1);
    	System.out.println("Normal time:"+strNHR_time1);
    	System.out.println("Exertive heart rate:"+strEHR1);
    	System.out.println("Exertive time:"+strEHR_time1);
    	//System.out.println("Heart_sync:"+strheart_sync1);
    	
    	ArrayList<NameValuePair> nameValuePairs_heart1 = new ArrayList<NameValuePair>();
    	nameValuePairs_heart1.add(new BasicNameValuePair("item_id",item_id));
    	nameValuePairs_heart1.add(new BasicNameValuePair("pan_user_id",panmom_reg_id));
    	nameValuePairs_heart1.add(new BasicNameValuePair("ehr",strEHR1));
    	nameValuePairs_heart1.add(new BasicNameValuePair("nhr",strNHR1));
    	nameValuePairs_heart1.add(new BasicNameValuePair("rhr",strRHR1));
    	nameValuePairs_heart1.add(new BasicNameValuePair("rtime",strRHR_time1));
    	nameValuePairs_heart1.add(new BasicNameValuePair("ntime",strNHR_time1));
    	nameValuePairs_heart1.add(new BasicNameValuePair("etime",strEHR_time1));
    	nameValuePairs_heart1.add(new BasicNameValuePair("date",strdate1));
    	nameValuePairs_heart1.add(new BasicNameValuePair("current_date",call_calender()));
		
       	
      	try
         {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/heart_insert.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs_heart1));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is2 = entity.getContent();
              
         }
         catch(Exception e)
         {
        	 
         } 
         data.update_heart(Integer.parseInt(strulogin), Integer.parseInt(strhtid1), strdate1, strRHR1, strRHR_time1,strNHR1,strNHR_time1,strEHR1,strEHR_time1,"Y");
		}
	}
	cur.close();
	
}

/*Type :Function
name:getfood_log
return type:void
date:12-12-2011
purpose:get the food trackers data from local and syncs it to the server*/
public void getfood_log()
{
	Cursor cur=data.getFood_data(Integer.parseInt(strulogin));
	
	 
	

	while(cur.moveToNext())
	{
		String strfood_sync1=cur.getString(6);
		if(strfood_sync1.equals("N"))
		{
		ArrayList<NameValuePair> nameValuePairs_food = new ArrayList<NameValuePair>();
		nameValuePairs_food.add(new BasicNameValuePair("user_id",panmom_reg_id));
		nameValuePairs_food.add(new BasicNameValuePair("current_date",call_calender()));
		
       	
      	try
         {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/food_get.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs_food));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is2 = entity.getContent();
              
         }
         catch(Exception e)
         {
        	 
         } 
         
         try
         {
         	BufferedReader reader = new BufferedReader(new InputStreamReader(is2,"iso-8859-1"),8);
         	StringBuilder sb = new StringBuilder();
         	String line ;
      
         	while ((line = reader.readLine()) != null) 
         	{
         		sb.append(line + "\n");
         	}
         	is2.close();
         	results=sb.toString();  
         }
         catch(Exception e)
         {
     	
         }
		         
         
          try
          {
	           	 
	             JSONArray jArray = new JSONArray(results);
	            
	             	int temp2 = 0;
	             	for(int i=0;i<jArray.length();i++)
	             	{
	             			JSONObject json_data = jArray.getJSONObject(i);
	             			temp2 = json_data.getInt("id");
	             					             			
                   		    System.out.println("iiiddddddddd   is ="+temp2);
	             	}
	            
	             	item_id=Integer.toString(temp2);
	             	System.out.println("retrived_item_id   is ="+item_id);
	            
	            
	       }
	      	catch(JSONException e)
	      	{
	           			//text1.setText("Invalid Login!!"); 
	      	}
		
		
		String strfid1=cur.getString(0);
		//String strulogin1=cur.getString(1);
		String strdate1=cur.getString(2); 
		String strwhen1=cur.getString(3); 
		String strwht_u_eat1=cur.getString(4);
		String strhow_much1=cur.getString(5);
		//String strfood_sync1=cur.getString(6);

    	
    	
		
		
    	
    	
    	//System.out.println("ULogin:"+strulogin1);
    	System.out.println("Date:"+strdate1);
    	System.out.println("When:"+strwhen1);
    	System.out.println("What you eat:"+strwht_u_eat1);
    	System.out.println("How much:"+strhow_much1);
    	//System.out.println("Food sync:"+strfood_sync1);
    	
    	ArrayList<NameValuePair> nameValuePairs_food1 = new ArrayList<NameValuePair>();
    	nameValuePairs_food1.add(new BasicNameValuePair("item_id",item_id));
    	nameValuePairs_food1.add(new BasicNameValuePair("pan_user_id",panmom_reg_id));
    	nameValuePairs_food1.add(new BasicNameValuePair("date",strdate1));
    	nameValuePairs_food1.add(new BasicNameValuePair("time",strwhen1));
    	nameValuePairs_food1.add(new BasicNameValuePair("what_u_eat",strwht_u_eat1));
    	nameValuePairs_food1.add(new BasicNameValuePair("how_much",strhow_much1));
    	nameValuePairs_food1.add(new BasicNameValuePair("current_date",call_calender()));
		
       	
      	try
         {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/food_insert.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs_food1));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is2 = entity.getContent();
              
         }
         catch(Exception e)
         {
        	 
         } 
    	
         data.update_food(Integer.parseInt(strulogin),Integer.parseInt(strfid1),strdate1,strwhen1,strwht_u_eat1,strhow_much1,"Y");
		}
		
	}
	cur.close();
	
}

/*Type :Function
name:getglucose_log
return type:void
date:12-12-2011
purpose:get the glucose trackers data from local and syncs it to the server*/
public void getglucose_log()
{
	Cursor cur=data.getGlucose_data(Integer.parseInt(strulogin));
	
	while(cur.moveToNext()) 
	{
		String strglucose_sync1=cur.getString(6);
		if(strglucose_sync1.equals("N"))
		{
		ArrayList<NameValuePair> nameValuePairs_glucose = new ArrayList<NameValuePair>();
		nameValuePairs_glucose.add(new BasicNameValuePair("user_id",panmom_reg_id));
		nameValuePairs_glucose.add(new BasicNameValuePair("current_date",call_calender()));
		
       	
      	try
         {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/glucose_get.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs_glucose));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is2 = entity.getContent();
              
         }
         catch(Exception e)
         {
        	 
         } 
         
         try
         {
         	BufferedReader reader = new BufferedReader(new InputStreamReader(is2,"iso-8859-1"),8);
         	StringBuilder sb = new StringBuilder();
         	String line ;
      
         	while ((line = reader.readLine()) != null) 
         	{
         		sb.append(line + "\n");
         	}
         	is2.close();
         	results=sb.toString();  
         }
         catch(Exception e)
         {
     	
         }
		         
         
          try
          {
	           	 
	             JSONArray jArray = new JSONArray(results);
	            
	             	int temp2 = 0;
	             	for(int i=0;i<jArray.length();i++)
	             	{
	             			JSONObject json_data = jArray.getJSONObject(i);
	             			temp2 = json_data.getInt("id");
	             					             			
                   		    System.out.println("iiiddddddddd   is ="+temp2);
	             	}
	            
	             	item_id=Integer.toString(temp2);
	             	System.out.println("retrived_item_id   is ="+item_id);
	            
	            
	       }
	      	catch(JSONException e)
	      	{
	           			//text1.setText("Invalid Login!!"); 
	      	}
		
		String strgid1=cur.getString(0);
		//String strulogin1=cur.getString(1);
		String strdate1=cur.getString(2); 
		String strtime1=cur.getString(3); 
		String strvalue1=cur.getString(4);
		String strtest_condition1=cur.getString(5);
		//String strfood_sync1=cur.getString(6);

    	
    	
    	
    	
    	//System.out.println("ULogin:"+strulogin1);
    	System.out.println("Date:"+strdate1);
    	System.out.println("Time:"+strtime1);
    	System.out.println("Value:"+strvalue1);
    	System.out.println("Test condition:"+strtest_condition1);
    	//System.out.println("Glucose sync:"+strfood_sync1);
    	
    	
    	
    	ArrayList<NameValuePair> nameValuePairs_glucose1 = new ArrayList<NameValuePair>();
    	nameValuePairs_glucose1.add(new BasicNameValuePair("item_id",item_id));
    	nameValuePairs_glucose1.add(new BasicNameValuePair("date",strdate1));
    	nameValuePairs_glucose1.add(new BasicNameValuePair("time",strtime1));
    	nameValuePairs_glucose1.add(new BasicNameValuePair("value",strvalue1));
    	nameValuePairs_glucose1.add(new BasicNameValuePair("test_condition",strtest_condition1));
    	nameValuePairs_glucose1.add(new BasicNameValuePair("current_date",call_calender()));
    	nameValuePairs_glucose1.add(new BasicNameValuePair("pan_user_id",panmom_reg_id));
		
       	
      	try
         {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/glucose_insert.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs_glucose1));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is2 = entity.getContent();
              
         }
         catch(Exception e)
         {
        	 
         } 
    	
         data.update_glucose(Integer.parseInt(strulogin),Integer.parseInt(strgid1),strdate1,strtime1,strvalue1,strtest_condition1,"Y");
		}
	}
	cur.close();
	
}

/*Type :Function
name:gethb_log
return type:void
date:12-12-2011
purpose:get the HB trackers data from local and syncs it to the server*/
public void gethb_log()
{
	Cursor cur=data.getHB_data(Integer.parseInt(strulogin));
	
	 
	

	while(cur.moveToNext())
	{
		String strhb_sync1=cur.getString(5);
		if(strhb_sync1.equals("N"))
		{
		
		ArrayList<NameValuePair> nameValuePairs_hb = new ArrayList<NameValuePair>();
		nameValuePairs_hb.add(new BasicNameValuePair("user_id",panmom_reg_id));
		nameValuePairs_hb.add(new BasicNameValuePair("current_date",call_calender()));
		
       	
      	try
         {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/hb_get.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs_hb));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is2 = entity.getContent();
              
         }
         catch(Exception e)
         {
        	 
         } 
         
         try
         {
         	BufferedReader reader = new BufferedReader(new InputStreamReader(is2,"iso-8859-1"),8);
         	StringBuilder sb = new StringBuilder();
         	String line ;
      
         	while ((line = reader.readLine()) != null) 
         	{
         		sb.append(line + "\n");
         	}
         	is2.close();
         	results=sb.toString();  
         }
         catch(Exception e)
         {
     	
         }
		         
         
          try
          {
	           	 
	             JSONArray jArray = new JSONArray(results);
	            
	             	int temp2 = 0;
	             	for(int i=0;i<jArray.length();i++)
	             	{
	             			JSONObject json_data = jArray.getJSONObject(i);
	             			temp2 = json_data.getInt("id");
	             					             			
                   		    System.out.println("iiiddddddddd   is ="+temp2);
	             	}
	            
	             	item_id=Integer.toString(temp2);
	             	System.out.println("retrived_item_id   is ="+item_id);
	            
	            
	       }
	      	catch(JSONException e)
	      	{
	           			//text1.setText("Invalid Login!!"); 
	      	}
		
		
		String strhid1=cur.getString(0);
		//String strulogin1=cur.getString(1); 
		String strhemoglobin1=cur.getString(2);
		String strdate1=cur.getString(3); 
		String strtime1=cur.getString(4);
		//String strhb_sync1=cur.getString(5);
		
		
    	
    	
    	System.out.println("ULogin:"+strulogin);
    	System.out.println("HB Value:"+strhemoglobin1);
    	System.out.println("Date:"+strdate1);
    	System.out.println("Time:"+strtime1);
    	//System.out.println("Flag:"+strhb_sync1);
    	
    	ArrayList<NameValuePair> nameValuePairs_hb1 = new ArrayList<NameValuePair>();
		nameValuePairs_hb1.add(new BasicNameValuePair("item_id",item_id));
		nameValuePairs_hb1.add(new BasicNameValuePair("pan_user_id",panmom_reg_id));
		nameValuePairs_hb1.add(new BasicNameValuePair("date",strdate1));
		nameValuePairs_hb1.add(new BasicNameValuePair("time",strtime1));
		nameValuePairs_hb1.add(new BasicNameValuePair("hemoglobin",strhemoglobin1));
		nameValuePairs_hb1.add(new BasicNameValuePair("current_date",call_calender()));
		
       	
      	try
         {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/hb_insert.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs_hb1));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is2 = entity.getContent();
              
         }
         catch(Exception e)
         {
        	 
         } 
         data.update_HB(Integer.parseInt(strulogin),Integer.parseInt(strhid1),strhemoglobin1,strdate1,strtime1,"Y");
		}
	}
	cur.close();
	
}


}
