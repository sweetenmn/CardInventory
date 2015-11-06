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
	
	
	public SearchResults(String query, TableType type, Database db) throws ClassNotFoundException{
		this.query = query;
		this.db = db;
		setResults(type);
	}
	
	public ObservableList<DataRow> getResults(){
		return entries;
	}
	
	private void setResults(TableType type) throws ClassNotFoundException{
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
		default:
			break;
		
		}
	}
	
	private void setSetResults() throws ClassNotFoundException{
		try {
			ResultSet queryResults = db.getResults("SELECT * FROM SetTable");
			if (!queryResults.isClosed()){
				while (queryResults.next()){
					if (queryResults.getString("SetName").toLowerCase().contains(query.toLowerCase())){
					entries.add(new DataRow(queryResults.getString("SetName")));
					}
				}
				db.closeConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void setListResults(){
		
	}
	
	private void setCardSearchResults() throws ClassNotFoundException{
		try {
			ResultSet queryResults = db.getResults("SELECT * FROM CardTable");
			if (!queryResults.isClosed()){
				while (queryResults.next()){
					setSingleResult(queryResults);
				}
				db.closeConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void setSingleResult(ResultSet results) throws SQLException, ClassNotFoundException{
		if (results.getString("CardName").toLowerCase().contains(query.toLowerCase())){
			String name = getNameFrom(results);
			int setID = getSetIDFrom(results);
			String set = getSetName(setID);
			int cardID = getCardID(name, setID);
			String rarity = getRarity(cardID);
			String foil = getFoil(cardID);
			int[] conditions = getConditions(cardID);
			Card newCard = new Card(name, set, rarity, foil, conditions);
			entries.add(new CardRow(newCard));
			
		}
		
	}
	
	//Put these in respective rarities, cards, sets etc. classes!s - M
	private String getNameFrom(ResultSet results) throws SQLException{
		return results.getString("CardName");
	}
	
	private int getSetIDFrom(ResultSet results) throws SQLException{
		return results.getInt("SetId");
	}
	
	private String getSetName(int setID) throws ClassNotFoundException, SQLException{
		SetDB sets = new SetDB();
		String set = db.getResults(sets.getSetName(setID)).getString("SetName");
		db.closeConnection();
		return set;
	}
	
	private int getCardID(String name, int setID) 
			throws ClassNotFoundException, SQLException{
		CardDB cards = new CardDB();
		int cardID = db.getResults(cards.getCardID(name, setID)).getInt("CardID");
		db.closeConnection();
		return cardID;
	}
	
	private String getRarity(int cardID) throws ClassNotFoundException, SQLException{
		RarityDB rarities = new RarityDB();
		ResultSet rarityInfo = db.getResults(rarities.getRarity(cardID));
		String rarity = rarityInfo.getString("Rarity");
		db.closeConnection();
		return rarity;	
	}
	
	private String getFoil(int cardID) throws ClassNotFoundException, SQLException{
		RarityDB rarities = new RarityDB();
		ResultSet rarityInfo = db.getResults(rarities.getRarity(cardID));
		String foil = rarityInfo.getString("Foil");
		db.closeConnection();
		return foil;
	}
	
	private int[] getConditions(int cardID) throws ClassNotFoundException, SQLException{
		ConditionDB conditions = new ConditionDB();
		ResultSet condInfo = db.getResults(conditions.getConditions(cardID));
		int[] conditionList = new int[]{condInfo.getInt("NewMint"),
				condInfo.getInt("Excellent"), condInfo.getInt("VeryGood"),
				condInfo.getInt("Good"), condInfo.getInt("Poor")};
		db.closeConnection();
		return conditionList;
	}

}
