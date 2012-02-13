//Images are can be shown in coverflow pattern
package com.Pan_Mom_Home;

/*
 * Copyright (C) 2010 Neil Davies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This code is base on the Android Gallery widget and was Created 
 * by Neil Davies neild001 'at' gmail dot com to be a Coverflow widget
 * 
 * @author Neil Davies
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class CoverFlowExample extends Activity {
	
	int mGalleryItemBackground;
     Context mContext;
     ArrayList<String> listCountrys;
	 ArrayList<Bitmap> listFlags;
	 Activity activity;
	 String roots;
	 Bitmap newImage;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     
     Bundle bun=getIntent().getExtras();
     roots=bun.getString("path"); 
		
	    
	 prepareLists(roots);
     
     CoverFlow coverFlow;
     coverFlow = new CoverFlow(this);
     
     coverFlow.setAdapter(new ImageAdapter(CoverFlowExample.this,listCountrys, listFlags,this));

     ImageAdapter coverImageAdapter =  new ImageAdapter(CoverFlowExample.this,listCountrys, listFlags,this);
     
     
     
     coverFlow.setAdapter(coverImageAdapter);
     coverFlow.setSpacing(-25);
     coverFlow.setAnimationDuration(1000);
     
     coverFlow.setBackgroundColor(Color.parseColor("#000000"));
     setContentView(coverFlow);
    }
    
    /*Type :Function
    name:prepareLists
    return type:void
    date:12-12-2011
    purpose:filling arraylist to bind to the adapter for gridview*/
    public void prepareLists(String dirPath)
    {
    	
    	
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
      		
      		System.gc();
      	  Bitmap bmp = BitmapFactory.decodeFile(f.getPath() + "/" + bitmapFileName);
      	  // do something with bitmap
      	  
      	if(bmp!=null)	     
        {
        	 newImage = Bitmap.createScaledBitmap(bmp, 180, 180, true);
            
        }
      	  listFlags.add(newImage);
      	  
      	}

                    
    }	
    
    /*Type :Class
    name:ImageAdapter
    return type:void
    date:12-12-2011
    purpose:adapter class to bind data to gridview and load it*/
 public class ImageAdapter extends BaseAdapter {
     

     
     Context mContext;
     private ArrayList<String> listCountrys;
     private ArrayList<Bitmap> listFlags;
     private Activity activity; 
     
     
     public ImageAdapter(Activity activity,ArrayList<String> listCountry, ArrayList<Bitmap> listFlag,Context c) {
      
      
      super();
		this.listCountrys = listCountry;
		this.listFlags = listFlag;
		this.activity = activity;
		mContext = c;
		TypedArray a = mContext.obtainStyledAttributes(R.styleable.Gallery1);
        mGalleryItemBackground = a.getResourceId(
                R.styleable.Gallery1_android_galleryItemBackground, 0);
        a.recycle();
     
     }
  

     public int getCount() {
    	 return listFlags.size();
     }

     public Object getItem(int position) {
         return position;
     }

     public long getItemId(int position) {
         return position;
     }

     public View getView(int position, View convertView, ViewGroup parent) {

      //Use this code if you want to load from resources
         ImageView i = new ImageView(mContext);
         i.setImageBitmap(listFlags.get(position));
         i.setLayoutParams(new CoverFlow.LayoutParams(250, 230));
         i.setScaleType(ImageView.ScaleType.CENTER_INSIDE); 
         
         //Make sure we set anti-aliasing otherwise we get jaggies
         BitmapDrawable drawable = (BitmapDrawable) i.getDrawable();
         drawable.setAntiAlias(true);
         return i;
      
      
     }
   /** Returns the size (0.0f to 1.0f) of the views 
      * depending on the 'offset' to the center. */ 
      public float getScale(boolean focused, int offset) { 
        /* Formula: 1 / (2 ^ offset) */ 
          return Math.max(0, 1.0f / (float)Math.pow(2, Math.abs(offset))); 
      } 

 }
}

