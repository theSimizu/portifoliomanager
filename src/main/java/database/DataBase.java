package database;

import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sqlite.SQLiteException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
	public static DataBase db = new DataBase();
	
	Connection connection = null;
	Statement statement;
	public DataBase() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:portfolios.db");
			statement = connection.createStatement();
			createTables();
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
		}
	}
	
	public void createTables() {
		try {
			statement.executeUpdate(
					  "CREATE TABLE IF NOT EXISTS CryptoWallets ("
					+ "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "name VARCHAR(15) NOT NULL UNIQUE"
					+ ")"
			);
		
			statement.executeUpdate(
					  "CREATE TABLE IF NOT EXISTS Transactions ("
					+ "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "name VARCHAR(15) NOT NULL,"
					+ "symbol VARCHAR(5),"
					+ "type INTEGER,"
					+ "pair VARCHAR(25),"
					+ "amount REAL,"
					+ "value REAL,"
					+ "datetime DATETIME,"
					+ "walletID INTEGER references CryptoWallets(ID)"
					+ ")"
			);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
			

	}
	
	public void createWallet(String walletName){
		walletName = walletName.replace(" ", "_");
		try {
			statement.executeUpdate(
				"INSERT INTO CryptoWallets(name) VALUES('" + walletName + "')"
			);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void createTransaction(String name, String symbol, int type, String pair, 
								  double amount, double value, LocalDateTime datetime, int walletID){
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String dt = datetime.format(myFormatObj);


		try {
			statement.executeUpdate(
				"INSERT INTO Transactions(name, symbol, type, pair, amount, value, datetime, walletID) VALUES("
				+ "'" + name + "',"
				+ "'" + symbol +"',"
				+ "" + type + ","
				+ "'" + pair + "',"
				+ "" + amount + ","
				+ "" + value + ","
				+ "'" + dt + "',"
				+ "" + walletID + ""
				+ ")"
			);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	
	}
	
	public ArrayList<Transaction> getTransactions(int walletID) {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		try {
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM Transactions WHERE walletID = " + walletID
			);
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				transactions.add(new Transaction(id));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}
	
	public int getWalletId(String name) {
		int id;
		try {
			name = name.replace(" ", "_");
			ResultSet rs = statement.executeQuery(
					  "SELECT * FROM CryptoWallets WHERE name = " + name
			);
			id = Integer.parseInt(rs.getString("id"));
		} catch (SQLException e) {
			e.printStackTrace();
			id = 0;
		}
		return id;
	}
	
	public String getWalletName(int id) {
		String name;
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM CryptoWallets WHERE id = " + id);
			name = rs.getString("name");
			
		} catch (SQLException e) {
			e.printStackTrace();
			name = "";
		}
		return name.replace("_", " ");
	}
	
	public ArrayList<Wallet> getAllWallets() {
		ArrayList<Wallet> wallets = new ArrayList<Wallet>();
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM CryptoWallets");
			
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				wallets.add(Wallet.getWallet(id));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wallets;
	}
	
	public Map<String, String> getTransactionData(int id) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM Transactions WHERE id = " + id
			);
			while (rs.next()) {
				map.put("name", rs.getString("name"));
				map.put("symbol", rs.getString("symbol"));
				map.put("type", rs.getString("type"));
				map.put("pair", rs.getString("pair"));
				map.put("amount", rs.getString("amount"));
				map.put("value", rs.getString("value"));
				map.put("datetime", rs.getString("datetime"));
				map.put("walletID", rs.getString("walletID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
		
	}

	
}
