package main;

import java.awt.*;
import javax.swing.*;

import pages.Page;
import pages.PageBank;
import pages.PageCrypto;

public class Screen extends JPanel {

	private static final long serialVersionUID = 1L;
	public static Page[] pages = { new PageCrypto(), new PageBank() };
	public JPanel cardPanel;
	public static LeftPanel leftPanel;

	public Screen() {
		cardPanel = new JPanel(new CardLayout());
		leftPanel = new LeftPanel(cardPanel);
		this.setBackground(new Color(0x696969));
		this.setLayout(new BorderLayout());
		this.add(leftPanel, BorderLayout.WEST);
		cardPanel.add(pages[0], "Crypto");
		cardPanel.add(pages[1], "Bank");
		this.add(cardPanel, BorderLayout.CENTER);
	}

	public static void update() {
		for (Page page : pages) page.update();
		leftPanel.update();
	}

}
