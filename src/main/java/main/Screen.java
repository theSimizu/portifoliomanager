package main;

import java.awt.*;
import javax.swing.*;

import pages.Page;
import pages.PageCrypto;

public class Screen extends JPanel {

	private static final long serialVersionUID = 1L;
//	public static LeftPanel leftPanel = new LeftPanel();
	public static Page[] pages = {new PageCrypto()};
//	public static Page pageCrypto = new PageCrypto();
	public static LeftPanel leftPanel = new LeftPanel();


	public Screen() {
		this.setBackground(new Color(0x696969));
		this.setLayout(new BorderLayout());
		this.add(leftPanel, BorderLayout.WEST);
//		this.add(pageCrypto, BorderLayout.CENTER);
		this.add(pages[0], BorderLayout.CENTER);
	}

}
