package utility.first.morsemessages.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ListenerDatabase extends SQLiteOpenHelper {

	public ListenerDatabase(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DatabaseTables.LISTENERDB, null, DatabaseTables.VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.d("Database created", "tables are to be created");
		/////// tables are to be created when database is intialized /////////
		db.execSQL(DatabaseTables.SelectedContactsTable.CREATE_TABLE);
		db.execSQL(DatabaseTables.ListenerTable.CREAT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	
	public void addContacts() {
		// TODO Auto-generated method stub

	}
	
	
	public int getContactsCount() /// returns total number of contacts
	{
		Cursor c= getReadableDatabase().rawQuery("select "+DatabaseTables.ListenerTable.TOTALCONTACTS+" from "+DatabaseTables.ListenerTable.TABLE_NAME+" where _id=1", null);
		int i;
		return  c.getInt(c.getColumnIndex(DatabaseTables.ListenerTable.TOTALCONTACTS));
		
	}
	
	////////////////////  listener service uses these functions//////////////////////////////////////////////////////////////
	public boolean receiveFromAllContacts()
	{
		Cursor c = getReadableDatabase().rawQuery("select "+DatabaseTables.ListenerTable.ALLCONTACTS+" from "+DatabaseTables.ListenerTable.TABLE_NAME+" where _id=1", null);
		if(c.getString(c.getColumnIndex(DatabaseTables.ListenerTable.ALLCONTACTS))==DatabaseTables.TRUE)
			return true;
		else
			return false;
	}
	
	
	public boolean willReadMessage()    //doesn't read message it will give status of 
	{
		Cursor c = getReadableDatabase().rawQuery("select "+DatabaseTables.ListenerTable.NAMEORMSG+" from "+DatabaseTables.ListenerTable.TABLE_NAME+" where _id=1", null);
		if(c.getString(c.getColumnIndex(DatabaseTables.ListenerTable.NAMEORMSG))==DatabaseTables.ListenerTable.MSG)
			return true;
		else
			return false;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	

}
