package pages.components;

import assets.Asset;
import assets.FiatAsset;
import wallets.Wallet;
import pages.Page;
import pages.windows.WindowNewTransaction;

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
        this.setBody(false);
        this.add(header, BorderLayout.NORTH);
        this.add(body, BorderLayout.CENTER);

    }

    public Wallet getWallet() {
        return this.wallet;
    }

    private void setHeader() {
        JButton headerAction = new JButton();
        headerAction.setLayout(new BorderLayout());
        header.setBackground(Color.gray);
        header.setPreferredSize(new Dimension(0, 30));
        header.add(headerAction, BorderLayout.CENTER);

        headerAction.setLayout(new GridBagLayout());
        headerAction.setBackground(null);
        headerAction.add(new JLabel(wallet.getName()));
        headerAction.addActionListener(e -> { body.setVisible(!body.isVisible()); });
    }



    private JPanel totalDataBox() {
//        System.out.println(wallet.getTotal());
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout());
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 0;
        panel.add(new JLabel("Total:"));
        gbc.gridy+=1;
        String symb = new FiatAsset(Page.fiat).getCurrencySymbol();
        panel.add(new JLabel(symb+wallet.getTotal()), gbc);
        panel.setPreferredSize(new Dimension(0, 50));
        panel.setBackground(null);

        return panel;
    }

    public void setBody() {
        body.removeAll();
        body.setLayout(new BoxLayout(body, BoxLayout.PAGE_AXIS));
        body.add(transactionButton);
        for (Asset asset : wallet.getAssets()) body.add(coinDataBox(asset));
        body.add(totalDataBox());
        body.revalidate();
        body.repaint();
    }

    public void setBody(boolean visible) {
        body.setVisible(visible);
        setBody();
    }

    private void setTransactionButton() {
        JButton button = new JButton("New Transaction");
        button.addActionListener(e -> new WindowNewTransaction(this));
        button.setBackground(null);

        transactionButton.setPreferredSize(new Dimension(0, 35));
        transactionButton.setBackground(new Color(0x999999));
        transactionButton.add(button);
    }

    private JPanel coinDataBox(Asset asset) {
        asset.convertTo(Page.fiat);

//      ICON //////////////////////////////////////////////////////////////////////////////////
        JPanel coinImgPanel = new JPanel();
        coinImgPanel.setBackground(null);
        coinImgPanel.setPreferredSize(new Dimension(100, 0));

        ImageIcon imageIcon = new ImageIcon("icons/" + asset.getId() + ".png");
        Image newImg = imageIcon.getImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);

        coinImgPanel.add(new JLabel(new ImageIcon(newImg)));

//      COIN INFO //////////////////////////////////////////////////////////////////////////////
        JPanel coinInfoPanel = new JPanel();
        coinInfoPanel.setLayout(new GridLayout(2, 2));
        coinInfoPanel.setBackground(null);
        Font font = new Font("arial", Font.PLAIN, 18);

        JLabel coinNameLabel = new JLabel(asset.getName());
        coinNameLabel.setForeground(Color.white);
        coinNameLabel.setFont(font);

        JLabel coinAmountLabel = new JLabel("<html>" + (asset.getAmount()) +"<font color=gray>" + " (" + asset.getCurrentUnitaryConvertedValueString() + ")" + "</font></html>");
        coinAmountLabel.setForeground(Color.white);
        coinAmountLabel.setFont(font);

        JLabel coinProfitLabel = new JLabel("Placeholder");
        coinProfitLabel.setForeground(Color.white);
        coinProfitLabel.setFont(font);

        JLabel coinTotalLabel = new JLabel(asset.getCurrentTotalConvertedValueString());
//        JLabel coinTotalLabel = new JLabel(asset.getCurrentTotalConvertedValueString());
        coinTotalLabel.setForeground(Color.white);
        coinTotalLabel.setFont(font);

        coinInfoPanel.add(coinNameLabel);
        coinInfoPanel.add(coinProfitLabel);
        coinInfoPanel.add(coinAmountLabel);
        coinInfoPanel.add(coinTotalLabel);

//      MAIN PANEL ////////////////////////////////////////////////////////////////////////////////////////////
        JPanel coinBoxPanel = new JPanel();
        coinBoxPanel.setLayout(new BorderLayout());
        coinBoxPanel.setPreferredSize(new Dimension(0, 70));
        coinBoxPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(0X6a6a6a)));
        coinBoxPanel.setBackground(new Color(0X3c3c3c));

        coinBoxPanel.add(coinImgPanel, BorderLayout.WEST);
        coinBoxPanel.add(coinInfoPanel, BorderLayout.CENTER);

        return coinBoxPanel;
    }

}
