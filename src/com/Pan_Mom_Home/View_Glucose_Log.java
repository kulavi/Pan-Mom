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

public class View_Glucose_Log extends Activity {
	
	
	TableLayout tbl_glucose_log;
	Button btnedit,btndel;
	DatabaseHelper data;
	Pan_mom_session mysession;
	int[]  selected_index1;
	int selected_index;
	protected static boolean[] _selections;
	int count=0;
	//int [] index;
	String flag11="false";
	int intuserid,selected_id,flag;
	LinearLayout ll;
	String strulogin,struname;
	String[] strvalue,strdate,strtime,strtest_condition,strhb_sync,strgid;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.view_glucose_log);
		
		/*initializing classes*/
		data=new DatabaseHelper(this);
		mysession=new Pan_mom_session(this);
		/*inflating the views to use in this class*/
		btnedit=(Button)findViewById(R.id.btnedit);
		btndel=(Button)findViewById(R.id.btndel);
		tbl_glucose_log=(TableLayout) findViewById(R.id.tbl_glucose_log);
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

		getglucose_log(strulogin);
		
		/*TextView textView = new TextView(this);
		textView.setText("HB LOG");
		setContentView(textView);*/
		btnedit.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) 
			{
				
				for(int k=0;k<strgid.length;k++)
                {
                	System.out.println("Value at "+k+" is"+_selections[k]);
                }
				
				
				if(count>1)
				{
					Toast.makeText(View_Glucose_Log.this, "Please Select only one value to edit..", Toast.LENGTH_LONG).show();
				}
				else
				{
				int x=0;
				for(int k=0;k<strgid.length;k++)
                {
					System.out.println("Selected Checkbox id is..."+selected_index1[k]);
            		if(_selections[k]=true)
            		{
                	if(strgid[k].equals(Integer.toString(selected_index1[x])))
                	{
                		selected_index=k;
                		selected_id=Integer.parseInt(strgid[k]);
                		
                	}
            		}
                	x++;
                	
                }
				System.out.println("Finally Selected Checkbox id is..."+selected_id);
               
				
				
				flag=1;
				mysession.settracker_flag("viewglucose");
				Intent i=new Intent(View_Glucose_Log.this, Glucosetab.class);
				Bundle bun=new Bundle();
				bun.putString("UNAME",struname);
				bun.putInt("USERID", intuserid);	
				bun.putInt("Rowid", (selected_index+1));	
				bun.putInt("Flagid", flag);
				bun.putInt("Selectedid", selected_id);
				System.out.println("in view food..."+(selected_index+1));
				System.out.println("in view food..."+selected_id);
				i.putExtras(bun);
		    	startActivity(i);
				}
			 }
			
		});

		 btndel.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) 
				{
					
					int x=0;
					for(int k=0;k<strgid.length;k++)
	                {
						System.out.println("Selected Checkbox id is..."+selected_index1[k]);
	            		if(_selections[k]=true)
	            		{
	                	if(strgid[k].equals(Integer.toString(selected_index1[x])))
	                	{
	                		selected_index=k;
	                		selected_id=Integer.parseInt(strgid[k]);
	                		data.delGlucose_data(Integer.parseInt(strulogin),selected_id);
	                			                		
	                	}
	            		}
	                	x++;
	                	
	                }
					
					Toast.makeText(View_Glucose_Log.this,"Data deleted successfully",Toast.LENGTH_LONG).show();
			         
					tbl_glucose_log.removeAllViews();
					getglucose_log(strulogin);
				}
			});
	}
	/*Type :Function
    name:getglucose_log
    return type:void
    date:12-12-2011
    purpose:Gets glucose data from local db*/
	public void getglucose_log(String user_login_id)
	{
		Cursor cur=data.getGlucose_data(Integer.parseInt(strulogin));
		strgid=new String[cur.getCount()];
		strvalue=new String[cur.getCount()];
		strdate=new String[cur.getCount()];
		strtime=new String[cur.getCount()];
		strtest_condition=new String[cur.getCount()];
		
		 
		int i=0;

    	while(cur.moveToNext())
    	{
    		String strgid1=cur.getString(0);
    		//String strulogin1=cur.getString(1);
    		String strdate1=cur.getString(2); 
    		String strtime1=cur.getString(3); 
    		String strvalue1=cur.getString(4);
    		String strtest_condition1=cur.getString(5);
    		//String strfood_sync1=cur.getString(6);
    
        		
    		strgid[i]=strgid1;
    		strdate[i]=strdate1;
        	strtime[i]=strtime1;        	
        	strvalue[i]=strvalue1;
        	strtest_condition[i]=strtest_condition1;
        	
        	
        	
        	//System.out.println("ULogin:"+strulogin1);
        	System.out.println("Date:"+strdate[i]);
        	System.out.println("Time:"+strtime[i]);
        	System.out.println("Value:"+strvalue[i]);
        	System.out.println("Test condition:"+strtest_condition[i]);
        	//System.out.println("Glucose sync:"+strfood_sync1);
        	
        	i++;
        	
    		
    	}
    	cur.close();
    	display_glucose_log();
	}
	/*Type :Function
    name:display_glucose_log
    return type:void
    date:12-12-2011
    purpose:Dispaying fetched data in table format*/
	public void display_glucose_log() 
	 {
		
		int gidlen= strgid.length;
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
			  final TextView labeltimetitle = new TextView(this);
			          
			  
			  labeltimetitle.setText("Time"); 
			  labeltimetitle.setTextColor(Color.BLACK);
			  
			  labeltimetitle.setPadding(10, 0, 0, 0);
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labeltimetitle);  
			  
			//Create a TextView to house the name of the province 
			  final TextView labelvaluetitle = new TextView(this);
			          
			  
			  labelvaluetitle.setText("Value"); 
			  labelvaluetitle.setTextColor(Color.BLACK);
			  
			  labelvaluetitle.setPadding(10, 0, 0, 0);  
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelvaluetitle);
			  
			//Create a TextView to house the name of the province 
			  final TextView labeltctitle = new TextView(this);
			          
			  
			  labeltctitle.setText("Test Condition"); 
			  labeltctitle.setTextColor(Color.BLACK);
			  
			  labeltctitle.setPadding(10, 0, 0, 0);  
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labeltctitle);
			  
			
			    
			  
			  tbl_glucose_log.addView(tr1, new TableLayout.LayoutParams(
			          LayoutParams.WRAP_CONTENT,
			         LayoutParams.WRAP_CONTENT)); 

	
			 for(int i4=0;i4<strvalue.length;i4++) 
			  {  
		
				try 
				{
				
	            
	            final TableRow tr2 = new TableRow(this);  
	            tr2.layout(0, 0, 0, 0);
	            
	            
	                
	            
	            final CheckBox chk1 = new CheckBox(this);
	            
	            chk1.setPadding(10, 0, 0, 0);
	            int a=Integer.parseInt(strgid[i4]);
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
	            
	            final TextView labeltime= new TextView(this);
	             
            
	            
	            labeltime.setText(strtime[i4]);      
	           
	            labeltime.setTextColor(Color.BLACK);
	            
	            labeltime.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labeltime);
	            
	            final TextView labelvalue = new TextView(this);
	             
            
	            
	            labelvalue.setText(strvalue[i4]);      
	           
	            labelvalue.setTextColor(Color.BLACK);
	            
	            labelvalue.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelvalue);
	            
	            final TextView labeltc = new TextView(this);
	             
            
	            
	            labeltc.setText(strtest_condition[i4]);      
	           
	            labeltc.setTextColor(Color.BLACK);
	            
	            labeltc.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labeltc);
	            
	            //CheckBox ch=new CheckBox(this);
	            
	            
	    
	                          
	            tbl_glucose_log.addView(tr2, new TableLayout.LayoutParams(
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