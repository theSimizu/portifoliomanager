package maven.portfoliomanager;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import database.DataBase;
import database.Wallet;

public class TestSQL {

	public static void main(String[] args) {
		DataBase db = new DataBase();
		db.createTables();
		Wallet wallet = Wallet.getWallet(4);
		wallet.printTransactions();
//		db.createWallet("MetaMask");
//		db.getAllWallets();
//		db.createTransaction("BinanceCoin", "bnb", 1, "USD", 2, 2000, LocalDateTime.now(), 4);
//		db.getTransactions(1);
	}

}
