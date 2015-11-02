package application;


import java.sql.*;

public class Database {
	String dbName;

	public Database(String dbName) throws ClassNotFoundException {
		this.dbName = dbName + ".db";
		Class.forName("org.sqlite.JDBC");

		int rsCount = 0;

		Connection connection = null;

		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:"+dbName);
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);

			while(rs.next()) {
				rsCount += 1;
			}

			if (rsCount != 4) {
				statement.executeUpdate("CREATE TABLE Card (CardId INTEGER, CardName TEXT, SetId INTEGER)");
				statement.executeUpdate("CREATE TABLE SetTable (SetId INTEGER, SetName TEXT)");

				statement.executeUpdate("CREATE TABLE Rarity (CardId INTEGER, Rarity TEXT, Foil TEXT)");
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

	public void updateDB(String command) {
		Connection connection = null;

		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:"+ dbName);
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

	public String GetValues(String command, String Column) {
		Connection connection = null;

		String value = null;

		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:"+ dbName);
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
	
	public ResultSet getResults(String command){
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:"+ dbName);
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			System.out.println(command);
			System.out.println(statement.executeQuery(command));
			return statement.executeQuery(command);

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
		return null;

		
		
	}

}