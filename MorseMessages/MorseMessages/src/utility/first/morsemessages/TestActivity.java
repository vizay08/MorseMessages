package utility.first.morsemessages;

import java.util.Random;

import utility.first.morsemessages.morseconverter.MorseConverter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TestActivity extends Activity {

	/*activity that  provides test to the user .
	 * 1.test to identify using vibrations
	 * 2.test to identify the dots and dashes patterns 
	 * 3.test to enter the dots and dashes for characters
	*/
	private EditText senseInput;
	private char randomCharacter ;
	private String message;
	private MorseConverter converter;
	private Vibrator vibrator;
	private InputMethodManager imm;
	private long DOT = 100;
	private long DASH = 300;
	
	private final int MSG = 2345;
	private final int NAME = 5234;
	private final int TEST = 2837;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.testlayout);
		///////////////Sense Button /////////////////////////////////////////
		converter = new MorseConverter();
		
		
		Button senseButton = (Button)findViewById(R.id.sensebutton);
		senseButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				imm.hideSoftInputFromInputMethod(senseInput.getWindowToken(), 0);
				Random r = new Random();
				boolean number=r.nextBoolean();
				int j;
				if(number)
					j=Math.abs(r.nextInt())%10+'0';
				else
					j=Math.abs(r.nextInt())%26+'a';
				randomCharacter = (char)(j);
				Log.d("in testactivity",j+" "+randomCharacter);
				message = converter.getMorseCode(""+randomCharacter,TEST);
				Log.d("in testactivity",j+" "+randomCharacter+" "+message);
				if(message==null)
					message = " ";
				for(int i=0;i<message.length();i++){
				if(message.charAt(i)=='.')
				{
					vibrator.vibrate(DOT);
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		
				}	
				else if(message.charAt(i)=='-')
				{
					vibrator.vibrate(DASH);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(message.charAt(i)==' ')
				{
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			 }// end of for
			}
		});
		
		//////////////////////////////////////////////////////////////////////
		
		senseInput = (EditText)findViewById(R.id.senseinput);
		////////////////////////////////////////////////////////////////////
		
		Button checkButton = (Button)findViewById(R.id.checkbutton);
		checkButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				imm.hideSoftInputFromInputMethod(senseInput.getWindowToken(), 0);
				String s;
				char c;
				try{
				s = senseInput.getText().toString();
				c = s.charAt(0);
				}
				catch(Exception e)
				{
					c='@';
				}
				
				
				if(c==randomCharacter )
					Toast.makeText(TestActivity.this, "Wow! You are Awesome", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(TestActivity.this, "Oops! Try Again", Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
}
