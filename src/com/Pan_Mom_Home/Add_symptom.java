//This class shows the list of symptom which the user can add to any of her week
package com.Pan_Mom_Home;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Add_symptom  extends CustomWindow{
	

	
	Button btndone,btncancel;
	Button btnhome;
	DatabaseHelper data;
	
	String flag="false";
	String struname,struserid;
	Pan_mom_session mysession;
	GridView grid_main;
	String strselected_week,strsymptomfoundid;
	int intuserid,count=0;
	protected static boolean[] _selections;
	String[] added_symptom,id;
	private ArrayList<String> symptom = new ArrayList<String>();
	private ArrayList<String> symptom_descri = new ArrayList<String>();
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_symptom_grid);
      
        
        /*initializing classes*/
        mysession=new Pan_mom_session(this);
        data=new DatabaseHelper(this);
        
        /*getting data from session*/
        struname=mysession.getUserName();
        struserid=mysession.getUserId();
        strselected_week=mysession.getselected_week();
        
        /*if there are no values in the session then getting data from previous activity*/
        if(struserid.equals(""))
        {
        	System.out.println("SAVED USER ID IS IN IF...."+struserid);
        	if(struname.equals(""))
	        {
	        	Bundle bun=getIntent().getExtras();
		        struname=bun.getString("UNAME");
		        intuserid=bun.getInt("USERID");
		        strselected_week=bun.getString("SELECTED_WEEK");
	        }
        }
        else
        {
        intuserid=Integer.parseInt(struserid);
        System.out.println("SAVED USER ID IS IN ELSE...."+intuserid);
        }    
        
        getsymptom();
        
        /*inflating the views to use in this class*/
        btndone=(Button) findViewById(R.id.done);
		btncancel=(Button) findViewById(R.id.cancel);
		 
        
        grid_main = (GridView)findViewById(R.id.GridView1);
        
        //binding data to gridview
        grid_main.setAdapter(new ImageAdapter(this));
       
        
        btndone.setOnClickListener(new OnClickListener() {
			
		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//getting symptoms from local db
				Cursor cur=data.getSymptom();
				
				id=new String[cur.getCount()];
				int x=0;
				while(cur.moveToNext())
				{
					
					String sid=cur.getString(0);
					id[x]=sid;
					x++;
				}
				
				
				added_symptom=new String[count];
				int k=0;
				
				//adding selected symptoms to an array
				for(int j=0;j<_selections.length;j++)
				{
					if(_selections[j]==true)
					{
						added_symptom[k]=symptom.get(j);
						k++;
					}
					
					
				}
				
				
				//adding all symptom from local db to an array
				for(int j=0;j<_selections.length;j++)
				{
					
					
					if(_selections[j]==true)
					{
						System.out.println("Selected Symptoms id is...."+id[j]);
						
						
						Cursor curs=data.getSymptom_validate(Integer.parseInt(strselected_week),Integer.parseInt(struserid),Integer.parseInt(id[j]));
						int len=curs.getCount();
						System.out.println("Length is:::::"+len);
						if(len>0)
						{
							System.out.println("Entry already exists...for... "+id[j]);
						}
						else
						{							
							data.InsertSymptom_to_user(struserid,id[j],strselected_week,"N");
						
						}
					
					}
				}
				
				
				//getting back to previous activity
				Intent i=new Intent();
				setResult(RESULT_OK, i);  
				finish(); 
				
				
			}
		});
		btncancel.setOnClickListener(new OnClickListener() {
			

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i=new Intent(Add_symptom.this,Symptom.class);
				Bundle bun=new Bundle();
    			bun.putString("UNAME",struname);		
    			bun.putInt("USERID", intuserid);
    			i.putExtras(bun);
				startActivity(i);
				
			}
		});
		int size=symptom.size();
		
		System.out.println("Array length is:::::::::"+size);
		_selections =  new boolean[size];
        
	}
	
	
	
	//Adapter class to bind data to gridview and display it.
	public class ImageAdapter extends BaseAdapter{
		Context mContext;
		
		public ImageAdapter(Context c){
			mContext = c;
		}
		
		public int getCount() {
			
			int cnt=symptom.size(); 
			return cnt;
		} 
		
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v;
			
				LayoutInflater li = getLayoutInflater();
				v = li.inflate(R.layout.add_symptom, null);
				
				
				System.out.println("$$$$$$$$$"+symptom.get(position));
				TextView tv = (TextView)v.findViewById(R.id.textdata);
				tv.setText(symptom.get(position));
				
		
				final CheckBox chk = (CheckBox)v.findViewById(R.id.check);
				
				
				chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					
					public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
						// TODO Auto-generated method stub
						
						if(chk.isChecked())
						{
							flag="true";
													
							_selections[position]=true;
							
							count++; 
							
						//Toast.makeText(Add_symptom.this, ""+symptom.get(position)+" Checked", Toast.LENGTH_LONG).show();
						}
						else
						{  
							flag="false";
							count--;
							_selections[position]=false;
							//Toast.makeText(Add_symptom.this, ""+symptom.get(position)+" UnChecked", Toast.LENGTH_LONG).show();
						}
						
					}
				});
				
				
			
							
			return v;
		}
		public Object getItem(int arg0) {
    
			return null;
		}
		public long getItemId(int arg0) {

			return 0;
		}
	
}
	
	
	/*Type :Function
    name:getsymptom
    return type:void
    date:12-12-2011
    purpose:Gets symptoms from local db and bind it to the arraylist*/
	public void getsymptom()
	{
		Cursor cur=data.getSymptom();
							
		while(cur.moveToNext())
    	{
    		
    	
    		 
    		String strsymptom1=cur.getString(1); 
    		String strsymptom_descri1=cur.getString(2);
    		
    	    symptom.add(strsymptom1);
    	    symptom_descri.add(strsymptom_descri1);
    	    
    	    
    	    System.out.println("1..."+strsymptom1);
    		System.out.println("2..."+strsymptom_descri1);
    	
        	       	        	
    		
    	}
    	cur.close();
    	
    	
    	for(int i=0;i<symptom.size();i++)
    	{
    		System.out.println("#######"+symptom.get(i));
    	}
    	
	}
	

}
