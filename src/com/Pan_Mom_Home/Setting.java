//Provides different kinds of app settings
package com.Pan_Mom_Home;



import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Setting extends CustomWindow implements OnItemClickListener{
	
	
	String struname,strpwd,strdue_date;
	int intuserid,select_phot_view_type;
	String isdiabetic,ischronic,strdc_sync;
	String unit_of_weight,unit_of_height,heightincm,weightinkg;
	String return_flag="Setting";
	
	String list_due_date;
	String list_diabetic_chronic;
	String list_height;
	String list_pre_pregnancy_wt;
	String list_height_unit;
	String list_weight_unit;
	String photo_view_type;
	
	String[] feet;
	
	protected static CharSequence[] complete_name;  
	protected static boolean[] _selections;
    int select_height=-1,select_weight;
	
	Pan_mom_session mysession;
	DatabaseHelper data;
	
	Dialog myDialog;
	AlertDialog alert=null;
	final static int REQ_CODE = 1;
	int curlength,size,count=0;
	
	ListView lv1;
	ArrayList<String> setting = new ArrayList<String>();

	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting); 
		
        data=new DatabaseHelper(this);
		mysession=new Pan_mom_session(this); 
			 
		mysession.setreturnflag("Setting");
		
				
		
		
		Bundle bun=getIntent().getExtras();
        struname=bun.getString("UNAME");
        strpwd=bun.getString("PWD");
        intuserid=bun.getInt("USERID");
        
        lv1=(ListView)findViewById(R.id.List);
		
        complete_name=new CharSequence[2];
		complete_name[0]="Diabetic";
		complete_name[1]="Chronic";
		size=complete_name.length;
		_selections =  new boolean[size];

		
		getDC_data();
		list_due_date="Due Date";
        list_diabetic_chronic="Are u diabetic or chronic";
   	 	refreshlistitems();
        getdue_date();
        bindlistview();
         
        
		
  

        
		
	}
	/*Type :Function
    name:refreshlistitems
    return type:void
    date:12-12-2011
    purpose:Refreshing the list items*/
public void refreshlistitems()
{
	String weightinkg1;
	String label;
	double f ;

	if(weightinkg.equals(""))
	{
		
		list_pre_pregnancy_wt="Pre-Pregnancy Weight";
	}
	else
	{
	if(weightinkg.contains(" kg"))
	{
		weightinkg1=weightinkg.replace(" kg", "");
		label=" kg";  
	}
	else
	{
		weightinkg1=weightinkg.replace(" lb", "");
		label=" lb";
	}
	double hincm=Double.parseDouble(weightinkg1);
	int r = (int) Math.round(hincm*100);
    f = r / 100.0;
    list_pre_pregnancy_wt="Pre-Pregnancy Weight\n"+f+label;
	}
	 list_height="Height\n"+heightincm;
	 
	 list_height_unit="Height Unit\n"+mysession.getheightunit().toString();
	 list_weight_unit="Weight Unit\n"+mysession.getweightunit().toString();	
	 photo_view_type="Photo View Type\n"+mysession.getgallery_type().toString();
}
/*Type :Function
name:getdue_date
return type:void
date:12-12-2011
purpose:Fetchingd due date from local db to edit*/
public void getdue_date()
{
	try
    {
    	
    	Cursor cur=data.getreg_info(intuserid);
		while(cur.moveToNext())
		{
			list_due_date="Due Date\n"+cur.getString(7);
			System.out.println("DUE DATE TO B SET FROM TRY:"+intuserid);
		}
    }
    catch (Exception e) {
		// TODO: handle exception
    	list_due_date="Due Date";
    	System.out.println("DUE DATE TO B SET FROM CATCH:"+intuserid);
    	
	}	
}
/*Type :Function
name:getDC_data
return type:void
date:12-12-2011
purpose:Fetching diabetic/chronic data from local db*/
public void getDC_data()
{
	try
    {
    	
    	Cursor cur=data.getDC_data(intuserid);
    	curlength=cur.getCount();
		while(cur.moveToNext())
		{
			isdiabetic=cur.getString(2);
			ischronic=cur.getString(3);
			heightincm=cur.getString(4);
			weightinkg=cur.getString(5);
						
			System.out.println("1.DC DATA FROM TRY:"+isdiabetic+" AND "+ischronic);
			System.out.println("2.DC DATA FROM TRY:"+heightincm+" AND "+weightinkg);
		}
		if(curlength==0)
		{
			isdiabetic="No";
			ischronic="No";
			heightincm="";
			weightinkg="";
		}
    }
    catch (Exception e) {
		// TODO: handle exception
    	isdiabetic="No";
		ischronic="No";
		heightincm="";
		weightinkg="";
    	System.out.println("DC DATA FROM CATCH:"+isdiabetic+" AND "+ischronic);
    	
	}	
}
	
