package fr.enigmacrew.golfcard.ui;

import java.awt.Graphics;

import javax.swing.JPanel;

import fr.enigmacrew.golfcard.Const;
import fr.enigmacrew.golfcard.utils.Utils;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public WinPanel winPanel;
	
	public Golf golf;
	
	public GamePanel(Golf golf) {
		
		/*
		 * The game panel (contains all the cards and the win panel)
		 */
		
		this.golf = golf;
		
		setOpaque(false);
		setLayout(null);
		setVisible(true);
		
		winPanel = new WinPanel(this);
		
		add(winPanel);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Utils.resizeImage(Const.IMAGE_CardCarpet, getWidth()-10, getHeight()-35).getImage(), 0, 0, null);
	}
	
}
