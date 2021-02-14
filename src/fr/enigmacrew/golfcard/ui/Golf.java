package fr.enigmacrew.golfcard.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.enigmacrew.golfcard.Config;
import fr.enigmacrew.golfcard.audio.musics.AudioPaths;
import fr.enigmacrew.golfcard.audio.musics.Music;
import fr.enigmacrew.golfcard.game.Game;

public class Golf extends JFrame {
	private static final long serialVersionUID = 1L;
	

	//**************************************************************************
	// Golf settings
	
	private Golf golf;
	public int sixOrNine;
	private Game game;
	
	public int drawTrashTurn;
	
	//**************************************************************************
	// Components and settings
	
	private JPanel menuPanel = new JPanel();
	public GamePanel gamePanel = new GamePanel();
	private JPanel configPanel = new JPanel();
	private Color configColor = new Color(38, 127, 0);
	
	private JSlider volumeSlider = new JSlider();
	
	private final int DEFAULT_WIDTH = 1000;
	private final int DEFAULT_HEIGHT = 600;
	private final int MIN_WIDTH = 650;
	private final int MIN_HEIGHT = 450;
	
	private Timer timer;
	
	public Golf() {
		
		/*
		 * Create the frame of the golf game.
		 */
		
		//**************************************************************************
		// Initializing frame

		golf = this;
		sixOrNine = 6;
		
		drawTrashTurn = 3; // Turn
		
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
		
		//**********************
		// Panels
		
		menuPanel.setLayout(null);
		menuPanel.setLocation(0, getHeight()/30);
		
		gamePanel.setLayout(null);
		gamePanel.setLocation(0, getHeight()/30);
		menuPanel.setVisible(false);
		
		configPanel.setLayout(null);
		configPanel.setLocation(0, 0);
		configPanel.setBackground(configColor);
		
		//**********************
		// Config components
		
		volumeSlider.setOpaque(false);
		volumeSlider.setMinimum(0);
		volumeSlider.setMaximum(75);
		volumeSlider.setValue((int) (Config.VOLUME * 75));
		volumeSlider.setMajorTickSpacing(1);
		volumeSlider.setBackground(configColor);
		volumeSlider.setForeground(configColor);
		volumeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				Config.VOLUME = (float) volumeSlider.getValue() / 75;
				volumeSlider.setValue(volumeSlider.getValue());
				Music.refreshVolume();
			}
		});

		game = new Game(6, true);
		updateComponents();
		
		//**************************************************************************
		// Adding components
		
		configPanel.add(volumeSlider);
		
		add(configPanel);
		add(gamePanel);
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
		
		//**********************
		// Panels

		menuPanel.setLocation(0, getHeight()/30);
		menuPanel.setSize(getWidth(), getHeight() - getHeight()/30);
		gamePanel.setLocation(0, getHeight()/30);
		gamePanel.setSize(getWidth(), getHeight() - getHeight()/30);
		gamePanel.repaint();
		configPanel.setSize(getWidth(), getHeight()/30);
		
		//**********************
		// Config components
		
		volumeSlider.setLocation(getWidth() - getWidth()/8, getHeight()/110);
		volumeSlider.setSize(getWidth()/10, getHeight()/50);
		
		//**********************
		// Cards
		
		CardPanel.redrawCard(golf, game);
	}
}
