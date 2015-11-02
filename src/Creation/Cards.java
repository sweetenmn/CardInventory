package Creation;

/**
 * Created by reedmershon on 10/30/15.
 */
public class Cards {

    public String getCardID(String CardName) {
        return ("SELECT CardId FROM CardTable WHERE CardName = '" + CardName + "'");
    }

    public String addCard(String CardName, String SetName, int count) {

        int setId = SetName.hashCode();
        System.out.println("INSERT INTO CardTable VALUES (" + count + ", '" + CardName + "', " + setId + ")");

        return ("INSERT INTO CardTable VALUES (" + count + ", '" + CardName + "', " + setId + ")");
    }

    public String getCard(String CardName) {
        return("SELECT * FROM CardTable WHERE CardName = '" + CardName + "'");
    }
}
