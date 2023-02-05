package database;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Transaction {
	
	private int id, type, walletID;
	private double amount, value;
	private String name, symbol, pair;
	private LocalDateTime dateTime;
	private static DataBase db = DataBase.db;
	

	public Transaction(String name, String symbol, int type, String pair, 
					   double amount, double value, LocalDateTime datetime, int walletID) {
		
		db.createTransaction(name, symbol, type, pair, amount, value, datetime, walletID);
		
		
	}
	
	public Transaction(int id) {
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Map<String, String> transactionData = db.getTransactionData(id);
		this.id = id;
		this.name = transactionData.get("name");
		this.symbol = transactionData.get("symbol");
		this.type = Integer.parseInt(transactionData.get("type"));
		this.pair = transactionData.get("pair");
		this.amount = Double.parseDouble(transactionData.get("amount"));
		this.value = Double.parseDouble(transactionData.get("value"));
		this.dateTime = LocalDateTime.parse(transactionData.get("datetime"), myFormatObj);
		this.walletID = Integer.parseInt(transactionData.get("walletID"));
		
	}
	
	public String toString() {
		return "| " + id + " | " + name + " | " + symbol + 
			  " | " + type + " | " + pair + " | " + amount +
			  " | " + value + " | " + dateTime + " | " + walletID + " |";
	}
	
}
