package wallets;

import assets.Asset;
import assets.FiatAsset;
import assets.crypto.CryptoAsset;
import assets.Transaction;
import database.DataBase;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Wallet {
	private final int id;
	private final String name;
	protected ArrayList<Transaction> transactions;
	protected ArrayList<? extends Asset> assets;
//	protected static final DataBase db = DataBase.db;

	protected abstract void setAssets();

	protected Wallet(int id, String table) {
		this.id = id;
		this.name = DataBase.getWalletName(id, table);
		this.setAssets();
	}

	public String getTotal() {
		DecimalFormat val = new DecimalFormat("0.00");
		double total = 0;
		for (Asset asset : assets) { total += asset.getCurrentTotalConvertedValue(); }
		return val.format(total);
	}

	public void createTransaction(Asset coin, Asset pair, boolean buy, LocalDateTime datetime){
		if (coin instanceof CryptoAsset) Transaction.createTransaction((CryptoAsset) coin, pair, buy, datetime, this.id);
		else if (coin instanceof FiatAsset) Transaction.createTransaction((FiatAsset) coin, pair, buy, datetime, this.id);
	}

//	public void createTransaction(FiatAsset coin, Asset pair, boolean buy, LocalDateTime datetime){
//		Transaction.createTransaction(coin, pair, buy, datetime, this.id);
//	}

	protected void loadTransactions(String table) {
		this.transactions = DataBase.getTransactions(this.id, table);
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<? extends Asset> getAssets() {
		return this.assets;
	}
}
