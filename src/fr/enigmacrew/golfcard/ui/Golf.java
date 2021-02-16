package fr.enigmacrew.golfcard.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.enigmacrew.golfcard.Config;
import fr.enigmacrew.golfcard.Const;
import fr.enigmacrew.golfcard.audio.musics.AudioPaths;
import fr.enigmacrew.golfcard.audio.musics.Music;
import fr.enigmacrew.golfcard.game.Game;
import fr.enigmacrew.golfcard.utils.Utils;

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
	public GamePanel gamePanel;
	private JPanel configPanel = new JPanel();
	public final Color configColor = new Color(38, 127, 0);
	
	private JSlider volumeSlider = new JSlider();
	public JButton reducedWinFrameButton = new JButton();
	
	public static final int DEFAULT_WIDTH = 1000;
	public static final int DEFAULT_HEIGHT = 600;
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
		sixOrNine = 9;
		
		drawTrashTurn = 3; // Turn
		
		setTitle("Golf");
		setLayout(null);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		setLocationRelativeTo(null);
		setIconImage(Const.IMAGE_CardBack.getImage());
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

		gamePanel = new GamePanel(golf);
		gamePanel.setLayout(null);
		gamePanel.setLocation(0, getHeight()/30);
		menuPanel.setVisible(false);
		
		configPanel.setLayout(null);
		configPanel.setLocation(0, 0);
		configPanel.setBackground(configColor);
		
		//**********************
		// Config components
		
		reducedWinFrameButton.setVisible(false);
		reducedWinFrameButton.setText("Scores and Options [+]");
		reducedWinFrameButton.setLocation(0, 0);
		reducedWinFrameButton.setBackground(configColor);
		reducedWinFrameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.winPanel.setVisible(true);
				reducedWinFrameButton.setVisible(false);
			}
		});
		
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

		game = new Game(sixOrNine);
		updateComponents();
		gamePanel.selectedCard.setVisible(false);
		
		//**************************************************************************
		// Adding components
		
		configPanel.add(reducedWinFrameButton);
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
	
	public void updateComponents() {
		
		/*
		 * Update the components size and font when the window is resized
		 */
		
		//**********************
		// Panels

		// Menu
		menuPanel.setLocation(0, getHeight()/30);
		menuPanel.setSize(getWidth(), getHeight() - getHeight()/30);
		
		// Game
		gamePanel.setLocation(0, getHeight()/30);
		gamePanel.setSize(getWidth(), getHeight() - getHeight()/30);
		gamePanel.repaint();
		
		// Config
		configPanel.setSize(getWidth(), getHeight()/30);
		
		// Win
		gamePanel.winPanel.setLocation(getWidth()/2 - (getWidth() - getWidth()/3)/4, getHeight()/2 - 
				(getHeight() - getHeight()/4)/2 + getHeight()/16);
		gamePanel.winPanel.setSize((getWidth()/3), getHeight()/2);
		
		//**********************
		// Game Panel components
		
		// Labels
		gamePanel.player1Label.setLocation(gamePanel.getWidth()/4, gamePanel.getHeight() - gamePanel.getHeight()/6);
		gamePanel.player1Label.setSize(gamePanel.getWidth()/8, gamePanel.getHeight()/20);
		gamePanel.player1Label.setFont(Utils.getUpdatedFont(getWidth() + getWidth()/2));
		
		gamePanel.player2Label.setLocation(gamePanel.getWidth() - gamePanel.getWidth()/4 - gamePanel.getWidth()/8, gamePanel.getHeight() - 
				gamePanel.getHeight()/6);
		gamePanel.player2Label.setSize(gamePanel.getWidth()/8, gamePanel.getHeight()/20);
		gamePanel.player2Label.setFont(Utils.getUpdatedFont(getWidth() + getWidth()/2));
		
		//**********************
		// Win Panel components
		
		// Buttons
		gamePanel.winPanel.closeButton.setLocation(gamePanel.winPanel.getWidth() - gamePanel.winPanel.getWidth()/20, 0);
		gamePanel.winPanel.closeButton.setSize(gamePanel.winPanel.getWidth()/20, gamePanel.winPanel.getHeight()/20);
		gamePanel.winPanel.closeButton.setIcon(Utils.resizeImage(Const.IMAGE_CloseButton, 
				gamePanel.winPanel.closeButton.getWidth(), gamePanel.winPanel.closeButton.getHeight()));
		
		gamePanel.winPanel.reduceButton.setLocation(gamePanel.winPanel.getWidth() - (gamePanel.winPanel.getWidth()/20)*2, 0);
		gamePanel.winPanel.reduceButton.setSize(gamePanel.winPanel.getWidth()/20, gamePanel.winPanel.getHeight()/20);
		gamePanel.winPanel.reduceButton.setIcon(Utils.resizeImage(Const.IMAGE_ReduceButton, 
				gamePanel.winPanel.reduceButton.getWidth(), gamePanel.winPanel.reduceButton.getHeight()));
		
		gamePanel.winPanel.replayButton.setLocation(0, gamePanel.winPanel.getHeight() - gamePanel.winPanel.getHeight()/4);
		gamePanel.winPanel.replayButton.setSize(gamePanel.winPanel.getWidth(), gamePanel.winPanel.getHeight()/8);
		gamePanel.winPanel.replayButton.setFont(Utils.getUpdatedFont(getWidth() + getWidth()/2));
		
		gamePanel.winPanel.replaySameButton.setLocation(0, gamePanel.winPanel.getHeight() - gamePanel.winPanel.getHeight()/8);
		gamePanel.winPanel.replaySameButton.setSize(gamePanel.winPanel.getWidth(), gamePanel.winPanel.getHeight()/8);
		gamePanel.winPanel.replaySameButton.setFont(Utils.getUpdatedFont(getWidth()));
		
		// Labels
		gamePanel.winPanel.winnerLabel.setLocation(gamePanel.winPanel.getWidth()/10, gamePanel.winPanel.getHeight()/15);
		gamePanel.winPanel.winnerLabel.setSize(gamePanel.winPanel.getWidth() - gamePanel.winPanel.getWidth()/8, 
				gamePanel.winPanel.getHeight()/10);
		gamePanel.winPanel.winnerLabel.setFont(Utils.getUpdatedFont(getWidth()*2));
		
		gamePanel.winPanel.scoreLabel.setLocation(gamePanel.winPanel.getWidth()/10, gamePanel.winPanel.getHeight()/5);
		gamePanel.winPanel.scoreLabel.setSize(gamePanel.winPanel.getWidth() - gamePanel.winPanel.getWidth()/8, 
				gamePanel.winPanel.getHeight()/10);
		gamePanel.winPanel.scoreLabel.setFont(Utils.getUpdatedFont(getWidth()));
		
		//**********************
		// Config components
		
		reducedWinFrameButton.setSize(getWidth()/3, getHeight()/30);
		reducedWinFrameButton.setFont(Utils.getUpdatedFont(getWidth()-getWidth()/3));
		
		volumeSlider.setLocation(getWidth() - getWidth()/8, getHeight()/110);
		volumeSlider.setSize(getWidth()/10, getHeight()/50);
		
		//**********************
		// Cards
		
		CardPanel.redrawCards(golf, game);
	}
}
