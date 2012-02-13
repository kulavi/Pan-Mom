package com.Pan_Mom_Home;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper
{
	private static final String TAG = "DataBaseHelper";
    
	//Database Name
	static final String dbName="PAN_MOM_1.7";   

	//Database version   
	private static final int DATABASE_VERSION = 35;
	
	//Registration Table
	static final String regTable="registration";
	static final String reg_id="reg_id";
	static final String reg_date="reg_date";
	static final String reg_user_login="reg_user_login";
	static final String reg_email="reg_email";
	static final String reg_pwd="reg_pwd";
	static final String reg_name="reg_name";
	static final String reg_lmp="reg_lmp";
	static final String reg_due_date="reg_due_date";
	static final String reg_age="reg_age";
	static final String reg_occu_self="reg_occu_self";
	static final String reg_occu_husband="reg_occu_husband";
	static final String reg_mo_no_self="reg_mo_no_self";
	static final String reg_mo_no_husband="reg_mo_no_husband";
	static final String reg_mo_no_guardian="reg_mo_no_guardian";
	static final String pan_mom_reg_id="pan_mom_reg_id";
	
	
	//Weight Tracker Table
	static final String WeightTable="Weight_tracker";
	static final String Weight_id="Weight_id";
	static final String Weight_user_login_id="Weight_user_login_id";
	static final String Weight_kg="Weight_kg";
	static final String Weight_date="Weight_date";
	static final String Weight_condition1="Weight_condition1";
	static final String Weight_condition2="Weight_condition2";
	static final String Weight_body_fat="Weight_body_fat";
	static final String Weight_sync_flag="Weight_sync_flag";
	
	
	//HB Tracker Table
	static final String HBTable="HB_tracker";
	static final String HB_id="HB_id";
	static final String HB_user_login_id="HB_user_login_id";
	static final String HB_value="HB_value";
	static final String HB_date="HB_date";
	static final String HB_time="HB_time";
	static final String HB_sync_flag="HB_sync_flag";
	
	
	//Food Tracker Table
	static final String FoodTable="Food_tracker";
	static final String Food_id="Food_id";
	static final String Food_user_login_id="Food_user_login_id";
	static final String Food_date="Food_date";
	static final String Food_time="Food_time";
	static final String Food_which_food="Food_which_food";
	static final String Food_quantinty="Food_quantinty";
	static final String Food_sync_flag="Food_sync_flag";
	
	
	//Activities Tracker Table
	static final String ActivityTable="Activity_tracker";
	static final String Activity_id="Activity_id";
	static final String Activity_user_login_id="Activity_user_login_id";
	static final String Activity_date="Activity_date";
	static final String Activity_etime="Activity_etime";
	static final String Activity_wtime="Activity_wtime";
	static final String Activity_ecalories="Activity_ecalories";
	static final String Activity_wcalories="Activity_wcalories";
	static final String Activity_distance="Activity_distance";
	static final String Activity_sync_flag="Food_sync_flag";
	
	
		
	//Glucose Tracker Table
	static final String GlucoseTable="Glucose_tracker";
	static final String Glucose_id="Glucose_id";
	static final String Glucose_user_login_id="Glucose_user_login_id";
	static final String Glucose_date="Glucose_date";
	static final String Glucose_time="Glucose_time";
	static final String Glucose_value="Glucose_value";
	static final String Glucose_test_condi="Glucose_test_condi";
	static final String Glucose_sync_flag="Glucose_sync_flag";
		
	
	//BP Tracker Table
	static final String BPTable="BP_tracker";
	static final String BP_id="BP_id";
	static final String BP_user_login_id="BP_user_login_id";
	static final String BP_date="BP_date";
	static final String BP_time="BP_time";
	static final String BP_systolic="BP_systolic";
	static final String BP_diastolic="BP_diastolic";
	static final String BP_pulse_count="BP_pulse_count";
	static final String BP_which_hand="BP_which_hand";
	static final String BP_sync_flag="BP_sync_flag";
	
	
	//Heart Tracker Table
	static final String HeartTable="Heart_tracker";
	static final String Heart_id="Heart_id";
	static final String Heart_user_login_id="Heart_user_login_id";
	static final String Heart_date="Heart_date";
	static final String Heart_RHR="Heart_RHR";
	static final String Heart_time_RHR="Heart_time_RHR";
	static final String Heart_NHR="Heart_NHR";
	static final String Heart_time_NHR="Heart_time_NHR";
	static final String Heart_EHR="Heart_EHR";
	static final String Heart_time_EHR="Heart_time_EHR";
	static final String Heart_sync_flag="Heart_sync_flag";
	
	
	//Doctor Info Table
	static final String DoctorTable="Doctor_Info";
	static final String Doctor_id="Doctor_id";
	static final String Doctor_user_login_id="Doctor_user_login_id";
	static final String Doctor_name="Doctor_name";
	static final String Doctor_phone="Doctor_phone";
	static final String Doctor_other_phone="Doctor_other_phone";
	static final String Doctor_address="Doctor_address";
	static final String Doctor_hosp_name="Doctor_hosp_name";
	static final String Doctor_hosp_phone="Doctor_hosp_phone";
	static final String Doctor_hosp_address="Doctor_hosp_address";
	static final String Doctor_sync_flag="Doctor_sync_flag";
	
	//Diabetic/Chronic Table
	
	static final String DCTable="DiabeticORChronic";
	static final String DC_id="DC_id";
	static final String DC_user_login_id="DC_user_login_id";
	static final String IsDiabetic="IsDiabetic";
	static final String IsChronic="IsChronic";
	static final String Heightincm="Heightincm";
	static final String Weightinkg="Weightinkg";
	static final String DC_sync_flag="DC_sync_flag";
	
   //40 Weeks Info Table
	
	static final String WeekInfoTable="WeekInfoTable";
	static final String Week_id="Week_id";
	static final String Week_lady_Info="Week_lady_Info";
	static final String Week_baby_Info="Week_baby_Info";
	static final String Week_Info_sync="Week_Info_sync";
	
	
	//Symptom Table
	
	static final String SymptomTable="SymptomTable";
	static final String Symptom_id="Symptom_id";
	static final String Symptom="Symptom";
	static final String Symptom_description="Symptom_description";
	static final String Symptom_sync="Symptom_sync";
	
	
//Symptom to User Table
	
	static final String Symptom_to_UserTable="Symptom_to_UserTable";
	static final String Symptom__to_id="Symptom__to_id";
	static final String Symptom__to_user_id="Symptom__to_user_id";
	static final String Symptom__week_id="Symptom__week_id";
	static final String Symptom__found_id="Symptom__found_id";
	static final String Symptom_to_user_sync="Symptom_to_user_sync";
	
	
	
	
	
	
	
	
	public DatabaseHelper(Context context) 
	{
		super(context, dbName, null,DATABASE_VERSION);
        
		// TODO Auto-generated constructor stub 
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE "+regTable+" ("+reg_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+reg_date+ " TEXT,"+reg_user_login+ " TEXT, "+reg_email+ " TEXT,"+reg_pwd+ " TEXT,"+reg_name+ " TEXT,"+reg_lmp+ " TEXT,"+reg_due_date+ " TEXT,"+reg_age+ " INTEGER,"+reg_occu_self+ " TEXT,"+reg_occu_husband+ " TEXT,"+reg_mo_no_self+ " TEXT,"+reg_mo_no_husband+" TEXT,"+reg_mo_no_guardian+ " TEXT,"+pan_mom_reg_id+" INTEGER)");
		db.execSQL("CREATE TABLE "+WeightTable+" ("+Weight_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+Weight_user_login_id+ " INTEGER,"+Weight_kg+ " INTEGER, "+Weight_date+ " TEXT,"+Weight_condition1+ " TEXT,"+Weight_condition2+ " TEXT,"+Weight_body_fat+ " TEXT,"+Weight_sync_flag+ " TEXT,FOREIGN KEY ("+Weight_user_login_id+") REFERENCES "+regTable+" ("+reg_id+"))");
		db.execSQL("CREATE TABLE "+HBTable+" ("+HB_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+HB_user_login_id+ " INTEGER,"+HB_value+ " INTEGER, "+HB_date+ " TEXT,"+HB_time+ " TEXT,"+HB_sync_flag+ " TEXT,FOREIGN KEY ("+HB_user_login_id+") REFERENCES "+regTable+" ("+reg_id+"))");
		db.execSQL("CREATE TABLE "+FoodTable+" ("+Food_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+Food_user_login_id+ " INTEGER,"+Food_date+ " TEXT, "+Food_time+ " TEXT,"+Food_which_food+ " TEXT,"+Food_quantinty+ " INTEGER,"+Food_sync_flag+ " TEXT,FOREIGN KEY ("+Food_user_login_id+") REFERENCES "+regTable+" ("+reg_id+"))");
		db.execSQL("CREATE TABLE "+ActivityTable+" ("+Activity_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+Activity_user_login_id+ " INTEGER,"+Activity_date+ " TEXT, "+Activity_etime+ " TEXT,"+Activity_ecalories+ " TEXT,"+Activity_wtime+ " TEXT,"+Activity_wcalories+ " TEXT, "+Activity_distance+ " TEXT,"+Activity_sync_flag+ " TEXT,FOREIGN KEY ("+Activity_user_login_id+") REFERENCES "+regTable+" ("+reg_id+"))");
		db.execSQL("CREATE TABLE "+GlucoseTable+" ("+Glucose_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+Glucose_user_login_id+ " INTEGER,"+Glucose_date+ " TEXT, "+Glucose_time+ " TEXT,"+Glucose_value+ " INTEGER,"+Glucose_test_condi+ " TEXT,"+Glucose_sync_flag+ " TEXT,FOREIGN KEY ("+Glucose_user_login_id+") REFERENCES "+regTable+" ("+reg_id+"))");
		db.execSQL("CREATE TABLE "+BPTable+" ("+BP_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+BP_user_login_id+ " INTEGER,"+BP_date+ " TEXT, "+BP_time+ " TEXT,"+BP_systolic+ " INTEGER,"+BP_diastolic+ " INTEGER,"+BP_pulse_count+ " INTEGER,"+BP_which_hand+ " TEXT,"+BP_sync_flag+ " TEXT,FOREIGN KEY ("+BP_user_login_id+") REFERENCES "+regTable+" ("+reg_id+"))");
		db.execSQL("CREATE TABLE "+HeartTable+" ("+Heart_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+Heart_user_login_id+ " INTEGER,"+Heart_date+ " TEXT, "+Heart_RHR+ " TEXT,"+Heart_time_RHR+ " TEXT,"+Heart_NHR+ " TEXT,"+Heart_time_NHR+ " TEXT,"+Heart_EHR+ " TEXT,"+Heart_time_EHR+ " TEXT,"+Heart_sync_flag+ " TEXT,FOREIGN KEY ("+Heart_user_login_id+") REFERENCES "+regTable+" ("+reg_id+"))");
		db.execSQL("CREATE TABLE "+DoctorTable+" ("+Doctor_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+Doctor_user_login_id+ " INTEGER,"+Doctor_name+ " TEXT, "+Doctor_phone+ " TEXT,"+Doctor_other_phone+ " TEXT,"+Doctor_address+ " TEXT,"+Doctor_hosp_name+ " TEXT,"+Doctor_hosp_phone+ " TEXT,"+Doctor_hosp_address+ " TEXT,"+Doctor_sync_flag+ " TEXT,FOREIGN KEY ("+Doctor_user_login_id+") REFERENCES "+regTable+" ("+reg_id+"))");
		db.execSQL("CREATE TABLE "+DCTable+" ("+DC_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+DC_user_login_id+ " INTEGER,"+IsDiabetic+ " TEXT, "+IsChronic+ " TEXT,"+Heightincm+ " TEXT, "+Weightinkg+ " TEXT,"+DC_sync_flag+ " TEXT,FOREIGN KEY ("+DC_user_login_id+") REFERENCES "+regTable+" ("+reg_id+"))");
		db.execSQL("CREATE TABLE "+WeekInfoTable+" ("+Week_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+Week_lady_Info+ " TEXT,"+Week_baby_Info+ " TEXT, "+Week_Info_sync+ " TEXT)");
		db.execSQL("CREATE TABLE "+SymptomTable+" ("+Symptom_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+Symptom+ " TEXT,"+Symptom_description+ " TEXT, "+Symptom_sync+ " TEXT)");
		db.execSQL("CREATE TABLE "+Symptom_to_UserTable+" ("+Symptom__to_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+Symptom__to_user_id+ " INTEGER,"+Symptom__week_id+ " INTEGER,"+Symptom__found_id+ " INTEGER, "+Symptom_to_user_sync+ " TEXT,FOREIGN KEY ("+Symptom__to_user_id+") REFERENCES "+regTable+" ("+reg_id+"),FOREIGN KEY ("+Symptom__week_id+") REFERENCES "+WeekInfoTable+" ("+Week_id+"),FOREIGN KEY ("+Symptom__found_id+") REFERENCES "+SymptomTable+" ("+Symptom_id+"))");
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		
	}
	//REGISTESTRATION TABLE
	void Insertregister(String strdate,String strulogin,String stremail,String strpwd,String strname,String strlmp,String strduedate,String strage,String strocc_s,String strocc_h,String strmo_s,String strmo_h,String str_mo_g,int strpan_id)
	{
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues cv=new ContentValues();

		cv.put(reg_date, strdate);                 
		cv.put(reg_user_login, strulogin);
		cv.put(reg_email, stremail);
		cv.put(reg_pwd, strpwd);
		cv.put(reg_name, strname);
		cv.put(reg_lmp, strlmp);
		cv.put(reg_due_date, strduedate);
		cv.put(reg_age, strage);
		cv.put(reg_occu_self, strocc_s);
		cv.put(reg_occu_husband, strocc_h);
		cv.put(reg_mo_no_self, strmo_s);
		cv.put(reg_mo_no_husband, strmo_h);
		cv.put(reg_mo_no_guardian,str_mo_g);
		cv.put(pan_mom_reg_id,strpan_id);
		
		myDB.insert(regTable,null,cv);
		
     }
	
	public void deleteregi(int id)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		db.delete(regTable,reg_id+"="+id, new String []{} );
		

	
	}
	public void deleteregi()
	{
		SQLiteDatabase db=this.getReadableDatabase();
		db.execSQL("DROP TABLE IF EXISTS "+regTable);
		db.execSQL("CREATE TABLE "+regTable+" ("+reg_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+reg_date+ " TEXT,"+reg_user_login+ " TEXT, "+reg_email+ " TEXT,"+reg_pwd+ " TEXT,"+reg_name+ " TEXT,"+reg_lmp+ " TEXT,"+reg_due_date+ " TEXT,"+reg_age+ " INTEGER,"+reg_occu_self+ " TEXT,"+reg_occu_husband+ " TEXT,"+reg_mo_no_self+ " TEXT,"+reg_mo_no_husband+" TEXT,"+reg_mo_no_guardian+ " TEXT,"+pan_mom_reg_id+" INTEGER)");

	
	}
	public int updatelmp_and_edd(String struname,String strpwd,String strlmp,String stredd) {
		SQLiteDatabase db= this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 cv.put(reg_lmp,strlmp);
		 cv.put(reg_due_date,stredd);
		 return db.update(regTable, cv,reg_user_login+"='"+struname+"' and "+reg_pwd+" ='"+strpwd+"'", new String []{});
		
	}
	public int updatepanmomid(int struid,int strpanid) {
		SQLiteDatabase db= this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 cv.put(pan_mom_reg_id,strpanid);
		 
		 return db.update(regTable, cv,reg_id+"="+struid, new String []{});
		
	}
	Cursor getreg_info(int id)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+regTable+" where "+reg_id+"="+id,new String [] {});
		return cur;		
	}
	//Cursor cur= db.rawQuery("SELECT * FROM "+WeightTable+" where "+Weight_user_login_id+"="+id,new String []{});
	//////////////////////////////
	Cursor getreg_info(String username)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+regTable+" where "+reg_user_login+"='"+username+"'",new String [] {});
		return cur;		
	}
	Cursor getreglogwith_email(String email){
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+regTable+" where "+reg_email+"='"+email+"'",new String [] {});
		return cur;
	}
