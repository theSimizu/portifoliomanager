package database;


import java.text.DecimalFormat;

public abstract class Asset implements Comparable<Asset> {

	protected String name, id, pairSymbol, symbol, currencySymbol;
	protected FiatAsset pair;

	protected double amount, value, total, quotationValue;



	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public Asset(String name, String symbol, String pairSymbol, double amount, double value) {
		this.name = name;
		this.symbol = symbol;
		this.pairSymbol = pairSymbol;
		this.amount = amount;
		this.value = value;
		this.total = amount * value;
		this.pair = new FiatAsset(pairSymbol);
		this.quotationValue = this.value * this.pair.getQuotation();

	}

	public Asset(String name, String symbol, double value) {
		this.name = name;
		this.symbol = symbol;
		this.value = value;
//		this.pair = new FiatAsset(pairSymbol);

	}

	protected Asset() {
	}

	public void setAmount(double amount) {
		this.amount = amount;
		this.total = amount * value;
	}

	public void setValue(double value) {
		this.value = value;
		this.total = amount * value;
	}

	public void setPair(String pairSymbol) {
		this.pair = new FiatAsset(pairSymbol);
		this.setQuotationValue(this.value * this.pair.getQuotation());
	}

	public void setQuotationValue(double value) {
		this.quotationValue = value;
		this.total = amount * quotationValue;
	}

	public void convertTo(String fiatSymbol) {
//		this.setQuotationValue(this.quotationValue/this.pair.getQuotation());
		this.setValue(this.value/this.pair.getQuotation());
		this.setPair(fiatSymbol);
	}

	public Asset getPair() {
		return this.pair;
	}


	public String getName() {
		return name;
	}

	public String getSymbol() {
		return symbol;
	}

//	public String getPair() {
//		return pair;
//	}

	public double getAmount() {
		return amount;
	}

	public double getTotal() {
//		return Math.round(total*100.0)/100.0;
		return total;
	}

	public String getTotalString() {
		DecimalFormat val = new DecimalFormat("#.##");
		return val.format(total);
	}

//	public double getTotalIn(String fiat) {
//		FiatAsset value = new FiatAsset(fiat);
//
//	}

	public String getQuotationValue() {
		DecimalFormat df = new DecimalFormat("0.#");
		df.setMaximumFractionDigits(8);
		String formatedValue = df.format(this.quotationValue);

		return formatedValue;
	}

	public double getValue() {
		return value;
	}

	public String getId() {
		return id;
	}

	public void sumCoin(Asset asset, boolean buy) {
		this.amount += (buy) ? asset.amount : - asset.amount;
		this.total += (buy) ? asset.total : - asset.total;

//		this.amount += asset.amount;
//		this.total += asset.total;
	}


	public boolean isEqual(Asset asset) {
		return this.name.equalsIgnoreCase(asset.getName());
	}


	@Override
	public int compareTo(Asset asset) {
		return (int)(asset.getTotal() - this.getTotal());
	}

	@Override
	public String toString() {
		return this.name;
	}
	
}
