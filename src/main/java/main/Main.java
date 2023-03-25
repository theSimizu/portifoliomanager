package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;


public class Main extends JFrame {
	
	private static final int scale=2;
	private static final int width=420*scale;
	private static final int height=420*scale;

	public static void main(String[] args) {
		Screen screen = new Screen();
//		JFrame frame = generateFrame();
		Main frame = new Main();
		generateFrame(frame);


		frame.add(screen);

	}
	
//	public static JFrame generateFrame() {
//		JFrame frame = new JFrame();
//
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setBounds(new Rectangle(width, height));
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
//		return frame;
//	}

	public static void generateFrame(Main frame) {
//		JFrame frame = new JFrame();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(new Rectangle(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}



}
