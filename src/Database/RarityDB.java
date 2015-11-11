package Database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by reedmershon on 11/1/15.
 */
public class RarityDB {

    public String setRarity(int cardID, String rarity, String foil){
        return ("INSERT INTO Rarity VALUES(" + cardID +", '"+ rarity + "', '" + foil + "')");
    }
    
    public String getRarityQuery(int cardID){
    	return ("SELECT * FROM Rarity WHERE CardID = " + cardID);
    }
    
	public String getFromRarities(String query, int cardID, Database db) throws ClassNotFoundException, SQLException{
		String foil = "";
		ResultSet rarityInfo = db.getResults(getRarityQuery(cardID));
		if (rarityInfo.next()){
			
			foil = rarityInfo.getString(query);
		}
		db.closeConnection();
		return foil;
	}
	

	public String updateRarity(int cardID, String rarity, String foil) {
		return ("UPDATE Rarity SET Rarity = '" + rarity + "', Foil = '" + foil + "' WHERE CardId = " + cardID);
	}
}
