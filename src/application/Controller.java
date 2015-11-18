package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Creation.Card;
import Creation.ImageOpener;
import Database.CardDB;
import Database.ConditionDB;
import Database.Database;
import Database.RarityDB;
import Database.SearchResults;
import Database.SetDB;
import GUI.CardRow;
import GUI.Table;
import GUI.DataRow;
import GUI.TableType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
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
	Button editCard, saveCard, addToSet, searchButton, newSetButton;
	@FXML
	Button editSetButton;
	@FXML
	Button saveSetButton;
	@FXML 
	Button cancelSetChanges;
	@FXML
	Text viewName, viewSet, viewRarity, addedCard;
	@FXML
	TextField searchBar, viewE, viewNM, viewPoor, viewGood, viewVG;
	@FXML
	TextField editName, editSet, editNM, editVG, editE, editG, editP;
	@FXML
	TextField editSetTitle;
	@FXML
	CheckBox searchSets, mythicRare, rare, uncommon, common;
	@FXML
	Label searchTerm, setTitle;
	@FXML
	ImageView Iview;
	@FXML
	ChoiceBox<String> setChoice;
	@FXML
	TableView<DataRow> setTableView, searchCardTableView, searchSetTableView;
	@FXML
	TabPane tabs;
	@FXML
	CheckBox editFoil;
	SingleSelectionModel<Tab> selectionModel;

	Database database;
	CardDB cards = new CardDB();
	SetDB sets = new SetDB();
	RarityDB rarities = new RarityDB();
	ConditionDB conditionsDB = new ConditionDB();
	Table searchCardTable, searchSetTable, setListTable;
	String newRarity = "";
	ImageOpener IO = new ImageOpener();
	Image img;
	Card oldCard, viewingCard;
	String viewingSet;
	boolean addToExistingSet = true;
	
	@FXML
	TableColumn<DataRow, String> searchCardName, searchCardSet, searchCardRarity, searchCardTotal;
	@FXML
	TableColumn<DataRow, String> searchSet;
	@FXML
	TableColumn<DataRow, String> setListCard, setListRarity, setListTotal;
	
	public void initialize(){
		canvas.setOnKeyPressed(key -> handleKeyPress(key.getCode()));
		selectionModel = tabs.getSelectionModel();
		createDatabase();
		createTables();
		setUpEditTab();
		
	}
	
	private void handleKeyPress(KeyCode code){
		if (code == KeyCode.ENTER){
			search();
		} else {
			handleDelete(code);
		}
	}
	
	private void handleDelete(KeyCode code){
		if (code == KeyCode.DELETE){
			if (selectionModel.getSelectedIndex() == 0 && !searchSets.isSelected()){
				searchCardTable.delete(database, setListTable);
			} else  if (selectionModel.getSelectedIndex() == 1){
				setListTable.delete(database,searchCardTable);
			}			
		}
	}
	
	private void createDatabase(){
		try{
			database = new Database("CardInventory");
		} catch(Exception e){
			badNews("Unable to create or connect to database.");
		}
	}

	private void createTables(){
		setUpCardSearchTable();
		setUpSetSearchTable();
		setUpSetListTable();
	}
	
	private void setUpCardSearchTable(){
		ArrayList<TableColumn<DataRow, String>> searchCard = addCardSearchColumns();
		searchCardTable = new Table(TableType.CARD_SEARCH, searchCardTableView, searchCard);
		searchCardTable.handleEvents(this);
	}
	
	private void setUpSetSearchTable(){
		ArrayList<TableColumn<DataRow, String>> searchSet = addSetSearchColumns();
		searchSetTable = new Table(TableType.SET_SEARCH, searchSetTableView, searchSet);
		searchSetTable.handleEvents(this);		
	}
	
	private void setUpSetListTable(){
		ArrayList<TableColumn<DataRow, String>> setList = addSetListColumns();
		setListTable = new Table(TableType.SET_LIST, setTableView, setList);
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
	
	private void setUpEditTab(){
		setChoice();
		enforceOneRarityChecked();
	}
	
	private void setChoice(){
		ObservableList<String> finalEntries = FXCollections.observableArrayList();
		getSetData().forEach(c -> {finalEntries.add(c.getSetName());});
		setChoice.setItems(finalEntries);
	}
	
	private ObservableList<DataRow> getSetData(){
		SearchResults results = null;
		try {
			results = new SearchResults("", TableType.SET_SEARCH, database);
		} catch (ClassNotFoundException e) {
			badNews("Unable to find existing sets.");
		}
		ObservableList<DataRow> entries = results.getResults();
		return entries;
	}

	private void enforceOneRarityChecked(){
		rarityList().forEach((c) -> {
            c.setOnAction(k -> {
				newRarity = c.getText();
				uncheckOtherBoxes();
            });
		});
	}

    @FXML
    void editORsave() {
    	int selectedSet = 0;
    	try {
    		Card newCard = createCardFromGUI();
	        if (saveCard.getText().contains("Update")) {
	            editCard(newCard);
	        } else {
	        	selectedSet = setChoice.getSelectionModel().getSelectedIndex();
	            saveCard(newCard);
	        }
	        setChoice();
	        setChoice.getSelectionModel().select(selectedSet);
    	} catch (IllegalArgumentException c) {
            badNews("Please enter the name and set of your card.");
        } catch (NullPointerException e) {
            badNews("Please select a card rarity.");
        }
        
    }
    
    private void editCard(Card card) throws IllegalArgumentException, NullPointerException {
    	try{
	        updateDatabase(card);
	        displaySuccessMessage("Updated");
    	} catch (IllegalStateException e){
    		badNews("That card already exists.");
    	}
    }    

	private void saveCard(Card card) throws NullPointerException{
		try{
			int selectedSet = setChoice.getSelectionModel().getSelectedIndex();
			sendToDatabase(card);
			displaySuccessMessage("Added");
			setChoice.getSelectionModel().select(selectedSet);
		} catch (IllegalStateException e){
			badNews("That card already exists.");
		}
	}
	
	
	
    private void displaySuccessMessage(String lead){
    	String isFoil = "";
    	if (editFoil.isSelected()){
    		isFoil = "Foil ";
    	}
    	addedCard.setText(lead + " '" + isFoil + editName.getText() + "'");
        addedCard.setVisible(true);
    }
	
	private String getSet(){
		if (addToExistingSet){
			if (setChoice.valueProperty().get() == null){
				throw (new IllegalArgumentException());
			} else {
				return setChoice.valueProperty().get();
			}
		} else {
			return getFrom(editSet);
		}
	}
	
	private void sendToDatabase(Card newCard) throws IllegalStateException {
		try {
			newCard.sendToDatabase(database);
		} catch (ClassNotFoundException | SQLException e) {
			badNews("Failed to add card to database.");
		} 
		database.closeConnection();
	}
	
	
	@FXML
	public void toggleAddNewSet(){
		if (!addToExistingSet){
			addingExistingSet();			
		} else {
			addingNewSet();			
		}
	}
	
	private void addingNewSet(){
		editSet.setVisible(true);
		newSetButton.setText("x");
		setChoice.setVisible(false);
		addToExistingSet = false;	
	}
	
	private void addingExistingSet(){
		editSet.clear();
		editSet.setVisible(false);
		newSetButton.setText("+");
		setChoice.setVisible(true);
		addToExistingSet = true;			
	}
	
	private int[] getConditions(){
		return new int[]{getInt(editNM), getInt(editE),
				getInt(editVG), getInt(editG), getInt(editP)};        
	}
    
    private Card createCardFromGUI(){
    	String newName = getFrom(editName);
		String newSet = getSet();
		int[] conditions = getConditions();        
		enforceRarityChecked();
		Card card = new Card(newName, newSet, newRarity, isFoil(), conditions);
		return card;
    }
    
    private void updateDatabase(Card card) throws IllegalStateException{
    	try {
            card.update(oldCard.copy(), database);
        } catch (ClassNotFoundException | SQLException e) {
            badNews("Failed to update Card");
		}
        database.closeConnection();
    }
    private void updateSet(){
    	int setID = 0;
    	try {
    		ResultSet rs = database.getResults("SELECT * FROM SetTable WHERE SetName = '" + viewingSet + "'");
    		if (!rs.isClosed()){
    			
    			if (rs.next()){
    				setID = rs.getInt("SetId");
    			}
    		}
		} catch (SQLException | ClassNotFoundException e) {
			badNews("Failed to edit set");
			e.printStackTrace();
		}
    	database.closeConnection();
    	SetDB setDB = new SetDB();
    	database.updateDB(setDB.updateSet(setTitle.getText(), setID));
    }
    
    
	void badNews(String message) {
		Alert badNum = new Alert(AlertType.ERROR);
		badNum.setContentText(message);
		badNum.show();
	}
	
	private void enforceRarityChecked(){
		boolean rarityChecked = false;
		for (CheckBox c: rarityList()){
			if (c.isSelected()) {
				 rarityChecked = true;
			}
		}
		if (!rarityChecked){throw (new NullPointerException());}
	}
	
	private String isFoil(){
		if (editFoil.isSelected()){
			return "true";
		} else {
			return "false";
		}
	}
	
	private String getFrom(TextField field){
		if (field.getText().equals("")){
			return handleEmptyField(field);
		} else {
			return field.getText();
		}
	}
	
	private String handleEmptyField(TextField field){
		if (field == editName || field == editSet){
			throw (new IllegalArgumentException());
		} else if (field != searchBar){
			return "0";
		} else {
			return field.getText();
		}
	}
	
	@FXML
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
			badSearch();
		}
	}
	
	public void searchCards(){
		try{
			searchCardTable.displayResultsFor(getFrom(searchBar), database);
		} catch (ClassNotFoundException e){
			badSearch();
		}		
	}
	
	
	public void displayQuery(String query){
		if (query.equals("")){
			searchTerm.setText("Search for all entries:");
		} else {
			searchTerm.setText("Search results for: '" + searchBar.getText() + "'");
		}
	}
	
	public void swapToView(CardRow data){
		viewingCard = data.getCard();
		updateViewFields(data);
		canvas.requestFocus();
		selectionModel.select(2);
	}
	
	public void updateViewFields(CardRow cardRow) {
		Card card = cardRow.getCard();
		populateViewFromCardRow(cardRow, card);
		displayImageFor(card);
	}
	
	public void populateViewFromCardRow(CardRow row, Card card){
		viewName.setText(row.getDisplayName());
		viewSet.setText(row.getSetName());
		viewRarity.setText(row.getRarity());
		setText(viewNM, toString(card.getNMCount()));
		setText(viewE, toString(card.getEXCCount()));
		setText(viewVG, toString(card.getVGCount()));
		setText(viewGood, toString(card.getGCount()));
		setText(viewPoor, toString(card.getPCount()));
	}
	
	private void displayImageFor(Card card){
		try {
			img = IO.open(card.getName());
			Iview.setImage(img);
		} catch(IOException ex) {
			badNews(ex.getMessage());
		}
	}
	
	public void swapToList(DataRow data){
		try {
			selectionModel.select(1);
			setTitle.setText(data.getSetName());
			
			setListTable.displayResultsFor(data.getSetName(), database);
		} catch (ClassNotFoundException e) {
			badNews("Unable to show list for set " + data.getSetName() + ".");
		}
	}
	
	@FXML
	public void swapSearchView(){
		toggleSearchTables();
		toggleSearchPrompt();
		highlightSearchText();
	}
	
	private void toggleSearchTables(){
		searchSetTableView.setVisible(!searchSetTableView.isVisible());
		searchCardTableView.setVisible(!searchSetTableView.isVisible());
	}
	
	private void toggleSearchPrompt(){
		if (searchBar.getPromptText().endsWith("card")){
			searchBar.setPromptText("Search by set");
		} else {
			searchBar.setPromptText("Search by card");
		}
	}
	
	@FXML
	public void newCard(){
		setChoice();
		addingExistingSet();
		clearEditFields();
		uncheckEditBoxes();
		newSetButton.setVisible(true);
		editSet.setEditable(true);
	}
	
    @FXML
    public void addToSet(){
    	clearEditFields();
    	uncheckEditBoxes();
    	addingNewSet();
    	setText(editSet, setTitle.getText());
    	selectionModel.select(3);    	
    }
    
    @FXML
    public void editSetTitle(){
    	editSetTitle.setText(setTitle.getText());
    	viewingSet = setTitle.getText();
    	toggleEditingSet();
    }
    @FXML
    public void saveSetTitle(){
    	try {
	    	toggleEditingSet();
	    	setTitle.setText(editSetTitle.getText());
	    	updateSet();
			setListTable.displayResultsFor(setTitle.getText(), database);
		} catch (ClassNotFoundException e) {
			badNews("Failed to update set name.");
		}
    	
    }
    @FXML
    public void cancelSetChanges(){
    	toggleEditingSet();
    }
    
    private void toggleEditingSet(){
    	editSetTitle.setVisible(!editSetTitle.isVisible());
    	setTitle.setVisible(!setTitle.isVisible());
    	addToSet.setVisible(!addToSet.isVisible());
    	editSetButton.setVisible(!editSetButton.isVisible());
    	cancelSetChanges.setVisible(!cancelSetChanges.isVisible());
    	saveSetButton.setVisible(!saveSetButton.isVisible());
    }
    
	
	private void clearEditFields(){
		editFieldList().forEach((field) -> {field.clear();});
		addedCard.setVisible(false);
		saveCard.setText("Save Card");
	}
	

    @FXML
	public void swapToEdit() {
        clearEditFields();
        uncheckEditBoxes();
        addingNewSet();
        populateEditFromView();
        oldCard = viewingCard;
        selectionModel.select(3);
        saveCard.setText("Update Card");
	}
    
    private void populateEditFromView(){
    	handleFoilDisplayName();
    	setText(editSet, viewSet.getText());
    	editSet.setEditable(false);
    	newSetButton.setVisible(false);
        setText(editNM, viewNM.getText());
        setText(editE, viewE.getText());
        setText(editVG, viewVG.getText());
        setText(editG, viewGood.getText());
        setText(editP, viewPoor.getText());
        checkCorrectRarity();
    }
    
	private void uncheckOtherBoxes(){
		rarityList().forEach((c) -> {
            if (!c.getText().equals(newRarity)) {
                c.setSelected(false);
            }
        });
	}

	private void uncheckEditBoxes(){
		rarityList().forEach((c) -> {c.setSelected(false);});
		editFoil.setSelected(false);
		addedCard.setVisible(false);
	}
    
    private void handleFoilDisplayName(){
    	if (viewName.getText().contains("Foil")){
            editFoil.setSelected(true);
            editName.setText(viewName.getText().substring(5));
        }
        else {
            editName.setText(viewName.getText());
        }
    }
    
    private void checkCorrectRarity(){
    	 rarityList().forEach((c) -> {
             if (c.getText().equals(viewRarity.getText())) {
                 c.setSelected(true);
             }
         });
    	 newRarity = viewRarity.getText();
    }
    
    private ArrayList<CheckBox> rarityList() {
        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        checkBoxes.add(mythicRare);
        checkBoxes.add(rare);
        checkBoxes.add(uncommon);
        checkBoxes.add(common);
        return checkBoxes;
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
	
	private void highlightSearchText(){
		if (!searchBar.getText().isEmpty()){
			searchBar.requestFocus();
			searchBar.selectAll();
		}
	}
	
	public void setText(TextField field, String target){
		field.setText(target);
	}
	
	private void badSearch(){
		badNews("Unable to display results for " + getFrom(searchBar) + ".");
	}
    
	private int getInt(TextField field){
		if (field.getText().equals("")){
			field.setText("0");
		}
		return Integer.valueOf(getFrom(field));
	}
	
	private String toString(int num){
		return String.valueOf(num);
	}
	
}
