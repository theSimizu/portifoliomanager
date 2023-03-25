package database;

import java.util.ArrayList;
import java.util.Collections;

public class CryptoAsset extends Asset {
    public CryptoAsset(String name, String symbol, String pairSymbol, double amount, double value) {
        super(name, symbol, pairSymbol, amount, value);
    }

    public CryptoAsset(String coinGeckoID, String name, String symbol, double value) {
        this.name = name;
		this.id = coinGeckoID;
		this.symbol = symbol;
		this.value = value;
    }

    public CryptoAsset(String name, String symbol, double value) {
        super(name, symbol, value);
    }


    public static ArrayList<CryptoAsset> getCryptos(ArrayList<Transaction> transactions) {
        ArrayList<CryptoAsset> assets = new ArrayList<>();
        CryptoAsset lastAsset = null;
        Collections.sort(transactions);

        for (Transaction transaction : transactions) {
            CryptoAsset currentAsset = (CryptoAsset) transaction.getCoin();

            if (lastAsset == null || !currentAsset.isEqual(lastAsset)) {
                lastAsset = currentAsset;
                assets.add(lastAsset);
            } else {
                lastAsset.sumCoin(currentAsset, transaction.getBuy());
            }

        }


        return assets;
    }
}
