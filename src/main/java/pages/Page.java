package pages;

import java.awt.*;
import javax.swing.*;
import database.Wallet;

public abstract class Page extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private final JPanel portfoliosPanel = new JPanel();
	private final JPanel totalMoneyPanel = new JPanel();
	private final JPanel newWalletButton = new JPanel(new BorderLayout());
	private final JScrollPane portfoliosPanelScrollPane = new JScrollPane(portfoliosPanel);
	private final JPanel rigidArea = new JPanel();

	public Page() {
		setLayout(new BorderLayout());
		setNewWalletButton();
		setTotalMoneyPanel();
		setPortfolioPanel();
		add(totalMoneyPanel, BorderLayout.NORTH);
		add(portfoliosPanelScrollPane, BorderLayout.CENTER);
	}


	private void setTotalMoneyPanel() {
		totalMoneyPanel.setBackground(new Color(0xf01e23));
		totalMoneyPanel.setPreferredSize(new Dimension(0, 60));
		totalMoneyPanel.add(new JLabel("R$0000,00"));
	}

	public void update() {
		portfoliosPanel.removeAll();
		for (Wallet wallet : Wallet.getWallets()) portfoliosPanel.add(new WalletBoxPanel(wallet));
		portfoliosPanel.add(newWalletButton);
		portfoliosPanel.add(rigidArea);
		portfoliosPanel.revalidate();
		portfoliosPanel.repaint();
	}

	private void setPortfolioPanel() {
		portfoliosPanelScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		portfoliosPanelScrollPane.getVerticalScrollBar().isVisible();
		portfoliosPanelScrollPane.getVerticalScrollBar().addAdjustmentListener(e -> adjustRigidArea());
		portfoliosPanel.setLayout(new BoxLayout(portfoliosPanel, BoxLayout.Y_AXIS));
		portfoliosPanel.setBackground(Color.DARK_GRAY);
		rigidArea.setBackground(null);
		update();
	}

	private void adjustRigidArea() {
		int tot = (int) portfoliosPanelScrollPane.getSize().getHeight();
		for (Component component : portfoliosPanel.getComponents()) tot -= (component != rigidArea) ? component.getPreferredSize().getHeight() : 0;
		rigidArea.setPreferredSize(new Dimension(0, Math.max(tot, 0)));
		portfoliosPanel.revalidate();
		portfoliosPanel.repaint();
	}
	
	private void setNewWalletButton() {
		JLabel plusLabel = new JLabel("+"); plusLabel.setFont(new Font("Arial", Font.BOLD, 20));

		JButton newWalletButtonAction = new JButton();
		newWalletButtonAction.setLayout(new GridBagLayout());
		newWalletButtonAction.add(plusLabel);
		newWalletButtonAction.addActionListener(e -> new PageNewWallet(this));

		newWalletButton.add(newWalletButtonAction, BorderLayout.CENTER);
		newWalletButton.setPreferredSize(new Dimension(0, 40));
	}

}
