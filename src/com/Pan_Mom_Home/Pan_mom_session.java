package com.Pan_Mom_Home;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Pan_mom_session {
 
	private SharedPreferences sharedPref;
	private Editor editor;
	
	private static final String SHARED = "Twitter_Preferences";
	private static final String PAN_MOM_USER_NAME = "user_name";
	private static final String PAN_MOM_PASSWORD = "password";
	private static final String PAN_MOM_USER_ID = "userid";
	private static final String PAN_MOM_FLAG = "flag";
	private static final String RETURN_FLAG = "return_flag";
	private static final String WEIGHT_UNIT = "weight_unit";
	private static final String HEIGHT_UNIT = "height_unit";
	private static final String PRE_PREGNANCY_WEIGHT = "pre_pregnancy_weight";
	private static final String HEIGHT = "height";
	private static final String GALLERY_TYPE = "GALLERY";
	private static final String SELECTED_WEEK = "SELECTED_WEEK";
	private static final String ACTIVITY_TAG = "ACTIVITY_TAG";
	private static final String TRACKER_FLAG= "TRACKER_FLAG";
	private static final String MAINPAGE_FLAG= "MAINPAGE_FLAG";
	private static final String SYNC_FLAG= "SYNC_FLAG";
	
	 public Pan_mom_session(Context context) 
	 {
		// TODO Auto-generated constructor stub
	 
		sharedPref 	  = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
		
		editor 		  = sharedPref.edit();
	 }
	 
	 public void storeuserdetails(String userid,String username, String pwd)
	 {
		 	editor.putString(PAN_MOM_USER_ID, userid);
			editor.putString(PAN_MOM_USER_NAME, username);
			editor.putString(PAN_MOM_PASSWORD, pwd);
			
			System.out.println("USER ID FROM SESSION...."+userid);
			System.out.println("USER NAME FROM SESSION...."+username);
			System.out.println("PASSWORD FROM SESSION...."+pwd);
			
			editor.commit();
	 }
	 public String getUserId() 
	 { 
			return sharedPref.getString(PAN_MOM_USER_ID, "");
			
	 }
	 public String getUserName() 
	 {
			return sharedPref.getString(PAN_MOM_USER_NAME, "");
			
	 }
	 public String getPwd() 
	 {
			return sharedPref.getString(PAN_MOM_PASSWORD, "");
			
	 }
	 public void setreturnflag(String flag)
	 {
		 	System.out.println("MYRETURNFLAG IS SET FROM SESSION...."+flag);
		 	
			editor.putString(RETURN_FLAG, flag);
			editor.commit();
	 }
	 public String getreturnflag() 
	 {
			return sharedPref.getString(RETURN_FLAG, "");
			
	 }
	 public void setflag(String flag)
	 {
		 	System.out.println("MYFLAG IS SET FROM SESSION....");
		 	
			editor.putString(PAN_MOM_FLAG, flag);
			editor.commit();
	 }
	 public String getflag() 
	 {
			return sharedPref.getString(PAN_MOM_FLAG, "");
			
	 }
	 public void setweightunit(String unit)
	 {
		 	System.out.println("WEIGHT UNIT IS SET FROM SESSION...."+unit);
		 	
			editor.putString(WEIGHT_UNIT, unit);
			editor.commit();
	 }
	 
	 public String getweightunit() 
	 {
			return sharedPref.getString(WEIGHT_UNIT, "");
			
	 }
	 public void setheightunit(String unit)
	 {
		 	System.out.println("HEIGHT UNIT IS SET FROM SESSION...."+unit);
		 	
			editor.putString(HEIGHT_UNIT, unit);
			editor.commit();
	 } 
	 
	 public String getheightunit() 
	 {
			return sharedPref.getString(HEIGHT_UNIT, "");
			
	 }
	 public void setprepregnancyweight(String weight)
	 {
		 	System.out.println("PRE PREGNANCY WEIGHT IS SET FROM SESSION...."+weight);
		 	
			editor.putString(PRE_PREGNANCY_WEIGHT, weight);
			editor.commit();
	 } 
	 
	 public String getprepregnancyweight() 
	 {
			return sharedPref.getString(PRE_PREGNANCY_WEIGHT, "");
			
	 }
	 public void setheight(String height)
	 {
		 	System.out.println("HEIGHT IS SET FROM SESSION...."+height);
		 	
			editor.putString(HEIGHT, height);
			editor.commit();
	 } 
	 
	 public String getheight() 
	 {
			return sharedPref.getString(HEIGHT, "");
			
	 }
	 public void setgallery_type(String gallery_type)
	 {
		 	System.out.println("GALLERY TYPE IS SET FROM SESSION...."+gallery_type);
		 	
			editor.putString(GALLERY_TYPE, gallery_type);
			editor.commit();
	 } 
	 
	 public String getgallery_type() 
	 {
			return sharedPref.getString(GALLERY_TYPE, "");
			
	 }
	 
	 public void setselected_week(String selected_week)
	 {
		 	System.out.println("SELECTED_WEEK IS SET FROM SESSION...."+selected_week);
		 	
			editor.putString(SELECTED_WEEK, selected_week);
			editor.commit();
	 } 
	 
	 public String getselected_week() 
	 {
			return sharedPref.getString(SELECTED_WEEK, "");
			
	 }
	 public void setactivity_tag(String activity_tag)
	 {
		 	System.out.println("ACTIVITY TAG IS SET FROM SESSION...."+activity_tag);
		 	
			editor.putString(ACTIVITY_TAG, activity_tag);
			editor.commit();
	 } 
	 
	 public String getactivity_tag() 
	 {
			return sharedPref.getString(ACTIVITY_TAG, "");
			
	 }
	 
	 public void settracker_flag(String tracker_flag)
	 {
		 	System.out.println("TRACKER_FLAG IS SET FROM SESSION...."+tracker_flag);
		 	
			editor.putString(TRACKER_FLAG, tracker_flag);
			editor.commit();
	 } 
	 
	 public String gettracker_flag() 
	 {
			return sharedPref.getString(TRACKER_FLAG, "");
			
	 }
	 
	 public void setsync_flag(String sync_flag)
	 {
		 	System.out.println("SYNC_FLAG IS SET FROM SESSION...."+sync_flag);
		 	
			editor.putString(SYNC_FLAG, sync_flag);
			editor.commit();
	 } 
	 
	 public String getsync_flag() 
	 {
			return sharedPref.getString(SYNC_FLAG, "");
			
	 }
	 
	 public void setmainpage_flag(String mainpage_flag)
	 {
		 	System.out.println("MAINPAGE_FLAG IS SET FROM SESSION...."+mainpage_flag);
		 	
			editor.putString(MAINPAGE_FLAG, mainpage_flag);
			editor.commit();
	 } 
	 
	 public String getmainpage_flag() 
	 {
			return sharedPref.getString(MAINPAGE_FLAG, "");
			
	 }


}
