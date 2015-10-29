package application;

import java.sql.*;

public class Database {

	private int cardCount;
	private int setCount;

	public Database() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("here");
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;

		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:CardInventory.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);

			if (rs.getFetchSize() == 0) {
				statement.executeUpdate("create table Set(SetId string, setName string)");
				statement.executeUpdate("create table Card (CardId string, cardName string, setId string)");
				statement.executeUpdate("create table Rarity (CardId string, Rarity string, Foil string)");
				statement.executeUpdate("create table Condition (CardId string, NewMint integer, Excellent integer, " +
						"VeryGood integer, Good integer, Poor integer)");
			}
		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
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

	public String addCard(String CardName, String SetName) {
		cardCount += 1;
		return ("insert into Card values(");
	}

	public String getSet(String SetName) {
		return("");
	}

	public String getCard(String CardName) {
		return("");
	}
}

