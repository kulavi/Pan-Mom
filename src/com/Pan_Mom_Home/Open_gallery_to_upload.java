//Loading images from gallery and showing it in the gridview
package com.Pan_Mom_Home;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class Open_gallery_to_upload extends Activity {
	private int count;
	private Bitmap[] thumbnails;
	private boolean[] thumbnailsselection;
	private String[] arrPath;
	private String[] selected_thumbnail;
	private ImageAdapter imageAdapter;
	String weekno,struname,struserid;
	int intuserid;
	int cnt = 0;
	protected static boolean[] _selections;
	Pan_mom_session mysession;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.open_gallery);
		
		
		
		mysession=new Pan_mom_session(this);
		
		struname=mysession.getUserName();
        struserid=mysession.getUserId();
        if(struserid.equals(""))
        {
        	System.out.println("SAVED USER ID IS IN IF...."+struserid);
        	if(struname.equals(""))
	        {
	        	Bundle bun=getIntent().getExtras();
	        	weekno=bun.getString("WEEKNO");
		        struname=bun.getString("UNAME");
		        intuserid=bun.getInt("USERID");
	        }
        }
        else
        {
        	
        	Bundle bun=getIntent().getExtras();
            weekno=bun.getString("WEEKNO");
            
        intuserid=Integer.parseInt(struserid);
        System.out.println("SAVED USER ID IS IN ELSE...."+intuserid);
        }  

		final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
		final String orderBy = MediaStore.Images.Media._ID;
		Cursor imagecursor = managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
				null, orderBy);
		int image_column_index = imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
		this.count = imagecursor.getCount();
		this.thumbnails = new Bitmap[this.count];
		this.arrPath = new String[this.count];
		this.thumbnailsselection = new boolean[this.count];
		for (int i = 0; i < this.count; i++) {
			imagecursor.moveToPosition(i);
			int id = imagecursor.getInt(image_column_index);
			int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
			thumbnails[i] = MediaStore.Images.Thumbnails.getThumbnail(
					getApplicationContext().getContentResolver(), id,
					MediaStore.Images.Thumbnails.MICRO_KIND, null);
			arrPath[i]= imagecursor.getString(dataColumnIndex);
		}
		GridView imagegrid = (GridView) findViewById(R.id.PhoneImageGrid);
		imageAdapter = new ImageAdapter();
		imagegrid.setAdapter(imageAdapter);
		imagecursor.close();
		

		final Button selectBtn = (Button) findViewById(R.id.selectBtn);
		selectBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				final int len = thumbnailsselection.length;
				
				String selectImages = "";
				for (int i =0; i<len; i++)
				{
					if (thumbnailsselection[i])
					{
						cnt++; 
						selectImages = selectImages + arrPath[i] + "@|@";
					}
				}
				if (cnt == 0)
				{
					Toast.makeText(getApplicationContext(),
							"Please select at least one image",
							Toast.LENGTH_LONG).show();
				}
				else 
				{
					Toast.makeText(getApplicationContext(),
							"You've selected Total " + cnt + " image(s).",
							Toast.LENGTH_LONG).show();
					Log.d("SelectedImages", selectImages);
					
				} 
				
				//uploading images to users folder from gallery
					if(selectImages.length()>1)
					{
 					selected_thumbnail=selectImages.split("@|@");
					
						try
						{
							                     
						   File sd = Environment.getExternalStorageDirectory();

						   if (sd.canWrite()) 
						   {
							   
							   for(int i=0;i<selected_thumbnail.length;i=i+2)
								{
									String mSelectedImagePath =selected_thumbnail[i] ;
									System.out.println("mSelectedImagePath : " + mSelectedImagePath);
									
									int ff=new File(Environment.getExternalStorageDirectory()+"/Pan_Momies_"+struserid+"_"+struname+"/week"+weekno).list().length;
								    ff=ff+1;
								    String ss=String.valueOf(ff);
							   
							   System.out.println("(sd.canWrite()) = " + (sd.canWrite()));
							                            
							   String destinationImagePath=ss+".jpg";   // this is the destination image path.
							                           
							   File source = new File(mSelectedImagePath );
							                            
							   File destination= new File(sd+"/Pan_Momies_"+struserid+"_"+struname+"/week"+weekno+"/", destinationImagePath);
							                            
							   if (source.exists()) 
							   {
							                               
								   FileChannel src = new FileInputStream(source).getChannel();
							                                
								   FileChannel dst = new FileOutputStream(destination).getChannel();
							                                
								   dst.transferFrom(src, 0, src.size());// copy the first file to second.....
							                                
								   src.close();
							                                
								   dst.close();
							                               
								   Toast.makeText(getApplicationContext(), "Check the copy of the image in the same path as the gallery image. File is name file.jpg", Toast.LENGTH_LONG).show();
							                           
							   }
								
								}
							                        
						   }
						   		else
						   		{							          
						         
						   			Toast.makeText(getApplicationContext(), "SDCARD Not writable.", Toast.LENGTH_LONG).show();
							       
						        }
						   
						   	Intent i=new Intent();
							setResult(RESULT_OK,i); 
							finish();
							               
						     }
					
						catch (Exception e) 
						{
							                      
						   	 System.out.println("Error :" + e.getMessage());
						   	 
						}
					
					
					
					}
				}
			
		});
	}
	/*Type :Class
    name:ImageAdapter
    return type:void
    date:12-12-2011
    purpose:adapter to bind data to gridview*/
	public class ImageAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public ImageAdapter() {
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return count;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(
						R.layout.galleryitem, null);
				holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);
				holder.checkbox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);

				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.checkbox.setId(position);
			holder.imageview.setId(position);
			holder.checkbox.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					CheckBox cb = (CheckBox) v;
					int id = cb.getId();
					if (thumbnailsselection[id]){
						cb.setChecked(false);
						thumbnailsselection[id] = false;
					} else {
						cb.setChecked(true);
						thumbnailsselection[id] = true;
					}
				}
			});
			holder.imageview.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					int id = v.getId();
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.parse("file://" + arrPath[id]), "image/*");
					startActivity(intent);
				}
			});
			holder.imageview.setImageBitmap(thumbnails[position]);
			holder.checkbox.setChecked(thumbnailsselection[position]);
			holder.id = position;
			return convertView;
		}
	}
	class ViewHolder {
		ImageView imageview;
		CheckBox checkbox;
		int id;
	}
//overriding devices back button
	@Override
	public void onBackPressed()  
	{
		
		Intent i=new Intent();
		setResult(RESULT_OK,i); 
		finish();
	}
}

