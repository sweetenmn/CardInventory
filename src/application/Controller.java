package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Creation.Cards;
import Creation.Conditions;
import Creation.Rarity;
import Creation.Sets;
import GUI.CardRow;
import GUI.Table;
import GUI.TableRow;
import GUI.TableType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

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
	TableView<TableRow> searchCardTableView;
	@FXML
	TableView<TableRow> searchSetTableView;
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
	Database database;
	Parser parser;
	Cards cards = new Cards();
	Sets sets = new Sets();
	Rarity rarities = new Rarity();
	Conditions conditions = new Conditions();
	Table searchCardTable;
	Table searchSetTable;
	@FXML
	TableColumn<TableRow, String> searchCardName;
	@FXML
	TableColumn<TableRow, String> searchCardSet;
	@FXML
	TableColumn<TableRow, String> searchCardRarity;
	@FXML
	TableColumn<TableRow, String> searchCardTotal;
	@FXML
	TableColumn<TableRow, String> searchSet;
	@FXML
	String newRarity = "";
	
	public void initialize(){
		parser = new Parser();
		canvas.setOnKeyPressed(k -> handlePress(k.getCode()));
		ArrayList<TableColumn<TableRow, String>> search = addColumns();
		addBoxes();
		searchCardTable = new Table(TableType.CARD_SEARCH, searchCardTableView, search);
		try{
			database = new Database("CardInventory");
		} catch(Exception e){
			System.out.println("Database not found.");
		}
		
	}
	
	private ArrayList<TableColumn<TableRow, String>> addColumns(){
		ArrayList<TableColumn<TableRow, String>> search = new ArrayList<TableColumn<TableRow,String>>();
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
		database.updateDB(cards.addCard(getFrom(editName), getFrom(editSet)));
		database.updateDB(sets.addSet(getFrom(editSet)));
		ResultSet set;
		int setID = 0;
		try {
			set = database.getResults(cards.getCardID(getFrom(editName), getFrom(editSet)));
			setID = set.getInt("CardId");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			database.closeConnection();
		}
		if (newRarity == ""){
			newRarity = "None";
		}
		database.updateDB(rarities.setRarity(setID,
				newRarity, isFoil()));
		database.updateDB(conditions.setConditions(setID, getInt(editNM),
				getInt(editE), getInt(editVG), getInt(editG), getInt(editP)));
		
	}
	
	private String isFoil(){
		if (foilBox.isSelected()){
			return "Yes";
		} else {
			return "No";
		}
	}
	
	private String getFrom(TextField field){
		if (field != editName && field != editSet){
			if (field.getText().equals("")){
				return "0";
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
		try{	
			searchCardTable.displayResultsFor(searchBar.getText(), database);
			searchTerm.setText("Search results for: '" + searchBar.getText() + "'");
		} catch (ClassNotFoundException e){
			e.printStackTrace();
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