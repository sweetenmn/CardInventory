package application;

import javafx.fxml.FXML;

public class Controller {
	
	public void initialize(){
		try{
			
			Database db = new Database("CardInventory.db");
		} catch( Exception e){
			System.out.println("here");
		}
		
	}
	

}
