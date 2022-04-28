package cs499.screens;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cs499.Quiz;
import cs499.ReferenceMaterial;
import cs499.gui_utils.FrameBuilder;
import cs499.utils.DatabaseUtils;

public class ReferencesScreen {
	private JFrame frame;
	
	public ReferencesScreen() {
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
		ArrayList<Quiz> quiz_list = DatabaseUtils.getAllQuizzes();
		ArrayList<ReferenceMaterial> references = new ArrayList<ReferenceMaterial>();
		
		for (Quiz quiz : quiz_list) {			
			if (quiz.getReferences() != null) {
				references.addAll(quiz.getReferences());
			}
		}

		//Create a list of all quizzes
		JPanel reference_listing = new JPanel();
		reference_listing.setLayout(new GridBagLayout());
		GridBagConstraints reference_list_constraints = new GridBagConstraints();
		
		//Create JPanel for storing Name info
		JPanel name_panel = new JPanel();
		name_panel.setLayout(new GridBagLayout());
		GridBagConstraints name_panel_constraints = new GridBagConstraints();
		name_panel_constraints.anchor = GridBagConstraints.WEST;
		
		//Create JPanel for storing Delete Buttons
		JPanel del_panel = new JPanel();
		del_panel.setLayout(new GridBagLayout());
		GridBagConstraints del_panel_constraints = new GridBagConstraints();
		del_panel_constraints.anchor = GridBagConstraints.WEST;

		//Get the proper inset size
		int inset_size = ((back_btn.getMinimumSize().height) - ((new JLabel(" ")).getMinimumSize().height)) / 2;

		// Element 1: Reference Material
		name_panel_constraints.gridx = 0;
		name_panel_constraints.gridy = 0;
		name_panel_constraints.weightx = 1;
		name_panel_constraints.weighty = 1;
		name_panel_constraints.insets = new Insets(inset_size, 25, inset_size, 0);
		name_panel.add(new JLabel("Reference Material"), name_panel_constraints);
		
		// Element 2: Delete Button
		del_panel_constraints.gridx = 0;
		del_panel_constraints.gridy = 0;
		del_panel_constraints.weightx = 1;
		del_panel_constraints.weighty = 1;
		del_panel_constraints.insets = new Insets(inset_size, 25, inset_size, 0);
		del_panel.add(new JLabel(" "), del_panel_constraints);
		del_panel_constraints.insets = new Insets(0, 0, 0, 0);

		//Add elements to listing
		reference_list_constraints.gridx = 0;
		reference_list_constraints.gridy = 0;
		reference_list_constraints.gridx = 1;
		reference_listing.add(name_panel, reference_list_constraints);
		reference_list_constraints.gridx = 2;
		reference_listing.add(del_panel, reference_list_constraints);
		
		for (int i = 0; i < references.size(); i++){
			ReferenceMaterial curRef = references.get(i);
			JButton delete_btn = new JButton("Delete");
			class DeleteRefAction implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					DatabaseUtils.deleteReference(curRef);
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					new ReferencesScreen();
				}
			}
			delete_btn.addActionListener(new DeleteRefAction());
			
			// Element 1: Reference Material
			name_panel_constraints.gridx = 0;
			name_panel_constraints.gridy = i+1;
			name_panel.add(new JLabel(curRef.getName()), name_panel_constraints);

			// Element 2: Delete Button
			del_panel_constraints.gridx = 0;
			del_panel_constraints.gridy = i+1;
			del_panel.add(delete_btn, del_panel_constraints);
		}
		
		// Element 1: Reference Material
		name_panel_constraints.gridx = 0;
		name_panel_constraints.gridy = references.size() + 1;
		name_panel_constraints.insets = new Insets(inset_size, 0, inset_size, 0);
		name_panel.add(new JLabel(" "), name_panel_constraints);
		
		// Element 2: Delete Button
		del_panel_constraints.gridx = 0;
		del_panel_constraints.gridy = references.size() + 1;
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
		frame.add(new JScrollPane(reference_listing), constraints);

        frame.setVisible(true);
	}
}
