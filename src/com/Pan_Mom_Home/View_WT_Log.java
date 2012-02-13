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

public class View_WT_Log extends Activity {
	
	TableLayout tbl_weight_log;
	TextView txt1,txt2;
	Button btnedit,btndel;
	
	DatabaseHelper data;
	Pan_mom_session mysession;
	
	String strpr_pregnancy_weight_kg,strweight_kg1,weight_gain;
	int[]  selected_index1;
	int selected_index;
	protected static boolean[] _selections;
	int count=0;	
	String strulogin,struname;
	int intuserid,selected_id,flag;
	LinearLayout ll;
	String[] strweight_kg,strdate,strc1,strc2,strbody_fat,strwt_sync,strwid;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.view_wt_log);
		
		data=new DatabaseHelper(this);
		mysession=new Pan_mom_session(this);
		
		
		tbl_weight_log=(TableLayout) findViewById(R.id.tbl_weight_log);
		btnedit=(Button)findViewById(R.id.btnedit);
		btndel=(Button)findViewById(R.id.btndel);
		ll=(LinearLayout)findViewById(R.id.footer1);
		ll.setVisibility(View.GONE);
		txt1=(TextView) findViewById(R.id.txtname1);
		txt2=(TextView) findViewById(R.id.txtname2);
		
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
 
		 getpre_pregnancy_wt(strulogin);
		 getlastwt_log(strulogin);
		 getwt_log(strulogin);
		btnedit.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) 
			{
				
				for(int k=0;k<strwid.length;k++)
                {
                	System.out.println("Value at "+k+" is"+_selections[k]);
                }
				
				
				if(count>1)
				{
					Toast.makeText(View_WT_Log.this, "Please Select only one value to edit..", Toast.LENGTH_LONG).show();
				}
				else
				{
				int x=0;
				for(int k=0;k<strwid.length;k++)
                {
					System.out.println("Selected Checkbox id is..."+selected_index1[k]);
            		if(_selections[k]=true)
            		{
                	if(strwid[k].equals(Integer.toString(selected_index1[x])))
                	{
                		selected_index=k;
                		selected_id=Integer.parseInt(strwid[k]);
                		
                	}
            		}
                	x++;
                	
                }
				System.out.println("Finally Selected Checkbox id is..."+selected_id);
				flag=1;
				mysession.settracker_flag("viewwt");
				Intent i=new Intent(View_WT_Log.this, WTtab.class);
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
				for(int k=0;k<strwid.length;k++)
                {
					System.out.println("Selected Checkbox id is..."+selected_index1[k]);
            		if(_selections[k]=true)
            		{
                	if(strwid[k].equals(Integer.toString(selected_index1[x])))
                	{
                		selected_index=k;
                		selected_id=Integer.parseInt(strwid[k]);
                		data.delWt_data(Integer.parseInt(strulogin),selected_id);  
                			                		
                	}
            		}
                	x++;
                	  
                }
				
				Toast.makeText(View_WT_Log.this,"Data deleted successfully",Toast.LENGTH_LONG).show();
		        tbl_weight_log.removeAllViews();
				getwt_log(strulogin);
			}
		});
		
		

	}
	
	public void getwt_log(String user_login_id)
	{
		Cursor cur=data.getWt_data(Integer.parseInt(user_login_id));
		
		
		strweight_kg=new String[cur.getCount()];
		strdate=new String[cur.getCount()];
		strc1=new String[cur.getCount()];
		strc2=new String[cur.getCount()];
		strbody_fat=new String[cur.getCount()]; 
		strwt_sync=new String[cur.getCount()];
		strwid=new String[cur.getCount()];
		int i=0;

    	while(cur.moveToNext())
    	{
    		String strwid1=cur.getString(0);
    		//String strulogin1=cur.getString(1); 
    		String strweight_kg1=cur.getString(2);
    		String strdate1=cur.getString(3); 
    		String strc11=cur.getString(4);
    		String strc21=cur.getString(5);
    		String strbody_fat1=cur.getString(6);
    		//String strwt_sync1=cur.getString(7);
    		
    		        	
        	strweight_kg[i]=strweight_kg1;
        	strdate[i]=strdate1;
        	strc1[i]=strc11;
        	strc2[i]=strc21;
        	strbody_fat[i]=strbody_fat1;
        	strwid[i]=strwid1;
        	
        	System.out.println("Weight In Kg:"+strweight_kg[i]);
        	System.out.println("Date:"+strdate[i]); 
        	System.out.println("Systolic:"+strc1[i]);
        	System.out.println("Diastolic:"+strc2[i]);
        	System.out.println("Pulse Count:"+strbody_fat[i]);
        	
        	
        	i++;
        	
    		
    	}
    	cur.close();
    	display_wt_log();
	}
	public void getlastwt_log(String user_login_id)
	{
		
		Cursor cur=data.getlast_Wt_data(Integer.parseInt(user_login_id));
		
		if(cur.getCount()==0)
		{ 
			strweight_kg1="0";
			weight_gain="0";
		}
		
		else
		{
    	while(cur.moveToNext())
    	{
    		String strwid1=cur.getString(0);
    		//String strulogin1=cur.getString(1); 
    		strweight_kg1=cur.getString(2);
    		String strdate1=cur.getString(3); 
    		String strc11=cur.getString(4);
    		String strc21=cur.getString(5);
    		String strbody_fat1=cur.getString(6);
    		//String strwt_sync1=cur.getString(7);
    		
    		        	
        	
    		System.out.println("1.Weight Id:"+strwid1);
        	System.out.println("2.Weight In Kg:"+strweight_kg1);
        	System.out.println("3.Date:"+strdate1);
        	System.out.println("4.Systolic:"+strc11);
        	System.out.println("5.Diastolic:"+strc21);
        	System.out.println("6.Pulse Count:"+strbody_fat1);
        	
        	
        	
        	
    		
    	}
    	cur.close();
    	
    	if(strpr_pregnancy_weight_kg.contains("kg"))
    	{
    		strpr_pregnancy_weight_kg=strpr_pregnancy_weight_kg.replace("kg", "").trim();
    	}
    	if(strpr_pregnancy_weight_kg.contains("lb"))
    	{
    		strpr_pregnancy_weight_kg=strpr_pregnancy_weight_kg.replace("lb", "").trim();
    		double kgdefault=0.45359237;      
            double kgwt1=Double.valueOf(strpr_pregnancy_weight_kg).doubleValue();
            double kgwt2=kgwt1*kgdefault;
            strpr_pregnancy_weight_kg=""+kgwt2;
            
            double hincm=Double.parseDouble(strpr_pregnancy_weight_kg);
        	int r = (int) Math.round(hincm*100);
            double f = r / 100.0;
            strpr_pregnancy_weight_kg=Double.toString(f);
            
            System.out.println("WEIGHT CALCULATED:"+strpr_pregnancy_weight_kg);
    	}
    	double wtgain=Double.parseDouble(strweight_kg1)-Double.parseDouble(strpr_pregnancy_weight_kg);
    	
    	 weight_gain=Double.toString(wtgain);
    	 
    	 System.out.println("1.WEIGHT CALCULATED:"+weight_gain);
		}
		
		String unit=mysession.getweightunit().toString();
		
		
			txt1.setText("Current Weight:"+strweight_kg1+" kg");
	    	txt2.setText("Weight Gain:"+weight_gain+" kg");
	    
		
		
    		
	}
	
	public void getpre_pregnancy_wt(String user_login_id)
	{
		Cursor cur=data.getDC_data(Integer.parseInt(user_login_id));
		
		if(cur.getCount()==0)
		{
			Toast.makeText(this, "Pre Pregnancy Weight Not Entered Yet!!!", Toast.LENGTH_LONG).show();
		}

    	while(cur.moveToNext())
    	{
    		
    		 strpr_pregnancy_weight_kg=cur.getString(5);
    		 	
        	
    		System.out.println("Pre Pregnancy Weight :"+strpr_pregnancy_weight_kg);
        
    	}
    	cur.close();
    	
	}
	public void display_wt_log() 
	 {
   		
		
		int gidlen= strwid.length;
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
			  final TextView labelkgtitle = new TextView(this); 
			          
			  
			  labelkgtitle.setText("Weight(kg)"); 
			  labelkgtitle.setTextColor(Color.BLACK);
			  
			  labelkgtitle.setPadding(10, 0, 0, 0);
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelkgtitle);  
			  
			//Create a TextView to house the name of the province 
			  final TextView labelc1title = new TextView(this);
			          
			  
			  labelc1title.setText("Condition1"); 
			  labelc1title.setTextColor(Color.BLACK);
			  
			  labelc1title.setPadding(10, 0, 0, 0);  
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelc1title);
			  
			//Create a TextView to house the name of the province 
			  final TextView labelc2title = new TextView(this);
			           
			  
			  labelc2title.setText("Condition2"); 
			  labelc2title.setTextColor(Color.BLACK);
			  
			  labelc2title.setPadding(10, 0, 0, 0);
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelc2title);
			  
			//Create a TextView to house the name of the province 
			  final TextView labelbodyfattitle = new TextView(this);
			           
			  
			  labelbodyfattitle.setText("Body Fat(%)"); 
			  labelbodyfattitle.setTextColor(Color.BLACK);
			  
			  labelbodyfattitle.setPadding(10, 0, 0, 0);
			  tr1.setPadding(0, 1, 0, 1);
			  
			  tr1.addView(labelbodyfattitle);
			    
			  
			  tbl_weight_log.addView(tr1, new TableLayout.LayoutParams(
			          LayoutParams.WRAP_CONTENT,
			         LayoutParams.WRAP_CONTENT)); 

	
			 for(int i4=0;i4<strweight_kg.length;i4++) 
			  {  
		
				try 
				{
				
	            
	            final TableRow tr2 = new TableRow(this);  
	            tr2.layout(0, 0, 0, 0);
	            
	            
	                
	            
	            final CheckBox chk1 = new CheckBox(this);
	            
	            chk1.setPadding(10, 0, 0, 0);
	            int a=Integer.parseInt(strwid[i4]);
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
	            
	            final TextView labelwtkg = new TextView(this);
	             
            
	            
	            labelwtkg.setText(strweight_kg[i4]);      
	           
	            labelwtkg.setTextColor(Color.BLACK);
	            
	            labelwtkg.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelwtkg);
	            
	            final TextView labelc1 = new TextView(this);
	             
            
	            
	            labelc1.setText(strc1[i4]);      
	           
	            labelc1.setTextColor(Color.BLACK);
	            
	            labelc1.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelc1);
	            
	            
	            
	            
	            final TextView labelc2 = new TextView(this);
	             
	               
	            
	            labelc2.setText(strc2[i4]);       
	           
	            labelc2.setTextColor(Color.BLACK);
	            
	            labelc2.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelc2);
	            
	            final TextView labelbodyfat = new TextView(this);
	             
	               
	            
	            labelbodyfat.setText(strbody_fat[i4]);       
	           
	            labelbodyfat.setTextColor(Color.BLACK);
	            
	            labelbodyfat.setPadding(10, 0, 0, 0);
				tr2.setPadding(0, 1, 0, 1);
	            
	            tr2.addView(labelbodyfat);
	            
	        
              // Add the TableRow to the TableLayout 
	                          
	            tbl_weight_log.addView(tr2, new TableLayout.LayoutParams(
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
