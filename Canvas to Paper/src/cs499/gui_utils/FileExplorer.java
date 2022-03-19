package cs499.gui_utils;

import java.awt.FileDialog;
import java.io.File;
import javax.swing.JFrame;

public class FileExplorer {
    /**
	 * Opens a file explorer frame to cause the user to select a file
	 * @return The file path that the user selected
	 */
	public String FileSelect() {
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
	public String[] FilesSelect() {
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
