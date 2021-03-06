package GUI;

import java.sql.SQLException;
import java.util.ArrayList;

import application.Controller;
import Database.Database;
import Database.SearchResults;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class Table {
	private TableView<DataRow> table;
	private TableType type;
	private ObservableList<DataRow> entries = FXCollections.observableArrayList();
	
	public Table(TableType type, TableView<DataRow> table,
			ArrayList<TableColumn<DataRow,String>> columns){
		this.table = table;
		this.type = type;
		createTable(columns);
	}
	
	public TableView<DataRow> getTable(){
		return table;
	}
	
	public void createTable(ArrayList<TableColumn<DataRow, String>> columns){
		switch(type){
		case CARD_SEARCH: 
			setUpCardSearch(columns);
			break;
		case SET_LIST:
			setUpSetCardList(columns);
			break;
		case SET_SEARCH:
			setUpSetSearch(columns);
			break;
		}
	}
	
	private void setUpSetSearch(ArrayList<TableColumn<DataRow, String>> columns){
		setColumn(columns.get(0), ColumnType.SET_NAME);
	}
	
	private void setUpSetCardList(ArrayList<TableColumn<DataRow, String>> columns){
		setColumn(columns.get(0), ColumnType.DISPLAY_NAME);
		setColumn(columns.get(1), ColumnType.RARITY);
		setColumn(columns.get(2), ColumnType.TOTAL);
	}
	
	private void setUpCardSearch(ArrayList<TableColumn<DataRow, String>> columns){
		setColumn(columns.get(0), ColumnType.DISPLAY_NAME);
		setColumn(columns.get(1), ColumnType.SET_NAME);
		setColumn(columns.get(2), ColumnType.RARITY);
		setColumn(columns.get(3), ColumnType.TOTAL);
	}
	

	public void add(DataRow newEntry){
		entries.add(newEntry);
	}
	
	private void setColumn(TableColumn<DataRow, String> column, ColumnType columnType){
		column.setCellValueFactory(columnType.factory());
	}
	
	public void handleEvents(Controller controller){
		table.setRowFactory(k -> {
		    TableRow<DataRow> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
		        	setOnEvent(row, controller);
		        }
		    });		    
		    return row ;
		});
	}
	
	
	public void delete(Database db, Table otherTable){
		CardRow row = (CardRow) table.getSelectionModel().getSelectedItem();
		try {
			row.delete(db);
			otherTable.entries.remove(row);
			entries.remove(row);
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			Alert badNum = new Alert(AlertType.ERROR);
			badNum.setContentText("Failed to delete entry.");
			badNum.show();
		} 
	}
	
	private void setOnEvent(TableRow<DataRow> row, Controller controller){
		switch(type){
		case CARD_SEARCH: case SET_LIST:
			CardRow cardRow = (CardRow) row.getItem();
			controller.swapToView(cardRow);
			break;
		case SET_SEARCH:
			DataRow setRow = row.getItem();
			controller.swapToList(setRow);
			break;
		
		}
	}
	
	public void displayResultsFor(String query, Database db) throws ClassNotFoundException, SQLException{
		SearchResults results = new SearchResults(query, type, db);
		entries = results.getResults();
		table.setItems(entries);
	}
	
	

}
