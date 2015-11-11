package Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConditionDB {

    public String setConditions(int cardID, int[] conditions) {
    	int nearMint = conditions[0];
    	int excellent = conditions[1];
    	int veryGood = conditions[2];
    	int good = conditions[3];
    	int poor = conditions[4];
        return("INSERT INTO Condition VALUES(" + cardID + 
        		", " + nearMint + ", " + excellent +", " + 
        		veryGood + ", " + good + ", " + poor + ")");
    }

    public String getConditionsQuery(int cardID) {
        return("SELECT * FROM Condition WHERE CardId = " + cardID);
    }
    
	public int[] getConditions(int cardID, Database db) throws ClassNotFoundException, SQLException{
		ResultSet condInfo = db.getResults(getConditionsQuery(cardID));
		int[] conditionList = null;
		if (condInfo.next()){
			
			conditionList = new int[]{condInfo.getInt("NewMint"),
					condInfo.getInt("Excellent"), condInfo.getInt("VeryGood"),
					condInfo.getInt("Good"), condInfo.getInt("Poor")};
		}
		db.closeConnection();
		return conditionList;
	}

	public String updateCondition(int cardID, int[] conditions) {
		int nearMint = conditions[0];
		int excellent = conditions[1];
		int veryGood = conditions[2];
		int good = conditions[3];
		int poor = conditions[4];
		return ("UPDATE Condition Set NewMint = " + nearMint + ", Excellent = " + excellent +", " +
				"VeryGood = " + veryGood + ", Good = " + good + ", Poor = " + poor + " WHERE CardId = " + cardID);
	}
	
	public String deleteCondition(int cardID) {
		return ("DELETE FROM Condition WHERE CardId = " + cardID);
	}
}