package GUI;

import java.util.ArrayList;

import application.Database;
import Creation.SearchResults;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Table {
	private TableView<DataRow> table;
	private TableType type;
	private ObservableList<DataRow> entries = FXCollections.observableArrayList();
	
	public Table(TableType type, TableView<DataRow> table, ArrayList<TableColumn<DataRow,String>> columns){
		this.table = table;
		this.type = type;
		createTable(columns);
		handleEvents();
		
		
	}
	
	public TableView<DataRow> getTable(){
		return table;
	}
	
	public void createTable(ArrayList<TableColumn<DataRow, String>> columns){
		if (type == TableType.CARD_SEARCH){
			columns.get(0).setCellValueFactory(
					new PropertyValueFactory<DataRow, String>("name"));
			columns.get(1).setCellValueFactory(
					new PropertyValueFactory<DataRow, String>("setName"));
			columns.get(2).setCellValueFactory(
					new PropertyValueFactory<DataRow, String>("rarity"));
			columns.get(3).setCellValueFactory(
					new PropertyValueFactory<DataRow, String>("total"));
		} else if (type == TableType.SET_SEARCH){
			columns.get(0).setCellValueFactory(
					new PropertyValueFactory<DataRow, String>("setName"));
		} else if (type == TableType.SET_LIST){
			columns.get(0).setCellValueFactory(
					new PropertyValueFactory<DataRow, String>("name"));
			columns.get(1).setCellValueFactory(
					new PropertyValueFactory<DataRow, String>("rarity"));
			columns.get(2).setCellValueFactory(
					new PropertyValueFactory<DataRow, String>("foil"));
			columns.get(3).setCellValueFactory(
					new PropertyValueFactory<DataRow, String>("total"));
			
		}
	}
	
	public void add(DataRow newEntry){
		entries.add(newEntry);
	}
	
	private void handleEvents(){
		table.setRowFactory(k -> {
		    TableRow<DataRow> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	//swap to view whatever
		        	//swapView()
		            String rowData = row.getItem().getSetName();
		            System.out.println(rowData);
		        }
		    });
		    return row ;
		});
	}
	
	private void swapView(){
		switch(type){
		case CARD_SEARCH: case SET_LIST:
			//view card
			//get the correct tab/class from the enum??
			break;
		case SET_SEARCH:
			//view set list
			break;
			
		}
	}
	
	public void displayResultsFor(String query, Database db) throws ClassNotFoundException{
		SearchResults results = new SearchResults(query, type, db);
		entries = results.getResults();
		table.setItems(entries);
	}
	
	

}
