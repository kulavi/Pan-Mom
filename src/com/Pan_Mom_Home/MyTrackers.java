//Displays the list of all trackers
package com.Pan_Mom_Home;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import android.os.Bundle;

public class MyTrackers extends CustomWindow{
	TextView txtlogout,txtname;
	AlertDialog alert=null;
	
	String struname,struserid;
	int intuserid;
	String flag;
	
	DatabaseHelper data;
	Pan_mom_session mysession;
	
	    /*Implementing the view flipper*/
		Button weight,activity,heart,bp,hemoglobin,food,glucose,btnhome;
		ViewFlipper flipper;
	   	 
		String isdiabetic,ischronic,heightincm,strdc_sync,weightinkg;
	   	private Animation inFromRightAnimation() {

	   		Animation inFromRight = new TranslateAnimation(
	   		Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
	   		Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,   0.0f
	   		);
	   		inFromRight.setDuration(500);
	   		inFromRight.setInterpolator(new AccelerateInterpolator());
	   		return inFromRight;
	   		}
	   		private Animation outToLeftAnimation() {
	   		Animation outtoLeft = new TranslateAnimation(
	   		  Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
	   		  Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   -1.0f
	   		);
	   		outtoLeft.setDuration(500);
	   		outtoLeft.setInterpolator(new AccelerateInterpolator());
	   		return outtoLeft;
	   		}
	   		/** Called when the activity is first created. */
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.mytracker);
	        
	        
	        /*initializing classes*/
	        data=new DatabaseHelper(this);
	        mysession=new Pan_mom_session(this);
	        
	        /*inflating the views to use in this class*/
	        flipper = (ViewFlipper) findViewById(R.id.flipper);
	        
	        flipper.setInAnimation(inFromRightAnimation());
            flipper.setOutAnimation(outToLeftAnimation());
            flipper.showNext();  
	        
            /*getting data from session*/
	        struname=mysession.getUserName();
	        struserid=mysession.getUserId();
	        /*if there are no values in the session then getting data from previous activity*/
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
	        
	        
	        /*inflating the views to use in this class*/
	        weight=(Button) findViewById(R.id.weight); 
	        activity=(Button) findViewById(R.id.activity);
	        heart=(Button) findViewById(R.id.heart);
	        bp=(Button) findViewById(R.id.bp);
	        hemoglobin=(Button) findViewById(R.id.hemoglobin);
	        food=(Button) findViewById(R.id.food);
	        glucose=(Button) findViewById(R.id.glucose); 
	        txtlogout=(TextView) findViewById(R.id.txtlogout);
	        txtname=(TextView) findViewById(R.id.txtname);
	       
	             
	        txtname.setText("Welcome "+struname);
	        
	        txtlogout.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {  
					// TODO Auto-generated method stub
					
					mysession.setmainpage_flag("false");
					mysession.setflag("False");
					mysession.storeuserdetails(null,null, null);
					home();
					
					
				}
			});
	        
	        weight.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) 
				{
					
					getpre_pregnancy_wt(struserid);
					
			
				}
			});
	        
	        food.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) 
				{
					Intent i=new Intent(MyTrackers.this, Foodtab.class);
					Bundle bun=new Bundle();
					bun.putString("UNAME",struname);
					bun.putInt("USERID", intuserid);
					i.putExtras(bun);
			    	startActivity(i);
			
				}
			});
	        
	        activity.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) 
				{
					Intent i=new Intent(MyTrackers.this, Activitytab.class);
					Bundle bun=new Bundle();
					bun.putString("UNAME",struname);
					bun.putInt("USERID", intuserid);
					i.putExtras(bun);
			    	startActivity(i);
			
				}
			});
	        
	        glucose.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) 
				{
					Intent i=new Intent(MyTrackers.this, Glucosetab.class);
					Bundle bun=new Bundle();
					bun.putString("UNAME",struname);
					bun.putInt("USERID", intuserid);
					i.putExtras(bun);
			    	startActivity(i);
			
				}
			});
	        hemoglobin.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) 
				{
					Intent i=new Intent(MyTrackers.this, HBtab.class);
					Bundle bun=new Bundle();
					bun.putString("UNAME",struname);
					bun.putInt("USERID", intuserid);
					i.putExtras(bun);
			    	startActivity(i);
			
				}
			});
	        bp.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) 
				{
					Intent i=new Intent(MyTrackers.this, BPtab.class);
					Bundle bun=new Bundle();
					bun.putString("UNAME",struname);
					bun.putInt("USERID", intuserid);
					i.putExtras(bun);
			    	startActivity(i);
			
				}
			});
	        heart.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) 
				{
					Intent i=new Intent(MyTrackers.this, Hearttab.class);
					Bundle bun=new Bundle();
					bun.putString("UNAME",struname);
					bun.putInt("USERID", intuserid);
					i.putExtras(bun);
			    	startActivity(i);
			
				}
			});
	        
	       
	        
	        
	    }
		
		/*Type :Function
	    name:fun
	    return type:void
	    date:12-12-2011
	    purpose:Asking to enter the pre-pregnancy weight by opening the dialog*/
		public void fun()
		{
			
			
			LayoutInflater factory = LayoutInflater.from(this);
            final View textEntryView = factory.inflate(R.layout.pre_pregnancy_weight, null);
            final EditText edtwt=(EditText) textEntryView.findViewById(R.id.edtwt1);
            TextView txtwt=(TextView) textEntryView.findViewById(R.id.txtwt1);
            
            if(mysession.getweightunit().equals("kg"))
            {
            	txtwt.setText("kg");
            }
            else
            {
            	txtwt.setText("lb");
            }
            AlertDialog.Builder builder= new AlertDialog.Builder(MyTrackers.this);
            builder.setTitle("Pre-Pregnancy Weight");
               builder.setView(textEntryView);
               
               builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	
                    	if(mysession.getweightunit().equals("kg"))
                        {
                    		weightinkg=edtwt.getText().toString()+" kg";
                        }
                        else
                        {
                        	weightinkg=edtwt.getText().toString()+" lb";
                        }
                    	if(flag.equals("insert"))
                        {
                    	data.InsertDC(""+intuserid, isdiabetic, ischronic,heightincm,weightinkg, strdc_sync);
                    	Toast.makeText(MyTrackers.this, "Data Saved Successfully!!!", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                        	data.updateDC(""+intuserid, isdiabetic, ischronic,heightincm,weightinkg, strdc_sync);
                        	Toast.makeText(MyTrackers.this, "Data Updated Successfully!!!", Toast.LENGTH_LONG).show();
                        }
                    	alert.dismiss();
                    	
                    	Intent i=new Intent(MyTrackers.this, WTtab.class);
    					Bundle bun=new Bundle();
    					bun.putString("UNAME",struname);
    					bun.putInt("USERID", intuserid);
    					i.putExtras(bun);
    			    	startActivity(i);
                    	
                    }
                });
               builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked cancel so do some stuff */
                    }
                });
                alert = builder.create(); 
                alert.show();
        
		}
		/*Type :Function
	    name:getpre_pregnancy_wt
	    return type:void
	    date:12-12-2011
	    purpose:Fetching pre-pregnancy weight from local db*/
		public void getpre_pregnancy_wt(String user_login_id)
		{
			
			Cursor cur=data.getDC_data(Integer.parseInt(user_login_id));
			
			if(cur.getCount()==0)
			{
				
				flag="insert";
				isdiabetic="No";
				ischronic="No";
				heightincm="";
				fun();
				
			}
			
			else
			{
				
				flag="update";
	    	while(cur.moveToNext())
	    	{
	    		
	    		
	    		isdiabetic=cur.getString(2);
	    		ischronic=cur.getString(3);
	    		heightincm=cur.getString(4);
	    		weightinkg=cur.getString(5);
	    
	    		System.out.println("Pre Pregnancy Weight :"+weightinkg);
	    		if(weightinkg.equals("")||weightinkg.equals(null))
	    		{
	    			Toast.makeText(this, "Pre Pregnancy Weight Yet Not Entered !!!", Toast.LENGTH_LONG).show();
	    			fun();
	    		}
	    		
	    		else
	    		{
	    			Intent i=new Intent(MyTrackers.this, WTtab.class);
					Bundle bun=new Bundle();
					bun.putString("UNAME",struname);
					bun.putInt("USERID", intuserid);
					i.putExtras(bun);
			    	startActivity(i);
	    		}
	    
	    	}
	    	
	    	
			}
	    	cur.close();
	    	
		}
		/*Type :Function
	    name:home
	    return type:void
	    date:12-12-2011
	    purpose:Calling home screen*/
		
	    public void home()
	    {
	    	Intent i=new Intent(MyTrackers.this, Pan_MomActivity.class);
	    	startActivity(i);
	    }
	    
	    //overriding devices back button
	    public void onBackPressed()
		{
		 Intent i=new Intent(MyTrackers.this, Dashboard.class);
	    	Bundle bun=new Bundle();
			bun.putString("UNAME",struname);		
			bun.putInt("USERID", intuserid);
			i.putExtras(bun);
	    	startActivity(i);
		
		}
	    

	    
	    
	}
	
	


