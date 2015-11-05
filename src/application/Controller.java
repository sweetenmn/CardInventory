package application;

import java.util.ArrayList;

import Creation.Card;
import Creation.CardDB;
import Creation.ConditionDB;
import Creation.RarityDB;
import Creation.SetDB;
import GUI.CardRow;
import GUI.Table;
import GUI.DataRow;
import GUI.TableType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

public class Controller {
	
	@FXML
	BorderPane canvas;
	
	@FXML
	Button addImage;
	@FXML
	Button editTitle;
	@FXML
	Button addToSet;
	@FXML
	Button searchButton;
	@FXML
	TextField searchBar;
	@FXML
	TextField viewName;
	@FXML
	TextField viewSet; 
	@FXML
	TextField viewE; 
	@FXML
	TextField viewNM;
	@FXML
	TextField viewPoor;
	@FXML
	TextField viewGood; 
	@FXML
	TextField viewVG;
	@FXML
	TextField editName;
	@FXML
	TextField editSet;
	@FXML
	TextField editNM; 
	@FXML
	TextField editE; 
	@FXML
	TextField editVG; 
	@FXML
	TextField editG;
	@FXML
	TextField editP;
	@FXML
	Label searchTerm;
	@FXML
	Label setTitle;
	@FXML
	TableView<CardRow> setTableView;
	@FXML
	TableView<DataRow> searchCardTableView;
	@FXML
	TableView<DataRow> searchSetTableView;
	@FXML
	Tab searchTab;
	@FXML
	Tab setTab; 
	@FXML
	Tab viewTab;
	@FXML
	Tab editTab;
	@FXML
	CheckBox searchSets;
	@FXML
	CheckBox mythicRare;
	@FXML
	CheckBox rare;
	@FXML
	CheckBox uncommon;
	@FXML
	CheckBox common;
	@FXML
	CheckBox foilBox;
	ArrayList<CheckBox> boxes = new ArrayList<CheckBox>();
	ArrayList<Node> nodes = new ArrayList<Node>();

	Database database;
	CardDB cards = new CardDB();
	SetDB sets = new SetDB();
	RarityDB rarities = new RarityDB();
	ConditionDB conditionsDB = new ConditionDB();
	Table searchCardTable;
	Table searchSetTable;
	String newRarity = "";
	
	@FXML
	TableColumn<DataRow, String> searchCardName;
	@FXML
	TableColumn<DataRow, String> searchCardSet;
	@FXML
	TableColumn<DataRow, String> searchCardRarity;
	@FXML
	TableColumn<DataRow, String> searchCardTotal;
	@FXML
	TableColumn<DataRow, String> searchSet;
	
	public void initialize(){
		canvas.setOnKeyPressed(key -> handlePress(key.getCode()));
		createDatabase();
		createTables();
		addBoxes();
	}
	
	public void createDatabase(){
		try{
			database = new Database("CardInventory");
		} catch(Exception e){
			System.out.println("Database not found.");
		}
	}
	
	public void createTables(){
		ArrayList<TableColumn<DataRow, String>> search = addColumns();
		searchCardTable = new Table(TableType.CARD_SEARCH, searchCardTableView, search);
	}
	
	private ArrayList<TableColumn<DataRow, String>> addColumns(){
		ArrayList<TableColumn<DataRow, String>> search = new ArrayList<TableColumn<DataRow,String>>();
		search.add(searchCardName);
		search.add(searchCardSet);
		search.add(searchCardRarity);
		search.add(searchCardTotal);
		return search;
	}
	
	private void addBoxes(){
		boxes.add(mythicRare);
		boxes.add(rare);
		boxes.add(uncommon);
		boxes.add(common);
		for (CheckBox c: boxes){
			c.setOnAction(k -> {
			newRarity = c.getText();
			uncheckOther();
			});
		}
		
	}
	
	void handlePress(KeyCode code){
		if (code == KeyCode.ENTER){
			search();
		}
	}
	
	//So many little functions
	//How can we generalize but still set up event handlers?
	@FXML
	void incExcellent(){editE.setText(inc(editE.getText()));}
	@FXML
	void incNearMint(){editNM.setText(inc(editNM.getText()));}	
	@FXML
	void incVeryGood(){editVG.setText(inc(editVG.getText()));}
	@FXML
	void incGood(){editG.setText(inc(editG.getText()));}
	@FXML
	void incPoor(){editP.setText(inc(editP.getText()));}
	@FXML
	void decExcellent(){editE.setText(dec(editE.getText()));}
	@FXML
	void decNearMint(){editNM.setText(dec(editNM.getText()));}
	@FXML
	void decVeryGood(){editVG.setText(dec(editVG.getText()));}
	@FXML
	void decGood(){editG.setText(dec(editG.getText()));}
	@FXML
	void decPoor(){editP.setText(dec(editP.getText()));}
	
	private String inc(String value){
		if (value.equals("")){
			value = "0";
		}
		return String.valueOf(Integer.valueOf(value) + 1);
	}
	private String dec(String value){
		if (!value.equals("0") && !value.equals("")){
			return String.valueOf(Integer.valueOf(value) - 1);			
		}
		return value;
	}
	@FXML
	void saveCard(){
		String newName = getFrom(editName);
		String newSet = getFrom(editSet);
		int[] conditions = new int[]{getInt(editNM), getInt(editE),
				getInt(editVG), getInt(editG), getInt(editP)};                   
		Card newCard = new Card(newName, newSet, newRarity, isFoil(), conditions);
		newCard.sendToDatabase(database);
		
	}
	
	private String isFoil(){
		if (foilBox.isSelected()){ 
			return "Yes";
		} else {
			return "No";
		}
	}
	
	private String getFrom(TextField field){
		if (field.getText().equals("")){
			//remove final &&
			if (field != editName && field != editSet && field != searchBar){
				return "0";
			} else {
				//generate exception
				return field.getText();
			}
		}
		return field.getText();
	}
	
	private int getInt(TextField field){
		return Integer.valueOf(getFrom(field));
	}
	
	public void updateView(Card card){
		searchCardTable.add(new CardRow(card));		
	}
	
	public void search(){
		if (searchSets.isSelected()){
			searchSets();
		} else {
			searchCards();
		}
	}
	
	public void searchSets(){
		
	}
	
	public void searchCards(){
		try{	
			searchCardTable.displayResultsFor(getFrom(searchBar), database);
			displayQuery(getFrom(searchBar));
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}		
	}
	
	public void displayQuery(String query){
		if (query.equals("")){
			searchTerm.setText("Search for all entries:");
		} else {
			searchTerm.setText("Search results for: '" + searchBar.getText() + "'");
		}
	}
	
	private void uncheckOther(){
		for (CheckBox c: boxes){
			if (!c.getText().equals(newRarity)){
				c.setSelected(false);
			} 
		}
		
	}
	
	
	
	
	
	
}
