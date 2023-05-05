package assets;


import java.text.DecimalFormat;

public abstract class Asset implements Comparable<Asset> {

	protected String name, id, pairSymbol, symbol, currencySymbol;
	protected FiatAsset pair;
	protected double amount, buySellDollarUnitaryValue, currentDollarUnitaryValue;//quotationValue

	public Asset(String name, String symbol, String coinGeckoID, String pairSymbol, double amount, double value) {
		this.id = coinGeckoID;
		this.name = name;
		this.symbol = symbol;
		this.pairSymbol = pairSymbol;
		this.amount = amount;
		this.buySellDollarUnitaryValue = value;
		this.pair = new FiatAsset(pairSymbol);
	}

	public Asset(String id, String name, String symbol, double value) {
		this.id = id;
		this.name = name;
		this.symbol = symbol;
		this.buySellDollarUnitaryValue = value;

	}

	protected Asset() {
	}

	public Asset(String coinName, String coinSymbol, double amount, double value) {
		this.name = coinName;
		this.symbol = coinSymbol;
		this.amount = amount;
		this.buySellDollarUnitaryValue = value;
	}

	public void convertTo(String fiatSymbol) {
		this.setPair(fiatSymbol);
	}

	public void sumCoin(Asset asset) {
		this.amount += asset.amount;
	}

	public boolean isEqual(Asset asset) {
		return this.name.equalsIgnoreCase(asset.name);
	}


	// GETTERS
	public Asset getPair() {
		return this.pair;
	}

	public String getName() {
		return name;
	}

	public String getSymbol() {
		return symbol;
	}

	public double getAmount() {
		return amount;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public double getBuySellDollarUnitaryValue() {
		return buySellDollarUnitaryValue;
	}

	public double getBuySellConvertedUnitaryValue() {
		return buySellDollarUnitaryValue * this.pair.getQuotation();
	}

	public double getCurrentDollarUnitaryValue() {
		return buySellDollarUnitaryValue;
	}

	public double getCurrentConvertedUnitaryValue() {
		return buySellDollarUnitaryValue * this.pair.getQuotation();
	}

	public double getBuySellTotalDollarValue() {
		return amount * buySellDollarUnitaryValue;
	}

	public double getBuySellTotalConvertedValue() {
		return amount * buySellDollarUnitaryValue * this.pair.getQuotation();
	}

	public double getCurrentTotalDollarValue() {
		return amount * currentDollarUnitaryValue;
	}

	public double getCurrentTotalConvertedValue() {return amount * currentDollarUnitaryValue * this.pair.getQuotation();}

	public String getId() {
		return id;
	}

	public String getCurrentTotalConvertedValueString() {
		String currencySymbol = pair.currencySymbol;
		DecimalFormat val = new DecimalFormat("0.00");
		String formattedTotal = val.format(getCurrentTotalConvertedValue());
		return currencySymbol+formattedTotal;
	}

	public String getCurrentUnitaryConvertedValueString() {
		String currencySymbol = pair.currencySymbol;
		DecimalFormat val = new DecimalFormat("0.00");
		String formattedTotal = val.format(currentDollarUnitaryValue * this.pair.getQuotation());
		return currencySymbol+formattedTotal;
	}

	// SETTERS

	public void setName(String name) { this.name = name; }

	public void setCurrencySymbol(String currencySymbol) { this.currencySymbol = currencySymbol; }

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setBuySellDollarUnitaryValue(double buySellDollarUnitaryValue) {
		this.buySellDollarUnitaryValue = buySellDollarUnitaryValue;
	}

	public void setPair(String pairSymbol) {
		this.pair = new FiatAsset(pairSymbol);
	}


	// OVERRIDE
	@Override
	public int compareTo(Asset asset) {
		return (int)((-asset.getCurrentTotalDollarValue() + this.getCurrentTotalDollarValue()));
	}

	@Override
	public String toString() {
		return this.name;
	}
}
