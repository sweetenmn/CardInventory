package Creation;

/**
 * Created by reedmershon on 10/30/15.
 */
public class SetDB {

    public String addSet(String SetName) {
        //TODO: assign the set a unique ID
        return("INSERT INTO SetTable VALUES(" + String.valueOf(SetName.hashCode()) + ", '" + SetName + "')");
    }

    public String getSetID(String SetName) {
        return ("SELECT SetId FROM SetTable WHERE SetName = '" + SetName + "'");
    }

    public String getSetName(int setID) {
        return("SELECT SetName FROM SetTable WHERE SetID = "+ String.valueOf(setID));
    }
    
}