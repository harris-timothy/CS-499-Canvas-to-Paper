package cs499.screens;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.util.ArrayList;

import cs499.qti.CreateQTI;
import cs499.qti.ParseQTI;
import cs499.Quiz;
import cs499.gui_utils.FileExplorer;
import cs499.gui_utils.FrameBuilder;
import cs499.gui_utils.MenuBuilder;

public class WelcomeScreen {
    public WelcomeScreen(){
        //Initialize starting simple variables
		String frame_title = "CS 499-01 Spring 2022 CtPP Project Prototype-01";
		String logo_icon_path = "Canvas to Paper/lib/images/logo_icon.png";
		
		//Initialize gui helper classes
		MenuBuilder menu = new MenuBuilder();
		FileExplorer explorer = new FileExplorer();
		FrameBuilder maker = new FrameBuilder();
		
		//Create Menu Bar
		JMenuBar menu_bar = new JMenuBar();

		//Initialize Frames
		JFrame frame = maker.buildFrame(
			frame_title, 
			JFrame.DISPOSE_ON_CLOSE, 
			1000, 800, 
			menu_bar, 
			logo_icon_path
		);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
	
		JFrame export_frame = maker.buildFrame(
			"Please Select Quizzes/QuizBanks to Export", 
			JFrame.DISPOSE_ON_CLOSE, 
			800, 
			600,
			null, 
			logo_icon_path);
		export_frame.setLayout(new GridBagLayout());
		GridBagConstraints export_constraints = new GridBagConstraints();
		
		//Fill the Menu Bar:
		JMenu file_menu = menu.buildMenu("File", KeyEvent.VK_F);
		menu_bar.add(file_menu);
		
		//File -> Import QTI Files
		JMenuItem import_mi = menu.buildMenuItem("Import QTI Files", KeyEvent.VK_I, file_menu);
		class ImportAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				//Initialize variables
				String[] ImportZip;
				String[] ErrorArray = {"Error"};
				ParseQTI qti = new ParseQTI();
				
				//Loop to ensure proper selection or cancel selection
				do {
					//Check for errors and retry if caught
					do {
						ImportZip = explorer.FilesSelect();
					} while (ImportZip == ErrorArray);
					
					//Check for cancellations and cancel if caught
					if (ImportZip == null) return;
					
					//Check for non .zip files and retry if caught
					for (String filepath : ImportZip) {
						if (!filepath.contains(".zip")) {
							ImportZip = new String[0];
						}
					}
				} while (ImportZip.length <= 0); 
				
				//Handle selection
				try {
					//Unzip .zip directory to QTITest directory
					for (String filepath : ImportZip) {
						qti.unzip(filepath, "QTITest");
					}
					//Loop through QTITest directory, handling all xml files
					qti.xmlLoop("QTITest");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		import_mi.addActionListener(new ImportAction());
		
		//File -> Export QTI File
		JMenuItem export_mi = menu.buildMenuItem("Export QTI File", KeyEvent.VK_E, file_menu);
		class ExportAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				// For teammates:
				/* TODO: 
				* Will need a way to get an array/arraylist/iteratable list of each quiz 
				* and each quiz bank they could export, each item in the list 
				* holding the following information:
				*	- ID/Name of quiz
				*	- Type of item (Quiz Bank/Quiz)
				*	- Any other information needed for the following to do block about exporting a modified version of this list
				*/
				// ListObject QQB = new someobject.listQuizStuff()
				ArrayList<Quiz> QQB = new ArrayList<Quiz>();
				
				// TODO: Make a second list to be modified
				// ListObject Modified_QQB = new ListObject;
				ArrayList<Quiz> Modified_QQB = new ArrayList<Quiz>();
				
				JButton export_cfm_btn = new JButton("Export");
				class ExportConfirmAction implements ActionListener {
					public void actionPerformed(ActionEvent e) {
						export_frame.dispose();
						CreateQTI qti_maker = new CreateQTI();
						//TODO: pass the quiz to the qti_maker
					}
				}
				export_cfm_btn.addActionListener(new ExportConfirmAction());
				
				JButton export_cnl_btn = new JButton("Cancel");
				class ExportCancelAction implements ActionListener {
					public void actionPerformed(ActionEvent e) {
						export_frame.dispose();
						//TODO: Ensure frame is properly reset, not just hidden and reshown later.
					}
				}
				export_cnl_btn.addActionListener(new ExportCancelAction());
				
				JPanel qqb_listing = new JPanel();
				qqb_listing.setLayout(new GridBagLayout());
				GridBagConstraints qqb_list_constraints = new GridBagConstraints();
				
				for (int i = 0; i < QQB.size(); i++){
					final int q_qb_index = i;
					String q_qb_name = QQB.get(q_qb_index).getName();
					JButton q_qb_btn = new JButton(q_qb_name) {{setBackground(Color.RED);}};
					class ExportQuizSelectorAction implements ActionListener {
						public void actionPerformed(ActionEvent e) {
							if (Modified_QQB.contains(QQB.get(q_qb_index))) {
								Modified_QQB.remove(QQB.get(q_qb_index));
								q_qb_btn.setBackground(Color.RED);
							} else {
								Modified_QQB.add(QQB.get(q_qb_index));
								q_qb_btn.setBackground(Color.GREEN);
							}
						}
					}
					q_qb_btn.addActionListener(new ExportQuizSelectorAction());

					JPanel q_qb_panel = new JPanel();
					q_qb_panel.add(q_qb_btn);

					qqb_list_constraints.gridx = 0;
					qqb_list_constraints.gridy = i;
					qqb_listing.add(q_qb_panel, qqb_list_constraints);
				}
				if (QQB.size() == 0) qqb_listing.add(new JLabel("No Quizzes Found. Please import or create a quiz to begin."));

				//Add elements to frame
				export_constraints.fill = GridBagConstraints.BOTH;
				
				export_constraints.gridx = 0;
				export_constraints.weightx = 0.1;
				export_constraints.gridy = 0;
				export_constraints.weighty = 0.1;
				export_frame.add(new JLabel(), export_constraints);
				
				export_constraints.gridx = 0;
				export_constraints.weightx = 0.1;
				export_constraints.gridy = 2;
				export_constraints.weighty = 0.1;
				export_frame.add(export_cfm_btn, export_constraints);

				export_constraints.gridx = 2;
				export_constraints.weightx = 0.1;
				export_constraints.gridy = 2;
				export_constraints.weighty = 0.1;
				export_frame.add(export_cnl_btn, export_constraints);

				export_constraints.gridx = 1;
				export_constraints.weightx = 0.9;
				export_constraints.gridy = 1;
				export_constraints.weighty = 0.9;
				export_frame.add(new JScrollPane(qqb_listing), export_constraints);

				export_frame.setVisible(true);
			}
		}
		export_mi.addActionListener(new ExportAction());

