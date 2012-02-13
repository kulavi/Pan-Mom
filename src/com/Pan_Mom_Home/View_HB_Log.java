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

public class View_HB_Log extends Activity {
	
	
	TableLayout tbl_hb_log;
	int  flag;
	int[]  selected_index1;
	int selected_index;
	protected static boolean[] _selections;
	int count=0;
	DatabaseHelper data;
	Pan_mom_session mysession;
	Button btnedit,btndel;	
	String strulogin,struname;
	int intuserid,selected_id;
	LinearLayout ll;
	String[] strhb_value,strdate,strtime,strhb_sync,strhid;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.view_hb_log);
		
		data=new DatabaseHelper(this);
		mysession=new Pan_mom_session(this);
		btnedit=(Button)findViewById(R.id.btnedit);
		btndel=(Button)findViewById(R.id.btndel);
		tbl_hb_log=(TableLayout) findViewById(R.id.tbl_hb_log);
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

		gethb_log(strulogin);
		
		/*TextView textView = new TextView(this);
		textView.setText("HB LOG");
		setContentView(textView);*/
		btnedit.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) 
			{
				
				for(int k=0;k<strhid.length;k++)
                {
                	System.out.println("Value at "+k+" is"+_selections[k]);
                }
				
				
				if(count>1)
				{
					Toast.makeText(View_HB_Log.this, "Please Select only one value to edit..", Toast.LENGTH_LONG).show();
				}
				else
				{
				int x=0;
				for(int k=0;k<strhid.length;k++)
                {
					System.out.println("Selected Checkbox id is..."+selected_index1[k]);
            		if(_selections[k]=true)
            		{
                	if(strhid[k].equals(Integer.toString(selected_index1[x])))
                	{
                		selected_index=k;
                		selected_id=Integer.parseInt(strhid[k]);
                		
                	}
            		}
                	x++;
                	
                }
				System.out.println("Finally Selected Checkbox id is..."+selected_id);
               
				flag=1;
				mysession.settracker_flag("viewhb");
				Intent i=new Intent(View_HB_Log.this, HBtab.class);
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
				for(int k=0;k<strhid.length;k++)
                {
					System.out.println("Selected Checkbox id is..."+selected_index1[k]);
            		if(_selections[k]=true)
            		{
                	if(strhid[k].equals(Integer.toString(selected_index1[x])))
                	{
                		selected_index=k;
                		selected_id=Integer.parseInt(strhid[k]);
                		data.delHB_data(Integer.parseInt(strulogin),selected_id);
                			                		
                	}
            		}
                	x++;
                	
                }
				
				Toast.makeText(View_HB_Log.this,"Data deleted successfully",Toast.LENGTH_LONG).show();
		        tbl_hb_log.removeAllViews();
				gethb_log(strulogin);
			}
		});

	}
	
	public void gethb_log(String user_login_id)
	{
		Cursor cur=data.getHB_data(Integer.parseInt(strulogin));
		strhid=new String[cur.getCount()];
		strhb_value=new String[cur.getCount()];
		strdate=new String[cur.getCount()];
		strtime=new String[cur.getCount()];
		
		 
		int i=0;

    	while(cur.moveToNext())
    	{
    		String strhid1=cur.getString(0);
    		//String strulogin1=cur.getString(1); 
    		String strhemoglobin1=cur.getString(2);
    		String strdate1=cur.getString(3); 
    		String strtime1=cur.getString(4);
    		//String strhb_sync1=cur.getString(5);
    		
    		strhid[i]=strhid1;  	
        	strhb_value[i]=strhemoglobin1;
        	strdate[i]=strdate1;
        	strtime[i]=strtime1;
        	
        	
        	//System.out.println("ULogin:"+strulogin1);
        	System.out.println("HB Value:"+strhb_value[i]);
        	System.out.println("Date:"+strdate[i]);
        	System.out.println("Time:"+strtime[i]);
        	//System.out.println("Flag:"+strhb_sync1);
        	
        	i++;
        	
    		
    	}
    	cur.close();
    	display_wt_log();
	}
	
	public void display_wt_log() 
	 {
		int gidlen= strhid.length;
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
			  final TextView labelhbvaluetitle = new TextView(this);
			          
			  
			  labelhbvaluetitle.setText("Value"); 
			  labelhbvaluetitle.setTextColor(Color.BLACK);
			  
			  labelhbvaluetitle.setPadding(10, 0, 0, 0);  
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelhbvaluetitle);
			  
			
			    
			  
			  tbl_hb_log.addView(tr1, new TableLayout.LayoutParams(
			          LayoutParams.WRAP_CONTENT,
			         LayoutParams.WRAP_CONTENT)); 

	
			 for(int i4=0;i4<strhb_value.length;i4++) 
			  {  
		
				try 
				{
				
	            
	            final TableRow tr2 = new TableRow(this);  
	            tr2.layout(0, 0, 0, 0);
	            
	            
	                
	            
	            final CheckBox chk1 = new CheckBox(this);
	            
	            chk1.setPadding(10, 0, 0, 0);
	            int a=Integer.parseInt(strhid[i4]);
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
	            
	            final TextView labelhbvalue = new TextView(this);
	             
            
	            
	            labelhbvalue.setText(strhb_value[i4]);      
	           
	            labelhbvalue.setTextColor(Color.BLACK);
	            
	            labelhbvalue.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelhbvalue);
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
              // Add the TableRow to the TableLayout 
	                          
	            tbl_hb_log.addView(tr2, new TableLayout.LayoutParams(
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