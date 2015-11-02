package Creation;

/**
 * Created by reedmershon on 10/30/15.
 */
public class Cards {

    public String addCard(String CardName, String SetName, int CardCount) {
        int setId = SetName.hashCode();

        if (Integer.toString(CardCount) == null) {
            CardCount = 0;
        }
        CardCount += 1;
        return ("INSERT INTO Card VALUES(" + CardCount + ", '" + CardName + "', " + setId + ")");
    }

    public String getCardID(String CardName) {
        return ("SELECT CardId FROM Card WHERE CardName = '" + CardName + "'");
    }

    public String getCard(String CardName) {
        return("SELECT * FROM Card WHERE CardName = '" + CardName + "'");
    }

    public String NextCardID() {
        return ("SELECT MAX(CardId) AS newID FROM Card");
    }
}
