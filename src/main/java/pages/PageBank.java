package pages;

import assets.FiatAsset;
import pages.components.WalletBoxPanel;
import wallets.BankWallet;
import wallets.CryptoWallet;
import wallets.Wallet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PageBank extends Page{
    private static final long serialVersionUID = 1L;

    public PageBank() {
        super();
    }

    @Override
    protected ArrayList<Wallet> getWallets() {
        return BankWallet.getWallets();
    }

    @Override
    public void update() {
        // PORTFOLIO PANEL
        for (Component comp : portfoliosPanel.getComponents()) {
            if (comp instanceof WalletBoxPanel) { ((WalletBoxPanel) comp).setBody(); }
        }
        portfoliosPanel.revalidate();
        portfoliosPanel.repaint();

        // TOTAL MONEY PANEL
        String symbol = new FiatAsset(Page.fiat).getCurrencySymbol();
        total = BankWallet.getWalletsTotal(wallets);
        totalMoneyPanel.removeAll();
        totalMoneyPanel.setBackground(new Color(0xf01e23));
        totalMoneyPanel.setPreferredSize(new Dimension(0, 60));
        totalMoneyPanel.add(new JLabel(BankWallet.getWalletsTotalString(symbol, wallets)));
        totalMoneyPanel.revalidate();
        totalMoneyPanel.repaint();
    }



}
