package mainpackage;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Takes care of the UI during gameplay
 */
public class UserInterface extends JFrame implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	private final int tetrisWidth = 400;
	private final int tetrisHeight = 800;
	private final int statsWidth = 400;
	private final int statsHeight = 200;
	private final int boxesWidth = 10;
	private final int boxesHeight = 20;
	private final int extraHeightForPiece=4;
	
	private TetrisPanel tetrispanel;
	private StatsPanel statspanel;
	
	/** Object that holds the state of the game logic */
	public GameState game;
	
	/** Thread for graphics loop. */
	private Thread looprunner;
	private boolean running=false;
	
	/**
	 * Constructor
	 * Creates the frame.
	 * Creates the loop for graphics and object that takes care of game logic.
	 */
	public UserInterface() {
		//create the object that takes care of game logic
		game = new GameState(boxesWidth, boxesHeight+extraHeightForPiece);
		this.setTitle("Tötris");
		
		//make tetrispanel
		tetrispanel = new TetrisPanel(game, tetrisWidth, tetrisHeight);
		//make statspanel
		statspanel = new StatsPanel(game, statsWidth,statsHeight);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//stops user from resizing screen
		this.setResizable(false);
		//adds tetris gamepanels to JFrame
		this.add((JPanel) statspanel, BorderLayout.NORTH);
		this.add((JPanel) tetrispanel, BorderLayout.SOUTH);
		//sets frame size to sum of heights=800+200, width 400
		this.setSize(tetrisWidth, tetrisHeight+statsHeight);
		//pack helps to center game on screen when the JFrame is set visible
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		//start the loop that takes care of graphics
		looprunner=new Thread(this);
		looprunner.start();
	}
	
	/**
	 * Starts frame drawing loop.
	 * Repaints the screen every 15 milliseconds (~60fps)
	 * Updates the panel that displays statistics and checks for game over.
	 */
	public void run(){
		running=true;
		while(running){
			tetrispanel.repaint();
			statspanel.update();
			if(game.gameOver)gameOver();
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				running=false;
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Saves high scores, closes screen, stops the loops that run the game and opens up main menu.
	 */
	public void gameOver() {
		game.stop();
		boolean highscore = false;
		if(game.points>game.highScore) {
			try {
				FileOutputStream fos = new FileOutputStream(new File("./Highscore.txt"));
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject((long) game.points);
				oos.close();
				fos.close();
				highscore=true;
				System.out.println("New highscore: " + game.points);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(highscore)JOptionPane.showMessageDialog(this, "Game over, your score: " + game.points + "\nNew highscore!");
		else JOptionPane.showMessageDialog(this, "Game over, your score: " + game.points);
		this.setVisible(false);
		this.dispose();
		this.running=false;
		new MainMenu();
	}

}
