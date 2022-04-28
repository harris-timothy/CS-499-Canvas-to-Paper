package cs499.screens;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cs499.Reporting;
import cs499.gui_utils.FrameBuilder;
import cs499.question.Question;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ReportingScreen {

	private JFrame frame;
	private JLabel generationsLabel;
	private JTable generationsTable;
	private JLabel questionsLabel;
	private JTable questionsTable;
	private JButton backButton;
	
	
	public ReportingScreen() {
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
		GridBagLayout gridbag = new GridBagLayout();
        frame.setLayout(gridbag);
        GridBagConstraints constraints = new GridBagConstraints();
        
        
        this.generationsLabel = new JLabel();
        generationsLabel.setText("Generated Quizzes");
        generationsLabel.setBounds(0,0,100,50);
        
        this.generationsTable = new JTable();
        generationsTable.setBounds(0,0,200,300);
        
        
        JScrollPane generations_panel = new JScrollPane(generationsTable);
        generations_panel.setPreferredSize(new Dimension(200,300));
        
        this.questionsLabel = new JLabel();
        questionsLabel.setText("Questions");
        questionsLabel.setBounds(0,0,100,50);
        
        this.questionsTable = new JTable();
        questionsTable.setBounds(0,0,200,300);
        
        JScrollPane questions_panel = new JScrollPane(questionsTable);
        questions_panel.setPreferredSize(new Dimension(200,300));
        
        Reporting report = new Reporting();
        
		Object[] columns = {" "};
		DefaultTableModel generations_model = new DefaultTableModel(new Object[0][0],columns);
		for(String title: report.getMetaTitles()) {
			Object[] o = new Object[1];
			o[0] = title;
			generations_model.addRow(o);
		}
		generationsTable.setModel(generations_model);
		generationsTable.setRowHeight(80);
		
		JButton details_button = new JButton();
		details_button.setText("Load details");
		details_button.setPreferredSize(new Dimension(100,30));
		
		class DetailButtonAction implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Question> questions = report.getDetails(report.getMetaList().get(generationsTable.getSelectedRow()));
				Object[] columns = {"Name","Type","Description"};
				DefaultTableModel questions_model = new DefaultTableModel(new Object[0][0],columns);
				
				for(Question q: questions) {
					Object[] o = new Object[3];
					o[0] = q.getName();
					o[1] = q.getType();
					o[2] = q.getDescription();
					questions_model.addRow(o);
				}
				
			}
			
		}
		details_button.addActionListener(new DetailButtonAction());		
		
        
        this.backButton = new JButton();
        backButton.setText("<- Back");
        backButton.setPreferredSize(new Dimension(100,50));
        
        class BackButtonAction implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				new WelcomeScreen();
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));;
				
			}
        }
        backButton.addActionListener(new BackButtonAction());
        
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
              
        constraints.weightx = 1;
        constraints.weighty = 1;
        
        constraints.gridx = 2;
        constraints.gridy = 1;
        frame.add(generationsLabel);
        
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridheight = 3;        
        frame.add(generations_panel);
        
        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.gridheight = 1;
        frame.add(details_button);
        
        constraints.gridx = 5;
        constraints.gridy = 2;        
        frame.add(questionsLabel);
        
        constraints.gridx = 5;
        constraints.gridy = 2;
        constraints.gridheight = 3;
        frame.add(questions_panel);
        
        constraints.gridx = 6;
        constraints.gridy = 5;
        constraints.gridheight = 1;
        frame.add(backButton);        
		
        frame.setVisible(true);
	}

}
