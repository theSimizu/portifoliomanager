package assets.crypto;

import assets.Asset;
import assets.data.CoingeckoData;

import java.util.ArrayList;
import java.util.Collections;

public class CryptoAsset extends Asset {

    // COIN RECOVERED FROM TRANSACTION
    public CryptoAsset(String name, String symbol, String coingeckoID, String pairSymbol, double amount, double value) {
        super(name, symbol, coingeckoID, pairSymbol, amount, value);
        this.currentDollarUnitaryValue = CoingeckoData.coinGecko.getCoinCurrentDollarPrice(coingeckoID);
    }

    // USED FOR CREATE A NEW TRANSACTION
    public CryptoAsset(String coinGeckoID, String name, String symbol, double value) {
        this.name = name;
		this.id = coinGeckoID;
		this.symbol = symbol;
		this.buySellDollarUnitaryValue = value;
    }

    public static ArrayList<CryptoAsset> getCryptos(ArrayList<Transaction> transactions) {
        ArrayList<CryptoAsset> assets = new ArrayList<>();
        CryptoAsset lastAsset = null;
        Collections.sort(transactions);

        for (Transaction transaction : transactions) {
            CryptoAsset currentAsset = (CryptoAsset) transaction.getCoin();
            if (lastAsset == null || !currentAsset.isEqual(lastAsset)) assets.add(lastAsset = currentAsset);
            else lastAsset.sumCoin(currentAsset);
        }

		assets.sort(Asset::compareTo);
        return assets;
    }
}
