package utility.first.morsemessages;

import utility.first.morsemessages.morseconverter.MorseConverter;
import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import android.webkit.WebSettings.TextSize;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class HelpActivity extends Activity {
	private MorseConverter converter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		converter = new MorseConverter();
		char[] alphabets = converter.getAlphabets();
		String[] morseCodes =converter.getMorseCodes();
		LinearLayout mainLayout = new LinearLayout(this);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		mainLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		////////////////////////////////Test Button ////////////////////////////////////////////////
		Button testButton = new Button(this);
		testButton.setText("Test - Train");
		
		testButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,0.3f));
		testButton.setBackgroundColor(Color.YELLOW);
		testButton.setTextColor(Color.BLACK);
		testButton.setHeight(150);
	
		testButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent();
				i.setClassName("utility.first.morsemessages","utility.first.morsemessages.TestActivity");
				startActivity(i);
			}
		});
		/////////////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////Text View///////////////////////////////////////////////////
		
		TextView help = new TextView(this);
		help.setText("MorseCode");
		help.setBackgroundColor(Color.BLACK);
		help.setTextColor(Color.WHITE);
		
		help.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,0.2f));
		help.setHeight(100);
		help.setGravity(Gravity.CENTER);
		
	
		////////////////////////////////////////////////////////////////////////////////////////////
		ScrollView scrollView = new ScrollView(this);
		scrollView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,0.5f));
		LinearLayout layout = new LinearLayout(this);
		layout.setBackgroundColor(Color.BLACK);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		for(int i=0;i<alphabets.length;i++)
		{
			//////////////////////////
			TextView tv = new TextView(this);
			tv.setText(alphabets[i]+"               "+morseCodes[i]);
			tv.setTextSize(50);
			tv.setBackgroundColor(color.black);
			tv.setTextColor(Color.YELLOW);
			tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			
			layout.addView(tv);
			//////////////////////////
		}
		 scrollView.addView(layout);
		 
		
		 mainLayout.addView(help);
		mainLayout.addView(scrollView);
		 mainLayout.addView(testButton);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 setContentView(mainLayout);
		 
		
		
	}
	

}