////////////////////////////////////////////
	
	Cursor getlogin_details(String uname,String pwd)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+regTable+" where "+reg_user_login+"='"+uname+"' AND "+reg_pwd+" ='"+pwd+"'",new String [] {});
		return cur;		
	}
	
	Cursor check_due_date(String uname,String pwd)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT "+reg_due_date+" FROM "+regTable+" where "+reg_user_login+"='"+uname+"' AND "+reg_pwd+" ='"+pwd+"'",new String [] {});
		return cur;		
	}
	
	
	//WEIGHT TABLE
	
	void InsertWeight(String struid,String strkg,String strdate,String strc1,String strc2,String strbodyfat,String strwt_sync)
	{
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues cv=new ContentValues();

		cv.put(Weight_user_login_id, struid);                 
		cv.put(Weight_kg, strkg);
		cv.put(Weight_date, strdate);
		cv.put(Weight_condition1, strc1);
		cv.put(Weight_condition2, strc2);
		cv.put(Weight_body_fat, strbodyfat);
		cv.put(Weight_sync_flag, strwt_sync);
		
		
		myDB.insert(WeightTable,null,cv); 
		 
     }
	
	public int update_wt(int uid,int fid,String strkg,String strdate,String strc1,String strc2,String strbodyfat,String strwt_sync) {
		SQLiteDatabase db= this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 
		 	cv.put(Weight_kg, strkg);
			cv.put(Weight_date, strdate);
			cv.put(Weight_condition1, strc1);
			cv.put(Weight_condition2, strc2);
			cv.put(Weight_body_fat, strbodyfat);
			cv.put(Weight_sync_flag, strwt_sync);
		 return db.update(WeightTable, cv,Weight_user_login_id+"="+uid+" and "+Weight_id+"="+fid, new String []{});
		
	}  
	
	Cursor getWt_data(int id) 
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+WeightTable+" where "+Weight_user_login_id+"="+id,new String []{});
		return cur;		
	}
	
	Cursor getlast_Wt_data(int id) 
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+WeightTable+" where "+Weight_user_login_id+"="+id+" and "+Weight_id+" IN (SELECT MAX("+Weight_id+") FROM "+WeightTable+")" ,new String []{});
		return cur;		
	}
	void delWt_data(int user_login_id,int id)
	{
		SQLiteDatabase db=this.getWritableDatabase();		
		db.execSQL("DELETE  FROM "+WeightTable+" where "+Weight_user_login_id+"="+user_login_id+" AND "+Weight_id+"="+id,new String [] {});
			
	}

	
	//HEMOGLOBIN TABLE
	void InsertHB(String struid,String strvalue,String strdate,String strtime,String strhb_sync)
	{
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues cv=new ContentValues();

		cv.put(HB_user_login_id, struid);                 
		cv.put(HB_value, strvalue);
		cv.put(HB_date, strdate);
		cv.put(HB_time, strtime);
		cv.put(HB_sync_flag, strhb_sync);
		
		
		myDB.insert(HBTable,null,cv);
		
     }
	public int update_HB(int uid,int HBid,String strdate,String strtime,String strvalue,String strhb_sync)
	{
		SQLiteDatabase db= this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 
		 	cv.put(HB_value, strvalue);
			cv.put(HB_date, strdate);
			cv.put(HB_time, strtime);
			cv.put(HB_sync_flag, strhb_sync);
		 return db.update(HBTable, cv,HB_user_login_id+"="+uid+" and "+HB_id+" ="+HBid, new String []{});
		
	}
	
	Cursor getHB_data(int id)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+HBTable+" where "+HB_user_login_id+"="+id,new String [] {});
		return cur;		
	}
	
	void delHB_data(int user_login_id,int id)
	{
		SQLiteDatabase db=this.getWritableDatabase();		
		db.execSQL("DELETE  FROM "+HBTable+" where "+HB_user_login_id+"="+user_login_id+" AND "+HB_id+"="+id,new String [] {});
			
	}

	
	//FOOD TABLE
	
	void InsertFood(String struid,String strdate,String strtime,String strwhich_food,String strqty,String strfood_sync)
	{
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues cv=new ContentValues();

		cv.put(Food_user_login_id, struid);                 
		cv.put(Food_date, strdate);
		cv.put(Food_time, strtime);
		cv.put(Food_which_food, strwhich_food);
		cv.put(Food_quantinty, strqty);
		cv.put(Food_sync_flag, strfood_sync);
		
		
		myDB.insert(FoodTable,null,cv);
		
     }
	
	public int update_food(int uid,int fid,String strdate,String strtime,String strwhich_food,String strqty,String strfood_sync) {
		SQLiteDatabase db= this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 
			cv.put(Food_date, strdate);
			cv.put(Food_time, strtime);
			cv.put(Food_which_food, strwhich_food);
			cv.put(Food_quantinty, strqty);
			cv.put(Food_sync_flag, strfood_sync);
		 return db.update(FoodTable, cv,Food_user_login_id+"="+uid+" and "+Food_id+" ="+fid, new String []{});
		
	}

	
	Cursor getFood_data(int id)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+FoodTable+" where "+Food_user_login_id+"="+id,new String [] {});
		return cur;		
	}
	
	void delFood_data(int user_login_id,int id)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		//db.execSQL("DROP TABLE IF EXISTS "+regTable);
		System.out.println("user id is : "+user_login_id);
		System.out.println("user fiid is : "+id);
		db.execSQL("DELETE  FROM "+FoodTable+" where "+Food_user_login_id+"="+user_login_id+" AND "+Food_id+"="+id,new String [] {});
		//Cursor cur= db.rawQuery("DELETE  FROM "+FoodTable+" where "+Food_user_login_id+"="+id+" AND "+Food_id+"="+id,new String [] {});
		//return cur;		
	}

	
