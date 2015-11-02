package Creation;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.Card;
import application.Database;
import application.Parser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import GUI.CardRow;
import GUI.TableRow;
import GUI.TableType;

public class SearchResults {
	
	private ObservableList<TableRow> entries = FXCollections.observableArrayList();
	private String query;
	private Database db;
	
	public SearchResults(String query, TableType type, Database db) throws ClassNotFoundException{
		this.query = query;
		this.db = db;
		setResults();
	}
	
	public ObservableList<TableRow> getResults(){
		return entries;
	}
	
	private void setResults() throws ClassNotFoundException{
		Parser parser = new Parser();
		Sets sets = new Sets();
		Cards cards=  new Cards();
		Rarity rarities = new Rarity();
		Conditions conditions = new Conditions();
		String name, setName, rarity, foil = "";
		String nm, exc, vg, gd, p = "";
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
						nm = condInfo.getString("NewMint");
						exc = condInfo.getString("Excellent");
						vg = condInfo.getString("VeryGood");
						gd = condInfo.getString("Good");
						p = condInfo.getString("Poor");
						
						String[] cardInfo = new String[]{name, setName, rarity, foil,
								nm, exc, vg, gd, p};
						Card newCard = new Card(parser.getCardString(cardInfo), parser);
						entries.add(new CardRow(newCard));
					}
				}
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
