package Database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by reedmershon on 10/30/15.
 */
public class CardDB {

    public String getCardID(String cardName, String setName, Database db) throws ClassNotFoundException {
    	int setID = 0;
    	try {
    		ResultSet rs = db.getResults("SELECT * FROM SetTable WHERE SetName = '" + setName + "'");
    		if (!rs.isClosed()){
    			
    			if (rs.next()){
    				setID = rs.getInt("SetId");
    			}
    		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	db.closeConnection();
        return ("SELECT CardId FROM CardTable WHERE CardName = '" + cardName + "' AND SetID = " + setID);
    }

    public String addCard(String cardName, String setName, Database db) throws ClassNotFoundException {
    	int setID = 0;
        	try {
        		ResultSet rs = db.getResults("SELECT * FROM SetTable WHERE SetName = '" + setName + "'");
        		if (!rs.isClosed()){
        			
        			if (rs.next()){
        				setID = rs.getInt("SetId");
        			}
        		}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	db.closeConnection();
        
        return ("INSERT INTO CardTable VALUES (NULL, '" + cardName + "', " + setID + ")");
    }

    public String getCard(String CardName) {
        return("SELECT * FROM CardTable WHERE CardName = '" + CardName + "'");
    }

    public String NextCardID() {
        return ("SELECT MAX(CardId) AS newID FROM Card");
    }
}
