package application;

import java.util.ArrayList;

import Creation.Cards;
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

public class Controller {
	
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
	Database database;
	Parser parser;
	Cards cards = new Cards();
	Sets sets = new Sets();
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
	int count;
	
	
	public void initialize(){
		parser = new Parser();
		count = 0;
		ArrayList<TableColumn<TableRow, String>> search = new ArrayList<TableColumn<TableRow,String>>();
		search.add(searchCardName);
		search.add(searchCardSet);
		search.add(searchCardRarity);
		search.add(searchCardTotal);
		searchCardTable = new Table(TableType.CARD_SEARCH, searchCardTableView, search);
		
		try{
			database = new Database("CardInventory");
		} catch(Exception e){
			System.out.println("Database not found.");
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
		count += 1;
		//need to get info out of check boxes somehow
		String[] cardInfo = new String[]{getFrom(editName), getFrom(editSet), "Common", "No",
				getFrom(editNM), getFrom(editE), getFrom(editVG), getFrom(editG),
				getFrom(editP)};
	//	Card newCard = new Card(parser.getCardString(cardInfo), parser);
		database.updateDB(cards.addCard(getFrom(editName), getFrom(editSet)));
		System.out.println("NAME: " + getFrom(editName));
		database.updateDB(sets.addSet(getFrom(editSet)));
	//	updateView(newCard);
	}
	
	private String getFrom(TextField field){
		if (field != editName && field != editSet){
			if (field.getText().equals("")){
				return "0";
			}
		}
		return field.getText();
	}
	
	public void updateView(Card card){
		searchCardTable.add(new CardRow(card));		
	}
	
	public void search(){
		System.out.println(searchBar.getText());
		try{
			
			searchCardTable.displayResultsFor(searchBar.getText(), database);
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	@FXML
	void uncheckOther(){
		//for check boxes, to ensure only one is clicked at a time
		//going to have to declare them in controller to do the event handler
	}
	
	
	
	
	
}