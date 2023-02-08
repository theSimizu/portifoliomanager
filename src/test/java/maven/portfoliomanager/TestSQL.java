package maven.portfoliomanager;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import database.DataBase;
import database.Wallet;

public class TestSQL {

	public static void main(String[] args) {
		DataBase db = new DataBase();
//		DataBase db = DataBase.db;
		db.createTables();
		Wallet wallet = Wallet.getWallet(1);
		wallet.printCoins();
//		wallet.printTransactions();
//		db.createWallet("MetaMask");
//		db.getAllWallets();
//		db.createTransaction("Cardano", "ada", 1, "USD", 1, 1, LocalDateTime.now(), 1);
//		db.getTransactions(1);
		
//		System.out.println(db.getTransactions(1));
	}

}
