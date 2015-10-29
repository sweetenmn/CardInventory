package application;

import java.util.ArrayList;

public class Parser {
	
	public Parser(){
		
	}
	
	public String getCardString(ArrayList<String> cardInfo){
		String cardString = "";
		for (String s: cardInfo){
			cardString += (s + "&&");
		}
		return cardString;
	}
	
	public String[] getCardInfo(String cardString){
		return cardString.split("&&");
	}

}
