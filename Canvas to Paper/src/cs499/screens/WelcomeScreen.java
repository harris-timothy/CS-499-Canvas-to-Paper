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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.util.ArrayList;
import java.util.HashMap;

import cs499.gui_utils.FileExplorer;
import cs499.gui_utils.FrameBuilder;
import cs499.gui_utils.MenuBuilder;
import cs499.qti.CreateQTI;
import cs499.qti.ParseQTI;
import cs499.Quiz;
import cs499.RecentItems;
import cs499.utils.DataUtils;

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
				
				//Obtain a list of all quizzes in the database
				ArrayList<Quiz> quiz_list = DataUtils.getAllQuizzes();

				//Create a modified list of these quizzes containing only the ones to export
				ArrayList<Quiz> modified_quiz_list = new ArrayList<Quiz>();
				
				JButton export_cfm_btn = new JButton("Export");
				class ExportConfirmAction implements ActionListener {
					public void actionPerformed(ActionEvent e) {
						CreateQTI qti_maker = new CreateQTI();
						try {
							String export_directory_path = "Exports/" + System.currentTimeMillis();
							if (modified_quiz_list.size() != 0) qti_maker.createPackage(modified_quiz_list, export_directory_path);
						} catch (Exception ex) {
							System.out.println(ex);
						}
						export_frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					}
				}
				export_cfm_btn.addActionListener(new ExportConfirmAction());
				
				JButton export_cnl_btn = new JButton("Cancel");
				class ExportCancelAction implements ActionListener {
					public void actionPerformed(ActionEvent e) {
						export_frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					}
				}
				export_cnl_btn.addActionListener(new ExportCancelAction());
				
				JPanel quiz_list_listing = new JPanel();
				quiz_list_listing.setLayout(new GridBagLayout());
				GridBagConstraints quiz_list_list_constraints = new GridBagConstraints();
				
				for (int i = 0; i < quiz_list.size(); i++){
					final int quiz_index = i;
					String quiz_name = quiz_list.get(quiz_index).getName();

					JButton quiz_btn = new JButton(quiz_name);
					quiz_btn.setBackground(Color.RED);
					class ExportQuizSelectorAction implements ActionListener {
						public void actionPerformed(ActionEvent e) {
							if (modified_quiz_list.contains(quiz_list.get(quiz_index))) {
								modified_quiz_list.remove(quiz_list.get(quiz_index));
								quiz_btn.setBackground(Color.RED);
							} else {
								modified_quiz_list.add(quiz_list.get(quiz_index));
								quiz_btn.setBackground(Color.GREEN);
							}
						}
					}
					quiz_btn.addActionListener(new ExportQuizSelectorAction());

					JPanel quiz_panel = new JPanel();
					quiz_panel.add(quiz_btn);

					quiz_list_list_constraints.gridx = 0;
					quiz_list_list_constraints.gridy = i;
					quiz_list_listing.add(quiz_panel, quiz_list_list_constraints);
				}
				if (quiz_list.size() == 0) quiz_list_listing.add(new JLabel("No Quizzes Found. Please import or create a quiz to begin."));

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
				export_frame.add(new JScrollPane(quiz_list_listing), export_constraints);

				export_frame.setVisible(true);
			}
		}
		export_mi.addActionListener(new ExportAction());

		JMenu edit_menu = menu.buildMenu("Edit", KeyEvent.VK_E);
		menu_bar.add(edit_menu);
		
		//File -> Generate Test From Quiz
		JMenuItem gen_mi = menu.buildMenuItem("Generate Test from Quiz", KeyEvent.VK_G, file_menu);
		class GenerationSelectAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				new GenerationSelectScreen();
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		}
		gen_mi.addActionListener(new GenerationSelectAction());

		//Edit -> Select Quiz
		JMenuItem select_mi = menu.buildMenuItem("Select Quiz", KeyEvent.VK_S, edit_menu);
		class SelectAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				new SelectQuizScreen();
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		}
		select_mi.addActionListener(new SelectAction());

		//Create Import QTI File Button
		JButton import_btn = new JButton("Import QTI Files");
		import_btn.addActionListener(new ImportAction());

		//Create Export QTI File Button
		JButton export_btn = new JButton("Export QTI File");
		export_btn.addActionListener(new ExportAction());

		//Create Create New Test Button
		JButton create_quiz_btn = new JButton("Create New Test");
		class CreateQuizAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Quiz new_quiz = new Quiz();
				new EditQuizScreen(new_quiz);
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		}
		create_quiz_btn.addActionListener(new CreateQuizAction());
		
		RecentItems recent = new RecentItems();
		JTable recent_table = new JTable();
		Object[] columns = {" "};
		DefaultTableModel model = new DefaultTableModel(new Object[0][0],columns);
		for(HashMap<String,String> map: recent.getRecentQuizzes()) {
			Object[] o = new Object[1];
			o[0] = map.get("quiz_name");
			model.addRow(o);
		}
		recent_table.setModel(model);
		recent_table.setRowHeight(80);
		JScrollPane table_pane = new JScrollPane(recent_table);
		table_pane.setPreferredSize(new Dimension(200, 425));

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
		frame.add(new JLabel(new ImageIcon(logo_icon_path)), constraints);

		//Add Username display
		constraints.gridy = 2;
		frame.add(new JLabel("Welcome!"), constraints);

		//Add Create New Test Button
		constraints.gridy = 3;
		frame.add(create_quiz_btn, constraints);

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
		frame.add(new JLabel("Recent Tests:"), constraints);
		
		constraints.gridy = 3;
		constraints.gridheight = 3;
		constraints.fill = GridBagConstraints.NONE;
		frame.add(table_pane, constraints);

		//View the Welcome Screen frame
		frame.setVisible(true);
    }
}
