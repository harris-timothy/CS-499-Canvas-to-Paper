package cs499.gui_utils;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.lang3.ArrayUtils;

import javax.swing.JFileChooser;

public class FileExplorer {
    /**
	 * Opens a file explorer frame to cause the user to select a file
	 * @param accepted_formats A string array which begins with first element "Title of format types accepted" and 
	 * 		is followed by elements of the file extensions without the '.', such as "doc" or "txt"
	 * @return The file path that the user selected; "Blank" if no selection; "Error" if error;
	 */
	public String FileSelect(String[] accepted_formats) {
		JFileChooser browser = new JFileChooser();
		browser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		browser.setDialogTitle("Please select a zipped quiz to export.");
		browser.setMultiSelectionEnabled(false);
		String[] extensions = ArrayUtils.remove(accepted_formats, 0);
		browser.setFileFilter(new FileNameExtensionFilter(accepted_formats[0], extensions));
		int option_selection = browser.showDialog(new JFrame(), "Select");
		switch (option_selection){
			case JFileChooser.APPROVE_OPTION:
				return browser.getSelectedFile().getAbsolutePath();
			case JFileChooser.CANCEL_OPTION:
				return "Blank";
			case JFileChooser.ERROR_OPTION:
				return "Error";
			default:
				return "Blank";
		}
	}
	/**
	 * Opens a file explorer frame to cause the user to select a directory
	 * @return The directory path that the user selected; "Blank" if no selection; "Error" if error;
	 */
	public String DirectorySelect() {
		JFileChooser browser = new JFileChooser();
		browser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		browser.setDialogTitle("Please select the directory to create the file within.");
		int option_selection = browser.showDialog(new JFrame(), "Select");
		switch (option_selection){
			case JFileChooser.APPROVE_OPTION:
				return browser.getSelectedFile().getAbsolutePath();
			case JFileChooser.CANCEL_OPTION:
				return "Blank";
			case JFileChooser.ERROR_OPTION:
				return "Error";
			default:
				return "Blank";
		}
	}
	/**
	 * Opens a file explorer frame to cause the user to select multiple files
	 * @param accepted_formats A string array which begins with first element "Title of format types accepted" and 
	 * 		is followed by elements of the file extensions without the '.', such as "doc" or "txt"
	 * @return An array of all file paths that the user selected; If no selection, array of one element "Blank".
	 * 		If error, array with single value "Error".
	 */
	public String[] FilesSelect(String[] accepted_formats) {
		JFileChooser browser = new JFileChooser();
		browser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		browser.setDialogTitle("Please select some zipped quizzes to export.");
		browser.setMultiSelectionEnabled(true);
		String[] extensions = ArrayUtils.remove(accepted_formats, 0);
		browser.setFileFilter(new FileNameExtensionFilter(accepted_formats[0], extensions));
		int option_selection = browser.showDialog(new JFrame(), "Select");
		String[] blank = {"Blank"};
		String[] error = {"Error"};
		switch (option_selection){
			case JFileChooser.APPROVE_OPTION:
				File[] files = browser.getSelectedFiles();
				String[] file_paths = new String[files.length];
				if (files.length <= 0) return blank;
				for (int i = 0; i < files.length; i++) {
					file_paths[i] = files[i].getAbsolutePath();
				}
				return file_paths;
			case JFileChooser.CANCEL_OPTION:
				return blank;
			case JFileChooser.ERROR_OPTION:
				return error;
			default:
				return blank;
		}
	}
}
