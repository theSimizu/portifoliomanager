package main;

import java.awt.Rectangle;
import javax.swing.JFrame;


public class Main{
	
	private static int scale=2;
	private static int width=420*scale, height=420*scale;

	public static void main(String[] args) {
		Screen screen = new Screen();
		JFrame frame = generateFrame();
		
		
		frame.add(screen);
	}
	
	public static JFrame generateFrame() {
		JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(new Rectangle(width, height));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		return frame;
	}

	
	

}
