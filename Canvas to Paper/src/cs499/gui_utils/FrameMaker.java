package cs499.gui_utils;

import javax.swing.JFrame;
import java.awt.Image;
import java.awt.Toolkit;

public class FrameMaker {
    public JFrame frameMake(String title, int close_behavior, int width, int height, String icon_path) {
        //Initialize the frame
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(close_behavior); // EXIT_ON_CLOSE terminates the entire program, not just the window
		frame.setSize(width, height);

		//Create an image of the icon path and set the icon of the Welcome Screen frame
		Image frame_icon = Toolkit.getDefaultToolkit().createImage(icon_path);
		frame.setIconImage(frame_icon);

        return frame;
    }
}
