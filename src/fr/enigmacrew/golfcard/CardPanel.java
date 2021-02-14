package fr.enigmacrew.golfcard;

import java.awt.Graphics;

import javax.swing.JPanel;

import fr.enigmacrew.golfcard.utils.Utils;

public class CardPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public CardPanel() {
		setOpaque(false);
		setLayout(null);
		setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Utils.resizeImage(Const.IMAGE_cardCarptet, getWidth()-10, getHeight()-35).getImage(), 0, 0, null);
	}
	
}
