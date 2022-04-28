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
import cs499.gui_utils.FileExplorer;
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
	private JLabel locationGenLabel;
	private JTextField locationGenField;
	private JLabel locationTemplateLabel;
	private JTextField locationTemplateField;
	private WordDocx doc;	
	
	public GeneratorScreen(Quiz quiz) {
		String frame_title = "Generate Quiz";
        String logo_icon_path = "Canvas to Paper/lib/images/logo_icon.png";
		FileExplorer explorer = new FileExplorer();

        //Initialize the frame
        FrameBuilder maker = new FrameBuilder();
		frame = maker.buildFrame(
			frame_title, 
			JFrame.DISPOSE_ON_CLOSE, 
			1130, 100, 
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

        this.locationGenLabel = new JLabel();
        locationGenLabel.setText("Generate to");
        locationGenLabel.setBounds(500,0,150,30);
        
        this.locationGenField = new JTextField();
        locationGenField.setBounds(500,30,200,20);

		//Create "Generate to" Button
		JButton gen_btn = new JButton("...");
		class GenAction implements ActionListener {
			String generation_directory_path = "";
			public void actionPerformed(ActionEvent e) {
				try {
					generation_directory_path = explorer.DirectorySelect();
					while (generation_directory_path == "Error") {
						generation_directory_path = explorer.DirectorySelect();
					}
					if (generation_directory_path == "Blank") {
						return;
					}
					locationGenField.setText(generation_directory_path);
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}
        gen_btn.addActionListener(new GenAction());
		gen_btn.setBounds(700, 30, 25, 20);
		genPanel.add(gen_btn);

        this.locationTemplateLabel = new JLabel();
        locationTemplateLabel.setText("Template File");
        locationTemplateLabel.setBounds(740,0,150,30);
        
        this.locationTemplateField = new JTextField();
        locationTemplateField.setBounds(740,30,200,20);

		//Create "Generate to" Button
		JButton template_btn = new JButton("...");
		class TemplateAction implements ActionListener {
			String template_path = "";
			public void actionPerformed(ActionEvent e) {
				try {
					String[] formats = {"Template Word Documents (.docx)", "docx"};
					template_path = explorer.FileSelect(formats);
					while (template_path == "Error") {
						template_path = explorer.FileSelect(formats);
					}
					if (template_path == "Blank") {
						return;
					}
					locationTemplateField.setText(template_path);
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}
        template_btn.addActionListener(new TemplateAction());
		template_btn.setBounds(940, 30, 25, 20);
		genPanel.add(template_btn);

		//Create Generate Button
        JButton save_btn = new JButton("Generate Quiz");
		class SaveAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				//quiz.setCourse(courseField.getText());
				quiz.setDate(dateField.getText());
				//quiz.setInstructor();
				quiz.saveQuiz();
				doc = new WordDocx();
				
				String test_path = locationGenField.getText() + "\\" + quiz.getName() + ".docx";
				
				try {
					doc.Shuffler(quiz, test_path, locationTemplateField.getText());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		}
		save_btn.addActionListener(new SaveAction());
		save_btn.setBounds(980, 30, 125, 20);
		genPanel.add(save_btn);
		
        genPanel.add(courseLabel);
        genPanel.add(courseField);
        genPanel.add(dateLabel);
        genPanel.add(dateField);
        genPanel.add(instructorLabel);
        genPanel.add(instructorField);
        genPanel.add(locationGenLabel);
        genPanel.add(locationGenField);
        genPanel.add(locationTemplateLabel);
        genPanel.add(locationTemplateField);
        
        frame.add(genPanel);
        frame.setVisible(true);
	}
	
}
