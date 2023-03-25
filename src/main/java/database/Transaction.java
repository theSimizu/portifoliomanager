package database;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Transaction implements Comparable<Transaction> {
	
	private int id, walletID;
	private boolean buy;
	private double amount, value;
	private String coinName, coinSymbol, pair;
	private LocalDateTime dateTime;
//	private static DataBase db = new DataBase();
	private static DataBase db = DataBase.db;
	

	public double getAmount() {
		return amount;
	}

	public double getValue() {
		return value;
	}

	public String getCoinSymbol() {
		return coinSymbol;
	}

	public String getPair() {
		return pair;
	}

	public boolean getBuy() {
		return buy;
	}


//	public Transaction(String coinName, String coinSymbol, int type, String pair,
//					   double amount, double value, LocalDateTime datetime, int walletID) {
//
//		db.createTransaction(coinName, coinSymbol, type, pair, amount, value, datetime, walletID);
//
//
//	}

	public Asset getCoin() {
		return new CryptoAsset(coinName, coinSymbol, pair, amount, value);
	}

	
	public Transaction(int id) {
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Map<String, String> transactionData;
		transactionData = db.getTransactionData(id);
		this.id = id;
		this.coinName = transactionData.get("name");
		this.coinSymbol = transactionData.get("symbol");
		this.buy = Integer.parseInt(transactionData.get("buy")) == 1;
		this.pair = transactionData.get("pair");
		this.amount = Double.parseDouble(transactionData.get("amount"));
		this.value = Double.parseDouble(transactionData.get("value"));
		this.dateTime = LocalDateTime.parse(transactionData.get("datetime"), myFormatObj);
		this.walletID = Integer.parseInt(transactionData.get("walletID"));
		
	}

	public String getCoinName() {
		return coinName;
	}

//	public static void createTransaction(String coinName, String coinSymbol, int type, String pair,
//										 double amount, double value, LocalDateTime datetime, int walletID) {
//		db.createTransaction(coinName, coinSymbol, type, pair, amount, value, datetime, walletID);
//	}

	public static void createTransaction(CryptoAsset coin, Asset pair, boolean buy, LocalDateTime datetime, int walletID) {
		db.createTransaction(coin, pair, buy, datetime, walletID);
	}
	
	@Override
	public String toString() {
		return "| " + id + " | " + coinName + " | " + coinSymbol + 
			  " | " + buy + " | " + pair + " | " + amount +
			  " | " + value + " | " + dateTime + " | " + walletID + " |";
	}

	@Override
	public int compareTo(Transaction t) {
		
		return this.getCoinName().compareTo(t.getCoinName());
	}
	
}
