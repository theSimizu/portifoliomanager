package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LeftPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private boolean moving;
	private boolean onBorder;
	private final JPanel totalMoneyPanel = new JPanel(new GridBagLayout());

	
	
	public LeftPanel() {
//		this.setMinimumSize(new Dimension(100, 0));
		this.setTotalMoneyPanel();
		this.setPreferredSize(new Dimension(200, 0));
		this.setLayout(new GridLayout(10, 1));
		this.add(totalMoneyPanel);
		this.add(typeOfPortfolio("Crypto"));
		this.add(typeOfPortfolio("Stocks"));
		this.add(typeOfPortfolio("Bank"));


	}
	
	public void setTotalMoneyPanel() {
		totalMoneyPanel.setBackground(new Color(0x22ee23));
		totalMoneyPanel.add(new JLabel("R$0000,00"));
		
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
		System.out.println(e.getX());
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