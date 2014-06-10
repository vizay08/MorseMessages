package utility.first.morsemessages.vibrator;

import java.util.ServiceLoader;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

public class MessageVibrator {
	
	private long UNIT = 100;
	int count   = 0;
	private long DOT = UNIT;
	private long DASH = 3*UNIT;
	private long INTERGAP = 3*UNIT;
	private long LETTERGAP = 6*UNIT;
	private long SLEEP = 7*UNIT;
	public void setDotndDash(int value)
	{
		value = 0;
		
		UNIT = (value+1)*UNIT;
		
		
		DOT = UNIT;
		DASH = 3*UNIT;
		LETTERGAP = 6*UNIT;
		INTERGAP = 3*UNIT;
		
		Log.d("in MessageVibrator setDotnDash method","Dot: "+DOT+" Dash: "+DASH);
	}
	
	
	public void setSleepTime(int value)
	{
		
		
		SLEEP = UNIT * SLEEP;
		Log.d("in MessageVibrator setSleepTime","Sleep time: "+SLEEP);
		
	}
	
	public void vibrate(Vibrator vibrator,String output)
	{
		
		
		for(int i=0;i<output.length();i++)
		{
			
			if(output.charAt(i)=='.')
			{
				vibrator.vibrate(DOT);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			}	
			else if(output.charAt(i)=='-')
			{
				vibrator.vibrate(DASH);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(output.charAt(i)==' ')
			{
				
				
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		Log.d("In MessageVibrator vibrate method","End of the process");
		
	}
	
}
