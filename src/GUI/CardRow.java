package GUI;

import Creation.Card;
import javafx.beans.property.SimpleStringProperty;

public class CardRow extends DataRow {
	private final SimpleStringProperty name, rarity, total, foil;
	
	//public CardRow(String name, String setName, String rarity, String total, String foil){
	public CardRow(Card card){
		super(card.getSet());
		this.name = new SimpleStringProperty(card.getName());
		this.rarity = new SimpleStringProperty(card.getRarity());
		this.total = new SimpleStringProperty(card.getTotal());
		this.foil = new SimpleStringProperty(card.getFoil());
	}
	
	public void setName(String newName){
		name.set(newName);
	}
	
	public void setRarity(String newRarity){
		rarity.set(newRarity);
	}
	
	public void setSet(String newSet){
		setName.set(newSet);
	}
	
	public void setTotal(String newTotal){
		total.set(newTotal);
	}
	
	public void setFoil(String newFoil){
		foil.set(newFoil);
	}
	
	public String getName(){return name.get();}
	public String getRarity(){return rarity.get();}
	public String getSet(){return setName.get();}
	public String getTotal(){return total.get();}
	public String getFoil(){return foil.get();}

}
