package utility.first.morsemessages.listener;


import utility.first.morsemessages.MainActivity;
import utility.first.morsemessages.morseconverter.MorseConverter;
import utility.first.morsemessages.servicesupport.ServiceSupport;
import utility.first.morsemessages.vibrator.MessageVibrator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MorseMessageReceiver extends BroadcastReceiver { 

	final String RECEIVER_TYPE="android.provider.Telephony.SMS_RECEIVED";
	MorseConverter converter = new MorseConverter();
	private int dotDashDuration = 0;
	private int gapDuration = 0;
	
	private int MSG = 2345;
	private int NAME = 5234;
	
	private Thread vibrationThread;
	MessageVibrator vibrator;
	
	Vibrator vibrate;
	
	String contactOutput = null;
	String outputString = null;
	
	
	private String getContactName(Context context, String number) {

	    String name = null;

	    // define the columns I want the query to return
	    String[] projection = new String[] {
	            ContactsContract.PhoneLookup.DISPLAY_NAME,
	            ContactsContract.PhoneLookup._ID};

	    // encode the phone number and build the filter URI
	    Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

	    // query time
	    Cursor cursor = context.getContentResolver().query(contactUri, projection, null, null, null);

	    if(cursor != null) {
	        if (cursor.moveToFirst()) {
	            name =      cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
	         //   Log.v(TAG, "Started uploadcontactphoto: Contact Found @ " + number);            
	         //   Log.v(TAG, "Started uploadcontactphoto: Contact name  = " + name);
	        } else {
	          // Log.v(TAG, "Contact Not Found @ " + number);
	        }
	        cursor.close();
	    }
	    return name;
	}

	@Override
	public void onReceive(Context context,Intent intent) {
		// TODO Auto-generated method stub
			converter = new MorseConverter(); 
		
			ServiceSupport support = new ServiceSupport(context.getApplicationContext());
				dotDashDuration = support.getDotDashDuration();
				gapDuration = support.getGapDuration();
				
				
				Log.d("dotduration",""+dotDashDuration);
				Log.d("gap duration",""+gapDuration);
				vibrator=new MessageVibrator();
				
				vibrate =(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
				if(intent.getAction().equals(RECEIVER_TYPE))
				{
					Log.d("in morsemessagerecver dotduration",""+dotDashDuration);
					Log.d("in morsemessagerecver gap duration",""+gapDuration);
					
					SmsMessage messages = null;
					String msgSender = null;
					String msgBody = null;
					Bundle bundle = intent.getExtras();
					if(bundle!= null)
					{
						Log.d("recv_type is sms","oh! got your message!!");
						Object[] pdus = (Object[])bundle.get("pdus");
						
						
							try{
							messages= SmsMessage.createFromPdu((byte[])pdus[0]);
							msgSender = messages.getOriginatingAddress();
							msgBody = messages.getMessageBody();
							
						
							/*Toast.makeText(context, "SMS Ochindi : "+messages.getOriginatingAddress(),
								     Toast.LENGTH_LONG).show();*/
							
							String contactName = null;
							
							
							
							contactName = getContactName(context, msgSender);
							if(contactName != null)
								contactOutput = converter.getMorseCode(contactName, NAME);
							else
								contactOutput = converter.getMorseCode(msgSender, NAME);
								
							Log.d("in MorseMessageReceiver onReceiver method","Msg sender"+msgSender+"name: "+contactName+" morsecode "+contactOutput);
							
							outputString = converter.getMorseCode(msgBody,MSG);//msgSender);
							
							Log.d("in MorseMessageReceiver onReceiver method","Msg Body "+msgBody+" converted output "+outputString);
							
							/*
							vibrator.vibrate(vibrate, contactOutput);
							Thread.sleep(100);
							
							vibrate.vibrate(400);
							
							Thread.sleep(100);
							
							vibrator.vibrate(vibrate, outputString);
							*/
						//	outputString = contactOutput +" "+outputString;
						
							/*
							
							if(outputString == null)
							{
								Log.e("in MorseMessageReceiver onReceive method","OOps...output string is null");
							}
							else
							{
								Log.d("in MorseMessageReceiver onReceive method","output morse code to be viberated is "+outputString);
							}
							
						/*	Toast.makeText(context, "SMS Idigo : "+outputString,
								     Toast.LENGTH_LONG).show();*/
							
							
							
							
								
						/*	Toast.makeText(context, "Durations : "+dotDashDuration + " " + gapDuration,
								     Toast.LENGTH_LONG).show();
						
							AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
					        
					        
					      
					        
					     
					        
					        int ringerMode = audioManager.getRingerMode();
					        
					        switch(ringerMode)
					        {
					        case AudioManager.RINGER_MODE_NORMAL:
					        	Log.d("in MorseMessageReceiver ","Ringing mode is normal");
					        	break;
					        case AudioManager.RINGER_MODE_SILENT:
					        	Log.d("in MorseMessageReceiver ","Ringing mode is silent");
					        	break;
					        case AudioManager.RINGER_MODE_VIBRATE:
					        	Log.d("in MorseMessageReceiver ","Ringing mode is Vibrate");
					        	break;
					       
					        }   
					     
					        
					        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
					    */    
					     //   vibrator.setDotndDash(dotDashDuration);
						//	vibrator.setSleepTime(gapDuration);
							//vibrator.vibrate(vibrate, outputString);
					        
							
							//audioManager.setRingerMode(ringerMode);
						
							
							
//							vibrator.setDotndDash(4);
//							vibrator.setSleepTime(9);
//							vibrator.vibrate(vibrate2, outputString);
							
							vibrationThread = new Thread() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									
									vibrator.vibrate(vibrate, contactOutput);
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									vibrate.vibrate(400);
									
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									vibrator.vibrate(vibrate, outputString);
									
									
									
								}
							};
							
							vibrationThread.start();
							
							}catch(Exception e)
							{
								Toast.makeText(context, "Some error occured while retrieving message", Toast.LENGTH_SHORT).show();
								Log.e("error in mmrecv", e.toString());
							}

					}
					
				}
	
	}

}
