package Database;

import java.sql.SQLException;

/**
 * Created by reedmershon on 10/30/15.
 */
public class SetDB {

    public String addSet(String setName) {
	    return("INSERT OR IGNORE INTO SetTable VALUES(NULL, '" + setName + "')");

	}

    public String getSetID(String setName) {
        return ("SELECT * FROM SetTable WHERE SetName = '"+ setName + "'");
    }

    public String getSetNameQuery(int setID) {
        return("SELECT SetName FROM SetTable WHERE SetID = "+ String.valueOf(setID));
    }
    
	public String getSetName(int setID, Database db) throws ClassNotFoundException, SQLException{
		String set = db.getResults(getSetNameQuery(setID)).getString("SetName");
		db.closeConnection();
		return set;
	}

    public String updateSet(String setName, int SetID) {
        return ("UPDATE OR IGNORE SetTable SET SetName = '" + setName + "' WHERE SetId = " + SetID);
    }

    //Here's the delete function
    public String deleteSet(int SetID) {
        return ("DELETE FROM SetTable WHERE SetId = " + SetID);
    }
}
