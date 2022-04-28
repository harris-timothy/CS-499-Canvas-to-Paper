package cs499.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cs499.Quiz;
import cs499.WordDocx;
import cs499.gui_utils.FrameBuilder;

public class GeneratorScreen {
	private JFrame frame;
	private JLabel courseLabel;
	private JTextField courseField;
	private JLabel dateLabel;
	//private JFormattedTextField dateField;
	private JTextField dateField;
	private JLabel instructorLabel;
	private JTextField instructorField;
	private WordDocx doc;	
	
	public GeneratorScreen(Quiz quiz) {
		String frame_title = "Generate Quiz";
        String logo_icon_path = "Canvas to Paper/lib/images/logo_icon.png";

        //Initialize the frame
        FrameBuilder maker = new FrameBuilder();
		frame = maker.buildFrame(
			frame_title, 
			JFrame.DISPOSE_ON_CLOSE, 
			690, 100, 
			null, 
			logo_icon_path
		);
        
        JPanel genPanel = new JPanel();
        genPanel.setLayout(null);
        
        this.courseLabel = new JLabel();
        courseLabel.setText("Course Number");
        courseLabel.setBounds(365,0,100,30);
        
        this.courseField = new JTextField();
        courseField.setText(quiz.getShortCourse());
        courseField.setBounds(365, 30, 120, 20);
        
        this.dateLabel = new JLabel();
        dateLabel.setText("Date");
        dateLabel.setBounds(10, 0, 150, 30);
        
        // DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        this.dateField = new JTextField();
        dateField.setHorizontalAlignment(JTextField.RIGHT);
        dateField.setText(quiz.getDate());
        dateField.setBounds(10, 30, 125, 20);
        
        this.instructorLabel = new JLabel();
        instructorLabel.setText("Instructor");
        instructorLabel.setBounds(150,0,150,30);
        
        this.instructorField = new JTextField();
        instructorField.setBounds(150,30,200,20);
        
		//Create Back Button
        JButton save_btn = new JButton("Generate Quiz");
		class SaveAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				//quiz.setCourse(courseField.getText());
				quiz.setDate(dateField.getText());
				//quiz.setInstructor();
				quiz.saveQuiz();
				doc = new WordDocx();
				
				String test_string = quiz.getName();
				String test_path = "";
				
				for (int i = 0; i < test_string.length(); i++) {
				    test_path += test_string.charAt(i);
				}
				test_path = "Generated_Tests\\" + quiz.getName();
				String test_fullpath = test_path + ".docx";
				
				try {
					doc.Shuffler(quiz, test_fullpath);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		}
		save_btn.addActionListener(new SaveAction());
		save_btn.setBounds(500, 30, 125, 20);
		genPanel.add(save_btn);
		
        genPanel.add(courseLabel);
        genPanel.add(courseField);
        genPanel.add(dateLabel);
        genPanel.add(dateField);
        genPanel.add(instructorLabel);
        genPanel.add(instructorField);
        
        frame.add(genPanel);
        frame.setVisible(true);
	}
	
}
