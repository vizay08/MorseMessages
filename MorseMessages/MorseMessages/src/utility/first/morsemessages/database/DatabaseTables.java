package utility.first.morsemessages.database;

public class DatabaseTables {
	
	public static final int VERSION  = 1;    //VERSION OF SQLITE DATABASE
	public static final String LISTENERDB="listener.db"; //first db
	private static final String DATASTRING = "text";
	private static final String DATAINT = "integer";
	private static final String DATAREAL = "real";
	private static final String NOTNULL = "not null";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String DEFAULT = "default";
	//////////////////////////////////////////////////////////////////////
	public class ListenerTable{     		//LISTENER TABLE ENTITIES
		public static final String TABLE_NAME ="listener_table";  //TABLE NAME :LISTENER_TABLE
		public static final String ALLCONTACTS ="all_contacts" ;     // whether button is on or not
		public static final String NAMEORMSG = "msg_or_name";
		public static final String MSG = "msg";
		public static final String NAME = "name";
		public static final String TOTALCONTACTS = "total contacts";
		
		public static final String CREAT_TABLE = "create table "+TABLE_NAME+" ( "+ALLCONTACTS+" "+DATASTRING+" "+DEFAULT+" "+TRUE+","+NAMEORMSG+" "+DATASTRING+" "+DEFAULT+" "+NAME+");";
		//more attribtes are to be added
	}
	
	public class SelectedContactsTable{
		public static final String TABLE_NAME = "contacts_table";
		public static final String CONTACT_NAME = "contact_name";
		public  static final String CONTACT_NUMBER = "contact_number";
		
		public static final String CREATE_TABLE = "create table "+TABLE_NAME+" ( "+CONTACT_NAME+" "+DATASTRING+" ,"+CONTACT_NUMBER +" "+DATASTRING+");";
		
		
				
	}
	
	public class VibrationsDurationTable{
		public static final String TABLE_NAME = "vibrations_table";
	}
	
	
	
}// END OF CLASS DATABASETABLES

