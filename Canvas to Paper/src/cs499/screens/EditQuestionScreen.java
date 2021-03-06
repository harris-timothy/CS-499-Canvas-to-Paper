package cs499.screens;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cs499.gui_utils.FrameBuilder;
import cs499.question.MultipleChoiceQuestion;
import cs499.question.Question;
import cs499.question.QuestionType;

public class EditQuestionScreen {
	
	 	private JFrame frame;
	    private JLabel questionLabel;
	    private JTextField nameField;
	    private JLabel descriptionLabel;
	    private JTextArea descriptionField;
	    private JLabel typeLabel;
	    private JComboBox<QuestionType> typeComboBox;
	    private JLabel instructionLabel;
	    private JTextField instructionField;
	    private JLabel answerLabel;
	    private JTextArea answerField;
	    
    public EditQuestionScreen(Question question){
    	
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
        GridBagConstraints constraints = new GridBagConstraints();
         
        JPanel question_info_panel = new JPanel();
         
        question_info_panel.setLayout(null);
         
        this.questionLabel = new JLabel();
        questionLabel.setText("Question Name");
        questionLabel.setBounds(10,0,100,30);
         
        this.nameField = new JTextField();
        nameField.setBounds(10, 30, 325, 20);
         
        this.descriptionLabel = new JLabel();
        descriptionLabel.setText("Description");
        descriptionLabel.setBounds(350,0,100,30);
         
        this.descriptionField = new JTextArea();
        descriptionField.setPreferredSize(new Dimension(300,250));
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descriptionField);
        
         
        this.typeLabel = new JLabel();
        typeLabel.setText("Type");
        typeLabel.setBounds(10, 55, 150, 30);
         
        this.typeComboBox = new JComboBox<QuestionType>();
        typeComboBox.setModel(new DefaultComboBoxModel<>(QuestionType.values()));
        typeComboBox.setBounds(10, 85, 125, 20);
        typeComboBox.getModel().setSelectedItem(question.getType());
        
        class TypeComboBox implements ActionListener {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				JComboBox<QuestionType> cb = (JComboBox<QuestionType>)e.getSource();
				QuestionType type = (QuestionType) cb.getSelectedItem();
				question.setType(type);
			}
        }
        
        typeComboBox.addActionListener(new TypeComboBox());
         
        this.instructionLabel = new JLabel();
        instructionLabel.setText("Grading Instructions");
        instructionLabel.setBounds(150,55,150,30);
         
        this.instructionField = new JTextField();
        instructionField.setBounds(150,85,200,20);
        
        this.answerLabel = new JLabel();
        answerLabel.setText("Answer:");
        answerLabel.setBounds(350,85,150,100);
        
        this.answerField = new JTextArea();
        answerField.setPreferredSize(new Dimension(300,250));
        answerField.setLineWrap(true);
        answerField.setWrapStyleWord(true);
        JScrollPane answer_scroll = new JScrollPane(answerField);
        
      //Create ABET checkbox
 		JCheckBox abet_box = new JCheckBox("Mark for ABET");
 		abet_box.setMnemonic(KeyEvent.VK_A);
 		abet_box.setSelected(question.getAbet());
 		abet_box.setBounds(350,0,120,80);
 		
 		JCheckBox blacklist_box = new JCheckBox("Blacklist");
 		blacklist_box.setMnemonic(KeyEvent.VK_B);
 		blacklist_box.setSelected(false);
 		blacklist_box.setBounds(350,50,120,80);
 		
 		question_info_panel.add(abet_box);
 		question_info_panel.add(blacklist_box);
 		
 		//TODO fix answer saving - currently duplicates.
         
        //Create Back Button
        JButton save_btn = new JButton("Save Changes");
 		class SaveAction implements ActionListener {
 			public void actionPerformed(ActionEvent e) {
 				question.setName(nameField.getText());
 				question.setDescription(descriptionField.getText());
 				question.setGradingInstructions(instructionField.getText());
 				question.setType((QuestionType) typeComboBox.getSelectedItem());
 				question.setAbet(abet_box.isSelected());
 				if(answerField != null) question.setAnswer(answerField.getText());
 				question.saveQuestion();
 				new SelectQuizScreen();
 				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));;
 			}
 		}
 		save_btn.addActionListener(new SaveAction());
 		save_btn.setBounds(485, 30, 200, 20);
 		question_info_panel.add(save_btn);
 		
 		
 		
 		
 		nameField.setText(question.getName());
        descriptionField.setText(question.getDescription());
        if (question.getGradingInstructions() != null) instructionField.setText(question.getGradingInstructions());
        if (question.getAnswer() != null && answerField != null) answerField.setText(question.getAnswer());
        

        question_info_panel.add(questionLabel);
        question_info_panel.add(nameField);
        question_info_panel.add(typeLabel);
        question_info_panel.add(typeComboBox);
        question_info_panel.add(instructionLabel);
        question_info_panel.add(instructionField);
        
        JPanel body_panel = new JPanel();
        body_panel.setLayout(new GridBagLayout());
        GridBagConstraints body_panel_constraints = new GridBagConstraints();
        
        body_panel_constraints.gridx=0;
        body_panel_constraints.gridy=0;
        body_panel.add(descriptionLabel, body_panel_constraints);
        
        body_panel_constraints.gridx=0;
        body_panel_constraints.gridy=1;
        body_panel.add(descScroll, body_panel_constraints);
        
        body_panel_constraints.gridx=1;
        body_panel_constraints.gridy=0;
        body_panel.add(answerLabel, body_panel_constraints);
        
        body_panel_constraints.gridx=1;
        body_panel_constraints.gridy=1;
        body_panel.add(answer_scroll, body_panel_constraints);
        
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
        question_info_panel.add(back_btn);

        //Add elements to frame
        constraints.fill = GridBagConstraints.BOTH;

        JScrollPane questions_scroller = new JScrollPane(body_panel);
        questions_scroller.setMaximumSize(new Dimension(0,300));
        questions_scroller.setPreferredSize(new Dimension(0, 300));

		constraints.gridx = 0;
		constraints.weightx = 1;
		constraints.gridy = 0;
		constraints.weighty = 1;
		frame.add(question_info_panel, constraints);

        constraints.gridx = 0;
		constraints.weightx = 1;
		constraints.gridy = 1;
		constraints.weighty = 1;
		frame.add(questions_scroller, constraints);
		
        frame.setVisible(true);
        
    }
}
