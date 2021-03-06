package Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Creation.Card;
import GUI.CardRow;
import GUI.DataRow;
import GUI.TableType;

public class SearchResults {
	
	private ObservableList<DataRow> entries = FXCollections.observableArrayList();
	private String query;
	private Database db;
	
	
	public SearchResults(String query, TableType type, Database db) throws ClassNotFoundException, SQLException{
		this.query = query;
		this.db = db;
		setResults(type);
	}
	
	public ObservableList<DataRow> getResults(){
		return entries;
	}
	
	private void setResults(TableType type) throws ClassNotFoundException, SQLException{
		switch(type){
		case CARD_SEARCH:
			setCardSearchResults();
			break;
		case SET_LIST:
			setListResults();
			break;
		case SET_SEARCH:
			setSetResults();
			break;
		}
	}
	
	private void setSetResults() throws ClassNotFoundException, SQLException{
		ResultSet queryResults = db.getResults("SELECT * FROM SetTable");
			if (!queryResults.isClosed()){
			while (queryResults.next()){
				if (queryResults.getString("SetName").toLowerCase().contains(query.toLowerCase())){
				entries.add(new DataRow(queryResults.getString("SetName")));					
				}
			}
		}
		db.closeConnection();
		
		
	}
	
	private void setListResults() throws ClassNotFoundException, SQLException{
			ResultSet queryResults = db.getResults(
					"SELECT * FROM SetTable WHERE SetName = '" + query + "'");
		if (queryResults.next()){
			int setID = queryResults.getInt("SetId");
			String setName = queryResults.getString("SetName");
			db.closeConnection();
			setCardList(setID, setName);
		}			
		
		
	}
	
	private void setCardSearchResults() throws ClassNotFoundException, SQLException{
		ResultSet queryResults = db.getResults("SELECT * FROM CardTable");
		if (!queryResults.isClosed()){
			while (queryResults.next()){
				setSingleResult(queryResults);		
			}
		}
		db.closeConnection();

	}
	
	private void setSingleResult(ResultSet results) throws SQLException, ClassNotFoundException{
		RarityDB rarities = new RarityDB();
		SetDB sets = new SetDB();
		ConditionDB conditionDB = new ConditionDB();
		if (results.getString("CardName").toLowerCase().contains(query.toLowerCase())){
			String name = getNameFrom(results);
			int setID = getSetIDFrom(results);
			String set = sets.getSetName(setID, db);
			int cardID = results.getInt("CardId");
			String rarity = rarities.getFromRarities("Rarity", cardID, db);
			String foil = rarities.getFromRarities("Foil", cardID, db);
			int[] conditions = conditionDB.getConditions(cardID, db);
			Card newCard = new Card(name, set, rarity, foil, conditions);
			entries.add(new CardRow(newCard));
		}
		
	}
	
	private void setCardList(int setID, String setName) throws SQLException, ClassNotFoundException{
		RarityDB rarities = new RarityDB();
		ConditionDB conditionDB = new ConditionDB();
		ResultSet cardList = db.getResults("SELECT * FROM CardTable WHERE SetId = " + String.valueOf(setID));
		if(!cardList.isClosed()){
			while (cardList.next()){
				String name = getNameFrom(cardList);
				int cardID = cardList.getInt("CardId");
				String rarity = rarities.getFromRarities("Rarity", cardID, db);
				String foil = rarities.getFromRarities("Foil", cardID, db);
				int[] conditions = conditionDB.getConditions(cardID, db);
				Card newCard = new Card(name, setName, rarity, foil, conditions);
				entries.add(new CardRow(newCard));
			}	
		}
		db.closeConnection();
	}
	
	private String getNameFrom(ResultSet results) throws SQLException{
		return results.getString("CardName");
	}
	
	private int getSetIDFrom(ResultSet results) throws SQLException{
		return results.getInt("SetId");
	}

}
