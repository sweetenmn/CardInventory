package GUI;

import javafx.beans.property.SimpleStringProperty;

public class CardRow {
	private final SimpleStringProperty name, rarity, set, total, foil;
	
	public CardRow(String name, String rarity, String set, String total, String foil){
		this.name = new SimpleStringProperty(name);
		this.rarity = new SimpleStringProperty(rarity);
		this.set = new SimpleStringProperty(set);
		this.total = new SimpleStringProperty(total);
		this.foil = new SimpleStringProperty(foil);
	}
	
	public void setName(String newName){
		name.set(newName);
	}
	
	public void setRarity(String newRarity){
		rarity.set(newRarity);
	}
	
	public void setSet(String newSet){
		set.set(newSet);
	}
	
	public void setTotal(String newTotal){
		total.set(newTotal);
	}
	
	public void setFoil(String newFoil){
		foil.set(newFoil);
	}
	
	public String getName(){
		return name.get();
	}
	public String getRarity(){
		return rarity.get();
	}
	public String getSet(){
		return set.get();
	}
	public String getTotal(){
		return total.get();
	}
	public String getFoil(){
		return foil.get();
	}

}
