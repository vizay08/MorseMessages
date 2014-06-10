package utility.first.morsemessages;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import utility.first.morsemessages.database.Database;
import utility.first.morsemessages.service.MorseMessageService;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	private static final Object LISTENERSERVICE = "utility.first.morsemessages.service.MorseMessageService";
	private ActivityManager acm;
	private boolean vibrateOn = false;
	
	private boolean on;
	private Database db;

	public boolean isVibrate() {
		return vibrateOn;
	}
	
	public void setVibrateOn(boolean vibrateOn)
	{
		this.vibrateOn = vibrateOn;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		
		db = new Database(MainActivity.this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.dashboardactivity);
		
		
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
	/////////////////////////////////////////// shared preferences added  over here ///////////////////////////////////////
		SharedPreferences preferences = this.getSharedPreferences("utility.first.morsemessages", 0);
		SharedPreferences.Editor editor = preferences.edit();
		on = false;
		
		editor.putBoolean("on", on);
		editor.commit();
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
	//	final MorseMessageReceiver morseMessageReceiver = new MorseMessageReceiver();
	//	final IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

		
	final Button aboutButton = (Button)findViewById(R.id.about_button);	
	final 	ImageButton composeButton = (ImageButton)findViewById(R.id.composebutton);
	final 	ImageButton settingsButton = (ImageButton)findViewById(R.id.settings);
	final 	ImageButton msgListener = (ImageButton)findViewById(R.id.msgvibrater);
	final ImageButton helpButton = (ImageButton)findViewById(R.id.help);
	final TextView companyName = (TextView)findViewById(R.id.company);
	
	if(db.isOn()&&isListenerServiceRunning())
	msgListener.setImageResource(R.drawable.morsemessages_vibration);
	else
	msgListener.setImageResource(R.drawable.morsemessages_vibration_stop);
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		companyName.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL, new String[]{"innovationlabs2014@gmail.com"});
				i.putExtra(Intent.EXTRA_SUBJECT,"MorseMessages Application Feedback");
				
				try{
				startActivity(Intent.createChooser(i, "Feedback.."));
			}
				catch(ActivityNotFoundException e)
				{
				   Toast.makeText(MainActivity.this,"There are no email clients installed..", Toast.LENGTH_SHORT).show();	

				}
				
			}
		});
	
		aboutButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent go = new Intent();
				go.setClassName("utility.first.morsemessages", "utility.first.morsemessages.AboutActivity");
				startActivity(go);
			}
		});
	
	
		composeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Main Activity","is in main activity on create method");
				vibrator.vibrate(50);
				Intent i = new Intent();
				i.setClassName("utility.first.morsemessages", "utility.first.morsemessages.ComposeActivity");
				startActivity(i);
				
			}
		});
		
		msgListener.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				
				vibrator.vibrate(50);
				
				isListenerServiceRunning();
				
				if(db.isOn() && isListenerServiceRunning())  // if button is perssed that is true
				{
					
				msgListener.setImageResource(R.drawable.morsemessages_vibration_stop);
				//MainActivity.this.unregisterReceiver(morseMessageReceiver);
				stopService(new Intent(MainActivity.this,MorseMessageService.class));
				
				Toast.makeText(MainActivity.this, "Morse Message Listener Stopped", Toast.LENGTH_SHORT).show();
				db.toggleListener();
				}
				else
				{
					msgListener.setImageResource(R.drawable.morsemessages_vibration);
					//MainActivity.this.registerReceiver(morseMessageReceiver, filter);
					startService(new Intent(MainActivity.this,MorseMessageService.class));
					Toast.makeText(MainActivity.this, "Morse Message Listener Started", Toast.LENGTH_SHORT).show();
					db.toggleListener();
				}
			
			
			}
		});  
		
		settingsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					vb.vibrate(50);
					
					Intent i = new Intent();
					i.setClassName("utility.first.morsemessages", "utility.first.morsemessages.SettingsActivity");
					startActivity(i);
			}
		});
		
		
		helpButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vb.vibrate(50);
			Intent i = new Intent();
			i.setClassName("utility.first.morsemessages", "utility.first.morsemessages.HelpActivity");
			startActivity(i);
			}
		});
	}

	
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("main activity destroyed ","main activity may be force fully closed");
		db.close();
	}
	
	
	private void showStatus(Database db) {
		// TODO Auto-generated method stub
		
	if(db != null)
		if(db.isOn())
			Toast.makeText(MainActivity.this, "is on", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(MainActivity.this, "is off", Toast.LENGTH_SHORT).show();
		}
	
	
	
	private boolean isListenerServiceRunning()
	{
		//checks for whether listener service is running or not
		boolean running = false;
		acm =(ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for(RunningServiceInfo service : acm.getRunningServices(Integer.MAX_VALUE))
		{
		
			if(service.service.getClassName().equals(LISTENERSERVICE))
				return true;
		}
		return false;
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("main activity destroyed ","main activity may be force fully closed");
		db.close();
	}
	
}


