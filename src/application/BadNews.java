package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class BadNews {
	
	public BadNews(String message){
		displayBadNews(message);
	}
	
	public void displayBadNews(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(message);
		alert.show();
	}

}
