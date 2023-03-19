package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import pages.Page;
import pages.PageCrypto;



public class Screen extends JPanel implements MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public LeftPanel leftPanel;
	public Page pageCrypto;


	
	public Screen() {
		setBackground(new Color(0x696969));
		setLayout(new BorderLayout());
		this.fullfil();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
//		buttons();
	}
	
	private void fullfil() {
		leftPanel = new LeftPanel();
		pageCrypto = new PageCrypto();
		pageCrypto.pageAdjusts();
		this.add(leftPanel, BorderLayout.WEST);
		this.add(pageCrypto, BorderLayout.CENTER);
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		leftPanel.resizePanel(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		leftPanel.mouseOnBorder(e);
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		leftPanel.setMoving(true);
	}

	@Override
	public void mouseReleased(MouseEvent e) { leftPanel.setMoving(false); }
}
