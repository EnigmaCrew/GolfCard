package fr.enigmacrew.golfcard.utils;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Utils {
	
	public static int randomNumber(int min, int max) {
		// Return a random number between min and max (both included)
		return (int) (Math.random() * (max-min+1)) + min;
	}
	
	public static int randomNumber(int max) {
		// Return a random number between 0 and max (both included)
		return randomNumber(0, max);
	}
	
	public static ImageIcon resizeImage(ImageIcon image, int new_width, int new_height) {
		// Resize the image given in parameter
		return new ImageIcon(image.getImage().getScaledInstance(new_width, new_height, Image.SCALE_DEFAULT)); 
	}
	
}
