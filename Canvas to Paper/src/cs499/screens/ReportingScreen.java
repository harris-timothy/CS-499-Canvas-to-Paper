package cs499.screens;

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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cs499.Reporting;
import cs499.gui_utils.FrameBuilder;
import cs499.question.Question;


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
        generationsLabel.setBounds(10,10,100,50);
        
        this.generationsTable = new JTable();
        generationsTable.setBounds(10,100,200,300);        
        
        JScrollPane generations_panel = new JScrollPane(generationsTable);
        generations_panel.setPreferredSize(new Dimension(100,300));
        
        this.questionsLabel = new JLabel();
        questionsLabel.setText("Questions");
        questionsLabel.setBounds(0,0,100,50);
        
        this.questionsTable = new JTable();
        questionsTable.setBounds(0,0,300,300);
        
        JScrollPane questions_panel = new JScrollPane(questionsTable);
        questions_panel.setPreferredSize(new Dimension(300,500));
        
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
				questionsTable.setModel(questions_model);
				questionsTable.setRowHeight(30);
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
        constraints.anchor = GridBagConstraints.NORTHWEST;
        
        
        constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.weightx = 0.1;
		constraints.weighty = 0.1;
		frame.add(new JLabel(), constraints);

		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.weightx = 0.1;
		constraints.weighty = 0.1;
		frame.add(new JLabel(), constraints);

		constraints.gridx = 2;
		constraints.weightx = 0.1;
		frame.add(new JLabel(), constraints);

		constraints.gridx = 3;
		constraints.gridy = 6;
		constraints.weightx = 0.1;
		constraints.weighty = 0.2;
		frame.add(new JLabel(), constraints);
		
		//Set table weights
		constraints.weightx = 0.6 / 2;
		constraints.weighty = 0.7 / 5;
        
		constraints.gridx = 1;
        constraints.gridy = 2;
        frame.add(generationsLabel, constraints);
       
        constraints.gridy = 3;
        constraints.gridheight = 2;
        frame.add(generations_panel, constraints);
        
        constraints.gridy = 5;
        constraints.gridheight = 1;
        frame.add(details_button, constraints);
        
        constraints.gridx = 3;
        constraints.gridy = 2;        
        frame.add(questionsLabel, constraints);
        
        constraints.gridy = 3;
        constraints.gridheight = 2;
        constraints.gridwidth = 2;        
        frame.add(questions_panel, constraints);
        
        constraints.gridx = 4;
        constraints.gridy = 4;
        constraints.gridheight = 1;
        frame.add(backButton, constraints);        
		
        frame.setVisible(true);
	}
}
