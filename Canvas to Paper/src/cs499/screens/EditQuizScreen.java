package cs499.screens;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import cs499.Quiz;
import cs499.gui_utils.FrameBuilder;

public class EditQuizScreen {
    private JFrame frame;
    private JLabel quizLabel;
    private JTextField quizField;
    private JLabel courseLabel;
    private JTextField courseField;
    private JLabel dateLabel;
    private JFormattedTextField dateField;
    private JLabel instructorLabel;
    private JTextField instructorField;
    
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
        quiz_info_panel.setLayout(null);
        
        this.quizLabel = new JLabel();
        quizLabel.setText("Quiz Name");
        quizLabel.setBounds(10,0,100,30);
        
        this.quizField = new JTextField();
        quizField.setBounds(10, 30, 325, 20);
        
        this.courseLabel = new JLabel();
        courseLabel.setText("Course Number");
        courseLabel.setBounds(350,0,100,30);
        
        this.courseField = new JTextField();
        courseField.setBounds(350, 30, 120, 20);
        
        this.dateLabel = new JLabel();
        dateLabel.setText("Date (MM/DD/YYYY)");
        dateLabel.setBounds(10, 55, 150, 30);
        
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        this.dateField = new JFormattedTextField(dateFormat);
        dateField.setHorizontalAlignment(JTextField.RIGHT);
        dateField.setBounds(10, 85, 125, 20);
        
        this.instructorLabel = new JLabel();
        instructorLabel.setText("Instructor");
        instructorLabel.setBounds(150,55,150,30);
        
        this.instructorField = new JTextField();
        instructorField.setBounds(150,85,200,20);
        
        //TODO: Add functionality of each field
        
        quiz_info_panel.add(quizLabel);
        quiz_info_panel.add(quizField);
        quiz_info_panel.add(courseLabel);
        quiz_info_panel.add(courseField);
        quiz_info_panel.add(dateLabel);
        quiz_info_panel.add(dateField);
        quiz_info_panel.add(instructorLabel);
        quiz_info_panel.add(instructorField);

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
