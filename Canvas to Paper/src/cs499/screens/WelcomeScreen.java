package cs499.screens;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import cs499.qti.ParseQTI;
import cs499.gui_utils.FileExplorer;
import cs499.gui_utils.FrameBuilder;
import cs499.gui_utils.MenuBuilder;

public class WelcomeScreen {
    public WelcomeScreen(){
        String frame_title = "CS 499-01 Spring 2022 CtPP Project Prototype-01";
		String logo_icon_path = "Canvas to Paper/lib/images/logo_icon.png";
		JFrame frame;

		//Initialize gui helper classes
		MenuBuilder menu = new MenuBuilder();
		FileExplorer explorer = new FileExplorer();

		//Create Menu Bar
		JMenuBar menu_bar = new JMenuBar();

		//Create the File Menu
		JMenu file_menu = menu.buildMenu("File", KeyEvent.VK_F);
		
		//Create File -> Import QTI File
		JMenuItem import_mi = menu.buildMenuItem("Import QTI Files", KeyEvent.VK_I, file_menu);
		
		//Create Import QTI File Action Handler
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
					//TODO: Ensure functionality
					qti.xmlLoop("QTITest");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		import_mi.addActionListener(new ImportAction());
		
		//Create File -> Export QTI File
		JMenuItem export_mi = menu.buildMenuItem("Export QTI File", KeyEvent.VK_E, file_menu);
		
		//Create Export QTI File Action Handler
		class ExportAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				//TODO: Export behavior
				System.out.println("Export QTI File Button Pressed.");
			}
		}
		export_mi.addActionListener(new ExportAction());

		//Create File -> Select Test
		JMenuItem select_mi = menu.buildMenuItem("Select Test", KeyEvent.VK_S, file_menu);

		//Add File Menu to Menu Bar
		menu_bar.add(file_menu);

		//Initialize the Welcome Screen frame of the application
		FrameBuilder maker = new FrameBuilder();
		frame = maker.buildFrame(
			frame_title, 
			JFrame.DISPOSE_ON_CLOSE, 
			1000, 800, 
			menu_bar, 
			logo_icon_path
		);
		frame.setLayout(new GridBagLayout());
		
		//Create Select Test Action Handler
		class SelectAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				new SelectQuizScreen();
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));;
			}
		}
		select_mi.addActionListener(new SelectAction());

		//Create Profile Picture Image
		String pfp_path = "";
		pfp_path = logo_icon_path;
		//TODO: Set pfp_path to username's profile picture

		String username = "";
		username = "USERNAME";
		//TODO: Get the username somehow

		//Create Import QTI File Button
		JButton import_btn = new JButton("Import QTI Files");
		import_btn.addActionListener(new ImportAction());

		//Create Export QTI File Button
		JButton export_btn = new JButton("Export QTI File");
		export_btn.addActionListener(new ExportAction());

		//Create Create New Test Button
		JButton new_test_btn = new JButton("Create New Test");

		//Create Constraints Guide
		GridBagConstraints constraints = new GridBagConstraints();

		//Add elements to frame
		constraints.fill = GridBagConstraints.BOTH;
		
		//Use a 5x2 content area, divider between, with padding around it for a 7x5
		
		//Add padding around of 10% top, 10% left, 20% bottom, 10% right, and a middle divider of 10%
		constraints.gridx = 0;
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
