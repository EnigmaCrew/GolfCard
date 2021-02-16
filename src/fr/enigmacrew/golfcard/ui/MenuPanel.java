package fr.enigmacrew.golfcard.ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.enigmacrew.golfcard.Const;
import fr.enigmacrew.golfcard.game.Game;
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
		choiceButton1.setText("Two Players");
		choiceButton1.setBackground(golf.configColor);
		choiceButton1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
            	if(choiceButton1.isContentAreaFilled())
            		choiceButton1.setContentAreaFilled(false);
            	else
            		choiceButton1.setContentAreaFilled(true);
            }
        });
		choiceButton1.setFocusPainted(false);
		choiceButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(choiceButton1.getText().equals("Two Players")) {
					choiceButton1.setText("6 cards");
					choiceButton2.setText("9 cards");
					backExitButton.setText("Back");
				}
				else {
					choiceButton1.setText("Two Players");
					choiceButton2.setText("One Player");
					backExitButton.setText("Exit");
					golf.sixOrNine = 6;
					golf.game = new Game(6);
					golf.menuPanel.setVisible(false);
					golf.gamePanel.setVisible(true);
					golf.updateComponents(false);
				}
			}
		});
		
		choiceButton2 = new JButton();
		choiceButton2.setText("One Player");
		choiceButton2.setBackground(golf.configColor);
		choiceButton2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
            	if(choiceButton2.isContentAreaFilled())
            		choiceButton2.setContentAreaFilled(false);
            	else
            		choiceButton2.setContentAreaFilled(true);
            }
        });
		choiceButton2.setFocusPainted(false);
		choiceButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(choiceButton2.getText().equals("One Player")) {
					choiceButton1.setText("6 cards");
					choiceButton2.setText("9 cards");
					backExitButton.setText("Back");
				}
				else {
					choiceButton1.setText("Two Players");
					choiceButton2.setText("One Player");
					backExitButton.setText("Exit");
					golf.sixOrNine = 9;
					golf.game = new Game(9);
					golf.menuPanel.setVisible(false);
					golf.gamePanel.setVisible(true);
					golf.updateComponents(false);
				}
			}
		});
		
		backExitButton = new JButton();
		backExitButton.setText("Exit");
		backExitButton.setBackground(golf.configColor);
		backExitButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
            	if(backExitButton.isContentAreaFilled())
            		backExitButton.setContentAreaFilled(false);
            	else
            		backExitButton.setContentAreaFilled(true);
            }
        });
		backExitButton.setFocusPainted(false);
		backExitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(backExitButton.getText().equals("Exit"))
					System.exit(0);
				else {
					choiceButton1.setText("Two Players");
					choiceButton2.setText("One Player");
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
