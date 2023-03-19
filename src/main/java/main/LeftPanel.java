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
	public boolean moving;
	public boolean onBorder;
	
	
	public LeftPanel() {
		this.setPreferredSize(new Dimension(200, 0));
		this.setLayout(new GridLayout(10, 1));
		this.add(genTotalMoneyPanel());
		this.add(typeOfPortifolio("Crypto"));
		this.add(typeOfPortifolio("Stocks"));
		this.add(typeOfPortifolio("Bank"));


	}
	
	public JPanel genTotalMoneyPanel() {
		JLabel label = new JLabel("R$0000,00");
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(new Color(0x22ee23));
		panel.add(label);
		
		return panel;
	}
	
	public JButton typeOfPortifolio(String name) {
		JButton button = new JButton(name);
		return button;
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