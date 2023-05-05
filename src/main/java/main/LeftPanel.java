package main;

import assets.Asset;
import assets.FiatAsset;
import assets.data.FiatCoinsData;
import pages.Page;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;

public class LeftPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JPanel cardPanel;
	private final JPanel totalMoneyPanel = new JPanel(new GridBagLayout());
	private final JPanel selectCurrencyPanel = new JPanel(new BorderLayout());

	public LeftPanel(JPanel cardPanel) {
		JPanel leftPanel = new JPanel(new GridLayout(5, 1));
		this.totalMoneyPanel.add(new JLabel(""));
		this.cardPanel = cardPanel;
		this.setTotalMoneyPanel();
		this.setSelectCurrencyPanel();
		this.setPreferredSize(new Dimension(150, 0));
		this.setLayout(new GridLayout(2, 1));
		this.add(leftPanel);
		this.add(selectCurrencyPanel);
		this.totalMoneyPanel.setBackground(new Color(0x22ee23));
		leftPanel.add(totalMoneyPanel);
		leftPanel.add(typeOfPortfolio("Crypto"));
		leftPanel.add(typeOfPortfolio("Stocks"));
		leftPanel.add(typeOfPortfolio("Bank"));
	}

	public void update() {
		setTotalMoneyPanel();
	}

	private JComboBox<Asset> pairComboBox(ArrayList<? extends Asset> assets) {
		JComboBox<Asset> comboBox = new JComboBox<>(assets.toArray(new Asset[0]));
		comboBox.addActionListener(e -> {
			Page.fiat = Objects.requireNonNull(comboBox.getSelectedItem()).toString();
			for (Page page : Screen.pages) page.update();
			setTotalMoneyPanel();
		});
		for (Asset asset : assets) if (asset.getSymbol().equals(Page.fiat)) { comboBox.setSelectedItem(asset); break; }
		return comboBox;
	}

	private void setSelectCurrencyPanel() {
		JComboBox<Asset> comboBox = pairComboBox(FiatCoinsData.getFiats());
		selectCurrencyPanel.add(comboBox, BorderLayout.SOUTH);
	}

	private void setTotalMoneyPanel() {
		double total = 0;
		String symbol = new FiatAsset(Page.fiat).getCurrencySymbol();
		for (int i = 0; i < Screen.pages.length; i++) total += Screen.pages[i].total;
		DecimalFormat val = new DecimalFormat("0.00");
		JLabel comp = (JLabel) totalMoneyPanel.getComponents()[0];
		comp.setText(symbol + val.format(total));
		totalMoneyPanel.revalidate();
		totalMoneyPanel.repaint();
	}
	
	private JButton typeOfPortfolio(String name) {
		JButton button = new JButton(name);
		button.addActionListener(e -> {
			CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
			cardLayout.show(cardPanel, name);
		});
		return button;
	}
}