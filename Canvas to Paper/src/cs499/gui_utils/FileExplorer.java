package cs499.gui_utils;

import java.awt.FileDialog;
import java.io.File;
import javax.swing.JFrame;

public class FileExplorer {
    /**
	 * Opens a file explorer frame to cause the user to select a file
	 * @return The file path that the user selected; "Blank" if no selection; "Error" if error;
	 */
	public String FileSelect() {
		try {
			FileDialog browser = new FileDialog(new JFrame());
			browser.setMultipleMode(false);
			browser.setTitle("Please select a zipped quiz to export.");
			browser.setVisible(true);
			File[] f = browser.getFiles();
			if(f.length <= 0){
				return "Blank";	
			} else {
				return browser.getFiles()[0].getAbsolutePath();
			}
		} catch (Exception e) {
			return "Error";
		}
	}
	/**
	 * Opens a file explorer frame to cause the user to select multiple files
	 * @return An array of all file paths that the user selected; Null array if no selection; Array with single value "" if error;
	 */
	public String[] FilesSelect() {
		try {
			FileDialog browser = new FileDialog(new JFrame(), "Please select a zipped quiz to export.");
			browser.setMultipleMode(true);
			browser.setVisible(true);
			File[] f = browser.getFiles();
			if(f.length <= 0){
				return null; 
			} else {
				String[] filepaths = new String[f.length];
				for (int i = 0; i < f.length; i++) {
					filepaths[i] = browser.getFiles()[i].getAbsolutePath();
				}
				return filepaths;
			}
		} catch (Exception e) {
			String[] error = {"Error"};
			return error;
		}
	}
}