		//File -> Select Test
		JMenuItem select_mi = menu.buildMenuItem("Select Test", KeyEvent.VK_S, file_menu);
		class SelectAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				new SelectQuizScreen();
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		}
		select_mi.addActionListener(new SelectAction());
		
		//TODO: For someone else, need to implement a login functionality to handle login, authentication, and any username/pfp stuff. 
		//		Alternatively, we can scrap this and just not display a username or pfp or anything like that. Relatively easy to cut.

		//TODO: For someone else, get the username somehow
		String username = "";

		//TODO: For someone else, get the username's profile picture
		String pfp_path = "";

		//Create Import QTI File Button
		JButton import_btn = new JButton("Import QTI Files");
		import_btn.addActionListener(new ImportAction());

		//Create Export QTI File Button
		JButton export_btn = new JButton("Export QTI File");
		export_btn.addActionListener(new ExportAction());

		//Create Create New Test Button
		JButton new_test_btn = new JButton("Create New Test");

		//Add elements to frame
		constraints.fill = GridBagConstraints.BOTH;
		
		//Use a 5x2 content area, divider between, with padding around it for a 7x5
		
		//Add padding around of 10% top, 10% left, 20% bottom, 10% right, and a middle divider of 10%
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

		//Column 1:
		constraints.gridx = 1;

		//Add PFP
		constraints.gridy = 1;
		frame.add(new JLabel(new ImageIcon(pfp_path)), constraints);

		//Add Username display
		constraints.gridy = 2;
		frame.add(new JLabel("Welcome, " + username + "!"), constraints);

		//Add Create New Test Button
		constraints.gridy = 3;
		frame.add(new_test_btn, constraints);

		//Add Import Button
		constraints.gridy = 4;
		frame.add(import_btn, constraints);

		//Add Export Button
		constraints.gridy = 5;
		frame.add(export_btn, constraints);

		//Column 2:
		constraints.gridx = 3;

		//TODO: Add document information
		constraints.gridy = 2;
		frame.add(new JLabel("Recent Documents:"), constraints);

		//View the Welcome Screen frame
		frame.setVisible(true);
    }
}
