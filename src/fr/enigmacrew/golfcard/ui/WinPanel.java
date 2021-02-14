package fr.enigmacrew.golfcard.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.enigmacrew.golfcard.game.Game;

public class WinPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private WinPanel winPanel;
	
	public Game game;
	public JButton closeButton = new JButton();
	public JButton reduceButton = new JButton();
	public JLabel winnerLabel = new JLabel();
	public JLabel scoreLabel = new JLabel();
	public JButton replayButton = new JButton();
	
	public WinPanel(GamePanel gamePanel) {
		
		/*
		 * The win panel (a popup that can be close and reduce)
		 * Print the scores and the winner
		 * Ask to play again and play again with the same configuration
		 */
		
		winPanel = this;
		
		setOpaque(true);
		setLayout(null);
		setVisible(false);
		
		setBackground(Golf.configColor);
		
		closeButton.setBackground(Color.RED);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Close the popup
				winPanel.setVisible(false);
			}
		});
		
		reduceButton.setBackground(Color.GRAY);
		reduceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Reduce the frame and put the shortcut in the config panel
				winPanel.setVisible(false);
				//TODO
			}
		});
		
		winnerLabel.setHorizontalAlignment(JLabel.CENTER);
		winnerLabel.setVerticalAlignment(JLabel.CENTER);
		
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreLabel.setVerticalAlignment(JLabel.CENTER);
		
		replayButton.setBackground(Golf.configColor);
		replayButton.setText("Replay");
		replayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Restart a new game
				winPanel.setVisible(false);
				if(game != null) {
					game.reset();
					gamePanel.golf.updateComponents();
				}
			}
		});
		
		add(closeButton);
		add(reduceButton);
		add(winnerLabel);
		add(scoreLabel);
		add(replayButton);
	}
	
	public void setValues(){
		if(game != null) {
			Integer[] scores = game.getScores();
			winnerLabel.setText(scores[0] > scores[1] ? "Player 2 Wins !" : "Player 1 Wins !");
			scoreLabel.setText("Player 1 : " + scores[0] + " - " + scores[1] + " : Player 2");
		}
	}
	
}
