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
//	private boolean moving = false;
//	private boolean onBorder = false;
	public LeftPanel left;
	public Page pageCrypto;


	
	public Screen() {
		setBackground(new Color(0x696969));
		setLayout(new BorderLayout());
		this.fullfil();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
//		buttons();
	}
	
	public void fullfil() {
		left = new LeftPanel();
		pageCrypto = new PageCrypto();
		this.add(left, BorderLayout.WEST);
		this.add(pageCrypto, BorderLayout.CENTER);
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		left.resizePanel(e);	
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		left.mouseOnBorder(e);
		
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
		left.setMoving(true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		left.setMoving(false);	}
}
