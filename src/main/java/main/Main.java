package main;

import java.awt.*;
import javax.swing.*;


public class Main extends JFrame {
	
	private static final int scale=2;
	private static final int width=420*scale;
	private static final int height=420*scale;
	public static Screen screen;

	public static void main(String[] args) {
		screen = new Screen();
		Main frame = new Main();
		generateFrame(frame);
		frame.add(screen);
	}

	private static void generateFrame(Main frame) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(new Rectangle(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}



}
