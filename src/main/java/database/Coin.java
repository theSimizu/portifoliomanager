package database;

public class Coin {
	
	private String name, symbol, pair;
	private double amount, value;

	public Coin(String name, String symbol, String pair, double amount, double value) {
		this.name = name;
		this.symbol = symbol;
		this.pair = pair;
		this.amount = amount;
		this.value = value;
	}
}
