package GUI;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Table {
	private TableView<TableRow> table;
	private ObservableList<TableRow> entries = FXCollections.observableArrayList();
	
	public Table(TableType type, TableView<TableRow> table, ArrayList<TableColumn<TableRow,String>> columns){
		this.table = table;
		createTable(type, columns);
		
	}
	
	public TableView<TableRow> getTable(){
		return table;
	}
	
	public void createTable(TableType type, ArrayList<TableColumn<TableRow, String>> columns){
		if (type == TableType.CARD_SEARCH){
			columns.get(0).setCellValueFactory(
					new PropertyValueFactory<TableRow, String>("name"));
			columns.get(1).setCellValueFactory(
					new PropertyValueFactory<TableRow, String>("setName"));
			columns.get(2).setCellValueFactory(
					new PropertyValueFactory<TableRow, String>("rarity"));
			columns.get(3).setCellValueFactory(
					new PropertyValueFactory<TableRow, String>("total"));
		}
		table.setItems(entries);
	}
	
	public void add(TableRow newEntry){
		entries.add(newEntry);
	}
	
	

}
