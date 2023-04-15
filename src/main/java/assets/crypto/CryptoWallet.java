package assets.crypto;

import assets.Asset;
import assets.Wallet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class CryptoWallet extends Wallet {


    protected CryptoWallet(int id) {
        super(id);
        this.setCryptos();
    }

    private void setCryptos() {
        super.loadTransactions();
        super.assets = CryptoAsset.getCryptos(super.transactions);
        Collections.reverse(assets);
    }

    @Override
    public ArrayList<? extends Asset> getAssets() {
        setCryptos();
        return assets;
    }

    protected static void setWallets() {
		for (int id : db.getAllWalletsIDs()) wallets.add(new CryptoWallet(id));
    }

    public static ArrayList<Wallet> getWallets() {
        wallets.clear();
        setWallets();
        return wallets;
    }

    public static double getWalletsTotal() {
        double tot = 0;
        for (Wallet wallet : wallets) tot += Double.parseDouble(wallet.getTotal());
        return tot;
    }

    public static String getWalletsTotalString(String symbol) {
        DecimalFormat val = new DecimalFormat("0.00");
        return symbol+val.format(getWalletsTotal());
    }

}
