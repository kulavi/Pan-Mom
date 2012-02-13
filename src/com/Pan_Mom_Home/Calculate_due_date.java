//Mathematical calculation is done in this class to calulate the due date

package com.Pan_Mom_Home;

import java.lang.ref.SoftReference;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Calculate_due_date extends CustomWindow 
{
	Spinner sprcycle_length;
	Button btnhome,btncalculate; 
	EditText edtlmpdate;
	
	int arrmonth[]={31,28,31,30,31,30,31,31,30,31,30,31};
	int arrmonth1[]={31,29,31,30,31,30,31,31,30,31,30,31};
	String due_date;
	int cycle_length,flag;
	int mYear,mMonth,mDay;
	int tmYear,tmMonth,tmDay;
	int nDay,nMonth,nYear;
	static final int DATE_DIALOG_ID = 1;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_due_date); 
          
        final Window window = getWindow();
        
     
        btncalculate=(Button) findViewById(R.id.btncalculate);
        sprcycle_length=(Spinner) findViewById(R.id.sprcycle_length);
        edtlmpdate=(EditText) findViewById(R.id.edtlmpdate);
        
          final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        
        ArrayAdapter<CharSequence> cycle_length_adapter = ArrayAdapter.createFromResource(
                this, R.array.Avg_Cycle_length, android.R.layout.simple_spinner_item);
        cycle_length_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprcycle_length.setAdapter(cycle_length_adapter);
       
        btncalculate.setOnClickListener(new OnClickListener() {
			
		
			public void onClick(View v) {
				String s1=edtlmpdate.getText().toString();
				System.out.println("sldjkflsj sdflk"+s1);
				
				if((edtlmpdate.getText().toString()).equals("")){
					Toast.makeText(getApplicationContext(), "Plese Enter date", Toast.LENGTH_LONG).show();
				}
				
				else{
				calculate_due_date();
				}
				
			}
		});
        
        edtlmpdate.setOnClickListener(new OnClickListener() {
			
		
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
								
				showDialog(DATE_DIALOG_ID);
				
				 
			}
		});

        
	}
	
	
	//overriding the method to get the date picker
	@Override
    protected Dialog onCreateDialog(int id) {
            switch (id) {

            case DATE_DIALOG_ID:
                    return new DatePickerDialog(this,
                            mDateSetListener,
                            mYear, mMonth, mDay);
            }
            return null;
    }
    protected void onPrepareDialog(int id, Dialog dialog) {
            switch (id) {

            case DATE_DIALOG_ID:
                    ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                    break;
            }
    }
    
    /*Type :Function
    name:updateDisplay
    return type:void
    date:12-12-2011
    purpose:display the selected date by the user*/
    private void updateDisplay() {
            edtlmpdate.setText(
                    new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mDay).append("-")
                    .append(mMonth + 1).append("-")
                    .append(mYear).append(" "));
    }
    /*Type :Function
    name:onDateSet
    return type:void
    date:12-12-2011
    purpose:date picker is opened to select the date*/
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

     
			public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth)
           	{
				// TODO Auto-generated method stub
				mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                updateDisplay();
			}
    };
    /*Type :Function
    name:calculate_due_date
    return type:void
    date:12-12-2011
    purpose:mathematical calculation is done here to get the exact due date*/
    public void calculate_due_date()
    {
    	
    	final Calendar c1 = Calendar.getInstance();
        tmYear = c1.get(Calendar.YEAR);
        tmMonth = c1.get(Calendar.MONTH);
        tmDay = c1.get(Calendar.DAY_OF_MONTH);

        String current_date=tmDay+"-"+(tmMonth+1)+"-"+tmYear;
        String[] dts_current=current_date.split("-");
        c1.set(tmYear,tmMonth,tmDay);
        System.out.println("current day......"+tmDay);
        System.out.println("current month......"+tmMonth);
        System.out.println("current year......"+tmYear);
        
    	Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH,mDay);
        thatDay.set(Calendar.MONTH,mMonth); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR,mYear);
        
   
     
        int a=thatDay.get(Calendar.DAY_OF_MONTH);
        int b=thatDay.get(Calendar.MONTH);
        int c=thatDay.get(Calendar.YEAR);
        thatDay.set(c, b, a);
        System.out.println("lmp day......"+a);
        System.out.println("lmp month......"+b);
        System.out.println("lmp year......"+c);
        long diff_lmp_and_current_date=c1.getTimeInMillis()-thatDay.getTimeInMillis();
		  long days = diff_lmp_and_current_date / (24 * 60 * 60 * 1000);
		  int ss=(int)days;
		  System.out.println("days are............."+ss);
		  
        if(ss<=280)
        {
        thatDay.add(Calendar.DAY_OF_MONTH, +7);
        thatDay.add(Calendar.MONTH,+9);
        
        int s1=thatDay.get(Calendar.MONTH);
        int s2=thatDay.get(Calendar.DAY_OF_MONTH);
        int s3=thatDay.get(Calendar.YEAR);
    	due_date=s2+"-"+(s1+1)+"-"+s3;
    	System.out.println("CALCULATED DUE DATE"+due_date);
    	Intent i=new Intent();
		Bundle bun=new Bundle();
		bun.putString("LMP",edtlmpdate.getText().toString());
		bun.putString("DUEDATE",due_date);
		i.putExtras(bun);
    	setResult(RESULT_OK, i);  
		finish();
    	
        }
        else
        {        	
        	Toast.makeText(getApplicationContext(), "Plese Enter Valid date", Toast.LENGTH_LONG).show();
        	edtlmpdate.setText("");
        }
        
    }
    public void home()
    {
    	Intent i=new Intent(Calculate_due_date.this, Pan_MomActivity.class);
    	startActivity(i);
    }
    @Override
	public void onBackPressed()
	{
		System.out.println("BACK BUTTON PRESSED!!!!");
	}
}
