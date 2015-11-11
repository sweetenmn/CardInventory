package GUI;

import javafx.scene.control.cell.PropertyValueFactory;

public enum ColumnType {
	DISPLAY_NAME(new PropertyValueFactory<DataRow, String>("displayName")),
	SET_NAME(new PropertyValueFactory<DataRow, String>("setName")),
	RARITY(new PropertyValueFactory<DataRow, String>("rarity")),
	TOTAL(new PropertyValueFactory<DataRow, String>("total"));
	
	private PropertyValueFactory<DataRow, String> factory;
	
	private ColumnType(PropertyValueFactory<DataRow, String> factory){
		this.factory = factory;
	}
	
	public PropertyValueFactory<DataRow, String> factory(){
		return factory;
	}
	
}
