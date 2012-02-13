//Showing images in gallery view
package com.Pan_Mom_Home;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Gallery_view extends Activity {
	
	String roots,path;
	ArrayList<String> listCountrys;
	ArrayList<Bitmap> listFlags;
	GridviewAdapters mAdapters;
	
	TextView myPath;
	Gallery gallery;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);
        
        /*inflating the views to use in this class*/
        gallery=(Gallery) findViewById(R.id.picturesTaken);
        myPath=(TextView) findViewById(R.id.mypath);
        
        
        /*getting data from previous activity*/
        Bundle bun=getIntent().getExtras();
        roots=bun.getString("path"); 
		
	    myPath.setText("Location: " + roots);
		prepareLists(roots);
		
		viewlist();
        
        
        
        gallery.setOnItemClickListener(new OnItemClickListener()
        {
		
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Toast.makeText(Gallery_view.this, mAdapters.getItem(position), Toast.LENGTH_SHORT).show();
				
				//Intent i=null;
				path=roots+mAdapters.getItem(position);
				
			    /** Called when the activity is first created. */
									
				Bitmap newImage;
				System.gc();
				BitmapFactory.Options bfo = new BitmapFactory.Options();
	            bfo.inSampleSize = 2;
				ImageView imageView = (ImageView) findViewById(R.id.image1);  
				Bitmap bm = BitmapFactory.decodeFile(path, bfo);
				
				if(bm!=null)	     
			     {
			     	 newImage = Bitmap.createScaledBitmap(bm, 100, 100, true);
			         
			     }
                imageView.setImageBitmap(bm);
				 /*Intent i=new Intent(Gallery_view.this, ViewImage.class);
				 Bundle bun=new Bundle();
				 bun.putString("path",path);
				 i.putExtras(bun);
			 	 startActivity(i);  */   
								
			}
		});
	}
	/*Type :Function
    name:viewlist
    return type:void
    date:12-12-2011
    purpose:setting adapter to gallery view*/
	public void viewlist()
	 { 
		 mAdapters=new GridviewAdapters(Gallery_view.this,listCountrys, listFlags,this);
		 gallery.setAdapter(mAdapters);
	 }
	/*Type :Function
    name:prepareLists
    return type:void
    date:12-12-2011
    purpose:filling data to arraylist to bind to gallery*/
public void prepareLists(String dirPath)
{
	
	Bitmap newImage = null;
	  listCountrys = new ArrayList<String>();
	  listFlags = new ArrayList<Bitmap>();
	  
	File f = new File(dirPath);
  	File[] files = f.listFiles();
  	
  	if(!dirPath.equals(roots))
  	{

  		listCountrys.add(roots);
  		     		
          
  	}
  	
  	for(int i=0; i < files.length; i++)
  	{
  			File file = files[i];
  			
  			if(file.isDirectory())
  				listCountrys.add(file.getName() + "/");
  			else
  				listCountrys.add(file.getName());
   	}
	  
  	String[] fileNames = f.list(new FilenameFilter() { 
  	  public boolean accept (File dir, String name)
  	  {
  	      if (new File(dir,name).isDirectory())
  	         return false;  
  	        //return name.toLowerCase().endsWith(".png");
  	    return name.toLowerCase().endsWith(".jpg")||name.toLowerCase().endsWith(".png");
  	  }
  	});
  	
  	for(String bitmapFileName : fileNames) {
  	  Bitmap bmp = BitmapFactory.decodeFile(f.getPath() + "/" + bitmapFileName);
  	  
  	 if(bmp!=null)	     
     {
     	 newImage = Bitmap.createScaledBitmap(bmp, 80, 80, true);
         
     }

  	  // do something with bitmap
  	  listFlags.add(newImage);
  	  
  	} 

                
}	
//overriding devices back button
@Override
public void onBackPressed()
{
	
	//gallery.removeAllViewsInLayout();
	System.gc();
	Intent i=new Intent();
	setResult(RESULT_OK, i);  
	finish();
}
//call in order to free memory of Bitmap objects
public void freeBitmaps() {
	for(Bitmap image: listFlags) {
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
}
	
}