package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.*;

import pages.Page;
import pages.PageCrypto;



public class Screen extends JPanel {

	private static final long serialVersionUID = 1L;
	public LeftPanel leftPanel = new LeftPanel();
	public Page pageCrypto = new PageCrypto();


//	private void test(JPanel k) {
//		k.addMouseListener(this);
//		k.addMouseMotionListener(this);
//	}

	public Screen() {
		this.setBackground(new Color(0x696969));
		this.setLayout(new BorderLayout());

		this.add(leftPanel, BorderLayout.WEST);
		this.add(pageCrypto, BorderLayout.CENTER);



	}
	


//	@Override
//	public void mouseDragged(MouseEvent e) {
//		leftPanel.resizePanel(e);
//	}
//
//	@Override
//	public void mouseMoved(MouseEvent e) {
//		Point point = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), this);
//		int x = point.x;
//		int y = point.y;
//
//		System.out.println("Mouse moved to (" + x + ", " + y + ")");
//
////		System.out.println(e.getX());
////		leftPanel.mouseOnBorder(e);
//
//	}
//
//	@Override
//	public void mouseClicked(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//
//
//	}
//
//	@Override
//	public void mouseEntered(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void mouseExited(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void mousePressed(MouseEvent e) {
////		leftPanel.setMoving(true);
//	}
//
//	@Override
//	public void mouseReleased(MouseEvent e) {
////		leftPanel.setMoving(false);
//	}
}
