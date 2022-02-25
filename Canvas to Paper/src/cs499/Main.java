package cs499;

import javax.swing.JFrame;
import java.awt.Image;
import java.awt.Toolkit;

public class Main {

	public static void main(String[] args) {
		
		String main_frame_title = "CS 499-01 Spring 2022 CtPP Project Prototype-01";
		String main_frame_icon_path = "Canvas to Paper/lib/images/main_frame_icon.png";

		//Initialize the main frame of the application
		JFrame main_frame = new JFrame(main_frame_title);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // EXIT_ON_CLOSE terminates the entire program, not just the window
		main_frame.setSize(600, 400);

		//Create an image of the icon path and set the icon of the main frame
		Image main_frame_icon = Toolkit.getDefaultToolkit().createImage(main_frame_icon_path);
		main_frame.setIconImage(main_frame_icon);
		
		main_frame.setVisible(true);
		
	}

}
