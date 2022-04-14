package cs499.screens;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.util.ArrayList;

import cs499.Quiz;
import cs499.gui_utils.FrameBuilder;
import cs499.utils.DataUtils;

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
        final JButton back_btn = new JButton("<- Back");
        
        //Create Back Action Handler
		class BackAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				new WelcomeScreen();
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));;
			}
		}
		back_btn.addActionListener(new BackAction());

		//Obtain a list of all quizzes in the database
		ArrayList<Quiz> quiz_list = DataUtils.getAllQuizzes();

		JPanel quiz_list_listing = new JPanel();
		quiz_list_listing.setLayout(new GridBagLayout());
		GridBagConstraints quiz_list_constraints = new GridBagConstraints();
		quiz_list_constraints.fill = GridBagConstraints.BOTH;
		
		for (int i = 0; i < quiz_list.size(); i++){
			final int quiz_index = i;
			String quiz_name = quiz_list.get(quiz_index).getName();

			JButton quiz_btn = new JButton(quiz_name);
			class SelectQuizAction implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					new EditQuizScreen(quiz_list.get(quiz_index));
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				}
			}
			quiz_btn.addActionListener(new SelectQuizAction());

			JPanel quiz_panel = new JPanel();
			if (i == 0) {
				quiz_list_constraints.gridx = 0;
				quiz_list_constraints.weightx = 0.1;
				quiz_list_constraints.gridy = 0;
				quiz_list_constraints.weighty = 0.1;
				quiz_list_listing.add(back_btn, quiz_list_constraints);
			}
		}
		
		//Create Constraints Guide
		GridBagConstraints constraints = new GridBagConstraints();
        
        //Add elements to frame
        constraints.fill = GridBagConstraints.BOTH;

        //Use a 4 column content area, along with 3 interior dividers, 2 exterior dividers, 2 different buttons, and padding on each side; 4+3+2+2+2 = 13.
        //Use as many rows as are needed for tests + 1 row for labels, 1 row for padding below; x+1+1 = x+2.

        //Add padding on each side, each worth 1.875%;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 0;
		constraints.weightx = 1;
		constraints.gridy = 0;
		constraints.weighty = 1;
		frame.add(new JScrollPane(quiz_list_listing), constraints);

        frame.setVisible(true);
    }
}
