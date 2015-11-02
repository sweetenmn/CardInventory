package Creation;

/**
 * Created by reedmershon on 11/1/15.
 */
public class Conditions {

    public String setConditions(int cardID, int NEWMINT, int EXCELLENT, int VERYGOOD, int GOOD, int POOR) {
        return("INSERT INTO Condition VALUES("+cardID+", "+NEWMINT+", "+EXCELLENT+", "+VERYGOOD+", "+GOOD+", "+POOR+")");
    }

    public String getConditions(int cardID) {
        return("SELECT * FROM Condition WHERE CardId = "+cardID);
    }
}