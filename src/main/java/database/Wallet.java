package database;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class Wallet {
	private final int id;
	private final String name;
	private ArrayList<Transaction> transactions;
	private ArrayList<CryptoAsset> assets;
	private static final DataBase db = DataBase.db;
//	private static DataBase db = new DataBase();
	private static final ArrayList<Wallet> wallets = new ArrayList<>();

	private Wallet(int id) {
		this.id = id;
		this.name = db.getWalletName(id);
		this.loadTransactions();
		this.setCryptos();
	}

	private void loadTransactions() {
		this.transactions = db.getTransactions(this.id);
	}

	private void setCryptos() {
		this.assets = CryptoAsset.getCryptos(this.transactions);
		Collections.sort(this.assets);
	}

	public void createTransaction(CryptoAsset coin, Asset pair, boolean buy, LocalDateTime datetime){
		Transaction.createTransaction(coin, pair, buy, datetime, this.id);
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<CryptoAsset> getCoins() {
		return this.assets;
	}

	public static void createWallet(String name) {
		db.createWallet(name);
	}

	private static void setWallets() {
		for (int id : db.getAllWalletsIDs()) wallets.add(new Wallet(id));
	}

	public static ArrayList<Wallet> getWallets() {
		wallets.clear();
		setWallets();
		return wallets;
	}


}
