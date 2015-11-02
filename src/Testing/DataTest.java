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
        newDb("TEST");
        String n = "New Set";
        String nc = "Card1";
        int cardc = 0;

        database.UpdateDb(s.addSet(n));
        database.UpdateDb(c.addCard(nc, n, 10));

        database.UpdateDb(c.getCardID(nc));

    }



}
