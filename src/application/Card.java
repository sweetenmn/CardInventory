package application;

public class Card {
	String name, set, rarity, foil, nm, exc, vg, g, p;
	Parser parser;
	
	public Card(String cardInfo, Parser parser){
		this.parser = parser;
		assignValues(cardInfo);
	}
	
	public void assignValues(String info){
		//ugh
		String[] infoList = parser.getCardInfo(info);
		name = infoList[0];
		set = infoList[1];
		rarity = infoList[2];
		foil = infoList[3];
		nm = infoList[4];
		exc = infoList[5];
		vg = infoList[6];
		g = infoList[7];
		p = infoList[8];
	}
	
	public String getName(){return name;}
	public String getSet(){return set;}
	public String getRarity(){return rarity;}
	public String getFoil(){return foil;}
	public String getNMCount(){return nm;}
	public String getEXCCount(){return exc;}
	public String getVGCount(){return vg;}
	public String getGCount(){return g;}
	public String getPCount(){return p;}
	
	public String getTotal(){return (String.valueOf(toInt(nm) + 
			toInt(exc) + toInt(vg) + toInt(g) + toInt(p)));
	}
	
	public void changeName(String name){
		this.name = name;
	}
	
	public void changeSet(String set){
		this.set = set;
	}
	
	public void changeRarity(String rarity){
		this.rarity = rarity;
	}
	public void changeFoil(String foil){
		this.foil = foil;
	}
	
	private int toInt(String str){
		return Integer.valueOf(str);
	}

	

}