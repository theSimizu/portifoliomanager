package wallets;

import assets.Asset;
import assets.bank.BankAsset;
import database.DataBase;
import pages.Page;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class BankWallet extends Wallet {

    protected BankWallet(int id) {
        super(id, "BankWallets");
    }

    @Override
    protected void setAssets() {
        super.loadTransactions("BankTransactions");
        super.assets = BankAsset.getAssets(super.transactions);
        super.setPair(Page.fiat);
        Collections.sort(assets);
        super.setTotal();
    }

//  STATIC
    private static final ArrayList<Wallet> wallets = new ArrayList<>();

    protected static void setWallets() {
        for (int id : DataBase.getAllWalletsIDs("BankWallets")) wallets.add(new BankWallet(id));
    }
    public static void createWallet(String name) {
        DataBase.createWallet(name, "BankWallets");
    }

    public static ArrayList<Wallet> getWallets() {
        wallets.clear();
        setWallets();
        return wallets;
    }

    public static double getWalletsTotal(ArrayList<Wallet> wallets) {
        double tot = 0;
        for (Wallet wallet : wallets) tot += Double.parseDouble(wallet.getTotalString());
        return tot;
    }

    public static String getWalletsTotalString(String symbol, ArrayList<Wallet> wallets) {
        DecimalFormat val = new DecimalFormat("0.00");
        return symbol+val.format(getWalletsTotal(wallets));
    }
}
