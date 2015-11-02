package application;

public class Parser {
	
	
	public String getCardString(String[] cardInfo){
		String cardString = "";
		for (int i = 0; i < cardInfo.length; i++){
			cardString += (cardInfo[i] + "&&");
		}
		return cardString;
	}
	
	public String[] getCardInfo(String cardString){
		return cardString.split("&&");
	}

}