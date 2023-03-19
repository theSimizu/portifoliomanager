package database;

import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
	public static DataBase db = new DataBase();
	private Connection connection = null;

	public DataBase() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:portfolios.db");
			createTables();
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}

	private ResultSet execute(String sql) throws SQLException {
		Statement statement = connection.createStatement();
		return statement.executeQuery(sql);
	}

	private void executeUpdate(String sql) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
		statement.close();
	}

	private void createTables() {
		String createWalletsTable =
				  "CREATE TABLE IF NOT EXISTS CryptoWallets ("
				+ "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "name VARCHAR(15) NOT NULL UNIQUE"
				+ ")";

		String createTransactionsTable =
				  "CREATE TABLE IF NOT EXISTS Transactions ("
				+ "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "name VARCHAR(20) NOT NULL,"
				+ "coingeckoID VARCHAR(20) NOT NULL,"
				+ "symbol VARCHAR(5),"
				+ "buy BOOLEAN,"
				+ "pair VARCHAR(25),"
				+ "amount REAL,"
				+ "value REAL,"
				+ "datetime DATETIME,"
				+ "walletID INTEGER references CryptoWallets(ID)"
				+ ")";

		try {
			executeUpdate(createWalletsTable);
			executeUpdate(createTransactionsTable);

		} catch (SQLException e) {
			e.printStackTrace();
		}
			

	}
	
	public void createWallet(String walletName) {
		walletName = walletName.replace(" ", "_");
		String createWallet = "INSERT INTO CryptoWallets(name) VALUES('" + walletName + "')";
		try {
			executeUpdate(createWallet);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void createTransaction(CryptoAsset coin, Asset pair, boolean buy, LocalDateTime datetime, int walletID){
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String dt = datetime.format(myFormatObj);
		String name = coin.getName();
		String coingeckoID = coin.getId();
		String symbol = coin.getSymbol();
		String pairSymbol = pair.getSymbol();
		double amount = coin.getAmount();
		double value = coin.getValue();
		String createTransaction =
				"INSERT INTO Transactions(name, coingeckoID, symbol, buy, pair, amount, value, datetime, walletID) VALUES("
						+ "'" + name + "',"
						+ "'" + coingeckoID + "',"
						+ "'" + symbol +"',"
						+ "" + buy + ","
						+ "'" + pairSymbol + "',"
						+ "" + amount + ","
						+ "" + value + ","
						+ "'" + dt + "',"
						+ "" + walletID + ""
						+ ")";
		try {
			executeUpdate(createTransaction);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
//	public void createTransaction(String name, String coingeckoID, String symbol, int type, String pair,
//								  double amount, double value, LocalDateTime datetime, int walletID){
//		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		String dt = datetime.format(myFormatObj);
//		String createTransaction =
//				"INSERT INTO Transactions(name, coingeckoID, symbol, type, pair, amount, value, datetime, walletID) VALUES("
//				+ "'" + name + "',"
//				+ "'" + coingeckoID + "',"
//				+ "'" + symbol +"',"
//				+ "" + type + ","
//				+ "'" + pair + "',"
//				+ "" + amount + ","
//				+ "" + value + ","
//				+ "'" + dt + "',"
//				+ "" + walletID + ""
//				+ ")";
//		try {
//			executeUpdate(createTransaction);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	public ArrayList<Transaction> getTransactions(int walletID) {
		ArrayList<Transaction> transactions = new ArrayList<>();
		String getTransactions = "SELECT id FROM Transactions WHERE walletID = " + walletID;
		try {
			ResultSet rs = execute(getTransactions);
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				transactions.add(new Transaction(id));
			}
			rs.getStatement().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}

	public int getWalletId(String name) {
		int id = 0;
		name = name.replace(" ", "_");
		String getWalletId = "SELECT id FROM CryptoWallets WHERE name = " + name;
		try {
			ResultSet rs = execute(getWalletId);
			id = Integer.parseInt(rs.getString("id"));
			rs.getStatement().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public String getWalletName(int id) {
		String name;
		try {
			ResultSet rs = execute("SELECT name FROM CryptoWallets WHERE id = " + id);
			name = rs.getString("name");
			rs.getStatement().close();
//			rs.getStatement().getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
			name = "";
		}
		return name.replace("_", " ");
	}

	public ArrayList<Integer> getAllWalletsIDs() {
		ArrayList<Integer> ids = new ArrayList<>();
		String getAllWalletsIDs = "SELECT id FROM CryptoWallets";
		try {
			ResultSet rs = execute(getAllWalletsIDs);
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				ids.add(id);
			}
			rs.getStatement().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ids;
	}

	public Map<String, String> getTransactionData(int id) {
		Map<String, String> map = new HashMap<>();
		String getTransactionData = "SELECT * FROM Transactions WHERE id = " + id;
		try {
			ResultSet rs = execute(getTransactionData);
			while (rs.next()) {
				map.put("name", rs.getString("name"));
				map.put("coingeckoID", rs.getString("coingeckoID"));
				map.put("symbol", rs.getString("symbol"));
				map.put("buy", rs.getString("buy"));
				map.put("pair", rs.getString("pair"));
				map.put("amount", rs.getString("amount"));
				map.put("value", rs.getString("value"));
				map.put("datetime", rs.getString("datetime"));
				map.put("walletID", rs.getString("walletID"));
			}
			rs.getStatement().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	
}
