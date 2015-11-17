package GUI;

import javafx.beans.property.SimpleStringProperty;

public class DataRow {
	protected SimpleStringProperty setName;
	
	public DataRow(String setName){
		this.setName = new SimpleStringProperty(setName);
	}
	
	public void setSetName(String newName){
		setName.set(newName);
	}
	
	public String getSetName(){
		return setName.get();
	}
	
	

}
