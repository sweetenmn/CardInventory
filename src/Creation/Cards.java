package Creation;

/**
 * Created by reedmershon on 10/30/15.
 */
public class Cards {

    public String getCardID(String CardName) {
        return ("SELECT CardId FROM Card WHERE CardName = '" + CardName + "'");
    }

    public String addCard(String CardName, String SetName) {
        int cardCount = 0;
        cardCount += 1;

        int setId = SetName.hashCode();

        return ("insert into Card values('C" + cardCount + "', '" + CardName + "', '" + setId + "')");
    }

    public String getCard(String CardName) {
        return("SELECT * FROM Card WHERE CardName = '" + CardName + "'");
    }
}
