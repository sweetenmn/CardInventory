package Creation;

/**
 * Created by reedmershon on 10/30/15.
 */
public class CardDB {

    public String getCardID(String CardName, String SetName) {
        return ("SELECT CardId FROM CardTable WHERE CardName = '" + CardName + "' AND SetID = " + SetName.hashCode());
    }

    public String addCard(String CardName, String SetName) {
        int setId = SetName.hashCode();
        return ("INSERT INTO CardTable VALUES (NULL, '" + CardName + "', " + setId + ")");
    }

    public String getCard(String CardName) {
        return("SELECT * FROM CardTable WHERE CardName = '" + CardName + "'");
    }
}
