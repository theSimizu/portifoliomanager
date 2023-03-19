package pages;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import database.Asset;
import database.Wallet;

public abstract class Page extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private final JPanel portifoliosPanel = new JPanel();
	private final JScrollPane portifoliosPanelScrollPane;
	private Component rigidArea;


	public Page() {
		setLayout(new BorderLayout());
		portifoliosPanelSettings();
		updatePortifolioPanel();
		JPanel totalMoneyPanel = genTotalMoneyPanel();
		portifoliosPanelScrollPane = genPortifoliosPanelScrollPane();
		add(totalMoneyPanel, BorderLayout.NORTH);
		add(portifoliosPanelScrollPane, BorderLayout.CENTER);
	}

	public void update() {
		updatePortifolioPanel();
		portifoliosPanel.revalidate();
		portifoliosPanel.repaint();
	}
	
	public JPanel genTotalMoneyPanel() {
		JLabel label = new JLabel("R$0000,00");
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0xf01e23));
		panel.setPreferredSize(new Dimension(0, 60));
		panel.add(label);
		return panel;
	}

	private void portifoliosPanelSettings() {
		portifoliosPanel.setLayout(new BoxLayout(portifoliosPanel, BoxLayout.Y_AXIS));
		portifoliosPanel.setBackground(Color.DARK_GRAY);
		rigidArea = new JPanel();
		rigidArea.setPreferredSize(new Dimension(0, 900));
		rigidArea.setBackground(null);
	}

	private void updatePortifolioPanel() {
		ArrayList<Wallet> wallets = Wallet.getWallets();
		portifoliosPanel.removeAll();
		for (Wallet wallet : wallets) portifoliosPanel.add(getWalletBoxPanel(wallet));
		portifoliosPanel.add(newWalletButton());
		portifoliosPanel.add(rigidArea);
	}

	private JScrollPane genPortifoliosPanelScrollPane() {
		JScrollPane scroll = new JScrollPane(portifoliosPanel);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.getVerticalScrollBar().isVisible();
		return scroll;
	}

	private JPanel newWalletButton() {
		JPanel newWalletPanel = new JPanel();
		JLabel plusLabel = new JLabel("+");
		JButton newWalletAction = new JButton();

		plusLabel.setFont(new Font("Arial", Font.BOLD, 20));
		newWalletAction.setLayout(new GridBagLayout());
		newWalletAction.add(plusLabel);
		newWalletAction.addActionListener(e -> new PageNewWallet(this));

		newWalletPanel.setLayout(new BorderLayout());
		newWalletPanel.add(newWalletAction, BorderLayout.CENTER);
		newWalletPanel.setPreferredSize(new Dimension(0, 40));
		return newWalletPanel;
	}

	private JPanel getCoinBoxPanel(Asset asset) {
		asset.convertTo("EUR");
		JPanel coinBoxPanel = new JPanel();
		JPanel coinImgPanel = new JPanel();
		JPanel coinInfoPanel = new JPanel();

		JLabel coinNameLabel = new JLabel(asset.getName()); coinNameLabel.setForeground(Color.white);
		JLabel coinAmountLabel = new JLabel(Double.toString(asset.getAmount())); coinAmountLabel.setForeground(Color.white);
		JLabel coinProfitLabel = new JLabel("Placeholder"); coinProfitLabel.setForeground(Color.white);
		JLabel coinTotalLabel = new JLabel(asset.getPair().getCurrencySymbol() + asset.getTotalString()); coinTotalLabel.setForeground(Color.white);

		coinImgPanel.setBackground(null);
		coinImgPanel.setPreferredSize(new Dimension(100, 0));

		coinInfoPanel.setLayout(new GridLayout(2, 2));
		coinInfoPanel.add(coinNameLabel);
		coinInfoPanel.add(coinProfitLabel);
		coinInfoPanel.add(coinAmountLabel);
		coinInfoPanel.add(coinTotalLabel);
		coinInfoPanel.setBackground(null);

		coinBoxPanel.setLayout(new BorderLayout());
		coinBoxPanel.add(coinImgPanel, BorderLayout.WEST);
		coinBoxPanel.add(coinInfoPanel, BorderLayout.CENTER);
		coinBoxPanel.setPreferredSize(new Dimension(0, 70));
		coinBoxPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(0X6a6a6a)));
		coinBoxPanel.setBackground(new Color(0X4a4a4a));
		return coinBoxPanel;
	}

	private JPanel getWalletBoxPanelHeader(Wallet wallet, JPanel coinsBoxListPanel) {
		JPanel headerPanel = new JPanel();
		JButton headerAction = new JButton();

		headerPanel.setLayout(new BorderLayout());
		headerPanel.setBackground(Color.gray);
		headerPanel.setPreferredSize(new Dimension(0, 30));
		headerPanel.add(headerAction, BorderLayout.CENTER);

		headerAction.setLayout(new GridBagLayout());
		headerAction.setBackground(null);
		headerAction.add(new JLabel(wallet.getName()));
		headerAction.addActionListener(e -> {
			showAndHideWalletBody(coinsBoxListPanel);
			correctHeight();
		});

		return headerPanel;
	}

	private JPanel getWalletBoxPanel(Wallet wallet) {
		JPanel walletBoxPanel = new JPanel();

		JPanel coinsBoxListPanel = walletCoinsListPanel(wallet, walletBoxPanel);
		JPanel headerPanel = getWalletBoxPanelHeader(wallet, coinsBoxListPanel);

		coinsBoxListPanel.setVisible(false);
		walletBoxPanel.setLayout(new BorderLayout());
		walletBoxPanel.add(headerPanel, BorderLayout.NORTH);
		walletBoxPanel.add(coinsBoxListPanel, BorderLayout.CENTER);

		return walletBoxPanel;
	}


	private JPanel walletCoinsListPanel(Wallet wallet, JPanel test) {
		JPanel coinsBoxListPanel = new JPanel();
		coinsBoxListPanel.setLayout(new BoxLayout(coinsBoxListPanel, BoxLayout.PAGE_AXIS));
		coinsBoxListPanel.add(newTransactionButton(wallet, test));
		for (Asset asset : wallet.getCoins()) coinsBoxListPanel.add(getCoinBoxPanel(asset));
		return coinsBoxListPanel;

	}



	private JPanel newTransactionButton(Wallet wallet, JPanel test) {
		JPanel panel = new JPanel();
		JButton button = new JButton("New Transaction");
		button.addActionListener(e -> new PageNewTransaction(wallet, test));

		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(0, 35));
		panel.setBackground(new Color(0x999999));
		panel.add(button);

		button.setBackground(null);

		return panel;
	}


	
	public void showAndHideWalletBody(JPanel body) {
		body.setVisible(!body.isVisible());
	}

	public void correctHeight() {
		int totalHeight = 0;
		for (Component panel : portifoliosPanelScrollPane.getComponents()) {
			if (!(panel instanceof JPanel)) break;
			totalHeight+=panel.getHeight();
		}
		int diff = portifoliosPanelScrollPane.getHeight() - totalHeight;
		if (totalHeight > 0 && diff > 0) {
			rigidArea.setPreferredSize(new Dimension(0, diff));
			rigidArea.revalidate();
		}
	}

	public void pageAdjusts() {

	}

}
