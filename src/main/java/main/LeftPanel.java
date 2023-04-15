package main;

import assets.Asset;
import assets.FiatAsset;
import main.Screen;
import pages.Page;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;

public class LeftPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private boolean moving;
	private boolean onBorder;
	private final JPanel totalMoneyPanel = new JPanel(new GridBagLayout());
	private final ArrayList<FiatAsset> fiatAssets = FiatAsset.getFiatList();
	
	
	public LeftPanel() {
		JPanel kkk = new JPanel(new GridLayout(5, 1));
		this.setTotalMoneyPanel();
		this.setPreferredSize(new Dimension(150, 0));
		this.setLayout(new GridLayout(2, 1));
		this.add(kkk);
		this.add(selectCurrency());
		kkk.add(totalMoneyPanel);
		kkk.add(typeOfPortfolio("Crypto"));
		kkk.add(typeOfPortfolio("Stocks"));
		kkk.add(typeOfPortfolio("Bank"));


	}

	private JComboBox<Asset> pairComboBox(ArrayList<? extends Asset> assets) {
		JComboBox<Asset> comboBox = new JComboBox<>(assets.toArray(new Asset[0]));
//		Page.fiat = Objects.requireNonNull(comboBox.getSelectedItem()).toString();
		comboBox.addActionListener(e -> {
			Page.fiat = comboBox.getSelectedItem().toString();
//			Screen.pageCrypto.update();
			Screen.pages[0].update();
			setTotalMoneyPanel();
		});

		for (Asset asset : assets) {
			if (asset.getSymbol().equals(Page.fiat)) {
				comboBox.setSelectedItem(asset);
				break;
			}
		}

		return comboBox;
	}

	private JPanel selectCurrency() {
		JPanel panel = new JPanel(new BorderLayout());

		JComboBox<Asset> comboBox = pairComboBox(fiatAssets);

		panel.add(comboBox, BorderLayout.SOUTH);


		return panel;
	}

	public void setTotalMoneyPanel() {
		double total = 0;
		total += Screen.pages[0].total;
		String symbol = new FiatAsset(Page.fiat).getCurrencySymbol();
//		for (int i = 0; i < Screen.pages.length; i++) {
//			total += Screen.pages[i].total;
//		}
//		for (Page page : Screen.pages) {
//			total += page.total;
//		}
		DecimalFormat val = new DecimalFormat("#.00");
		totalMoneyPanel.removeAll();
		totalMoneyPanel.setBackground(new Color(0x22ee23));
		totalMoneyPanel.add(new JLabel(symbol + val.format(total)));
		totalMoneyPanel.revalidate();
		totalMoneyPanel.repaint();
	}
	
	public JButton typeOfPortfolio(String name) {
		return new JButton(name);
	}
	
	public void resizePanel(MouseEvent e) {
		if (moving && onBorder) {
			Dimension d = new Dimension(e.getX(), 0);
			setPreferredSize(d);
			revalidate();
		}
	}
	
	public void mouseOnBorder(MouseEvent e) {
		if (e.getX() < getPreferredSize().width && e.getX() > getPreferredSize().width - 15) {
			onBorder = true;
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		} else {
			onBorder = false;
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	
	
}