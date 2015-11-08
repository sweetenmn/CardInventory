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
    
	public String getFoil(int cardID, Database db) throws ClassNotFoundException, SQLException{
		RarityDB rarities = new RarityDB();
		ResultSet rarityInfo = db.getResults(rarities.getRarityQuery(cardID));
		String foil = rarityInfo.getString("Foil");
		db.closeConnection();
		return foil;
	}
	
	public String getRarity(int cardID, Database db) throws ClassNotFoundException, SQLException{
		ResultSet rarityInfo = db.getResults(getRarityQuery(cardID));
		String rarity = "";
		if (rarityInfo.next()){
			rarity = rarityInfo.getString("Rarity");
		}
		db.closeConnection();
		return rarity;	
	}
}
