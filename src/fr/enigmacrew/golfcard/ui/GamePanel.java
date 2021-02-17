package fr.enigmacrew.golfcard.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.enigmacrew.golfcard.Const;
import fr.enigmacrew.golfcard.ui.CardPanel.SelectedCard;
import fr.enigmacrew.golfcard.utils.Utils;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	//**************************************************************************
	// Components and settings
	
	public WinPanel winPanel;
	public SelectedCard selectedCard;
	public boolean selectedVisible;
	public char selectedType; // Deck (D) or Trash (T)
	public JLabel player1Label;
	public JLabel player2Label;
	public JLabel scoresLabel;
	public JLabel lastTurnLabel;
	
	public Golf golf;
	
	public GamePanel(Golf golf) {
		
		/*
		 * The game panel (contains all the cards and the win panel)
		 */
		
		//**************************************************************************
		// Initializing panel
		
		this.golf = golf;
		
		setOpaque(false);
		setLayout(null);
		setVisible(true);
		
		//**************************************************************************
		// Creating components
		
		winPanel = new WinPanel(golf);
		selectedCard = new CardPanel.SelectedCard(this, 0, 0);
		selectedVisible = false;
		selectedType = ' ';
		
		player1Label = new JLabel("Player 1");
		player1Label.setForeground(Color.BLACK);
		player1Label.setHorizontalAlignment(JLabel.CENTER);
		player1Label.setVerticalAlignment(JLabel.CENTER);
		
		player2Label = new JLabel("Player 2");
		player2Label.setForeground(Color.BLACK);
		player2Label.setHorizontalAlignment(JLabel.CENTER);
		player2Label.setVerticalAlignment(JLabel.CENTER);
		
		scoresLabel = new JLabel("0 - 0");
		scoresLabel.setForeground(Color.BLACK);
		scoresLabel.setVisible(false);
		scoresLabel.setHorizontalAlignment(JLabel.CENTER);
		scoresLabel.setVerticalAlignment(JLabel.CENTER);
		
		lastTurnLabel = new JLabel("Last Turn");
		lastTurnLabel.setForeground(Color.RED);
		lastTurnLabel.setVisible(false);
		lastTurnLabel.setHorizontalAlignment(JLabel.CENTER);
		lastTurnLabel.setVerticalAlignment(JLabel.CENTER);
		
		setTurn(1); // Default
		
		//**************************************************************************
		// Adding components
		
		add(player1Label);
		add(player2Label);
		add(selectedCard);
		add(winPanel);
		add(scoresLabel);
		add(lastTurnLabel);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Utils.resizeImage(Const.IMAGE_CardCarpet, getWidth()-10, getHeight()-35).getImage(), 0, 0, null);
	}
	
	//**************************************************************************
	// Functions
	
	public void setTurn(int player) {
		
		/*
		 * Print a blue border around the player who needs to play
		 */
		
		if(player == 0) {
			player1Label.setBorder(null);
			player2Label.setBorder(null);
		}
		else if(player == 1) {
			player1Label.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
			player2Label.setBorder(null);
		}
		else {
			player1Label.setBorder(null);
			player2Label.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		}
		
	}
	
	public void setScores() {
		Integer[] scores = golf.game.getScores();
		scoresLabel.setText(scores[0] + " : " + scores[1]);
	}
	
}
