package utility.first.morsemessages.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class Database extends SQLiteOpenHelper {

	private static final String DBNAME = "morsemsg.db";
	private static final int VERSION = 1;
	
	//constructor
	
	
	
	public Database(Context context)
	{
		super(context, DBNAME, null, VERSION);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
//			Log.d("error in sql","before");
			db.execSQL("create table morsemsg(somename integer default 0,_id integer default 0);");
			db.execSQL("create table settingtable(_id integer default 0,dotdashduration integer default 0,gapduration integer default 0);");
			//		Log.d("error in sql","after");
			
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	//methods
	
	public int getDotDashDuration()
	{
		int ret = 0;
		Cursor c = getReadableDatabase().rawQuery("select * from settingtable where _id = 0",null);
		if(c!= null)
		{
		if(c.moveToFirst()){
		ret  = c.getInt(c.getColumnIndex("dotdashduration"));
		c.close();
		return ret;
		}
		else
		{
			Log.d("in getdot methd","no values should  insert some valuies");
			ContentValues values = new ContentValues();
			values.put("_id", 0);
			getWritableDatabase().insert("settingtable", null, values);
		}
		}
		c.close();
		return ret;
	}
	
	public int getGapDuration()
	{
		Cursor c = getReadableDatabase().rawQuery("select * from settingtable where _id = 0",null);
		int ret = 0;
		if(c!= null)
		{
		if(c.moveToFirst()){
		ret  = c.getInt(c.getColumnIndex("gapduration"));
		c.close();
		return ret;
		}
		else
		{
			Log.d("in getdot methd","no values should  insert some valuies");
			ContentValues values = new ContentValues();
			values.put("_id", 0);
			getWritableDatabase().insert("settingtable", null, values);
		}
		}
		c.close();
		return 0;
	}
	
	public void setDotDashDuration(int value)
	{
		ContentValues values = new ContentValues();
		values.put("dotdashduration", value);
		
		getWritableDatabase().update("settingtable", values,"_id = 0 ", null);
		
		
	}
	
	public void setGapDuration(int value)
	{
		ContentValues values = new ContentValues();
		values.put("gapduration", value);
		
		getWritableDatabase().update("settingtable", values,"_id = 0 ",null);
		
	}
	
	public boolean isOn()
	{
//		Log.d("debug","is on ");
		
		
		try{
		Cursor c = getReadableDatabase().rawQuery("select * from morsemsg;", null);
		
		
		if(c!=null )
		{
			if(c.moveToFirst()){
				String t[] =c.getColumnNames();
				for(String s:t)
				{
					Log.d("columms",s);
				}
			Log.d("value updated","on = "+c.getInt(0)+" "+c.getInt(1)+" "+c.getCount());
			
			if(c.getInt(0)==1){ // column 0 is on if 1 it is on else  off
//				Log.d("getInt",c.getInt(0)+"");
				c.close();
				return true;
			}
			else
				{
	//			Log.d("getInt",c.getInt(0)+"");
				c.close();
				return false;
				}
			}
			
			else
			{
				ContentValues cv = new ContentValues();
				cv.put("somename", 1);
				getWritableDatabase().insert("morsemsg", null, cv);
	//			Log.d("debugging","not move to first");
				return false;
			}
		}
		
		c.close();
		
		}
		catch(SQLException exception)
		{
			Log.e("sql error","table may be "+exception.toString());
		}
		return false;
		
		
		
		
		
	}


	public void toggleListener()
	{
		
		Log.d("debug","toggle");
		ContentValues cv = new ContentValues();
		
		if(isOn()){
		cv.put("somename", 0);
		
		}
		else{
		cv.put("somename", 1);
		
		}
		
		getWritableDatabase().delete("morsemsg", "_id = 0", null);
		
		getWritableDatabase().insert("morsemsg", "_id", cv);
		
	
	}
	
	
	
}
