package assets;

import assets.crypto.CryptoAsset;
import assets.crypto.Transaction;
import database.DataBase;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Wallet {
	private final int id;
	private final String name;
	protected ArrayList<Transaction> transactions;
	protected ArrayList<? extends Asset> assets;
	protected static final DataBase db = DataBase.db;
	protected static ArrayList<Wallet> wallets = new ArrayList<>();

	protected Wallet(int id) {
		this.id = id;
		this.name = db.getWalletName(id);
	}



	public String getTotal() {
		DecimalFormat val = new DecimalFormat("0.00");
		double total = 0;
		for (Asset asset : assets) {
//			System.out.println(asset.formattedTotalText());
//			total += Double.parseDouble(asset.formattedTotalText());
//			total += asset.getCurTotal();
//			total += asset.getBuySellDollarTotal();
			total += asset.getCurrentTotalConvertedValue();
		}
		return val.format(total);
	}


	protected void loadTransactions() {
		this.transactions = db.getTransactions(this.id);
	}

	public void createTransaction(CryptoAsset coin, Asset pair, boolean buy, LocalDateTime datetime){
		Transaction.createTransaction(coin, pair, buy, datetime, this.id);
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<? extends Asset> getAssets() {
		return this.assets;
	}

	public static void createWallet(String name) {
		db.createWallet(name);
	}
}
