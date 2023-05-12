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
    private final JPanel totalDataBox = new JPanel(new GridBagLayout());

    public WalletBoxPanel(Wallet wallet) {
        this.wallet = wallet;
        this.wallet.setPair(Page.fiat);
        this.setLayout(new BorderLayout());
        this.setTransactionButton();
        this.update(false);
        this.add(header, BorderLayout.NORTH);
        this.add(body, BorderLayout.CENTER);
    }

    private String getTotal() {
        String currencySymbol = new FiatAsset(Page.fiat).getCurrencySymbol();
        return currencySymbol + wallet.getTotalString();
    }

    public Wallet getWallet() {
        return this.wallet;
    }

    public void update() {
        setBody();
        setHeader();
        setTotalDataBox();
    }

    public void update(boolean visible) {
        setBody(visible);
        setHeader();
    }

    private JPanel coinDataBox(Asset asset) {
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

        JLabel coinNameLabel = new JLabel(asset.getName() + " (" + asset.getSymbol().toUpperCase() + ")");
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

    // SET COMPONENTS
    public void setHeader() {
        GridBagConstraints c = new GridBagConstraints();
        JButton headerAction = new JButton();
        headerAction.setLayout(new GridBagLayout());

        header.removeAll();
        header.setBackground(Color.gray);
        header.setPreferredSize(new Dimension(0, 50));
        header.add(headerAction, BorderLayout.CENTER);
        c.gridy = 0;
        headerAction.add(new JLabel(wallet.getName()), c);
        c.gridy += 1;
        headerAction.add(new JLabel(getTotal()), c);
        headerAction.setBackground(null);
        headerAction.addActionListener(e -> { body.setVisible(!body.isVisible()); });
        header.revalidate();
        header.repaint();
    }

    private void setBody() {
        body.removeAll();
        body.setLayout(new BoxLayout(body, BoxLayout.PAGE_AXIS));
        body.add(transactionButton);
        for (Asset asset : wallet.getAssets()) body.add(coinDataBox(asset));
        body.add(totalDataBox);
        body.revalidate();
        body.repaint();
    }

    private void setBody(boolean visible) {
        body.setVisible(visible);
        setBody();
    }

    private void setTotalDataBox() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 0;
        totalDataBox.removeAll();
        totalDataBox.add(new JLabel("Total:"));
        gbc.gridy+=1;
        totalDataBox.add(new JLabel(getTotal()), gbc);
        totalDataBox.setPreferredSize(new Dimension(0, 50));
        totalDataBox.setBackground(null);
        totalDataBox.revalidate();
        totalDataBox.repaint();
    }

    private void setTransactionButton() {
        JButton button = new JButton("New Transaction");
        button.addActionListener(e -> new WindowNewTransaction(this));
        button.setBackground(null);
        transactionButton.setPreferredSize(new Dimension(0, 35));
        transactionButton.setBackground(new Color(0x999999));
        transactionButton.add(button);
    }

}
