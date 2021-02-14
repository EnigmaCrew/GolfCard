package fr.enigmacrew.golfcard;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import fr.enigmacrew.golfcard.Game.Phase;
import fr.enigmacrew.golfcard.GameAction.Kind;
import fr.enigmacrew.golfcard.utils.Utils;

public class CardPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Golf golf;
	private Game game;
	private GameCard card;
	private boolean p1;
	private int index;
	
	public CardPanel(Golf golf, Game game, GameCard card, boolean p1, int index, int x, int y) {
		
		this.golf = golf;
		this.game = game;
		this.card = card;
		this.p1 = p1;
		this.index = index;
		
		setLocation(x, y);
		setSize(golf.getWidth()/12, golf.gamePanel.getHeight()/5);
		setOpaque(false);
		setLayout(null);
		setVisible(true);
		
		//**********************
		// Click Listener
		
		addMouseListener(new ClickListener());
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(card != null && card.visible) {
			g.drawImage(Utils.resizeImage(Const.getCardImage(card.id + card.color.toString()), getWidth(), getHeight()).getImage(), 0, 0, null);
		}
		else {
			if(index == -1 && game.cardTrash.size() == 0)
				g.drawImage(Utils.resizeImage(Const.IMAGE_EmptyTrash, getWidth(), getHeight()).getImage(), 0, 0, null);
			else
				g.drawImage(Utils.resizeImage(Const.IMAGE_CardBack, getWidth(), getHeight()).getImage(), 0, 0, null);
		}
	}
	
	public static void redrawCard(Golf golf, Game game) {
		for(Component c : golf.gamePanel.getComponents()) {
			if(c instanceof CardPanel) {
				c.setVisible(false);
				golf.remove(c);
			}
		}
		
		// Player 1
		for(int i = 0; i < golf.sixOrNine; i++) {
			CardPanel c = new CardPanel(golf, game, game.p1.get(i), true, i, (i%3) * golf.gamePanel.getWidth()/10 + golf.gamePanel.getWidth()/10, 
					golf.gamePanel.getHeight()/10 + ((i/3)+1) * golf.gamePanel.getHeight()/4);
			golf.gamePanel.add(c);
		}
		
		// Player 2
		for(int i = 0; i < golf.sixOrNine; i++) {
			CardPanel c = new CardPanel(golf, game, game.p2.get(i), false, i, golf.gamePanel.getWidth()/2 + (i%3) * golf.gamePanel.getWidth()/10 + 
					golf.gamePanel.getWidth()/10, golf.gamePanel.getHeight()/10 + ((i/3)+1) * golf.gamePanel.getHeight()/4);
			golf.gamePanel.add(c);
		}

		// Deck
		CardPanel c = new CardPanel(golf, game, game.cardStack.get(game.cardStack.size()-1), false, -2, golf.gamePanel.getWidth()/2 - 
				golf.gamePanel.getWidth()/24, golf.gamePanel.getHeight()/100);
		golf.gamePanel.add(c);
		
		// Trash
		if(game.cardTrash.size() != 0) {
			c = new CardPanel(golf, game, game.cardTrash.get(game.cardTrash.size()-1), false, -1, golf.gamePanel.getWidth()/2 - 
					golf.gamePanel.getWidth()/24, golf.gamePanel.getHeight()/4);
			golf.gamePanel.add(c);
		}
		else {
			c = new CardPanel(golf, game, null, false, -1, golf.gamePanel.getWidth()/2 - 
					golf.gamePanel.getWidth()/24, golf.gamePanel.getHeight()/4);
			golf.gamePanel.add(c);
		}
		
	}
	
	private class ClickListener implements MouseListener {
		
		/*
		 * Display the card information on a click.
		 */
	
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if(game.phase != Phase.END) {
				if(card == null) {
					if(golf.drawTrashTurn == 1) {
						// Throw the picked card
						game.step(new GameAction(Kind.DRAW, -1));
						// Reset the action memory
						golf.drawTrashTurn = 3;
						redrawCard(golf, game);
					}
				}
				else {
					if(index == -2) {
						// Pick a card if the two first turns are over
						if(game.phase != Phase.START && game.cardStack.size() != 0) {
							game.cardStack.get(game.cardStack.size()-1).visible = true;
							golf.drawTrashTurn = 1;
							redrawCard(golf, game);
						}
					}
					else if((!card.visible) && game.p1Turn == p1 && index >= 0) {
						if(golf.drawTrashTurn == 3) {
							//Turn a card
							game.step(new GameAction(Kind.TURN, index));						
						}
						else {
							if(golf.drawTrashTurn == 2)
								// Switch the card from the trash
								game.step(new GameAction(Kind.TRASH, index));
							else
								// Switch the card from the deck
								game.step(new GameAction(Kind.DRAW, index));
						}
						// Reset the action memory
						golf.drawTrashTurn = 3;
						redrawCard(golf, game);
					}
					else if(index == -1) {
						if(golf.drawTrashTurn == 3) {
							// Pick a card
							golf.drawTrashTurn = 2;
						}
						else if(golf.drawTrashTurn != 2){
							// Throw the picked card
							game.step(new GameAction(Kind.DRAW, -1));
							// Reset the action memory
							golf.drawTrashTurn = 3;
							redrawCard(golf, game);
						}
					}
					else if(card.visible && game.p1Turn == p1 && index >= 0) {
						if(golf.drawTrashTurn != 3) {
							if(golf.drawTrashTurn == 2)
								// Switch the card from the trash
								game.step(new GameAction(Kind.TRASH, index));
							else
								// Switch the card from the deck
								game.step(new GameAction(Kind.DRAW, index));
							// Reset the action memory
							golf.drawTrashTurn = 3;
							redrawCard(golf, game);
						}
					}
				}
				if(game.phase == Phase.END) {
					// TODO Print the winner
					System.out.println("End of the game !");
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// Do nothing
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// Do nothing
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// Do nothing
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// Do nothing
		}
	}
	
}
