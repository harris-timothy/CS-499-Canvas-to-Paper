package cs499.screens;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.util.ArrayList;

import cs499.QuestionBank;
import cs499.Quiz;
import cs499.gui_utils.FrameBuilder;
import cs499.utils.DatabaseUtils;

public class SelectBankScreen {
    private JFrame frame;
    public SelectBankScreen(){
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

		//Obtain a list of all banks in the database
		ArrayList<QuestionBank> bank_list = DatabaseUtils.getAllBanks();

		//Create a list of all banks
		JPanel bank_list_listing = new JPanel();
		bank_list_listing.setLayout(new GridBagLayout());
		GridBagConstraints bank_list_list_constraints = new GridBagConstraints();
		
		//Create JPanel for storing Edit Buttons
		JPanel edit_panel = new JPanel();
		edit_panel.setLayout(new GridBagLayout());
		GridBagConstraints edit_panel_constraints = new GridBagConstraints();
		edit_panel_constraints.anchor = GridBagConstraints.WEST;
		
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
		
		// Element 1: Edit Button
		edit_panel_constraints.gridx = 0;
		edit_panel_constraints.gridy = 0;
		edit_panel_constraints.weightx = 1;
		edit_panel_constraints.weighty = 1;
		edit_panel.add(back_btn, edit_panel_constraints);
		
		// Element 2: Bank Name
		name_panel_constraints.gridx = 0;
		name_panel_constraints.gridy = 0;
		name_panel_constraints.weightx = 1;
		name_panel_constraints.weighty = 1;
		name_panel_constraints.insets = new Insets(inset_size, 25, inset_size, 0);
		name_panel.add(new JLabel("Bank Name"), name_panel_constraints);

		// Element 3: Delete Button
		del_panel_constraints.gridx = 0;
		del_panel_constraints.gridy = 0;
		del_panel_constraints.weightx = 1;
		del_panel_constraints.weighty = 1;
		del_panel_constraints.insets = new Insets(inset_size, 25, inset_size, 0);
		del_panel.add(new JLabel(" "), del_panel_constraints);
		del_panel_constraints.insets = new Insets(0, 0, 0, 0);

		//Add elements to listing
		bank_list_list_constraints.gridx = 0;
		bank_list_list_constraints.gridy = 0;
		bank_list_listing.add(edit_panel, bank_list_list_constraints);
		bank_list_list_constraints.gridx = 1;
		bank_list_listing.add(name_panel, bank_list_list_constraints);
		bank_list_list_constraints.gridx = 2;
		bank_list_listing.add(del_panel, bank_list_list_constraints);

		for (int i = 0; i < bank_list.size(); i++){
			JButton edit_btn = new JButton("Edit");
			QuestionBank bank = bank_list.get(i);
			class SelectQuizAction implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					new EditBankScreen(bank);
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				}
			}
			edit_btn.addActionListener(new SelectQuizAction());

			JButton delete_btn = new JButton("Delete");
			class DeleteBankAction implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					DatabaseUtils.deleteBank(bank.getId());
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					new SelectBankScreen();
				}
			}
			delete_btn.addActionListener(new DeleteBankAction());

			// Element 1: Edit Button
			edit_panel_constraints.gridx = 0;
			edit_panel_constraints.gridy = i+1;
			edit_panel.add(edit_btn, edit_panel_constraints);
			
			// Element 2: Quiz Name
			name_panel_constraints.gridx = 0;
			name_panel_constraints.gridy = i+1;
			name_panel.add(new JLabel(bank.getName()), name_panel_constraints);
			
			// Element 3: Delete Button
			del_panel_constraints.gridx = 0;
			del_panel_constraints.gridy = i+1;
			del_panel.add(delete_btn, del_panel_constraints);
		}

		//Create create bank button
		JButton create_quiz_btn = new JButton("Create Quiz Bank");
		class CreateBankAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				QuestionBank new_bank = new QuestionBank();
				new EditBankScreen(new_bank);
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		}
		create_quiz_btn.addActionListener(new CreateBankAction());

		// Element 1: Edit Button
		edit_panel_constraints.gridx = 0;
		edit_panel_constraints.gridy = bank_list.size() + 1;
		edit_panel_constraints.insets = new Insets(0, 0, 0, 0);
		edit_panel.add(create_quiz_btn, edit_panel_constraints);
		
		// Element 2: Quiz Name
		name_panel_constraints.gridx = 0;
		name_panel_constraints.gridy = bank_list.size() + 1;
		name_panel_constraints.insets = new Insets(inset_size, 0, inset_size, 0);
		name_panel.add(new JLabel(" "), name_panel_constraints);
		
		// Element 3: Delete Button
		del_panel_constraints.gridx = 0;
		del_panel_constraints.gridy = bank_list.size() + 1;
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
		frame.add(new JScrollPane(bank_list_listing), constraints);

        frame.setVisible(true);
    }
}
