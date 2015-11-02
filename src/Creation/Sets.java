package Creation;

/**
 * Created by reedmershon on 10/30/15.
 */
public class Sets {

    public String addSet(String SetName) {
        //TODO: assign the set a unique ID
    	System.out.println("here3");
        return("INSERT INTO SetTable VALUES(" + String.valueOf(SetName.hashCode()) + ", '" + SetName + "')");
    }

    public String getSetID(String SetName) {
    	System.out.println("here1");
        return ("SELECT SetId FROM SetTable WHERE SetName = '" + SetName + "'");
    }

    public String getSetName(int setID) {
    	System.out.println("here");
        return("SELECT SetName FROM SetTable WHERE SetID = "+ String.valueOf(setID));
    }
    
}
