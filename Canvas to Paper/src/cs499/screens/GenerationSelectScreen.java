package cs499.screens;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import cs499.Quiz;
import cs499.WordDocx;
import cs499.gui_utils.FrameBuilder;
import cs499.utils.DataUtils;

public class GenerationSelectScreen {
    JFrame frame;
    private WordDocx doc;
    public GenerationSelectScreen(){
        String frame_title = "CS 499-01 Spring 2022 CtPP Project Prototype-01";
        String logo_icon_path = "Canvas to Paper/lib/images/logo_icon.png";

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

		//Create a list of all quizzes
		JPanel quiz_list_listing = new JPanel();
		quiz_list_listing.setLayout(new GridBagLayout());
		GridBagConstraints quiz_list_list_constraints = new GridBagConstraints();
		
		//Create JPanel for storing Generate Buttons
		JPanel gen_panel = new JPanel();
		gen_panel.setLayout(new GridBagLayout());
		GridBagConstraints gen_panel_constraints = new GridBagConstraints();
		gen_panel_constraints.anchor = GridBagConstraints.WEST;
		
		//Create JPanel for storing Name info
		JPanel name_panel = new JPanel();
		name_panel.setLayout(new GridBagLayout());
		GridBagConstraints name_panel_constraints = new GridBagConstraints();
		name_panel_constraints.anchor = GridBagConstraints.WEST;
		
		//Create JPanel for storing Course info
		JPanel course_panel = new JPanel();
		course_panel.setLayout(new GridBagLayout());
		GridBagConstraints course_panel_constraints = new GridBagConstraints();
		course_panel_constraints.anchor = GridBagConstraints.WEST;
		
		//Create JPanel for storing Delete Buttons
		JPanel del_panel = new JPanel();
		del_panel.setLayout(new GridBagLayout());
		GridBagConstraints del_panel_constraints = new GridBagConstraints();
		del_panel_constraints.anchor = GridBagConstraints.WEST;

		//Get the proper inset size
		int inset_size = ((back_btn.getMinimumSize().height) - ((new JLabel(" ")).getMinimumSize().height)) / 2;
		
		// Element 1: Edit Button
		gen_panel_constraints.gridx = 0;
		gen_panel_constraints.gridy = 0;
		gen_panel_constraints.weightx = 1;
		gen_panel_constraints.weighty = 1;
		gen_panel.add(back_btn, gen_panel_constraints);
		
		// Element 2: Quiz Name
		name_panel_constraints.gridx = 0;
		name_panel_constraints.gridy = 0;
		name_panel_constraints.weightx = 1;
		name_panel_constraints.weighty = 1;
		name_panel_constraints.insets = new Insets(inset_size, 25, inset_size, 0);
		name_panel.add(new JLabel("Quiz Name"), name_panel_constraints);

		// Element 3: Course Number
		course_panel_constraints.gridx = 0;
		course_panel_constraints.gridy = 0;
		course_panel_constraints.weightx = 1;
		course_panel_constraints.weighty = 1;
		course_panel_constraints.insets = new Insets(inset_size, 25, inset_size, 0);
		course_panel.add(new JLabel("Course Section"), course_panel_constraints);
		
		// Element 4: Delete Button
		del_panel_constraints.gridx = 0;
		del_panel_constraints.gridy = 0;
		del_panel_constraints.weightx = 1;
		del_panel_constraints.weighty = 1;
		del_panel_constraints.insets = new Insets(inset_size, 25, inset_size, 0);
		del_panel.add(new JLabel(" "), del_panel_constraints);
		del_panel_constraints.insets = new Insets(0, 0, 0, 0);

		//Add elements to listing
		quiz_list_list_constraints.gridx = 0;
		quiz_list_list_constraints.gridy = 0;
		quiz_list_listing.add(gen_panel, quiz_list_list_constraints);
		quiz_list_list_constraints.gridx = 1;
		quiz_list_listing.add(name_panel, quiz_list_list_constraints);
		quiz_list_list_constraints.gridx = 2;
		quiz_list_listing.add(course_panel, quiz_list_list_constraints);
		quiz_list_list_constraints.gridx = 3;
		quiz_list_listing.add(del_panel, quiz_list_list_constraints);

		for (int i = 0; i < quiz_list.size(); i++){
			JButton gen_btn = new JButton("Generate");
			Quiz quiz = quiz_list.get(i);
			class SelectQuizAction implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					//TODO: Generate Paper Test from Quiz functionality
					doc = new WordDocx();
					
					String test_string = quiz.getName();
					String test_path = "";
					
					for (int i = 0; i < test_string.length(); i++) {
					    test_path += test_string.charAt(i);
					}
					test_path = "Generated_Tests\\" + quiz.getName();
					String test_fullpath = test_path + ".docx";
					String key_fullpath = test_path + "_ANSWER_KEY.docx";
					
					try {
						doc.DocumentBuilder(quiz, test_fullpath);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					try {
						doc.TestKeyBuilder(quiz, key_fullpath);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				}
			}
			gen_btn.addActionListener(new SelectQuizAction());

			JButton delete_btn = new JButton("Delete");
			class DeleteQuizAction implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					DataUtils.deleteQuiz(quiz.getId());
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					new SelectQuizScreen();
				}
			}
			delete_btn.addActionListener(new DeleteQuizAction());

