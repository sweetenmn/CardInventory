package application;

import java.util.ArrayList;

import Creation.Card;
import Database.CardDB;
import Database.ConditionDB;
import Database.Database;
import Database.RarityDB;
import Database.SetDB;
import GUI.CardRow;
import GUI.Table;
import GUI.DataRow;
import GUI.TableType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

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
	Text viewName;
	@FXML
	Text viewSet; 
	@FXML
	Text viewRarity;
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
	Text addedCard;
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
	Label searchTerm;
	@FXML
	Label setTitle;

	@FXML
	TableView<DataRow> setTableView;
	@FXML
	TableView<DataRow> searchCardTableView;
	@FXML
	TableView<DataRow> searchSetTableView;
	@FXML
	TabPane tabs;
	@FXML
	CheckBox editFoil;
	ArrayList<CheckBox> boxes = new ArrayList<CheckBox>();
	SingleSelectionModel<Tab> selectionModel;

	Database database;
	CardDB cards = new CardDB();
	SetDB sets = new SetDB();
	RarityDB rarities = new RarityDB();
	ConditionDB conditionsDB = new ConditionDB();
	Table searchCardTable;
	Table searchSetTable;
	Table setListTable;
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
	@FXML
	TableColumn<DataRow, String> setListCard;
	@FXML
	TableColumn<DataRow, String> setListRarity;
	@FXML
	TableColumn<DataRow, String> setListTotal;
	
	
	public void initialize(){
		canvas.setOnKeyPressed(key -> handlePress(key.getCode()));
		selectionModel = tabs.getSelectionModel();
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
		ArrayList<TableColumn<DataRow, String>> searchCard = addCardSearchColumns();
		ArrayList<TableColumn<DataRow, String>> searchSet = addSetSearchColumns();
		ArrayList<TableColumn<DataRow, String>> setList = addSetListColumns();
		searchCardTable = new Table(TableType.CARD_SEARCH, searchCardTableView, searchCard);
		searchSetTable = new Table(TableType.SET_SEARCH, searchSetTableView, searchSet);
		setListTable = new Table(TableType.SET_LIST, setTableView, setList);
		searchCardTable.handleEvents(this);
		searchSetTable.handleEvents(this);
		setListTable.handleEvents(this);
	}
	
	private ArrayList<TableColumn<DataRow, String>> addCardSearchColumns(){
		ArrayList<TableColumn<DataRow, String>> search = new ArrayList<TableColumn<DataRow,String>>();
		search.add(searchCardName);
		search.add(searchCardSet);
		search.add(searchCardRarity);
		search.add(searchCardTotal);
		return search;
	}
	
	private ArrayList<TableColumn<DataRow, String>> addSetSearchColumns(){
		ArrayList<TableColumn<DataRow, String>> search = new ArrayList<TableColumn<DataRow,String>>();
		search.add(searchSet);
		return search;
	}
	
	private ArrayList<TableColumn<DataRow, String>> addSetListColumns(){
		ArrayList<TableColumn<DataRow, String>> search = new ArrayList<TableColumn<DataRow,String>>();
		search.add(setListCard);
		search.add(setListRarity);
		search.add(setListTotal);
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
				uncheckOtherBoxes();
			});
		}
	}
	
	void handlePress(KeyCode code){
		if (code == KeyCode.ENTER){ search(); }
	}
	
	//So many little functions
	//How can we generalize but still set up event handlers?
	@FXML
	void incExcellent(){editE.setText(inc(editE));}
	@FXML
	void incNearMint(){editNM.setText(inc(editNM));}	
	@FXML
	void incVeryGood(){editVG.setText(inc(editVG));}
	@FXML
	void incGood(){editG.setText(inc(editG));}
	@FXML
	void incPoor(){editP.setText(inc(editP));}
	@FXML
	void decExcellent(){editE.setText(dec(editE));}
	@FXML
	void decNearMint(){editNM.setText(dec(editNM));}
	@FXML
	void decVeryGood(){editVG.setText(dec(editVG));}
	@FXML
	void decGood(){editG.setText(dec(editG));}
	@FXML
	void decPoor(){editP.setText(dec(editP));}
	
	private String inc(TextField numberField){
		String value = numberField.getText();
		if (value.equals("")){
			value = "0";
		}
		return String.valueOf(Integer.valueOf(value) + 1);
	}
	private String dec(TextField numberField){
		String value = numberField.getText();
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
		try {
			newCard.sendToDatabase(database);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addedCard.setText("Added '" + newName + "'" );
		addedCard.setVisible(true);
		
	}
	
	private String isFoil(){
		if (editFoil.isSelected()){ 
			return "Yes";
		} else {
			return "No";
		}
	}
	
	private String getFrom(TextField field){
		if (field.getText().equals(null)){
			field.setText("");
		}
		if (field.getText().equals("")){
			//remove final &&
			if (field != editName && field != editSet && field != searchBar){
				return "0";
			} else {
				//generate exception
				return field.getText();
			}
		} else {
			
			return field.getText();
		}
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
		selectionModel.select(0);
		displayQuery(getFrom(searchBar));
	}
	
	public void searchSets(){
		try {
			searchSetTable.displayResultsFor(getFrom(searchBar), database);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void searchCards(){
		try{
			searchCardTable.displayResultsFor(getFrom(searchBar), database);
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}		
	}
	
	public void viewSetSearch(){
		searchCardTableView.setVisible(false);
		searchSetTableView.setVisible(true);
		
	}
	
	public void viewCardSearch(){
		searchSetTableView.setVisible(false);
		searchCardTableView.setVisible(true);
		
	}
	
	public void displayQuery(String query){
		if (query.equals("")){
			searchTerm.setText("Search for all entries:");
		} else {
			searchTerm.setText("Search results for: '" + searchBar.getText() + "'");
		}
	}
	
	private void uncheckOtherBoxes(){
		for (CheckBox c: boxes){
			if (!c.getText().equals(newRarity)){
				c.setSelected(false);
			} 
		}
		
	}
	
	public void swapToView(CardRow data){
		Card card = data.getCard();
		updateViewFields(card);
		canvas.requestFocus();
		selectionModel.select(2);
	
	}
	
	public void updateViewFields(Card card){
		if (card.isFoil()){
			viewName.setText("Foil " + card.getName());
		} else {
			viewName.setText(card.getName());
		}
		viewSet.setText(card.getSet());
		viewNM.setText(toString(card.getNMCount()));
		viewE.setText(toString(card.getEXCCount()));
		viewVG.setText(toString(card.getVGCount()));
		viewGood.setText(toString(card.getGCount()));
		viewPoor.setText(toString(card.getPCount()));
		viewRarity.setText(card.getRarity());
		
	}
	
	
	public void swapToList(DataRow data){
		try {
			selectionModel.select(1);
			canvas.requestFocus();
			setListTable.displayResultsFor(data.getSetName(), database);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String toString(int num){
		return String.valueOf(num);
	}
	
	public void swapSearchPrompt(){
		if (searchBar.getPromptText().endsWith("card")){
			searchBar.setPromptText("Search by set");
			viewSetSearch();
		} else {
			searchBar.setPromptText("Search by card");
			viewCardSearch();
		}
		highlightSearchText();
	}
	
	private void highlightSearchText(){
		searchBar.requestFocus();
		searchBar.selectAll();
	}
	
	@FXML
	public void newCard(){
		clearEditFields();
		uncheckEditBoxes();
	}
	
	private void clearEditFields(){
		editName.clear();
		editSet.clear();
		editNM.clear();
		editE.clear();
		editVG.clear();
		editG.clear();
		editP.clear();
		addedCard.setVisible(false);
	}
	
	private void uncheckEditBoxes(){
		for (CheckBox c: boxes){
			c.setSelected(false);
		}
		editFoil.setSelected(false);
		addedCard.setVisible(false);
		
	}
	
	
	
}
