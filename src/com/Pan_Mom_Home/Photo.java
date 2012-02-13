//Showing the folders of images

package com.Pan_Mom_Home;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.ListActivity;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Photo extends Activity {
	
	
	Button btntake,btnupload;
    TextView txtlogout,txtname;
    final static int REQ_CODE=1;
    final static int REQ_CODE1=1;
    Dialog myDialog;
	 
	String struname,struserid,str_lmp,str_duedate;
	int intuserid;
	DatabaseHelper data;
	Pan_mom_session mysession;
	                        
	
	
	Bitmap bmp1;
	String root;
	String roots;
	  
	ImageView imageView1;
	int folderposition;
	
	
	int week;
	
	
	File sdImageMainDirectorys;
	
	
    /** Called when the activity is first created. */

	 GridviewAdapter mAdapter;
	 
	 ArrayList<String> listCountry;
	 ArrayList<Bitmap> listFlag;
	

	 TextView myPath;
	 GridView gridView;
	 
	 int mYear,mMonth,mDay;
	 int date,month,allday=0,reminday,year;
	 int arr[]={31,28,31,30,31,30,31,31,30,31,30,31};
	//private GridView gridView1;
	

	Log d;
	                  
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos);
         
        /*initializing classes*/
        data=new DatabaseHelper(this);
        mysession=new Pan_mom_session(this);
       
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
       
        myPath=(TextView) findViewById(R.id.path);
        btntake=(Button) findViewById(R.id.takephoto);
        btnupload=(Button) findViewById(R.id.uploadphoto);
        gridView = (GridView) findViewById(R.id.gridView1); 
       
        
        File folder = new File(Environment.getExternalStorageDirectory()+"/Pan_Momies_"+struserid+"_"+struname);

        if(folder.exists())
        {
        	root=Environment.getExternalStorageDirectory()+"/Pan_Momies_"+struserid+"_"+struname;
        	prepareList(root);
        
        // prepared arraylist and passed it to the Adapter class
        mAdapter = new GridviewAdapter(this,listCountry, listFlag);

        // Set custom adapter to gridview
        gridView.setAdapter(mAdapter);
                       
        }
        // Implement On Item click listener
        gridView.setOnItemClickListener(new OnItemClickListener()
        {
		
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Toast.makeText(Photo.this, mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
				
				roots=Environment.getExternalStorageDirectory()+"/Pan_Momies_"+struserid+"_"+struname+"/"+mAdapter.getItem(position);
			    String view_type=mysession.getgallery_type();
			    
			    if(view_type.equals("Gallery"))
			    {
				 System.out.println("PATH IS:::::::"+roots);
				 Intent i=new Intent(Photo.this, Gallery_view.class);
				 Bundle bun=new Bundle();
				 bun.putString("path",roots);
				 i.putExtras(bun);
				 startActivityForResult(i, REQ_CODE);
			    }
			    if(view_type.equals("CoveredFlow"))
			    {
			    	System.out.println("PATH IS:::::::"+roots);
					 Intent i=new Intent(Photo.this, CoverFlowExample.class);
					 Bundle bun=new Bundle();
					 bun.putString("path",roots);
					 i.putExtras(bun);
					 startActivityForResult(i, REQ_CODE);
			    	
			    }
								
				
			}
		});
          

        btntake.setOnClickListener(new OnClickListener() {
    		
         	 
        	public void onClick(View v) {
        		
        		
        		String pathname=Environment.getExternalStorageDirectory().toString();
                System.out.println("COMPLETE PATH IS:"+pathname);
                 
                
                
                   
        		  sdImageMainDirectorys=new File(Environment.getExternalStorageDirectory()+"/Pan_Momies_"+struserid+"_"+struname+"/week"+week);
        		 
        		  if(!sdImageMainDirectorys.exists())
        		  {
        		  sdImageMainDirectorys.mkdirs();
        		  }
        		 
        		 if(!sdImageMainDirectorys.mkdirs())
        		 {
        			 System.out.println("Problem Creating directory "+sdImageMainDirectorys.toString());
        		 }
        		 else
        		 {
        			 System.out.println(sdImageMainDirectorys.toString()+" Created successfully");
        		 }
        		Intent i=new Intent(Photo.this, CameraDemo.class);
        		Bundle bun=new Bundle();
        		bun.putInt("CURRENTWEEK", week);
        		bun.putString("UNAME", struname);
                bun.putInt("USERID", intuserid);
                i.putExtras(bun);
        		startActivityForResult(i,REQ_CODE);
    		}
    	});
        
        btnupload.setOnClickListener(new OnClickListener() {
    		
        	 
        	public void onClick(View v) {
        		
        		myDialog = new Dialog(Photo.this);
	        	myDialog.setContentView(R.layout.upload_to_week);
	        	myDialog.setTitle("Enter Week Number");
	        	myDialog.setCancelable(true); 
	        	
	        	
	        	final EditText edtno=(EditText) myDialog.findViewById(R.id.edtno);
	        	Button btnadd = (Button) myDialog.findViewById(R.id.btnadd);
	        	Button btncancel = (Button) myDialog.findViewById(R.id.btncancel);

	        	btnadd.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {

                    	//Checking if the week no is greater than current week?
                    	if(edtno.getText().toString().equals(""))
                    	{
                    		Toast.makeText(Photo.this, "Please Enter Week Number", Toast.LENGTH_LONG).show();
                    	}
                    	else
                    	{
                    		
                    		if(Integer.parseInt(edtno.getText().toString())>week)
                    		{
                    			Toast.makeText(Photo.this, "Week Number Should Be Smaller Than Current Week ", Toast.LENGTH_LONG).show();
                    		}
                    		else
                    		{
                    	String weekno=edtno.getText().toString();
                    	System.out.println("Week no is:"+weekno);
                    	
                    	sdImageMainDirectorys=new File(Environment.getExternalStorageDirectory()+"/Pan_Momies_"+struserid+"_"+struname+"/week"+weekno);
               		 
              		  	if(!sdImageMainDirectorys.exists())
              		  	{
              		  		sdImageMainDirectorys.mkdirs();
              		  	}
              		 
              		  	if(!sdImageMainDirectorys.mkdirs())
              		  	{
              		  		System.out.println("Problem Creating directory "+sdImageMainDirectorys.toString());
              		  	}
              		  	else
              		  	{
              		  		System.out.println(sdImageMainDirectorys.toString()+" Created successfully");
              		  	}
              		  	
              		  
                    	myDialog.dismiss();

                    	Intent i=new Intent(Photo.this, Open_gallery_to_upload.class);
                    	Bundle bun=new Bundle();
                    	bun.putString("WEEKNO", weekno);
                    	bun.putString("UNAME", struname);
                        bun.putInt("USERID", intuserid);
                    	i.putExtras(bun);
                		startActivityForResult(i,REQ_CODE1);
                		
                    	}
                    	}
                    	
                        }
                    });
	        	btncancel.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                 	                    	
                       	
                    	myDialog.dismiss();
                        }
                    });
        
                    myDialog.show();

        		
    		}
    	});
	 
    	
	}
	//Doing some work when called activity returns to the calling activity
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == REQ_CODE
				){

			if (resultCode == RESULT_OK)
			{   
				
				System.gc();
				File folder = new File(Environment.getExternalStorageDirectory()+"/Pan_Momies_"+struserid+"_"+struname);

		        if(folder.exists())
		        {
		        	root=Environment.getExternalStorageDirectory()+"/Pan_Momies_"+struserid+"_"+struname;
		        	prepareList(root);
		        
		        // prepared arraylist and passed it to the Adapter class
		        mAdapter = new GridviewAdapter(this,listCountry, listFlag);

		        // Set custom adapter to gridview 
		        gridView.setAdapter(mAdapter);
		        }
								

			} 
			
			if(requestCode==REQ_CODE1)
			{
				if(resultCode==RESULT_OK)
				{
					
					prepareList(root);
                    
                    // prepared arraylist and passed it to the Adapter class
                    mAdapter = new GridviewAdapter(Photo.this,listCountry, listFlag);

                    // Set custom adapter to gridview
                    gridView.setAdapter(mAdapter);
					Toast.makeText(this, "Photo Uploaded!!!", Toast.LENGTH_LONG).show();
				}
			}
		  
		
	    	    
	  }
	}
	/*Type :Function
    name:prepareList
    return type:void
    date:12-12-2011
    purpose:filling arraylist with data and binding it to some view*/
	public void prepareList(String dirPath)
    {
		Bitmap newImage = null;
		try
		{
		
			myPath.setText("Location: " + dirPath);
			listCountry = new ArrayList<String>();
			listFlag = new ArrayList<Bitmap>();
			bmp1 = BitmapFactory.decodeResource(getResources(),R.drawable.folder); 
			
			if(bmp1!=null)	     
		     {
		     	 newImage = Bitmap.createScaledBitmap(bmp1, 80, 80, true);
		         
		     }
	    	File f = new File(dirPath);
	      	File[] files = f.listFiles();
      	
	      	if(!dirPath.equals(root))  
	      	{
	
	      		listCountry.add(root);
	      	   		
	              
	      	}
      	
	      	for(int i=0; i < files.length; i++)
	      	{
	      			File file = files[i];
	      			
	      			if(file.isDirectory())
	      			{
	      				listFlag.add(newImage);
	      				listCountry.add(file.getName() + "/");
	      			}
	      			else
	      			{
	      				listFlag.add(newImage);
	      				listCountry.add(file.getName());
	      			}
	      	}
    	  
		}
		catch (Exception e) {
			// TODO: handle exception
			
			myPath.setText("No images to show");
		}
          
          
    }
	//call in order to free memory of Bitmap objects
	public void freeBitmaps() {
		for(Bitmap image: listFlag) {
			// also, it's better to check whether it is recycled or not
			if(image != null && !image.isRecycled()) {
				image.recycle();
				image = null; // null reference
			}
		}
	}

	// the destructor should also handle the memory in case it still holds memory
	protected void finalize() {
		freeBitmaps();
		listFlag.clear();
		listFlag=null;
	}
	
	/*Type :Function
    name:get_lmp_and_due_date_from_db
    return type:void
    date:12-12-2011
    purpose:Fetching LMP and due date from local db*/
	public void get_lmp_and_due_date_from_db()
    {
		Cursor cur1=data.getreg_info(intuserid);
    	
    	while(cur1.moveToNext())
    	{
    		 str_lmp=cur1.getString(6);
    		 str_duedate=cur1.getString(7);
    		
    	}
    	
    	cur1.close();
    	String[] arr_lmp=str_lmp.split("-");
    	
    	date=Integer.parseInt(arr_lmp[0].trim());
    	month=Integer.parseInt(arr_lmp[1].trim());
    	year=Integer.parseInt(arr_lmp[2].trim());
    	
    }
	//overriding devices back button
	@Override
	public void onBackPressed()
	{
		System.out.println("BACK BUTTON PRESSED!!!!");
	}
    
   }
