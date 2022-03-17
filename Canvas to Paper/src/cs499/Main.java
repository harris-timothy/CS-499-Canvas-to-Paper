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

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

import org.w3c.dom.Document;

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

		//Create the menus within the menu bar

		//Create the File Menu
		JMenu welc_file_menu = new JMenu("File");
		JMenuItem welc_import_mi = new JMenuItem("Import QTI File");
		welc_import_mi.setMnemonic(KeyEvent.VK_I);
		welc_file_menu.add(welc_import_mi);
		JMenuItem welc_export_mi = new JMenuItem("Export QTI File");
		welc_export_mi.setMnemonic(KeyEvent.VK_E);
		welc_file_menu.add(welc_export_mi);
		welc_menu_bar.add(welc_file_menu);
		welc_frame.setJMenuBar(welc_menu_bar);
		
		String Test = FileSelect();
		System.out.println(Test);

		//View the Welcome Screen frame
		welc_frame.setVisible(true);
	}

	private static String FileSelect() {
			try {
				FileDialog browser = new FileDialog(new JFrame());
				browser.setTitle("Please select a zipped quiz to export.");
				browser.setVisible(true);
				File[] f = browser.getFiles();
				if(f.length > 0){
    				return browser.getFiles()[0].getAbsolutePath();
				}
			} catch (Exception e) {
				return e.toString();
			}
			return "If you've encountered this, the program has logically not functioned. This should not be possible to encounter. Please cry.";
	}
}