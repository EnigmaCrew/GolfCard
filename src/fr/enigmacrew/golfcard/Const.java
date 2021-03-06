package fr.enigmacrew.golfcard;

import javax.swing.ImageIcon;

public class Const {

	//***********************
	// Paths
	
	public static final ImageIcon IMAGE_Logo = new ImageIcon(Main.class.getResource("images/logo.PNG"));
	public static final ImageIcon IMAGE_CardCarpet = new ImageIcon(Main.class.getResource("images/ui/cardCarpet.png"));
	public static final ImageIcon IMAGE_CloseButton = new ImageIcon(Main.class.getResource("images/ui/closeButton.png"));
	public static final ImageIcon IMAGE_ReduceButton = new ImageIcon(Main.class.getResource("images/ui/reduceButton.png"));
	public static final ImageIcon IMAGE_HideButtonUp = new ImageIcon(Main.class.getResource("images/ui/hideButtonUp.png"));
	public static final ImageIcon IMAGE_HideButtonDown = new ImageIcon(Main.class.getResource("images/ui/hideButtonDown.png"));
	public static final ImageIcon IMAGE_CardBack = new ImageIcon(Main.class.getResource("images/cardBack1.png"));
	public static final ImageIcon IMAGE_EmptyTrash = new ImageIcon(Main.class.getResource("images/emptyTrash.gif"));
	public static final ImageIcon IMAGE_SelectedCard = new ImageIcon(Main.class.getResource("images/selectedCard.gif"));
	public static final String cardsPath = "images/cards/";
	
	public static ImageIcon getCardImage(String card) {
		return new ImageIcon(Main.class.getResource(cardsPath + card + ".png"));
	}
	
}
