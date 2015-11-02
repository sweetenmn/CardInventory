package Creation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public SearchResults(String query, TableType type, Database db){
		this.query = query;
		this.db = db;
		setResults();
	}
	
	public ObservableList<TableRow> getResults(){
		return entries;
	}
	
	private void setResults(){
		Parser parser = new Parser();
		Sets sets = new Sets();
		ResultSet results = db.getResults("SELECT CardName FROM CARD");
		try {
			while (results.next()){
				if (results.getString(1).contains(query)){
					String name = results.getString(1);
					int id = results.getInt("SetId");
					String setName = db.getResults(sets.getSetName(id)).getString("SetName");
					String[] cardInfo = new String[]{name, setName, "Common", "No",
							"1", "5", "0", "0", "0"};
					Card newCard = new Card(parser.getCardString(cardInfo), parser);
					entries.add(new CardRow(newCard));
					System.out.println(results.getString("CardName"));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}