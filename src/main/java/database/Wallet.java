package database;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Wallet {
	private int id;
	private String name;
	
	private ArrayList<Transaction> transactions;// = new ArrayList<Transactions>();
	private static DataBase db = DataBase.db;
	private static Wallet currentWallet;
	private static ArrayList<Wallet> wallets = db.getAllWallets();
	
	@Override
	public String toString() {
		return "| " + this.id + " | " + this.name + " |";
	}
	
	private Wallet(int id) {
		this.id = id;
		this.name = db.getWalletName(id);
		this.loadTransactions();
	}
	
	private Wallet(String name) {
		db.createWallet(name);
	}
	
	private void loadTransactions() {
		this.transactions = db.getTransactions(this.id);
	}
	
	public void printTransactions() {
		for (Transaction transaction : this.transactions) {
			System.out.println(transaction);
		}
	}
	
	public void createTransaction(String name, String symbol, int type, String pair, 
			  					  double amount, double value, LocalDateTime datetime) {
		
		db.createTransaction(name, symbol, type, pair, amount, value, datetime, this.id);
	}
	
	public static Wallet getWallet(int id) {
		return new Wallet(id);
	}
	
	public static Wallet createWallet(String name) {
		return new Wallet(name);
	}
	
	public static ArrayList<Wallet> getWallets() {
		return wallets;
	}
}
