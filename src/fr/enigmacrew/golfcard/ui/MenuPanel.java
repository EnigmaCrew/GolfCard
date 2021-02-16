package fr.enigmacrew.golfcard.ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import fr.enigmacrew.golfcard.Const;
import fr.enigmacrew.golfcard.utils.Utils;

public class MenuPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	//**************************************************************************
	// Components and settings
	
	public LogoPanel logoPanel;
	public JButton choiceButton1;
	public JButton choiceButton2;
	public JButton backExitButton;
	
	public MenuPanel(Golf golf) {
		
		/*
		 * The menu panel (contains the choice buttons)
		 */
		
		//**************************************************************************
		// Initializing panel
		
		setOpaque(false);
		setLayout(null);
		setVisible(true);
		
		//**************************************************************************
		// Creating components
		
		logoPanel = new LogoPanel();
		
		choiceButton1 = new JButton();
		choiceButton1.setText("Player vs Player");
		choiceButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(choiceButton1.getText().equals("Player vs Player")) {
					choiceButton1.setText("6 cards");
					choiceButton2.setText("9 cards");
					backExitButton.setText("Back");
				}
				else {
					
				}
			}
		});
		
		choiceButton2 = new JButton();
		choiceButton2.setText("Player vs AI");
		choiceButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(choiceButton2.getText().equals("Player vs AI")) {
					choiceButton1.setText("6 cards");
					choiceButton2.setText("9 cards");
					backExitButton.setText("Back");
				}
				else {
					
				}
			}
		});
		
		backExitButton = new JButton();
		backExitButton.setText("Exit");
		backExitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(backExitButton.getText().equals("Exit"))
					System.exit(0);
				else {
					choiceButton1.setText("Player vs Player");
					choiceButton2.setText("Player vs AI");
					backExitButton.setText("Exit");
				}
			}
		});
		
		//**************************************************************************
		// Adding components
		
		add(logoPanel);
		add(choiceButton1);
		add(choiceButton2);
		add(backExitButton);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Utils.resizeImage(Const.IMAGE_CardCarpet, getWidth()-10, getHeight()-35).getImage(), 0, 0, null);
	}
	
	//**************************************************************************
	// Classes
	
	public class LogoPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		/*
		 * Display the logo on the menu panel
		 */
		
		public LogoPanel() {
			
			//**************************************************************************
			// Initializing panel
			
			setOpaque(false);
			setLayout(null);
			setVisible(true);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(Utils.resizeImage(Const.IMAGE_Logo, getWidth(), getHeight()).getImage(), 0, 0, null);
		}
	}
}