/*Type :Function
name:onItemClick
return type:void
date:12-12-2011
purpose:Handling on click listener for each and every list row and perform the action accordingly*/
	public void onItemClick(AdapterView<?> a, View v, int position, long id) 
	{
		// TODO Auto-generated method stub
		
		
		if(position==0)
		{
			Intent i=new Intent(Setting.this,Enter_due_date.class);
			Bundle bun=new Bundle();
			bun.putString("UNAME",struname);
			bun.putString("PWD", strpwd);
			bun.putInt("USERID", intuserid);
			bun.putString("RETURNFLAG", return_flag);
			i.putExtras(bun);
			startActivityForResult(i,REQ_CODE);
		}
		if(position==1)
		{
			
			getDC_data();
			
			
			Toast.makeText(this, lv1.getItemAtPosition(1).toString(), Toast.LENGTH_LONG).show();
			
			strdc_sync="N";
			
			if(isdiabetic.equals("Yes"))
			{
				_selections[0]=true;
			}
			else
			{
				_selections[0]=false;
			}
			if(ischronic.equals("Yes"))
			{
				_selections[1]=true;
			}
			else
			{
				_selections[1]=false;
			}
			function();
			
			
		}
		if(position==2)
		{
			
			
			LayoutInflater factory = LayoutInflater.from(this);
            final View textEntryView = factory.inflate(R.layout.enter_height, null);
            final EditText edtht1=(EditText) textEntryView.findViewById(R.id.edtht1);
            TextView txtht1=(TextView) textEntryView.findViewById(R.id.txtht1);
            final EditText edtht2=(EditText) textEntryView.findViewById(R.id.edtht2);
            TextView txtht2=(TextView) textEntryView.findViewById(R.id.txtht2);
            if(mysession.getheightunit().equals("in"))
            {
            	txtht1.setText("ft");
            	txtht2.setText("in");
            }
            else
            {
            	txtht1.setText("cm");
            	txtht2.setText("");
            	edtht2.setVisibility(View.GONE);
            }
            AlertDialog.Builder builder= new AlertDialog.Builder(Setting.this);
            builder.setTitle("Enter Your Height");
               builder.setView(textEntryView);
                
               builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	
                    	if(mysession.getheightunit().equals("in"))
                        {
                    		heightincm=edtht1.getText().toString()+" ft "+edtht2.getText().toString()+" in";
                        }
                        else
                        {
                        	heightincm=edtht1.getText().toString()+" cm";
                        }
                    	if(curlength==0)
                        {
                    	data.InsertDC(""+intuserid, isdiabetic, ischronic,heightincm,weightinkg, strdc_sync);
                    	Toast.makeText(Setting.this, "Data Saved Successfully!!!", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                        	data.updateDC(""+intuserid, isdiabetic, ischronic,heightincm,weightinkg, strdc_sync);
                        	Toast.makeText(Setting.this, "Data Updated Successfully!!!", Toast.LENGTH_LONG).show();
                        }
                    	
                    	getDC_data();
                    	refreshlistitems();
                        bindlistview();
                    	
                        
                    }
                });
               builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked cancel so do some stuff */
                    }
                });
                AlertDialog alert = builder.create();
                alert.show(); 
                

		}
		
		if(position==3)
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
            AlertDialog.Builder builder= new AlertDialog.Builder(Setting.this);
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
                    	if(curlength==0)
                        {
                    	data.InsertDC(""+intuserid, isdiabetic, ischronic,heightincm,weightinkg, strdc_sync);
                    	Toast.makeText(Setting.this, "Data Saved Successfully!!!", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                        	data.updateDC(""+intuserid, isdiabetic, ischronic,heightincm,weightinkg, strdc_sync);
                        	Toast.makeText(Setting.this, "Data Updated Successfully!!!", Toast.LENGTH_LONG).show();
                        }
                    	
                    	getDC_data();
                    	refreshlistitems();
                        bindlistview();
                    }
                });
               builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked cancel so do some stuff */
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
        
                
	       
	        	
	        		
	        		
		}
		if(position==4)
		{
			
			getDC_data();
    		final CharSequence[] items = {"in", "cm"};
    		
    		
    		if(mysession.getheightunit().equals("in"))
			{
				select_height=0;
			}
			else
			{
				select_height=1;
			}

    		AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
    		builder.setTitle("Select Height Units");
    		
    		builder.setSingleChoiceItems(items, select_height, new DialogInterface.OnClickListener() 
    		{
    			
			    public void onClick(DialogInterface dialog, int item) {
			    	Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
			        unit_of_height=(String) items[item];
			        mysession.setheightunit(unit_of_height);
			    	if(select_height==1)
			    	{
			    		
			    		   if(heightincm.equals(""))
			    		   {
			    			   
			    		   }
			    		   else
			    		   {
			    		   heightincm=heightincm.replace(" cm", "");
			    		   double cm=Double.parseDouble(heightincm);
			    	       int w= (int) (cm / 30.48); 
			    	       int w1=(int) (cm/2.54);
			    	       int ans=w1-(w*12);
			    	       heightincm=w+" ft "+ans+" in";
			    	       
			    	       System.out.println("HEIGHT IN FEET:"+heightincm);
			    		   }

			    	}
			    	else
			    	{
			    			if(heightincm.equals(""))
			    		    {
			    			   
			    		    }
			    		    else
			    		   {
			    		   feet=heightincm.split(" ft");
			    		   feet[1]=feet[1].replace(" in","");
			    	       
			    	       double feetdefault=30.48;
			    	       double inchdefault=2.54;
			    	       double ft=Double.valueOf(feet[0]).doubleValue();
			    	       double in=Double.valueOf(feet[1]).doubleValue();
			    	       
			    	       double fd=ft*feetdefault;
			    	       double ind=in*inchdefault;
			    	       double fin=fd+ind;
			    	       heightincm=fin+" cm";
			    	       System.out.println("HEIGHT IN CM:"+heightincm);
			    	       
			    		   }
			    		
			    	}
			    	
                	if(curlength==0)
                    {
                	data.InsertDC(""+intuserid, isdiabetic, ischronic,heightincm,weightinkg, strdc_sync);
                	Toast.makeText(Setting.this, "Data Saved Successfully!!!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                    	data.updateDC(""+intuserid, isdiabetic, ischronic,heightincm,weightinkg, strdc_sync);
                    	Toast.makeText(Setting.this, "Data Updated Successfully!!!", Toast.LENGTH_LONG).show();
                    }
                	alert.dismiss();
                	getDC_data();
                	refreshlistitems();
                    bindlistview();
			        
			        	
			        
			    }
			});
    		
    		
    		builder.setNegativeButton("Cancel",
    				new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog, int id) {
    						
    						
    					}
    				});
    		alert = builder.create();
    		alert.show();
    		

		}
		if(position==5)
		{
			getDC_data();
			final CharSequence[] items = {"kg", "lb"};
			select_weight=-1;
			if(mysession.getweightunit().equals("kg"))
			{
				select_weight=0;
			}
			else
			{
				select_weight=1;
			}

    		AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
    		builder.setTitle("Select Weight Units");
    		
    	
    		builder.setSingleChoiceItems(items, select_weight, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			    	
			        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
			        unit_of_weight=(String) items[item];
			        mysession.setweightunit(unit_of_weight);
			        
			        if(select_weight==0)
			        {
			        	if(weightinkg.equals(""))
			        	{
			        		
			        	}
			        	else
			        	{
			        		
			        		weightinkg=weightinkg.replace(" kg", "");
				        	double lbdefault=2.20462262185;
				            double wt1=Double.valueOf(weightinkg).doubleValue();
				            double wt2=wt1*lbdefault;
				            //int r = (int) Math.round(wt2*100);
				            //double f = r / 100.0;

				            weightinkg=wt2+" lb";
				            System.out.println("WEIGHT IN KG"+weightinkg);
			        	
			        	}

			        	
			        }
			        else
			        {
			        	if(weightinkg.equals(""))
			        	{
			        		
			        	}
			        	else
			        	{
			        		weightinkg=weightinkg.replace(" lb", "");
				            double kgdefault=0.45359237;      
				            double kgwt1=Double.valueOf(weightinkg).doubleValue();
				            double kgwt2=kgwt1*kgdefault;
				            weightinkg=kgwt2+" kg";
				            System.out.println("WEIGHT IN LB:"+weightinkg);
			        	
			        	}
			        	

			        }
			        if(curlength==0)
                    {
                	data.InsertDC(""+intuserid, isdiabetic, ischronic,heightincm,weightinkg, strdc_sync);
                	Toast.makeText(Setting.this, "Data Saved Successfully!!!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                    	data.updateDC(""+intuserid, isdiabetic, ischronic,heightincm,weightinkg, strdc_sync);
                    	Toast.makeText(Setting.this, "Data Updated Successfully!!!", Toast.LENGTH_LONG).show();
                    }
			        alert.dismiss();
			        getDC_data();
			        refreshlistitems();
		            bindlistview();
			    }
			});
    		    		
    		builder.setNegativeButton("Cancel",
    				new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog, int id) {
    						
    					}
    				});
    		alert = builder.create();
    		alert.show();
    		
    		
    		}
		
		if(position==6)
		{
			
    		final CharSequence[] items = {"Gallery", "CoveredFlow"};
    		
    		
    		if(mysession.getgallery_type().equals("Gallery"))
			{
				select_phot_view_type=0;
			}
			else
			{
				select_phot_view_type=1;
			}

    		AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
    		builder.setTitle("Select Photo View Type");
    		
    		builder.setSingleChoiceItems(items, select_phot_view_type, new DialogInterface.OnClickListener() 
    		{
    			
			    public void onClick(DialogInterface dialog, int item) {
			    	Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
			        photo_view_type=(String) items[item];
			        mysession.setgallery_type(photo_view_type);
			    	if(select_phot_view_type==1)
			    	{
			    		
			    		   mysession.setgallery_type("Gallery");

			    	}
			    	else
			    	{
			    		   mysession.setgallery_type("CoveredFlow");
			    		
			    	}
			    	
			    	alert.dismiss();
                	getDC_data();
                	refreshlistitems();
                    bindlistview();
			        
			        	
			        
			    }
			});
    		
    		
    		builder.setNegativeButton("Cancel",
    				new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog, int id) {
    						
    						
    					}
    				});
    		alert = builder.create();
    		alert.show();
		}

		
		
	}
	/*Type :Function
	name:bindlistview
	return type:void
	date:12-12-2011
	purpose:Binding data to listview*/
	public void bindlistview()
	{
		setting.clear();
		setting.add(list_due_date);
        setting.add(list_diabetic_chronic);
        setting.add(list_height);
        setting.add(list_pre_pregnancy_wt);
        setting.add(list_height_unit);  
        setting.add(list_weight_unit);
        setting.add(photo_view_type);
        
        lv1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,setting));
		lv1.setTextFilterEnabled(true);
		lv1.setOnItemClickListener(this);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQ_CODE){

			if (resultCode == RESULT_OK) 
			{

						      	
		      	System.out.println("ON ACTIVITY RESULT FROM SETTING:");
		      	getdue_date();
		      	bindlistview();
		      	

			} 
		  
		
	    	    
	  }
	}
	/*Type :Function
	name:function
	return type:void
	date:12-12-2011
	purpose:Opening alert dialog asking for diabetic or chronic*/
	public void function()
    {
    	final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
    	alertbox.setTitle("Are U Diabetic Or Chronic?");
    	
    	alertbox.setMultiChoiceItems( complete_name, _selections, new DialogSelectionClickHandler() );
    	alertbox.setPositiveButton( "Done", new DialogButtonClickHandler() );
    	alertbox.setNegativeButton("Cancel", new DialogButtonClickHandler() );
		alertbox.show();
    	 
    }   
	/*Type :class
	name:onClick
	return type:void
	date:12-12-2011
	purpose:Handling on check listener for checkboxes*/
    public class DialogSelectionClickHandler implements DialogInterface.OnMultiChoiceClickListener
	{
		public void onClick( DialogInterface dialog, int clicked, boolean selected )
		{
			System.out.println( "ME "+ complete_name[ clicked ] + " selected: " + selected );
			
			if(selected==true)
			{
				count++;
					if(complete_name[ clicked ].equals("Diabetic"))
					{
					 isdiabetic="Yes";
					 System.out.println("IN SELECTED TRUE :"+isdiabetic);
					}
				
					if(complete_name[ clicked ].equals("Chronic"))
					{
					 ischronic="Yes";
					 System.out.println("IN SELECTED TRUE :"+ischronic);
					}
			
			}
			else
			{
				count--; 
				if(complete_name[ clicked ].equals("Diabetic"))
				{
				 isdiabetic="No";
				 System.out.println("IN SELECTED FALSE :"+isdiabetic);
				}
			
				if(complete_name[ clicked ].equals("Chronic"))
				{
				 ischronic="No";
				 System.out.println("IN SELECTED FALSE :"+ischronic);
				}
				
			}
			
			
			
			
		}

		
	}  
	
    /*Type :class
	name:onClick
	return type:void
	date:12-12-2011
	purpose:Handling on click listener for buttons on the dialog*/
	public class DialogButtonClickHandler implements DialogInterface.OnClickListener
	{
		public void onClick( DialogInterface dialog, int clicked )
		{
						
			switch( clicked )
			{
				case DialogInterface.BUTTON_POSITIVE:
					System.out.println("Positive Button Clicked");
					if(curlength==0)
                    {
                	data.InsertDC(""+intuserid, isdiabetic, ischronic,heightincm,weightinkg, strdc_sync);
                	Toast.makeText(Setting.this, "Data Saved Successfully!!!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                    	data.updateDC(""+intuserid, isdiabetic, ischronic,heightincm,weightinkg, strdc_sync);
                    	Toast.makeText(Setting.this, "Data Updated Successfully!!!", Toast.LENGTH_LONG).show();
                    }
					break;
					
				case DialogInterface.BUTTON_NEGATIVE:
					System.out.println("Negative Button Clicked");
					Toast.makeText(Setting.this, "Nothing Saved", Toast.LENGTH_LONG).show();
					break;
			}
			
		}
	}
	
	
//overriding devices back button
	@Override
	public void onBackPressed()
	{
		Intent i=new Intent(Setting.this,Dashboard.class);
		Bundle bun=new Bundle();
		bun.putString("UNAME",struname);
		bun.putString("PWD", strpwd);
		bun.putInt("USERID", intuserid);
		i.putExtras(bun);
		startActivity(i);
	}
	
	}
