package database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Coin implements Comparable<Coin> {
	
	private String name, symbol, pair;
	private double amount, total;

	public Coin(String name, String symbol, String pair, double amount, double value) {
		this.name = name;
		this.symbol = symbol;
		this.pair = pair;
		this.amount = amount;
		this.total = amount*value;
	}
	
	
	
	public String getName() {
		return name;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getPair() {
		return pair;
	}

	public double getAmount() {
		return amount;
	}

	public double getTotal() {
		return total;
	}

	public static ArrayList<Coin> getCoins(ArrayList<Transaction> transactions) {
		ArrayList<Coin> coins = new ArrayList<Coin>();
		Collections.sort(transactions);
		
		for (int i = 0; i < transactions.size(); i++) {
			String coinName = transactions.get(i).getCoinName();
			String coinSymbol = transactions.get(i).getCoinSymbol();
			String pair = transactions.get(i).getPair();
			double amount = transactions.get(i).getAmount();
			double value = transactions.get(i).getValue();
			Coin coin = new Coin(coinName, coinSymbol, pair, amount, value);

			if (coins.size() == 0 || !coinName.equalsIgnoreCase(coins.get(coins.size() - 1).getName())) coins.add(coin);
			else coins.get(coins.size() - 1).sumCoin(coin);

		}


		return coins;
	}
	
	public void sumCoin(Coin coin) {
		this.amount += coin.amount;
		this.total += coin.total;
	}



	@Override
	public int compareTo(Coin coin) {
		return (int)(coin.getTotal() - this.getTotal());
	}
	
}
