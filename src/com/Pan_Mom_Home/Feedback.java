//Feedback for this application 
package com.Pan_Mom_Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Feedback extends Activity{
	
	
	final static int REQ_CODE = 1;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	
	
	//Calling the default email intent to send feedback through mail to the application
	final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
    
    emailIntent.setType("plain/text");

    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ "sush.mbs@gmail.com"});

    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Feedback For Pan Mom Android App!!");

    Feedback.this.startActivityForResult(Intent.createChooser(emailIntent, "Send mail..."),REQ_CODE);
	}
	
	
    /*Doing some action when returned to this activity*/
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQ_CODE){

			finish();
	    	    
	  }
	}

}
