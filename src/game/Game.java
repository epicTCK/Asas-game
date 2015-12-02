package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import javax.swing.*;

public class Game {

	private int width, height;

	private JFrame frame;
	private JPanel mainPanel;
	private Player player;
	private Obstical a, b;
	private int score;
	private Boolean gameOver = false;
	

	public Game(int width, int height) {
		this.width = width;
		this.height = height;
		player = new Player();
		a = new Obstical();
		b = new Obstical();
	
		initDisplay(this.width, this.height);
		
		
	

	}

	void initDisplay(int width, int height) {
		/*
		 * Setting up the frame
		 */
		frame = new JFrame("Asa's Game");
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		Container pane = frame.getContentPane();
		Color background = new Color(255, 255, 255);
		pane.setBackground(background);
		frame.setVisible(true);

		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		/*
		 * Title Screen
		 */
		
		JLabel titlePictureLabel = new JLabel();
		JPanel titleScreen = new JPanel();
		JButton startGame = new JButton();
		
		
		startGame.setText("Play Game");
		startGame.setBounds(200, 300, 100, 100);
		
		ActionListener playGameAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startThreads();
				mainPanel.setVisible(true);
				frame.remove(titleScreen);
				
			}

		};
		startGame.addActionListener(playGameAction);
		
		
		titlePictureLabel.setBounds(100, 0, 354, 390);
		titleScreen.setBounds(0, 0, width, height);
		URL titlePicUrl = Player.class.getResource("/resources/title.png");
		titlePictureLabel.setIcon(new ImageIcon(titlePicUrl));
		
		titleScreen.setLayout(null);
		titleScreen.add(startGame);
		titleScreen.add(titlePictureLabel);
		
		titleScreen.setOpaque(false);
		frame.add(titleScreen);
		
		
		
		
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, width, height);
		mainPanel.add(player.getLabel());
	
		mainPanel.setVisible(false);
		
		mainPanel.setOpaque(false);
		

		frame.add(mainPanel);
	
		frame.repaint();
		
	}

	void startThreads() {

		Thread inputThread = new Thread() {
			@Override
			public void run() {
				getInputAlt();
			}
		};
		inputThread.start();
		Thread loopObsticalThread = new Thread() {
			@Override
			public void run() {
				loopObsticals();
			}
		};
		loopObsticalThread.start();

		Thread checkIfDead = new Thread() {
			@Override
			public void run() {
				try {
					checkIfDead();
				} catch (IOException | URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		checkIfDead.start();
	}

	void loopObsticals() {
		mainPanel.add(a.getLabel());
		mainPanel.add(b.getLabel());
		score = 0;
		int speedMulti = 5;
		while (gameOver != true) {
			score++;

			if (score % speedMulti == 0 && Obstical.speed < 5) {
				Obstical.speed++;
				speedMulti += speedMulti;
				speedMulti += speedMulti;
			}

			frame.setTitle("Asa's Game | Score: " + score);
			Boolean useB = false;
			Random random = new Random();
			a.reset();
			b.reset();
			int set = random.nextInt(6) + 1;
			switch (set) {
			case 1:
				a.setLocation("right");
				break;
			case 2:
				a.setLocation("left");
				break;
			case 3:
				a.setLocation("center");
				break;
			case 4:
				a.setLocation("center");
				b.setLocation("left");
				useB = true;
				break;
			case 5:
				a.setLocation("center");
				b.setLocation("right");
				useB = true;
				break;
			case 6:
				a.setLocation("left");
				b.setLocation("right");
				useB = true;
			}

			while (a.getY() + a.getHeight() > 0) {
				if (useB) {
					b.moveUp();
				}
				a.moveUp();
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	void getInputAlt() {

		double mousePos = 0;

		while (gameOver != true) {

			Point mousePoint = MouseInfo.getPointerInfo().getLocation();

			mousePos = mousePoint.getX();

			if (mousePos < player.getScreenX()) {
				player.moveLeft();
			} else if (mousePos > player.getScreenX()) {
				player.moveRight();
			}

		}
	}

	void checkIfDead() throws IOException, URISyntaxException {

		while (true) {
			try {
				Thread.sleep(5);// for some reason the game doesnt work without this
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//check for collision
			if (a.getLabel().getBounds().intersects(player.getLabel().getBounds())
					|| b.getLabel().getBounds().intersects(player.getLabel().getBounds())) {
				
				gameOver = true;
				frame.getContentPane().setLayout(null);
				try {
					Thread.sleep(100);// wait for the other loops to realise the game has ended.
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				frame.remove(mainPanel);
				
				int highScore = getHigh();
				
				if (highScore < score) {
					highScore = score;
					setHigh(highScore);
				}
			
				JLabel gameOverDisplay = new JLabel();
				JLabel scoreDisplay = new JLabel();
				
				// play again button and actions 
				JButton playAgain = new JButton();
				
				ActionListener playAgainAction = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
							new Game(500, 500);
							try {
								Thread.sleep(50);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}// create a new game
							frame.dispose();
					}

				};
				
				playAgain.addActionListener(playAgainAction);
				
				playAgain.setBounds(200, 300, 100, 100);
				playAgain.setText("Play again?");
				//the score and highscore
				scoreDisplay.setText("Score: " + score + " | Highscore: " + highScore + " | Boss level: " + Obstical.speed);
				scoreDisplay.setBounds(100, 400, 400, 100);
				gameOverDisplay.setBounds(0, 0, 500, 300);
				URL gameOverUrl = getClass().getResource("/resources/gameOver.png");
				gameOverDisplay.setIcon(new ImageIcon(gameOverUrl));
			
				// add all the gameOver display stuff to frame
				frame.add(gameOverDisplay);
				frame.add(scoreDisplay);
				frame.add(playAgain);
				
				frame.revalidate();
				frame.repaint();
				
			
				break;
			}
		}
	}

	int getHigh() throws IOException, URISyntaxException {
	
		
		FileReader fileInReader;
		URL getHighUrl = getClass().getResource("/resources/highScore.txt");
		
		File fileIn = new File(getHighUrl.toURI());
		fileInReader = new FileReader(fileIn);

		BufferedReader bufferedIn = new BufferedReader(fileInReader);
		String highScoreS = bufferedIn.readLine();
		bufferedIn.close();
		fileInReader.close();
		

		int highScore = Integer.parseInt(highScoreS);
		return highScore;
		
	}

	void setHigh(int high) throws IOException, URISyntaxException {
		URL setHighUrl = getClass().getResource("/resources/highScore.txt");
		File fileOut = new File(setHighUrl.toURI());
		FileWriter fileOutReader = new FileWriter(fileOut);

		BufferedWriter bufferedOut = new BufferedWriter(fileOutReader);

		bufferedOut.write(Integer.toString(high));
		bufferedOut.close();
		fileOutReader.close();
		
		

	}
}
