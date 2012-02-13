//Creating a common title bar for all screens
package com.Pan_Mom_Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomWindow extends Activity {
	protected TextView title;
	protected ImageButton icon;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
          
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        setContentView(R.layout.mainpage);
        
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
        
        //title = (TextView) findViewById(R.id.title);
        icon  = (ImageButton) findViewById(R.id.icon);  
        
          
        
         
	}
	
	 /*Type :Function
    name:onClickHome
    return type:void
    date:12-12-2011
    purpose:Click function for home button*/
	public void onClickHome (View v)
	{
	    goHome (this);
	}
	 /*Type :Function
    name:onClickAbout
    return type:void
    date:12-12-2011
    purpose:Click function for about info button*/
	public void onClickAbout (View v)
	{
	    
		about_app(this);
		Toast.makeText(this, "Info Selected!!!", Toast.LENGTH_SHORT).show();
	}
	 /*Type :Function
    name:onClickFeedback
    return type:void
    date:12-12-2011
    purpose:Click function for feedback button*/
	public void onClickFeedback (View v)
	{
		feedback(this);
	    Toast.makeText(this, "Feedback Selected!!!", Toast.LENGTH_SHORT).show();
	}
	/*Type :Function
    name:about_app
    return type:void
    date:12-12-2011
    purpose:Displays the about info screen*/
	public void about_app(Context context)
	{
		final Intent intent = new Intent(context, About_app.class);
	    intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    context.startActivity (intent);
	}
	/*Type :Function
    name:feedback
    return type:void
    date:12-12-2011
    purpose:Displays the feedback screen*/
	public void feedback(Context context)
	{
		final Intent intent = new Intent(context, Feedback.class);
	    intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    context.startActivity (intent);
	}
	/*Type :Function
    name:goHome
    return type:void
    date:12-12-2011
    purpose:Displays the dashboard*/
	public void goHome(Context context) 
	{
	    final Intent intent = new Intent(context, Dashboard.class);
	    intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    context.startActivity (intent);
	}
} 