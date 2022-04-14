package cs499.screens;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import cs499.Quiz;
import cs499.gui_utils.FrameBuilder;

public class EditQuizScreen {
    private JFrame frame;
    public EditQuizScreen(Quiz quiz){
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
        GridBagConstraints constraints = new GridBagConstraints();

        JPanel quiz_info_panel = new JPanel();

        //TODO: Add quiz info to the quiz_info_panel

        JPanel questions_panel = new JPanel();

        //TODO: Add questions to the questions_panel

        //Add elements to frame
        constraints.fill = GridBagConstraints.BOTH;

		constraints.gridx = 0;
		constraints.weightx = 1;
		constraints.gridy = 0;
		constraints.weighty = 0.5;
		frame.add(quiz_info_panel, constraints);

        constraints.gridx = 0;
		constraints.weightx = 1;
		constraints.gridy = 1;
		constraints.weighty = 0.5;
		frame.add(new JScrollPane(questions_panel), constraints);
        
        frame.setVisible(true);
    }
}
