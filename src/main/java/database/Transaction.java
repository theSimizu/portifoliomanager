package database;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Transaction implements Comparable<Transaction> {
	
	private int id, type, walletID;
	private double amount, value;
	private String coinName, coinSymbol, pair;
	private LocalDateTime dateTime;
	private static DataBase db = new DataBase();
//	private static DataBase db = DataBase.db;
	

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

	public Transaction(String coinName, String coinSymbol, int type, String pair, 
					   double amount, double value, LocalDateTime datetime, int walletID) {
		
		db.createTransaction(coinName, coinSymbol, type, pair, amount, value, datetime, walletID);
		
		
	}
	
	public String getCoinName() {
		return coinName;
	}
	
	public Transaction(int id) {
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Map<String, String> transactionData = db.getTransactionData(id);
		this.id = id;
		this.coinName = transactionData.get("name");
		this.coinSymbol = transactionData.get("symbol");
		this.type = Integer.parseInt(transactionData.get("type"));
		this.pair = transactionData.get("pair");
		this.amount = Double.parseDouble(transactionData.get("amount"));
		this.value = Double.parseDouble(transactionData.get("value"));
		this.dateTime = LocalDateTime.parse(transactionData.get("datetime"), myFormatObj);
		this.walletID = Integer.parseInt(transactionData.get("walletID"));
		
	}
	
	
	@Override
	public String toString() {
		return "| " + id + " | " + coinName + " | " + coinSymbol + 
			  " | " + type + " | " + pair + " | " + amount +
			  " | " + value + " | " + dateTime + " | " + walletID + " |";
	}

	@Override
	public int compareTo(Transaction t) {
		
		return this.getCoinName().compareTo(t.getCoinName());
	}
	
}
