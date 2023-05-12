package database;

import assets.Asset;
import assets.FiatAsset;
import assets.crypto.CryptoAsset;
import assets.Transaction;

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
	private static Connection connection = null;

	public static void startDatabase() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:resources/portfolios.db");
			createTables();
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}

	private static ResultSet execute(String sql) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static void executeUpdate(String sql) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			statement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	private static void createTables() {
		String createCryptoWalletsTable =
				"CREATE TABLE IF NOT EXISTS CryptoWallets ("
						+ "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
						+ "name VARCHAR(15) NOT NULL UNIQUE"
						+ ")";

		String createCryptoTransactionsTable =
				"CREATE TABLE IF NOT EXISTS CryptoTransactions ("
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

		String createBankWalletsTable =
				"CREATE TABLE IF NOT EXISTS BankWallets ("
						+ "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
						+ "name VARCHAR(15) NOT NULL UNIQUE"
						+ ")";

		String createBankTransactionsTable =
				"CREATE TABLE IF NOT EXISTS BankTransactions ("
						+ "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
						+ "name VARCHAR(20) NOT NULL,"
						+ "symbol VARCHAR(5),"
						+ "buy BOOLEAN,"
						+ "pair VARCHAR(25),"
						+ "amount REAL,"
						+ "value REAL,"
						+ "datetime DATETIME,"
						+ "walletID INTEGER references BankWallets(ID)"
						+ ")";
		executeUpdate(createCryptoWalletsTable);
		executeUpdate(createCryptoTransactionsTable);
		executeUpdate(createBankWalletsTable);
		executeUpdate(createBankTransactionsTable);



	}

	public static void createWallet(String walletName, String table) {
		walletName = walletName.replace(" ", "_");
		String createWallet = "INSERT INTO " + table + "(name) VALUES('" + walletName + "')";
		executeUpdate(createWallet);
	}

	public static void createTransaction(CryptoAsset coin, Asset pair, boolean buy, LocalDateTime datetime, int walletID){
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String dt = datetime.format(myFormatObj);
		String name = coin.getName();
		String coingeckoID = coin.getId();
		String symbol = coin.getSymbol();
		String pairSymbol = pair.getSymbol();
		double amount = coin.getAmount();
		double value = coin.getBuySellDollarUnitaryValue();
		String createTransaction =
				"INSERT INTO CryptoTransactions(name, coingeckoID, symbol, buy, pair, amount, value, datetime, walletID) VALUES("
						+ "'" + name + "','"
						+ coingeckoID + "', '"
						+ symbol +"',"
						+ buy + ", '"
						+ pairSymbol + "',"
						+ amount + ","
						+ value + ",'"
						+ dt + "',"
						+ walletID
						+ ")";
		executeUpdate(createTransaction);
	}

	public static void createTransaction(FiatAsset coin, Asset pair, boolean buy, LocalDateTime datetime, int walletID){
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String dt = datetime.format(myFormatObj);
		String name = coin.getName();
		String symbol = coin.getSymbol();
		String pairSymbol = pair.getSymbol();
		double amount = coin.getAmount();
		double value = coin.getBuySellDollarUnitaryValue();
		String createTransaction =
				"INSERT INTO BankTransactions(name, symbol, buy, pair, amount, value, datetime, walletID) VALUES("
						+ "'" + name + "','"
						+ symbol +"',"
						+ buy + ", '"
						+ pairSymbol + "',"
						+ amount + ","
						+ value + ",'"
						+ dt + "',"
						+ walletID
						+ ")";
//		try {
		executeUpdate(createTransaction);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}


	public static ArrayList<Transaction> getTransactions(int walletID, String table) {
		ArrayList<Transaction> transactions = new ArrayList<>();
		String getTransactions = "SELECT id FROM " + table + " WHERE walletID = " + walletID;
		try {
			ResultSet rs = execute(getTransactions);
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				transactions.add(new Transaction(id, table));
			}
			rs.getStatement().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}


	public static String getWalletName(int id, String table) {
		String name;
		try {
			ResultSet rs = execute("SELECT name FROM " + table + " WHERE id = " + id);
			name = rs.getString("name");
			rs.getStatement().close();
		} catch (SQLException e) {
			e.printStackTrace();
			name = "";
		}
		return name.replace("_", " ");
	}

	public static ArrayList<Integer> getAllWalletsIDs(String table) {
		ArrayList<Integer> ids = new ArrayList<>();
		String getAllWalletsIDs = "SELECT id FROM " + table;
//		String getAllWalletsIDs = "SELECT id FROM CryptoWallets";
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

	public static Map<String, String> getCryptoTransactionData(int id) {
		Map<String, String> map = new HashMap<>();
		String getTransactionData = "SELECT * FROM CryptoTransactions WHERE id = " + id;
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

	public static Map<String, String> getBankTransactionData(int id) {
		Map<String, String> map = new HashMap<>();
		String getTransactionData = "SELECT * FROM BankTransactions WHERE id = " + id;
		try {
			ResultSet rs = execute(getTransactionData);
			while (rs.next()) {
				map.put("name", rs.getString("name"));
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
