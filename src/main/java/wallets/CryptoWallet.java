package wallets;

import assets.Asset;
import assets.crypto.CryptoAsset;
import database.DataBase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class CryptoWallet extends Wallet {

    protected CryptoWallet(int id) {
        super(id, "CryptoWallets");
    }

    @Override
    protected void setAssets() {
        super.loadTransactions("CryptoTransactions");
        super.assets = CryptoAsset.getCryptos(super.transactions);
        Collections.reverse(assets);
    }

    @Override
    public ArrayList<? extends Asset> getAssets() {
        setAssets();
        return assets;
    }

//  STATIC
    private static final ArrayList<Wallet> wallets = new ArrayList<>();

    private static void setWallets() {
		for (int id : DataBase.getAllWalletsIDs("CryptoWallets")) wallets.add(new CryptoWallet(id));
    }
    public static void createWallet(String name) {
        DataBase.createWallet(name, "CryptoWallets");
    }

    public static ArrayList<Wallet> getWallets() {
        wallets.clear();
        setWallets();
        return wallets;
    }

    public static double getWalletsTotal(ArrayList<Wallet> wallets) {
        double tot = 0;
        for (Wallet wallet : wallets) tot += Double.parseDouble(wallet.getTotal());
        return tot;
    }

    public static String getWalletsTotalString(String symbol, ArrayList<Wallet> wallets) {
        DecimalFormat val = new DecimalFormat("0.00");
        return symbol+val.format(getWalletsTotal(wallets));
    }
}
