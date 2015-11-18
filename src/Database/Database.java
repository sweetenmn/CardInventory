package Database;

import java.sql.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Database {
	String dbName;
	Connection connection;

	public Database(String dbName) throws ClassNotFoundException {
		this.dbName = dbName + ".db";
		Class.forName("org.sqlite.JDBC");

		int rsCount = 0;

		connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + this.dbName);
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(10); 

			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);

			while(rs.next()) {
				rsCount += 1;
			}

			if (rsCount != 4) {
				createDBTables(statement);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				badNews("Database error.");
			}
		}
	}
	
	void badNews(String message) {
		Alert badNum = new Alert(AlertType.ERROR);
		badNum.setContentText(message);
		badNum.show();
	}
	
	private void createDBTables(Statement statement) throws SQLException{
		statement.executeUpdate("CREATE TABLE CardTable (CardId INTEGER PRIMARY KEY, CardName TEXT, SetId INTEGER)");
		statement.executeUpdate("CREATE TABLE SetTable (SetId INTEGER PRIMARY KEY, SetName TEXT, UNIQUE(SetName))");
		statement.executeUpdate("CREATE TABLE Rarity (CardId INTEGER, Rarity TEXT, Foil TEXT)");
		statement.executeUpdate("CREATE TABLE Condition (CardId TEXT, NewMint INTEGER, Excellent INTEGER, " +
				"VeryGood INTEGER, Good INTEGER, Poor INTEGER)");
	}

	public void updateDB(String command) throws SQLException {
		connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:"+ dbName);
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate(command);
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				badNews("Database error.");
			}
		}
	}

	public ResultSet getResults(String command) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		connection = null;
		ResultSet set = null;
		connection = DriverManager.getConnection("jdbc:sqlite:"+ dbName);
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(10);
		set = statement.executeQuery(command);
		return set;
	}
	
	public void closeConnection() throws SQLException{
		if (connection != null){
			connection.close();
		}
	}

}
