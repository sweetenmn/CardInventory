package application;

import java.io.IOException;
import java.util.ArrayList;

import Creation.Card;
import Creation.ImageOpener;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;


public class Controller {
	
	@FXML
	BorderPane canvas;
	@FXML
	Button editTitle;
    @FXML
    Button editCard;
    @FXML
    Button saveCard;
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
	ImageView Iview;

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
	ImageOpener IO = new ImageOpener();
	Image img;
	
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
			badNews("Unable to create or connect to database.");
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
    void editORsave() {
        if (saveCard.getText().contains("Edit")) {
            editCard();
        } else {
            saveCard();
        }
    }

	void saveCard(){
		Card newCard;
		try{
			String newName = getFrom(editName);
			String newSet = getFrom(editSet);
			int[] conditions = new int[]{getInt(editNM), getInt(editE),
					getInt(editVG), getInt(editG), getInt(editP)};         
			checkFields();
			newCard = new Card(newName, newSet, newRarity, isFoil(), conditions);
			try {
				newCard.sendToDatabase(database);
			} catch (ClassNotFoundException e) {
				badNews("Failed to add card to database.");
			}
			database.closeConnection();
			addedCard.setText("Added '" + newName + "'" );
			addedCard.setVisible(true);
		} catch (IllegalArgumentException c){
			badNews("Please enter the name and set of your card.");
		} catch (NullPointerException e){
			badNews("Please select a card rarity.");
		}
	}


    void editCard() {
        Card newCard;
        try {
            String cardName = getFrom(editName);
            String setName = getFrom(editSet);
            int[] conditions = new int[]{getInt(editNM), getInt(editE),
                    getInt(editVG), getInt(editG), getInt(editP)};
            checkFields();
            newCard = new Card(cardName, setName, newRarity, isFoil(), conditions);
            try {
                newCard.updateCardDatabase(database);
            } catch (ClassNotFoundException e) {
                badNews("Failed to update Card");
            }
            database.closeConnection();
            addedCard.setText("Updated '" + editName.getText() + "'");
            addedCard.setVisible(true);
        } catch (IllegalArgumentException c) {
            badNews("Please enter the name and set of your card.");
        } catch (NullPointerException e) {
            badNews("Please select a card rarity.");
        }
    }
	
	void badNews(String message) {
		Alert badNum = new Alert(AlertType.ERROR);
		badNum.setContentText(message);
		badNum.show();
	}
	
	private void checkFields(){
		boolean rarityChecked = false;
		for (CheckBox c: boxes){
			if (c.isSelected()){
				rarityChecked = true;
			}
		}
		if (rarityChecked == false){
			throw (new NullPointerException());
		}
	}
	
	private String isFoil(){
		if (editFoil.isSelected()){ 
			return "Yes";
		} else {
			return "No";
		}
	}
	
	private String getFrom(TextField field){
		if (field.getText().equals("")){
			if (field == editName || field == editSet){
				throw (new IllegalArgumentException());
			} else if (field != searchBar){
				return "0";
			} else {
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
			badNews("Unable to display results for " + getFrom(searchBar) + ".");
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
	
	public void updateViewFields(Card card) {
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
		try {
			img = IO.Open(card.getName());
			Iview.setImage(img);
		} catch(IOException ex) {
			badNews(ex.getMessage());
		}

		
	}
	
	
	public void swapToList(DataRow data){
		try {
			selectionModel.select(1);
			canvas.requestFocus();
			setListTable.displayResultsFor(data.getSetName(), database);
		} catch (ClassNotFoundException e) {
			badNews("Unable to show list for set " + data.getSetName() + ".");
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
		if (!searchBar.getText().isEmpty()){
			searchBar.requestFocus();
			searchBar.selectAll();
		}
	}
	
	@FXML
	public void newCard(){
		clearEditFields();
		uncheckEditBoxes();
	}
	
	private void clearEditFields(){
		for (TextField field: editFieldList()){
			field.clear();
		}
		addedCard.setVisible(false);
        saveCard.setText("Save Card");
	}
	
	private void uncheckEditBoxes(){
		for (CheckBox c: boxes){
			c.setSelected(false);
		}
		editFoil.setSelected(false);
		addedCard.setVisible(false);
	}
	
	private ArrayList<TextField> editFieldList(){
		ArrayList<TextField> fields = new ArrayList<TextField>();
		fields.add(editName);
		fields.add(editSet);
		fields.add(editNM);
		fields.add(editE);
		fields.add(editVG);
		fields.add(editG);
		fields.add(editP);
		return fields;
	}

    private void setCheckBox(String rarity) {
        if (rarity.contains("Mythic")) {
            mythicRare.setSelected(true);
        } else if (rarity.contentEquals("Rare")) {
            rare.setSelected(true);
        } else if (rarity.contentEquals("Uncommon")) {
            uncommon.setSelected(true);
        } else if (rarity.contentEquals("Common")) {
            common.setSelected(true);
        }
    }

    @FXML
	public void swapToEdit() {
        clearEditFields();
        uncheckEditBoxes();
        if (viewName.getText().contains("Foil")){
            editFoil.setSelected(true);
            editName.setText(viewName.getText().substring(5));
        }
        else {
            editName.setText(viewName.getText());
        }

        editSet.setText(viewSet.getText());
        editNM.setText(viewNM.getText());
        editE.setText(viewE.getText());
        editVG.setText(viewVG.getText());
        editG.setText(viewGood.getText());
        editP.setText(viewPoor.getText());

        setCheckBox(viewRarity.getText());
        newRarity = viewRarity.getText();
        System.out.println(viewRarity.getText());

        selectionModel.select(3);
        saveCard.setText("Update Card");

        //TODO: need to set the save button text to edit when swapping over
        //TODO: need to setup the edit function!!!!
	}
	
}
