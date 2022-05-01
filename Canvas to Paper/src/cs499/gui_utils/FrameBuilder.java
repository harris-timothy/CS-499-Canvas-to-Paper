package cs499.gui_utils;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import java.awt.Image;
import java.awt.Toolkit;

public class FrameBuilder {
    public JFrame buildFrame(String title, int close_behavior, int width, int height, JMenuBar menu_bar, String icon_path) {
        //Initialize the frame
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(close_behavior);
		frame.setSize(width, height);

		//Create an image of the icon path and set the icon of the frame
		Image frame_icon = Toolkit.getDefaultToolkit().createImage(icon_path);
		frame.setIconImage(frame_icon);

		//Set Menu Bar as Menu Bar
		frame.setJMenuBar(menu_bar);

        return frame;
    }
}
