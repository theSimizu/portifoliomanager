package pages;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.swing.*;

import assets.FiatAsset;
import wallets.Wallet;
import wallets.CryptoWallet;
import pages.components.WalletBoxPanel;
import pages.windows.WindowNewWallet;

public abstract class Page extends JPanel {

	private static final long serialVersionUID = 1L;
	protected final JPanel portfoliosPanel = new JPanel();
	protected final JPanel totalMoneyPanel = new JPanel();
	protected final JPanel rigidArea = new JPanel();
	protected final JPanel newWalletButton = new JPanel(new BorderLayout());
	protected final JScrollPane portfoliosPanelScrollPane = new JScrollPane(portfoliosPanel);
	public double total;
	public static String fiat = "BRL";
	protected ArrayList<Wallet> wallets;

	protected abstract ArrayList<Wallet> getWallets();
	public abstract void update();

	public Page() {
		this.wallets = this.getWallets();
		setBackground(new Color(0x01afc7));
		setLayout(new BorderLayout());
		setNewWalletButton();
		setPortfolioPanel();
		update();
		add(totalMoneyPanel, BorderLayout.NORTH);
		add(portfoliosPanelScrollPane, BorderLayout.CENTER);
	}

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
		JLabel plusLabel = new JLabel("+"); plusLabel.setFont(new Font("Arial", Font.BOLD, 20));
		JButton newWalletButtonAction = new JButton();
		newWalletButtonAction.setLayout(new GridBagLayout());
		newWalletButtonAction.add(plusLabel);
		newWalletButtonAction.addActionListener(e -> new WindowNewWallet(this));
		newWalletButton.add(newWalletButtonAction, BorderLayout.CENTER);
		newWalletButton.setPreferredSize(new Dimension(0, 40));
	}

	public void newWalletUpdate() {
		portfoliosPanel.removeAll();
		wallets = getWallets();
		for (Wallet wallet : wallets) { portfoliosPanel.add(new WalletBoxPanel(wallet)); }
		portfoliosPanel.add(newWalletButton);
		portfoliosPanel.add(rigidArea);
		portfoliosPanel.revalidate();
		portfoliosPanel.repaint();
	}

}