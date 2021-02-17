package fr.enigmacrew.golfcard.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.enigmacrew.golfcard.Const;
import fr.enigmacrew.golfcard.utils.Utils;

public class ConfigPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	//**************************************************************************
	// Config pannel settings
	
	private ConfigPanel configPanel;
	
	//**************************************************************************
	// Components and settings
	
	public JCheckBox scoreCheckBox = new JCheckBox();
	public JButton replayButton = new JButton();
	
	public ConfigPanel(Golf golf) {
		
		/*
		 * The config panel (a popup that can be closed)
		 * Contain a box that, if ticked, prints the scores
		 * Ask to play again and play again with the same configuration
		 */
		
		//**************************************************************************
		// Initializing panel
		
		configPanel = this;
		
		setOpaque(true);
		setLayout(null);
		setVisible(false);
		
		setBackground(golf.configColor);
		
		//**************************************************************************
		// Creating components
		
		//**********************
		// Replay button
		
		replayButton.setBackground(golf.configColor);
		replayButton.setText("Play again");
		replayButton.setFocusable(false);
		replayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Restart a new game
				configPanel.setVisible(false);
				golf.reducedWinFrameButton.setVisible(false);
				golf.configButton.setIcon(Utils.resizeImage(Const.IMAGE_HideButtonDown, 
						golf.configButton.getWidth(), golf.configButton.getHeight()));
				if(golf.game != null) {
					golf.game.reset();
					golf.gamePanel.setTurn(1);
					golf.updateComponents(false);
					golf.gamePanel.setVisible(false);
					golf.menuPanel.setVisible(true);
				}
			}
		});
		
		//**********************
		// CheckBox
		
		scoreCheckBox.setBackground(golf.configColor);
		scoreCheckBox.setText("Show scores");
		scoreCheckBox.setFocusable(false);
		scoreCheckBox.setHorizontalAlignment(JLabel.CENTER);
		scoreCheckBox.setVerticalAlignment(JLabel.CENTER);
		scoreCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(golf.game != null)
					golf.updateComponents(false);
			}
		});
		
		//**************************************************************************
		// Adding components
		
		add(scoreCheckBox);
		add(replayButton);
	}
}
