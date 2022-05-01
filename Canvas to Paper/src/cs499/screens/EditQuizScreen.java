package cs499.screens;


import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import cs499.Quiz;
import cs499.gui_utils.FrameBuilder;
import cs499.question.Question;
import cs499.question.SingleAnswerQuestion;
import cs499.RecentItems;

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
        
        RecentItems recent = new RecentItems();
        if(quiz != null && quiz.getName() != null) {
        	recent.addToRecent(quiz);
        }
        
        //Initialize the frame
        FrameBuilder maker = new FrameBuilder();
		frame = maker.buildFrame(
            frame_title, 
			JFrame.DISPOSE_ON_CLOSE, 
			800, 600, 
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
        
		//Create Back Button
        JButton save_btn = new JButton("Save Changes");
		class SaveAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				quiz.setName(quizField.getText());
				//quiz.setCourse(courseField.getText());
				quiz.setDate(dateField.getText());
				//quiz.setInstructor(instructorField.getText());
				quiz.saveQuiz();
				new WelcomeScreen();
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));;
			}
		}
		save_btn.addActionListener(new SaveAction());
		save_btn.setBounds(485, 30, 200, 20);
		quiz_info_panel.add(save_btn);

        quizField.setText(quiz.getName());
        courseField.setText(quiz.getShortCourse());
        dateField.setText(quiz.getDate());
        if (quiz.getInstructor() != null) instructorField.setText(quiz.getInstructor().getName());

        quiz_info_panel.add(quizLabel);
        quiz_info_panel.add(quizField);
        quiz_info_panel.add(courseLabel);
        quiz_info_panel.add(courseField);
        quiz_info_panel.add(dateLabel);
        quiz_info_panel.add(dateField);
        quiz_info_panel.add(instructorLabel);
        quiz_info_panel.add(instructorField);
        
        JPanel questions_panel = new JPanel();
        questions_panel.setLayout(new GridBagLayout());
        GridBagConstraints questions_panel_constraints = new GridBagConstraints();
        
        //Create Back Button
        final JButton back_btn = new JButton("<- Back");
        
        //Create Back Action Handler
		class BackAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				new SelectQuizScreen();
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));;
			}
		}
		back_btn.addActionListener(new BackAction());
            
        //Get the proper inset size
        int inset_size = ((back_btn.getMinimumSize().height) - ((new JLabel(" ")).getMinimumSize().height)) / 2;

        //Create JPanel for storing Edit Buttons
		JPanel edit_panel = new JPanel();
		edit_panel.setLayout(new GridBagLayout());
		GridBagConstraints edit_panel_constraints = new GridBagConstraints();
		edit_panel_constraints.anchor = GridBagConstraints.WEST;
		
		//Create JPanel for storing Question Text info
		JPanel text_panel = new JPanel();
		text_panel.setLayout(new GridBagLayout());
		GridBagConstraints text_panel_constraints = new GridBagConstraints();
		text_panel_constraints.anchor = GridBagConstraints.WEST;
        
		//Create JPanel for storing Question Type info
		JPanel type_panel = new JPanel();
		type_panel.setLayout(new GridBagLayout());
		GridBagConstraints type_panel_constraints = new GridBagConstraints();
		type_panel_constraints.anchor = GridBagConstraints.WEST;
		
		//Create JPanel for storing Delete Buttons
		JPanel del_panel = new JPanel();
		del_panel.setLayout(new GridBagLayout());
		GridBagConstraints del_panel_constraints = new GridBagConstraints();
		del_panel_constraints.anchor = GridBagConstraints.WEST;
        
		
		// Element 1: Edit Button
		edit_panel_constraints.gridx = 0;
		edit_panel_constraints.gridy = 0;
		edit_panel_constraints.weightx = 1;
		edit_panel_constraints.weighty = 1;
        edit_panel_constraints.insets = new Insets(inset_size, 0, inset_size, 0);
		edit_panel.add(new JLabel(" "), edit_panel_constraints);
		edit_panel_constraints.insets = new Insets(0, 0, 0, 0);
		
		// Element 2: Question Text
		text_panel_constraints.gridx = 0;
		text_panel_constraints.gridy = 0;
		text_panel_constraints.weightx = 1;
		text_panel_constraints.weighty = 1;
		text_panel_constraints.insets = new Insets(inset_size, 25, inset_size, 0);
		text_panel.add(new JLabel("Question Text"), text_panel_constraints);

		// Element 3: Question Type
		type_panel_constraints.gridx = 0;
		type_panel_constraints.gridy = 0;
		type_panel_constraints.weightx = 1;
		type_panel_constraints.weighty = 1;
		type_panel_constraints.insets = new Insets(inset_size, 25, inset_size, 0);
		type_panel.add(new JLabel("Question Type"), type_panel_constraints);
		
		// Element 4: Delete Button
		del_panel_constraints.gridx = 0;
		del_panel_constraints.gridy = 0;
		del_panel_constraints.weightx = 1;
		del_panel_constraints.weighty = 1;
		del_panel_constraints.insets = new Insets(inset_size, 25, inset_size, 0);
		del_panel.add(new JLabel(" "), del_panel_constraints);
		del_panel_constraints.insets = new Insets(0, 25, 0, 0);

		//Add elements to listing
		questions_panel_constraints.gridx = 0;
		questions_panel_constraints.gridy = 0;
		questions_panel.add(edit_panel, questions_panel_constraints);
		questions_panel_constraints.gridx = 1;
		questions_panel.add(text_panel, questions_panel_constraints);
		questions_panel_constraints.gridx = 2;
		questions_panel.add(type_panel, questions_panel_constraints);
		questions_panel_constraints.gridx = 3;
		questions_panel.add(del_panel, questions_panel_constraints);

        ArrayList<Question> question_list = quiz.getQuestions();

		for (int i = 0; i < question_list.size(); i++){
			JButton edit_btn = new JButton("Edit");
			Question question = question_list.get(i);
			if(question == null) {
				break;
			}
			class EditQuestionAction implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					new EditQuestionScreen(question);
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				}
			}
			edit_btn.addActionListener(new EditQuestionAction());

			JButton delete_btn = new JButton("Delete");
			class DeleteQuestionAction implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					quiz.removeQuestion(question);
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					new EditQuizScreen(quiz);
				}
			}
			delete_btn.addActionListener(new DeleteQuestionAction());

			// Element 1: Edit Button
			edit_panel_constraints.gridx = 0;
			edit_panel_constraints.gridy = i+1;
			edit_panel.add(edit_btn, edit_panel_constraints);
			
			// Element 2: Question Text
			text_panel_constraints.gridx = 0;
			text_panel_constraints.gridy = i+1;
			if (question.getDescription().length() > 50) text_panel.add(new JLabel(question.getDescription().substring(0, 50) + "..."), text_panel_constraints);
			else text_panel.add(new JLabel(question.getDescription()), text_panel_constraints);

			// Element 3: Question Type
			type_panel_constraints.gridx = 0;
			type_panel_constraints.gridy = i+1;
			type_panel.add(new JLabel("" + question.getType()), type_panel_constraints);
			
			// Element 4: Delete Button
			del_panel_constraints.gridx = 0;
			del_panel_constraints.gridy = i+1;
			del_panel.add(delete_btn, del_panel_constraints);
		}

		//Create create question button
		JButton create_question_btn = new JButton("Create Question");
		class CreateQuestionAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Question newQuestion = new SingleAnswerQuestion();
				quiz.addQuestion(newQuestion);
				quiz.addAssociation(newQuestion);
				new EditQuestionScreen(newQuestion);
				                
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		}
		create_question_btn.addActionListener(new CreateQuestionAction());

		// Element 1: Edit Button
		edit_panel_constraints.gridx = 0;
		edit_panel_constraints.gridy = question_list.size() + 1;
		edit_panel_constraints.insets = new Insets(0, 0, 0, 0);
		edit_panel.add(create_question_btn, edit_panel_constraints);
		
		// Element 2: Question Text
		text_panel_constraints.gridx = 0;
		text_panel_constraints.gridy = question_list.size() + 1;
		text_panel_constraints.insets = new Insets(inset_size, 25, inset_size, 0);
		text_panel.add(new JLabel(" "), text_panel_constraints);
		
		// Element 3: Question Type
		type_panel_constraints.gridx = 0;
		type_panel_constraints.gridy = question_list.size() + 1;
		type_panel_constraints.insets = new Insets(inset_size, 25, inset_size, 0);
		type_panel.add(new JLabel(" "), type_panel_constraints);
		
		// Element 4: Delete Button
		del_panel_constraints.gridx = 0;
		del_panel_constraints.gridy = question_list.size() + 1;
		del_panel_constraints.insets = new Insets(inset_size, 25, inset_size, 0);
		del_panel.add(new JLabel(" "), del_panel_constraints);

        //Add elements to frame
        constraints.fill = GridBagConstraints.BOTH;

        JScrollPane questions_scroller = new JScrollPane(questions_panel);
        questions_scroller.setMaximumSize(new Dimension(0,300));
        questions_scroller.setPreferredSize(new Dimension(0, 300));

		constraints.gridx = 0;
		constraints.weightx = 1;
		constraints.gridy = 0;
		constraints.weighty = 1;
		frame.add(quiz_info_panel, constraints);

        constraints.gridx = 0;
		constraints.weightx = 1;
		constraints.gridy = 1;
		constraints.weighty = 1;
		frame.add(questions_scroller, constraints);
        
        frame.setVisible(true);
    }
}
