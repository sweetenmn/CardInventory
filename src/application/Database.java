package application;


import java.sql.*;

public class Database {
	String dbName;
	Connection connection;

	public Database(String dbName) throws ClassNotFoundException {
		this.dbName = dbName + ".db";
		Class.forName("org.sqlite.JDBC");

		int rsCount = 0;

		connection = null;

		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:" + this.dbName);
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); 

			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);

			while(rs.next()) {
				rsCount += 1;
			}

			if (rsCount != 4) {
				statement.executeUpdate("CREATE TABLE CardTable (CardId INTEGER PRIMARY KEY, CardName TEXT, SetId INTEGER)");
				statement.executeUpdate("CREATE TABLE SetTable (SetId INTEGER, SetName TEXT)");
				statement.executeUpdate("CREATE TABLE Rarity (CardId INTEGER, Rarity TEXT, Foil TEXT)");
				statement.executeUpdate("CREATE TABLE Condition (CardId TEXT, NewMint INTEGER, Excellent INTEGER, " +
						"VeryGood INTEGER, Good INTEGER, Poor INTEGER)");
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

	public void updateDB(String command) {
		connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:"+ dbName);
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate(command);
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

	public String GetValues(String command, String Column) {
		Connection connection = null;
		String value = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:"+ dbName);
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet rs = statement.executeQuery(command);
			while (rs.next()) {
				value = rs.getString(Column);
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
		return (value);
	}
	
	public ResultSet getResults(String command) throws ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");
		connection = null;
		ResultSet set = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:"+ dbName);
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			set = statement.executeQuery(command);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} 
		return set;
	}
	
	public void closeConnection(){
		if (connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Failed to close connection.");
				e.printStackTrace();
			}
		}
	}

}