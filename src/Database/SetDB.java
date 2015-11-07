package Database;

/**
 * Created by reedmershon on 10/30/15.
 */
public class SetDB {

    public String addSet(String setName) {
	return("INSERT OR IGNORE INTO SetTable VALUES(NULL, '" + setName + "')");

	}

    public String getSetID(String setName) {
        return ("SELECT SetId FROM SetTable WHERE SetName = '"+ setName + "'");
        
    }

    public String getSetName(int setID) {
        return("SELECT SetName FROM SetTable WHERE SetID = "+ String.valueOf(setID));
    }
    
}
