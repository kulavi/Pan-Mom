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

public class View_Activity_Log extends Activity {
	
	TableLayout tbl_exercise_log,tbl_walking_log;
	TextView txtexercise,txtwalking;
	Button btnedit,btndel;
	LinearLayout ll;
	DatabaseHelper data;
	Pan_mom_session mysession;
	
	int[]  selected_index1;
	int selected_index;
	protected static boolean[] _selections;
	int count=0;
	int intuserid,selected_id,flag;
	String strulogin,struname;
	String[] strid,strdate,stretime,strwtime,strecalories,strwcalories,strdistance,strwt_sync;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.view_activity_log);
		
		data=new DatabaseHelper(this);
		mysession=new Pan_mom_session(this);
		
		tbl_exercise_log=(TableLayout) findViewById(R.id.tbl_exercise_log);
		tbl_walking_log=(TableLayout) findViewById(R.id.tbl_walking_log);
		
		txtexercise=(TextView) findViewById(R.id.txtexercise);
		txtwalking=(TextView) findViewById(R.id.txtwalking);
		btnedit=(Button)findViewById(R.id.btnedit);
		btndel=(Button)findViewById(R.id.btndel);
	   ll=(LinearLayout)findViewById(R.id.footer1);
		// btndel.setVisibility(View.GONE);
	   // btnedit.setVisibility(View.GONE);
		ll.setVisibility(View.GONE);
		strulogin=mysession.getUserId();
		
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

		getactivity_log(strulogin);
		
		btnedit.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) 
			{
				for(int k=0;k<strid.length;k++)
                {
                	System.out.println("Value at "+k+" is"+_selections[k]);
                }
				
				
				if(count>1)
				{
					Toast.makeText(View_Activity_Log.this, "Please Select only one value to edit..", Toast.LENGTH_LONG).show();
				}
				else
				{
				int x=0;
				for(int k=0;k<strid.length;k++)
                {
					System.out.println("Selected Checkbox id is..."+selected_index1[k]);
            		if(_selections[k]=true)
            		{
                	if(strid[k].equals(Integer.toString(selected_index1[x])))
                	{
                		selected_index=k;
                		selected_id=Integer.parseInt(strid[k]);
                		
                	}
            		}
                	x++;
                	
                }
				System.out.println("Finally Selected Checkbox id is..."+selected_id);
				flag=1;
				mysession.settracker_flag("viewactivity");
				Intent i=new Intent(View_Activity_Log.this, Activitytab.class);
				Bundle bun=new Bundle();
				bun.putString("UNAME",struname);
				bun.putInt("USERID", intuserid);	
				bun.putInt("Rowid", (selected_index+1));	
				bun.putInt("Flagid", flag);
				bun.putInt("Selectedid", selected_id);
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
				for(int k=0;k<strid.length;k++)
                {
					System.out.println("Selected Checkbox id is..."+selected_index1[k]);
            		if(_selections[k]=true)
            		{
                	if(strid[k].equals(Integer.toString(selected_index1[x])))
                	{
                		selected_index=k;
                		selected_id=Integer.parseInt(strid[k]);
                		data.delActivity_data(Integer.parseInt(strulogin),selected_id);
                			                		
                	}
            		}
                	x++;
                	
                }
				
				Toast.makeText(View_Activity_Log.this,"Data deleted successfully",Toast.LENGTH_LONG).show();
				tbl_exercise_log.removeAllViews();
				getactivity_log(strulogin);
			
			}
		});
	}
	
	public void getactivity_log(String user_login_id)
	{
		Cursor cur=data.getActivity_data(Integer.parseInt(strulogin));
		
		strid=new String[cur.getCount()];
		strdate=new String[cur.getCount()];
		stretime=new String[cur.getCount()];
		strwtime=new String[cur.getCount()];
		strecalories=new String[cur.getCount()];
		strwcalories=new String[cur.getCount()];
		strdistance=new String[cur.getCount()];
		strwt_sync=new String[cur.getCount()];
		 
		int i=0;

    	while(cur.moveToNext())
    	{
    		
    		String strid1=cur.getString(0);
    		//String strulogin1=cur.getString(1); 
    		String strdate1=cur.getString(2);
    		String stretime1=cur.getString(3); 
    		String strecalori1=cur.getString(4);
    		String strwtime1=cur.getString(5);
    		String strwcalori1=cur.getString(6);
    		String strdistance1=cur.getString(7);
    		//String strbp_sync1=cur.getString(8);
    		    		
    		        	
    		strid[i]=strid1;
        	strdate[i]=strdate1;
        	stretime[i]=stretime1;
        	strwtime[i]=strwtime1;
        	strecalories[i]=strecalori1;
        	strwcalories[i]=strwcalori1;
        	strdistance[i]=strdistance1;
        	
        	
        	System.out.println("ID IS:"+strid[i]);
        	//System.out.println("ULogin:"+strulogin1);
        	System.out.println("Date:"+strdate[i]);
        	System.out.println("Time:"+stretime[i]);
        	System.out.println("Systolic:"+strwtime[i]);
        	System.out.println("Diastolic:"+strecalories[i]);
        	System.out.println("Pulse Count:"+strwcalories[i]);
        	System.out.println("Hand:"+strdistance[i]);
        	//System.out.println("Flag:"+strbp_sync1);
        	
        	i++;
        	
    		
    	}
    	cur.close();
    	
    	//txtexercise.setText("Exercise");
    	display_exercise_log();
    	//txtwalking.setText("Walking");
    	
	}
	
	public void display_exercise_log() 
	 {
		
		int gidlen= strid.length;
    	_selections=new boolean[gidlen];
		selected_index1=new int[gidlen];
   		
		  	final TableRow tr1 = new TableRow(this); 
			tr1.layout(0, 0, 0, 0); 
			
			final CheckBox chk = new CheckBox(this);
           
			chk.setPadding(10, 0, 0, 0);
			tr1.setPadding(0, 1, 0, 1);
			
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
			  final TextView labelsystitle = new TextView(this);
			          
			  
			  labelsystitle.setText("Calories Burned"); 
			  labelsystitle.setTextColor(Color.BLACK);
			  
			  labelsystitle.setPadding(10, 0, 0, 0);
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelsystitle);  
			  
			  
			//Create a TextView to house the name of the province 
			  final TextView labelwaltime = new TextView(this);
			          
			  
			  labelwaltime.setText("Walking time"); 
			  labelwaltime.setTextColor(Color.BLACK);
			  
			  labelwaltime.setPadding(10, 0, 0, 0);
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelwaltime);  
			  
			  
			//Create a TextView to house the name of the province 
			  final TextView labelwalcalburned = new TextView(this);
			          
			  
			  labelwalcalburned.setText("Calories Burned"); 
			  labelwalcalburned.setTextColor(Color.BLACK);
			  
			  labelwalcalburned.setPadding(10, 0, 0, 0);
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelwalcalburned);  
			  
			//Create a TextView to house the name of the province 
			  final TextView labelwaldistance = new TextView(this);
			          
			  
			  labelwaldistance.setText("Distance Walked"); 
			  labelwaldistance.setTextColor(Color.BLACK);
			  
			  labelwaldistance.setPadding(10, 0, 0, 0);
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelwaldistance);  
			  
			  
			
			    
			  
			  tbl_exercise_log.addView(tr1, new TableLayout.LayoutParams(
			          LayoutParams.WRAP_CONTENT,
			         LayoutParams.WRAP_CONTENT)); 

	
			 for(int i4=0;i4<stretime.length;i4++) 
			  {  
		
				try 
				{
				
	            
	            final TableRow tr2 = new TableRow(this);  
	            tr2.layout(0, 0, 0, 0);
	            
	            
	                
	            
	            final CheckBox chk1 = new CheckBox(this);
	            
	            chk1.setPadding(10, 0, 0, 0);
	            chk1.setId(Integer.parseInt(strid[i4]));
	            chk1.setTag(i4+1);
				tr2.setPadding(0, 1, 0, 1);
	              
	            tr2.addView(chk1);
	            
	            
	        	
	            
	            final TextView labeldate = new TextView(this);
	             
	               
	              
	            labeldate.setText(strdate[i4]);      
	             
	            labeldate.setTextColor(Color.BLACK);
	            
	            labeldate.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labeldate);
	            
	            final TextView labeletime = new TextView(this);
	             
            
	            
	            labeletime.setText(stretime[i4]);      
	           
	            labeletime.setTextColor(Color.BLACK);
	            
	            labeletime.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labeletime);
	            
	            final TextView labelecalories = new TextView(this);
	             
            
	            
	            labelecalories.setText(strecalories[i4]);      
	           
	            labelecalories.setTextColor(Color.BLACK);
	            
	            labelecalories.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelecalories);
	            
	            
               final TextView labelwaltime1 = new TextView(this);
	             
            
	            
               labelwaltime1.setText(strwtime[i4]);      
	           
               labelwaltime1.setTextColor(Color.BLACK);
	            
               labelwaltime1.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelwaltime1);
	            
	            
	            
	            final TextView labelwalcal= new TextView(this);
	             
	            
	            
	            labelwalcal.setText(strwcalories[i4]);      
		           
	            labelwalcal.setTextColor(Color.BLACK);
		            
	            labelwalcal.setPadding(10, 0, 0, 0);
					tr2.setPadding(0, 1, 0, 1);
		            
		            tr2.addView(labelwalcal);
		            
		            final TextView labelwaldis= new TextView(this);
		             
		            
		            
		            labelwaldis.setText(strdistance[i4]);      
			           
		            labelwaldis.setTextColor(Color.BLACK);
			            
			            labelecalories.setPadding(10, 0, 0, 0);
						tr2.setPadding(0, 1, 0, 1);
			            
			            tr2.addView(labelwaldis);
	            
	            
	            
	            
	            
              // Add the TableRow to the TableLayout 
	                          
	            tbl_exercise_log.addView(tr2, new TableLayout.LayoutParams(
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
	
	
	@Override
	public void onBackPressed()
	{
		System.out.println("BACK BUTTON PRESSED!!!!");
	}

}
