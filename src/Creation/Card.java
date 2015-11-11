package Creation;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.CardDB;
import Database.ConditionDB;
import Database.Database;
import Database.RarityDB;
import Database.SetDB;

public class Card {
	String name, set, rarity, foil;
	int nm, exc, vg, g, p;
	int[] conditions;
	CardDB cards = new CardDB();
	ConditionDB conditionDB = new ConditionDB();
	SetDB setDB = new SetDB();
	RarityDB rarityDB = new RarityDB();
	
	public Card(String name, String set, String rarity, String foil, int[] conditions){
		this.name = name;
		this.set = set;
		this.rarity = rarity;
		this.foil = foil;
		this.conditions = conditions;
		setConditions();
	}
	
	public void sendToDatabase(Database database) throws ClassNotFoundException{
		database.updateDB(setDB.addSet(set));
		database.updateDB(cards.addCard(name, set, database));
		int cardID = getCardID(database);
		database.updateDB(rarityDB.setRarity(cardID, rarity, foil));
		database.updateDB(conditionDB.setConditions(cardID, conditions));
	}

	public void updateCardDatabase(Database database) throws ClassNotFoundException {
		int setID = getSetID(database);
		database.updateDB(setDB.updateSet(set, setID));
		database.updateDB(cards.updateCard(name, set, setID, database));
		int cardID = getCardID(database);
		database.updateDB(rarityDB.updateRarity(cardID, rarity, foil));
		database.updateDB(conditionDB.updateCondition(cardID, conditions));
		database.closeConnection();
	}
	
	private void setConditions(){
		nm = conditions[0];
		exc = conditions[1];
		vg = conditions[2];
		g = conditions[3];
		p = conditions[4];
		
	}
	
	private int getCardID(Database database){
		ResultSet rs;
		int setID = 0;
		try {
			rs = database.getResults(cards.getCardID(name, set, database));
			setID = rs.getInt("CardId");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		database.closeConnection();
		database.closeConnection();
		return setID;
	}

	private int getSetID(Database database){
		ResultSet rs;
		int setID = 0;
		try {
			rs = database.getResults(setDB.getSetID(set));
			if (!rs.isClosed()){

				if (rs.next()){
					setID = rs.getInt("SetId");
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		database.closeConnection();
		database.closeConnection();
		return setID;
	}
	
	
	public String getName(){return name;}
	public String getSet(){return set;}
	public String getRarity(){return rarity;}
	public String getFoil(){return foil;}
	public boolean isFoil(){return foil.equals("Yes");}
	
	public int getNMCount(){return nm;}
	public int getEXCCount(){return exc;}
	public int getVGCount(){return vg;}
	public int getGCount(){return g;}
	public int getPCount(){return p;}
	
	public String getTotal(){
		return String.valueOf(nm + exc + vg + g + p);
	}
	
	public int[] getConditions(){
		return conditions;
	}
	

}