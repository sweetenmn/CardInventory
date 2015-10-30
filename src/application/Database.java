package application;

import java.sql.*;

public class Database {

	private int cardCount;
	private int setCount;
	Statement statement;
	Connection connection;

	public Database() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");

		connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:sqlite:CardInventory.db");
			statement = connection.createStatement();
			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);

			if (!rs.next()) {
				statement.executeUpdate("CREATE TABLE Sets (SetId string, setName string)");
				statement.executeUpdate("CREATE TABLE Cards (CardId string, cardName string, setId string)");
				statement.executeUpdate("CREATE TABLE Details (CardId string, Rarity string, Foil string)");
				statement.executeUpdate("CREATE TABLE Conditions (CardId string, NewMint integer, Excellent integer, " +
						"VeryGood integer, Good integer, Poor integer)");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}

	}

	public String addSet(String SetName) {
		//TODO: assign the set a unique ID
		//statement.executeUpdate("insert into Set values(
		setCount += 1;

		return("");

	}

	public void addCard(Card card) throws ClassNotFoundException {
		//TODO: this is only like 1/3 of this operation
		//gotta check for repeats
		//count does not work like this-- need to get the last count from the DB?
		Class.forName("org.sqlite.JDBC");

		connection = null;
		String CardID = "'C" + String.valueOf(cardCount + 1) + "'";
		cardCount += 1;	
		
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:CardInventory.db");
			statement = connection.createStatement();
			statement.execute("INSERT INTO Cards VALUES(" + CardID + ", '" 
			+ card.getName() + "', '" + card.getSet() + "')" );
		
			ResultSet rs = statement.executeQuery("SELECT * FROM Cards WHERE CardID = " + CardID);
			System.out.println("ID: " + rs.getString("CardId"));
			System.out.println("SET: " + rs.getString("setID"));
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
			
		
	} finally {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	}

	public String getSet(String SetName) {
		return("");
	}

	public String getCard(String CardName) {
		return("");
	}
}

