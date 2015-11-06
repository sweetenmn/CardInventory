package Database;

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

    public String getConditions(int cardID) {
        return("SELECT * FROM Condition WHERE CardId = " + cardID);
    }
}