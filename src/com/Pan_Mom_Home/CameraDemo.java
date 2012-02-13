//Through this class opening the devices camera and capturing the photograph
package com.Pan_Mom_Home;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

public class CameraDemo extends Activity {
	private static final String TAG = "CameraDemo";
	Camera camera;
	Preview preview;
	Button buttonClick,buttonback;
	String struname,struserid;
	int intuserid,week;
	DatabaseHelper data;
	Pan_mom_session mysession; 
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.takephoto);   
		
		
		//getting data from previous activity
		 Bundle bun=getIntent().getExtras();
		 struname=bun.getString("UNAME");
	     intuserid=bun.getInt("USERID");
		 week=bun.getInt("CURRENTWEEK");
		 struserid=Integer.toString(intuserid);
		 
		 
		 //initializing some classes
		preview = new Preview(this);
		((FrameLayout) findViewById(R.id.preview)).addView(preview);
		data=new DatabaseHelper(this);
        mysession=new Pan_mom_session(this);
                
          
		//capturing the photograph
		buttonClick = (Button) findViewById(R.id.buttonClick);
		buttonClick.setOnClickListener( new OnClickListener() {
			public void onClick(View v) {
				
				preview.camera.takePicture(shutterCallback, rawCallback, jpegCallback);
			
				
				
			}
		});
		
		
				

		Log.d(TAG, "onCreate'd");
	}


	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
			Log.d(TAG, "onShutter'd");
		}
	};

	/** Handles data for raw picture */
	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d(TAG, "onPictureTaken - raw");
			
			
			
		}
	}; 

	/** Handles data for jpeg picture */
	PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			
			//writing images to the sdcard at particular path and returning back to previous activity 	
			int ff=new File(Environment.getExternalStorageDirectory()+"/Pan_Momies_"+struserid+"_"+struname+"/week"+week).list().length;
			    ff=ff+1;
			    String ss=String.valueOf(ff);
				
			FileOutputStream outStream=null ;
			     
			try {
			
				outStream = new FileOutputStream(Environment.getExternalStorageDirectory()+"/Pan_Momies_"+struserid+"_"+struname+"/week"+week+"/"+ss+".jpg");
				//outStream = new FileOutputStream(Environment.getExternalStorageDirectory()+"/Pan_Momies/week21"+"/sush.jpg");
				//outStream = new FileOutputStream(String.format("/sdcard/%d.jpg", System.currentTimeMillis()));
				outStream.write(data);
				String we=data.toString();
				outStream.close();
				Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
				Log.d(TAG, "onPict88888*********************************************** " + we);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally { 
			}
			
			Log.d(TAG, "onPictureTaken - jpeg.......................................................");
			Intent i=new Intent();
			setResult(RESULT_OK,i); 
			finish();
		}
	};

}
