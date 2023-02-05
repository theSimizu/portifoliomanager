package pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Spring;
import javax.swing.SpringLayout;

import database.Wallet;

public abstract class Page extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JScrollPane portifoliosPanel;
	private JPanel totalMoneyPanel;
	private Component rigidArea;
	private ArrayList<JPanel> walletPanels = new ArrayList<JPanel>();
	private ArrayList<Wallet> wallets = Wallet.getWallets();

	public Page() {
		setLayout(new BorderLayout());
		totalMoneyPanel = genTotalMoneyPanel();
		portifoliosPanel = genPortifoliosPanel();
		add(totalMoneyPanel, BorderLayout.NORTH);
		add(portifoliosPanel, BorderLayout.CENTER);
	}
	
	public JPanel genTotalMoneyPanel() {
		JLabel label = new JLabel("R$0000,00");
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0xf01e23));
		panel.setPreferredSize(new Dimension(0, 60));
		panel.add(label);
		
		
		
		return panel;
	}
	
	public JScrollPane genPortifoliosPanel() {
		JPanel panel = new JPanel();
		JScrollPane scroll = new JScrollPane(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.DARK_GRAY);
		
		walletPanels.add(createWalletPanel());
		walletPanels.add(createWalletPanel());
		walletPanels.add(createWalletPanel());
		walletPanels.add(createWalletPanel());
		walletPanels.add(createWalletPanel());
		walletPanels.add(createWalletPanel());
		walletPanels.add(createWalletPanel());
		walletPanels.add(createWalletPanel());
		walletPanels.add(createWalletPanel());
		walletPanels.add(createWalletPanel());
		walletPanels.add(createWalletPanel());
//		walletPanels.add(walletsPanels());
//		walletPanels.add(walletsPanels());
//		walletPanels.add(walletsPanels());
//		walletPanels.add(walletsPanels());
//		walletPanels.add(walletsPanels());
		walletPanels.add(createWalletPanel());
		walletPanels.add(createWalletPanel());
		walletPanels.add(createWalletPanel());
		walletPanels.add(createWalletPanel());
		walletPanels.add(createWalletPanel());
		walletPanels.add(createWalletPanel());
		walletPanels.add(createWalletPanel());
		walletPanels.add(createWalletPanel());
		
		for (JPanel panel2 : walletPanels) {
			panel.add(panel2);
		}
		

//		rigidArea = Box.createRigidArea(new Dimension(0, 900));
		rigidArea = new JPanel();
		rigidArea.setPreferredSize(new Dimension(0, 900));
		rigidArea.setBackground(null);
		
		panel.add(rigidArea);
		
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.getVerticalScrollBar().isVisible();


		
		return scroll;
	}


	
	
	public JPanel createWalletPanel() {
		
		JPanel mainPanel = new JPanel();
		JPanel header = new JPanel();
		JButton headerAction = new JButton();
		JPanel coinBody = new JPanel();
		JPanel allCoinsBody = new JPanel();
		allCoinsBody.setLayout(new BoxLayout(allCoinsBody, BoxLayout.PAGE_AXIS));;
		
		header.setLayout(new GridLayout());
		header.setBackground(Color.gray);
		header.setPreferredSize(new Dimension(0, 30));
		header.add(headerAction);
		
		
		headerAction.setLayout(new GridBagLayout());
		headerAction.setBackground(null);
		headerAction.add(new JLabel("Electrum"));
		headerAction.addActionListener(e -> showAndHideWalletBody(coinBody));
		headerAction.addActionListener(e -> correctHeight());
		
		
		JLabel coinNameLabel = new JLabel("Bitcoin");
		JLabel coinAmountLabel = new JLabel("0000");
		JLabel coinProfitLabel = new JLabel("1111");
		JLabel coinTotalLabel = new JLabel("2222");
		
		
		JPanel coinImg = new JPanel();
		JPanel coinInfo = new JPanel();
		
		coinImg.setBackground(new Color(0x000000));
		coinImg.setPreferredSize(new Dimension(100, 0));
		coinInfo.setLayout(new GridLayout(2, 2));
		
		coinInfo.add(coinNameLabel);
		coinInfo.add(coinProfitLabel);
		coinInfo.add(coinAmountLabel);
		coinInfo.add(coinTotalLabel);

		coinBody.setLayout(new BorderLayout());
		coinBody.add(coinImg, BorderLayout.WEST);
		coinBody.add(coinInfo, BorderLayout.CENTER);
		coinBody.setBackground(Color.orange);
		coinBody.setPreferredSize(new Dimension(0, 70));
		coinBody.setVisible(false);


		allCoinsBody.add(coinBody);
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(header, BorderLayout.NORTH);
		mainPanel.add(allCoinsBody, BorderLayout.CENTER);
		mainPanel.setBackground(Color.cyan);
		return mainPanel;
	}
	//as
public void walletsPanels1() {
		for (Wallet wallet : wallets) {
			JPanel mainPanel = new JPanel();
			JPanel header = new JPanel();
			JPanel body = new JPanel();
			JButton headerAction = new JButton();
			
			header.setLayout(new GridLayout());
			header.setBackground(Color.gray);
			header.setPreferredSize(new Dimension(0, 30));
			header.add(headerAction);
			
			
			headerAction.setLayout(new GridBagLayout());
			headerAction.setBackground(null);
			headerAction.add(new JLabel("Electrum"));
			headerAction.addActionListener(e -> showAndHideWalletBody(body));
			headerAction.addActionListener(e -> correctHeight());
			
			body.add(new JLabel("00000"));
			body.setBackground(Color.orange);
			body.setPreferredSize(new Dimension(0, 70));
			body.setVisible(false);
			
			mainPanel.setLayout(new BorderLayout());

			mainPanel.add(header, BorderLayout.NORTH);
			mainPanel.add(body, BorderLayout.CENTER);
			mainPanel.setBackground(Color.cyan);
		}
	}
	
	public void showAndHideWalletBody(JPanel body) {
		body.setVisible(!body.isVisible());
		//aaaaaaaaaaaaaaaa
	}
	
	
	
	
	
	public void correctHeight() {
		int totalHeight = 0;
		for (JPanel panel : walletPanels) {
			totalHeight+=panel.getHeight();
		}
		
		int diff = portifoliosPanel.getHeight() - totalHeight;

		if (totalHeight > 0 && diff > 0) {
			rigidArea.setPreferredSize(new Dimension(0, diff+50)); 
			rigidArea.revalidate();
		}

	}
	


}
