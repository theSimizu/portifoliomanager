package pages;

import database.Asset;
import database.Wallet;

import javax.swing.*;
import java.awt.*;

public class WalletBoxPanel extends JPanel {
    private final Wallet wallet;
    private final JPanel header = new JPanel(new BorderLayout());
    private final JPanel body = new JPanel();
    private final JPanel transactionButton = new JPanel(new BorderLayout());

    public WalletBoxPanel(Wallet wallet) {
        this.wallet = wallet;
        this.setLayout(new BorderLayout());
        this.setTransactionButton();
        this.setHeader();
        this.setBody();
        this.add(header, BorderLayout.NORTH);
        this.add(body, BorderLayout.CENTER);
    }

    public Wallet getWallet() {
        return this.wallet;
    }

    private void setHeader() {
        JButton headerAction = new JButton();
        header.setBackground(Color.gray);
        header.setPreferredSize(new Dimension(0, 30));
        header.add(headerAction, BorderLayout.CENTER);

        headerAction.setLayout(new GridBagLayout());
        headerAction.setBackground(null);
        headerAction.add(new JLabel(wallet.getName()));
        headerAction.addActionListener(e -> { body.setVisible(!body.isVisible()); });
    }

    public void setBody() {
        body.setVisible(false);
        body.setLayout(new BoxLayout(body, BoxLayout.PAGE_AXIS));
        body.add(transactionButton);
        for (Asset asset : wallet.getCoins()) body.add(coinDataBox(asset));

    }
    public void updateBody() {
        body.removeAll();
        body.setVisible(true);
        body.add(transactionButton);
        for (Asset asset : wallet.getCoins()) body.add(coinDataBox(asset));
        body.revalidate();
        body.repaint();
    }

    private void setTransactionButton() {
        JButton button = new JButton("New Transaction");
        button.addActionListener(e -> new PageNewTransaction(this));
        button.setBackground(null);

        transactionButton.setPreferredSize(new Dimension(0, 35));
        transactionButton.setBackground(new Color(0x999999));
        transactionButton.add(button);
    }

    private JPanel coinDataBox(Asset asset) {
        asset.convertTo("EUR");

//      ICON //////////////////////////////////////////////////////////////////////////////////
        JPanel coinImgPanel = new JPanel();
        coinImgPanel.setBackground(null);
        coinImgPanel.setPreferredSize(new Dimension(100, 0));

//      COIN INFO //////////////////////////////////////////////////////////////////////////////
        JPanel coinInfoPanel = new JPanel();
        coinInfoPanel.setLayout(new GridLayout(2, 2));
        coinInfoPanel.setBackground(null);


        JLabel coinNameLabel = new JLabel(asset.getName());
        coinNameLabel.setForeground(Color.white);

        JLabel coinAmountLabel = new JLabel(Double.toString(asset.getAmount()));
        coinAmountLabel.setForeground(Color.white);

        JLabel coinProfitLabel = new JLabel("Placeholder");
        coinProfitLabel.setForeground(Color.white);

        String totalText = asset.getPair().getCurrencySymbol() + asset.getTotalString();
        JLabel coinTotalLabel = new JLabel(totalText);
        coinTotalLabel.setForeground(Color.white);

        coinInfoPanel.add(coinNameLabel);
        coinInfoPanel.add(coinProfitLabel);
        coinInfoPanel.add(coinAmountLabel);
        coinInfoPanel.add(coinTotalLabel);

//      MAIN PANEL ////////////////////////////////////////////////////////////////////////////////////////////
        JPanel coinBoxPanel = new JPanel();
        coinBoxPanel.setLayout(new BorderLayout());
        coinBoxPanel.setPreferredSize(new Dimension(0, 70));
        coinBoxPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(0X6a6a6a)));
        coinBoxPanel.setBackground(new Color(0X4a4a4a));

        coinBoxPanel.add(coinImgPanel, BorderLayout.WEST);
        coinBoxPanel.add(coinInfoPanel, BorderLayout.CENTER);

        return coinBoxPanel;
    }

//    private JPanel getWalletBoxPanelHeader(Wallet wallet, JPanel coinsBoxListPanel) {
//        JPanel headerPanel = new JPanel();
//        JButton headerAction = new JButton();
//
//        headerPanel.setLayout(new BorderLayout());
//        headerPanel.setBackground(Color.gray);
//        headerPanel.setPreferredSize(new Dimension(0, 30));
//        headerPanel.add(headerAction, BorderLayout.CENTER);
//
//        headerAction.setLayout(new GridBagLayout());
//        headerAction.setBackground(null);
//        headerAction.add(new JLabel(wallet.getName()));
//        headerAction.addActionListener(e -> {
////            showAndHideWalletBody(coinsBoxListPanel);
////            correctHeight();
//        });
//
//        return headerPanel;
//    }
//
//    private JPanel getWalletBoxPanel(Wallet wallet) {
//        JPanel walletBoxPanel = new JPanel();
//
//        JPanel coinsBoxListPanel = walletCoinsListPanel(wallet, walletBoxPanel);
//        JPanel headerPanel = getWalletBoxPanelHeader(wallet, coinsBoxListPanel);
//
//        coinsBoxListPanel.setVisible(false);
//        walletBoxPanel.setLayout(new BorderLayout());
//        walletBoxPanel.add(headerPanel, BorderLayout.NORTH);
//        walletBoxPanel.add(coinsBoxListPanel, BorderLayout.CENTER);
//
//        return walletBoxPanel;
//    }
//
//
//    private JPanel walletCoinsListPanel(Wallet wallet, JPanel test) {
//        JPanel coinsBoxListPanel = new JPanel();
//        coinsBoxListPanel.setLayout(new BoxLayout(coinsBoxListPanel, BoxLayout.PAGE_AXIS));
////        coinsBoxListPanel.add(newTransactionButton(wallet, test));
//        for (Asset asset : wallet.getCoins()) coinsBoxListPanel.add(coinDataBox(asset));
//        return coinsBoxListPanel;
//
//    }
}
