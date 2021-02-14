package fr.enigmacrew.golfcard;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Golf extends JFrame {
	private static final long serialVersionUID = 1L;
	

	//**************************************************************************
	// Golf settings
	
	private Golf golf;
	
	//**************************************************************************
	// Components and settings
	
	private final int DEFAULT_WIDTH = 1000;
	private final int DEFAULT_HEIGHT = 600;
	private final int MIN_WIDTH = 500;
	private final int MIN_HEIGHT = 300;
	
	public Golf() {
		
		/*
		 * Create the frame of the golf game.
		 */
		
		//**************************************************************************
		// Initializing frame
		
		setTitle("Golf");
		setLayout(null);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				//updateComponents();
		    }
		});
		addWindowListener(new WindowAdapter() {
	    	public void windowClosed(WindowEvent e) {
	    		if(golf.isVisible()){
	    			System.exit(0);
	    		}
	    	}
		});
	}
	
}
