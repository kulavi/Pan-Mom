package com.Pan_Mom_Home;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class View_Food_Log extends Activity {
	
	
	TableLayout tbl_food_log;
	Button btnedit,btndel;
	LinearLayout ll;
	DatabaseHelper data;
	Pan_mom_session mysession;
	int  flag;
	int[]  selected_index1;
	int selected_index;
	protected static boolean[] _selections;
	int count=0;
	int [] index;
	int idlen;
	int intuserid,selected_id;
	String strulogin,struname;
	String[] strwht_eat,strdate,strtime,strhow_much,strhb_sync,strfid;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.view_food_log);
		
		data=new DatabaseHelper(this);
		mysession=new Pan_mom_session(this);
		btnedit=(Button)findViewById(R.id.btnedit);
		btndel=(Button)findViewById(R.id.btndel);
		tbl_food_log=(TableLayout) findViewById(R.id.tbl_food_log);
		ll=(LinearLayout)findViewById(R.id.footer1);
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
		 selected_index=0; 
		getfood_log(strulogin);
		
		/*TextView textView = new TextView(this);
		textView.setText("HB LOG");
		setContentView(textView);*/
		btnedit.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) 
			{
				
				for(int k=0;k<strfid.length;k++)
                {
                	System.out.println("Value at "+k+" is"+_selections[k]);
                }
				
				
				if(count>1)
				{
					Toast.makeText(View_Food_Log.this, "Please Select only one value to edit..", Toast.LENGTH_LONG).show();
				}
				else
				{
				int x=0;
				for(int k=0;k<strfid.length;k++)
                {
					System.out.println("Selected Checkbox id is..."+selected_index1[k]);
            		if(_selections[k]=true)
            		{
                	if(strfid[k].equals(Integer.toString(selected_index1[x])))
                	{
                		selected_index=k;
                		selected_id=Integer.parseInt(strfid[k]);
                		
                	}
            		}
                	x++;
                	
                }
				System.out.println("Finally Selected Checkbox id is..."+selected_id);
				flag=1;
				mysession.settracker_flag("view");
				Intent i=new Intent(View_Food_Log.this, Foodtab.class);
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
				for(int k=0;k<strfid.length;k++)
                {
					System.out.println("Selected Checkbox id is..."+selected_index1[k]);
            		if(_selections[k]=true)
            		{
                	if(strfid[k].equals(Integer.toString(selected_index1[x])))
                	{
                		selected_index=k;
                		selected_id=Integer.parseInt(strfid[k]);
                		data.delFood_data(Integer.parseInt(strulogin),selected_id);
                			                		
                	}
            		}
                	x++;
                	
                }
				
				Toast.makeText(View_Food_Log.this,"Data deleted successfully",Toast.LENGTH_LONG).show();
		        tbl_food_log.removeAllViews();
				getfood_log(strulogin);
			}
		});

	}
	
	public void getfood_log(String user_login_id)
	{
		Cursor cur=data.getFood_data(Integer.parseInt(strulogin));
		strwht_eat=new String[cur.getCount()];
		strdate=new String[cur.getCount()];
		strtime=new String[cur.getCount()];
		strhow_much=new String[cur.getCount()];
		strfid=new String[cur.getCount()];
		 
		int i=0;

    	while(cur.moveToNext())
    	{
    		String strfid1=cur.getString(0);
    		//String strulogin1=cur.getString(1);
    		String strdate1=cur.getString(2); 
    		String strwhen1=cur.getString(3); 
    		String strwht_u_eat1=cur.getString(4);
    		String strhow_much1=cur.getString(5);
    		//String strfood_sync1=cur.getString(6);
    
        	
        	
    		
    		strdate[i]=strdate1;
        	strtime[i]=strwhen1;        	
        	strwht_eat[i]=strwht_u_eat1;
        	strhow_much[i]=strhow_much1;
        	strfid[i]=strfid1;
        	
        	
        	//System.out.println("ULogin:"+strulogin1);
        	System.out.println("Date:"+strdate1);
        	System.out.println("When:"+strwhen1);
        	System.out.println("What you eat:"+strwht_u_eat1);
        	System.out.println("How much:"+strhow_much1);
        	//System.out.println("Food sync:"+strfood_sync1);
        	
        	i++;
        	
    		
    	}
    	cur.close();
    	display_food_log();
	}
	
	public void display_food_log() 
	 {
		
		int gidlen= strfid.length;
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
			  final TextView labelfoodtitle = new TextView(this);
			          
			  
			  labelfoodtitle.setText("Food"); 
			  labelfoodtitle.setTextColor(Color.BLACK);
			  
			  labelfoodtitle.setPadding(10, 0, 0, 0);  
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelfoodtitle);
			  
			//Create a TextView to house the name of the province 
			  final TextView labelqauntititle = new TextView(this);
			          
			  
			  labelqauntititle.setText("Quantinty"); 
			  labelqauntititle.setTextColor(Color.BLACK);
			  
			  labelqauntititle.setPadding(10, 0, 0, 0);  
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelqauntititle);
			  
			
			    
			  
			  tbl_food_log.addView(tr1, new TableLayout.LayoutParams(
			          LayoutParams.WRAP_CONTENT,
			         LayoutParams.WRAP_CONTENT)); 

	
			 for(int i4=0;i4<strwht_eat.length;i4++) 
			  {  
		
				try 
				{
				
	            
	            final TableRow tr2 = new TableRow(this);  
	            tr2.layout(0, 0, 0, 0);
	            
	            
	                
	            
	            final CheckBox chk1 = new CheckBox(this);
	            
	            chk1.setPadding(10, 0, 0, 0);
	            int a=Integer.parseInt(strfid[i4]);
	            System.out.println("value of a issssssssssss :"+a);
	            chk1.setId(a);
	            chk1.setTag(i4+1);
	            //chk1.setId(i4);
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
	            
	            final TextView labelhbvalue = new TextView(this);
	             
            
	            
	            labelhbvalue.setText(strwht_eat[i4]);      
	           
	            labelhbvalue.setTextColor(Color.BLACK);
	            
	            labelhbvalue.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelhbvalue);
	            
	            final TextView labelquanti = new TextView(this);
	             
            
	            
	            labelquanti.setText(strhow_much[i4]);      
	           
	            labelquanti.setTextColor(Color.BLACK);
	            
	            labelquanti.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelquanti);
	  
              // Add the TableRow to the TableLayout 
	                          
	            tbl_food_log.addView(tr2, new TableLayout.LayoutParams(
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
	/*public void delete(String user_login_id,int bpid) {
		
		data.delFood_data(Integer.parseInt(strulogin),selected_id);
		System.out.println("data deleted successfully");
	}*/
	@Override
	public void onBackPressed()
	{
		System.out.println("BACK BUTTON PRESSED!!!!");
	}
}