//ACTIVITY TABLE
	
	void InsertActivity(String struid,String strdate,String stretime,String strecalori,String strwtime,String strwcalori,String strdistance,String stractivity_sync)
	{
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues cv=new ContentValues();

		cv.put(Activity_user_login_id, struid);                 
		cv.put(Activity_date, strdate);
		cv.put(Activity_etime, stretime);
		cv.put(Activity_ecalories, strecalori);
		cv.put(Activity_wtime, strwtime);
		cv.put(Activity_wcalories, strwcalori);
		cv.put(Activity_distance, strdistance);
		cv.put(Activity_sync_flag, stractivity_sync);
		
		
		myDB.insert(ActivityTable,null,cv);
		
     }
	
	public int update_activity(int uid,int activityid,String strdate,String stretime,String strecalori,String strwtime,String strwcalori,String strdistance,String stractivity_sync)
	{
		SQLiteDatabase db= this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 	
		 cv.put(Activity_date, strdate);
			cv.put(Activity_etime, stretime);
			cv.put(Activity_ecalories, strecalori);
			cv.put(Activity_wtime, strwtime);
			cv.put(Activity_wcalories, strwcalori);
			cv.put(Activity_distance, strdistance);
			cv.put(Activity_sync_flag, stractivity_sync);
			
			
		 return db.update(ActivityTable, cv,Activity_user_login_id+"="+uid+" and "+Activity_id+" ="+activityid, new String []{});
		
	}

	
	Cursor getActivity_data(int id)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+ActivityTable+" where "+Activity_user_login_id+"="+id,new String [] {});
		return cur;		
	}
	
	void delActivity_data(int user_login_id,int id)
	{
		SQLiteDatabase db=this.getWritableDatabase();		
		db.execSQL("DELETE  FROM "+ActivityTable+" where "+Activity_user_login_id+"="+user_login_id+" AND "+Activity_id+"="+id,new String [] {});
			
	}

	//GLUCOSE TABLE
	
	void InsertGlucose(String struid,String strdate,String strtime,String strvalue,String strtcondi,String strglucose_sync)
	{
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues cv=new ContentValues();

		cv.put(Glucose_user_login_id, struid);                 
		cv.put(Glucose_date, strdate);
		cv.put(Glucose_time, strtime);
		cv.put(Glucose_value, strvalue);
		cv.put(Glucose_test_condi, strtcondi);
		cv.put(Glucose_sync_flag, strglucose_sync);
		
		
		myDB.insert(GlucoseTable,null,cv);
		
     }
	
	public int update_glucose(int uid,int glucoseid,String strdate,String strtime,String strvalue,String strtcondi,String strglucose_sync)
	{
		SQLiteDatabase db= this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 	
		 cv.put(Glucose_date, strdate);
			cv.put(Glucose_time, strtime);
			cv.put(Glucose_value, strvalue);
			cv.put(Glucose_test_condi, strtcondi);
			cv.put(Glucose_sync_flag, strglucose_sync);
			
		 return db.update(GlucoseTable, cv,Glucose_user_login_id+"="+uid+" and "+Glucose_id+" ="+glucoseid, new String []{});
		
	}
	Cursor getGlucose_data(int id)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+GlucoseTable+" where "+Glucose_user_login_id+"="+id,new String [] {});
		return cur;		
	}
	
	void delGlucose_data(int user_login_id,int id)
	{
		SQLiteDatabase db=this.getWritableDatabase();		
		db.execSQL("DELETE  FROM "+GlucoseTable+" where "+Glucose_user_login_id+"="+user_login_id+" AND "+Glucose_id+"="+id,new String [] {});
			
	}

	
	//BP TABLE
	
	void InsertBP(String struid,String strdate,String strtime,String strsystolic,String strdiastolic,String strpulsecount,String strthand,String strbp_sync)
	{
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues cv=new ContentValues();

		cv.put(BP_user_login_id, struid);                 
		cv.put(BP_date, strdate);
		cv.put(BP_time, strtime);
		cv.put(BP_systolic, strsystolic);
		cv.put(BP_diastolic, strdiastolic);
		cv.put(BP_pulse_count, strpulsecount);
		cv.put(BP_which_hand, strthand);
		cv.put(BP_sync_flag, strbp_sync);
		
		
		myDB.insert(BPTable,null,cv);
		
     }
	
	public int update_bp(int uid,int bpid,String strdate,String strtime,String strsystolic,String strdiastolic,String strpulsecount,String strthand,String strbp_sync) {
		SQLiteDatabase db= this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 cv.put(BP_date, strdate);
			cv.put(BP_time, strtime);
			cv.put(BP_systolic, strsystolic);
			cv.put(BP_diastolic, strdiastolic);
			cv.put(BP_pulse_count, strpulsecount);
			cv.put(BP_which_hand, strthand);
			cv.put(BP_sync_flag, strbp_sync);
		 return db.update(BPTable, cv,BP_user_login_id+"="+uid+" and "+BP_id+" ="+bpid, new String []{});
		
	}

	
	Cursor getBP_data(int id)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+BPTable+" where "+BP_user_login_id+"="+id,new String [] {});
		return cur;		
	}
	
	void delbp_data(int user_login_id,int id)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		//db.execSQL("DROP TABLE IF EXISTS "+regTable);
		System.out.println("user id is : "+user_login_id);
		System.out.println("user fiid is : "+id);
		db.execSQL("DELETE  FROM "+BPTable+" where "+BP_user_login_id+"="+user_login_id+" AND "+BP_id+"="+id,new String [] {});
			
	}

	
