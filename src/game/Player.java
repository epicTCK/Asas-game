package game;

import java.net.URL;

import javax.swing.*;


public class Player {
	private JLabel PlayerLabel;
	
	private int x = 100; private int y = 0;

	public Player(){
		PlayerLabel = new JLabel();
	    PlayerLabel.setBounds(x, y, 75, 157);
	    URL PlayerUrl = Player.class.getResource("/resources/player.png");
	    PlayerLabel.setIcon(new ImageIcon(PlayerUrl));// the player's graphics   
	}
	public JLabel getLabel(){
		return PlayerLabel;
	}
	public void moveLeft(){
		if(x > 0 ) {x -= 2;}
		PlayerLabel.setLocation(x, y);
	}
	
	public void moveRight(){
	if( x + PlayerLabel.getWidth() < 500){ x+= 2; }// check for collision
		PlayerLabel.setLocation(x, y);
	}
	public double getX() {
		double returnX = PlayerLabel.getX();
		return returnX;
	}
	
	public double getScreenX() {
		double returnScreenX = PlayerLabel.getLocationOnScreen().getX();
		return returnScreenX;
	}
	public double getScreenY() {
		double returnScreenY = PlayerLabel.getLocationOnScreen().getY();
		return returnScreenY;
	}
	public double getY() {
		double returnY = PlayerLabel.getY();
		return returnY;
	}
	
	}