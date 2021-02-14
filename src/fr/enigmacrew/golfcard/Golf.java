package fr.enigmacrew.golfcard;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.enigmacrew.golfcard.audio.musics.AudioPaths;
import fr.enigmacrew.golfcard.audio.musics.Music;

public class Golf extends JFrame {
	private static final long serialVersionUID = 1L;
	

	//**************************************************************************
	// Golf settings
	
	private Golf golf;
	
	//**************************************************************************
	// Components and settings
	
	private JPanel menuPanel = new JPanel();
	
	private final int DEFAULT_WIDTH = 1000;
	private final int DEFAULT_HEIGHT = 600;
	private final int MIN_WIDTH = 500;
	private final int MIN_HEIGHT = 300;
	
	private Timer timer;
	
	public Golf() {
		
		/*
		 * Create the frame of the golf game.
		 */
		
		//**************************************************************************
		// Initializing frame

		golf = this;
		setTitle("Golf");
		setLayout(null);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				updateComponents();
		    }
		});
		addWindowListener(new WindowAdapter() {
	    	public void windowClosed(WindowEvent e) {
	    		if(golf.isVisible()){
	    			System.exit(0);
	    		}
	    	}
		});
		
		//**************************************************************************
		// Creating components
		
		menuPanel.setLayout(null);
		menuPanel.setLocation(0, 0);
		
		updateComponents();
		
		//**************************************************************************
		// Adding components
		
		add(menuPanel);
		
		//**************************************************************************
		// Start music
		
		if(Music.music != null)
			Music.music.close();
		Music.playMusic(AudioPaths.Theme01);
		
		//**************************************************************************
		// Check music
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			/*
			 * Mute the game if it is not the focused program
			 */
			
			@Override
			public void run() {
				while(golf != null) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(golf.isActive())
						Music.refreshVolume();
					else
						Music.setVolume(0);
				}
			}
		}, 1);
	}
	
	//**************************************************************************
	// Functions
	
	private void updateComponents() {
		
		/*
		 * Update the components size and font when the window is resized
		 */
		
		menuPanel.setSize(getWidth(), getHeight());
		
	}
}
