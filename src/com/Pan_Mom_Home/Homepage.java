//Launcher screen for the app
package com.Pan_Mom_Home;



import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Homepage extends Activity 
{
Button ok;
Pan_mom_session mysession;
DatabaseHelper data;
ProgressBar bar;
TextView t1,t2,t3,t4;
int a;
String check_flag="false";
boolean isRunning=false;
int userid;
String username,pwd,strdue_date,return_flag,struserid;

@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainpage);
		
		/*inflating the views to use in this class*/
	    t1=(TextView)findViewById(R.id.t1);
	    t1.setTypeface(null, Typeface.ITALIC);

        bar=(ProgressBar)findViewById(R.id.ProgressBar01);
        
        /*initializing classes*/
        data=new DatabaseHelper(this);
        mysession=new Pan_mom_session(this);
          
        
        
     
	}  

//handler for the background updating
Handler handler=new Handler() 
{

public void handleMessage(Message msg) 
{
	
	bar.incrementProgressBy(10);
	a=bar.getProgress();
 	  
	if(a==100)
	{
  		 
  		 
  		 	
    	   if(mysession.getmainpage_flag().equals("true")){
    		   
    		   
    	    	fun();
    	   }
    	    else{
    	    	
    	    	Intent i=new Intent(Homepage.this, Pan_MomActivity.class);
    			startActivity(i);
    			finish();
    			
    		}  
    	 } 

 
}
};

public void onStart() {
	 super.onStart();
	 // reset the bar to the default value of 0
	 bar.setProgress(0);
	  // create a thread for updating the progress bar
	 Thread background=new Thread(new Runnable() {
	 public void run() {
	 try {
	 for (int i=0;i<10 && isRunning;i++) {
	 // wait 1000ms between each update
		
	 Thread.sleep(400);
	 handler.sendMessage(handler.obtainMessage());
	 
	 }
	 }
	 catch (Throwable t) {
	   }     }     });
	 isRunning=true;
	  // start the background thread
	 background.start();
	 }
	 public void onStop() {
	 super.onStop();
	 isRunning=false;
	 
	
	 }
	 /*Type :Function
	    name:fun
	    return type:void
	    date:12-12-2011
	    purpose:checking whether user has entered the due date or not and based on that calling respective intents*/
	 public void fun()
	 {
		 
		 
		 String myflag=mysession.getflag();
	        
	        System.out.println("FLAG IS...."+myflag);
	        
	        if(myflag.equals("true"))
	        {
	        	username= mysession.getUserName();
	        	
	        	pwd= mysession.getPwd(); 
	        	struserid=mysession.getUserId();
	        	userid=Integer.parseInt(struserid);
	        	return_flag=mysession.getreturnflag();
	        	System.out.println("IN MYFLAG!!!!!!!!!!!");
	        	
	        	if(username.equals(""))
	        	{
	        		Cursor c=data.check_due_date(username, pwd);
	        		while(c.moveToNext())
	    			{
	    				strdue_date=c.getString(0);
	    				System.out.println("DUE DATE IS..."+strdue_date);	
	    			}
	        		c.close();
	        	}
	        	else
	        	{
	            	Cursor c=data.check_due_date(username, pwd);
	            	while(c.moveToNext())
	    			{
	    				strdue_date=c.getString(0);
	    				System.out.println("DUE DATE IS..."+strdue_date);	
	    			}
	            	c.close();
	        	}

				
				
				try  
				{
					if(strdue_date.equalsIgnoreCase(null))
	    			{
						System.out.println("IN TRY IF!!!!!!!!!");
	      			}
					else
					{
						System.out.println("IN TRY ELSE!!!!!!!!!");
						Intent i=new Intent(Homepage.this, Dashboard.class);
						Bundle bun=new Bundle();
						bun.putString("UNAME",username);
						bun.putString("PWD",pwd);
						bun.putInt("USERID", userid);
						i.putExtras(bun);
		    			startActivity(i); 
		    			
						finish();
					}
				}
				catch (Exception e) {
					// TODO: handle exception
					System.out.println("IN CATCH!!!!!!!!!");
					Intent i=new Intent(Homepage.this, Enter_due_date.class);
					Bundle bun=new Bundle();
					bun.putString("UNAME",username);
					bun.putString("PWD",pwd);
					bun.putString("RETURNFLAG", return_flag);
					bun.putInt("USERID", userid);
					i.putExtras(bun);
	    			startActivity(i);
	    			finish();
	    			
				}
	        	
	        	
	        	
	        }
	 }
	
}
