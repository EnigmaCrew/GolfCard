package fr.enigmacrew.golfcard.utils;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;

import fr.enigmacrew.golfcard.ui.Golf;

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
	
	public static Font getUpdatedFont(int width) {
		// Return an appropriated font for the resized frame
		if(width < Golf.DEFAULT_WIDTH - 200)
			return new Font("Arial", Font.BOLD, 10);
		if(width < Golf.DEFAULT_WIDTH)
			return new Font("Arial", Font.BOLD, 15);
		if(width < Golf.DEFAULT_WIDTH + 300)
			return new Font("Arial", Font.BOLD, 20);
		if(width < Golf.DEFAULT_WIDTH + 600)
			return new Font("Arial", Font.BOLD, 25);
		if(width < Golf.DEFAULT_WIDTH + 900)
			return new Font("Arial", Font.BOLD, 30);
		if(width < Golf.DEFAULT_WIDTH + 1200)
			return new Font("Arial", Font.BOLD, 35);
		if(width < Golf.DEFAULT_WIDTH + 1500)
			return new Font("Arial", Font.BOLD, 40);
		return new Font("Arial", Font.BOLD, 45);
	}
	
}
