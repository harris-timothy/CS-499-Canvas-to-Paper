package cs499.screens;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import cs499.Reporting;
import cs499.gui_utils.FrameBuilder;
import cs499.gui_utils.MenuBuilder;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

import java.awt.GridBagConstraints;
import javax.swing.JTable;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class ReportingScreen {

	private JPanel contentPane;
	private JTable generationsTable;
	private JTable detailsTable;
	private JLabel generationLabel;
	private JLabel detailLabel;
	private JButton backBtn;

	
	/**
	 * Create the frame.
	 */
	public ReportingScreen() {
		
		String frame_title = "CS 499-01 Spring 2022 CtPP Project Prototype-01";
		String logo_icon_path = "Canvas to Paper/lib/images/logo_icon.png";
		
		MenuBuilder menu = new MenuBuilder();
		FrameBuilder maker = new FrameBuilder();
		JMenuBar menu_bar = new JMenuBar();
		
		JFrame frame = maker.buildFrame(
				frame_title, 
				JFrame.DISPOSE_ON_CLOSE, 
				1000, 800, 
				menu_bar, 
				logo_icon_path
			);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{40, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{81, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		generationLabel = new JLabel("Generated quizzes");
		GridBagConstraints gbc_generationLabel = new GridBagConstraints();
		gbc_generationLabel.insets = new Insets(0, 0, 5, 5);
		gbc_generationLabel.gridx = 1;
		gbc_generationLabel.gridy = 0;
		contentPane.add(generationLabel, gbc_generationLabel);
		
		detailLabel = new JLabel();
		GridBagConstraints gbc_detailLabel = new GridBagConstraints();
		gbc_detailLabel.insets = new Insets(0, 0, 5, 5);
		gbc_detailLabel.gridx = 4;
		gbc_detailLabel.gridy = 0;
		contentPane.add(detailLabel, gbc_detailLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		Reporting reporting = new Reporting();
		generationsTable = new JTable();
		Object[] columns = {" "};
		DefaultTableModel model = new DefaultTableModel(new Object[0][0], columns);
		for(String title:reporting.getMetaTitles()) {
			Object[] o = new Object[1];
			o[0] = title;
			model.addRow(o);
 		}
		generationsTable.setModel(model);
		generationsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = generationsTable.getSelectedRow();
				System.out.println(model.getValueAt(index, 0));
			}
		});
		generationsTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(generationsTable);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridheight = 4;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 4;
		gbc_scrollPane_1.gridy = 1;
		contentPane.add(scrollPane_1, gbc_scrollPane_1);
		
		detailsTable = new JTable();
		detailsTable.setFillsViewportHeight(true);
		scrollPane_1.setViewportView(detailsTable);
		
		backBtn = new JButton("<- Back");
		GridBagConstraints gbc_backBtn = new GridBagConstraints();
		gbc_backBtn.gridx = 5;
		gbc_backBtn.gridy = 5;
		contentPane.add(backBtn, gbc_backBtn);
		
		class BackAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				new WelcomeScreen();
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));;
			}
		}
		backBtn.addActionListener(new BackAction());
	}

}
