package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
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
	Button incNM;
	@FXML
	Button decNM; 
	@FXML
	Button incE; 
	@FXML
	Button decE;
	@FXML
	Button incVG; 
	@FXML
	Button decVG; 
	@FXML
	Button incG;
	@FXML
	Button decG;
	@FXML
	Button incP; 
	@FXML
	Button decP;
	@FXML
	TextField searchBar;
	@FXML
	TextField viewName;
	@FXML
	TextField viewSet; 
	@FXML
	TextField viewExc; 
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
	TableView<String> setTable;
	@FXML
	TableView<String> searchTable;
	@FXML
	Tab searchTab;
	@FXML
	Tab setTab; 
	@FXML
	Tab viewTab;
	@FXML
	Tab editTab;
	
	public void initialize(){
		try{
			
			Database db = new Database();
		} catch( Exception e){
			System.out.println("DB not found");
		}
		
	}
}
