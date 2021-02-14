package fr.enigmacrew.golfcard;

import javax.swing.ImageIcon;

public class Const {

	//***********************
	// Paths
	
	public static final ImageIcon IMAGE_CardCarpet = new ImageIcon(Main.class.getResource("images/cardCarpet.png"));
	public static final ImageIcon IMAGE_CardBack = new ImageIcon(Main.class.getResource("images/cardBack1.png"));
	public static final ImageIcon IMAGE_EmptyTrash = new ImageIcon(Main.class.getResource("images/emptyTrash.gif"));
	public static final String cardsPath = "images/cards/";
	
	public static ImageIcon getCardImage(String card) {
		return new ImageIcon(Main.class.getResource(cardsPath + card + ".png"));
	}
	
}
