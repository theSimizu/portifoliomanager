package assets.bank;

import assets.Asset;
import assets.FiatAsset;
import assets.Transaction;
import assets.crypto.CryptoAsset;
import assets.data.FiatCoinsData;

import java.util.ArrayList;
import java.util.Collections;

public class BankAsset extends FiatAsset {

    public BankAsset(String coinName, String coinSymbol, double amount, double value) {
        super(coinName, coinSymbol, amount, value);
        this.currentDollarUnitaryValue = FiatCoinsData.getCoinCurrentDollarPrice(coinSymbol);
    }

    public static ArrayList<BankAsset> getAssets(ArrayList<Transaction> transactions) {
        ArrayList<BankAsset> assets = new ArrayList<>();
        BankAsset lastAsset = null;
        Collections.sort(transactions);
        for (Transaction transaction : transactions) {
            BankAsset currentAsset = (BankAsset) transaction.getAsset();
            if (lastAsset == null || !currentAsset.isEqual(lastAsset)) assets.add(lastAsset = currentAsset);
            else lastAsset.sumCoin(currentAsset);
        }
        assets.sort(Asset::compareTo);
        return assets;
    }
}
