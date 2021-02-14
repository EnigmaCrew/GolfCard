package fr.enigmacrew.golfcard;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

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
		setOpaque(true);
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
		if(card.visible) {
			g.drawImage(Utils.resizeImage(Const.getCardImage(card.id + card.color.toString()), getWidth(), getHeight()).getImage(), 0, 0, null);
		}
		else
			g.drawImage(Utils.resizeImage(Const.IMAGE_CardBack, getWidth(), getHeight()).getImage(), 0, 0, null);
	}
	
	public static void redrawCard(Golf golf, Game game) {
		for(Component c : golf.gamePanel.getComponents()) {
			if(c instanceof CardPanel) {
				c.setVisible(false);
				golf.remove(c);
			}
		}
		for(int i = 0; i < golf.sixOrNine; i++) {
			game.p1.get(i).visible = true;
			CardPanel c = new CardPanel(golf, game, game.p1.get(i), true, i, (i%3) * golf.gamePanel.getWidth()/10 + golf.gamePanel.getWidth()/10, 
					golf.gamePanel.getHeight()/10 + ((i/3)+1) * golf.gamePanel.getHeight()/4);
			golf.gamePanel.add(c);
		}
		for(int i = 0; i < golf.sixOrNine; i++) {
			game.p1.get(i).visible = true;
			CardPanel c = new CardPanel(golf, game, game.p2.get(i), false, i, golf.gamePanel.getWidth()/2 + (i%3) * golf.gamePanel.getWidth()/10 + 
					golf.gamePanel.getWidth()/10, golf.gamePanel.getHeight()/10 + ((i/3)+1) * golf.gamePanel.getHeight()/4);
			golf.gamePanel.add(c);
		}
		/*CardPanel c = new CardPanel(golf, game, game.cardTrash.get(game.cardTrash.size()-1), -1, 0, 0);
		golf.gamePanel.add(c);
		c = new CardPanel(golf, game, game.cardStack.get(game.cardStack.size()-1), -2, 0, 0);
		golf.gamePanel.add(c);*/
		
	}
	
	private class ClickListener implements MouseListener {
		
		/*
		 * Display the card information on a click.
		 */
	
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if((!card.visible) && game.p1Turn == p1) {
				System.out.println("coucou");
				game.step(new GameAction(Kind.TURN, index));
				redrawCard(golf, game);
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
