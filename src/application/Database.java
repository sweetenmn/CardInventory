package application;

import java.sql.*;

public class Database {

	int cardCount;
	int setCount;

	public Database() throws ClassNotFoundException {
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
				statement.executeUpdate("CREATE TABLE SetTable (SetId TEXT, SetName TEXT)");
				statement.executeUpdate("CREATE TABLE Card (CardId TEXT, cardName TEXT, SetId TEXT)");
				statement.executeUpdate("CREATE TABLE Rarity (CardId TEXT, Rarity TEXT, Foil TEXT)");
				statement.executeUpdate("CREATE TABLE Condition (CardId TEXT, NewMint INTEGER, Excellent INTEGER, " +
						"VeryGood INTEGER, Good INTEGER, Poor INTEGER)");
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

	public void UpdateDb(String command){
		Connection connection = null;

		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:CardInventory.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			//updates the database with given command
			statement.executeUpdate(command);

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
		//statement.executeUpdate("insert into Set values()")
		setCount += 1;

		return("insert into Set values('S" + setCount + "', '" + SetName + "')");

	}

	public String getSetID(String SetName) {
		return ("SELECT SetId FROM SetTable WHERE SetName = '"+ SetName + "'");
	}

	public String getCardID(String CardName) {
		return ("SELECT CardId FROM Card WHERE CardName = '" + CardName + "'");
	}

	public String addCard(String CardName, String SetName) {
		cardCount += 1;

		return ("insert into Card values('C" + cardCount + "', '" + CardName + "', 'S" + getSetID(SetName) + "')");
	}

	public String getSet(String SetName) {

		return("");
	}

	public String getCard(String CardName) {
		return("SELECT CardName FROM Card ()");
	}
}

