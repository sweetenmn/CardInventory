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
/*

package helpers;

import javafx.scene.image.Image;

public enum CellState {
	EMPTY(new Image("emptyChip.png")),
	RED(new Image("redChip.png")), 
	BLACK(new Image("blackChip.png")), 
	RED_KING(new Image("redCrown.png")), 
	BLACK_KING(new Image("blackCrown.png"));
	
	private Image image;
	
	private CellState(Image image){this.image = image;}
	
	public Image getImage(){return image;}
}
*/