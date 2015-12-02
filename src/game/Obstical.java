package game;

import java.net.URL;

import javax.swing.*;

public class Obstical {
	private JLabel obsticalLabel;
	private int width, height;
	public static int speed, callCount;

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public Obstical() {

		obsticalLabel = new JLabel();
		width = 128;
		height = 128;
		URL ObsticalUrl = Player.class.getResource("/resources/trash1.png");
		obsticalLabel.setIcon(new ImageIcon(ObsticalUrl));
		obsticalLabel.setBounds(0, 500, width, height);
		speed = 1;
		callCount = 0;
	}

	public JLabel getLabel() {

		return obsticalLabel;
	}

	public void setLocation(String location) {
		switch (location) {
		case "center":
			obsticalLabel.setLocation(250 - 64, obsticalLabel.getY());
			break;
		case "left":
			obsticalLabel.setLocation(0, obsticalLabel.getY());
			break;
		case"right":
			obsticalLabel.setLocation(500 - 128, obsticalLabel.getY());
			break;
		}
	}

	public void moveUp() {
		int x = obsticalLabel.getX();
		int y = obsticalLabel.getY();
		y-=speed;
		obsticalLabel.setLocation(x, y);
		
		
		
	}

	public int getX() {
		return obsticalLabel.getX();
	}

	public int getY() {
		return obsticalLabel.getY();
	}

	public void reset() {
		obsticalLabel.setBounds(0, 500, width, height);
	}
}
