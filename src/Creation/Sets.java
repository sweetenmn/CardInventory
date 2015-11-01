package Creation;

/**
 * Created by reedmershon on 10/30/15.
 */
public class Sets {

    public String addSet(String SetName) {
        //TODO: assign the set a unique ID
        int setCount = SetName.hashCode();


        return("INSERT INTO SetTable VALUES('" + setCount + "', '" + SetName + "')");
    }

    public String getSetID(String SetName) {
        return ("SELECT SetId FROM SetTable WHERE SetName = '"+ SetName + "'");
    }

    public String getSet(String SetName) {

        return("SELECT * FROM SetTable WHERE SetName = '"+ SetName + "'");
    }
}
