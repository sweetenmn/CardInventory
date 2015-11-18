package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by reedmershon on 10/30/15.
 */
public class CardDB {


    public String getCardID(String cardName, String setName, Database db) throws ClassNotFoundException, SQLException {
    	int setID = 0;
    	ResultSet rs = db.getResults("SELECT * FROM SetTable WHERE SetName = '" + setName + "'");
    	if (!rs.isClosed()){
    		if (rs.next()){
    			setID = rs.getInt("SetId");
    		}
    	}
    	db.closeConnection();
        return ("SELECT CardId FROM CardTable WHERE CardName = '" + cardName + "' AND SetID = " + setID);
    }
    
	public int getLatestCardID(String name, String set, Database database) throws ClassNotFoundException, SQLException{
		ResultSet rs;
		int cardID = 0;
		rs = database.getResults(getCardID(name, set, database));
		while(rs.next()){		
			cardID = rs.getInt("CardId");
		}
		database.closeConnection();
		database.closeConnection();
		return cardID;
	}

    public String addCard(String cardName, String setName, Database db) throws ClassNotFoundException, SQLException {
    	int setID = 0;
    	ResultSet rs = db.getResults("SELECT * FROM SetTable WHERE SetName = '" + setName + "'");
        	if (!rs.isClosed()){
        		if (rs.next()){
        			setID = rs.getInt("SetId");
        		}
        	}
        db.closeConnection();
        return ("INSERT INTO CardTable VALUES (NULL, '" + cardName + "', " + setID + ")");
    }
    
    
    
    public boolean cardExists(String cardName, String setName, String foil, Database db) throws SQLException, ClassNotFoundException{
    	boolean exists = false;
    	ArrayList<Integer> idChecks = new ArrayList<Integer>();
		ResultSet rs = db.getResults(getCardID(cardName, setName, db));
		while (rs.next()){
			idChecks.add(rs.getInt("CardID"));
		}
		db.closeConnection();
		RarityDB rarities = new RarityDB();
		for (int i: idChecks){
			String isFoil = rarities.getFromRarities("Foil", i, db);
			if (isFoil.equals(foil)){
				exists = true;
			}
		}
		return exists;
    }


	public String updateCard(String cardName, String setName, int CardID, String oldName, Database db) throws ClassNotFoundException, SQLException{
		int setID = 0;
		ResultSet rs = db.getResults("SELECT * FROM SetTable WHERE SetName = '" + setName + "'");
		if (!rs.isClosed()){
			if (rs.next()){
				setID = rs.getInt("SetId");
			}
		}
		db.closeConnection();
		return ("UPDATE OR IGNORE CardTable SET CardName = '" + cardName + "' WHERE CardId = " + CardID + " AND CardName = '" +
				oldName + "' AND SetId = " + setID);
	}
	
	public String deleteCard(int i){
		return("DELETE FROM CardTable WHERE CardId = " + i);
	}
	
	public int getCardIDInteger(String cardName, String setName, Database db) throws ClassNotFoundException, SQLException{
		int ID = 0;
		ResultSet rs = db.getResults(getCardID(cardName, setName, db));
		if (rs.next()){
			ID = rs.getInt("CardId");
		}
		db.closeConnection();
		return ID;
	}
}
