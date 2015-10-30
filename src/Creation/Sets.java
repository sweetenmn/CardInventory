package Creation;

/**
 * Created by reedmershon on 10/30/15.
 */
public class Sets {

    public String addSet(String SetName) {
        //TODO: assign the set a unique ID
        int setCount = 0;
        setCount += 1;

        return("insert into Set values('S" + setCount + "', '" + SetName + "')");
    }

    public String getSetID(String SetName) {
        int setId = SetName.hashCode();
        return ("SELECT SetId FROM SetTable WHERE SetName = '"+ setId + "'");
    }

    public String getSet(String SetName) {

        return("SELECT * FROM SetTable WHERE SetName = '"+ SetName + "'");
    }
}
