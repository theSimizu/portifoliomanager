package wallets;

import assets.Asset;
import assets.FiatAsset;
import assets.crypto.CryptoAsset;
import assets.Transaction;
import database.DataBase;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Wallet implements Comparable<Wallet>{
	private final int id;
	private final String name;
	protected ArrayList<Transaction> transactions;
	protected ArrayList<? extends Asset> assets;
	private double total;
//	protected static final DataBase db = DataBase.db;

	protected abstract void setAssets();

	protected Wallet(int id, String table) {
		this.id = id;
		this.name = DataBase.getWalletName(id, table);
		this.setAssets();
	}

	public void setTotal() {
		double total = 0;
		for (Asset asset : assets) { total += asset.getCurrentTotalConvertedValue(); }
		this.total = total;
	}

	public void setPair(String pair) {
		for (Asset asset : assets) asset.setPair(pair);
	}

	public String getTotalString() {
		DecimalFormat val = new DecimalFormat("0.00");
		this.setTotal();
		return val.format(this.total).replace(",", ".");
	}

	public double getTotal() {
//		this.setTotal();
		return total;
	}

	public void createTransaction(Asset coin, Asset pair, boolean buy, LocalDateTime datetime){
		if (coin instanceof CryptoAsset) Transaction.createTransaction((CryptoAsset) coin, pair, buy, datetime, this.id);
		else if (coin instanceof FiatAsset) Transaction.createTransaction((FiatAsset) coin, pair, buy, datetime, this.id);
	}

	protected void loadTransactions(String table) {
		this.transactions = DataBase.getTransactions(this.id, table);
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<? extends Asset> getAssets() {
		setAssets();
		return assets;
	}

	@Override
	public int compareTo(Wallet wallet) {
		return (int)(100*(wallet.getTotal() - this.getTotal()));
	}
}
