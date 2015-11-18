package Creation;

import Database.Database;
import Database.SearchResults;
import GUI.CardRow;
import GUI.DataRow;
import GUI.TableType;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by reedmershon on 11/17/15.
 */
public class CardTest{

    int[] conditions = new int[]{1, 2, 3, 1 , 2};
    String name, set, rarity, foil;

    @Test
    public void createCards() {
        name = "Grapeshot Catapult";
        set = "Old";
        rarity = "Common";
        foil = "false";
        Card card = new Card(name, set, rarity, foil, conditions);
        assertEquals(card.getName(), "Grapeshot Catapult");
        assertEquals(card.getConditions(), conditions);
        assertEquals(card.getFoil(), "false");
        assertEquals(card.getSet(), "Old");
        assertEquals(card.getRarity(), "Common");
        assertEquals(card.getTotal(), "9");
        Card newCard = new Card("Cool", "New", rarity, foil, conditions);
        assertEquals(newCard.getName(), "Cool");
        assertNotEquals(card.getSet(), "New");
    }

    @Test
    public void CardDB() throws ClassNotFoundException{
        name = "Grapeshot Catapult";
        set = "Old";
        rarity = "Common";
        foil = "false";
        Card card = new Card(name, set, rarity, foil, conditions);
        Database db = new Database("TestDB");
        card.sendToDatabase(db);
        assertNotNull(db);
    }

}
