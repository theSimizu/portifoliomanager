package assets;

import assets.bank.BankAsset;
import assets.crypto.CryptoAsset;
import database.DataBase;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Transaction implements Comparable<Transaction> {
	
	private int id, walletID;
	private boolean buy;
	private double amount, value;
	private String coinName, coinSymbol, pair, coingeckoID;
	private LocalDateTime dateTime;

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

	public Asset getCoin() {
		return new CryptoAsset(coinName, coinSymbol, coingeckoID, pair, amount, value);
	}

	public Asset getAsset() {
		return new BankAsset(coinName, coinSymbol, amount, value);
	}

	public Transaction(int id, String table) {
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Map<String, String> transactionData = null;
		if (table.equals("CryptoTransactions")) {
			transactionData = DataBase.getCryptoTransactionData(id);
		} else if (table.equals("BankTransactions")) {
			transactionData = DataBase.getBankTransactionData(id);
		}
		this.id = id;
		assert transactionData != null;
		this.coinName = transactionData.get("name");
		this.coinSymbol = transactionData.get("symbol");
		this.buy = Integer.parseInt(transactionData.get("buy")) == 1;
		this.coingeckoID = transactionData.get("coingeckoID");
		this.pair = transactionData.get("pair");
		this.amount = Double.parseDouble(transactionData.get("amount"));
		this.value = Double.parseDouble(transactionData.get("value"));
		this.dateTime = LocalDateTime.parse(transactionData.get("datetime"), myFormatObj);
		this.walletID = Integer.parseInt(transactionData.get("walletID"));
		if (!buy) amount *= -1;
	}

	public String getCoinName() {
		return coinName;
	}

	public static void createTransaction(CryptoAsset coin, Asset pair, boolean buy, LocalDateTime datetime, int walletID) {
		DataBase.createTransaction(coin, pair, buy, datetime, walletID);
	}

	public static void createTransaction(FiatAsset coin, Asset pair, boolean buy, LocalDateTime datetime, int walletID) {
		DataBase.createTransaction(coin, pair, buy, datetime, walletID);
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