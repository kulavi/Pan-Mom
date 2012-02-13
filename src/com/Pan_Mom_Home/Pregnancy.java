//Dashboard Screen
package com.Pan_Mom_Home;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Pregnancy extends Activity {

	Button btntracker,btncalendar,btnsymptom,btndrinfo;
	SeekBar seekbar;
	LinearLayout ll,ll1;
	ImageButton home;
	
	ImageView image;
	TextView due,conceived;
	Pan_mom_session mysession;
	DatabaseHelper data;
	
	final public static int REQ_CODE=1;
	String struname,strpwd,struserid,strlmp,strduedate,strcurrent_date;
	int intuserid;
	int seek_max=0,seek_progress=0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.pregnancy);
		
		/*initializing classes*/
		data=new DatabaseHelper(this);
		mysession=new Pan_mom_session(this);
	
		/*getting data from session*/
		struname=mysession.getUserName();
        struserid=mysession.getUserId();
        strpwd=mysession.getPwd();
        /*if there are no values in the session then getting data from previous activity*/
        if(struserid.equals(""))
        {
        	System.out.println("SAVED USER ID IS IN IF...."+struserid);
        	if(struname.equals(""))
	        {
        		Bundle bun=getIntent().getExtras();
                struname=bun.getString("UNAME");
                System.out.println("PREGNANCY:UNAME:"+struname);
                strpwd=bun.getString("PWD");
                intuserid=bun.getInt("USERID");
	        }
        }
        else
        {
        intuserid=Integer.parseInt(struserid);
        System.out.println("SAVED USER ID IS IN ELSE...."+intuserid);
        }  
		
        /*inflating the views to use in this class*/
		seekbar = (SeekBar) findViewById(R.id.seekbar);
		btntracker=(Button) findViewById(R.id.btntrackar);
		
		btnsymptom=(Button) findViewById(R.id.btnsymptom);
		btndrinfo=(Button) findViewById(R.id.btndrinfo);
		due=(TextView)findViewById(R.id.due);
		conceived=(TextView)findViewById(R.id.conceived);
		
		//Setting progress to seek bar
		view_seekbar();
		seekbar.setMax(seek_max);
        seekbar.setProgress(seek_progress);
		
		

		       
        seekbar.setOnSeekBarChangeListener( new OnSeekBarChangeListener() {

            int originalProgress;

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            public void onStartTrackingTouch(SeekBar seekBar) {

                originalProgress = seekBar.getProgress();
            }

            public void onProgressChanged(SeekBar seekBar, int arg1, boolean fromUser) {
                if( fromUser == true){
                    seekBar.setProgress( originalProgress);
                }               
            }
        });
		
		
		btntracker.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i=new Intent(Pregnancy.this, MyTrackers.class);
				Bundle bun=new Bundle();
		        bun.putString("UNAME", struname);
		        bun.putInt("USERID", intuserid);
		        i.putExtras(bun);
				startActivity(i);
				
				
				
			}
		});
		
		btndrinfo.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i=new Intent(Pregnancy.this, Doctor_Info.class);
				Bundle bun=new Bundle();
		        bun.putString("UNAME", struname);
		        bun.putInt("USERID", intuserid);
		        i.putExtras(bun);  
				startActivity(i);
				
				
				
			}
		});
		
       btnsymptom.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i=new Intent(Pregnancy.this, Symptom.class);
				Bundle bun=new Bundle();
		        bun.putString("UNAME", struname);
		        bun.putInt("USERID", intuserid);
		        i.putExtras(bun);  
				startActivity(i);
				
				
				
			}
		});
       
	
}
	
	//Creating some menus and menu items for the screen
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            
            
            case R.id.setting:
            	
            	Intent i=new Intent(Pregnancy.this,Setting.class);
            	Bundle bun=new Bundle();
        		bun.putString("UNAME",struname);
        		bun.putString("PWD",strpwd);
        		bun.putInt("USERID", intuserid);
        		i.putExtras(bun);
            	startActivity(i);             
            	
            	break;
            case R.id.sync:     
                
            	Intent i1=new Intent(Pregnancy.this,Sync.class);
            	Bundle bun1=new Bundle();
        		bun1.putString("UNAME",struname);
        		bun1.putString("PWD",strpwd);
        		bun1.putInt("USERID", intuserid);
        		i1.putExtras(bun1);
            	startActivityForResult(i1, REQ_CODE);
            	break;
        }
        return true;
    }
    /*Type :Function
    name:view_seekbar
    return type:void
    date:12-12-2011
    purpose:Calulating the difference between due date and LMp 
    and LMP and current date and showing progress on seek bar accordongly*/
    public void view_seekbar()
    {
    	get_lmp_and_due_date_from_db();
    	
    	Calendar c = Calendar.getInstance();  
        int mYear = c.get(Calendar.YEAR);
        int mMonth= c.get(Calendar.MONTH);
        int mDay= c.get(Calendar.DAY_OF_MONTH);
        
        
        String current_date=mDay+"-"+(mMonth+1)+"-"+mYear;
        
        
        System.out.println("LMP FROM DB IS:"+strlmp);
		System.out.println("DUE DATE FROM DB IS:"+strduedate);
        System.out.println("CURRENT DATE IS:"+current_date);
    	
    	
		 String [] dts_lmp=strlmp.split("-");
		 String[] dts_duedate=strduedate.split("-");
		 String[] dts_current=current_date.split("-");
		 
		 //LMP
		 int day=Integer.parseInt(dts_lmp[0].trim());  
		 int mnth=Integer.parseInt(dts_lmp[1].trim());
		 int year=Integer.parseInt(dts_lmp[2].trim());
		 
		 conceived.setText("Conceived date:\n"+day+"-"+mnth+"-"+year);
		 
		 System.out.println("day is :"+day);
		 System.out.println("month is:"+mnth);
		 System.out.println("year is :"+year);
		 
		 Calendar thatDay_lmp = Calendar.getInstance();
		 thatDay_lmp.set(year, mnth, day);
		  
		 
		 //DUE DATE
		  int day1=Integer.parseInt(dts_duedate[0].trim());
		  int mnth1=Integer.parseInt(dts_duedate[1].trim());
		  int year1=Integer.parseInt(dts_duedate[2].trim());
		  
		  due.setText("Due Date :\n"+day1+"-"+mnth1+"-"+year1);
		  
		  Calendar thatDay_duedate = Calendar.getInstance();
		  thatDay_duedate.set(year1, mnth1,day1);
		  
		
		  System.out.println("day is 1:"+day1);
		  System.out.println("month is 1:"+mnth1);
		  System.out.println("year is 1:"+year1);
		  
		          
          
          //CURRENT DATE
          int mnth2=Integer.parseInt(dts_current[0].trim());
		  int day2=Integer.parseInt(dts_current[1].trim());
		  int year2=Integer.parseInt(dts_current[2].trim());
          
          
          Calendar thatday_current = Calendar.getInstance();
          thatday_current.set(year2,day2,mnth2);
		  
          System.out.println("day is 2:"+mnth2);
		  System.out.println("month is 2:"+day2);
		  System.out.println("year is 2:"+year2);
          
		
		  long diff_lmp_and_due_date=thatDay_duedate.getTimeInMillis()-thatDay_lmp.getTimeInMillis();
		  long days = diff_lmp_and_due_date / (24 * 60 * 60 * 1000);
		  
		  
		  System.out.println("lmp and due date difference is :"+days);
          
        
		  long diff_lmp_and_current_date=thatday_current.getTimeInMillis()-thatDay_lmp.getTimeInMillis();
		  long days1 = diff_lmp_and_current_date / (24 * 60 * 60 * 1000);
		 
		  
		  System.out.println("lmp and current date difference is :"+days1);
		  
		  seek_max=(int) days;
		  seek_progress=(int) days1;

		 
		 
 
    	
    }
    /*Type :Function
    name:get_lmp_and_due_date_from_db
    return type:void
    date:12-12-2011
    purpose:Fetching due date from local db*/
    public void get_lmp_and_due_date_from_db()
    {
    	Cursor cur=data.getreg_info(intuserid);
    	
    	while(cur.moveToNext())
    	{
    		 strlmp=cur.getString(6);
    		 strduedate=cur.getString(7);
    		  
    	}
    	cur.close();
    	
    }
    //overriding devices back button
    @Override
	public void onBackPressed()
	{  
    
    	finish();
    	
		System.out.println("BACK BUTTON PRESSED!!!!");
	}
}