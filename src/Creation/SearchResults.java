package Creation;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
		setResults();
	}
	
	public ObservableList<DataRow> getResults(){
		return entries;
	}
	
	private void setResults() throws ClassNotFoundException{
		SetDB sets = new SetDB();
		CardDB cards=  new CardDB();
		RarityDB rarities = new RarityDB();
		ConditionDB conditions = new ConditionDB();
		String name, setName, rarity, foil = "";
		try {
			//Break up into sub functions--use join?
			ResultSet queryResults = db.getResults("SELECT * FROM CardTable");
			if (!queryResults.isClosed()){
				while (queryResults.next()){
					if (queryResults.getString("CardName").toLowerCase().contains(query.toLowerCase())){
						name = queryResults.getString("CardName");
						int id = queryResults.getInt("SetId");
						setName = db.getResults(sets.getSetName(id)).getString("SetName");
						ResultSet cardresult = db.getResults(cards.getCardID(name, setName));
						cardresult.next();
						int cardID = cardresult.getInt("CardID");
						ResultSet rarityInfo = db.getResults(rarities.getRarity(cardID));
						rarityInfo.next();
						rarity = rarityInfo.getString("Rarity");
						foil = rarityInfo.getString("Foil");
						ResultSet condInfo = db.getResults(conditions.getConditions(cardID));
						int[] conditionList = new int[]{condInfo.getInt("NewMint"),
								condInfo.getInt("Excellent"), condInfo.getInt("VeryGood"),
								condInfo.getInt("Good"), condInfo.getInt("Poor")};
						Card newCard = new Card(name, setName, rarity, foil, conditionList);
						entries.add(new CardRow(newCard));
					}
				}
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
