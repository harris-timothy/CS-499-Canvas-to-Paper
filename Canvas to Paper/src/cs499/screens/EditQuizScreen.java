package cs499.screens;

import javax.swing.JFrame;

import cs499.Quiz;
import cs499.gui_utils.FrameBuilder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class EditQuizScreen {
    private JFrame frame;
    public EditQuizScreen(Quiz quiz){
        String frame_title = "CS 499-01 Spring 2022 CtPP Project Prototype-01";
        String logo_icon_path = "Canvas to Paper/lib/images/logo_icon.png";

        //Initialize the frame
        FrameBuilder maker = new FrameBuilder();
		frame = maker.buildFrame(
			frame_title, 
			JFrame.DISPOSE_ON_CLOSE, 
			500, 400, 
			null, 
			logo_icon_path
		);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        
        frame.setVisible(true);
    }
}
