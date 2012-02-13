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

public class View_BP_Log extends Activity {
	
	TableLayout tbl_bp_log;
	Button btnedit,btndel;
	DatabaseHelper data;
	LinearLayout ll;
	Pan_mom_session mysession;
	String s1,s2,s3,s4,s5,s6;	
	int intuserid,selected_id;
	int  flag;
	int[]  selected_index1;
	int selected_index;
	protected static boolean[] _selections;
	int count=0;
	String strulogin,struname;
	String[] strsystolic,strdate,strtime,strdiastolic,strhand,strpulase_count,strwt_sync,strbpid;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.view_bp_log);
		
		/*initializing classes*/
		data=new DatabaseHelper(this);
		mysession=new Pan_mom_session(this);
		/*inflating the views to use in this class*/
		btnedit=(Button)findViewById(R.id.btnedit);
		btndel=(Button)findViewById(R.id.btndel);
		tbl_bp_log=(TableLayout) findViewById(R.id.tbl_bp_log);
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

		getbp_log(strulogin);
		/*TextView textView = new TextView(this);
		textView.setText("WEIGHT LOG");
		setContentView(textView);*/
		
		 btnedit.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) 
				{
				
					
					for(int k=0;k<strbpid.length;k++)
	                {
	                	System.out.println("Value at "+k+" is"+_selections[k]);
	                }
					
					
					if(count>1)
					{
						Toast.makeText(View_BP_Log.this, "Please Select only one value to edit..", Toast.LENGTH_LONG).show();
					}
					else
					{
					int x=0;
					for(int k=0;k<strbpid.length;k++)
	                {
						System.out.println("Selected Checkbox id is..."+selected_index1[k]);
	            		if(_selections[k]=true)
	            		{
	                	if(strbpid[k].equals(Integer.toString(selected_index1[x])))
	                	{
	                		selected_index=k;
	                		selected_id=Integer.parseInt(strbpid[k]);
	                		
	                	}
	            		}
	                	x++;
	                	
	                }
					System.out.println("Finally Selected Checkbox id is..."+selected_id);
				}
					flag=1;
					mysession.settracker_flag("viewbp");
					Intent i=new Intent(View_BP_Log.this, BPtab.class);
					Bundle bun=new Bundle();
					bun.putString("UNAME",struname);
					bun.putInt("USERID", intuserid);	
					bun.putInt("Rowid", selected_index+1);
					bun.putInt("Flagid", flag);
					bun.putInt("Selectedid", selected_id);
					i.putExtras(bun);
			    	startActivity(i);
			
				}
			});

		 btndel.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) 
				{
					int x=0;
					for(int k=0;k<strbpid.length;k++)
	                {
						System.out.println("Selected Checkbox id is..."+selected_index1[k]);
	            		if(_selections[k]=true)
	            		{
	                	if(strbpid[k].equals(Integer.toString(selected_index1[x])))
	                	{
	                		selected_index=k;
	                		selected_id=Integer.parseInt(strbpid[k]);
	                		data.delbp_data(Integer.parseInt(strulogin),selected_id);
	                			                		
	                	}
	            		}
	                	x++;
	                	
	                }
					
					Toast.makeText(View_BP_Log.this,"Data deleted successfully",Toast.LENGTH_LONG).show();
					tbl_bp_log.removeAllViews();
					getbp_log(strulogin);
					
				}
			});
	}
	/*Type :Function
    name:getbp_log
    return type:void
    date:12-12-2011
    purpose:Gets BP data from local db*/
	public void getbp_log(String user_login_id)
	{
		Cursor cur=data.getBP_data(Integer.parseInt(strulogin));
		
		strdate=new String[cur.getCount()];
		strtime=new String[cur.getCount()];
		strsystolic=new String[cur.getCount()];
		strdiastolic=new String[cur.getCount()];
		strpulase_count=new String[cur.getCount()];
		strhand=new String[cur.getCount()];
		strwt_sync=new String[cur.getCount()];
		strbpid=new String[cur.getCount()];
		
		int i=0;

    	while(cur.moveToNext())
    	{
    		String strbpid1=cur.getString(0);
    		//String strulogin1=cur.getString(1); 
    		String strdate1=cur.getString(2);
    		String strtime1=cur.getString(3); 
    		String strsystolic1=cur.getString(4);
    		String strdiastolic1=cur.getString(5);
    		String strpulsecount1=cur.getString(6);
    		String strhand1=cur.getString(7);
    		//String strbp_sync1=cur.getString(8);
    		    		
    		        	
    		strbpid[i]=strbpid1;
        	strdate[i]=strdate1;
        	strtime[i]=strtime1;
        	strsystolic[i]=strsystolic1;
        	strdiastolic[i]=strdiastolic1;
        	strpulase_count[i]=strpulsecount1;
        	strhand[i]=strhand1;
        	
        	//System.out.println("ULogin:"+strulogin1);
        	System.out.println("Date:"+strdate[i]);
        	System.out.println("Time:"+strtime[i]);
        	System.out.println("Systolic:"+strsystolic[i]);
        	System.out.println("Diastolic:"+strdiastolic[i]);
        	System.out.println("Pulse Count:"+strpulase_count[i]);
        	System.out.println("Hand:"+strhand[i]);
        	//System.out.println("Flag:"+strbp_sync1);
        	
        	i++;
        	
    		
    	}
    	cur.close();
    	display_bp_log();
	}
	/*Type :Function
    name:display_bp_log
    return type:void
    date:12-12-2011
    purpose:Dispaying fetched data in table format*/
	public void display_bp_log() 
	 {
		
		int gidlen= strbpid.length;
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
			  final TextView labelsystitle = new TextView(this);
			 
			  labelsystitle.setText("Systolic"); 
			  labelsystitle.setTextColor(Color.BLACK);
			  
			  labelsystitle.setPadding(10, 0, 0, 0);
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelsystitle);  
			  
			//Create a TextView to house the name of the province 
			  final TextView labeldiastitle = new TextView(this);
			  
			  labeldiastitle.setText("Diastolic"); 
			  labeldiastitle.setTextColor(Color.BLACK);
			  
			  labeldiastitle.setPadding(10, 0, 0, 0);  
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labeldiastitle);
			  
			//Create a TextView to house the name of the province 
			  final TextView labelpulsecnttitle = new TextView(this);
			   
			  labelpulsecnttitle.setText("Pulse Count"); 
			  labelpulsecnttitle.setTextColor(Color.BLACK);
			  
			  labelpulsecnttitle.setPadding(10, 0, 0, 0);
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelpulsecnttitle);
			  
			//Create a TextView to house the name of the province 
			  final TextView labelhandtitle = new TextView(this);
			  
			  labelhandtitle.setText("Hand"); 
			  labelhandtitle.setTextColor(Color.BLACK);
			  
			  labelhandtitle.setPadding(10, 0, 0, 0);
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelhandtitle);
			 
			  tbl_bp_log.addView(tr1, new TableLayout.LayoutParams(
			          LayoutParams.WRAP_CONTENT,
			         LayoutParams.WRAP_CONTENT)); 

			 for(int i4=0;i4<strsystolic.length;i4++) 
			  {  
				try 
				{
			     
	            final TableRow tr2 = new TableRow(this);  
	            tr2.layout(0, 0, 0, 0);
	             
	            
	            final CheckBox chk1 = new CheckBox(this); 
	            
	            chk1.setPadding(10, 0, 0, 0);
	            //chk1.setId(strbpid[i4]);
	            int a=Integer.parseInt(strbpid[i4]);
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
	            
	            final TextView labeltime = new TextView(this);
	       
	            labeltime.setText(strtime[i4]);      
	           
	            labeltime.setTextColor(Color.BLACK);
	            
	            labeltime.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labeltime);
	            
	            final TextView labelsys = new TextView(this);
	             
	            labelsys.setText(strsystolic[i4]);      
	           
	            labelsys.setTextColor(Color.BLACK);
	            
	            labelsys.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelsys);
	        
	            final TextView labeldias = new TextView(this);
	            
	            labeldias.setText(strdiastolic[i4]);       
	           
	            labeldias.setTextColor(Color.BLACK);
	            
	            labeldias.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labeldias);
	            
	            final TextView labelpulsecnt = new TextView(this);
	             
	            labelpulsecnt.setText(strpulase_count[i4]);       
	           
	            labelpulsecnt.setTextColor(Color.BLACK);
	            
	            labelpulsecnt.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelpulsecnt);
	            
	            final TextView labelhand = new TextView(this);
	             
	            labelhand.setText(strhand[i4]);       
	           
	            labelhand.setTextColor(Color.BLACK);
	            
	            labelhand.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelhand);
	       
              // Add the TableRow to the TableLayout 
	                          
	            tbl_bp_log.addView(tr2, new TableLayout.LayoutParams(
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
