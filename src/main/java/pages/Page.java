package pages;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;

import assets.FiatAsset;
import assets.Wallet;
import assets.crypto.CryptoWallet;
import pages.components.WalletBoxPanel;

public abstract class Page extends JPanel {
	
	private static final long serialVersionUID = 1L;
	protected final JPanel portfoliosPanel = new JPanel();
	protected final JPanel totalMoneyPanel = new JPanel();
	protected final JPanel rigidArea = new JPanel();
	protected final JPanel newWalletButton = new JPanel(new BorderLayout());
	protected final JScrollPane portfoliosPanelScrollPane = new JScrollPane(portfoliosPanel);
	public double total;
	public static String fiat = "USD";
	protected static ArrayList<Wallet> wallets;

	public Page(ArrayList<Wallet> wallets) {
		Page.wallets = wallets;
		setLayout(new BorderLayout());
		setNewWalletButton();
		setPortfolioPanel();
		updateTotalMoneyPanel();
		add(totalMoneyPanel, BorderLayout.NORTH);
		add(portfoliosPanelScrollPane, BorderLayout.CENTER);
	}

	public String total() {
//		return "";
		DecimalFormat val = new DecimalFormat("#.00");
		double tot = 0;
		for (Wallet wallet : wallets) tot += Double.parseDouble(wallet.getTotal());
		return val.format(tot);
	}

	public void update() {
		generalUpdate();
		updateTotalMoneyPanel();
	}

	private void updateTotalMoneyPanel() {
//		total = Wallet.getWalletsTotal();
		total = CryptoWallet.getWalletsTotal();
		totalMoneyPanel.removeAll();
		totalMoneyPanel.setBackground(new Color(0xf01e23));
		totalMoneyPanel.setPreferredSize(new Dimension(0, 60));
		String symbol = new FiatAsset(Page.fiat).getCurrencySymbol();
		totalMoneyPanel.add(new JLabel(CryptoWallet.getWalletsTotalString(symbol)));
		totalMoneyPanel.revalidate();
		totalMoneyPanel.repaint();
	}

	private void generalUpdate() {
		for (Component comp : portfoliosPanel.getComponents()) {
			if (comp instanceof WalletBoxPanel) { ((WalletBoxPanel) comp).setBody(); }
		}
		portfoliosPanel.revalidate();
		portfoliosPanel.repaint();
	}

	public void newWalletUpdate() {

	}

//	public void newWalletUpdate() {
////		portfoliosPanel.removeAll();
////		wallets = Wallet.getWallets();
////		for (Object wallet : wallets) { portfoliosPanel.add(new WalletBoxPanel(wallet)); }
////		portfoliosPanel.add(newWalletButton);
////		portfoliosPanel.add(rigidArea);
////		portfoliosPanel.revalidate();
////		portfoliosPanel.repaint();
//	}
//
//	protected void newWalletUpdate(ArrayList<?> list) {
//		portfoliosPanel.removeAll();
//		wallets = list;
//		for (Object wallet : wallets) { portfoliosPanel.add(new WalletBoxPanel((Wallet) wallet)); }
//		portfoliosPanel.add(newWalletButton);
//		portfoliosPanel.add(rigidArea);
//		portfoliosPanel.revalidate();
//		portfoliosPanel.repaint();
//	}

	private void setPortfolioPanel() {
		portfoliosPanelScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		portfoliosPanelScrollPane.getVerticalScrollBar().isVisible();
		portfoliosPanelScrollPane.getVerticalScrollBar().addAdjustmentListener(e -> adjustRigidArea());
		portfoliosPanel.setLayout(new BoxLayout(portfoliosPanel, BoxLayout.Y_AXIS));
		portfoliosPanel.setBackground(Color.DARK_GRAY);
		rigidArea.setBackground(null);
		newWalletUpdate();
	}

	private void adjustRigidArea() {
		int tot = (int) portfoliosPanelScrollPane.getSize().getHeight();
		for (Component component : portfoliosPanel.getComponents()) tot -= (component != rigidArea) ? component.getPreferredSize().getHeight() : 0;
		rigidArea.setPreferredSize(new Dimension(0, Math.max(tot, 0)));
		portfoliosPanel.revalidate();
		portfoliosPanel.repaint();
	}
	
	protected void setNewWalletButton() {

	}

	protected void setNewWalletButton(ActionListener listener) {
		JLabel plusLabel = new JLabel("+"); plusLabel.setFont(new Font("Arial", Font.BOLD, 20));
		JButton newWalletButtonAction = new JButton();
		newWalletButtonAction.setLayout(new GridBagLayout());
		newWalletButtonAction.add(plusLabel);
		newWalletButtonAction.addActionListener(listener);
		newWalletButton.add(newWalletButtonAction, BorderLayout.CENTER);
		newWalletButton.setPreferredSize(new Dimension(0, 40));
	}

}
