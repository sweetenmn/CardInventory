package Testing;

import Creation.*;
import application.Database;
import org.junit.Test;

/**
 * Created by reedmershon on 10/30/15.
 */
public class DataTest {

    Database database;
    Cards c = new Cards();
    Sets s = new Sets();

    public void newDb(String DBName) throws ClassNotFoundException{
        database = new Database(DBName);
    }


    @Test
    public void test() throws ClassNotFoundException{
        String dbName = "TEST";
        newDb("TEST");
        String n = "New Set";
        String nc = "Card1";
        int cardc;

        database.UpdateDb(s.addSet(n), dbName);
        String cc = database.GetValues(c.NextCardID(), dbName, "CardId");

        if (Integer.valueOf(cc) != null) {
            cardc = Integer.valueOf(cc);
        } else {
            cardc = 0;
        }

        database.UpdateDb(c.addCard(nc, n, cardc), dbName);
        database.UpdateDb(c.addCard("Card2", n, cardc), dbName);

        System.out.println(database.GetValues(s.getSet("New Set"), dbName, "SetId"));
        System.out.println(database.GetValues(c.getCard("Card2"), dbName, "SetId"));
        System.out.println(database.GetValues(c.getCard("Card1"), dbName, "SetId"));

    }



}
