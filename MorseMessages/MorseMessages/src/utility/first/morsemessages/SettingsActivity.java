package utility.first.morsemessages;

import utility.first.morsemessages.database.Database;
import android.R.color;
import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout.Alignment;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	
	private ScrollView sv;
	private int dotBarWidth,dashBarWidth,dotStartX,dashStartX;
	private TextView dotTextView,gapTextView;
	private Button addListButton;
	private int fromInner;
	private LinearLayout popUpLayout,mainLayout;
	private PopupWindow popup ;
	private boolean addContactsPopupAlive = false;
	private LinearLayout popSubLayout;
	private ProgressDialog progressSpinner ;
	private Database database;
	private int progressGlobal = 0,gapProgressGlobal = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		database = new Database(SettingsActivity.this);
		progressGlobal = database.getDotDashDuration();      //this is added may be the reaon for delete
		
		gapProgressGlobal = database.getGapDuration();
		
		super.onCreate(savedInstanceState);
		final Cursor contactCursor;
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		setContentView(R.layout.settingslayout);
		
		
		Log.d("database stored value",""+progressGlobal);
		
		
		dotTextView  = (TextView)findViewById(R.id.dotvalue);
		dotTextView.setText(((progressGlobal+1)*100)+" ms");
		
		gapTextView = (TextView)findViewById(R.id.dashvalue);
		gapTextView.setText(((gapProgressGlobal+1)*100)+" ms");
		///////////////////////Progress dialog///////////////////////////////////////////////
	
		progressSpinner  = new ProgressDialog(this);
		////////////////////////////////////////////////////////////////////////////////////
		
		////making full screen//////////////////////////////////////////////////////////////////////////////////////
	
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		contactCursor= this.getContentResolver().query(Phone.CONTENT_URI, null, null, null,Phone.DISPLAY_NAME);
		
		int i =  contactCursor.getColumnCount();
		int j = contactCursor.getCount();
		
		Log.d("total number of contacts",i+" "+j+" "+ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
		///////////programatically adding control points to popup screen////////////////////////////////////////////
		
		/*TextView text = new TextView(this);
		text.setText("Welcome to the screen");
		text.setTextColor(Color.YELLOW);
		text.setBackgroundColor(Color.BLACK);
		text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));*/
		
		
		
		
		Button doneAdding = new Button(this);
		//sv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,100));
		doneAdding.setText("Done");
		doneAdding.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
	
		
		
		doneAdding.setGravity(Gravity.CENTER);
		doneAdding.setBackgroundColor(Color.BLACK);
		doneAdding.setTextColor(Color.YELLOW);
		doneAdding.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				addContactsPopupAlive = false;
				popup.dismiss();
				popSubLayout.removeAllViews();
			}
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		popup = new PopupWindow(this);
		mainLayout = new LinearLayout(this);  //main layout for the whole settings activity
		
		
	
		SeekBar dotSeekBar = (SeekBar)findViewById(R.id.dotseekBar);
		SeekBar gapSeekBar = (SeekBar)findViewById(R.id.dashseekBar);
		
		
		dotSeekBar.setProgress(progressGlobal);   //this is added may be the reason for error
		gapSeekBar.setProgress(gapProgressGlobal);
		popUpLayout =  new LinearLayout(this);
		
		popUpLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		popUpLayout.setOrientation(LinearLayout.VERTICAL);
		
		
			
	
		popUpLayout.addView(doneAdding);
		
		sv = new ScrollView(this);
		sv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		
		popSubLayout = new LinearLayout(this);
		
		popSubLayout.setBackgroundColor(Color.BLACK);
		popSubLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		popSubLayout.setOrientation(LinearLayout.VERTICAL);
	
		
	
		
		addListButton = (Button) findViewById(R.id.addlist);

	
		dotSeekBar.setMax(9);
		dotBarWidth = dotSeekBar.getWidth();
		
		
		///////////listeners ///////////////////////////////////////////////////////////////////////////////////////
		dotSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar s) {
				// TODO Auto-generated method stub
					database.setDotDashDuration(progressGlobal);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar s) {
				// TODO Auto-generated method stub
				
				Log.d("in seek start","seek stated");
				
			}
			
			@Override
			public void onProgressChanged(SeekBar s, int progress, boolean fromUser) {
				// TODO Auto-generated method stub
					progressGlobal = progress;
					Log.d("in seek bar","value changing "+progress);
					dotTextView  = (TextView)findViewById(R.id.dotvalue);
					dotTextView.setText((progress+1)*40+" ms : "+(progress+1)*120+" ms");
					
			}
		});
		
		
		gapSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				database.setGapDuration(gapProgressGlobal);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar s, int progress, boolean arg2) {
				// TODO Auto-generated method stub
				gapProgressGlobal = progress;
				Log.d("in seek bar","value changing "+progress);
				gapTextView  = (TextView)findViewById(R.id.dashvalue);
				gapTextView.setText((progress+1)*40+" ms");
			}
		});
		
		addListButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				// TODO Auto-generated method stub
				if(e.getAction() == MotionEvent.ACTION_DOWN)
				{
					addListButton.setTextColor(Color.WHITE);
				}
				else if(e.getAction() == MotionEvent.ACTION_UP)
				{
					addListButton.setTextColor(Color.YELLOW);
				}
					
				return false;
			}
		});
		
		addListButton.setOnClickListener(new View.OnClickListener() { //addList button   
			/// this should be  modified in case of new functionality
		
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			
				//Toast msg = new Toast(SettingsActivity.this);
				
			//	msg.setDuration(500);
			//	msg.show();
				
				
				int i=0;
				
				if(!addContactsPopupAlive)
				while(contactCursor!= null && contactCursor.moveToNext())//&&fromInner!=10)//contactCursor.moveToNext())
				{
					//TextView phoneNumber = new TextView(SettingsActivity.this);
					CheckBox checkBox = new CheckBox(SettingsActivity.this);
					final String name =  contactCursor.getString(contactCursor.getColumnIndex("display_name"));
					//phoneNumber.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
					checkBox.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
					//phoneNumber.setText(name);
					
					if(Integer.parseInt(contactCursor.getString(contactCursor.getColumnIndex( ContactsContract.Contacts.HAS_PHONE_NUMBER ))) > 0)
					{
						checkBox.setText(name);
						//phoneNumber.setTextSize(20);
						checkBox.setTextSize(20);
						checkBox.setLineSpacing(2, 1);
						//phoneNumber.setBackgroundColor(color.black);
						checkBox.setBackgroundColor(color.black);
						//phoneNumber.setTextColor(Color.YELLOW);
						checkBox.setTextColor(Color.YELLOW);
					
						
						
					
						//popSubLayout.addView(phoneNumber);
						popSubLayout.addView(checkBox);
					//	checkBox.setText(i);
						i++;
					}
					checkBox.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							
							// Toast.makeText(SettingsActivity.this, name+"  is clicked",Toast.LENGTH_SHORT).show();
						}
					});
				}
				
				
				/*
				
				CheckBox cb = (CheckBox)findViewById(5);
				if(cb.isChecked())
					Toast.makeText(SettingsActivity.this, cb.getText()+" is checked", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(SettingsActivity.this, cb.getText()+" is checked", Toast.LENGTH_SHORT).show();
				*/
				contactCursor.moveToFirst();
				fromInner = i;
				
				
				
				
				if(!addContactsPopupAlive){
				popup.showAtLocation(mainLayout, Gravity.BOTTOM,0,0 );
				popup.update(0,0,700,800);
				
				
			    addContactsPopupAlive = true;
				
				}
				
				
			}
		});
		
		
		
		
		

		sv.addView(popSubLayout);
		popUpLayout.addView(sv);
		popup.setContentView(popUpLayout);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}

	private void open() {
		// TODO Auto-generated method stub

		Log.d("in open function","button called open funtion");
		progressSpinner.setMessage("Loading  Contacts");
		progressSpinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressSpinner.show();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		database.close();
	}


}
