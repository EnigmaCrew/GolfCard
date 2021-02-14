package fr.enigmacrew.golfcard;

import java.awt.Graphics;

import javax.swing.JPanel;

import fr.enigmacrew.golfcard.utils.Utils;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public GamePanel() {
		setOpaque(false);
		setLayout(null);
		setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Utils.resizeImage(Const.IMAGE_CardCarpet, getWidth()-10, getHeight()-35).getImage(), 0, 0, null);
	}
	
}