			// Element 1: Edit Button
			gen_panel_constraints.gridx = 0;
			gen_panel_constraints.gridy = i+1;
			gen_panel.add(gen_btn, gen_panel_constraints);
			
			// Element 2: Quiz Name
			name_panel_constraints.gridx = 0;
			name_panel_constraints.gridy = i+1;
			name_panel.add(new JLabel(quiz.getName()), name_panel_constraints);
			
			// Element 3: Course Number
			course_panel_constraints.gridx = 0;
			course_panel_constraints.gridy = i+1;
			if (quiz.getCourse() != null) course_panel.add(new JLabel(quiz.getCourse()), course_panel_constraints);
			else course_panel.add(new JLabel("Not Course Attached"), course_panel_constraints);
			
			// Element 4: Delete Button
			del_panel_constraints.gridx = 0;
			del_panel_constraints.gridy = i+1;
			del_panel.add(delete_btn, del_panel_constraints);
		}

		//Create create quiz button
		JButton create_quiz_btn = new JButton("Create Quiz");
		class CreateQuizAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Quiz new_quiz = new Quiz();
				new EditQuizScreen(new_quiz);
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		}
		create_quiz_btn.addActionListener(new CreateQuizAction());

		// Element 1: Edit Button
		gen_panel_constraints.gridx = 0;
		gen_panel_constraints.gridy = quiz_list.size() + 1;
		gen_panel_constraints.insets = new Insets(0, 0, 0, 0);
		gen_panel.add(create_quiz_btn, gen_panel_constraints);
		
		// Element 2: Quiz Name
		name_panel_constraints.gridx = 0;
		name_panel_constraints.gridy = quiz_list.size() + 1;
		name_panel_constraints.insets = new Insets(inset_size, 0, inset_size, 0);
		name_panel.add(new JLabel(" "), name_panel_constraints);
		
		// Element 3: Course Number
		course_panel_constraints.gridx = 0;
		course_panel_constraints.gridy = quiz_list.size() + 1;
		course_panel_constraints.insets = new Insets(inset_size, 0, inset_size, 0);
		course_panel.add(new JLabel(" "), course_panel_constraints);
		
		// Element 4: Delete Button
		del_panel_constraints.gridx = 0;
		del_panel_constraints.gridy = quiz_list.size() + 1;
		del_panel_constraints.insets = new Insets(inset_size, 0, inset_size, 0);
		del_panel.add(new JLabel(" "), del_panel_constraints);

		//Create Constraints Guide
		GridBagConstraints constraints = new GridBagConstraints();
        
        //Add elements to frame
        constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 0;
		constraints.weightx = 1;
		constraints.gridy = 0;
		constraints.weighty = 1;
		frame.add(new JScrollPane(quiz_list_listing), constraints);

        frame.setVisible(true);
    }
}
