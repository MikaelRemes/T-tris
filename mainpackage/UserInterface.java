package mainpackage;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


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
	
	public GameState game;
	
	//thread for loop
	private Thread looprunner;
	private boolean running=false;
		
	public UserInterface() {
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
		//pack helps to center game on screen when jframe is set visible
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		looprunner=new Thread(this);
		looprunner.start();
	}
	
	/**
	 * starts frame drawing loop
	 * repaints the screen every 15 milliseconds (~60fps)
	 * updates the statspanel and checks for gameover
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
	 * saves highscores, closes the application and opens up main menu
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
