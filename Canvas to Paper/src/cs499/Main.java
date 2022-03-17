package cs499;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.FileDialog;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		
		String welc_frame_title = "CS 499-01 Spring 2022 CtPP Project Prototype-01";
		String welc_frame_icon_path = "Canvas to Paper/lib/images/welc_frame_icon.png";
		
		//Initialize the Welcome Screen frame of the application
		JFrame welc_frame = new JFrame(welc_frame_title);
		welc_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // EXIT_ON_CLOSE terminates the entire program, not just the window
		welc_frame.setSize(600, 400);

		//Create an image of the icon path and set the icon of the Welcome Screen frame
		Image welc_frame_icon = Toolkit.getDefaultToolkit().createImage(welc_frame_icon_path);
		welc_frame.setIconImage(welc_frame_icon);
		welc_frame.setLayout(new GridLayout(3,2));

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
				//TODO: Import behavior
				String Test = FilesSelect()[0];
				System.out.println(Test);
			}
		}
		welc_import_mi.addActionListener(new ImportAction());
		welc_file_menu.add(welc_import_mi);

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
		welc_file_menu.add(welc_export_mi);
		welc_menu_bar.add(welc_file_menu);
		welc_frame.setJMenuBar(welc_menu_bar);

		//View the Welcome Screen frame
		welc_frame.setVisible(true);
	}

	/**
	 * Opens a file explorer frame to cause the user to select a file
	 * @return The file path that the user selected
	 */
	private static String FileSelect() {
		try {
			FileDialog browser = new FileDialog(new JFrame());
			browser.setMultipleMode(false);
			browser.setTitle("Please select a zipped quiz to export.");
			browser.setVisible(true);
			File[] f = browser.getFiles();
			if(f.length > 0){
				return browser.getFiles()[0].getAbsolutePath();
			}
		} catch (Exception e) {
			return e.toString();
		}
		return "User did not select a file.";
	}
	/**
	 * Opens a file explorer frame to cause the user to select multiple files
	 * @return An array of all file paths that the user selected
	 */
	private static String[] FilesSelect() {
		try {
			FileDialog browser = new FileDialog(new JFrame(), "Please select a zipped quiz to export.");
			browser.setMultipleMode(true);
			browser.setVisible(true);
			File[] f = browser.getFiles();
			if(f.length > 0){
				String[] filepaths = new String[f.length];
				for (int i = 0; i < f.length; i++) {
					filepaths[i] = browser.getFiles()[i].getAbsolutePath();
				}
				return filepaths;
			}
		} catch (Exception e) {
			String[] errors = new String[1];
			errors[0] = e.toString();
			return errors;
		}
		String[] failures = new String[1];
		failures[0] = "User did not select a file.";
		return failures;
	}
}