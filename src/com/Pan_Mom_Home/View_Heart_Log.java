//Showing Heart Data in table format
package com.Pan_Mom_Home;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TableLayout.LayoutParams;

public class View_Heart_Log extends Activity {
	
	TableLayout tbl_heart_log;
	
	DatabaseHelper data;
	Pan_mom_session mysession;
	Button btnedit,btndel;
	int[]  selected_index1;
	int selected_index;
	protected static boolean[] _selections;
	int count=0;
	int intuserid,selected_id,flag;
	String strulogin,struname;
	LinearLayout ll;
	String[] strdate,strRHR,strRHR_time,strNHR,strNHR_time,strEHR,strEHR_time,strwt_sync,strhtid;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.view_heart_log);
		
		/*initializing classes*/
		data=new DatabaseHelper(this);
		mysession=new Pan_mom_session(this);
		/*inflating the views to use in this class*/
		btnedit=(Button)findViewById(R.id.btnedit);
		btndel=(Button)findViewById(R.id.btndel);
		tbl_heart_log=(TableLayout) findViewById(R.id.tbl_heart_log);
		ll=(LinearLayout)findViewById(R.id.footer1);
		ll.setVisibility(View.GONE);
		
		 /*getting data from session*/
		strulogin=mysession.getUserId();
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
	        	
	        }

		getheart_log(strulogin);
		
		btnedit.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) 
			{
				for(int k=0;k<strhtid.length;k++)
                {
                	System.out.println("Value at "+k+" is"+_selections[k]);
                }
				
				
				if(count>1)
				{
					Toast.makeText(View_Heart_Log.this, "Please Select only one value to edit..", Toast.LENGTH_LONG).show();
				}
				else
				{
				int x=0;
				for(int k=0;k<strhtid.length;k++)
                {
					System.out.println("Selected Checkbox id is..."+selected_index1[k]);
            		if(_selections[k]=true)
            		{
                	if(strhtid[k].equals(Integer.toString(selected_index1[x])))
                	{
                		selected_index=k;
                		selected_id=Integer.parseInt(strhtid[k]);
                		
                	}
            		}
                	x++;
                	
                }
				System.out.println("Finally Selected Checkbox id is..."+selected_id);
				flag=1;
				mysession.settracker_flag("viewheart");
				Intent i=new Intent(View_Heart_Log.this, Hearttab.class);
				Bundle bun=new Bundle();
				bun.putString("UNAME",struname);
				bun.putInt("USERID", intuserid);	
				bun.putInt("Rowid", (selected_index+1));	
				bun.putInt("Selectedid", selected_id);
				bun.putInt("Flagid", flag);
				System.out.println("in view food..."+(selected_index+1));
				i.putExtras(bun);
		    	startActivity(i);
				}
			}
		});

		btndel.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) 
			{
				int x=0;
				for(int k=0;k<strhtid.length;k++)
                {
					System.out.println("Selected Checkbox id is..."+selected_index1[k]);
            		if(_selections[k]=true)
            		{
                	if(strhtid[k].equals(Integer.toString(selected_index1[x])))
                	{
                		selected_index=k;
                		selected_id=Integer.parseInt(strhtid[k]);
                		data.delHeart_data(Integer.parseInt(strulogin),selected_id);
                			                		
                	}
            		}
                	x++;
                	
                }
				
				Toast.makeText(View_Heart_Log.this,"Data deleted successfully",Toast.LENGTH_LONG).show();
		        tbl_heart_log.removeAllViews();
				getheart_log(strulogin);
			}
		});
	}
	/*Type :Function
    name:getheart_log
    return type:void
    date:12-12-2011
    purpose:Gets heart data from local db*/
	public void getheart_log(String user_login_id)
	{
		Cursor cur=data.getHeart_data(Integer.parseInt(strulogin));
		
		strdate=new String[cur.getCount()];
		strRHR=new String[cur.getCount()];
		strRHR_time=new String[cur.getCount()];
		strNHR=new String[cur.getCount()];
		strNHR_time=new String[cur.getCount()];
		strEHR=new String[cur.getCount()];
		strEHR_time=new String[cur.getCount()];
		strhtid=new String[cur.getCount()];
		 
		int i=0;

    	while(cur.moveToNext())
    	{
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
    		
    		
    		        	
    		strhtid[i]=strhtid1;
        	strdate[i]=strdate1;
        	strRHR[i]=strRHR1;
        	strRHR_time[i]=strRHR_time1;
        	strNHR[i]=strNHR1;
        	strNHR_time[i]=strNHR_time1;
        	strEHR[i]=strEHR1;
        	strEHR_time[i]=strEHR_time1;
        	
        	//System.out.println("ULogin:"+strulogin1);
        	System.out.println("Date:"+strdate[i]);	        	
        	System.out.println("Resting heart rate:"+strRHR[i]);
        	System.out.println("resting time:"+strRHR_time[i]);
        	System.out.println("Normal heart rate:"+strNHR[i]);
        	System.out.println("Normal time:"+strNHR_time[i]);
        	System.out.println("Exertive heart rate:"+strEHR[i]);
        	System.out.println("Exertive time:"+strEHR_time[i]);
        	//System.out.println("Heart_sync:"+strheart_sync1);
        	
        	i++;
        	
    		
    	}
    	cur.close();
    	display_heart_log();
	}
	/*Type :Function
    name:display_heart_log
    return type:void
    date:12-12-2011
    purpose:Dispaying fetched data in table format*/
	public void display_heart_log() 
	 {
		int gidlen= strhtid.length;
    	_selections=new boolean[gidlen];
		selected_index1=new int[gidlen];
		
		  	final TableRow tr1 = new TableRow(this); 
			tr1.layout(0, 0, 0, 0); 
			
			final CheckBox chk = new CheckBox(this);
           
			chk.setPadding(10, 0, 0, 0);
			tr1.setPadding(0, 1, 0, 1);
			chk.setId(0);
            tr1.addView(chk);
		
			//Create a TextView to house the name of the province 
			  final TextView labeldatetitle = new TextView(this);
			  
			  labeldatetitle.setText("Date"); 
			  labeldatetitle.setTextColor(Color.BLACK);
			  
			  labeldatetitle.setPadding(10, 0, 0, 0);
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labeldatetitle);
			  
			//Create a TextView to house the name of the province 
			  final TextView labelRHRtitle = new TextView(this);
			  
			  labelRHRtitle.setText("RHR"); 
			  labelRHRtitle.setTextColor(Color.BLACK);
			  
			  labelRHRtitle.setPadding(10, 0, 0, 0);
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelRHRtitle);
			  
			//Create a TextView to house the name of the province 
			  final TextView labelRHR_timetitle = new TextView(this);
			          
			  
			  labelRHR_timetitle.setText("Time"); 
			  labelRHR_timetitle.setTextColor(Color.BLACK);
			  
			  labelRHR_timetitle.setPadding(10, 0, 0, 0);
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelRHR_timetitle);  
			  
			//Create a TextView to house the name of the province 
			  final TextView labelNHRtitle = new TextView(this);
			          
			  
			  labelNHRtitle.setText("NHR"); 
			  labelNHRtitle.setTextColor(Color.BLACK);
			  
			  labelNHRtitle.setPadding(10, 0, 0, 0);  
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelNHRtitle);
			  
			//Create a TextView to house the name of the province 
			  final TextView labelNHR_timetitle = new TextView(this);
			           
			  
			  labelNHR_timetitle.setText("Time"); 
			  labelNHR_timetitle.setTextColor(Color.BLACK);
			  
			  labelNHR_timetitle.setPadding(10, 0, 0, 0);
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelNHR_timetitle);
			  
			//Create a TextView to house the name of the province 
			  final TextView labelEHRtitle = new TextView(this);
			           
			  
			  labelEHRtitle.setText("EHR"); 
			  labelEHRtitle.setTextColor(Color.BLACK);
			  
			  labelEHRtitle.setPadding(10, 0, 0, 0);
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelEHRtitle);
			  
			//Create a TextView to house the name of the province 
			  final TextView labelEHR_timetitle = new TextView(this);
			           
			  
			  labelEHR_timetitle.setText("Time"); 
			  labelEHR_timetitle.setTextColor(Color.BLACK);
			  
			  labelEHR_timetitle.setPadding(10, 0, 0, 0);
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelEHR_timetitle);
			    
			  
			  tbl_heart_log.addView(tr1, new TableLayout.LayoutParams(
			          LayoutParams.WRAP_CONTENT,
			         LayoutParams.WRAP_CONTENT)); 

	
			 for(int i4=0;i4<strRHR.length;i4++) 
			  {  
		
				try 
				{
				
	            
	            final TableRow tr2 = new TableRow(this);  
	            tr2.layout(0, 0, 0, 0);
	            
	            
	                
	            
	            final CheckBox chk1 = new CheckBox(this);
	            
	            chk1.setPadding(10, 0, 0, 0);
	            int a=Integer.parseInt(strhtid[i4]);
	            System.out.println("value of a issssssssssss :"+a);
	            chk1.setId(a);
	            chk1.setTag(i4+1);
				tr2.setPadding(0, 1, 0, 1);
	              
	            tr2.addView(chk1);
	            
	            
	        	
	            
	            final TextView labeldate = new TextView(this);
	             
	               
	              
	            labeldate.setText(strdate[i4]);      
	             
	            labeldate.setTextColor(Color.BLACK);
	            
	            labeldate.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labeldate);
	            
	            final TextView labelRHR = new TextView(this);
	             
            
	            
	            labelRHR.setText(strRHR[i4]);      
	           
	            labelRHR.setTextColor(Color.BLACK);
	            
	            labelRHR.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelRHR);
	            
	            final TextView labelRHR_time = new TextView(this);
	             
            
	            
	            labelRHR_time.setText(strRHR_time[i4]);      
	           
	            labelRHR_time.setTextColor(Color.BLACK);
	            
	            labelRHR_time.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelRHR_time);
	            
	            
	            
	            
	            final TextView labelNHR = new TextView(this);
	             
	               
	            
	            labelNHR.setText(strNHR[i4]);       
	           
	            labelNHR.setTextColor(Color.BLACK);
	            
	            labelNHR.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelNHR);
	            
	            final TextView labelNHR_time = new TextView(this);
	             
	               
	            
	            labelNHR_time.setText(strNHR_time[i4]);       
	           
	            labelNHR_time.setTextColor(Color.BLACK);
	            
	            labelNHR_time.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelNHR_time);
	            
	            final TextView labelEHR = new TextView(this);
	             
	               
	            
	            labelEHR.setText(strEHR[i4]);       
	           
	            labelEHR.setTextColor(Color.BLACK);
	            
	            labelEHR.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelEHR);
	            
	            final TextView labelEHR_time = new TextView(this);
	             
	               
	            
	            labelEHR_time.setText(strEHR_time[i4]);       
	           
	            labelEHR_time.setTextColor(Color.BLACK);
	            
	            labelEHR_time.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelEHR_time);
	            
	            
	            
	            
	            
	            
              // Add the TableRow to the TableLayout 
	                          
	            tbl_heart_log.addView(tr2, new TableLayout.LayoutParams(
	                     LayoutParams.WRAP_CONTENT,
	                    LayoutParams.WRAP_CONTENT));
	            
	            
	          chk1.setOnCheckedChangeListener(new OnCheckedChangeListener()
	            {
	            	

					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	                {
						if (isChecked )
	                    {
	                    	
	                    	 
							count++; 
							
							if(count>0)
							{
								ll.setVisibility(View.VISIBLE);
							}
													
							System.out.println(chk1.getId()+" is checked");
	                        
							_selections[(Integer.parseInt(chk1.getTag().toString())-1)]=true;
	                        
							selected_index1[(Integer.parseInt(chk1.getTag().toString())-1)]=chk1.getId();
	                       
	                    	
	                    	
	                    	
	                    	
	                    	
	                    }
	                    else
	                    {
	                    	
	                    	count--;
						                
	                    	
	                    	_selections[(Integer.parseInt(chk1.getTag().toString())-1)]=false;
	                    	
	                    	selected_index1[(Integer.parseInt(chk1.getTag().toString())-1)]=0;
	                        
	    					
	                      
	                    	System.out.println(chk1.getId()+" is unchecked");
                        
                        
						}

	                }

					
	            });
           
	            
	                     
	              
				}
				catch (Exception e)    
		        {
		         
		        System.out.println("Error msg:::::::::"+e.getMessage());
		        }
			}
	 }
	
	//overriding devices back button
	@Override
	public void onBackPressed()
	{
		System.out.println("BACK BUTTON PRESSED!!!!");
	}

}
