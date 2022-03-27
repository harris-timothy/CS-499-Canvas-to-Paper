package cs499.screens;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import java.util.ArrayList;

import org.apache.xmlbeans.impl.xb.xsdschema.ImportDocument.Import;
import org.w3c.dom.Document;

import cs499.ParseQTI;
import cs499.gui_utils.FileExplorer;
import cs499.gui_utils.FrameBuilder;
import cs499.gui_utils.MenuBuilder;

public class WelcomeScreen {
    public WelcomeScreen(){
        String frame_title = "CS 499-01 Spring 2022 CtPP Project Prototype-01";
		String logo_icon_path = "Canvas to Paper/lib/images/logo_icon.png";
		
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
				ArrayList<Document> tempArray = new ArrayList<Document>();

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
					tempArray = qti.xmlLoop("QTITest");
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

		//Add File Menu to Menu Bar
		menu_bar.add(file_menu);

		//Initialize the Welcome Screen frame of the application
		FrameBuilder maker = new FrameBuilder();
		JFrame frame = maker.buildFrame(
			frame_title, 
			JFrame.EXIT_ON_CLOSE, 
			1000, 800, 
			menu_bar, 
			logo_icon_path
		);
		frame.setLayout(new GridBagLayout());

		//Create Import QTI File Button
		JButton import_btn = new JButton("Import QTI Files");
		import_btn.addActionListener(new ImportAction());

		//Create Export QTI File Button
		JButton export_btn = new JButton("Export QTI File");
		export_btn.addActionListener(new ExportAction());

		GridBagConstraints constraints = new GridBagConstraints();

		//Add elements to frame
		constraints.weightx = 0.25;
		constraints.weighty = 0.25;
		constraints.fill = GridBagConstraints.BOTH;

		constraints.gridx = 0;
		constraints.gridy = 0;
		frame.add(new JLabel(), constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.insets = new Insets(30,30,0,30);
		frame.add(import_btn, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.insets = new Insets(0,30,30,30);
		frame.add(export_btn, constraints);

		constraints.gridx = 2;
		constraints.gridy = 3;
		frame.add(new JLabel(), constraints);

		constraints.gridx = 3;
		constraints.gridy = 3;
		frame.add(new JLabel(), constraints);

		//View the Welcome Screen frame
		frame.setVisible(true);
    }
}
