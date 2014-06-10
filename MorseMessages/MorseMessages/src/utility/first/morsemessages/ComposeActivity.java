package utility.first.morsemessages;



import utility.first.morsemessages.data.Data;
import utility.first.morsemessages.morseconverter.MorseConverter;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;

import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.view.inputmethod.InputMethodManager;

import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

public class ComposeActivity extends Activity{
	
	private Button vibButton;
	private Vibrator vibrator;
	private Thread vibrationThread;
	final long DOT = 100;
	private Data data;
	final long DASH = 300;
	private static volatile boolean isThreadRunning  = false;
	private static volatile boolean isVibratorRunning = false;
	
	private final int MSG = 2345;
	private final int NAME = 5234;
	private final int TEST = 2837;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		
		final InputMethodManager imm = (InputMethodManager)getSystemService(
			      this.INPUT_METHOD_SERVICE);
		
		vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.composelayout);
		
		
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		 data =new Data();
		 final EditText input = (EditText)findViewById(R.id.input);
		
		final TextView morseView =(TextView)findViewById(R.id.morsemessage);
		
		morseView.setTextSize(22);
		morseView.setText("");
		
				final Button b = (Button)findViewById(R.id.composebutton);
				vibButton= (Button)findViewById(R.id.vibratebutton);
				final Button stopVibButton = (Button)findViewById(R.id.stopvibration);
				
				try{
				b.setOnClickListener(new View.OnClickListener() {
		
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		
				imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
				
				MorseConverter converter = new MorseConverter();
			    
				String s=input.getText().toString(),output="";
				
				
				output = converter.getMorseCode(s,TEST);
				data.setStringData(output);
			    Log.d("b clicked","b button clicked "+s);
			    
				
					Toast.makeText(ComposeActivity.this, "Input text: "+s, Toast.LENGTH_SHORT).show();
					input.setText("");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Toast.makeText(ComposeActivity.this, "Output text: "+output, Toast.LENGTH_LONG).show();
		
					morseView.setText(output);
				
			}
			
			
			
		});
				
				
				stopVibButton.setOnClickListener(new View.OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						vibButton.setVisibility(Button.VISIBLE);
							Log.d("on click stop vibarate","button clicked");
							isThreadRunning = false;
							isVibratorRunning = false;
							vibrationThread.currentThread().interrupt();
							
							
					stopVibButton.setVisibility(Button.INVISIBLE);
					
					}
				});
				
				vibButton.setOnClickListener(new View.OnClickListener() {
					
					
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if(!isThreadRunning){
							stopVibButton.setVisibility(Button.VISIBLE);
							vibButton.setVisibility(Button.INVISIBLE);
							isThreadRunning = true;
							vibrationThread = new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
						
								isVibratorRunning = true;
								
							
								
										
										String output= data.getStringData();
										if(output == null)
											output=" ";
										Log.d("inpit from label",output);
										
											if(output!=null){
												for(int i=0;i<output.length();i++)
												{
													
												if(isVibratorRunning){
													
													
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
											} // end of if vibrator check
												else{
													
													break;
												}
												
											}//end of for
												
										}//end of if
											
									isVibratorRunning = false;
							isThreadRunning = false;

						
							
							
						};
							
						
				
					});
							/*
							Thread checkingThread = new Thread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
								
									while(new Thread(vibrationThread.getName()).isAlive()){//vibrationThread.isAlive())
										Log.d("about thread","Thread is running");
										isThreadRunning = true;
									
										}
									while(!new Thread(vibrationThread.getName()).isAlive()){//vibrationThread.isAlive())
										Log.d("about thread","Thread is not running");
										isThreadRunning = false;
									
										}
								
								}
							});*/
							
						//checkingThread.start();
						vibrationThread.start();
					
						if(!new Thread(vibrationThread.getName()).isAlive()){//vibrationThread.isAlive())
							Log.d("about thread","Thread is not running");
							isThreadRunning = false;
						
							}
						
						
						
						
						}
						
					}; // end of on click method
				}); // end of onclick listenre

				}catch (Exception e) {
					// TODO: handle exception
					Log.d("onclick", "error in click listeneer");
				}
				
				
			
		
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}


}
