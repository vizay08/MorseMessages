package utility.first.morsemessages.service;

import utility.first.morsemessages.database.Database;
import utility.first.morsemessages.listener.MorseMessageReceiver;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.ToggleButton;

public class MorseMessageService extends Service{
	
	private boolean onCreated=false,onStarted=false,onDestroyed=false;
	MorseMessageReceiver receiver = new MorseMessageReceiver();
	IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	 
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		onCreated = true;
		Log.d("Listener SerVice","Listener Service Created");
		registerReceiver(receiver, filter);
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	//	db.toggleListener();
		onDestroyed = true;
		onCreated = false;
		onStarted  = false;
		
		Log.d("Listener SerVice","Listener Service destoyed"+onCreated+" "+onStarted+" "+onDestroyed);
		unregisterReceiver(receiver);
		
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		onStarted = true;
		Log.d("Listener SerVice","Listener Service Started "+onStarted);
	}
}
