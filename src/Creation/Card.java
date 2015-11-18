package Creation;

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
	
	public Card copy(){
		return new Card(name, set, rarity, foil, conditions);
	}
	
	public void sendToDatabase(Database database) throws ClassNotFoundException, SQLException{
		if (!cards.cardExists(name, set, foil, database)){
			database.updateDB(setDB.addSet(set));
			database.updateDB(cards.addCard(name, set, database));
			int cardID = cards.getLatestCardID(name, set, database);
			database.updateDB(rarityDB.setRarity(cardID, rarity, foil));
			database.updateDB(conditionDB.setConditions(cardID, conditions));
		} else {
			throw (new IllegalStateException());
		}
	}

	public void update(Card oldCard, Database database) throws ClassNotFoundException, SQLException {
		int cardID = cards.getCardIDInteger(oldCard.name, oldCard.set, database);
		database.updateDB(cards.updateCard(name, set, cardID, oldCard.name, database));
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
	
	public String getName(){return name;}
	public String getSet(){return set;}
	public String getRarity(){return rarity;}
	public String getFoil(){return foil;}
	public boolean isFoil(){return foil.equals("true");}
	
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