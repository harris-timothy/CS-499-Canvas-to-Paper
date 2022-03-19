package cs499;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

import org.w3c.dom.Document;

import cs499.gui_utils.FileExplorer;
import cs499.gui_utils.FrameMaker;

public class Main {

	public static void main(String[] args) {
		
		String welc_frame_title = "CS 499-01 Spring 2022 CtPP Project Prototype-01";
		String logo_icon_path = "Canvas to Paper/lib/images/logo_icon.png";
		
		//Create the menu bar at the top of the Welcome Screen frame
		final JMenuBar welc_menu_bar = new JMenuBar();

		//Create the File Menu
		JMenu welc_file_menu = new JMenu("File");

		//Create File -> Import QTI File
		JMenuItem welc_import_mi = new JMenuItem("Import QTI File");
		welc_import_mi.setMnemonic(KeyEvent.VK_I);
		
		//Create Import QTI File Action Handler
		class ImportAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				FileExplorer explorer = new FileExplorer();
				String[] ImportZip = explorer.FilesSelect();
				for (String filepath : ImportZip) {
					if (!filepath.contains(".zip")) {
						ImportZip = new String[0];
					}
				}
				while (ImportZip.length <= 0) {
					ImportZip = explorer.FilesSelect();
					for (String filepath : ImportZip) {
						if (!filepath.contains(".zip")) {
							ImportZip = new String[0];
						}
					}
				}
				ParseQTI qti = new ParseQTI();
				ArrayList<Document> tempArray = new ArrayList<Document>();
				try {
					for (String filepath : ImportZip) {
						qti.unzip(filepath, "QTITest");
					}
					tempArray = qti.xmlLoop("QTITest");
					System.out.println(tempArray.size());
					for(Document doc : tempArray)
					{
						System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		welc_import_mi.addActionListener(new ImportAction());
		
		//Create File -> Export QTI File
		JMenuItem welc_export_mi = new JMenuItem("Export QTI File");
		welc_export_mi.setMnemonic(KeyEvent.VK_E);
		
		//Create Export QTI File Action Handler
		class ExportAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				//TODO: Export behavior
				System.out.println("Export QTI File Button Pressed.");
			}
		}
		welc_export_mi.addActionListener(new ExportAction());
		
		//Add File Menu Menu Items to File Menu 
		welc_file_menu.add(welc_import_mi);
		welc_file_menu.add(welc_export_mi);

		//Add Menus to Menu Bar
		welc_menu_bar.add(welc_file_menu);

		//Initialize the Welcome Screen frame of the application
		FrameMaker maker = new FrameMaker();
		JFrame welc_frame = maker.frameMake(
			welc_frame_title, 
			JFrame.EXIT_ON_CLOSE, 
			1000, 800, 
			welc_menu_bar, 
			logo_icon_path
		);
		welc_frame.setLayout(new GridLayout(3,2));

		//View the Welcome Screen frame
		welc_frame.setVisible(true);
	}

	
}