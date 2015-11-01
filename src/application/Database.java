package application;

import sun.tools.java.ClassNotFound;

import java.sql.*;

public class Database {

	public Database(String dbName) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");

		int rsCount = 0;

		Connection connection = null;

		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:"+dbName+".db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);

			while(rs.next()) {
				rsCount += 1;
			}

			if (rsCount != 4) {
				statement.executeUpdate("CREATE TABLE SetTable (SetId TEXT, SetName TEXT)");
				statement.executeUpdate("CREATE TABLE Card (CardId TEXT, CardName TEXT, SetId TEXT)");
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

	public void UpdateDb(String command, String dbName) {
		Connection connection = null;

		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:"+ dbName +".db");
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

	public String GetValues(String command, String dbName, String Column) {
		Connection connection = null;

		String value = null;

		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:"+ dbName +".db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			//updates the database with given command
			ResultSet rs = statement.executeQuery(command);

			while (rs.next()) {
				value = rs.getString(Column);
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

		return (value);
	}

}

