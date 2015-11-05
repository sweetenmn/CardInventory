package GUI;

import javafx.beans.property.SimpleStringProperty;

public class TableRow {
	protected SimpleStringProperty setName;
	
	public TableRow(String setName){
		this.setName = new SimpleStringProperty(setName);
	}
	
	public void setSetName(String newName){
		setName.set(newName);
	}
	
	public String getSetName(){
		return setName.get();
	}

}
