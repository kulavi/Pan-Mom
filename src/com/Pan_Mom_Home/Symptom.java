//Showing the list of symptom which logged in user suffers
package com.Pan_Mom_Home;
import java.util.Calendar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.os.Bundle;

public class Symptom extends CustomWindow{
	TextView text;
     Button add,btnhome;
	Button rig,lef;
	GridView grid_main;
	 int count;
	 
	 int mYear,mMonth,mDay;
	 int date,month,year,allday=0,week,reminday;
	 String[] selected_id;
	 int arr[]={31,28,31,30,31,30,31,31,30,31,30,31};
	 String struname,struserid,strlmp,strduedate;
		int intuserid;
		DatabaseHelper data;
		Pan_mom_session mysession;
		final static int REQ_CODE = 1;
		String[] added_symptom,selected_symptom;
	
	 @Override
	   
	        
	        public void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.symptom_grid);
		        
		        data=new DatabaseHelper(this);
		        mysession=new Pan_mom_session(this);
		       
		        
		        add=(Button)findViewById(R.id.addsymptom);
		        rig=(Button) findViewById(R.id.imageButton1);
		        lef=(Button) findViewById(R.id.imageButton2);
		        text = (TextView) findViewById(R.id.text);
				
				
		        
		        struname=mysession.getUserName();
		        struserid=mysession.getUserId();
		        if(struserid.equals(""))
		        {
		        	System.out.println("SAVED USER ID IS IN IF...."+struserid);
		        	if(struname.equals(""))
			        {
			        	Bundle bun=getIntent().getExtras();
				        struname=bun.getString("UNAME");
				        intuserid=bun.getInt("USERID");
			        }
		        }
		        else
		        {
		        intuserid=Integer.parseInt(struserid);
		        System.out.println("SAVED USER ID IS IN ELSE...."+intuserid);
		        }    
		   
		        
		        
		        
		        
		        
		        add.setOnClickListener(new OnClickListener() {
		    		
					   
		    		public void onClick(View v) {
		    			// TODO Auto-generated method stub
		    			
		    			mysession.setselected_week(""+count);
		    			
		    			Intent i=new Intent(Symptom.this, Add_symptom.class);
		    			Bundle bun=new Bundle();
		    			bun.putString("UNAME",struname);		
		    			bun.putInt("USERID", intuserid);
		    			bun.putInt("SELECTED_WEEK", count);
		    			i.putExtras(bun);
		    	    	startActivityForResult(i,REQ_CODE);
		    			
		    			
		    		}
		    	});
		        
		        
		        
		        
		        
		        	get_lmp_and_due_date_from_db();
			
				 	final Calendar c = Calendar.getInstance();
				    mYear = c.get(Calendar.YEAR);
		            mMonth = c.get(Calendar.MONTH);
			        mDay = c.get(Calendar.DAY_OF_MONTH);
			        
			        String current_date=mDay+"-"+(mMonth+1)+"-"+mYear;
			        String[] dts_current=current_date.split("-");
			        
			        int mnth2=Integer.parseInt(dts_current[0].trim());
					  int day2=Integer.parseInt(dts_current[1].trim());
					  int year2=Integer.parseInt(dts_current[2].trim());
					 
					  Calendar thatday_current = Calendar.getInstance();
			          thatday_current.set(year2,day2,mnth2);
			        
			        Calendar thatDay_lmp = Calendar.getInstance();
					 thatDay_lmp.set(year, month, date);
			        
					 long diff_lmp_and_current_date=thatday_current.getTimeInMillis()-thatDay_lmp.getTimeInMillis();
					  long days = diff_lmp_and_current_date / (24 * 60 * 60 * 1000);
					  int ss=(int)days;
					  week=ss/7;
					  reminday=ss%7;
					  if(reminday!=0){week=week+1;}
			           
			       
		        count=week;
		        text.setText("Symptom For WEEK: "+count);
		        rig.setOnClickListener(new OnClickListener() {
		    		
		          	 
		        	public void onClick(View v) {
		    		    if(count==1){count=40;}
		    		    else{
		    			count--;} 
		    			text = (TextView) findViewById(R.id.text);
		    	        text.setText("Symptom For WEEK: "+count);
		    	        
		    	       
		    	        get_symptoms();
				        
		    		
		    			
		    		}
		    	});
		        lef.setOnClickListener(new OnClickListener() {
		    		
		        	public void onClick(View v) {
		      		  if(count==40){count=1;}
		      		  else{
		      			count++;}
		      			 text = (TextView) findViewById(R.id.text);  
		      		        text.setText("Symptom For WEEK: "+count);
		      		        
		      		            		       
		      		       get_symptoms();
		  		        	  		        
		      			
		      		}
		    	});
	
		        	
		        
		        	grid_main = (GridView)findViewById(R.id.GridView1);
		        	get_symptoms();
	
	        }
	 
	 @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			if(requestCode == REQ_CODE)
			 {
				if (resultCode == RESULT_OK)
				{	
			      	get_symptoms();
			      				      	
				} 
		
		     } 
		}
	 /*Type :Function
	    name:get_symptoms
	    return type:void
	    date:12-12-2011
	    purpose:Fetching symptoms which user suffers from local db*/
	 public void get_symptoms()
	 {
		 Cursor c=data.getSymptom_to_user_for_week_id(count,Integer.parseInt(struserid));
		 int len=c.getCount();
			
			if(len>0)
			{
				int k=0;
				int x=0;
				selected_id=new String[len];
				selected_symptom=new String[len];
			while(c.moveToNext())
			{
				
				String strselected_id=c.getString(3);
				
				selected_id[k]=strselected_id;
									
				
				Cursor cur=data.getSymptom_for_id(Integer.parseInt(selected_id[k]));
				while(cur.moveToNext())
				{
					
					selected_symptom[x]=cur.getString(1);
					
					System.out.println("SELECTED SYMPTOMS FROM SYMPTOM ARE::::[x]:::"+selected_symptom[x]);
					x++;
					
				}
				
				k++;
				
			}
			
			
			}
			else
			{
				
				selected_symptom=new String[0];
				System.out.println("Nothing to display");
			}
			
			
			grid_main.setAdapter(new ImageAdapter(this));
	 }
	 /*Type :Function
	    name:get_lmp_and_due_date_from_db
	    return type:void
	    date:12-12-2011
	    purpose:Fetching lmp and due date from local db*/
	 public void get_lmp_and_due_date_from_db()
	    {
	    	Cursor cur=data.getreg_info(intuserid);
	    	
	    	while(cur.moveToNext())
	    	{
	    		 strlmp=cur.getString(6);
	    		 strduedate=cur.getString(7);
	    		
	    	}
	    	
	    	
	    	String[] arr_lmp=strlmp.split("-");
	    	
	    	date=Integer.parseInt(arr_lmp[0].trim());
	    	month=Integer.parseInt(arr_lmp[1].trim());
	    	year=Integer.parseInt(arr_lmp[2].trim());
	    }
	 /*Type :Class
	    name:ImageAdapter
	    return type:void
	    date:12-12-2011
	    purpose:adapter to bind data to gridview*/
	 public class ImageAdapter extends BaseAdapter{
			Context mContext;
			
			public ImageAdapter(Context c){
				mContext = c;
			}
			
			public int getCount() {
				
				int cnt=selected_symptom.length; 
				System.out.println("Count is:"+cnt);
				return cnt;
			} 
			
			public View getView(final int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				View v;
				
					LayoutInflater li = getLayoutInflater();
					v = li.inflate(R.layout.symptom, null);
					
					TextView tv = (TextView)v.findViewById(R.id.textdata);
					tv.setText(selected_symptom[position]);
								
				
								
				return v;
			}
			public Object getItem(int arg0) {
	    
				return null;
			}
			public long getItemId(int arg0) {

				return 0;
			}
		
	}
	 
	 
	 //overriding devices back button
	 public void onBackPressed()
		{
		 Intent i=new Intent(Symptom.this, Dashboard.class);
	    	Bundle bun=new Bundle();
			bun.putString("UNAME",struname);		
			bun.putInt("USERID", intuserid);
			i.putExtras(bun);
	    	startActivity(i);
		
		}

}
