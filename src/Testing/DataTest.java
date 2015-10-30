package Testing;

import application.Database;
import org.junit.Test;

/**
 * Created by reedmershon on 10/30/15.
 */
public class DataTest {

    Database database;

    @Test
    public void test() {
        database.addSet("7th Edition");
        database.addCard("Thorn Elemental", "7th Edition");

        assert "Thorn Elemental".equals("Thorn Elemental");
    }



}
