//ask the user to register to the app
package com.Pan_Mom_Home;

import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.InputFilter.LengthFilter;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register_user extends CustomWindow{
	
	boolean matchFound,bemail,bpass,flag,bname,buname,internet;
	EditText edtuserlogin,edtemailaddr,edtpassword,edtconfpassword,edtname;
	EditText edtlmp,edtedd,edtage,edtoccpself,edtoccphusband,edtmobself;
	EditText edtmobhusband,edtmobguardian;
	Button btnregister,btnhome;
	String  strurl,strid,struname1,strpass,strduedate,stroccupself,stroccuphus,strmobself,strmobhus,strmobguard;
	DatabaseHelper data;
	String username,test;
	TextView txtemail,txtuser,txtpass,txtconfpass,txtname,txtmsg;
	int mYear,mMonth,mDay,mHour,mMinute,msecond;
	String stime,current_date;
	String strdate,strulogin,stremail,strpwd,strname,strlmp,retrived_id="";
	String stredd,strage,strocc_s,strocc_h,strmo_s,strmo_h,strmo_g;
	InputStream is = null;
	InputStream is1 = null;
	InputStream is2 = null;
	String result =null;
	int temp1,intuserid;
	
      String getid,strulogin11,stremail11;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user); 
       

        data=new DatabaseHelper(this);  
  
        
        
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        
        mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		msecond=c.get(Calendar.SECOND);
		
		
		stime=mHour+":"+mMinute+":"+msecond;
        
        current_date=mYear+"-"+(mMonth+1)+"-"+mDay+" "+stime;

        
        edtuserlogin = (EditText) findViewById(R.id.userlogin);
        edtemailaddr = (EditText) findViewById(R.id.emailaddr);
        edtpassword= (EditText) findViewById(R.id.password);
        edtconfpassword= (EditText) findViewById(R.id.confpassword);
        edtname= (EditText) findViewById(R.id.name);
       
        edtage= (EditText) findViewById(R.id.age);
        edtoccpself= (EditText) findViewById(R.id.occpself);
        edtoccphusband= (EditText) findViewById(R.id.occphusband);
        edtmobself= (EditText) findViewById(R.id.mobself);
        edtmobhusband= (EditText) findViewById(R.id.mobhusband);
        edtmobguardian= (EditText) findViewById(R.id.mobguardian);
        txtemail=(TextView)findViewById(R.id.txtemail);
        txtuser=(TextView)findViewById(R.id.txtuser);
        txtpass=(TextView)findViewById(R.id.txtpass);
        txtconfpass=(TextView)findViewById(R.id.txtconfpass);
        txtname=(TextView)findViewById(R.id.txtname);
        txtmsg=(TextView)findViewById(R.id.txtmsg);
        btnregister=(Button) findViewById(R.id.register);
        
        
        
        final SpannableStringBuilder sb = new SpannableStringBuilder(" * Fields are Mandatory");
 	   	final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(255, 0, 0)); // Span to set text color to some RGB value
 	   	final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
 	   	sb.setSpan(fcs, 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // Set the text color for first 4 characters
 	   	sb.setSpan(bss, 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make them also bold	  
 	   	txtmsg.setText(sb);
       
       
       final SpannableStringBuilder sb1 = new SpannableStringBuilder("User Login  *");
	   final ForegroundColorSpan fcs1 = new ForegroundColorSpan(Color.rgb(255, 0, 0)); // Span to set text color to some RGB value
	   final StyleSpan bss1 = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
	   sb1.setSpan(fcs1, 12, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // Set the text color for first 4 characters
	   sb1.setSpan(bss1, 12, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make them also bold
	   txtuser.setText(sb1);
       
            
	   final SpannableStringBuilder sb2 = new SpannableStringBuilder("Email Address  *");
	   final ForegroundColorSpan fcs2 = new ForegroundColorSpan(Color.rgb(255, 0, 0)); // Span to set text color to some RGB value
	   final StyleSpan bss2 = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
	   sb2.setSpan(fcs2, 15, 16, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // Set the text color for first 4 characters
	   sb2.setSpan(bss2, 15, 16, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make them also bold
	   txtemail.setText(sb2);
       
       
	   final SpannableStringBuilder sb3 = new SpannableStringBuilder("Password  *");
	   final ForegroundColorSpan fcs3 = new ForegroundColorSpan(Color.rgb(255, 0, 0)); // Span to set text color to some RGB value
	   final StyleSpan bss3 = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
	   sb3.setSpan(fcs3, 10, 11, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // Set the text color for first 4 characters
	   sb3.setSpan(bss3, 10, 11, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make them also bold
	   txtpass.setText(sb3);
       
       
	   final SpannableStringBuilder sb4 = new SpannableStringBuilder("Confirm Password  *");
	   final ForegroundColorSpan fcs4 = new ForegroundColorSpan(Color.rgb(255, 0, 0)); // Span to set text color to some RGB value
	   final StyleSpan bss4 = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
	   sb4.setSpan(fcs4, 18, 19, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // Set the text color for first 4 characters
	   sb4.setSpan(bss4, 18, 19, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make them also bold
	   txtconfpass.setText(sb4);
       
       
	   final SpannableStringBuilder sb5 = new SpannableStringBuilder("Name  *");
	   final ForegroundColorSpan fcs5 = new ForegroundColorSpan(Color.rgb(255, 0, 0)); // Span to set text color to some RGB value
	   final StyleSpan bss5 = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
	   sb5.setSpan(fcs5, 6, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // Set the text color for first 4 characters
	   sb5.setSpan(bss5, 6, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make them also bold
	   txtname.setText(sb5);
       
      
        
        btnregister.setOnClickListener(new OnClickListener() {					
			public void onClick(View v) {		  
				internet=checkInternetConnection();
				if(internet==false)
				{
					//Toast.makeText(Register_user.this, "Please turn on Internet connection", Toast.LENGTH_LONG).show();
					}
				else{
					  
					username=edtuserlogin.getText().toString();
			        //getreg_info(username);     
			    	
			 /////       
			        Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");

			    	Matcher matcher = pattern.matcher( edtemailaddr.getText());

			    	 matchFound = matcher.matches();
			         
			    	   if(matchFound==false)
			    	   {
			    	        	//Toast.makeText(Register_user.this,"Invalid email",Toast.LENGTH_LONG).show();
			    	        	//edtpassword.setEnabled(false);
			    	        	 bemail=true;
			    	        	
			    	   }      
//////			    	   
			    	   String pass=edtpassword.getText().toString();
						String conf=edtconfpassword.getText().toString();
			          
			       	   if(!pass.equals(conf)|| conf=="")
			       	   { //Toast.makeText(Register_user.this,"Please check password",Toast.LENGTH_LONG).show();	
			                    bpass=true;     	  
			       	     
			       	   
			       	   }
			///////
			       	String name1=edtname.getText().toString(); 		
			 	   if(name1=="")
			 	   { //Toast.makeText(Register_user.this,"Please check name",Toast.LENGTH_LONG).show();	
			              bname=true;   	  
			 	
			 	   }
			    	   
			 //////       
			    	
			    	if(bname==true || buname==true || bpass==true || bemail==true)
			    	{
			    		String alert="Please check fields: ";
			    		if(buname==true){alert=alert+"User name ";}
			    		if(bemail==true){alert=alert+"Email Address ";}
			    		if(bpass==true){alert=alert+"Password ";}
			    		if(bname==true){alert=alert+"Name ";}
			    		
			    		AlertDialog alertDialog = new AlertDialog.Builder(
			            Register_user.this).create();
			            alertDialog.setTitle("Please Check Manadatory Fields");
			            alertDialog.setMessage(alert);
			            alertDialog.setIcon(R.drawable.uu);
			            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int which) {
			           
			            Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
			            bname=false; buname=false; bpass=false; bemail=false;
			            }
			            });
			             alertDialog.show();
			    	}
			    	else{
					
			    	   /* purpose:Inserting registration data to remotedb that is server*/	
					 strulogin11=edtuserlogin.getText().toString(); 
			    	 stremail11=edtemailaddr.getText().toString(); 
 			    	 ArrayList<NameValuePair> nameValuePairs23 = new ArrayList<NameValuePair>();
				       	nameValuePairs23.add(new BasicNameValuePair("username11",strulogin11));
				       	nameValuePairs23.add(new BasicNameValuePair("useremail11",stremail11));
				     	
				        try
				         {
				                HttpClient httpclient = new DefaultHttpClient();
				                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/register.php");
				                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs23));
				                HttpResponse response = httpclient.execute(httppost);
				                HttpEntity entity = response.getEntity();
				                is = entity.getContent();      
				              
				         }
				         catch(Exception e) 
				         { 
				        	 
				         }   
				       	
				         try
				         {
				         	BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
				         	StringBuilder sb = new StringBuilder();
				       
				         	String line ;
				      
				         	while ((line = reader.readLine())!= null) 
				         	{
				         		sb.append(line + "\n");
				         		//sb=line;
				         		System.out.println("line is :"+line);
				         	}
				         	is.close();
				         	result=sb.toString();
				         	System.out.println("result   :"+result);
				         }
				         catch(Exception e)
			 	         { 
				     	   
				         }
						
				        		// if(result.startsWith("null"))
			 	        			
						    if(result.startsWith("null"))
				        	  {
				        		  System.out.println("rrresult is : "+result);
				        		  Toast.makeText(Register_user.this,"New User",Toast.LENGTH_LONG).show();
				        		
				        		  insertreg();
				  				
				  				get_reg_log();
				  				 strurl="http://www.panmom.com/members/"+struname1;
				  				intuserid=Integer.parseInt(strid);
				  				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				  		       	nameValuePairs.add(new BasicNameValuePair("username",struname1));
				  		 		nameValuePairs.add(new BasicNameValuePair("password",strpass));
				  				nameValuePairs.add(new BasicNameValuePair("name",strname));
				  				nameValuePairs.add(new BasicNameValuePair("email",stremail));
				  				nameValuePairs.add(new BasicNameValuePair("url",strurl));
				  				nameValuePairs.add(new BasicNameValuePair("date",strdate));
				  				
				  			
				  		        System.out.println(".............."+strid+strdate+struname1+stremail+strpass+strage+strlmp+strduedate+strmobself+strmobguard);
				  		        try
				  		         {
				  		                HttpClient httpclient = new DefaultHttpClient();
				  		                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/pan.php");
				  		                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				  		                HttpResponse response = httpclient.execute(httppost);
				  		                HttpEntity entity = response.getEntity();
				  		                is = entity.getContent();
				  		              
				  		         }
				  		         catch(Exception e)
				  		         {
				  		        	 
				  		         }  
				  		         
				  		         try
				  		         {
				  		         	BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
				  		         	StringBuilder sb = new StringBuilder();
				  		         	String line ;
				  		      
				  		         	while ((line = reader.readLine()) != null) 
				  		         	{
				  		         		sb.append(line + "\n");
				  		         	}
				  		         	is.close();
				  		         	result=sb.toString();  
				  		         }
				  		         catch(Exception e)
				  		         {
				  		     	
				  		         }
				  				
				  		          try
				  		          {
				  			             
				  			             JSONArray jArray = new JSONArray(result);
				  			            
				  			            
				  			             	for(int i=0;i<jArray.length();i++)
				  			             	{
				  			             			JSONObject json_data = jArray.getJSONObject(i);
				  			             			temp1 = json_data.getInt("ID");
				  			             					             			
				  	                       		    System.out.println("iiiddddddddd   is ="+temp1);
				  			             	}
				  			            
				  			             	retrived_id=Integer.toString(temp1);
				  			             	System.out.println("retrived_id   is ="+retrived_id);
				  			            
				  			            
				  			       }
				  			      	catch(JSONException e)
				  			      	{
				  			           			//text1.setText("Invalid Login!!"); 
				  			      	}
				  			      	
				  			      	data.updatepanmomid(intuserid, Integer.parseInt(retrived_id));
				  			     
				  			    	
				  				   
				  			       ArrayList<NameValuePair> nameValuePairs12 = new ArrayList<NameValuePair>();
				  					nameValuePairs12.add(new BasicNameValuePair("user_id",retrived_id));
				  					nameValuePairs12.add(new BasicNameValuePair("username1",struname1));
				  					nameValuePairs12.add(new BasicNameValuePair("user_name",strname));
				  					nameValuePairs12.add(new BasicNameValuePair("date",strdate));
				  					System.out.println("in wp_usermeta  : "+retrived_id);
				  					System.out.println("in wp_usermeta  : "+struname1);
				  					System.out.println("in wp_usermeta  : "+strname);
				  					System.out.println("in wp_usermeta  : "+strdate);
				  					try
				  			         {
				  			                HttpClient httpclient = new DefaultHttpClient();
				  			                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/wp_usermeta.php");
				  			                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs12));
				  			                HttpResponse response = httpclient.execute(httppost);
				  			                HttpEntity entity = response.getEntity();
				  			                is2 = entity.getContent();
				  			              
				  			         }
				  			         catch(Exception e)
				  			         {
				  			        	 
				  			         }   
				  			      				  			         
				        		  }
				        	  else
				        	  {
				        		  
				        		  Toast.makeText(Register_user.this,"User already exists",Toast.LENGTH_LONG).show();
				      
				}
				}
				}
			}
		});                           
    }
    
    
    /*Type :Function
    name:insertreg
    return type:String
    date:12-12-2011
    purpose:Inserting registration data to localdb*/
    public void insertreg()
    {

    	 strulogin=edtuserlogin.getText().toString(); 
    	 stremail=edtemailaddr.getText().toString(); 
    	 strpwd=edtpassword.getText().toString();
    	 strname=edtname.getText().toString();
    	 strage=edtage.getText().toString();
    	 strocc_s=edtoccpself.getText().toString();
    	 strocc_h=edtoccphusband.getText().toString();
    	 strmo_s=edtmobself.getText().toString();
    	 strmo_h=edtmobhusband.getText().toString();
    	 strmo_g=edtmobguardian.getText().toString();
    	
    	 strpwd=md5(strpwd);
    	 
    	data.Insertregister(current_date, strulogin, stremail, strpwd, strname, null, null, strage, strocc_s, strocc_h, strmo_s, strmo_h, strmo_g, 0);
    	
    	Toast.makeText(this, "Registration Successfull!!!!",Toast.LENGTH_LONG).show();
    	    	
    	
    	Cursor cur=data. getreglogwith_email(stremail);
    	while(cur.moveToNext())
    	{
    		getid=cur.getString(0);
    		
    	  }
    	home();
    	
    }
    /*Type :Function
    name:md5
    return type:String
    date:12-12-2011
    purpose:Converting string to md5 hash*/
    public String md5(String s)
    {   	
			
    	String signature;
	    try 
	    {
	    	
	        // Create MD5 Hash
	        MessageDigest md5 = MessageDigest.getInstance("MD5");
	        md5.update(s.getBytes(),0,s.length());
	        signature = new BigInteger(1,md5.digest()).toString(16);
	        return signature;
	        
	    } 
	    catch (NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
		return "";
	    
	}

    public void home()
    {
    	Intent i=new Intent(Register_user.this, Pan_MomActivity.class);
    	startActivity(i);
    }
    /*Type :Function
    name:get_reg_log
    return type:void
    date:12-12-2011
    purpose:Fetching reg details from local db*/
    public void get_reg_log()
	{
		int r=Integer.parseInt(getid);
	    Cursor cur=data.getreg_info(r);
	    
		
        while(cur.moveToNext())
         {
	    strid=cur.getString(0);
	    strdate=cur.getString(1);
	    struname1=cur.getString(2);
	    stremail=cur.getString(3);
	    strpass=cur.getString(4);
	    strname=cur.getString(5);
	    strage=cur.getString(6);
	    strlmp=cur.getString(7);
	    strduedate=cur.getString(8);
	    stroccupself=cur.getString(9);
	    stroccuphus=cur.getString(10);
	    strmobself=cur.getString(11);
	    strmobhus=cur.getString(12);
	    strmobguard=cur.getString(13);
         }
		cur.close();
		
	}
    public void onClickHome (View v)
	{
	    goHome (this);
	}
    /*Type :Function
    name:goHome
    return type:void
    date:12-12-2011
    purpose:Calling Login screen*/
    public void goHome(Context context) 
	{
	    final Intent intent = new Intent(context, Pan_MomActivity.class);
	    intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    context.startActivity (intent);
	}
    /*Type :Function
    name:checkInternetConnection
    return type:void
    date:12-12-2011
    purpose:Checking whether internet connection is available or not*/
    private boolean checkInternetConnection() {
		   ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
		   if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() &&    conMgr.getActiveNetworkInfo().isConnected()) {
		         return true;
		   } else {
		         System.out.println("Internet Connection Not Present");
		       return false;
		   }
		}
}

