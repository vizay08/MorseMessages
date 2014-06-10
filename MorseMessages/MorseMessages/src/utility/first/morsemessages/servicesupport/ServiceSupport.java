package utility.first.morsemessages.servicesupport;

import android.content.Context;
import utility.first.morsemessages.database.Database;

public class ServiceSupport {
////////////This class contains all the help for the Vibrator Service /////////////
	private Database database;
	
	public ServiceSupport(Context context) {
		// TODO Auto-generated constructor stub
		database = new Database(context);
	}
	
	
	public int getDotDashDuration()
	{
		return database.getDotDashDuration();
	}
	
	public int getGapDuration()
	{
		return database.getGapDuration();
	}
}
