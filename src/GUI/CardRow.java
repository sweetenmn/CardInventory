package GUI;

import java.sql.SQLException;

import Creation.Card;
import Database.CardDB;
import Database.Database;
import javafx.beans.property.SimpleStringProperty;

public class CardRow extends DataRow {
	private final SimpleStringProperty displayName, name, rarity, total, foil;
	private Card card;
	
	public CardRow(Card card){
		super(card.getSet());
		this.card = card;
		this.name = new SimpleStringProperty(card.getName());
		this.rarity = new SimpleStringProperty(card.getRarity());
		this.total = new SimpleStringProperty(card.getTotal());
		this.foil = new SimpleStringProperty(card.getFoil());
		this.displayName = new SimpleStringProperty(getDisplayName());
	}
	
	public Card getCard(){
		return card;
	}
	
	public String getDisplayName(){
		if(card.isFoil()){
			return "Foil " + name.get();
		} else {
			return name.get();
		}
	}
	
	public void delete(Database db) throws NumberFormatException, ClassNotFoundException, SQLException{
		CardDB cardDB = new CardDB();
		db.updateDB(cardDB.deleteCard(cardDB.getCardIDInteger(getName(), getSet(), db)));
	}
	
	public String getName(){return name.get();}
	public String getRarity(){return rarity.get();}
	public String getSet(){return setName.get();}
	public String getTotal(){return total.get();}
	public String getFoil(){return foil.get();}

}
