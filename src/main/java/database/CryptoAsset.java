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

//    public void setPair(FiatAsset asset){
//        this.pair = asset;
//    }

    public static ArrayList<CryptoAsset> getCryptos(ArrayList<Transaction> transactions) {
        ArrayList<CryptoAsset> assets = new ArrayList<>();
        Collections.sort(transactions);

        for (Transaction transaction : transactions) {
            String coinName = transaction.getCoinName();
            String coinSymbol = transaction.getCoinSymbol();
            String pair = transaction.getPair();
            double amount = transaction.getAmount();
            double value = transaction.getValue();


            CryptoAsset asset = new CryptoAsset(coinName, coinSymbol, pair, amount, value);

            if (assets.size() == 0 || !coinName.equalsIgnoreCase(assets.get(assets.size() - 1).getName()))
                assets.add(asset);
            else
                assets.get(assets.size() - 1).sumCoin(asset);

        }


        return assets;
    }
}
