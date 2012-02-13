//Adapter class for binding images to gallery
package com.Pan_Mom_Home;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class GridviewAdapters extends BaseAdapter
{
	 int mGalleryItemBackground;
	private Context mContext;
	private ArrayList<String> listCountrys;
	private ArrayList<Bitmap> listFlags;
	private Activity activity;
	public ImageView imgViewFlag;
	public TextView txtViewTitle;

	public GridviewAdapters(Activity activity,ArrayList<String> listCountry, ArrayList<Bitmap> listFlag,Context c) {
		super();
		this.listCountrys = listCountry;
		this.listFlags = listFlag;
		this.activity = activity;
		
		mContext = c;
	       
        // See res/values/attrs.xml for the  defined values here for styling
        TypedArray a = mContext.obtainStyledAttributes(R.styleable.Gallery1);
        mGalleryItemBackground = a.getResourceId(
                R.styleable.Gallery1_android_galleryItemBackground, 0);
        a.recycle();
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return listFlags.size();
	}


	public String getItem(int position) {
		// TODO Auto-generated method stub
		return listCountrys.get(position);
	}


	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(listFlags.get(position));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new Gallery.LayoutParams(100, 70));
        imageView.setBackgroundResource(mGalleryItemBackground);
        return imageView;
		
		
	}
}


