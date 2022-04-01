package cs499.screens;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import cs499.gui_utils.FrameBuilder;

public class SelectQuizScreen {
    private JFrame frame;
    public SelectQuizScreen(){
        String frame_title = "CS 499-01 Spring 2022 CtPP Project Prototype-01";
        String logo_icon_path = "Canvas to Paper/lib/images/logo_icon.png";

        //Initialize the frame
        FrameBuilder maker = new FrameBuilder();
		frame = maker.buildFrame(
			frame_title, 
			JFrame.DISPOSE_ON_CLOSE, 
			500, 400, 
			null, 
			logo_icon_path
		);
        frame.setLayout(new GridBagLayout());

        //Create Back Button
        JButton back_btn = new JButton("<- Back");
        
        //Create Back Action Handler
		class BackAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				new WelcomeScreen();
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));;
			}
		}
		back_btn.addActionListener(new BackAction());

        //Create Constraints Guide
		GridBagConstraints constraints = new GridBagConstraints();
        
        //Add elements to frame
        constraints.fill = GridBagConstraints.BOTH;

        //Use a 4 column content area, along with 3 interior dividers, 2 exterior dividers, 2 different buttons, and padding on each side; 4+3+2+2+2 = 13.
        //Use as many rows as are needed for tests + 1 row for labels, 1 row for padding below; x+1+1 = x+2.

        //Add padding on each side, each worth 1.875%;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0.01875;
		constraints.weighty = 0;
		frame.add(new JLabel(), constraints);

        constraints.gridx = 12;
		constraints.gridy = 1;
		constraints.weightx = 0.01875;
		constraints.weighty = 0;
		frame.add(new JLabel(), constraints);
        
        //Add back button
        constraints.gridx = 12;
		constraints.gridy = 1;
		constraints.weightx = 0.01875;
		constraints.weighty = 0;
        frame.add(back_btn, constraints);

        frame.setVisible(true);
    }
}
