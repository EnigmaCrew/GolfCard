package fr.enigmacrew.golfcard.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	public boolean ai;
	public Game game;
	
	public int drawTrashTurn;
	
	//**************************************************************************
	// Components and settings
	
	public MenuPanel menuPanel;
	public GamePanel gamePanel;
	public JButton configButton;
	private JPanel configPanel = new JPanel();
	public ConfigPanel trueConfigPanel;
	public final Color configColor = new Color(38, 127, 0);
	
	private JLabel timeLabel;
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
		ai = false;
		
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
				if(menuPanel.isVisible())
					updateComponents(true);
				else
					updateComponents(false);
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
		
		menuPanel = new MenuPanel(golf);
		menuPanel.setLayout(null);
		menuPanel.setLocation(0, getHeight()/30);

		gamePanel = new GamePanel(golf);
		gamePanel.setLayout(null);
		gamePanel.setLocation(0, getHeight()/30);
		gamePanel.setVisible(false);
		
		configPanel.setLayout(null);
		configPanel.setLocation(0, 0);
		configPanel.setBackground(configColor);
		
		trueConfigPanel = new ConfigPanel(golf);
		
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
		
		timeLabel = new JLabel();
		timeLabel.setBackground(configColor);
		new Timer().schedule(new TimerTask() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				// Actualize the time every second
				Date date = new Date();
				timeLabel.setText(((date.getHours() < 10 ? "0" + date.getHours() : (date.getHours() + "")) + ":") + 
						(date.getMinutes() < 10 ? "0" + date.getMinutes() : (date.getMinutes() + "")));
			}
		}, 0, 1000);
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		timeLabel.setVerticalAlignment(JLabel.CENTER);
		
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
		
		configButton = new JButton();
		configButton.setBackground(Color.GRAY);
		configButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Reduce the frame and put the shortcut in the config panel
				if(trueConfigPanel.isVisible()) {
					configButton.setIcon(Utils.resizeImage(Const.IMAGE_HideButtonDown, configButton.getWidth(), configButton.getHeight()));
					trueConfigPanel.setVisible(false);
				}
				else {
					configButton.setIcon(Utils.resizeImage(Const.IMAGE_HideButtonUp, configButton.getWidth(), configButton.getHeight()));
					trueConfigPanel.setVisible(true);
				}
			}
		});
		
		golf.updateComponents(true);
		gamePanel.selectedCard.setVisible(false);
		
		//**************************************************************************
		// Adding components
		
		configPanel.add(reducedWinFrameButton);
		configPanel.add(timeLabel);
		configPanel.add(volumeSlider);
		configPanel.add(configButton);

		add(trueConfigPanel);
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
		
		//**************************************************************************
		// Key listener
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyListener());
	}
	
	//**************************************************************************
	// Functions
	
	public void updateComponents(boolean onlyMenu) {
		
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
		
		trueConfigPanel.setLocation(getWidth() - getWidth()/6, getHeight()/30);
		trueConfigPanel.setSize(getWidth()/6, getHeight()/7);
		
		// Win
		gamePanel.winPanel.setLocation(getWidth()/2 - (getWidth() - getWidth()/3)/4, getHeight()/2 - 
				(getHeight() - getHeight()/4)/2 + getHeight()/16);
		gamePanel.winPanel.setSize((getWidth()/3), getHeight()/2);
		
		//**********************
		// Menu Panel components
		
		// Logo Panel
		menuPanel.logoPanel.setLocation(getWidth()/6, gamePanel.getHeight()/6);
		menuPanel.logoPanel.setSize(getWidth() - getWidth()/3, gamePanel.getHeight()/3);
		
		// Buttons
		menuPanel.choiceButton1.setLocation(getWidth()/4 - getWidth()/12, getHeight() - getHeight()/4 - getHeight()/6);
		menuPanel.choiceButton1.setSize(getWidth()/6, getHeight()/6);
		menuPanel.choiceButton1.setFont(Utils.getUpdatedFont(getWidth()));
		
		menuPanel.choiceButton2.setLocation(getWidth() - getWidth()/4 - getWidth()/12, getHeight() - getHeight()/4 - getHeight()/6);
		menuPanel.choiceButton2.setSize(getWidth()/6, getHeight()/6);
		menuPanel.choiceButton2.setFont(Utils.getUpdatedFont(getWidth()));
		
		menuPanel.backExitButton.setLocation(getWidth()/2 - getWidth()/12, getHeight() - getHeight()/5 - getHeight()/6);
		menuPanel.backExitButton.setSize(getWidth()/6, getHeight()/6);
		menuPanel.backExitButton.setFont(Utils.getUpdatedFont(getWidth()));
		
		//**********************
		// Config components
		
		// Buttons
		
		trueConfigPanel.replayButton.setLocation(trueConfigPanel.getWidth()/40, trueConfigPanel.getHeight() - trueConfigPanel.getHeight()/3);
		trueConfigPanel.replayButton.setSize((trueConfigPanel.getWidth()/6)*5, trueConfigPanel.getHeight()/4);
		trueConfigPanel.replayButton.setFont(Utils.getUpdatedFont(getWidth() - getWidth()/5));
		
		trueConfigPanel.scoreCheckBox.setLocation(trueConfigPanel.getWidth()/40, trueConfigPanel.getHeight()/4);
		trueConfigPanel.scoreCheckBox.setSize((trueConfigPanel.getWidth()/6)*5, trueConfigPanel.getHeight()/4);
		trueConfigPanel.scoreCheckBox.setFont(Utils.getUpdatedFont(getWidth() - getWidth()/5));
		
		reducedWinFrameButton.setSize(getWidth()/3, getHeight()/30);
		reducedWinFrameButton.setFont(Utils.getUpdatedFont(getWidth()-getWidth()/3));
		
		timeLabel.setLocation(getWidth()/2 - getWidth()/20, getHeight()/350);
		timeLabel.setSize(getWidth()/10, getHeight()/30);
		timeLabel.setFont(Utils.getUpdatedFont(getWidth()-getWidth()/5));
		
		volumeSlider.setLocation(getWidth() - getWidth()/8, getHeight()/110);
		volumeSlider.setSize(getWidth()/10, getHeight()/50);

		configButton.setLocation(getWidth() - getWidth()/6, 0);
		configButton.setSize(getWidth()/30, getHeight()/30);
		if(trueConfigPanel.isVisible())
			configButton.setIcon(Utils.resizeImage(Const.IMAGE_HideButtonUp, configButton.getWidth(), configButton.getHeight()));
		else
			configButton.setIcon(Utils.resizeImage(Const.IMAGE_HideButtonDown, configButton.getWidth(), configButton.getHeight()));
		
		
		if(!onlyMenu) {
			
			//**********************
			// Game Panel components
			
			// Labels
			gamePanel.player1Label.setLocation(gamePanel.getWidth()/4, gamePanel.getHeight() - gamePanel.getHeight()/6);
			gamePanel.player1Label.setSize(gamePanel.getWidth()/8, gamePanel.getHeight()/16);
			gamePanel.player1Label.setFont(Utils.getUpdatedFont(getWidth() + getWidth()/2));
			
			gamePanel.player2Label.setLocation(gamePanel.getWidth() - gamePanel.getWidth()/4 - gamePanel.getWidth()/8, gamePanel.getHeight() - 
					gamePanel.getHeight()/6);
			gamePanel.player2Label.setSize(gamePanel.getWidth()/8, gamePanel.getHeight()/16);
			gamePanel.player2Label.setFont(Utils.getUpdatedFont(getWidth() + getWidth()/2));
			
			if(trueConfigPanel.scoreCheckBox.isSelected()) {
				gamePanel.scoresLabel.setVisible(true);
				gamePanel.setScores();
				gamePanel.scoresLabel.setLocation(gamePanel.getWidth()/2 - gamePanel.getWidth()/16, gamePanel.getHeight() - 
						gamePanel.getHeight()/3);
				gamePanel.scoresLabel.setSize(gamePanel.getWidth()/8, gamePanel.getHeight()/16);
				gamePanel.scoresLabel.setFont(Utils.getUpdatedFont(getWidth() + getWidth()/2));
			}
			else
				gamePanel.scoresLabel.setVisible(false);
			
			gamePanel.lastTurnLabel.setLocation(gamePanel.getWidth()/2 - gamePanel.getWidth()/16, gamePanel.getHeight() - 
					gamePanel.getHeight()/6);
			gamePanel.lastTurnLabel.setSize(gamePanel.getWidth()/8, gamePanel.getHeight()/16);
			gamePanel.lastTurnLabel.setFont(Utils.getUpdatedFont(getWidth() + getWidth()/2));
			
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
			gamePanel.winPanel.replaySameButton.setFont(Utils.getUpdatedFont(getWidth() - getWidth()/10));
			
			// Labels
			gamePanel.winPanel.winnerLabel.setLocation(gamePanel.winPanel.getWidth()/10, gamePanel.winPanel.getHeight()/15);
			gamePanel.winPanel.winnerLabel.setSize(gamePanel.winPanel.getWidth() - gamePanel.winPanel.getWidth()/8, 
					gamePanel.winPanel.getHeight()/10);
			gamePanel.winPanel.winnerLabel.setFont(Utils.getUpdatedFont(getWidth() + (getWidth()/5)*4));
			
			gamePanel.winPanel.scoreLabel.setLocation(gamePanel.winPanel.getWidth()/10, gamePanel.winPanel.getHeight()/5);
			gamePanel.winPanel.scoreLabel.setSize(gamePanel.winPanel.getWidth() - gamePanel.winPanel.getWidth()/8, 
					gamePanel.winPanel.getHeight()/10);
			gamePanel.winPanel.scoreLabel.setFont(Utils.getUpdatedFont(getWidth()));
			
			//**********************
			// Cards
			
			CardPanel.redrawCards(golf, game);
		}
	}
	
	//**************************************************************************
	// Classes
	
	private class KeyListener implements KeyEventDispatcher {
		
		/*
		 * Listener for the escape key
		 *  - Print the config panel if it is hidden
		 *  - Hide the config panel if it is printed
		 */
		
        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
        	
        	if(golf.isVisible()) {
        		if(event.getID() == KeyEvent.KEY_PRESSED) {
        			int keyCode = event.getKeyCode();
        			if(keyCode == KeyEvent.VK_ESCAPE) {
        				golf.trueConfigPanel.setVisible(!golf.trueConfigPanel.isVisible());
        				if(trueConfigPanel.isVisible())
        					configButton.setIcon(Utils.resizeImage(Const.IMAGE_HideButtonUp, configButton.getWidth(), configButton.getHeight()));
        				else
        					configButton.setIcon(Utils.resizeImage(Const.IMAGE_HideButtonDown, configButton.getWidth(), configButton.getHeight()));
        			}
        		}
        	}
        	return false;
        }
	}
}