//HEART TABLE
	
	void InsertHeart(String struid,String strdate,String strRHR,String strRHR_time,String strNHR,String strNHR_time,String strEHR,String strEHR_time,String strheart_sync)
	{
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues cv=new ContentValues();

		cv.put(Heart_user_login_id, struid);                 
		cv.put(Heart_date, strdate);
		cv.put(Heart_RHR, strRHR);
		cv.put(Heart_time_RHR, strRHR_time);
		cv.put(Heart_NHR, strNHR);
		cv.put(Heart_time_NHR, strNHR_time);
		cv.put(Heart_EHR, strEHR);
		cv.put(Heart_time_EHR, strEHR_time);
		cv.put(Heart_sync_flag, strheart_sync);
		
		
		myDB.insert(HeartTable,null,cv);
		
     }
	
	public int update_heart(int uid,int fid,String strdate,String strRHR,String strRHR_time,String strNHR,String strNHR_time,String strEHR,String strEHR_time,String strheart_sync) {
		SQLiteDatabase db= this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 
		 cv.put(Heart_date, strdate);
			cv.put(Heart_RHR, strRHR);
			cv.put(Heart_time_RHR, strRHR_time);
			cv.put(Heart_NHR, strNHR);
			cv.put(Heart_time_NHR, strNHR_time);
			cv.put(Heart_EHR, strEHR);
			cv.put(Heart_time_EHR, strEHR_time);
			cv.put(Heart_sync_flag, strheart_sync);
		 return db.update(HeartTable, cv,Heart_user_login_id+"="+uid+" and "+Heart_id+" ="+fid, new String []{});
		
	}

	Cursor getHeart_data(int id)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+HeartTable+" where "+Heart_user_login_id+"="+id,new String [] {});
		return cur;		
	}
	
	void delHeart_data(int user_login_id,int id)
	{
		SQLiteDatabase db=this.getWritableDatabase();		
		db.execSQL("DELETE  FROM "+HeartTable+" where "+Heart_user_login_id+"="+user_login_id+" AND "+Heart_id+"="+id,new String [] {});
			
	}

	
	//Doctor Info TABLE
	
	void InsertDoctor_Info(String strdruid,String strdrname,String strdrphn,String strdrothrphn,String strdraddress,String strhospname,String strhospphn,String strhospaddress,String strdr_sync)
	{
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues cv=new ContentValues();

		cv.put(Doctor_user_login_id, strdruid);                 
		cv.put(Doctor_name, strdrname);
		cv.put(Doctor_phone, strdrphn);
		cv.put(Doctor_other_phone, strdrothrphn);
		cv.put(Doctor_address, strdraddress);
		cv.put(Doctor_hosp_name, strhospname);
		cv.put(Doctor_hosp_phone, strhospphn);
		cv.put(Doctor_hosp_address, strhospaddress);
		cv.put(Doctor_sync_flag, strdr_sync);
		
		
		myDB.insert(DoctorTable,null,cv);
		
     }
	
	
	Cursor getDoctor_Info(int id)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+DoctorTable+" where "+Doctor_user_login_id+"="+id,new String [] {});
		return cur;		
	}
		
	//DIABETIC/CHRONIC TABLE
	void InsertDC(String struid,String strisdiabetic,String strischronic,String strheight,String strweight,String strdc_sync)
	{
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues cv=new ContentValues();

		cv.put(DC_user_login_id, struid);                 
		cv.put(IsDiabetic, strisdiabetic);
		cv.put(IsChronic, strischronic);
		cv.put(Heightincm, strheight);
		cv.put(Weightinkg, strweight);
		cv.put(DC_sync_flag, strdc_sync);
		
		
		myDB.insert(DCTable,null,cv);
		
     }
	
	public int updateDC(String struid,String strisdiabetic,String strischronic,String strheight,String strweight,String strdc_sync)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues();
		                 
		cv.put(IsDiabetic, strisdiabetic);
		cv.put(IsChronic, strischronic);
		cv.put(Heightincm, strheight);
		cv.put(Weightinkg, strweight);
		cv.put(DC_sync_flag, strdc_sync);
		
		return db.update(DCTable, cv,DC_user_login_id+"="+struid, new String []{});

	}
	Cursor getDC_data(int id)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+DCTable+" where "+DC_user_login_id+"="+id,new String [] {});
		return cur;		
	}
	
	//WEEK INFO TABLE
	void InsertWeekInfo(String strlady_info,String strbaby_info,String strweek_info_sync)
	{
		/*SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+WeekInfoTable,null);
		while(cur.moveToNext())
		{
		if(cur.isNull(0))
		{*/
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues cv=new ContentValues();

		                 
		cv.put(Week_lady_Info, strlady_info);
		cv.put(Week_baby_Info, strbaby_info);
		cv.put(Week_Info_sync, strweek_info_sync);
		
		
		myDB.insert(WeekInfoTable,null,cv);
		/*}
		}
		cur.close();*/
		
     }
	public void createweek_info()
	{
		SQLiteDatabase db= this.getWritableDatabase();
		db.execSQL("CREATE TABLE "+WeekInfoTable+" ("+Week_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+Week_lady_Info+ " TEXT,"+Week_baby_Info+ " TEXT, "+Week_Info_sync+ " TEXT)");
		
	}

	void deleteweek_info()
	 {
		 SQLiteDatabase db=this.getWritableDatabase();
		
		 db.execSQL("DROP TABLE IF EXISTS "+WeekInfoTable);
		 createweek_info();
		 		 
	 }

	Cursor getWeek_Info_data()
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+WeekInfoTable,new String [] {});
		return cur;		
	}
	
	//SYMPTOM TABLE
	void InsertSymptom(String strsymptom,String strsdescription,String  strsymptom_sync)
	{
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues cv=new ContentValues();

		cv.put(Symptom, strsymptom);                 
		cv.put(Symptom_description, strsdescription); 
		cv.put(Symptom_sync, strsymptom_sync);
				
		
		myDB.insert(SymptomTable,null,cv);
		
     }
	public void createsymptom()
	{
		SQLiteDatabase db= this.getWritableDatabase();
		db.execSQL("CREATE TABLE "+SymptomTable+" ("+Symptom_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+Symptom+ " TEXT,"+Symptom_description+ " TEXT, "+Symptom_sync+ " TEXT)");
		
	}

	void deletesymptom()
	 {
		 SQLiteDatabase db=this.getWritableDatabase();
		
		 db.execSQL("DROP TABLE IF EXISTS "+SymptomTable);
		 createsymptom();
		 		 
	 }
	Cursor getSymptom()
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+SymptomTable,new String [] {});
		return cur;		
	}
	
	Cursor getSymptom_for_id(int id)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+SymptomTable+" where "+Symptom_id+"="+id,new String [] {});
		return cur;		
	}
	
	
	//SYMPTOM TO USER TABLE
	void InsertSymptom_to_user(String struserid,String strsymptomfoundid,String strweek_id,String  strsymptom_to_user__sync)
	{
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues cv=new ContentValues();

		cv.put(Symptom__to_user_id, struserid);                 
		cv.put(Symptom__found_id, strsymptomfoundid);
		cv.put(Symptom__week_id, strweek_id); 
		cv.put(Symptom_to_user_sync, strsymptom_to_user__sync);
				
		
		myDB.insert(Symptom_to_UserTable,null,cv);
		
     }
	public void createsymptom_to_user()
	{
		SQLiteDatabase db= this.getWritableDatabase();
		//db.execSQL("CREATE TABLE "+Symptom_to_UserTable+" ("+Symptom__to_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+Symptom__to_user_id+ " INTEGER,"+Symptom__found_id+ " INTEGER, "+Symptom_to_user_sync+ " TEXT,FOREIGN KEY ("+Symptom__to_user_id+") REFERENCES "+regTable+" ("+reg_id+"))");
		db.execSQL("CREATE TABLE "+Symptom_to_UserTable+" ("+Symptom__to_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+Symptom__to_user_id+ " INTEGER,"+Symptom__week_id+ " INTEGER,"+Symptom__found_id+ " INTEGER, "+Symptom_to_user_sync+ " TEXT,FOREIGN KEY ("+Symptom__to_user_id+") REFERENCES "+regTable+" ("+reg_id+"),FOREIGN KEY ("+Symptom__week_id+") REFERENCES "+WeekInfoTable+" ("+Week_id+"),FOREIGN KEY ("+Symptom__found_id+") REFERENCES "+SymptomTable+" ("+Symptom_id+"))");
	}

	void deletesymptom_to_user()
	 {
		 SQLiteDatabase db=this.getWritableDatabase();
		
		 db.execSQL("DROP TABLE IF EXISTS "+Symptom_to_UserTable);
		 createsymptom();
		 		 
	 }
	Cursor getSymptom_to_user()
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+Symptom_to_UserTable,new String [] {});
		return cur;		
	}
	
	Cursor getSymptom_to_user_for_week_id(int id,int userid)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+Symptom_to_UserTable+" where "+Symptom__week_id+"="+id+" AND "+Symptom__to_user_id+"="+userid ,new String [] {});
		return cur;		
	}
	Cursor getSymptom_validate(int id,int userid,int sid)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+Symptom_to_UserTable+" where "+Symptom__week_id+"="+id+" AND "+Symptom__to_user_id+"="+userid+" AND "+Symptom__found_id+"="+sid ,new String [] {});
		return cur;		
	}
	
}
	
	
